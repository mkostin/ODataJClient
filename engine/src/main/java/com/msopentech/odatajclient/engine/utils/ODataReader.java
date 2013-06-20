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

import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataFeed;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.w3c.dom.Node;

/**
 * OData reader.
 * <p>
 * Use this class to de-serialize an OData response body.
 * <p>
 * This class provides method helpers to de-serialize an entire feed, a set of entities and a single entity as well.
 */
public class ODataReader {

    /**
     * De-Serializes a stream into an OData feed.
     *
     * @param input stream to de-serialize.
     * @return de-serialized feed.
     * @throws NoValidEntityFound in case the feed was not found into the input stream.
     */
    public static ODataFeed deserializeFeed(final InputStream input)
            throws NoValidEntityFound {

        return null;
    }

    /**
     * Parses a DOM object taking care to de-serializes the first OData entity found.
     *
     * @param DOM object to be de-serialized.
     * @return entity de-serialized.
     * @throws NoValidEntityFound in case of no entity was found into the input stream.
     */
    public static ODataEntity deserializeEntity(final Node input)
            throws NoValidEntityFound {
        try {
            final JAXBContext context = JAXBContext.newInstance(AtomEntry.class);

            @SuppressWarnings("unchecked")
            AtomEntry entry = ((JAXBElement<AtomEntry>) context.createUnmarshaller().unmarshal(input)).getValue();

            return ODataBinder.getODataEntity(entry);
        } catch (JAXBException e) {
            throw new NoValidEntityFound(e);
        }
    }

    /**
     * Parses a stream taking care to de-serializes the first OData entity found.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as AtomEntry or JSONEntry
     * @return entity de-serialized.
     */
    public static ODataEntity deserializeEntity(final InputStream input, final ODataFormat format) {
        return ODataBinder.getODataEntity(SerializationUtils.deserializeEntry(input, format.getEntryClass()));
    }

    /**
     * Parses a stream taking care to de-serializes the first OData entity property found.
     *
     * @param input stream to de-serialize.
     * @return OData entity property de-serialized.
     * @throws NoValidEntityFound in case no property was found into the input stream.
     */
    public static ODataProperty deserializeProperty(final InputStream input)
            throws NoValidEntityFound {

        return null;
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
