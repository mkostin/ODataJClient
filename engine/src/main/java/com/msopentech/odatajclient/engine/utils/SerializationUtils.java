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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.FeedResource;
import com.msopentech.odatajclient.engine.data.ServiceDocumentResource;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public final class SerializationUtils {

    private SerializationUtils() {
        // Empty private constructor for static utility classes
    }

    public static String getSimpleName(final Node node) {
        return node.getLocalName() == null
                ? node.getNodeName().substring(node.getNodeName().indexOf(':') + 1)
                : node.getLocalName();
    }

    public static <T extends FeedResource> void serializeFeed(final T obj, final OutputStream out) {
        serializeFeed(obj, new OutputStreamWriter(out));
    }

    public static <T extends FeedResource> void serializeFeed(final T obj, final Writer writer) {
        if (obj.getClass().equals(AtomFeed.class)) {
            serializeAsAtom(obj, obj.getClass(), writer);
        } else {
            serializeAsJSON(obj, writer);
        }
    }

    public static <T extends EntryResource> void serializeEntry(final T obj, final OutputStream out) {
        serializeEntry(obj, new OutputStreamWriter(out));
    }

    public static <T extends EntryResource> void serializeEntry(final T obj, final Writer writer) {
        if (obj.getClass().equals(AtomEntry.class)) {
            serializeAsAtom(obj, obj.getClass(), writer);
        } else {
            serializeAsJSON(obj, writer);
        }
    }

    public static void serializeDOMElement(final Element content, final OutputStream out) {
        serializeDOMElement(content, new OutputStreamWriter(out));
    }

    public static void serializeDOMElement(final Element content, final Writer writer) {
        try {
            final DOMImplementationRegistry reg = DOMImplementationRegistry.newInstance();
            final DOMImplementationLS impl = (DOMImplementationLS) reg.getDOMImplementation("LS");
            final LSSerializer serializer = impl.createLSSerializer();
            final LSOutput lso = impl.createLSOutput();
            lso.setCharacterStream(writer);
            serializer.write(content, lso);
        } catch (Exception e) {
            throw new IllegalArgumentException("While serializing DOM element", e);
        }
    }

    private static Marshaller getMarshaller(final Class<?> reference) throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(reference);
        final Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        return marshaller;
    }

    public static void serializeAsAtom(final Object obj, final Class<?> reference, final Node root) {
        try {
            final Marshaller marshaller = getMarshaller(reference);
            marshaller.marshal(obj, root);
        } catch (JAXBException e) {
            throw new IllegalArgumentException("While serializing Atom object", e);
        }
    }

    private static void serializeAsAtom(final Object obj, final Class<?> reference, final Writer writer) {
        try {
            final Marshaller marshaller = getMarshaller(reference);
            marshaller.marshal(obj, writer);
        } catch (JAXBException e) {
            throw new IllegalArgumentException("While serializing Atom object", e);
        }
    }

    private static void serializeAsJSON(final Object obj, final Writer writer) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.writeValue(writer, obj);
        } catch (IOException e) {
            throw new IllegalArgumentException("While serializing JSON object", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends FeedResource> T deserializeFeed(final InputStream input, final Class<T> reference) {
        T entry;

        if (AtomFeed.class.equals(reference)) {
            entry = (T) deserializeAtomFeed(input);
        } else {
            entry = (T) deserializeJSONFeed(input);
        }

        return entry;
    }

    @SuppressWarnings("unchecked")
    public static <T extends EntryResource> T deserializeEntry(final InputStream input, final Class<T> reference) {
        T entry;

        if (AtomEntry.class.equals(reference)) {
            entry = (T) deserializeAtomEntry(input);
        } else {
            entry = (T) deserializeJSONEntry(input);
        }

        return entry;
    }

    public static Element deserializeProperty(final InputStream input, final ODataPropertyFormat format) {
        return format == ODataPropertyFormat.XML
                ? deserializeXMLProperty(input)
                : deserializeJSONProperty(input);
    }

    public static ServiceDocumentResource deserializeServiceDocument(
            final InputStream input, final ODataServiceDocumentFormat format) {

        return format == ODataServiceDocumentFormat.XML
                ? deserializeXMLServiceDocument(input)
                : deserializeJSONServiceDocument(input);
    }

    public static List<URI> deserializeLinks(final InputStream input, final ODataPropertyFormat format) {
        return format == ODataPropertyFormat.XML
                ? deserializeXMLLinks(input)
                : deserializeJSONLinks(input);
    }

    @SuppressWarnings("unchecked")
    private static AtomFeed deserializeAtomFeed(final InputStream input) {
        try {
            final JAXBContext context = JAXBContext.newInstance(AtomFeed.class);
            return ((JAXBElement<AtomFeed>) context.createUnmarshaller().unmarshal(input)).getValue();
        } catch (JAXBException e) {
            throw new IllegalArgumentException("While deserializing Atom feed", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static AtomEntry deserializeAtomEntry(final InputStream input) {
        try {
            final JAXBContext context = JAXBContext.newInstance(AtomEntry.class);
            return ((JAXBElement<AtomEntry>) context.createUnmarshaller().unmarshal(input)).getValue();
        } catch (JAXBException e) {
            throw new IllegalArgumentException("While deserializing Atom entry", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static AtomFeed deserializeAtomFeed(final Node node) {
        try {
            final JAXBContext context = JAXBContext.newInstance(AtomFeed.class);
            return ((JAXBElement<AtomFeed>) context.createUnmarshaller().unmarshal(node)).getValue();
        } catch (JAXBException e) {
            throw new IllegalArgumentException("While deserializing Atom feed", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static AtomEntry deserializeAtomEntry(final Node node) {
        try {
            final JAXBContext context = JAXBContext.newInstance(AtomEntry.class);
            return ((JAXBElement<AtomEntry>) context.createUnmarshaller().unmarshal(node)).getValue();
        } catch (JAXBException e) {
            throw new IllegalArgumentException("While deserializing Atom entry", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static JSONFeed deserializeJSONFeed(final InputStream input) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.readValue(input, JSONFeed.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON feed", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static JSONEntry deserializeJSONEntry(final InputStream input) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.readValue(input, JSONEntry.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON entry", e);
        }
    }

    private static Element deserializeXMLProperty(final InputStream input) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(input).getDocumentElement();
        } catch (Exception e) {
            throw new IllegalArgumentException("While deserializing XML property", e);
        }
    }

    private static Element deserializeJSONProperty(final InputStream input) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.readValue(input, JSONProperty.class).getContent();
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON property", e);
        }
    }

    private static ServiceDocumentResource deserializeXMLServiceDocument(final InputStream input) {
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

    private static ServiceDocumentResource deserializeJSONServiceDocument(final InputStream input) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.readValue(input, JSONServiceDocument.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON service document", e);
        }
    }

    private static List<URI> deserializeXMLLinks(final InputStream input) {
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

    private static List<URI> deserializeJSONLinks(final InputStream input) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.readValue(input, JSONLinks.class).getLinks();
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON $links", e);
        }
    }
}
