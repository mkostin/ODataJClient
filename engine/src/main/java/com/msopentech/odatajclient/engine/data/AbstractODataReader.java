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

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.msopentech.odatajclient.engine.client.ODataClient;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.format.ODataValueFormat;
import com.msopentech.odatajclient.engine.utils.ODataConstants;

public abstract class AbstractODataReader implements ODataReader {

    private static final long serialVersionUID = -1988865870981207079L;

    /**
     * Logger.
     */
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractODataReader.class);

    protected final ODataClient client;

    protected AbstractODataReader(final ODataClient client) {
        this.client = client;
    }

    @Override
    public ODataEntitySet readEntitySet(final InputStream input, final ODataPubFormat format) {
        return client.getODataBinder().getODataEntitySet(
                client.getDeserializer().toFeed(input, client.getResourceFactory().feedClassForFormat(format)));
    }

    @Override
    public ODataEntity readEntity(final InputStream input, final ODataPubFormat format) {
        return client.getODataBinder().getODataEntity(
                client.getDeserializer().toEntry(input, client.getResourceFactory().entryClassForFormat(format)));
    }

    @Override
    public ODataProperty readProperty(final InputStream input, final ODataFormat format) {
        final Element property = client.getDeserializer().toPropertyDOM(input, format);

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

    @Override
    public ODataLinkCollection readLinks(final InputStream input, final ODataFormat format) {
        return client.getODataBinder().getLinkCollection(
                client.getDeserializer().toLinkCollection(input, format));
    }

    @Override
    public ODataServiceDocument readServiceDocument(final InputStream input, final ODataFormat format) {
        return client.getODataBinder().getODataServiceDocument(
                client.getDeserializer().toServiceDocument(input, format));
    }

    @Override
    public EdmMetadata readMetadata(final InputStream input) {
        return new EdmMetadata(client, input);
    }

    @Override
    public ODataError readError(final InputStream inputStream, final boolean isXML) {
        return client.getDeserializer().toODataError(inputStream, isXML);
    }

    @Override
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
                res = new ODataPrimitiveValue.Builder(client).
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
