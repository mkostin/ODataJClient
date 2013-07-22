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
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.io.InputStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
     * De-Serializes a stream into an OData entity set.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as AtomFeed or JSONFeed
     * @return de-serialized entity set.
     */
    public static ODataEntitySet readEntitySet(final InputStream input, final ODataPubFormat format) {
        return ODataBinder.getODataEntitySet(Deserializer.toFeed(input, ResourceFactory.feedClassForFormat(format)));
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
     * Parses a stream taking care to de-serialize the first OData entity property found.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as XML or JSON
     * @return OData entity property de-serialized.
     */
    public static ODataProperty readProperty(final InputStream input, final ODataFormat format) {
        final Element property = Deserializer.toDOM(input, format);

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
        final String type = property.getAttribute(ODataConstants.ATTR_TYPE);
        final NodeList elements = property.getElementsByTagName(ODataConstants.ELEM_ELEMENT);
        if (StringUtils.isBlank(type) && elements != null && elements.getLength() > 0) {
            final Node elementType = elements.item(0).getAttributes().getNamedItem(ODataConstants.ATTR_TYPE);
            if (elementType != null) {
                property.setAttribute(ODataConstants.ATTR_TYPE, "Collection(" + elementType.getTextContent() + ")");
            }
        }

        return ODataBinder.getProperty(property);
    }

    /**
     * Parses a $links request response.
     *
     * @param input stream to de-serialize.
     * @param format de-serialize as XML or JSON
     * @return List of URIs.
     */
    public static ODataLinkCollection readLinks(final InputStream input, final ODataFormat format) {
        return ODataBinder.getLinkCollection(Deserializer.toLinkCollection(input, format));
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
    public static EdmMetadata readMetadata(final InputStream input) {
        return new EdmMetadata(input);
    }

    /**
     * Parses a stream into an OData error.
     *
     * @param inputStream stream to de-serialize.
     * @param isXML 'TRUE' if the error is in XML format.
     * @return OData error.
     */
    public static ODataError readError(final InputStream inputStream, final boolean isXML) {
        return Deserializer.toODataError(inputStream, isXML);
    }
}
