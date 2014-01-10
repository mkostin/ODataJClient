/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.engine.data;

import com.msopentech.odatajclient.engine.client.ODataClient;
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
 * <br/>
 * Use this class to serialize an OData request body.
 * <br/>
 * This class provides method helpers to serialize a set of entities and a single entity as well.
 */
public abstract class AbstractODataWriter {

    protected final ODataClient client;

    protected AbstractODataWriter(final ODataClient client) {
        this.client = client;
    }

    /**
     * Writes a collection of OData entities.
     *
     * @param entities entities to be serialized.
     * @param format serialization format.
     * @return stream of serialized objects.
     */
    public InputStream writeEntities(final Collection<ODataEntity> entities, final ODataPubFormat format) {
        return writeEntities(entities, format, true);
    }

    /**
     * Writes a collection of OData entities.
     *
     * @param entities entities to be serialized.
     * @param format serialization format.
     * @param outputType whether to explicitly output type information.
     * @return stream of serialized objects.
     */
    public InputStream writeEntities(
            final Collection<ODataEntity> entities, final ODataPubFormat format, final boolean outputType) {

        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            for (ODataEntity entity : entities) {
                Serializer.entry(client.getODataBinder().
                        getEntry(entity, ResourceFactory.entryClassForFormat(format), outputType), output);
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
    public InputStream writeEntity(final ODataEntity entity, final ODataPubFormat format) {
        return writeEntity(entity, format, true);
    }

    /**
     * Serializes a single OData entity.
     *
     * @param entity entity to be serialized.
     * @param format serialization format.
     * @param outputType whether to explicitly output type information.
     * @return stream of serialized object.
     */
    public InputStream writeEntity(
            final ODataEntity entity, final ODataPubFormat format, final boolean outputType) {

        return writeEntities(Collections.<ODataEntity>singleton(entity), format, outputType);
    }

    /**
     * Writes a single OData entity property.
     *
     * @param property entity property to be serialized.
     * @param format serialization format.
     * @return stream of serialized object.
     */
    public InputStream writeProperty(final ODataProperty property, final ODataFormat format) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            Serializer.property(client.getODataBinder().toDOMElement(property), format, output);

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
    public InputStream writeLink(final ODataLink link, final ODataFormat format) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            Serializer.link(link, format, output);

            return new ByteArrayInputStream(output.toByteArray());
        } finally {
            IOUtils.closeQuietly(output);
        }
    }
}
