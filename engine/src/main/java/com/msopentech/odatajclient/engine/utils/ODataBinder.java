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

import com.msopentech.odatajclient.engine.data.ODataCollectionValue;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntityAtomExtensions;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataLink.LinkType;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.atom.AtomContent;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.data.atom.Link;
import com.msopentech.odatajclient.engine.data.metadata.EdmType;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import java.io.OutputStream;
import java.net.URI;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ODataBinder {

    /**
     * Logger.
     */
    protected static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ODataBinder.class);

    public static AtomEntry getAtomEntry(final ODataEntity entity) {
        final AtomEntry entry = new AtomEntry();

        final AtomContent content = newAtomContent();
        entry.getValues().add(content);

        final Element properties = content.getXMLContent();

        for (ODataProperty prop : entity.getProperties()) {
            properties.appendChild(newProperty(prop, properties.getOwnerDocument()));
        }

        return entry;
    }

    public static ODataEntity getODataEntity(final AtomEntry entry) {
        if (LOG.isDebugEnabled()) {
            getAtomSerialization(entry, AtomEntry.class, System.out);
        }

        final ODataEntity entity = EntityFactory.newEntity(entry.getCategory().getTerm());

        final ODataEntityAtomExtensions ext = new ODataEntityAtomExtensions();
        ext.setId(entry.getId().getContent());
        ext.setSummary(entry.getSummary());
        ext.setAuthor(entry.getAuthor());
        entity.setAtomExtensions(ext);

        for (Link link : entry.getLinks()) {
            addLink(link, entry, entity);
        }

        final AtomContent content = entry.getAtomContent();

        if (content != null) {
            final Element xmlContent = content.getXMLContent();
            for (int i = 0; i < xmlContent.getChildNodes().getLength(); i++) {
                final Element property = (Element) xmlContent.getChildNodes().item(i);

                try {
                    if (property.getNodeType() != Node.TEXT_NODE) {
                        entity.addProperty(newProperty(property));
                    }
                } catch (IllegalArgumentException e) {
                    LOG.warn("Failure retriving EdmType for {}", property.getTextContent(), e);
                }
            }
        }

        return entity;

    }

    public static ODataLink getODataLink(final Link link) {
        return getODataLink(link, null);
    }

    public static ODataLink getODataLink(final Link link, final AtomEntry entry) {
        return EntityFactory.newLink(
                link.getTitle(),
                entry == null ? getURI(link, null) : getURI(link, entry.getBase()),
                LinkType.evaluate(link.getRel(), link.getType()));
    }

    private static AtomContent newAtomContent() {
        final AtomContent content = new AtomContent();
        content.getType().add("application/xml");

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.newDocument();
            final Element properties = doc.createElement("m:properties");
            properties.setAttribute("xmlns:m", ODataContants.M_NS);
            properties.setAttribute("xmlns:d", ODataContants.D_NS);
            properties.setAttribute("xmlns:gml", ODataContants.GML_NS);
            properties.setAttribute("xmlns:georss", ODataContants.GEORSS_NS);
            doc.appendChild(properties);

            content.getValues().add(properties);

        } catch (ParserConfigurationException e) {
            LOG.error("Failur building AtomEntry content", e);
        }

        return content;
    }

    private static ODataProperty newProperty(final Element property) {
        final Node typeNode = property.getAttributes().getNamedItem("m:type");

        try {
            final ODataProperty res;

            if (typeNode == null) {
                final Node nullNode = property.getAttributes().getNamedItem("m:null");
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
        final Element element = doc.createElement("d:" + prop.getName());
        element.setAttribute("m:null", "true");
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

        final Element element = doc.createElement("d:" + name);
        element.setAttribute("m:type", value.getTypeName());
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

        final Element element = doc.createElement("d:" + name);
        element.setAttribute("m:type", value.getTypeName());

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

        final Element element = doc.createElement("d:" + prop.getName());
        element.setAttribute("m:type", value.getTypeName());

        for (ODataValue el : value) {
            if (el instanceof ODataPrimitiveValue) {
                element.appendChild(newPrimitiveProperty("element", el, doc));
            } else {
                element.appendChild(newComplexProperty("element", el, doc));
            }
        }

        return element;
    }

    private static void addLink(final Link link, final AtomEntry entry, final ODataEntity entity) {
        if ("edit".equals(link.getRel())) {
            addEditLink(link, entry, entity);
        } else if ("self".equals(link.getRel())) {
            addSelfLink(link, entry, entity);
        } else {
            entity.addLink(getODataLink(link, entry));
        }
    }

    private static void addEditLink(final Link link, final AtomEntry entry, final ODataEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Provided OData entity is null");
        }

        if (link == null) {
            throw new IllegalArgumentException("Provided link is null");
        }
        try {
            URI uri = getURI(link, entry.getBase());
            entity.setEditLink(uri);
        } catch (Exception e) {
            LOG.warn("Unparsable link", e);
        }
    }

    private static void addSelfLink(final Link link, final AtomEntry entry, final ODataEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Provided OData entity is null");
        }

        if (link == null) {
            throw new IllegalArgumentException("Provided link is null");
        }
        try {
            URI uri = getURI(link, entry.getBase());
            entity.setLink(uri);
        } catch (Exception e) {
            LOG.warn("Unparsable link", e);
        }
    }

    private static URI getURI(final Link link, final String base) {
        if (link == null) {
            throw new IllegalArgumentException("Null link provided");
        }

        URI uri = URI.create(link.getHref());

        if (!uri.isAbsolute()) {
            final String prefix = link.getBase() == null ? base : link.getBase();
            if (StringUtils.isNotBlank(prefix)) {
                uri = URI.create(prefix + "/" + link.getHref());
            }
        }

        return uri.normalize();
    }

    public static void getAtomSerialization(final Object obj, final Class<?> reference, final OutputStream os) {
        try {
            JAXBContext context = JAXBContext.newInstance(reference);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(obj, os);
        } catch (JAXBException e) {
            LOG.error("Failure serializing object", e);
        }
    }
}
