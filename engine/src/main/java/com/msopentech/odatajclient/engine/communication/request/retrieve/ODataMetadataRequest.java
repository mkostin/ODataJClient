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

import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements a metadata query request.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getMetadataRequest(java.lang.String)
 */
public class ODataMetadataRequest extends ODataRetrieveRequest<EdmMetadata, ODataPubFormat> {

    /**
     * Constructor.
     *
     * @param uri metadata URI.
     */
    ODataMetadataRequest(final URI uri) {
        super(ODataPubFormat.class, uri);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataRetrieveResponse<EdmMetadata> execute() {
        final HttpResponse res = doExecute();
        return new ODataMetadataResponsImpl(client, res);
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
                    metadata = ODataReader.readMetadata(getRawResponse());
                } finally {
                    this.close();
                }
            }
            return metadata;
        }
    }
}
