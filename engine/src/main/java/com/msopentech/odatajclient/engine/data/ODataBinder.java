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
import com.msopentech.odatajclient.engine.utils.URIUtils;
import com.msopentech.odatajclient.engine.utils.XMLUtils;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

    /**
     * Gets a
     * <code>FeedResource</code> from the given OData entity set.
     *
     * @param <T> feed resource type.
     * @param feed OData entity set.
     * @param reference reference class.
     * @return <code>FeedResource</code> object.
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
     * Gets an
     * <code>EntryResource</code> from the given OData entity.
     *
     * @param <T> entry resource type.
     * @param entity OData entity.
     * @param reference reference class.
     * @return <code>EntryResource</code> object.
     */
    @SuppressWarnings("unchecked")
    public static <T extends EntryResource> T getEntry(final ODataEntity entity, final Class<T> reference) {
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
            entry.setMediaContent(entity.getMediaContentSource(), entity.getMediaContentType());
        } else {
            entry.setContent(content);
        }

        for (ODataProperty prop : entity.getProperties()) {
            content.appendChild(newProperty(prop, content.getOwnerDocument()));
        }

        return entry;
    }

    /**
     * Gets content from the given OData property.
     *
     * @param prop OData property.
     * @return <code>Element</code> object.
     */
    public static Element getProperty(final ODataProperty prop) {
        try {
            return newProperty(prop, DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
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
     * Gets
     * <code>ODataServiceDocument</code> from the given service document resource.
     *
     * @param resource service document resource.
     * @return <code>ODataServiceDocument</code> object.
     */
    public static ODataServiceDocument getODataServiceDocument(final ServiceDocumentResource resource) {
        final ODataServiceDocument serviceDocument = new ODataServiceDocument();

        for (Map.Entry<String, String> entry : resource.getToplevelEntitySets().entrySet()) {
            serviceDocument.addEntitySet(entry.getKey(), URIUtils.getURI(resource.getBaseURI(), entry.getValue()));
        }

        return serviceDocument;
    }

    /**
     * Gets
     * <code>ODataEntitySet</code> from the given feed resource.
     *
     * @param resource feed resource.
     * @return <code>ODataEntitySet</code> object.
     */
    public static ODataEntitySet getODataEntitySet(final FeedResource resource) {
        return getODataEntitySet(resource, null);
    }

    /**
     * Gets
     * <code>ODataEntitySet</code> from the given feed resource.
     *
     * @param resource feed resource.
     * @param defaultBaseURI default base URI.
     * @return <code>ODataEntitySet</code> object.
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
     * Gets
     * <code>ODataEntity</code> from the given entry resource.
     *
     * @param resource entry resource.
     * @return <code>ODataEntity</code> object.
     */
    public static ODataEntity getODataEntity(final EntryResource resource) {
        return getODataEntity(resource, null);
    }

    /**
     * Gets
     * <code>ODataEntity</code> from the given entry resource.
     *
     * @param resource entry resource.
     * @param defaultBaseURI default base URI.
     * @return <code>ODataEntity</code> object.
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

        entity.setOperations(resource.getOperations());

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
            for (int i = 0; i < content.getChildNodes().getLength(); i++) {
                final Element property = (Element) content.getChildNodes().item(i);

                try {
                    if (property.getNodeType() != Node.TEXT_NODE) {
                        entity.addProperty(getProperty(property));
                    }
                } catch (IllegalArgumentException e) {
                    LOG.warn("Failure retrieving EdmType for {}", property.getTextContent(), e);
                }
            }
        }

        return entity;
    }

    /**
     * Gets a
     * <code>LinkResource</code> from the given OData link.
     *
     * @param <T> link resource type.
     * @param link OData link.
     * @param reference reference class.
     * @return <code>LinkResource</code> object.
     */
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
        } else if (link instanceof ODataInlineEntitySet) {
            // append inline feed
            final ODataEntitySet inlineFeed = ((ODataInlineEntitySet) link).getEntitySet();
            LOG.debug("Append in-line feed\n{}", inlineFeed);

            linkResource.setInlineFeed(getFeed(inlineFeed, ResourceFactory.feedClassForLink(reference)));
        }

        return linkResource;
    }

    /**
     * Gets an
     * <code>ODataProperty</code> from the given content.
     *
     * @param property content.
     * @return <code>ODataProperty</code> object.
     */
    public static ODataProperty getProperty(final Element property) {
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
                    res = ODataFactory.newPrimitiveProperty(XMLUtils.getSimpleName(property), null);
            }
        } else {
            res = ODataFactory.newPrimitiveProperty(XMLUtils.getSimpleName(property), null);
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

        element.setAttribute(ODataConstants.XMLNS_METADATA, ODataConstants.NS_METADATA);
        element.setAttribute(ODataConstants.XMLNS_DATASERVICES, ODataConstants.NS_DATASERVICES);
        element.setAttribute(ODataConstants.XMLNS_GML, ODataConstants.NS_GML);
        element.setAttribute(ODataConstants.XMLNS_GEORSS, ODataConstants.NS_GEORSS);

        return element;
    }

    private static String getPoints(final NodeList points) {
        final StringBuilder builder = new StringBuilder();

        for (int j = 0; j < points.getLength(); j++) {
            final Node point = points.item(j);

            if (point.getNodeName().equals(ODataConstants.ELEM_POINT)) {

                if (builder.length() > 0) {
                    builder.append('|');
                }

                builder.append(getPoses(point.getChildNodes()));
            }
        }

        return builder.toString();
    }

    private static String getLineStrings(final NodeList lines) {
        final StringBuilder builder = new StringBuilder();

        for (int j = 0; j < lines.getLength(); j++) {
            final Node line = lines.item(j);

            if (line.getNodeName().equals(ODataConstants.ELEM_LINESTRING)) {

                if (builder.length() > 0) {
                    builder.append(';');
                }
                builder.append(getPoses(line.getChildNodes()));
            }
        }

        return builder.toString();
    }

    private static String getPolygon(final Node polygon) {
        final StringBuilder builder = new StringBuilder();

        NodeList exterior = ((Element) polygon).getElementsByTagName(ODataConstants.ELEM_POLYGON_EXTERIOR);
        NodeList interior = ((Element) polygon).getElementsByTagName(ODataConstants.ELEM_POLYGON_INTERIOR);

        if (interior.getLength() > 0) {
            builder.append(getPoses(((Element) interior.item(0)).getElementsByTagName(ODataConstants.ELEM_POS)));
        }

        builder.append(':');

        if (exterior.getLength() > 0) {
            builder.append(getPoses(((Element) exterior.item(0)).getElementsByTagName(ODataConstants.ELEM_POS)));
        }

        return builder.toString();
    }

    private static String getPolygons(final NodeList polygons) {
        final StringBuilder builder = new StringBuilder();

        for (int j = 0; j < polygons.getLength(); j++) {
            final Node polygon = polygons.item(j);

            if (polygon.getNodeName().equals(ODataConstants.ELEM_POLYGON)) {

                if (builder.length() > 0) {
                    builder.append(';');
                }
                builder.append(getPolygon(polygon));
            }
        }

        return builder.toString();
    }

    private static String getPoses(final NodeList poses) {
        final StringBuilder builder = new StringBuilder();

        for (int j = 0; j < poses.getLength(); j++) {
            final Node pos = poses.item(j);

            if (pos.getNodeName().equals(ODataConstants.ELEM_POS)) {

                if (builder.length() > 0) {
                    builder.append('|');
                }
                builder.append(pos.getTextContent());
            }
        }

        return builder.toString();
    }

    private static String getGeoCollection(final Node collection) {
        final StringBuilder builder = new StringBuilder();

        final NodeList memberListss = collection.getChildNodes();

        for (int i = 0; i < memberListss.getLength(); i++) {
            final Node memberList = memberListss.item(i);
            if (memberList.getNodeName().equals(ODataConstants.ELEM_GEOCOLLECTION_MEMBERS)) {
                final NodeList members = memberList.getChildNodes();

                for (int j = 0; j < members.getLength(); j++) {
                    final Node member = members.item(j);
                    if (member.getNodeType() == Node.ELEMENT_NODE) {
                        if (builder.length() > 0) {
                            builder.append(',');
                        }
                        builder.append(geospatialsNodeAsString(member));
                    }
                }
            }
        }

        return builder.toString();
    }

    private static String geospatialsNodeAsString(final Node node) {
        final StringBuilder geoData = new StringBuilder();

        final String name = node.getNodeName();

        if (ODataConstants.ELEM_POINT.equals(name) || ODataConstants.ELEM_LINESTRING.equals(name)) {
            geoData.append(getPoses(node.getChildNodes()));
        } else if (ODataConstants.ELEM_MULTIPOINT.equals(name)) {
            geoData.append(getPoints(((Element) node).getElementsByTagName(ODataConstants.ELEM_POINT)));
        } else if (ODataConstants.ELEM_MULTILINESTRING.equals(name)) {
            geoData.append(getLineStrings(((Element) node).getElementsByTagName(ODataConstants.ELEM_LINESTRING)));
        } else if (ODataConstants.ELEM_POLYGON.equals(name)) {
            geoData.append(getPolygon(node));
        } else if (ODataConstants.ELEM_MULTIPOLYGON.equals(name)) {
            geoData.append(getPolygons(((Element) node).getElementsByTagName(ODataConstants.ELEM_POLYGON)));
        } else if (ODataConstants.ELEM_GEOCOLLECTION.equals(name)) {
            geoData.append(getGeoCollection(node));
        } else {
            throw new IllegalArgumentException("Invalid geospatial node " + node.getNodeName());
        }

        try {
            return name + ":" + URLEncoder.encode(geoData.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static ODataPrimitiveValue newPrimitiveValue(final Element prop, final EdmType edmType) {
        // Geospatial types value management
        final StringBuilder geoData = new StringBuilder();
        final NodeList points = prop.getChildNodes();

        for (int i = 0; i < points.getLength(); i++) {
            final Node point = points.item(i);

            try {
                geoData.append(geospatialsNodeAsString(point));
            } catch (IllegalArgumentException ignore) {
                //ignore: is not a geospatial data
            }
        }

        return new ODataPrimitiveValue.Builder().
                setType(edmType == null ? null : edmType.getSimpleType()).
                setText(geoData.length() > 0 ? geoData.toString() : prop.getTextContent()).build();
    }

    private static ODataProperty newPrimitiveProperty(final Element prop, final EdmType edmType) {
        return ODataFactory.newPrimitiveProperty(XMLUtils.getSimpleName(prop), newPrimitiveValue(prop, edmType));
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
            final Element point = doc.createElement(ODataConstants.ELEM_POINT);
            element.appendChild(point);

            final Element pos = doc.createElement(ODataConstants.ELEM_POS);
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
                value.add(getProperty(child));
            }
        }

        return value;
    }

    private static ODataProperty newComplexProperty(final Element prop, final EdmType edmType) {
        return ODataFactory.newComplexProperty(XMLUtils.getSimpleName(prop), newComplexValue(prop, edmType));
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

        return ODataFactory.newCollectionProperty(XMLUtils.getSimpleName(prop), value);
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
