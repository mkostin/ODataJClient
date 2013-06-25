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
package com.msopentech.odatajclient.engine.communication.request.cud;

import com.msopentech.odatajclient.engine.client.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataStreamUpdateRequest.StreamUpdateRequestPayload;
import com.msopentech.odatajclient.engine.communication.request.ODataStreamingManagement;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataStreamUpdateResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.Future;
import javax.ws.rs.core.Response;

/**
 * This class implements an OData stream create/update request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getStreamRequest(com.msopentech.odatajclient.engine.data.ODataURI, java.io.InputStream)
 */
public class ODataStreamUpdateRequest
        extends ODataStreamedRequestImpl<ODataStreamUpdateResponse, StreamUpdateRequestPayload>
        implements ODataBatchableRequest {

    private final InputStream stream;

    /**
     * Constructor.
     *
     * @param targetURI target URI.
     * @param stream stream to be updated.
     */
    ODataStreamUpdateRequest(final URI targetURI, final InputStream stream) {
        super(Method.PUT, targetURI);
        this.stream = stream;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected StreamUpdateRequestPayload getPayload() {
        if (payload == null) {
            payload = new StreamUpdateRequestPayload(this.stream);
        }

        return (StreamUpdateRequestPayload) payload;
    }

    public class StreamUpdateRequestPayload extends ODataStreamingManagement<ODataStreamUpdateResponse> {

        /**
         * Private constructor.
         *
         * @param is payload input stream.
         */
        private StreamUpdateRequestPayload(final InputStream is) {
            super(is);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public ODataStreamUpdateResponse getResponse() {
            try {
                finalizeBody();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

            return new ODataStreamUpdateResponseImpl(res);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public Future<ODataStreamUpdateResponse> asyncResponse() {
            try {
                finalizeBody();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

            return null;
        }
    }

    private class ODataStreamUpdateResponseImpl extends ODataResponseImpl implements ODataStreamUpdateResponse {

        private InputStream is = null;

        public ODataStreamUpdateResponseImpl(final Response res) {
            super(res);
        }

        @Override
        public InputStream getBody() {
            if (is == null) {
                try {
                    is = res.readEntity(InputStream.class);
                } finally {
                    res.close();
                }
            }
            return is;
        }
    }
}
