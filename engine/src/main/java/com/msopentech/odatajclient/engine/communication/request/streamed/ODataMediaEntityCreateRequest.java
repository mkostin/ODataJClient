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

import com.msopentech.odatajclient.engine.communication.request.ODataStreamManager;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataMediaEntityCreateRequest.MediaEntityCreateStreamManager;
import com.msopentech.odatajclient.engine.communication.response.ODataMediaEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataReader;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData Media Entity create request.
 * Get instance by using ODataStreamedRequestFactory.
 *
 * @see ODataStreamedRequestFactory#getMediaEntityCreateRequest(java.net.URI, java.io.InputStream)
 */
public class ODataMediaEntityCreateRequest
        extends ODataStreamedEntityRequestImpl<ODataMediaEntityCreateResponse, MediaEntityCreateStreamManager>
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
    protected MediaEntityCreateStreamManager getStreamManager() {
        if (streamManager == null) {
            streamManager = new MediaEntityCreateStreamManager(media);
        }
        return (MediaEntityCreateStreamManager) streamManager;
    }

    /**
     * Media entity payload object.
     */
    public class MediaEntityCreateStreamManager extends ODataStreamManager<ODataMediaEntityCreateResponse> {

        /**
         * Private constructor.
         *
         * @param input media stream.
         */
        private MediaEntityCreateStreamManager(final InputStream input) {
            super(ODataMediaEntityCreateRequest.this.futureWrapper, input);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        protected ODataMediaEntityCreateResponse getResponse(final long timeout, final TimeUnit unit) {
            finalizeBody();
            return new ODataMediaEntityCreateResponseImpl(client, getHttpResponse(timeout, unit));
        }
    }

    /**
     * Response class about an ODataMediaEntityCreateRequest.
     */
    private class ODataMediaEntityCreateResponseImpl extends ODataResponseImpl
            implements ODataMediaEntityCreateResponse {

        private ODataEntity entity = null;

        /**
         * Constructor.
         * <p>
         * Just to create response templates to be initialized from batch.
         */
        private ODataMediaEntityCreateResponseImpl() {
        }

        /**
         * Constructor.
         *
         * @param client HTTP client.
         * @param res HTTP response.
         */
        private ODataMediaEntityCreateResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        /**
         * {@inheritDoc }
         */
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
