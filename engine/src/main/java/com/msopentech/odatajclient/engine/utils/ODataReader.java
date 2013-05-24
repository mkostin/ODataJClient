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
import com.msopentech.odatajclient.engine.types.ODataFormat;
import java.io.InputStream;
import java.util.Collection;

/**
 * OData reader.
 * <p>
 * Use this class to de-serialize an OData response body.
 * <p>
 * This class provides method helpers to de-serialize an entire feed, a set of entities and a single entity as well.
 */
public class ODataReader {

    private final ODataFormat format;

    /**
     * Constructor.
     *
     * @param format OData format.
     */
    public ODataReader(ODataFormat format) {
        this.format = format;
    }

    /**
     * De-Serializes a stream into a collection of OData entities.
     *
     * @param input stream to de-serialize.
     * @return collection of entities de-serialized.
     * @throws NoSuchEntityFound in case of no entity has been found into the input stream.
     */
    public Collection<ODataEntity> deserialize(final InputStream input)
            throws NoSuchEntityFound {
        return null;
    }

    /**
     * Parses a stream taking care to de-serializes the first OData entity found.
     *
     * @param input stream to de-serialize.
     * @return entity de-serialized.
     * @throws NoSuchEntityFound in case of no entity has been found into the input stream.
     */
    public ODataEntity deserializeEntity(final InputStream input) {
        return null;
    }
}
