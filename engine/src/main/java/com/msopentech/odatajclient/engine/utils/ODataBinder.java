/*
 * Copyright 2013 MS OpenTech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msopentech.odatajclient.engine.utils;

import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.LinkResource;
import com.msopentech.odatajclient.engine.data.ODataCollectionValue;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataFeed;
import com.msopentech.odatajclient.engine.data.ODataInlineEntity;
import com.msopentech.odatajclient.engine.data.ODataInlineFeed;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataLinkType;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.data.atom.AtomLink;
import com.msopentech.odatajclient.engine.data.json.JSONEntry;
import com.msopentech.odatajclient.engine.data.metadata.EdmType;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import java.io.StringWriter;
import java.net.URI;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ODataBinder {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ODataBinder.class);

    private static Element newEntryContent() {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        Element properties = null;
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.newDocument();
            properties = doc.createElement(ODataConstants.ELEM_PROPERTIES);
            properties.setAttribute(ODataConstants.XMLNS_METADATA, ODataConstants.NS_METADATA);
            properties.setAttribute(ODataConstants.XMLNS_DATASERVICES, ODataConstants.NS_DATASERVICES);
            properties.setAttribute(ODataConstants.XMLNS_GML, ODataConstants.NS_GML);
            properties.setAttribute(ODataConstants.XMLNS_GEORSS, ODataConstants.NS_GEORSS);
        } catch (ParserConfigurationException e) {
            LOG.error("Failure building entry content", e);
        }

        return properties;
    }

    @SuppressWarnings("unchecked")
    public static <T extends EntryResource> T getEntry(final ODataEntity entity, final Class<T> reference) {
        T entry;

        if (reference.equals(AtomEntry.class)) {
            entry = (T) getAtomEntry(entity);
        } else {
            entry = (T) getJSONEntry(entity);
        }

        return entry;
    }

    private static AtomEntry getAtomEntry(final ODataEntity entity) {
        final AtomEntry entry = new AtomEntry();

        // -------------------------------------------------------------
        // Add edit and self link
        // -------------------------------------------------------------
        final URI editLink = entity.getEditLink();
        if (editLink != null) {
            AtomLink entryEditLink = new AtomLink();
            entryEditLink.setTitle(entity.getName());
            entryEditLink.setHref(editLink.toASCIIString());
            entryEditLink.setRel(ODataConstants.EDIT_LINK_REL);
            entry.getValues().add(entryEditLink);
        }

        final URI selfLink = entity.isReadOnly() ? entity.getLink() : null;
        if (selfLink != null) {
            AtomLink entrySelfLink = new AtomLink();
            entrySelfLink.setTitle(entity.getName());
            entrySelfLink.setHref(selfLink.toASCIIString());
            entrySelfLink.setRel(ODataConstants.SELF_LINK_REL);
            entry.getValues().add(entrySelfLink);
        }
        // -------------------------------------------------------------

        // -------------------------------------------------------------
        // Append navigation links (handling inline entry / feed as well)
        // -------------------------------------------------------------
        // handle navigation links
        for (ODataLink link : entity.getNavigationLinks()) {
            final AtomLink atomLink = new AtomLink();
            entry.getValues().add(atomLink);

            atomLink.setHref(link.getLink().toASCIIString());
            atomLink.setRel(link.getRel());
            atomLink.setTitle(link.getName());
            atomLink.setType(link.getType().toString());

            // append link 
            LOG.debug("Append navigation link\n{}", link);

            if (link instanceof ODataInlineEntity) {
                // append inline entity
                final ODataEntity inlineEntity = ((ODataInlineEntity) link).getEntity();
                LOG.debug("Append in-line entity{}\n", inlineEntity);

                atomLink.setInlineEntry(getEntry(inlineEntity, AtomEntry.class));
            } else if (link instanceof ODataInlineFeed) {
                // append inline feed
                final ODataFeed inlineFeed = ((ODataInlineFeed) link).getFeed();
                LOG.debug("Append in-line feed\n{}", inlineFeed);
            }
        }
        // -------------------------------------------------------------

        // -------------------------------------------------------------
        // Append media-edit links
        // -------------------------------------------------------------
        for (ODataLink link : entity.getMediaEditLinks()) {
            final AtomLink mediaEditLink = new AtomLink();
            mediaEditLink.setRel(link.getRel());
            mediaEditLink.setTitle(link.getName());
            mediaEditLink.setHref(link.getLink().toASCIIString());
            mediaEditLink.setType(link.getType().toString());
        }
        // -------------------------------------------------------------

        final Element content = newEntryContent();
        if (entity.isMediaEntity()) {
            entry.setMediaEntryProperties(content);
            entry.setMediaContent(entity.getMediaContentSource(), entity.getMediaContentType());
        } else {
            entry.setContent(content);
        }

        for (ODataProperty prop : entity.getProperties()) {
            content.appendChild(newProperty(prop, content.getOwnerDocument()));
        }

        return entry;
    }

    private static JSONEntry getJSONEntry(final ODataEntity entity) {
        final JSONEntry entry = new JSONEntry();

        entry.setContent(newEntryContent());

        final Element properties = entry.getContent();

        for (ODataProperty prop : entity.getProperties()) {
            properties.appendChild(newProperty(prop, properties.getOwnerDocument()));
        }

        return entry;
    }

    public static ODataEntity getODataEntity(final EntryResource entry) {
        if (LOG.isDebugEnabled()) {
            StringWriter writer = new StringWriter();
            SerializationUtils.serializeEntry(entry, writer);
            writer.flush();
            LOG.debug("Processing entity:\n{}", writer.toString());
        }

        final ODataEntity entity = entry.getSelfLink() == null
                ? ODataFactory.newEntity(entry.getType())
                : ODataFactory.newEntity(entry.getType(), URIUtils.getURI(entry.getBaseURI(), entry.getSelfLink()));

        if (entry.getEditLink() != null) {
            entity.setEditLink(URIUtils.getURI(entry.getBaseURI(), entry.getEditLink()));
        }

        for (LinkResource link : entry.getAssociationLinks()) {
            entity.addLink(ODataFactory.newAssociationLink(link.getTitle(), entry.getBaseURI(), link.getHref()));
        }

        for (LinkResource link : entry.getNavigationLinks()) {
            EntryResource inlineEntry = link.getInlineEntry();
            if (inlineEntry != null) {
                entity.addLink(ODataFactory.newInlineEntity(
                        link.getTitle(), entry.getBaseURI(), link.getHref(),
                        getODataEntity(inlineEntry)));
            } else {
                entity.addLink(
                        ODataFactory.newEntityNavigationLink(
                        link.getTitle(), entry.getBaseURI(), link.getHref()));
            }
        }

        for (LinkResource link : entry.getMediaEditLinks()) {
            entity.addLink(ODataFactory.newMediaEditLink(link.getTitle(), entry.getBaseURI(), link.getHref()));
        }

        final Element content;

        if (entry.isMediaEntry()) {
            entity.setMediaEntity(true);
            entity.setMediaContentSource(entry.getMediaContentSource());
            entity.setMediaContentType(entry.getMediaContentType());
            content = entry.getOtherContent();
        } else {
            content = entry.getContent();
        }

        if (content != null) {
            for (int i = 0; i < content.getChildNodes().getLength(); i++) {
                final Element property = (Element) content.getChildNodes().item(i);

                try {
                    if (property.getNodeType() != Node.TEXT_NODE) {
                        entity.addProperty(newProperty(property));
                    }
                } catch (IllegalArgumentException e) {
                    LOG.warn("Failure retrieving EdmType for {}", property.getTextContent(), e);
                }
            }
        }

        return entity;
    }

    public static ODataLink getODataLink(final LinkResource link) {
        return getODataLink(link, null);
    }

    public static ODataLink getODataLink(final LinkResource link, final EntryResource entry) {
        return ODataFactory.newLink(
                link.getTitle(),
                entry == null ? URI.create(link.getHref()) : URIUtils.getURI(entry.getBaseURI(), link.getHref()),
                ODataLinkType.evaluate(link.getRel(), link.getType()));
    }

    private static ODataProperty newProperty(final Element property) {
        final Node typeNode = property.getAttributes().getNamedItem(ODataConstants.ATTR_TYPE);

        try {
            final ODataProperty res;

            if (typeNode == null) {
                final Node nullNode = property.getAttributes().getNamedItem(ODataConstants.ATTR_NULL);
                if (nullNode == null) {
                    res = newPrimitiveProperty(property, new EdmType(EdmSimpleType.STRING.toString()));
                } else {
                    res = new ODataProperty(property.getLocalName(), null);
                }
            } else {
                final EdmType edmType = new EdmType(typeNode.getTextContent());

                if (edmType.isCollection()) {
                    // Collection
                    res = newCollectionProperty(property, edmType);
                } else if (edmType.isSimpleType()) {
                    // EdmSimpleType
                    res = newPrimitiveProperty(property, edmType);
                } else {
                    // ComplexType or EnumType
                    res = newComplexProperty(property, edmType);
                }
            }

            return res;
        } catch (IllegalArgumentException e) {
            LOG.warn("Failure retriving EdmSimpleType {}", typeNode.getTextContent(), e);
            throw e;
        }
    }

    private static Element newNullProperty(final ODataProperty prop, final Document doc) {
        final Element element = doc.createElement(ODataConstants.PREFIX_DATASERVICES + prop.getName());
        element.setAttribute(ODataConstants.ATTR_NULL, Boolean.toString(true));
        return element;
    }

    private static Element newProperty(final ODataProperty prop, final Document doc) {
        final Element element;

        if (prop.getValue() == null) {
            element = newNullProperty(prop, doc);
        } else if (prop.getValue() instanceof ODataPrimitiveValue) {
            // primitive property handling
            element = newPrimitiveProperty(prop, doc);
        } else if (prop.getValue() instanceof ODataCollectionValue) {
            // collection property handling
            element = newCollectionProperty(prop, doc);
        } else {
            // complex property handling
            element = newComplexProperty(prop, doc);
        }

        return element;
    }

    private static ODataPrimitiveValue newPrimitiveValue(final Element prop, final EdmType edmType) {
        return new ODataPrimitiveValue(prop.getTextContent(), edmType.getSimpleType());
    }

    private static ODataProperty newPrimitiveProperty(final Element prop, final EdmType edmType) {
        return new ODataProperty(prop.getLocalName(), newPrimitiveValue(prop, edmType));
    }

    private static Element newPrimitiveProperty(final ODataProperty prop, final Document doc) {
        return newPrimitiveProperty(prop.getName(), prop.getValue(), doc);
    }

    private static Element newPrimitiveProperty(
            final String name, final ODataValue propValue, final Document doc) {
        if (!(propValue instanceof ODataPrimitiveValue)) {
            throw new IllegalArgumentException("Invalid property value type " + propValue.getClass().getSimpleName());
        }

        final ODataPrimitiveValue value = (ODataPrimitiveValue) propValue;

        final Element element = doc.createElement(ODataConstants.PREFIX_DATASERVICES + name);
        element.setAttribute(ODataConstants.ATTR_TYPE, value.getTypeName());
        element.setTextContent(value.toString());
        return element;
    }

    private static ODataComplexValue newComplexValue(final Element prop, final EdmType edmType) {
        final ODataComplexValue value = new ODataComplexValue(edmType.getTypeExpression());

        final NodeList elements = prop.getChildNodes();

        for (int i = 0; i < elements.getLength(); i++) {
            final Element child = (Element) elements.item(i);
            if (child.getNodeType() != Node.TEXT_NODE) {
                value.add(newProperty(child));
            }
        }

        return value;
    }

    private static ODataProperty newComplexProperty(final Element prop, final EdmType edmType) {
        return new ODataProperty(prop.getLocalName(), newComplexValue(prop, edmType));
    }

    private static Element newComplexProperty(final ODataProperty prop, final Document doc) {
        return newComplexProperty(prop.getName(), prop.getValue(), doc);
    }

    private static Element newComplexProperty(final String name, final ODataValue propValue, final Document doc) {
        if (!(propValue instanceof ODataComplexValue)) {
            throw new IllegalArgumentException("Invalid property value type "
                    + propValue.getClass().getSimpleName());
        }

        final ODataComplexValue value = (ODataComplexValue) propValue;

        final Element element = doc.createElement(ODataConstants.PREFIX_DATASERVICES + name);
        element.setAttribute(ODataConstants.ATTR_TYPE, value.getTypeName());

        for (ODataProperty field : value) {
            element.appendChild(newProperty(field, doc));
        }
        return element;
    }

    private static ODataProperty newCollectionProperty(final Element prop, final EdmType edmType) {
        final ODataCollectionValue value = new ODataCollectionValue(edmType.getTypeExpression());

        final NodeList elements = prop.getChildNodes();

        for (int i = 0; i < elements.getLength(); i++) {
            final Element child = (Element) elements.item(i);
            if (child.getNodeType() != Node.TEXT_NODE) {
                final EdmType type = new EdmType(edmType.getBaseType());

                if (edmType.isSimpleType()) {
                    // collection of EdmSimpleType
                    value.add(newPrimitiveValue(child, type));
                } else {
                    // collection of ComplexType or EnumType            
                    value.add(newComplexValue(child, type));
                }
            }
        }

        return new ODataProperty(prop.getLocalName(), value);
    }

    private static Element newCollectionProperty(final ODataProperty prop, final Document doc) {
        if (!(prop.getValue() instanceof ODataCollectionValue)) {
            throw new IllegalArgumentException("Invalid property value type "
                    + prop.getValue().getClass().getSimpleName());
        }

        final ODataCollectionValue value = (ODataCollectionValue) prop.getValue();

        final Element element = doc.createElement(ODataConstants.PREFIX_DATASERVICES + prop.getName());
        element.setAttribute(ODataConstants.ATTR_TYPE, value.getTypeName());

        for (ODataValue el : value) {
            if (el instanceof ODataPrimitiveValue) {
                element.appendChild(newPrimitiveProperty(ODataConstants.ELEM_ELEMENT, el, doc));
            } else {
                element.appendChild(newComplexProperty(ODataConstants.ELEM_ELEMENT, el, doc));
            }
        }

        return element;
    }
}
