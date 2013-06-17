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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ODataBinder {

    /**
     * Logger.
     */
    protected static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ODataBinder.class);

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
                final Node property = xmlContent.getChildNodes().item(i);

                try {
                    entity.addProperty(getProperty(property));
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

    private static ODataProperty getProperty(final Node property) {
        final Node typeNode = property.getAttributes().getNamedItem("m:type");

        final ODataValue value;
        final EdmSimpleType type;

        try {
            if (typeNode == null) {
                type = EdmSimpleType.STRING;
                value = new ODataPrimitiveValue(property.getTextContent(), type);
            } else {
                final EdmType edmType = new EdmType(typeNode.getTextContent());
                type = edmType.getSimpleType();

                if (edmType.isCollection()) {
                    value = new ODataCollectionValue(typeNode.getTextContent());
                    for (int k = 0; k < property.getChildNodes().getLength(); k++) {
                        Node el = property.getChildNodes().item(k);
                        ODataPrimitiveValue primitive = new ODataPrimitiveValue(el.getTextContent(), type);
                        ((ODataCollectionValue) value).add(primitive);
                    }
                } else {
                    value = new ODataPrimitiveValue(property.getTextContent(), type);
                }
            }

            return new ODataProperty(property.getLocalName(), value);
        } catch (IllegalArgumentException e) {
            LOG.warn("Failure retriving EdmSimpleType {}", typeNode.getTextContent(), e);
            throw e;
        }
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
