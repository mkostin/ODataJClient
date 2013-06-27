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
package com.msopentech.odatajclient.spi;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequest;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequest.BatchRequestPayload;
import com.msopentech.odatajclient.engine.communication.request.ODataStreamingManagement;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataChangeset;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataRetrieve;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import org.junit.Test;
import com.msopentech.odatajclient.engine.communication.response.ODataBatchResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import java.io.IOException;
import java.util.concurrent.Future;
import org.junit.Ignore;

public class StreamingTest {

    private static String PREFIX = "!!PREFIX!!";

    private static String SUFFIX = "!!SUFFIX!!";

    private static int MAX = 10000;

    @Test
    @Ignore
    public void stringStreaming() {
        final ODataStreamingMgt streaming = new ODataStreamingMgt();

        new StreamingThread(streaming).start();

        streaming.addObject((PREFIX + "\n").getBytes());

        for (int i = 0; i <= MAX; i++) {
            streaming.addObject((i + ") send info\n").getBytes());
        }

        streaming.addObject((SUFFIX).getBytes());
        streaming.finalizeBody();
    }

    @Test
    @Ignore
    public void batchRequestStreaming() {
        // create your request
        final ODataBatchRequest request =
                ODataBatchRequestFactory.getBatchRequest("http://services.odata.org/OData/Odata.svc");

        final ODataBatchRequest.BatchRequestPayload payload = request.execute();

        new BatchStreamingThread(payload).start();

        // -------------------------------------------
        // Add retrieve item
        // -------------------------------------------
        ODataRetrieve retrieve = payload.addRetrieve();

        // prepare URI
        ODataURIBuilder uri = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");
        uri.appendEntityTypeSegment("Products(0)").expand("Supplier").select("Rating,Supplier/Name");

        // create new request
        ODataEntityRequest query = ODataRetrieveRequestFactory.getEntityRequest(uri.build());
        query.setDataServiceVersion("2.0");
        query.setMaxDataServiceVersion("3.0");
        query.setFormat(ODataFormat.ATOM);

        retrieve.setRequest(query);
        // -------------------------------------------

        // -------------------------------------------
        // Add changeset item
        // -------------------------------------------
        final ODataChangeset changeset = payload.addChangeset();

        // add several request into the changeset
        for (int i = 0; i < 2; i++) {
            // provide the target URI
            final ODataURIBuilder targetURI = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");
            targetURI.appendEntityTypeSegment("Products(2)");

            // build the new object to change Rating value
            final ODataEntity changes = ODataFactory.newEntity("Java Code");
            changes.addProperty(new ODataProperty("Rating",
                    new ODataPrimitiveValue.Builder().setText("3").setType(EdmSimpleType.INT_32).build()));

            // create your request
            final ODataEntityUpdateRequest change =
                    ODataCUDRequestFactory.getEntityUpdateRequest(targetURI.build(), UpdateType.PATCH, changes);

            change.setFormat(ODataFormat.JSON);

            changeset.addRequest(change);
        }
        // -------------------------------------------

        // -------------------------------------------
        // Add retrieve item
        // -------------------------------------------
        retrieve = payload.addRetrieve();

        // prepare URI
        uri = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");
        uri.appendEntityTypeSegment("Products(2)");

        // create new request
        query = ODataRetrieveRequestFactory.getEntityRequest(uri.build());
        query.setDataServiceVersion("2.0");

        retrieve.setRequest(query);
        // -------------------------------------------

        final ODataBatchResponse response = payload.getResponse();
    }

    private static class ODataStreamingMgt extends ODataStreamingManagement<ODataBatchResponse> {

        public ODataStreamingMgt() {
            super();
        }

        public ODataStreamingManagement<ODataBatchResponse> addObject(byte[] src) {
            stream(src);
            return this;
        }

        @Override
        public ODataBatchResponse getResponse() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Future<ODataBatchResponse> asyncResponse() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };

    private static class StreamingThread extends Thread {

        private final ODataStreamingMgt streaming;

        public StreamingThread(ODataStreamingMgt streaming) {
            this.streaming = streaming;
        }

        @Override
        public void run() {
            try {
                final StringBuilder builder = new StringBuilder();

                byte[] buff = new byte[1024];

                int len;

                while ((len = streaming.getBody().read(buff)) >= 0) {
                    builder.append(new String(buff, 0, len));
                }

                assertTrue(builder.toString().startsWith(PREFIX));
                assertTrue(builder.toString().contains((MAX / 2) + ") send info"));
                assertTrue(builder.toString().contains((MAX / 3) + ") send info"));
                assertTrue(builder.toString().contains((MAX / 20) + ") send info"));
                assertTrue(builder.toString().contains((MAX / 30) + ") send info"));
                assertTrue(builder.toString().contains(MAX + ") send info"));
                assertTrue(builder.toString().endsWith(SUFFIX));

            } catch (IOException e) {
                fail();
            }
        }
    }

    private static class BatchStreamingThread extends Thread {

        private final BatchRequestPayload streaming;

        public BatchStreamingThread(BatchRequestPayload streaming) {
            this.streaming = streaming;
        }

        @Override
        public void run() {
            try {
                final StringBuilder builder = new StringBuilder();

                byte[] buff = new byte[1024];

                int len;

                while ((len = streaming.getBody().read(buff)) >= 0) {
                    builder.append(new String(buff, 0, len));
                }

                assertTrue(builder.toString().contains("Content-Id:2"));
                assertTrue(builder.toString().contains("GET http://services.odata.org/OData/Odata.svc"));

            } catch (IOException e) {
                fail();
            }
        }
    }
}
