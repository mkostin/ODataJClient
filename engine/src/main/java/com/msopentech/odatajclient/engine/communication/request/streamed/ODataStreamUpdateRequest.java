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
package com.msopentech.odatajclient.engine.communication.request.streamed;

import com.msopentech.odatajclient.engine.client.http.HttpMethod;
import com.msopentech.odatajclient.engine.communication.request.ODataStreamManager;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataStreamUpdateRequest.StreamUpdateStreamManager;
import com.msopentech.odatajclient.engine.communication.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.communication.response.ODataStreamUpdateResponse;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData stream create/update request.
 * Get instance by using ODataStreamedRequestFactory.
 *
 * @see ODataStreamedRequestFactory#getStreamUpdateRequest(java.net.URI, java.io.InputStream)
 */
public class ODataStreamUpdateRequest
        extends ODataStreamedRequestImpl<ODataStreamUpdateResponse, StreamUpdateStreamManager>
        implements ODataBatchableRequest {

    private final InputStream stream;

    /**
     * Constructor.
     *
     * @param method request method.
     * @param targetURI target URI.
     * @param stream stream to be updated.
     */
    ODataStreamUpdateRequest(final HttpMethod method, final URI targetURI, final InputStream stream) {
        super(method, targetURI);
        this.stream = stream;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected StreamUpdateStreamManager getStreamManager() {
        if (streamManager == null) {
            streamManager = new StreamUpdateStreamManager(this.stream);
        }

        return (StreamUpdateStreamManager) streamManager;
    }

    public class StreamUpdateStreamManager extends ODataStreamManager<ODataStreamUpdateResponse> {

        /**
         * Private constructor.
         *
         * @param input payload input stream.
         */
        private StreamUpdateStreamManager(final InputStream input) {
            super(ODataStreamUpdateRequest.this.futureWrapper, input);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        protected ODataStreamUpdateResponse getResponse(final long timeout, final TimeUnit unit) {
            finalizeBody();
            return new ODataStreamUpdateResponseImpl(client, getHttpResponse(timeout, unit));
        }
    }

    /**
     * Response class about an ODataStreamUpdateRequest.
     */
    private class ODataStreamUpdateResponseImpl extends ODataResponseImpl implements ODataStreamUpdateResponse {

        private InputStream input = null;

        /**
         * Constructor.
         * <p>
         * Just to create response templates to be initialized from batch.
         */
        private ODataStreamUpdateResponseImpl() {
        }

        /**
         * Constructor.
         *
         * @param client HTTP client.
         * @param res HTTP response.
         */
        private ODataStreamUpdateResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public InputStream getBody() {
            if (input == null) {
                try {
                    input = getRawResponse();
                } finally {
                    this.close();
                }
            }
            return input;
        }
    }
}
