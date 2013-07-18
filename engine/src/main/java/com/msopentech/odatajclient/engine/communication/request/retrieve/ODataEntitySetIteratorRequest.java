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
import com.msopentech.odatajclient.engine.data.ODataEntitySetIterator;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData EntitySet query request.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getEntitySetRequest(java.net.URI)
 */
public class ODataEntitySetIteratorRequest extends ODataRetrieveRequest<ODataEntitySetIterator, ODataPubFormat> {

    private ODataEntitySetIterator feedIterator = null;

    /**
     * Private constructor.
     *
     * @param query query to be executed.
     */
    ODataEntitySetIteratorRequest(final URI query) {
        super(query);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataRetrieveResponse<ODataEntitySetIterator> execute() {
        final HttpResponse res = doExecute();
        return new ODataEntitySetIteratorResponseImpl(client, res);
    }

    /**
     * Response class about an ODataEntitySetIteratorRequest.
     */
    protected class ODataEntitySetIteratorResponseImpl extends ODataRetrieveResponseImpl {

        /**
         * Constructor.
         *
         * @param client HTTP client.
         * @param res HTTP response.
         */
        private ODataEntitySetIteratorResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        @SuppressWarnings("unchecked")
        public ODataEntitySetIterator getBody() {
            if (feedIterator == null) {
                feedIterator = new ODataEntitySetIterator(getRawResponse(), ODataPubFormat.valueOf(getFormat()));
            }
            return feedIterator;
        }
    }
}