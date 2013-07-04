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
import com.msopentech.odatajclient.engine.format.ODataServiceDocumentFormat;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public static ODataFeed readFeed(final InputStream input, final ODataPubFormat format) {
        return ODataBinder.getODataFeed(Deserializer.toFeed(input, ResourceFactory.feedClassForFormat(format)));
    }

    /**
     * Parses a stream taking care to de-serializes the first OData entity found.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as AtomEntry or JSONEntry
     * @return entity de-serialized.
     */
    public static ODataEntity readEntity(final InputStream input, final ODataPubFormat format) {
        return ODataBinder.getODataEntity(Deserializer.toEntry(input, ResourceFactory.entryClassForFormat(format)));
    }

    /**
     * Parses a stream taking care to de-serializes the first OData entity property found.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as XML or JSON
     * @return OData entity property de-serialized.
     */
    public static ODataProperty readProperty(final InputStream input, final ODataFormat format) {
        return ODataBinder.getProperty(Deserializer.toDOM(input, format));
    }

    /**
     * Parses a $links request response.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as XML or JSON
     * @return List of URIs.
     */
    public static List<URI> readLinks(final InputStream input, final ODataFormat format) {
        return Deserializer.toLinks(input, format);
    }

    /**
     * Parses an OData service document.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as XML or JSON
     * @return List of URIs.
     */
    public static ODataServiceDocument readServiceDocument(
            final InputStream input, final ODataServiceDocumentFormat format) {

        return ODataBinder.getODataServiceDocument(Deserializer.toServiceDocument(input, format));
    }

    /**
     * Parses a stream into metadata representation.
     *
     * @param input stream to de-serialize.
     * @return metadata representation.
     */
    public static EdmMetadata readMetadata(final InputStream inputStream) {
        try {
            return new EdmMetadata(inputStream);
        } catch (JAXBException e) {
            LOG.error("Error unmarshalling metadata info", e);
            throw new IllegalStateException(e);
        }
    }

    public static ODataError readError(final InputStream inputStream, final boolean isXML) {
        return Deserializer.toODataError(inputStream, isXML);
    }
}
