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

import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataFeed;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;

/**
 * OData writer.
 * <p>
 * Use this class to serialize an OData request body.
 * <p>
 * This class provides method helpers to serialize a set of entities and a single entity as well.
 */
public class ODataWriter {

    private final ODataFormat format;

    /**
     * Constructor.
     *
     * @param format OData format.
     */
    public ODataWriter(ODataFormat format) {
        this.format = format;
    }

    /**
     * Serializes an entire feed.
     *
     * @param feed entities to be serialized.
     * @return stream of serialized object.
     */
    public InputStream serialize(final ODataFeed feed) {
        return null;
    }

    /**
     * Serializes a collection of OData entities.
     *
     * @param entities entities to be serialized.
     * @return stream of serialized objects.
     */
    public static InputStream serialize(
            final Collection<ODataEntity> entities, final Class<? extends EntryResource> reference) {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            for (ODataEntity entity : entities) {
                SerializationUtils.serializeEntry(ODataBinder.getEntry(entity, reference), os);
            }

            return new ByteArrayInputStream(os.toByteArray());
        } finally {
            try {
                os.close();
            } catch (IOException ignore) {
                // ignore exception
            }
        }
    }

    /**
     * Serializes a single OData entity.
     *
     * @param entity entity to be serialized.
     * @return stream of serialized object.
     */
    public static InputStream serialize(final ODataEntity entity, final Class<? extends EntryResource> reference) {
        return serialize(Collections.<ODataEntity>singleton(entity), reference);
    }

    /**
     * Serializes a single OData entity property.
     *
     * @param property entity property to be serialized.
     * @return stream of serialized object.
     */
    public InputStream serialize(final ODataProperty property) {
        return null;
    }

    /**
     * Serializes a single OData entity property value.
     *
     * @param value entity property value to be serialized.
     * @return stream of serialized object.
     */
    public InputStream serialize(final ODataValue value) {
        return null;
    }

    /**
     * Serializes a single OData entity into a given OutputStream.
     *
     * @param entity entity to be serialized.
     * @param os output stream of the serialization.
     */
    public void serialize(final ODataEntity entity, final OutputStream os) {
    }
}
