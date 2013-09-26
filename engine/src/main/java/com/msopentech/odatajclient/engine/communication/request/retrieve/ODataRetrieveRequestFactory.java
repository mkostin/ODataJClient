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
package com.msopentech.odatajclient.engine.communication.request.retrieve;

import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import java.net.URI;
import org.apache.commons.lang3.StringUtils;

/**
 * OData request factory class.
 */
public final class ODataRetrieveRequestFactory {

    private ODataRetrieveRequestFactory() {
        // Empty private constructor for static utility classes
    }

    /**
     * Gets a service document request instance.
     *
     * @param serviceRoot absolute URL (schema, host and port included) representing the location of the root of the
     * data service.
     * @return new ODataServiceDocumentRequest instance.
     */
    public static ODataServiceDocumentRequest getServiceDocumentRequest(final String serviceRoot) {
        return new ODataServiceDocumentRequest(StringUtils.isNotBlank(serviceRoot) && serviceRoot.endsWith("/")
                ? new ODataURIBuilder(serviceRoot).build() : new ODataURIBuilder(serviceRoot + "/").build());
    }

    /**
     * Gets a metadata request instance.
     *
     * @param serviceRoot absolute URL (schema, host and port included) representing the location of the root of the
     * data service.
     * @return new ODataMetadataRequest instance.
     */
    public static ODataMetadataRequest getMetadataRequest(final String serviceRoot) {
        return new ODataMetadataRequest(new ODataURIBuilder(serviceRoot).appendMetadataSegment().build());
    }

    /**
     * Gets a query request returning a set of one or more OData entities.
     *
     * @param query query to be performed.
     * @return new ODataEntitySetRequest instance.
     */
    public static ODataEntitySetRequest getEntitySetRequest(final URI query) {
        return new ODataEntitySetRequest(query);
    }

    /**
     * Gets a query request returning a set of one or more OData entities.
     * <p>
     * Returned request gives the possibility to consume entities iterating on them without parsing and loading in
     * memory the entire entity set.
     *
     * @param query query to be performed.
     * @return new ODataEntitySetIteratorRequest instance.
     */
    public static ODataEntitySetIteratorRequest getEntitySetIteratorRequest(final URI query) {
        return new ODataEntitySetIteratorRequest(query);
    }

    /**
     * Gets a query request returning a single OData entity.
     *
     * @param query query to be performed.
     * @return new ODataEntityRequest instance.
     */
    public static ODataEntityRequest getEntityRequest(final URI query) {
        return new ODataEntityRequest(query);
    }

    /**
     * Gets a query request returning a single OData entity property.
     *
     * @param query query to be performed.
     * @return new ODataPropertyRequest instance.
     */
    public static ODataPropertyRequest getPropertyRequest(final URI query) {
        return new ODataPropertyRequest(query);
    }

    /**
     * Gets a query request returning a single OData entity property value.
     *
     * @param query query to be performed.
     * @return new ODataValueRequest instance.
     */
    public static ODataValueRequest getValueRequest(final URI query) {
        return new ODataValueRequest(query);
    }

    /**
     * Gets a query request returning a single OData link.
     *
     * @param targetURI target URI.
     * @param linkName link name.
     * @return new ODataLinkRequest instance.
     */
    public static ODataLinkCollectionRequest getLinkCollectionRequest(final URI targetURI, final String linkName) {
        return new ODataLinkCollectionRequest(targetURI, linkName);
    }

    /**
     * Gets a query request returning a media stream.
     *
     * @param query query to be performed.
     * @return new ODataMediaRequest instance.
     */
    public static ODataMediaRequest getMediaRequest(final URI query) {
        return new ODataMediaRequest(query);
    }

    /**
     * Implements a raw request returning a stream.
     *
     * @param uri query to be performed.
     * @return new ODataRawRequest instance.
     */
    public static ODataRawRequest getRawRequest(final URI uri) {
        return new ODataRawRequest(uri);
    }

    /**
     * Implements a generic retrieve request without specifying any return type.
     *
     * @param uri query to be performed.
     * @return new ODataGenericRerieveRequest instance.
     */
    public static ODataGenericRetrieveRequest getGenericRetrieveRequest(final URI uri) {
        return new ODataGenericRetrieveRequest(uri);
    }
}
