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

import com.msopentech.odatajclient.engine.client.ODataClient;
import com.msopentech.odatajclient.engine.communication.request.ODataRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;

/**
 * This class implements a metadata query request.
 */
public class ODataMetadataRequest extends AbstractODataRetrieveRequest<EdmMetadata, ODataPubFormat> {

    /**
     * Constructor.
     *
     * @param odataClient client instance getting this request
     * @param uri metadata URI.
     */
    ODataMetadataRequest(final ODataClient odataClient, final URI uri) {
        super(odataClient, ODataPubFormat.class, uri);
        super.setAccept(ContentType.APPLICATION_XML.getMimeType());
        super.setContentType(ContentType.APPLICATION_XML.getMimeType());
    }

    @Override
    public ODataRequest setAccept(final String value) {
        // do nothing: Accept is application/XML
        return this;
    }

    @Override
    public ODataRequest setContentType(final String value) {
        // do nothing: Accept is application/XML
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataRetrieveResponse<EdmMetadata> execute() {
        final HttpResponse res = doExecute();
        return new ODataMetadataResponsImpl(httpClient, res);
    }

    /**
     * Response class about an ODataMetadataRequest.
     */
    protected class ODataMetadataResponsImpl extends ODataRetrieveResponseImpl {

        private EdmMetadata metadata = null;

        /**
         * Constructor.
         * <p>
         * Just to create response templates to be initialized from batch.
         */
        public ODataMetadataResponsImpl() {
        }

        /**
         * Constructor.
         *
         * @param client HTTP client.
         * @param res HTTP response.
         */
        private ODataMetadataResponsImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public EdmMetadata getBody() {
            if (metadata == null) {
                try {
                    metadata = odataClient.getODataReader().readMetadata(getRawResponse());
                } finally {
                    this.close();
                }
            }
            return metadata;
        }
    }
}
