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
     * Serializes a collection of OData entities.
     *
     * @param entities entities to be serialized.
     * @return stream of serialized objects.
     */
    public InputStream serialize(final Collection<ODataEntity> entities) {
        return null;
    }

    /**
     * Serializes a single OData entity.
     *
     * @param entity entity to be serialized.
     * @return stream of serialized object.
     */
    public InputStream serialize(final ODataEntity entity) {
        return serialize(Collections.<ODataEntity>singleton(entity));
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
     * Serializes a single OData entity into a given OutputStream.
     *
     * @param entity entity to be serialized.
     * @param os output stream of the serialization.
     */
    public void serialize(final ODataEntity entity, final OutputStream os) {
    }
}
