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
import com.msopentech.odatajclient.engine.communication.request.cud.ODataMediaEntityUpdateRequest.MediaEntityUpdateRequestPayload;
import com.msopentech.odatajclient.engine.communication.request.ODataStreamingManagement;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataMediaEntityUpdateResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataReader;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.Future;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData Media Entity create request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getMediaEntityUpdateRequest(com.msopentech.odatajclient.engine.data.ODataURI,
 * java.io.InputStream)
 */
public class ODataMediaEntityUpdateRequest
        extends ODataStreamedEntityRequestImpl<ODataMediaEntityUpdateResponse, MediaEntityUpdateRequestPayload>
        implements ODataBatchableRequest {

    private final InputStream media;

    /**
     * Constructor.
     *
     * @param editURI edit URI of the entity to be updated.
     * @param media media entity blob to be created.
     */
    ODataMediaEntityUpdateRequest(final URI editURI, final InputStream media) {
        super(Method.PUT, editURI);
        this.media = media;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected MediaEntityUpdateRequestPayload getPayload() {
        if (payload == null) {
            payload = new MediaEntityUpdateRequestPayload(media);
        }
        return (MediaEntityUpdateRequestPayload) payload;
    }

    /**
     * Media entity payload object.
     */
    public class MediaEntityUpdateRequestPayload extends ODataStreamingManagement<ODataMediaEntityUpdateResponse> {

        /**
         * Private constructor.
         *
         * @param is media stream.
         */
        private MediaEntityUpdateRequestPayload(final InputStream is) {
            super(is);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public ODataMediaEntityUpdateResponse getResponse() {
            finalizeBody();
            return new ODataMediaEntityUpdateResponseImpl(client, ODataMediaEntityUpdateRequest.this.getResponse());
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public Future<ODataMediaEntityUpdateResponse> asyncResponse() {
            finalizeBody();
            return null;
        }
    }

    private class ODataMediaEntityUpdateResponseImpl extends ODataResponseImpl
            implements ODataMediaEntityUpdateResponse {

        private ODataEntity entity = null;

        private ODataMediaEntityUpdateResponseImpl() {
        }

        private ODataMediaEntityUpdateResponseImpl(final HttpClient client, final HttpResponse res) {
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
