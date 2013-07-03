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

import com.msopentech.odatajclient.engine.client.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.ODataStreamer;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequest.BatchRequestPayload;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataStreamedRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataStreamingManagement;
import com.msopentech.odatajclient.engine.communication.response.ODataBatchChangesetResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataBatchResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataBatchResponseItem;
import com.msopentech.odatajclient.engine.communication.response.ODataBatchRetrieveResponse;
import com.msopentech.odatajclient.engine.utils.ODataBatchConstants;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements a batch request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getBatchRequest().
 */
public class ODataBatchRequest extends ODataStreamedRequestImpl<ODataBatchResponse, BatchRequestPayload> {

    /**
     * Batch request boundary.
     */
    private final String boundary;

    private final List<ODataBatchResponseItem> expectedResItems = new ArrayList<ODataBatchResponseItem>();

    /**
     * Constructor.
     *
     * @param uri batch request URI (http://serviceRoot/$batch)
     */
    ODataBatchRequest(final URI uri) {
        super(Method.POST, uri);

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

            final ODataBatchChangesetResponse expectedResItem = new ODataBatchChangesetResponse();
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

            final ODataBatchRetrieveResponse expectedResItem = new ODataBatchRetrieveResponse();
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
    private static class ODataBatchResponseImpl extends ODataResponseImpl implements ODataBatchResponse {

        private enum BatchStatus {

            NONE,
            START,
            END,
            RETRIEVE_ITEM,
            CHANGESET_ITEM,
            CLOSEITEM,
            RETRIEVE,
            CHANGESET,
            REQUEST,
            HEADER

        };

        /**
         * Batch request content.
         */
        private final List<ODataBatchResponseItem> batch = new ArrayList<ODataBatchResponseItem>();

        private ODataBatchResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);

            // create the batch response ....

        }

        /**
         * Adds a query response to the batch response.
         * <p>
         * Each query response is about a query request submitted embedded into a batch request.
         *
         * @param response query response to be added.
         * @return the current batch response.
         *
         * @see ODataRetrieveResponse
         */
        private ODataBatchResponseImpl addItem(final ODataBatchRetrieveResponse response) {
            batch.add(response);
            return this;
        }

        /**
         * Add a changeset to the batch response.
         *
         * @param item changeset to be added.
         * @return the current batch response.
         */
        private ODataBatchResponseImpl addResponse(final ODataBatchChangesetResponse item) {
            batch.add(item);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<ODataBatchResponseItem> getBody() {
            try {
                BatchStatus status = BatchStatus.NONE;

                String startBoundary = null;
                String endBoundary = null;
                boolean isChangeset = false;

                final LineIterator lineIterator = IOUtils.lineIterator(getRawResponse(), "UTF-8");

                final Map<String, String> headers = new HashMap<String, String>();
                while (lineIterator.hasNext()) {
                    final String line = lineIterator.nextLine();
                    switch (status) {
                        case NONE:
                            startBoundary = line;
                            endBoundary = line + "--";
                            status = BatchStatus.START;
                            break;
                        case START:
                            // do nothing (but collect headers) till CRLF
                            if (Arrays.equals(line.getBytes(), ODataStreamer.CRLF)) {
                                if (headers.get("content-type").equals("application/http")) {
                                    status = BatchStatus.REQUEST;
                                } else {
                                    status = BatchStatus.CHANGESET;
                                }
                                headers.clear();
                            } else {
                                int sep = line.indexOf(":");
                                if (sep > 0 && sep < line.length() - 1) {
                                    headers.put(
                                            line.substring(0, sep).trim().toLowerCase(),
                                            line.substring(sep + 1, line.length()).trim().toLowerCase());
                                }
                            }
                            break;
                        case REQUEST:
                            // parse the request "<protocol> <status code> <status msg>"

                            // create and add the new response item

                            // expected info
                            status = BatchStatus.HEADER;
                            break;
                        case HEADER:
                        // parse header
                        case RETRIEVE_ITEM:
                            if (endBoundary.equals(line)) {
                                // end of batch
                                status = BatchStatus.END;
                            } else if (startBoundary.equals(line)) {
                                // start new item
                                status = BatchStatus.START;
                            } else if (line.startsWith("--")) {
                                // found 
                                status = BatchStatus.CHANGESET;
                            } else {
                            }
                            break;
                        case CHANGESET:
                            break;
                        case END:
                        default:
                    }

                    if (startBoundary == null) {
                        // found open boundary tag
                        startBoundary = line;
                    } else {
                        // process batch
                        if ((startBoundary + "--").equals(line)) {
                            // found close boundary tag
                        } else if (startBoundary.equals(startBoundary)) {
                            // found new item boundary tag
                        } else {
                            // process item
                        }
                    }
                }

                return batch.iterator();
            } catch (IOException e) {
                LOG.error("Error parsing batch response", e);
                throw new IllegalStateException(e);
            }
        }
    }
}
