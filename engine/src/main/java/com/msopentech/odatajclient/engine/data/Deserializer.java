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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.msopentech.odatajclient.engine.data.atom.AtomDeserializer;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.data.atom.AtomFeed;
import com.msopentech.odatajclient.engine.data.json.JSONEntry;
import com.msopentech.odatajclient.engine.data.json.JSONFeed;
import com.msopentech.odatajclient.engine.data.json.JSONLinkCollection;
import com.msopentech.odatajclient.engine.data.json.JSONProperty;
import com.msopentech.odatajclient.engine.data.json.JSONServiceDocument;
import com.msopentech.odatajclient.engine.data.json.error.JSONODataError;
import com.msopentech.odatajclient.engine.data.json.error.JSONODataErrorBundle;
import com.msopentech.odatajclient.engine.data.metadata.edm.AbstractAnnotatedEdm;
import com.msopentech.odatajclient.engine.data.metadata.edm.AbstractAnnotatedEdmUtils;
import com.msopentech.odatajclient.engine.data.metadata.edm.Edmx;
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
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Utility class for serialization.
 */
public final class Deserializer {

    private Deserializer() {
        // Empty private constructor for static utility classes
    }

    public static Edmx toMetadata(final InputStream input) {
        try {
            final XmlMapper xmlMapper = new XmlMapper(
                    new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl()), new JacksonXmlModule());
            xmlMapper.addHandler(new DeserializationProblemHandler() {

                @Override
                public boolean handleUnknownProperty(final DeserializationContext ctxt, final JsonParser jp,
                        final JsonDeserializer<?> deserializer, final Object beanOrClass, final String propertyName)
                        throws IOException, JsonProcessingException {

                    // 1. special handling of AbstractAnnotatedEdm's fields
                    if (beanOrClass instanceof AbstractAnnotatedEdm
                            && AbstractAnnotatedEdmUtils.isAbstractAnnotatedProperty(propertyName)) {

                        AbstractAnnotatedEdmUtils.parseAnnotatedEdm((AbstractAnnotatedEdm) beanOrClass, jp);
                    } // 2. skip any other unknown property
                    else {
                        ctxt.getParser().skipChildren();
                    }

                    return true;
                }
            });
            return xmlMapper.readValue(input, Edmx.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Could not parse as Edmx document", e);
        }
    }

    /**
     * Gets a feed object from the given InputStream.
     *
     * @param <T> reference class type
     * @param input stream to be de-serialized.
     * @param reference reference class (AtomFeed.class, JSONFeed.class).
     * @return FeedResource instance.
     */
    @SuppressWarnings("unchecked")
    public static <T extends FeedResource> T toFeed(final InputStream input, final Class<T> reference) {
        T entry;

        if (AtomFeed.class.equals(reference)) {
            entry = (T) toAtomFeed(input);

        } else {
            entry = (T) toJSONFeed(input);
        }

        return entry;
    }

    /**
     * Gets an entry object from the given InputStream.
     *
     * @param <T> reference class type
     * @param input stream to be de-serialized.
     * @param reference reference class (AtomEntry.class, JSONEntry.class).
     * @return EntryResource instance.
     */
    @SuppressWarnings("unchecked")
    public static <T extends EntryResource> T toEntry(final InputStream input, final Class<T> reference) {
        T entry;

        if (AtomEntry.class.equals(reference)) {
            entry = (T) toAtomEntry(input);

        } else {
            entry = (T) toJSONEntry(input);
        }

        return entry;
    }

    /**
     * Gets a DOM representation of the given InputStream.
     *
     * @param input stream to be de-serialized.
     * @param format OData format.
     * @return DOM.
     */
    public static Element toPropertyDOM(final InputStream input, final ODataFormat format) {
        return format == ODataFormat.XML
                ? toPropertyDOMFromXML(input)
                : toPropertyDOMFromJSON(input);
    }

    /**
     * Gets the ServiceDocumentResource object represented by the given InputStream.
     *
     * @param input stream to be de-serialized.
     * @param format OData service document format.
     * @return ServiceDocumentResource object.
     */
    public static ServiceDocumentResource toServiceDocument(final InputStream input, final ODataFormat format) {
        return format == ODataFormat.XML
                ? toServiceDocumentFromXML(input)
                : toServiceDocumentFromJSON(input);
    }

    /**
     * Gets a list of links from the given InputStream.
     *
     * @param input stream to be de-serialized.
     * @param format OData format.
     * @return de-serialized links.
     */
    public static LinkCollectionResource toLinkCollection(final InputStream input, final ODataFormat format) {
        return format == ODataFormat.XML
                ? toLinkCollectionFromXML(input)
                : toLinkCollectionFromJSON(input);
    }

    /**
     * Gets the ODataError object represented by the given InputStream.
     *
     * @param input stream to be parsed and de-serialized.
     * @param isXML 'TRUE' if the error is represented by XML; 'FALSE' otherwise.
     * @return
     */
    public static ODataError toODataError(final InputStream input, final boolean isXML) {
        return isXML
                ? toODataErrorFromXML(input)
                : toODataErrorFromJSON(input);
    }

    /**
     * Parses the given input into a DOM tree.
     *
     * @param input stream to be parsed and de-serialized.
     * @return DOM tree
     */
    public static Element toDOM(final InputStream input) {
        return XMLUtils.PARSER.parse(input);
    }

    /*
     * ------------------ Private methods ------------------
     */
    @SuppressWarnings(
            "unchecked")
    private static AtomFeed toAtomFeed(final InputStream input) {
        try {
            return AtomDeserializer.feed(toDOM(input));
        } catch (Exception e) {
            throw new IllegalArgumentException("While deserializing Atom feed", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static AtomEntry toAtomEntry(final InputStream input) {
        try {
            return AtomDeserializer.entry(toDOM(input));
        } catch (Exception e) {
            throw new IllegalArgumentException("While deserializing Atom entry", e);
        }
    }

    private static JSONFeed toJSONFeed(final InputStream input) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

            return mapper.readValue(input, JSONFeed.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON feed", e);
        }
    }

    private static JSONEntry toJSONEntry(final InputStream input) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

            return mapper.readValue(input, JSONEntry.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON entry", e);
        }
    }

    private static Element toPropertyDOMFromXML(final InputStream input) {
        return toDOM(input);
    }

    private static Element toPropertyDOMFromJSON(final InputStream input) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

            return mapper.readValue(input, JSONProperty.class).getContent();
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON property", e);
        }
    }

    private static ServiceDocumentResource toServiceDocumentFromXML(final InputStream input) {
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

    private static ServiceDocumentResource toServiceDocumentFromJSON(final InputStream input) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

            return mapper.readValue(input, JSONServiceDocument.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON service document", e);
        }
    }

    private static XMLLinkCollection toLinkCollectionFromXML(final InputStream input) {
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

    private static JSONLinkCollection toLinkCollectionFromJSON(final InputStream input) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

            return mapper.readValue(input, JSONLinkCollection.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON $links", e);
        }
    }

    private static XMLODataError toODataErrorFromXML(final InputStream input) {
        try {
            final XmlMapper xmlMapper = new XmlMapper(
                    new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl()), new JacksonXmlModule());
            return xmlMapper.readValue(input, XMLODataError.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("While deserializing XML error", e);
        }
    }

    private static JSONODataError toODataErrorFromJSON(final InputStream input) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

            return mapper.readValue(input, JSONODataErrorBundle.class).getError();
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON error", e);
        }
    }
}
