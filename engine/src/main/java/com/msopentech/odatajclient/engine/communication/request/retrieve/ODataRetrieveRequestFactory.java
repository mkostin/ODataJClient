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

/**
 * OData request factory class.
 */
public class ODataRetrieveRequestFactory {

    /**
     * Gets a document service request instance.
     *
     * @param serviceRoot absolute URL (schema, host and port included) representing the location of the root of the
     * data service.
     * @return new ODataDocumentServiceRequest instance.
     */
    public static ODataDocumentServiceRequest getDocumentServiceRequest(final String serviceRoot) {
        return new ODataDocumentServiceRequest(new ODataURIBuilder(serviceRoot).build());
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
    public static ODataLinkRequest getLinkRequest(final URI query) {
        return new ODataLinkRequest(query);
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
     * Implements a row request returning a stream.
     *
     * @param query query to be performed.
     * @return new ODataRowRetrieveRequest instance.
     */
    public static ODataRowRequest getRowRetrieveRequest(final URI uri) {
        return new ODataRowRequest(uri);

    }
}
