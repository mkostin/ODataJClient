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
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import com.msopentech.odatajclient.engine.utils.URIUtils;
import com.msopentech.odatajclient.engine.utils.XMLUtils;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class ODataBinder {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ODataBinder.class);

    private ODataBinder() {
        // Empty private constructor for static utility classes
    }

    private static Element newEntryContent() {
        Element properties = null;
        try {
            final DocumentBuilder builder = ODataConstants.DOC_BUILDER_FACTORY.newDocumentBuilder();
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

    /**
     * Gets a <tt>FeedResource</tt> from the given OData entity set.
     *
     * @param <T> feed resource type.
     * @param feed OData entity set.
     * @param reference reference class.
     * @return <tt>FeedResource</tt> object.
     */
    public static <T extends FeedResource> T getFeed(final ODataEntitySet feed, final Class<T> reference) {
        final T feedResource = ResourceFactory.newFeed(reference);

        final List<EntryResource> entries = new ArrayList<EntryResource>();
        feedResource.setEntries(entries);

        final URI next = feed.getNext();
        if (next != null) {
            feedResource.setNext(next);
        }

        for (ODataEntity entity : feed.getEntities()) {
            entries.add(getEntry(entity, ResourceFactory.entryClassForFeed(reference)));
        }

        feedResource.setEntries(entries);

        return feedResource;
    }

    /**
     * Gets an <tt>EntryResource</tt> from the given OData entity.
     *
     * @param <T> entry resource type.
     * @param entity OData entity.
     * @param reference reference class.
     * @return <tt>EntryResource</tt> object.
     */
    public static <T extends EntryResource> T getEntry(final ODataEntity entity, final Class<T> reference) {
        return getEntry(entity, reference, true);
    }

    /**
     * Gets an <tt>EntryResource</tt> from the given OData entity.
     *
     * @param <T> entry resource type.
     * @param entity OData entity.
     * @param reference reference class.
     * @param setType whether to explicitly output type information.
     * @return <tt>EntryResource</tt> object.
     */
    @SuppressWarnings("unchecked")
    public static <T extends EntryResource> T getEntry(final ODataEntity entity, final Class<T> reference,
            final boolean setType) {

        final T entry = ResourceFactory.newEntry(reference);
        entry.setType(entity.getName());

        // -------------------------------------------------------------
        // Add edit and self link
        // -------------------------------------------------------------
        final URI editLink = entity.getEditLink();
        if (editLink != null) {
            final LinkResource entryEditLink = ResourceFactory.newLinkForEntry(reference);
            entryEditLink.setTitle(entity.getName());
            entryEditLink.setHref(editLink.toASCIIString());
            entryEditLink.setRel(ODataConstants.EDIT_LINK_REL);
            entry.setEditLink(entryEditLink);
        }

        if (entity.isReadOnly()) {
            final LinkResource entrySelfLink = ResourceFactory.newLinkForEntry(reference);
            entrySelfLink.setTitle(entity.getName());
            entrySelfLink.setHref(entity.getLink().toASCIIString());
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
            entry.setMediaContentSource(entity.getMediaContentSource());
            entry.setMediaContentType(entity.getMediaContentType());
        } else {
            entry.setContent(content);
        }

        for (ODataProperty prop : entity.getProperties()) {
            content.appendChild(toDOMElement(prop, content.getOwnerDocument(), setType));
        }

        return entry;
    }

    /**
     * Gets the given OData property as DOM element.
     *
     * @param prop OData property.
     * @return <tt>Element</tt> object.
     */
    public static Element toDOMElement(final ODataProperty prop) {
        try {
            return toDOMElement(prop, ODataConstants.DOC_BUILDER_FACTORY.newDocumentBuilder().newDocument(), true);
        } catch (ParserConfigurationException e) {
            LOG.error("Error retrieving property DOM", e);
            throw new IllegalArgumentException(e);
        }
    }

    public static ODataLinkCollection getLinkCollection(final LinkCollectionResource linkCollection) {
        final ODataLinkCollection collection = new ODataLinkCollection(linkCollection.getNext());
        collection.setLinks(linkCollection.getLinks());
        return collection;
    }

    /**
     * Gets <tt>ODataServiceDocument</tt> from the given service document resource.
     *
     * @param resource service document resource.
     * @return <tt>ODataServiceDocument</tt> object.
     */
    public static ODataServiceDocument getODataServiceDocument(final ServiceDocumentResource resource) {
        final ODataServiceDocument serviceDocument = new ODataServiceDocument();

        for (Map.Entry<String, String> entry : resource.getToplevelEntitySets().entrySet()) {
            serviceDocument.addEntitySet(entry.getKey(), URIUtils.getURI(resource.getBaseURI(), entry.getValue()));
        }

        return serviceDocument;
    }

    /**
     * Gets <tt>ODataEntitySet</tt> from the given feed resource.
     *
     * @param resource feed resource.
     * @return <tt>ODataEntitySet</tt> object.
     */
    public static ODataEntitySet getODataEntitySet(final FeedResource resource) {
        return getODataEntitySet(resource, null);
    }

    /**
     * Gets <tt>ODataEntitySet</tt> from the given feed resource.
     *
     * @param resource feed resource.
     * @param defaultBaseURI default base URI.
     * @return <tt>ODataEntitySet</tt> object.
     */
    public static ODataEntitySet getODataEntitySet(final FeedResource resource, final URI defaultBaseURI) {
        if (LOG.isDebugEnabled()) {
            final StringWriter writer = new StringWriter();
            Serializer.feed(resource, writer);
            writer.flush();
            LOG.debug("FeedResource -> ODataEntitySet:\n{}", writer.toString());
        }

        final URI base = defaultBaseURI == null ? resource.getBaseURI() : defaultBaseURI;

        final URI next = resource.getNext();

        final ODataEntitySet entitySet = next == null
                ? ODataFactory.newEntitySet()
                : ODataFactory.newEntitySet(URIUtils.getURI(base, next.toASCIIString()));

        if (resource.getCount() != null) {
            entitySet.setCount(resource.getCount());
        }

        for (EntryResource entryResource : resource.getEntries()) {
            entitySet.addEntity(getODataEntity(entryResource));
        }

        return entitySet;
    }

    /**
     * Gets <tt>ODataEntity</tt> from the given entry resource.
     *
     * @param resource entry resource.
     * @return <tt>ODataEntity</tt> object.
     */
    public static ODataEntity getODataEntity(final EntryResource resource) {
        return getODataEntity(resource, null);
    }

    /**
     * Gets <tt>ODataEntity</tt> from the given entry resource.
     *
     * @param resource entry resource.
     * @param defaultBaseURI default base URI.
     * @return <tt>ODataEntity</tt> object.
     */
    public static ODataEntity getODataEntity(final EntryResource resource, final URI defaultBaseURI) {
        if (LOG.isDebugEnabled()) {
            final StringWriter writer = new StringWriter();
            Serializer.entry(resource, writer);
            writer.flush();
            LOG.debug("EntryResource -> ODataEntity:\n{}", writer.toString());
        }

        final URI base = defaultBaseURI == null ? resource.getBaseURI() : defaultBaseURI;

        final ODataEntity entity = resource.getSelfLink() == null
                ? ODataFactory.newEntity(resource.getType())
                : ODataFactory.newEntity(resource.getType(), URIUtils.getURI(base, resource.getSelfLink().getHref()));

        if (StringUtils.isNotBlank(resource.getETag())) {
            entity.setETag(resource.getETag());
        }

        if (resource.getEditLink() != null) {
            entity.setEditLink(URIUtils.getURI(base, resource.getEditLink().getHref()));
        }

        for (LinkResource link : resource.getAssociationLinks()) {
            entity.addLink(ODataFactory.newAssociationLink(link.getTitle(), base, link.getHref()));
        }

        for (LinkResource link : resource.getNavigationLinks()) {
            final EntryResource inlineEntry = link.getInlineEntry();
            final FeedResource inlineFeed = link.getInlineFeed();

            if (inlineEntry == null && inlineFeed == null) {
                entity.addLink(ODataFactory.newEntityNavigationLink(link.getTitle(), base, link.getHref()));
            } else if (inlineFeed == null) {
                entity.addLink(ODataFactory.newInlineEntity(
                        link.getTitle(), base, link.getHref(),
                        getODataEntity(inlineEntry, inlineEntry.getBaseURI() == null ? base : inlineEntry.getBaseURI())));
            } else {
                entity.addLink(ODataFactory.newInlineEntitySet(
                        link.getTitle(), base, link.getHref(),
                        getODataEntitySet(inlineFeed, inlineFeed.getBaseURI() == null ? base : inlineFeed.getBaseURI())));
            }
        }

        for (LinkResource link : resource.getMediaEditLinks()) {
            entity.addLink(ODataFactory.newMediaEditLink(link.getTitle(), base, link.getHref()));
        }

        for (ODataOperation operation : resource.getOperations()) {
            operation.setTarget(URIUtils.getURI(base, operation.getTarget()));
            entity.addOperation(operation);
        }

        final Element content;
        if (resource.isMediaEntry()) {
            entity.setMediaEntity(true);
            entity.setMediaContentSource(resource.getMediaContentSource());
            entity.setMediaContentType(resource.getMediaContentType());
            content = resource.getMediaEntryProperties();
        } else {
            content = resource.getContent();
        }
        if (content != null) {
            for (Node property : XMLUtils.getChildNodes(content, Node.ELEMENT_NODE)) {
                try {
                    entity.addProperty(getProperty((Element) property));
                } catch (IllegalArgumentException e) {
                    LOG.warn("Failure retrieving EdmType for {}", property.getTextContent(), e);
                }
            }
        }

        return entity;
    }

    /**
     * Gets a <tt>LinkResource</tt> from the given OData link.
     *
     * @param <T> link resource type.
     * @param link OData link.
     * @param reference reference class.
     * @return <tt>LinkResource</tt> object.
     */
    @SuppressWarnings("unchecked")
    public static <T extends LinkResource> T getLinkResource(final ODataLink link, final Class<T> reference) {
        final T linkResource = ResourceFactory.newLink(reference);
        linkResource.setRel(link.getRel());
        linkResource.setTitle(link.getName());
        linkResource.setHref(link.getLink() == null ? null : link.getLink().toASCIIString());
        linkResource.setType(link.getType().toString());

        if (link instanceof ODataInlineEntity) {
            // append inline entity
            final ODataEntity inlineEntity = ((ODataInlineEntity) link).getEntity();
            LOG.debug("Append in-line entity\n{}", inlineEntity);

            linkResource.setInlineEntry(getEntry(inlineEntity, ResourceFactory.entryClassForLink(reference)));
        } else if (link instanceof ODataInlineEntitySet) {
            // append inline feed
            final ODataEntitySet inlineFeed = ((ODataInlineEntitySet) link).getEntitySet();
            LOG.debug("Append in-line feed\n{}", inlineFeed);

            linkResource.setInlineFeed(getFeed(inlineFeed, ResourceFactory.feedClassForLink(reference)));
        }

        return linkResource;
    }

    /**
     * Gets an <tt>ODataProperty</tt> from the given DOM element.
     *
     * @param property content.
     * @return <tt>ODataProperty</tt> object.
     */
    public static ODataProperty getProperty(final Element property) {
        final ODataProperty res;

        final Node nullNode = property.getAttributes().getNamedItem(ODataConstants.ATTR_NULL);

        if (nullNode == null) {
            final EdmType edmType = StringUtils.isBlank(property.getAttribute(ODataConstants.ATTR_M_TYPE))
                    ? null
                    : new EdmType(property.getAttribute(ODataConstants.ATTR_M_TYPE));

            final PropertyType propType = edmType == null
                    ? guessPropertyType(property)
                    : edmType.isCollection()
                    ? PropertyType.COLLECTION
                    : edmType.isSimpleType()
                    ? PropertyType.PRIMITIVE
                    : PropertyType.COMPLEX;

            switch (propType) {
                case COLLECTION:
                    res = fromCollectionPropertyElement(property, edmType);
                    break;

                case COMPLEX:
                    res = fromComplexPropertyElement(property, edmType);
                    break;

                case PRIMITIVE:
                    res = fromPrimitivePropertyElement(property, edmType);
                    break;

                case EMPTY:
                default:
                    res = ODataFactory.newPrimitiveProperty(XMLUtils.getSimpleName(property), null);
            }
        } else {
            res = ODataFactory.newPrimitiveProperty(XMLUtils.getSimpleName(property), null);
        }

        return res;
    }

    private static PropertyType guessPropertyType(final Element property) {
        PropertyType res = null;

        if (property.hasChildNodes()) {
            final NodeList children = property.getChildNodes();

            for (int i = 0; res == null && i < children.getLength(); i++) {
                final Node child = children.item(i);

                if (child.getNodeType() == Node.ELEMENT_NODE
                        && !child.getNodeName().startsWith(ODataConstants.PREFIX_GML)) {

                    res = ODataConstants.ELEM_ELEMENT.equals(XMLUtils.getSimpleName(child))
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

    private static Element toDOMElement(final ODataProperty prop, final Document doc, final boolean setType) {
        final Element element;

        if (prop.hasNullValue()) {
            // null property handling
            element = toNullPropertyElement(prop, doc);
        } else if (prop.hasPrimitiveValue()) {
            // primitive property handling
            element = toPrimitivePropertyElement(prop, doc, setType);
        } else if (prop.hasCollectionValue()) {
            // collection property handling
            element = toCollectionPropertyElement(prop, doc, setType);
        } else {
            // complex property handling
            element = toComplexPropertyElement(prop, doc, setType);
        }

        element.setAttribute(ODataConstants.XMLNS_METADATA, ODataConstants.NS_METADATA);
        element.setAttribute(ODataConstants.XMLNS_DATASERVICES, ODataConstants.NS_DATASERVICES);
        element.setAttribute(ODataConstants.XMLNS_GML, ODataConstants.NS_GML);
        element.setAttribute(ODataConstants.XMLNS_GEORSS, ODataConstants.NS_GEORSS);

        return element;
    }

    private static Element toNullPropertyElement(final ODataProperty prop, final Document doc) {
        final Element element = doc.createElement(ODataConstants.PREFIX_DATASERVICES + prop.getName());
        element.setAttribute(ODataConstants.ATTR_NULL, Boolean.toString(true));
        return element;
    }

    private static Element toPrimitivePropertyElement(
            final ODataProperty prop, final Document doc, final boolean setType) {

        return toPrimitivePropertyElement(prop.getName(), prop.getPrimitiveValue(), doc, setType);
    }

    private static Element toPrimitivePropertyElement(
            final String name, final ODataPrimitiveValue value, final Document doc, final boolean setType) {

        final Element element = doc.createElement(ODataConstants.PREFIX_DATASERVICES + name);
        if (setType) {
            element.setAttribute(ODataConstants.ATTR_M_TYPE, value.getTypeName());
        }

        if (value instanceof ODataGeospatialValue) {
            element.appendChild(doc.importNode(((ODataGeospatialValue) value).toTree(), true));
        } else {
            element.setTextContent(value.toString());
        }

        return element;
    }

    private static Element toCollectionPropertyElement(
            final ODataProperty prop, final Document doc, final boolean setType) {

        if (!prop.hasCollectionValue()) {
            throw new IllegalArgumentException("Invalid property value type "
                    + prop.getValue().getClass().getSimpleName());
        }

        final ODataCollectionValue value = prop.getCollectionValue();

        final Element element = doc.createElement(ODataConstants.PREFIX_DATASERVICES + prop.getName());
        if (value.getTypeName() != null && setType) {
            element.setAttribute(ODataConstants.ATTR_M_TYPE, value.getTypeName());
        }

        for (ODataValue el : value) {
            if (el.isPrimitive()) {
                element.appendChild(
                        toPrimitivePropertyElement(ODataConstants.ELEM_ELEMENT, el.asPrimitive(), doc, setType));
            } else {
                element.appendChild(
                        toComplexPropertyElement(ODataConstants.ELEM_ELEMENT, el.asComplex(), doc, setType));
            }
        }

        return element;
    }

    private static Element toComplexPropertyElement(
            final ODataProperty prop, final Document doc, final boolean setType) {

        return toComplexPropertyElement(prop.getName(), prop.getComplexValue(), doc, setType);
    }

    private static Element toComplexPropertyElement(
            final String name, final ODataComplexValue value, final Document doc, final boolean setType) {

        final Element element = doc.createElement(ODataConstants.PREFIX_DATASERVICES + name);
        if (value.getTypeName() != null && setType) {
            element.setAttribute(ODataConstants.ATTR_M_TYPE, value.getTypeName());
        }

        for (ODataProperty field : value) {
            element.appendChild(toDOMElement(field, doc, true));
        }
        return element;
    }

    private static ODataPrimitiveValue fromPrimitiveValueElement(final Element prop, final EdmType edmType) {
        final ODataPrimitiveValue value;
        if (edmType != null && edmType.getSimpleType().isGeospatial()) {
            final Element geoProp = ODataConstants.PREFIX_GML.equals(prop.getPrefix())
                    ? prop : (Element) XMLUtils.getChildNodes(prop, Node.ELEMENT_NODE).get(0);
            value = new ODataGeospatialValue.Builder().
                    setType(edmType.getSimpleType()).setTree(geoProp).build();
        } else {
            value = new ODataPrimitiveValue.Builder().
                    setType(edmType == null ? null : edmType.getSimpleType()).setText(prop.getTextContent()).build();
        }
        return value;
    }

    private static ODataProperty fromPrimitivePropertyElement(final Element prop, final EdmType edmType) {
        return ODataFactory.newPrimitiveProperty(
                XMLUtils.getSimpleName(prop), fromPrimitiveValueElement(prop, edmType));
    }

    private static ODataComplexValue fromComplexValueElement(final Element prop, final EdmType edmType) {
        final ODataComplexValue value = new ODataComplexValue(edmType == null ? null : edmType.getTypeExpression());

        for (Node child : XMLUtils.getChildNodes(prop, Node.ELEMENT_NODE)) {
            value.add(getProperty((Element) child));
        }

        return value;
    }

    private static ODataProperty fromComplexPropertyElement(final Element prop, final EdmType edmType) {
        return ODataFactory.newComplexProperty(XMLUtils.getSimpleName(prop), fromComplexValueElement(prop, edmType));
    }

    private static ODataProperty fromCollectionPropertyElement(final Element prop, final EdmType edmType) {
        final ODataCollectionValue value =
                new ODataCollectionValue(edmType == null ? null : edmType.getTypeExpression());

        final EdmType type = edmType == null ? null : new EdmType(edmType.getBaseType());
        final NodeList elements = prop.getChildNodes();

        for (int i = 0; i < elements.getLength(); i++) {
            final Element child = (Element) elements.item(i);
            if (child.getNodeType() != Node.TEXT_NODE) {
                switch (guessPropertyType(child)) {
                    case COMPLEX:
                        value.add(fromComplexValueElement(child, type));
                        break;
                    case PRIMITIVE:
                        value.add(fromPrimitiveValueElement(child, type));
                        break;
                    default:
                    // do not add null or empty values
                }
            }
        }

        return ODataFactory.newCollectionProperty(XMLUtils.getSimpleName(prop), value);
    }
}
