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
package com.msopentech.odatajclient.engine.communication.request.batch;

import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequest.BatchRequestPayload;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataStreamedRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataStreamingManagement;
import com.msopentech.odatajclient.engine.communication.response.ODataBatchResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.utils.ODataBatchConstants;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements a batch request.
 * Get instance by using ODataBatchRequestFactory.
 *
 * @see ODataBatchRequestFactory#getBatchRequest(java.lang.String)
 */
public class ODataBatchRequest extends ODataStreamedRequestImpl<ODataBatchResponse, BatchRequestPayload> {

    /**
     * Batch request boundary.
     */
    private final String boundary;

    private final List<ODataBatchResponseItem> expectedResItems = new ArrayList<ODataBatchResponseItem>();

    /**
     * Constructor.
     */
    ODataBatchRequest() {
        super(Method.POST);

        // create a random UUID value for boundary
        boundary = "batch_" + UUID.randomUUID().toString();

        // specify the contentType header
        setContentType(ODataBatchConstants.MULTIPART_CONTENT_TYPE + ";" + ODataBatchConstants.BOUNDARY + "=" + boundary);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected BatchRequestPayload getPayload() {
        if (payload == null) {
            payload = new BatchRequestPayload(this);
        }
        return (BatchRequestPayload) payload;
    }

    PipedOutputStream getOutputStream() {
        return getPayload().getBodyStreamWriter();
    }

    public ODataBatchRequest rawAppend(final byte[] toBeStreamed) throws IOException {
        getPayload().getBodyStreamWriter().write(toBeStreamed);
        return this;
    }

    public ODataBatchRequest rawAppend(final byte[] toBeStreamed, int off, int len) throws IOException {
        getPayload().getBodyStreamWriter().write(toBeStreamed, off, len);
        return this;
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
         * batch request reference.
         */
        private final ODataBatchRequest req;

        /**
         * Private constructor.
         *
         * @param req batch request reference.
         */
        private BatchRequestPayload(final ODataBatchRequest req) {
            this.req = req;
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

            final ODataChangesetResponseItem expectedResItem = new ODataChangesetResponseItem();
            expectedResItems.add(expectedResItem);

            currentItem = new ODataChangeset(req, expectedResItem);

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

            final ODataRetrieveResponseItem expectedResItem = new ODataRetrieveResponseItem();
            currentItem = new ODataRetrieve(req, expectedResItem);

            expectedResItems.add(expectedResItem);

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
            finalizeBody();
            return new ODataBatchResponseImpl(client, ODataBatchRequest.this.getResponse());
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
            finalizeBody();
            return null;
        }

        /**
         * Streams dash boundary.
         */
        private void streamDashBoundary() {
            // preamble
            newLine();

            // stream batch-boundary
            stream(("--" + boundary).getBytes());
            newLine();
        }

        /**
         * Streams close delimiter.
         */
        private void streamCloseDelimiter() {
            // stream close-delimiter
            newLine();
            stream(("--" + boundary + "--").getBytes());
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This operation is unsupported by a batch request.
     */
    @Override
    public void batch(ODataBatchRequest req) {
        throw new UnsupportedOperationException("A batch request is not batchable");
    }

    /**
     * This class implements a response to a batch request.
     *
     * @see com.msopentech.odatajclient.engine.communication.request.ODataBatchRequest
     */
    private class ODataBatchResponseImpl extends ODataResponseImpl implements ODataBatchResponse {

        private ODataBatchResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<ODataBatchResponseItem> getBody() {
            return new ODataBatchResponseManager(this, expectedResItems);
        }
    }
}
