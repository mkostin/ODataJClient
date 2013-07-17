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

import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.io.IOUtils;

/**
 * OData writer.
 * <p>
 * Use this class to serialize an OData request body.
 * <p>
 * This class provides method helpers to serialize a set of entities and a single entity as well.
 */
public final class ODataWriter {

    private ODataWriter() {
        // Empty private constructor for static utility classes
    }

    /**
     * Writes a collection of OData entities.
     *
     * @param entities entities to be serialized.
     * @param format serialization format.
     * @return stream of serialized objects.
     */
    public static InputStream writeEntities(final Collection<ODataEntity> entities, final ODataPubFormat format) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            for (ODataEntity entity : entities) {
                Serializer.entry(ODataBinder.getEntry(entity, ResourceFactory.entryClassForFormat(format)), output);
            }

            return new ByteArrayInputStream(output.toByteArray());
        } finally {
            IOUtils.closeQuietly(output);
        }
    }

    /**
     * Serializes a single OData entity.
     *
     * @param entity entity to be serialized.
     * @param format serialization format.
     * @return stream of serialized object.
     */
    public static InputStream writeEntity(final ODataEntity entity, final ODataPubFormat format) {
        return writeEntities(Collections.<ODataEntity>singleton(entity), format);
    }

    /**
     * Writes a single OData entity property.
     *
     * @param property entity property to be serialized.
     * @param format serialization format.
     * @return stream of serialized object.
     */
    public static InputStream writeProperty(final ODataProperty property, final ODataFormat format) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            Serializer.property(ODataBinder.getProperty(property), format, output);

            return new ByteArrayInputStream(output.toByteArray());
        } finally {
            IOUtils.closeQuietly(output);
        }
    }

    /**
     * Writes an OData link.
     *
     * @param link link to be serialized.
     * @param format serialization format.
     * @return stream of serialized object.
     */
    public static InputStream writeLink(final ODataLink link, final ODataFormat format) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            Serializer.link(link, format, output);

            return new ByteArrayInputStream(output.toByteArray());
        } finally {
            IOUtils.closeQuietly(output);
        }
    }
}
