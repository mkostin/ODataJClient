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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.data.atom.AtomFeed;
import com.msopentech.odatajclient.engine.data.json.JSONEntry;
import com.msopentech.odatajclient.engine.data.json.JSONFeed;
import com.msopentech.odatajclient.engine.data.json.JSONLinkCollection;
import com.msopentech.odatajclient.engine.data.json.JSONProperty;
import com.msopentech.odatajclient.engine.data.json.JSONServiceDocument;
import com.msopentech.odatajclient.engine.data.json.error.JSONODataError;
import com.msopentech.odatajclient.engine.data.json.error.JSONODataErrorBundle;
import com.msopentech.odatajclient.engine.data.metadata.edmx.Edmx;
import com.msopentech.odatajclient.engine.data.xml.XMLLinkCollection;
import com.msopentech.odatajclient.engine.data.xml.XMLServiceDocument;
import com.msopentech.odatajclient.engine.data.xml.error.XMLODataError;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import com.msopentech.odatajclient.engine.format.ODataServiceDocumentFormat;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utility class for serialization.
 */
public final class Deserializer {

    /**
     * Factory for StAX de-serialization.
     */
    private static final XMLInputFactory XMLIF = XMLInputFactory.newInstance();

    private Deserializer() {
        // Empty private constructor for static utility classes
    }

    public static Edmx toMetadata(final InputStream input) {
        try {
            final XMLStreamReader xmler = XMLIF.createXMLStreamReader(input);
            final JAXBContext context = JAXBContext.newInstance(Edmx.class);

            return context.createUnmarshaller().unmarshal(xmler, Edmx.class).getValue();
        } catch (Exception e) {
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
     * Gets the AtomFeed object represented by the given XML node.
     *
     * @param node XML node representing an Atom feed.
     * @return AtomFeed object.
     */
    @SuppressWarnings("unchecked")
    public static AtomFeed toAtomFeed(final Node node) {
        try {
            final JAXBContext context = JAXBContext.newInstance(AtomFeed.class);
            return ((JAXBElement<AtomFeed>) context.createUnmarshaller().unmarshal(node)).getValue();
        } catch (Exception e) {
            throw new IllegalArgumentException("While deserializing Atom feed", e);
        }
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
     * Gets the AtomEntry object represented by the given XML node.
     *
     * @param node XML node representing an Atom entry.
     * @return AtomEntry object.
     */
    @SuppressWarnings("unchecked")
    public static AtomEntry toAtomEntry(final Node node) {
        try {
            final JAXBContext context = JAXBContext.newInstance(AtomEntry.class);
            return ((JAXBElement<AtomEntry>) context.createUnmarshaller().unmarshal(node)).getValue();
        } catch (Exception e) {
            throw new IllegalArgumentException("While deserializing Atom entry", e);
        }
    }

    /**
     * Gets a DOM representation of the given InputStream.
     *
     * @param input stream to be de-serialized.
     * @param format OData format.
     * @return DOM.
     */
    public static Element toDOM(final InputStream input, final ODataFormat format) {
        return format == ODataFormat.XML
                ? toDOMFromXML(input)
                : toDOMFromJSON(input);
    }

    /**
     * Gets the ServiceDocumentResource object represented by the given InputStream.
     *
     * @param input stream to be de-serialized.
     * @param format OData service document format.
     * @return ServiceDocumentResource object.
     */
    public static ServiceDocumentResource toServiceDocument(
            final InputStream input, final ODataServiceDocumentFormat format) {

        return format == ODataServiceDocumentFormat.XML
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

    /*
     * ------------------ Private methods ------------------
     */
    @SuppressWarnings("unchecked")
    private static AtomFeed toAtomFeed(final InputStream input) {
        try {
            final XMLStreamReader xmler = XMLIF.createXMLStreamReader(input);
            final JAXBContext context = JAXBContext.newInstance(AtomFeed.class);

            return ((JAXBElement<AtomFeed>) context.createUnmarshaller().unmarshal(xmler)).getValue();
        } catch (Exception e) {
            throw new IllegalArgumentException("While deserializing Atom feed", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static AtomEntry toAtomEntry(final InputStream input) {
        try {
            final XMLStreamReader xmler = XMLIF.createXMLStreamReader(input);
            final JAXBContext context = JAXBContext.newInstance(AtomEntry.class);

            return ((JAXBElement<AtomEntry>) context.createUnmarshaller().unmarshal(xmler)).getValue();
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

    private static Element toDOMFromXML(final InputStream input) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(input).getDocumentElement();
        } catch (Exception e) {
            throw new IllegalArgumentException("While deserializing XML property", e);
        }
    }

    private static Element toDOMFromJSON(final InputStream input) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.readValue(input, JSONProperty.class).getContent();
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON property", e);
        }
    }

    private static ServiceDocumentResource toServiceDocumentFromXML(final InputStream input) {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();

            final Document doc = builder.parse(input);
            final NodeList services = doc.getElementsByTagName(ODataConstants.ELEM_SERVICE);

            if (services.getLength() != 1) {
                throw new IllegalArgumentException("Invalid service document");
            }

            final Element service = (Element) services.item(0);

            final XMLServiceDocument res = new XMLServiceDocument();
            res.setBaseURI(URI.create(service.getAttribute(ODataConstants.ATTR_XMLBASE)));

            final NodeList collections = service.getElementsByTagName(ODataConstants.ELEM_COLLECTION);
            for (int i = 0; i < collections.getLength(); i++) {
                final Element collection = (Element) collections.item(i);

                final NodeList title = collection.getElementsByTagName(ODataConstants.ATTR_ATOM_TITLE);
                if (title.getLength() != 1) {
                    throw new IllegalArgumentException("Invalid collection element found");
                }

                res.addToplevelEntitySet(title.item(0).getTextContent(),
                        collection.getAttribute(ODataConstants.ATTR_HREF));
            }
            return res;
        } catch (Exception e) {
            throw new IllegalArgumentException("While deserializing XML service document", e);
        }
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
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();

            final Document doc = builder.parse(input);
            final NodeList uris = doc.getElementsByTagName(ODataConstants.ELEM_URI);

            final List<URI> links = new ArrayList<URI>();
            for (int i = 0; i < uris.getLength(); i++) {
                links.add(URI.create(uris.item(i).getTextContent()));
            }

            final NodeList next = doc.getElementsByTagName(ODataConstants.NEXT_LINK_REL);
            final XMLLinkCollection res = next.getLength() > 0
                    ? new XMLLinkCollection(URI.create(next.item(0).getTextContent()))
                    : new XMLLinkCollection();
            res.setLinks(links);
            return res;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deserializing XML $links", e);
        }
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
            final XMLStreamReader xmler = XMLIF.createXMLStreamReader(input);
            final JAXBContext context = JAXBContext.newInstance(XMLODataError.class);

            return context.createUnmarshaller().unmarshal(xmler, XMLODataError.class).getValue();
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
