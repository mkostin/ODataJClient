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
package com.msopentech.odatajclient.engine.communication.request.retrieve;

import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import java.net.URI;
import javax.ws.rs.core.MediaType;
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
        final ODataMetadataRequest request =
                new ODataMetadataRequest(new ODataURIBuilder(serviceRoot).appendMetadataSegment().build());
        request.setContentType(MediaType.APPLICATION_XML);
        return request;
    }

    /**
     * Gets a query request returning a set of one or more OData entity.
     *
     * @param query query to be performed.
     * @return new ODataEntitySetRequest instance.
     */
    public static ODataEntitySetRequest getEntitySetRequest(final URI query) {
        return new ODataEntitySetRequest(query);
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
     * @param query query to be performed.
     * @return new ODataLinkRequest instance.
     */
    public static ODataLinkRequest getLinkRequest(final URI query, final String linkName) {
        return new ODataLinkRequest(query, linkName);
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
     * @param query query to be performed.
     * @return new ODataRawRetrieveRequest instance.
     */
    public static ODataRawRequest getRawRetrieveRequest(final URI uri) {
        return new ODataRawRequest(uri);

    }
}
