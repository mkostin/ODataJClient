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
import com.msopentech.odatajclient.engine.data.json.JSONLinks;
import com.msopentech.odatajclient.engine.data.json.JSONProperty;
import com.msopentech.odatajclient.engine.data.json.JSONServiceDocument;
import com.msopentech.odatajclient.engine.data.xml.XMLServiceDocument;
import com.msopentech.odatajclient.engine.types.ODataPropertyFormat;
import com.msopentech.odatajclient.engine.types.ODataServiceDocumentFormat;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utility class for deserialization.
 */
public final class Deserializer {

    private Deserializer() {
        // Empty private constructor for static utility classes
    }

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

    @SuppressWarnings("unchecked")
    public static AtomFeed toAtomFeed(final Node node) {
        try {
            final JAXBContext context = JAXBContext.newInstance(AtomFeed.class);
            return ((JAXBElement<AtomFeed>) context.createUnmarshaller().unmarshal(node)).getValue();
        } catch (JAXBException e) {
            throw new IllegalArgumentException("While deserializing Atom feed", e);
        }
    }

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

    @SuppressWarnings("unchecked")
    public static AtomEntry toAtomEntry(final Node node) {
        try {
            final JAXBContext context = JAXBContext.newInstance(AtomEntry.class);
            return ((JAXBElement<AtomEntry>) context.createUnmarshaller().unmarshal(node)).getValue();
        } catch (JAXBException e) {
            throw new IllegalArgumentException("While deserializing Atom entry", e);
        }
    }

    public static Element toDOM(final InputStream input, final ODataPropertyFormat format) {
        return format == ODataPropertyFormat.XML
                ? toDOMFromXML(input)
                : toDOMFromJSON(input);
    }

    public static ServiceDocumentResource toServiceDocument(
            final InputStream input, final ODataServiceDocumentFormat format) {

        return format == ODataServiceDocumentFormat.XML
                ? toServiceDocumentFromXML(input)
                : toServiceDocumentFromJSON(input);
    }

    public static List<URI> toLinks(final InputStream input, final ODataPropertyFormat format) {
        return format == ODataPropertyFormat.XML
                ? toLinksFromXML(input)
                : toLinksFromJSON(input);
    }

    /*
     * ------------------ Private methods ------------------
     */
    @SuppressWarnings("unchecked")
    private static AtomFeed toAtomFeed(final InputStream input) {
        try {
            final JAXBContext context = JAXBContext.newInstance(AtomFeed.class);
            return ((JAXBElement<AtomFeed>) context.createUnmarshaller().unmarshal(input)).getValue();
        } catch (JAXBException e) {
            throw new IllegalArgumentException("While deserializing Atom feed", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static AtomEntry toAtomEntry(final InputStream input) {
        try {
            final JAXBContext context = JAXBContext.newInstance(AtomEntry.class);
            return ((JAXBElement<AtomEntry>) context.createUnmarshaller().unmarshal(input)).getValue();
        } catch (JAXBException e) {
            throw new IllegalArgumentException("While deserializing Atom entry", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static JSONFeed toJSONFeed(final InputStream input) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.readValue(input, JSONFeed.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON feed", e);
        }
    }

    @SuppressWarnings("unchecked")
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

    private static List<URI> toLinksFromXML(final InputStream input) {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();

            final Document doc = builder.parse(input);
            final NodeList uris = doc.getElementsByTagName("uri");

            final List<URI> links = new ArrayList<URI>();
            for (int i = 0; i < uris.getLength(); i++) {
                links.add(URI.create(uris.item(i).getTextContent()));
            }
            return links;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deserializing XML $links", e);
        }
    }

    private static List<URI> toLinksFromJSON(final InputStream input) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.readValue(input, JSONLinks.class).getLinks();
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON $links", e);
        }
    }
}
