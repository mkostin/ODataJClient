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

import com.msopentech.odatajclient.engine.communication.request.ODataBatchRequest.BatchRequestPayload;
import com.msopentech.odatajclient.engine.communication.response.ODataBatchResponse;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * This class implements a batch request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getBatchRequest().
 */
public class ODataBatchRequest extends ODataStreamedRequestImpl<ODataBatchResponse, BatchRequestPayload> {

    private static String CONTENT_TYPE = "multipart/mixed";

    private final String boundary;

    /**
     * Constructor.
     *
     * @param uri batch request URI (http://serviceRoot/$batch)
     */
    ODataBatchRequest(final URI uri) {
        super(Method.POST);

        // create a random UUID value for boundary
        boundary = "batch_" + UUID.randomUUID().toString();

        // specify the contentType header
        setContentType(CONTENT_TYPE + ";boundary=" + boundary);

        this.uri = uri;
    }

    /**
     * Execute the request.
     *
     * @return request payload management to be used to add batch part to be streamed on the HTTP channel.
     */
    @Override
    public BatchRequestPayload execute() {
        final BatchRequestPayload payload = new BatchRequestPayload();
        // perform HTTP connection passing input stream from BatchRequestPayload

        return payload;
    }

    /**
     * Batch request payload management.
     */
    public class BatchRequestPayload extends ODataStreamingManagement<ODataBatchResponse> {

        /**
         * Batch request current item.
         */
        private ODataBatchRequestItem currentItem = null;

        /**
         * Private constructor.
         */
        private BatchRequestPayload() {
        }

        /**
         * Gets a changeset batch item instance.
         * A changeset can be submitted embedded into a batch request only.
         *
         * @return ODataChangeset instance.
         */
        public ODataChangeset addChangeset() {
            closeCurrentItem();

            // stream dash boundary
            streamDashBoundary();

            currentItem = new ODataChangeset(bodyStreamWriter);
            return (ODataChangeset) currentItem;
        }

        /**
         * Gets a retrieve batch item instance.
         * A retrieve item can be submitted embedded into a batch request only.
         *
         * @return ODataRetrieve instance.
         */
        public ODataRetrieve addRetrieve() {
            closeCurrentItem();

            // stream dash boundary
            streamDashBoundary();

            currentItem = new ODataRetrieve(bodyStreamWriter);
            return (ODataRetrieve) currentItem;
        }

        /**
         * Close the current streamed item.
         */
        private void closeCurrentItem() {
            if (currentItem != null) {
                currentItem.close();
            }
        }

        /**
         * Closes the stream and returns OData response.
         *
         * @return batch response.
         */
        @Override
        public ODataBatchResponse getResponse() {
            closeCurrentItem();

            streamCloseDelimiter();

            try {
                finalizeBody();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

            return null;
        }

        /**
         * Closes the stream and asks for an asynchronous response.
         *
         * @return Future object of the async batch response.
         */
        @Override
        public Future<ODataBatchResponse> asyncResponse() {
            closeCurrentItem();

            streamCloseDelimiter();

            try {
                finalizeBody();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

            return null;
        }

        private void streamDashBoundary() {
            // preamble
            newLine();

            // stream batch-boundary
            stream(("--" + boundary).getBytes());
            newLine();
        }

        private void streamCloseDelimiter() {
            // stream close-delimiter
            newLine();
            stream(("--" + boundary + "--").getBytes());
        }
    }
}
