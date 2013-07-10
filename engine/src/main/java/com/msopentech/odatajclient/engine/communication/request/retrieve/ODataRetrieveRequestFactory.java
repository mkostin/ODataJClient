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
import org.apache.http.entity.ContentType;

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
        final ODataServiceDocumentRequest req = new ODataServiceDocumentRequest();
        req.setURI(URI.create(serviceRoot));
        return req;
    }

    /**
     * Gets a metadata request instance.
     *
     * @param serviceRoot absolute URL (schema, host and port included) representing the location of the root of the
     * data service.
     * @return new ODataMetadataRequest instance.
     */
    public static ODataMetadataRequest getMetadataRequest(final String serviceRoot) {
        final ODataMetadataRequest req = new ODataMetadataRequest();
        req.setURI(new ODataURIBuilder(serviceRoot).appendMetadataSegment().build());
        req.setAccept(ContentType.APPLICATION_XML.getMimeType());
        req.setContentType(ContentType.APPLICATION_XML.getMimeType());
        return req;
    }

    /**
     * Gets a query request returning a set of one or more OData entity.
     *
     * @param query query to be performed.
     * @return new ODataEntitySetRequest instance.
     */
    public static ODataEntitySetRequest getEntitySetRequest(final URI query) {
        final ODataEntitySetRequest req = new ODataEntitySetRequest();
        req.setURI(query);
        return req;
    }

    /**
     * Gets a query request returning a single OData entity.
     *
     * @param query query to be performed.
     * @return new ODataEntityRequest instance.
     */
    public static ODataEntityRequest getEntityRequest(final URI query) {
        final ODataEntityRequest req = new ODataEntityRequest();
        req.setURI(query);
        return req;
    }

    /**
     * Gets a query request returning a single OData entity property.
     *
     * @param query query to be performed.
     * @return new ODataPropertyRequest instance.
     */
    public static ODataPropertyRequest getPropertyRequest(final URI query) {
        final ODataPropertyRequest req = new ODataPropertyRequest();
        req.setURI(query);
        return req;
    }

    /**
     * Gets a query request returning a single OData entity property value.
     *
     * @param query query to be performed.
     * @return new ODataValueRequest instance.
     */
    public static ODataValueRequest getValueRequest(final URI query) {
        final ODataValueRequest req = new ODataValueRequest();
        req.setURI(query);
        return req;
    }

    /**
     * Gets a query request returning a single OData link.
     *
     * @param query query to be performed.
     * @return new ODataLinkRequest instance.
     */
    public static ODataLinkRequest getLinkRequest(final URI query, final String linkName) {
        final ODataLinkRequest req = new ODataLinkRequest();
        req.setURI(new ODataURIBuilder(query.toASCIIString()).appendLinksSegment(linkName).build());
        return req;
    }

    /**
     * Gets a query request returning a media stream.
     *
     * @param query query to be performed.
     * @return new ODataMediaRequest instance.
     */
    public static ODataMediaRequest getMediaRequest(final URI query) {
        final ODataMediaRequest req = new ODataMediaRequest();
        req.setURI(query);
        return req;
    }
}
