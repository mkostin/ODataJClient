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
import com.msopentech.odatajclient.engine.data.atom.AtomSerializer;
import com.msopentech.odatajclient.engine.data.json.JSONEntry;
import com.msopentech.odatajclient.engine.data.json.JSONFeed;
import com.msopentech.odatajclient.engine.data.json.JSONProperty;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
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
     * Writes <tt>FeedResource</tt> object onto the given stream.
     *
     * @param <T> feed resource type.
     * @param obj object to be streamed.
     * @param out output stream.
     */
    public static <T extends FeedResource> void feed(final T obj, final OutputStream out) {
        feed(obj, new OutputStreamWriter(out));
    }

    /**
     * Writes <tt>FeedResource</tt> object by the given writer.
     *
     * @param <T> feed resource type.
     * @param obj object to be streamed.
     * @param writer writer.
     */
    public static <T extends FeedResource> void feed(final T obj, final Writer writer) {
        if (obj instanceof AtomFeed) {
            atom((AtomFeed) obj, writer);
        } else {
            json((JSONFeed) obj, writer);
        }
    }

    /**
     * Writes <tt>EntryResource</tt> object onto the given stream.
     *
     * @param <T> entry resource type.
     * @param obj object to be streamed.
     * @param out output stream.
     */
    public static <T extends EntryResource> void entry(final T obj, final OutputStream out) {
        entry(obj, new OutputStreamWriter(out));
    }

    /**
     * Writes <tt>EntryResource</tt> object by the given writer.
     *
     * @param <T> entry resource type.
     * @param obj object to be streamed.
     * @param writer writer.
     */
    public static <T extends EntryResource> void entry(final T obj, final Writer writer) {
        if (obj instanceof AtomEntry) {
            atom((AtomEntry) obj, writer);
        } else {
            json((JSONEntry) obj, writer);
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
    public static void dom(final Node content, final OutputStream out) {
        dom(content, new OutputStreamWriter(out));
    }

    /**
     * Writes DOM object by the given writer.
     *
     * @param content DOM to be streamed.
     * @param writer writer.
     */
    public static void dom(final Node content, final Writer writer) {
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

    /*
     * ------------------ Private methods ------------------
     */
    private static <T extends AbstractPayloadObject> void atom(final T obj, final Writer writer) {
        try {
            dom(AtomSerializer.serialize(obj), writer);
        } catch (Exception e) {
            throw new IllegalArgumentException("While serializing Atom object", e);
        }
    }

    private static <T extends AbstractPayloadObject> void json(final T obj, final Writer writer) {
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
            throw new IllegalArgumentException("While serializing JSON property", e);
        }
    }

    private static void xmlLink(final ODataLink link, final Writer writer) {
        try {
            final DocumentBuilder builder = ODataConstants.DOC_BUILDER_FACTORY.newDocumentBuilder();
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
