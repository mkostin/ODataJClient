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
package com.msopentech.odatajclient.engine.data;

import com.msopentech.odatajclient.engine.data.ODataProperty.PropertyType;
import com.msopentech.odatajclient.engine.data.metadata.EdmType;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Point;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import com.msopentech.odatajclient.engine.utils.SerializationUtils;
import com.msopentech.odatajclient.engine.utils.URIUtils;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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

    public static <T extends FeedResource> T getFeed(final ODataFeed feed, final Class<T> reference) {
        final T feedResource = ResourceFactory.newFeed(reference);

        final List<EntryResource> entries = new ArrayList<EntryResource>();
        feedResource.setEntries(entries);

        final URI next = feed.getNext();
        if (next != null) {
            final LinkResource nextLink = ResourceFactory.newLinkForFeed(reference);
            nextLink.setTitle(feed.getName());
            nextLink.setHref(next.toASCIIString());
            nextLink.setRel(ODataConstants.NEXT_LINK_REL);
            feedResource.setNext(nextLink);
        }

        for (ODataEntity entity : feed.getEntities()) {
            entries.add(getEntry(entity, ResourceFactory.entryClassForFeed(reference)));
        }

        feedResource.setEntries(entries);

        return feedResource;
    }

    @SuppressWarnings("unchecked")
    public static <T extends EntryResource> T getEntry(final ODataEntity entity, final Class<T> reference) {
        T entry = ResourceFactory.newEntry(reference);

        // -------------------------------------------------------------
        // Add edit and self link
        // -------------------------------------------------------------
        final URI editLink = entity.getEditLink();
        if (editLink != null) {
            LinkResource entryEditLink = ResourceFactory.newLinkForEntry(reference);
            entryEditLink.setTitle(entity.getName());
            entryEditLink.setHref(editLink.toASCIIString());
            entryEditLink.setRel(ODataConstants.EDIT_LINK_REL);
            entry.setEditLink(entryEditLink);
        }

        final URI selfLink = entity.isReadOnly() ? entity.getLink() : null;
        if (selfLink != null) {
            LinkResource entrySelfLink = ResourceFactory.newLinkForEntry(reference);
            entrySelfLink.setTitle(entity.getName());
            entrySelfLink.setHref(selfLink.toASCIIString());
            entrySelfLink.setRel(ODataConstants.SELF_LINK_REL);
            entry.setSelfLink(entrySelfLink);
        }
        // -------------------------------------------------------------

        // -------------------------------------------------------------
        // Append navigation links (handling inline entry / feed as well)
        // -------------------------------------------------------------
        // handle navigation links
        for (ODataLink link : entity.getNavigationLinks()) {
            // append link 
            LOG.debug("Append navigation link\n{}", link);
            entry.addNavigationLink(getLinkResource(link, ResourceFactory.linkClassForEntry(reference)));
        }
        // -------------------------------------------------------------

        // -------------------------------------------------------------
        // Append edit-media links
        // -------------------------------------------------------------
        for (ODataLink link : entity.getEditMediaLinks()) {
            LOG.debug("Append edit-media link\n{}", link);
            entry.addMediaEditLink(getLinkResource(link, ResourceFactory.linkClassForEntry(reference)));
        }
        // -------------------------------------------------------------

        // -------------------------------------------------------------
        // Append association links
        // -------------------------------------------------------------
        for (ODataLink link : entity.getAssociationLinks()) {
            LOG.debug("Append association link\n{}", link);
            entry.addAssociationLink(getLinkResource(link, ResourceFactory.linkClassForEntry(reference)));
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

    public static ODataFeed getODataFeed(final FeedResource feedResource) {
        final LinkResource next = feedResource.getNext();

        final ODataFeed feed = next == null
                ? ODataFactory.newFeed()
                : ODataFactory.newFeed(URI.create(next.getHref()));

        for (EntryResource entryResource : feedResource.getEntries()) {
            feed.addEntity(getODataEntity(entryResource));
        }

        return feed;
    }

    public static ODataEntity getODataEntity(final EntryResource entry) {
        return getODataEntity(entry, null);
    }

    public static ODataEntity getODataEntity(final EntryResource entry, final URI defaultBaseURI) {
        if (LOG.isDebugEnabled()) {
            StringWriter writer = new StringWriter();
            SerializationUtils.serializeEntry(entry, writer);
            writer.flush();
            LOG.debug("Processing entity:\n{}", writer.toString());
        }

        final URI base = defaultBaseURI == null ? entry.getBaseURI() : defaultBaseURI;

        final ODataEntity entity = entry.getSelfLink() == null
                ? ODataFactory.newEntity(entry.getType())
                : ODataFactory.newEntity(entry.getType(), URIUtils.getURI(base, entry.getSelfLink().getHref()));

        if (entry.getEditLink() != null) {
            entity.setEditLink(URIUtils.getURI(base, entry.getEditLink().getHref()));
        }

        for (LinkResource link : entry.getAssociationLinks()) {
            entity.addLink(ODataFactory.newAssociationLink(link.getTitle(), base, link.getHref()));
        }

        for (LinkResource link : entry.getNavigationLinks()) {
            EntryResource inlineEntry = link.getInlineEntry();
            if (inlineEntry != null) {
                entity.addLink(ODataFactory.newInlineEntity(
                        link.getTitle(), base, link.getHref(),
                        getODataEntity(inlineEntry, inlineEntry.getBaseURI() == null ? base : inlineEntry.getBaseURI())));
            } else {
                entity.addLink(ODataFactory.newEntityNavigationLink(link.getTitle(), base, link.getHref()));
            }
        }

        for (LinkResource link : entry.getMediaEditLinks()) {
            entity.addLink(ODataFactory.newMediaEditLink(link.getTitle(), base, link.getHref()));
        }

        final Element content;

        if (entry.isMediaEntry()) {
            entity.setMediaEntity(true);
            entity.setMediaContentSource(entry.getMediaContentSource());
            entity.setMediaContentType(entry.getMediaContentType());
            content = entry.getMediaEntryProperties();
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

    @SuppressWarnings("unchecked")
    public static <T extends LinkResource> T getLinkResource(final ODataLink link, final Class<T> reference) {
        final T linkResource = ResourceFactory.newLink(reference);
        linkResource.setRel(link.getRel());
        linkResource.setTitle(link.getName());
        linkResource.setHref(link.getLink().toASCIIString());
        linkResource.setType(link.getType().toString());

        if (link instanceof ODataInlineEntity) {
            // append inline entity
            final ODataEntity inlineEntity = ((ODataInlineEntity) link).getEntity();
            LOG.debug("Append in-line entity\n{}", inlineEntity);

            linkResource.setInlineEntry(getEntry(inlineEntity, ResourceFactory.entryClassForLink(reference)));
        } else if (link instanceof ODataInlineFeed) {
            // append inline feed
            final ODataFeed inlineFeed = ((ODataInlineFeed) link).getFeed();
            LOG.debug("Append in-line feed\n{}", inlineFeed);

            linkResource.setInlineFeed(getFeed(inlineFeed, ResourceFactory.feedClassForLink(reference)));
        }

        return linkResource;
    }

    public static ODataProperty newProperty(final Element property) {
        final ODataProperty res;

        final Node nullNode = property.getAttributes().getNamedItem(ODataConstants.ATTR_NULL);

        if (nullNode == null) {
            final Node typeNode = property.getAttributes().getNamedItem(ODataConstants.ATTR_TYPE);

            final EdmType edmType = typeNode == null ? null : new EdmType(typeNode.getTextContent());

            switch (getPropertyType(property)) {
                case EMPTY:
                case COLLECTION:
                    res = newCollectionProperty(property, edmType);
                    break;

                case COMPLEX:
                    res = newComplexProperty(property, edmType);
                    break;

                case PRIMITIVE:
                    res = newPrimitiveProperty(property, edmType);
                    break;

                default:
                    res = new ODataProperty(property.getLocalName(), null);
            }
        } else {
            res = new ODataProperty(property.getLocalName(), null);
        }

        return res;
    }

    private static PropertyType getPropertyType(final Element property) {
        PropertyType res = null;

        if (property.hasChildNodes()) {
            final NodeList children = property.getChildNodes();

            for (int i = 0; res == null && i < children.getLength(); i++) {
                final Node child = children.item(i);

                if (child.getNodeType() == Node.ELEMENT_NODE
                        && !child.getNodeName().startsWith(ODataConstants.PREFIX_GML)) {

                    res = (ODataConstants.PREFIX_DATASERVICES + ODataConstants.ELEM_ELEMENT).
                            equals(child.getNodeName())
                            ? PropertyType.COLLECTION
                            : PropertyType.COMPLEX;
                }
            }
        } else {
            res = PropertyType.EMPTY;
        }

        if (res == null) {
            res = PropertyType.PRIMITIVE;
        }

        return res;
    }

    private static Element newNullProperty(final ODataProperty prop, final Document doc) {
        final Element element = doc.createElement(ODataConstants.PREFIX_DATASERVICES + prop.getName());
        element.setAttribute(ODataConstants.ATTR_NULL, Boolean.toString(true));
        return element;
    }

    private static Element newProperty(final ODataProperty prop, final Document doc) {
        final Element element;

        if (prop.hasNullValue()) {
            element = newNullProperty(prop, doc);
        } else if (prop.hasPrimitiveValue()) {
            // primitive property handling
            element = newPrimitiveProperty(prop, doc);
        } else if (prop.hasCollectionValue()) {
            // collection property handling
            element = newCollectionProperty(prop, doc);
        } else {
            // complex property handling
            element = newComplexProperty(prop, doc);
        }

        return element;
    }

    private static ODataPrimitiveValue newPrimitiveValue(final Element prop, final EdmType edmType) {
        // Geospatial types value management
        final StringBuffer geoData = new StringBuffer();
        final NodeList points = prop.getChildNodes();
        for (int i = 0; i < points.getLength(); i++) {
            final Node point = points.item(i);

            if (point.getNodeType() == Node.ELEMENT_NODE
                    && point.getNodeName().equals(ODataConstants.ELEM_POINT)) {

                final NodeList poses = point.getChildNodes();
                for (int j = 0; j < poses.getLength(); j++) {
                    final Node pos = poses.item(j);

                    if (pos.getNodeType() == Node.ELEMENT_NODE
                            && pos.getNodeName().equals(ODataConstants.ELEM_POS)) {

                        if (geoData.length() > 0) {
                            geoData.append('|');
                        }
                        geoData.append(pos.getTextContent());
                    }
                }
            }
        }

        return new ODataPrimitiveValue.Builder().
                setType(edmType == null ? null : edmType.getSimpleType()).
                setText(geoData.length() > 0 ? geoData.toString() : prop.getTextContent()).build();
    }

    private static ODataProperty newPrimitiveProperty(final Element prop, final EdmType edmType) {
        return new ODataProperty(SerializationUtils.getSimpleName(prop), newPrimitiveValue(prop, edmType));
    }

    private static Element newPrimitiveProperty(final ODataProperty prop, final Document doc) {
        return newPrimitiveProperty(prop.getName(), prop.getPrimitiveValue(), doc);
    }

    private static Element newPrimitiveProperty(
            final String name, final ODataPrimitiveValue value, final Document doc) {

        final Element element = doc.createElement(ODataConstants.PREFIX_DATASERVICES + name);
        element.setAttribute(ODataConstants.ATTR_TYPE, value.getTypeName());

        // Geospatial types value management
        if (value.toValue() instanceof Point) {
            Element point = doc.createElement(ODataConstants.ELEM_POINT);
            element.appendChild(point);

            Element pos = doc.createElement(ODataConstants.ELEM_POS);
            point.appendChild(pos);

            pos.appendChild(doc.createTextNode(value.toString()));
        } else {
            element.setTextContent(value.toString());
        }

        return element;
    }

    private static ODataComplexValue newComplexValue(final Element prop, final EdmType edmType) {
        final ODataComplexValue value = new ODataComplexValue(edmType == null ? null : edmType.getTypeExpression());

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
        return new ODataProperty(SerializationUtils.getSimpleName(prop), newComplexValue(prop, edmType));
    }

    private static Element newComplexProperty(final ODataProperty prop, final Document doc) {
        return newComplexProperty(prop.getName(), prop.getComplexValue(), doc);
    }

    private static Element newComplexProperty(final String name, final ODataComplexValue value, final Document doc) {
        final Element element = doc.createElement(ODataConstants.PREFIX_DATASERVICES + name);
        if (value.getTypeName() != null) {
            element.setAttribute(ODataConstants.ATTR_TYPE, value.getTypeName());
        }

        for (ODataProperty field : value) {
            element.appendChild(newProperty(field, doc));
        }
        return element;
    }

    private static ODataProperty newCollectionProperty(final Element prop, final EdmType edmType) {
        final ODataCollectionValue value =
                new ODataCollectionValue(edmType == null ? null : edmType.getTypeExpression());

        final EdmType type = edmType == null ? null : new EdmType(edmType.getBaseType());
        final NodeList elements = prop.getChildNodes();

        for (int i = 0; i < elements.getLength(); i++) {
            final Element child = (Element) elements.item(i);
            if (child.getNodeType() != Node.TEXT_NODE) {
                switch (getPropertyType(child)) {
                    case COMPLEX:
                        value.add(newComplexValue(child, type));
                        break;
                    case PRIMITIVE:
                        value.add(newPrimitiveValue(child, type));
                        break;
                    default:
                    // do not add null or empty values
                }
            }
        }

        return new ODataProperty(SerializationUtils.getSimpleName(prop), value);
    }

    private static Element newCollectionProperty(final ODataProperty prop, final Document doc) {
        if (!prop.hasCollectionValue()) {
            throw new IllegalArgumentException("Invalid property value type "
                    + prop.getValue().getClass().getSimpleName());
        }

        final ODataCollectionValue value = prop.getCollectionValue();

        final Element element = doc.createElement(ODataConstants.PREFIX_DATASERVICES + prop.getName());
        if (value.getTypeName() != null) {
            element.setAttribute(ODataConstants.ATTR_TYPE, value.getTypeName());
        }

        for (ODataValue el : value) {
            if (el.isPrimitive()) {
                element.appendChild(newPrimitiveProperty(ODataConstants.ELEM_ELEMENT, el.asPrimitive(), doc));
            } else {
                element.appendChild(newComplexProperty(ODataConstants.ELEM_ELEMENT, el.asComplex(), doc));
            }
        }

        return element;
    }
}
