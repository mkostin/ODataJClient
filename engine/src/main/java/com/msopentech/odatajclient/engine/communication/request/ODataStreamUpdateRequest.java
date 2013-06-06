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
package com.msopentech.odatajclient.engine.communication.request;

import com.msopentech.odatajclient.engine.communication.request.ODataStreamUpdateRequest.StreamUpdateRequestPayload;
import com.msopentech.odatajclient.engine.communication.response.ODataStreamUpdateResponse;
import java.io.InputStream;
import java.io.PipedOutputStream;
import java.net.URI;
import java.util.concurrent.Future;

/**
 * This class implements an OData stream create/update request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getStreamRequest(com.msopentech.odatajclient.engine.data.ODataURI, java.io.InputStream)
 */
public class ODataStreamUpdateRequest
        extends ODataStreamedRequestImpl<ODataStreamUpdateResponse, StreamUpdateRequestPayload>
        implements ODataBatchableRequest {

    /**
     * Constructor.
     *
     * @param targetURI target URI.
     * @param stream stream to be updated.
     */
    ODataStreamUpdateRequest(final URI targetURI, final InputStream stream) {
        super(Method.PUT);
        this.uri = targetURI;
    }

    @Override
    public StreamUpdateRequestPayload execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class StreamUpdateRequestPayload extends ODataStreamingManagement<ODataStreamUpdateResponse> {

        public StreamUpdateRequestPayload() {
        }

        public StreamUpdateRequestPayload(PipedOutputStream os) {
            super(os);
        }

        @Override
        public ODataStreamUpdateResponse getResponse() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Future<ODataStreamUpdateResponse> asyncResponse() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
