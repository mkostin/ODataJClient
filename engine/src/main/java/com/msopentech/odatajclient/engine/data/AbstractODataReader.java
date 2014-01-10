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
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import com.msopentech.odatajclient.engine.format.ODataValueFormat;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * OData reader.
 * <br/>
 * Use this class to de-serialize an OData response body.
 * <br/>
 * This class provides method helpers to de-serialize an entire feed, a set of entities and a single entity as well.
 */
public abstract class AbstractODataReader {

    /**
     * Logger.
     */
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractODataReader.class);

    protected final ODataClient client;

    protected AbstractODataReader(final ODataClient client) {
        this.client = client;
    }

    /**
     * De-Serializes a stream into an OData entity set.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as AtomFeed or JSONFeed
     * @return de-serialized entity set.
     */
    public ODataEntitySet readEntitySet(final InputStream input, final ODataPubFormat format) {
        return client.getODataBinder().getODataEntitySet(
                Deserializer.toFeed(input, ResourceFactory.feedClassForFormat(format)));
    }

    /**
     * Parses a stream taking care to de-serializes the first OData entity found.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as AtomEntry or JSONEntry
     * @return entity de-serialized.
     */
    public ODataEntity readEntity(final InputStream input, final ODataPubFormat format) {
        return client.getODataBinder().getODataEntity(
                Deserializer.toEntry(input, ResourceFactory.entryClassForFormat(format)));
    }

    /**
     * Parses a stream taking care to de-serialize the first OData entity property found.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as XML or JSON
     * @return OData entity property de-serialized.
     */
    public ODataProperty readProperty(final InputStream input, final ODataFormat format) {
        final Element property = Deserializer.toPropertyDOM(input, format);

        // The ODataProperty object is used either for actual entity properties and for invoke result (when return type
        // is neither an entity nor a collection of entities).
        // Such formats are mostly the same except for collections: an entity property looks like
        //     <aproperty m:type="Collection(AType)">
        //       <element>....</element>
        //     </aproperty>
        //
        // while an invoke result with returnType="Collection(AnotherType)" looks like
        //     <functionImportName>
        //       <element m:type="AnotherType">...</element>
        //     <functionImportName>
        //
        // The code below is meant for "normalizing" the latter into
        //     <functionImportName m:type="Collection(AnotherType)">
        //       <element m:type="AnotherType">...</element>
        //     <functionImportName>
        final String type = property.getAttribute(ODataConstants.ATTR_M_TYPE);
        final NodeList elements = property.getElementsByTagName(ODataConstants.ELEM_ELEMENT);
        if (StringUtils.isBlank(type) && elements != null && elements.getLength() > 0) {
            final Node elementType = elements.item(0).getAttributes().getNamedItem(ODataConstants.ATTR_M_TYPE);
            if (elementType != null) {
                property.setAttribute(ODataConstants.ATTR_M_TYPE, "Collection(" + elementType.getTextContent() + ")");
            }
        }

        return client.getODataBinder().getProperty(property);
    }

    /**
     * Parses a $links request response.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as XML or JSON
     * @return List of URIs.
     */
    public ODataLinkCollection readLinks(final InputStream input, final ODataFormat format) {
        return client.getODataBinder().getLinkCollection(Deserializer.toLinkCollection(input, format));
    }

    /**
     * Parses an OData service document.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as XML or JSON
     * @return List of URIs.
     */
    public ODataServiceDocument readServiceDocument(final InputStream input, final ODataFormat format) {
        return client.getODataBinder().getODataServiceDocument(Deserializer.toServiceDocument(input, format));
    }

    /**
     * Parses a stream into metadata representation.
     *
     * @param input stream to de-serialize.
     * @return metadata representation.
     */
    public EdmMetadata readMetadata(final InputStream input) {
        return new EdmMetadata(input);
    }

    /**
     * Parses a stream into an OData error.
     *
     * @param inputStream stream to de-serialize.
     * @param isXML 'TRUE' if the error is in XML format.
     * @return OData error.
     */
    public ODataError readError(final InputStream inputStream, final boolean isXML) {
        return Deserializer.toODataError(inputStream, isXML);
    }

    /**
     * Parses a stream into the object type specified by the given reference.
     *
     * @param <T> expected object type.
     * @param src input stream.
     * @param format format
     * @param reference reference.
     * @return read object.
     */
    @SuppressWarnings("unchecked")
    public <T> T read(final InputStream src, final String format, final Class<T> reference) {

        Object res;

        try {
            if (ODataEntitySetIterator.class.isAssignableFrom(reference)) {
                res = new ODataEntitySetIterator(client, src, ODataPubFormat.fromString(format));
            } else if (ODataEntitySet.class.isAssignableFrom(reference)) {
                res = readEntitySet(src, ODataPubFormat.fromString(format));
            } else if (ODataEntity.class.isAssignableFrom(reference)) {
                res = readEntity(src, ODataPubFormat.fromString(format));
            } else if (ODataProperty.class.isAssignableFrom(reference)) {
                res = readProperty(src, ODataFormat.fromString(format));
            } else if (ODataLinkCollection.class.isAssignableFrom(reference)) {
                res = readLinks(src, ODataFormat.fromString(format));
            } else if (ODataValue.class.isAssignableFrom(reference)) {
                res = new ODataPrimitiveValue.Builder().
                        setType(ODataValueFormat.fromString(format) == ODataValueFormat.TEXT
                                ? EdmSimpleType.String : EdmSimpleType.Stream).
                        setText(IOUtils.toString(src)).
                        build();
            } else if (EdmMetadata.class.isAssignableFrom(reference)) {
                res = readMetadata(src);
            } else if (ODataServiceDocument.class.isAssignableFrom(reference)) {
                res = readServiceDocument(src, ODataFormat.fromString(format));
            } else if (ODataError.class.isAssignableFrom(reference)) {
                res = readError(src, !format.toString().contains("json"));
            } else {
                throw new IllegalArgumentException("Invalid reference type " + reference);
            }
        } catch (Exception e) {
            LOG.warn("Cast error", e);
            res = null;
        } finally {
            if (!ODataEntitySetIterator.class.isAssignableFrom(reference)) {
                IOUtils.closeQuietly(src);
            }
        }

        return (T) res;
    }
}
