/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.engine.data;

import com.fasterxml.aalto.stax.InputFactoryImpl;
import com.fasterxml.aalto.stax.OutputFactoryImpl;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.msopentech.odatajclient.engine.client.ODataClient;
import com.msopentech.odatajclient.engine.data.atom.AtomDeserializer;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.data.atom.AtomFeed;
import com.msopentech.odatajclient.engine.data.json.AbstractJSONEntry;
import com.msopentech.odatajclient.engine.data.json.AbstractJSONFeed;
import com.msopentech.odatajclient.engine.data.json.JSONLinkCollection;
import com.msopentech.odatajclient.engine.data.json.JSONProperty;
import com.msopentech.odatajclient.engine.data.json.JSONServiceDocument;
import com.msopentech.odatajclient.engine.data.json.error.JSONODataError;
import com.msopentech.odatajclient.engine.data.json.error.JSONODataErrorBundle;
import com.msopentech.odatajclient.engine.data.xml.XMLLinkCollection;
import com.msopentech.odatajclient.engine.data.xml.XMLServiceDocument;
import com.msopentech.odatajclient.engine.data.xml.XMLODataError;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import com.msopentech.odatajclient.engine.utils.XMLUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class AbstractODataDeserializer extends AbstractJacksonMarshaller implements ODataDeserializer {

    private static final long serialVersionUID = -4244158979195609909L;

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractODataDeserializer.class);

    public AbstractODataDeserializer(final ODataClient client) {
        super(client);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends FeedResource> T toFeed(final InputStream input, final Class<T> reference) {
        T entry;

        if (AtomFeed.class.equals(reference)) {
            entry = (T) toAtomFeed(input);

        } else {
            entry = (T) toJSONFeed(input);
        }

        return entry;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends EntryResource> T toEntry(final InputStream input, final Class<T> reference) {
        T entry;

        if (AtomEntry.class.equals(reference)) {
            entry = (T) toAtomEntry(input);

        } else {
            entry = (T) toJSONEntry(input);
        }

        return entry;
    }

    @Override
    public Element toPropertyDOM(final InputStream input, final ODataFormat format) {
        return format == ODataFormat.XML
                ? toPropertyDOMFromXML(input)
                : toPropertyDOMFromJSON(input);
    }

    @Override
    public ServiceDocumentResource toServiceDocument(final InputStream input, final ODataFormat format) {
        return format == ODataFormat.XML
                ? toServiceDocumentFromXML(input)
                : toServiceDocumentFromJSON(input);
    }

    @Override
    public LinkCollectionResource toLinkCollection(final InputStream input, final ODataFormat format) {
        return format == ODataFormat.XML
                ? toLinkCollectionFromXML(input)
                : toLinkCollectionFromJSON(input);
    }

    @Override
    public ODataError toODataError(final InputStream input, final boolean isXML) {
        return isXML
                ? toODataErrorFromXML(input)
                : toODataErrorFromJSON(input);
    }

    @Override
    public Element toDOM(final InputStream input) {
        return XMLUtils.PARSER.parse(input);
    }

    /*
     * ------------------ Protected methods ------------------
     */
    protected XmlMapper getXmlMapper() {
        final XmlMapper xmlMapper = new XmlMapper(
                new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl()), new JacksonXmlModule());
        xmlMapper.setInjectableValues(new InjectableValues.Std().addValue(ODataClient.class, client));
        xmlMapper.addHandler(new DeserializationProblemHandler() {

            @Override
            public boolean handleUnknownProperty(final DeserializationContext ctxt, final JsonParser jp,
                    final JsonDeserializer<?> deserializer, final Object beanOrClass, final String propertyName)
                    throws IOException, JsonProcessingException {

                // skip any unknown property
                LOG.warn("Skipping unknown property {}", propertyName);
                ctxt.getParser().skipChildren();
                return true;
            }
        });
        return xmlMapper;
    }

    protected AtomFeed toAtomFeed(final InputStream input) {
        try {
            return AtomDeserializer.feed(toDOM(input));
        } catch (Exception e) {
            throw new IllegalArgumentException("While deserializing Atom feed", e);
        }
    }

    protected AtomEntry toAtomEntry(final InputStream input) {
        try {
            return AtomDeserializer.entry(toDOM(input));
        } catch (Exception e) {
            throw new IllegalArgumentException("While deserializing Atom entry", e);
        }
    }

    protected abstract AbstractJSONFeed toJSONFeed(final InputStream input);

    protected abstract AbstractJSONEntry<?> toJSONEntry(final InputStream input);

    protected Element toPropertyDOMFromXML(final InputStream input) {
        return toDOM(input);
    }

    protected Element toPropertyDOMFromJSON(final InputStream input) {
        try {
            return getObjectMapper().readValue(input, JSONProperty.class).getContent();
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON property", e);
        }
    }

    protected ServiceDocumentResource toServiceDocumentFromXML(final InputStream input) {
        final Element service = toDOM(input);

        final XMLServiceDocument serviceDoc = new XMLServiceDocument();
        serviceDoc.setBaseURI(URI.create(service.getAttribute(ODataConstants.ATTR_XMLBASE)));

        final NodeList collections = service.getElementsByTagName(ODataConstants.ELEM_COLLECTION);
        for (int i = 0; i < collections.getLength(); i++) {
            final Element collection = (Element) collections.item(i);

            final NodeList title = collection.getElementsByTagName(ODataConstants.ATOM_ATTR_TITLE);
            if (title.getLength() != 1) {
                throw new IllegalArgumentException("Invalid collection element found");
            }

            serviceDoc.addToplevelEntitySet(title.item(0).getTextContent(),
                    collection.getAttribute(ODataConstants.ATTR_HREF));
        }

        return serviceDoc;
    }

    protected ServiceDocumentResource toServiceDocumentFromJSON(final InputStream input) {
        try {
            return getObjectMapper().readValue(input, JSONServiceDocument.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON service document", e);
        }
    }

    protected XMLLinkCollection toLinkCollectionFromXML(final InputStream input) {
        final Element root = toDOM(input);

        final NodeList uris = root.getOwnerDocument().getElementsByTagName(ODataConstants.ELEM_URI);

        final List<URI> links = new ArrayList<URI>();
        for (int i = 0; i < uris.getLength(); i++) {
            links.add(URI.create(uris.item(i).getTextContent()));
        }

        final NodeList next = root.getElementsByTagName(ODataConstants.NEXT_LINK_REL);
        final XMLLinkCollection linkCollection = next.getLength() > 0
                ? new XMLLinkCollection(URI.create(next.item(0).getTextContent()))
                : new XMLLinkCollection();
        linkCollection.setLinks(links);

        return linkCollection;
    }

    protected JSONLinkCollection toLinkCollectionFromJSON(final InputStream input) {
        try {
            return getObjectMapper().readValue(input, JSONLinkCollection.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON $links", e);
        }
    }

    protected XMLODataError toODataErrorFromXML(final InputStream input) {
        try {
            final XmlMapper xmlMapper = new XmlMapper(
                    new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl()), new JacksonXmlModule());
            return xmlMapper.readValue(input, XMLODataError.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("While deserializing XML error", e);
        }
    }

    protected JSONODataError toODataErrorFromJSON(final InputStream input) {
        try {
            return getObjectMapper().readValue(input, JSONODataErrorBundle.class).getError();
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON error", e);
        }
    }
}
