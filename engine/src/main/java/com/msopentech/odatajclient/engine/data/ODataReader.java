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

import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.types.ODataServiceDocumentFormat;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import com.msopentech.odatajclient.engine.types.ODataPropertyFormat;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import com.msopentech.odatajclient.engine.utils.SerializationUtils;
import com.msopentech.odatajclient.engine.utils.URIUtils;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * OData reader.
 * <p>
 * Use this class to de-serialize an OData response body.
 * <p>
 * This class provides method helpers to de-serialize an entire feed, a set of entities and a single entity as well.
 */
public final class ODataReader {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ODataReader.class);

    private ODataReader() {
        // Empty private constructor for static utility classes
    }

    /**
     * De-Serializes a stream into an OData feed.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as AtomEntry or JSONEntry
     * @return de-serialized feed.
     */
    public static ODataFeed deserializeFeed(final InputStream input, final ODataFormat format) {
        final FeedResource feedResource =
                SerializationUtils.deserializeFeed(input, ResourceFactory.feedClassForFormat(format));
        return ODataBinder.getODataFeed(feedResource);
    }

    /**
     * Parses a stream taking care to de-serializes the first OData entity found.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as AtomEntry or JSONEntry
     * @return entity de-serialized.
     */
    public static ODataEntity deserializeEntity(final InputStream input, final ODataFormat format) {
        return ODataBinder.getODataEntity(
                SerializationUtils.deserializeEntry(input, ResourceFactory.entryClassForFormat(format)));
    }

    /**
     * Parses a stream taking care to de-serializes the first OData entity property found.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as XML or JSON
     * @return OData entity property de-serialized.
     */
    public static ODataProperty deserializeProperty(final InputStream input, final ODataPropertyFormat format) {
        return ODataBinder.newProperty(SerializationUtils.deserializeProperty(input, format));
    }

    /**
     * Parses a $links request response.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as XML or JSON
     * @return List of URIs.
     */
    public static List<URI> deserializeLinks(final InputStream input, final ODataPropertyFormat format) {
        return format == ODataPropertyFormat.XML
                ? deserializeLinksAsXML(input) : null;
    }

    private static List<URI> deserializeLinksAsXML(final InputStream input) {
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
        } catch (ParserConfigurationException e) {
            LOG.error("Error parsing input stream", e);
            throw new IllegalArgumentException(e);
        } catch (Exception e) {
            LOG.error("Error deserializing property", e);
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Parses an OData service document.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as XML or JSON
     * @return List of URIs.
     */
    public static ODataServiceDocument deserializeServiceDocument(
            final InputStream input, final ODataServiceDocumentFormat format) {

        return format == ODataServiceDocumentFormat.XML
                ? deserializeServiceDocumentAsXML(input)
                : null;
    }

    private static ODataServiceDocument deserializeServiceDocumentAsXML(final InputStream input) {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();

            final Document doc = builder.parse(input);
            final NodeList services = doc.getElementsByTagName(ODataConstants.ELEM_SERVICE);

            if (services.getLength() != 1) {
                throw new IllegalArgumentException("Invalid service document");
            }

            final Element service = (Element) services.item(0);
            final String base = service.getAttribute(ODataConstants.ATTR_XMLBASE);

            final NodeList collections = service.getElementsByTagName(ODataConstants.ELEM_COLLECTION);

            final ODataServiceDocument res = new ODataServiceDocument();

            for (int i = 0; i < collections.getLength(); i++) {
                final Element collection = (Element) collections.item(i);
                final URI uri = URIUtils.getURI(base, collection.getAttribute(ODataConstants.ATTR_HREF).trim());
                final NodeList title = collection.getElementsByTagName(ODataConstants.ATTR_ATOM_TITLE);

                if (title.getLength() != 1) {
                    throw new IllegalArgumentException("Invalid collection element found");
                }

                res.addEntitySet(title.item(0).getTextContent().trim(), uri);
            }
            return res;
        } catch (ParserConfigurationException e) {
            LOG.error("Error parsing input stream", e);
            throw new IllegalArgumentException(e);
        } catch (Exception e) {
            LOG.error("Error deserializing property", e);
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Parses a stream into metadata representation.
     *
     * @param input stream to de-serialize.
     * @return metadata representation.
     */
    public static EdmMetadata deserializeMetadata(final InputStream inputStream) {
        return null;
    }
}
