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

import com.msopentech.odatajclient.engine.communication.request.ODataStreamingManagement;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataMediaEntityCreateRequest.MediaEntityCreateRequestPayload;
import com.msopentech.odatajclient.engine.communication.response.ODataMediaEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataReader;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.Future;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData Media Entity create request.
 * Get instance by using ODataCUDRequestFactory.
 *
 * @see ODataCUDRequestFactory#getMediaEntityCreateRequest(java.net.URI, java.io.InputStream)
 */
public class ODataMediaEntityCreateRequest
        extends ODataStreamedEntityRequestImpl<ODataMediaEntityCreateResponse, MediaEntityCreateRequestPayload>
        implements ODataBatchableRequest {

    private final InputStream media;

    /**
     * Constructor.
     *
     * @param targetURI target entity set.
     * @param media media entity blob to be created.
     */
    ODataMediaEntityCreateRequest(final URI targetURI, final InputStream media) {
        super(Method.POST, targetURI);
        this.media = media;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected MediaEntityCreateRequestPayload getPayload() {
        if (payload == null) {
            payload = new MediaEntityCreateRequestPayload(media);
        }
        return (MediaEntityCreateRequestPayload) payload;
    }

    /**
     * Media entity payload object.
     */
    public class MediaEntityCreateRequestPayload extends ODataStreamingManagement<ODataMediaEntityCreateResponse> {

        /**
         * Private constructor.
         *
         * @param is media stream.
         */
        private MediaEntityCreateRequestPayload(final InputStream is) {
            super(is);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public ODataMediaEntityCreateResponse getResponse() {
            finalizeBody();
            return new ODataMediaEntityCreateResponseImpl(client, ODataMediaEntityCreateRequest.this.getResponse());
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public Future<ODataMediaEntityCreateResponse> asyncResponse() {
            finalizeBody();
            return null;
        }
    }

    private class ODataMediaEntityCreateResponseImpl extends ODataResponseImpl
            implements ODataMediaEntityCreateResponse {

        private ODataEntity entity = null;

        private ODataMediaEntityCreateResponseImpl() {
        }

        private ODataMediaEntityCreateResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        @Override
        public ODataEntity getBody() {
            if (entity == null) {
                try {
                    entity = ODataReader.readEntity(getRawResponse(), getFormat());
                } finally {
                    this.close();
                }
            }
            return entity;
        }
    }
}
