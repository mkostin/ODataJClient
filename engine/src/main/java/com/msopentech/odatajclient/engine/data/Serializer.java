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
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.data.atom.AtomFeed;
import com.msopentech.odatajclient.engine.data.json.JSONProperty;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

/**
 * Utility class for serialization.
 */
public final class Serializer {

    private Serializer() {
        // Empty private constructor for static utility classes
    }

    /**
     * Writes
     * <code>FeedResource</code> object onto the given stream.
     *
     * @param <T> feed resource type.
     * @param obj object to be streamed.
     * @param out output stream.
     */
    public static <T extends FeedResource> void feed(final T obj, final OutputStream out) {
        feed(obj, new OutputStreamWriter(out));
    }

    /**
     * Writes
     * <code>FeedResource</code> object by the given writer.
     *
     * @param <T> feed resource type.
     * @param obj object to be streamed.
     * @param writer writer.
     */
    public static <T extends FeedResource> void feed(final T obj, final Writer writer) {
        if (obj.getClass().equals(AtomFeed.class)) {
            atom(obj, obj.getClass(), writer);
        } else {
            json(obj, writer);
        }
    }

    /**
     * Writes
     * <code>EntryResource</code> object onto the given stream.
     *
     * @param <T> entry resource type.
     * @param obj object to be streamed.
     * @param out output stream.
     */
    public static <T extends EntryResource> void entry(final T obj, final OutputStream out) {
        entry(obj, new OutputStreamWriter(out));
    }

    /**
     * Writes
     * <code>EntryResource</code> object by the given writer.
     *
     * @param <T> entry resource type.
     * @param obj object to be streamed.
     * @param writer writer.
     */
    public static <T extends EntryResource> void entry(final T obj, final Writer writer) {
        if (obj.getClass().equals(AtomEntry.class)) {
            atom(obj, obj.getClass(), writer);
        } else {
            json(obj, writer);
        }
    }

    /**
     * Writes entry content onto the given stream.
     *
     * @param element element to be streamed.
     * @param format streaming format.
     * @param out output stream.
     */
    public static void property(final Element element, final ODataFormat format, final OutputStream out) {
        property(element, format, new OutputStreamWriter(out));
    }

    /**
     * Writes entry content by the given writer.
     *
     * @param element element to be streamed.
     * @param format streaming format.
     * @param writer writer.
     */
    public static void property(final Element element, final ODataFormat format, final Writer writer) {
        if (format == ODataFormat.XML) {
            dom(element, writer);
        } else {
            json(element, writer);
        }
    }

    /**
     * Writes OData link onto the given stream.
     *
     * @param link OData link to be streamed.
     * @param format streaming format.
     * @param out output stream.
     */
    public static void link(final ODataLink link, final ODataFormat format, final OutputStream out) {
        link(link, format, new OutputStreamWriter(out));
    }

    /**
     * Writes OData link by the given writer.
     *
     * @param link OData link to be streamed.
     * @param format streaming format.
     * @param writer writer.
     */
    public static void link(final ODataLink link, final ODataFormat format, final Writer writer) {
        if (format == ODataFormat.XML) {
            xmlLink(link, writer);
        } else {
            jsonLink(link, writer);
        }
    }

    /**
     * Writes DOM object onto the given stream.
     *
     * @param content DOM to be streamed.
     * @param out output stream.
     */
    public static void dom(final Element content, final OutputStream out) {
        dom(content, new OutputStreamWriter(out));
    }

    /**
     * Writes DOM object by the given writer.
     *
     * @param content DOM to be streamed.
     * @param writer writer.
     */
    public static void dom(final Element content, final Writer writer) {
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

    /**
     * Adds atom element as child element of the given node.
     *
     * @param obj atom object.
     * @param reference reference.
     * @param root destination node (parent of the new one).
     */
    public static void atom(final Object obj, final Class<?> reference, final Node root) {
        try {
            final Marshaller marshaller = getMarshaller(reference);
            marshaller.marshal(obj, root);
        } catch (JAXBException e) {
            throw new IllegalArgumentException("While serializing Atom object", e);
        }
    }


    /*
     * ------------------ Private methods ------------------
     */
    private static Marshaller getMarshaller(final Class<?> reference) throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(reference);
        final Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, ODataConstants.UTF8);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        return marshaller;
    }

    private static void atom(final Object obj, final Class<?> reference, final Writer writer) {
        try {
            final Marshaller marshaller = getMarshaller(reference);
            marshaller.marshal(obj, writer);
        } catch (JAXBException e) {
            throw new IllegalArgumentException("While serializing Atom object", e);
        }
    }

    private static void json(final Object obj, final Writer writer) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.writeValue(writer, obj);
        } catch (IOException e) {
            throw new IllegalArgumentException("While serializing JSON object", e);
        }
    }

    private static void json(final Element element, final Writer writer) {
        try {
            final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            final JSONProperty property = new JSONProperty();
            property.setContent(element);
            mapper.writeValue(writer, property);
        } catch (IOException e) {
            throw new IllegalArgumentException("While serializing JSON object", e);
        }
    }

    private static void xmlLink(final ODataLink link, final Writer writer) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.newDocument();
            final Element uri = doc.createElementNS(ODataConstants.NS_DATASERVICES, ODataConstants.ELEM_URI);
            uri.appendChild(doc.createTextNode(link.getLink().toASCIIString()));

            dom(uri, writer);
        } catch (Exception e) {
            throw new IllegalArgumentException("While serializing XML link", e);
        }
    }

    private static void jsonLink(final ODataLink link, final Writer writer) {
        final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        final ObjectNode uri = mapper.createObjectNode();
        uri.put(ODataConstants.JSON_URL, link.getLink().toASCIIString());

        try {
            mapper.writeValue(writer, uri);
        } catch (Exception e) {
            throw new IllegalArgumentException("While serializing JSON link", e);
        }
    }
}
