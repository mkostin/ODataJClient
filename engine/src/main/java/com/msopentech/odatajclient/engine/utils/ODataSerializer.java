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
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import java.io.InputStream;

/**
 * This class has to be used to serialize and deserialize entries, links, attributes and values.
 */
public class ODataSerializer {

    private final ODataFormat format;

    public ODataSerializer(final ODataFormat format) {
        this.format = format;
    }

    /**
     * Use this method to serialize a single entity.
     *
     * @param entity entity to be serialized.
     * @param format serialization format.
     * @return the serialized entity.
     */
    public InputStream serialize(final ODataEntity entity) {
        return null;
    }

    /**
     * Use this method to deserialize an entire feed or a sub set of entities.
     *
     * @param source stream to deserialize.
     * @param format format of the source.
     * @return the deserialized entity.
     */
    public ODataEntity deserializeEntity(final InputStream source) {
        return null;
    }

    /**
     * Use this method to deserialize a single requested property.
     *
     * @param source stream to deserialize.
     * @param format format of the source.
     * @return the deserialized property.
     */
    public ODataProperty deserializeProperty(final InputStream source) {
        return null;
    }
}
