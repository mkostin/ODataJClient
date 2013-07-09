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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequest;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequest.BatchRequestPayload;
import com.msopentech.odatajclient.engine.communication.request.ODataStreamingManagement;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchResponseItem;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataChangeset;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataChangesetResponseItem;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataRetrieve;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataRetrieveResponseItem;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest.ODataEntityResponseImpl;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import org.junit.Test;
import com.msopentech.odatajclient.engine.communication.response.ODataBatchResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityUpdateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.concurrent.Future;

public class StreamingTest extends AbstractTest {

    private static String PREFIX = "!!PREFIX!!";

    private static String SUFFIX = "!!SUFFIX!!";

    private static int MAX = 10000;

    @Test
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
    public void emptyBatchRequest() {
        // create your request
        final ODataBatchRequest request = ODataBatchRequestFactory.getBatchRequest(testODataServiceRootURL);

        final BatchRequestPayload payload = request.execute();
        final ODataBatchResponse response = payload.getResponse();

        assertEquals(202, response.getStatusCode());
        assertEquals("Accepted", response.getStatusMessage());

        final Iterator<ODataBatchResponseItem> iter = response.getBody();
        assertFalse(iter.hasNext());
    }

    @Test
    public void changesetWithError() {
        // create your request
        final ODataBatchRequest request = ODataBatchRequestFactory.getBatchRequest(testODataServiceRootURL);

        final BatchRequestPayload payload = request.execute();
        final ODataChangeset changeset = payload.addChangeset();

        ODataURIBuilder targetURI;
        ODataEntityCreateRequest create;

        for (int i = 1; i <= 2; i++) {
            // Create Customer into the changeset
            targetURI = new ODataURIBuilder(testODataServiceRootURL).appendEntitySetSegment("Customer");
            create = ODataCUDRequestFactory.getEntityCreateRequest(
                    targetURI.build(),
                    getSampleCustomerProfile(100 + i, "Sample customer", false));
            create.setFormat(ODataPubFormat.JSON);
            changeset.addRequest(create);
        }

        targetURI = new ODataURIBuilder(testODataServiceRootURL).appendEntitySetSegment("WrongEntitySet");
        create = ODataCUDRequestFactory.getEntityCreateRequest(
                targetURI.build(),
                getSampleCustomerProfile(105, "Sample customer", false));
        create.setFormat(ODataPubFormat.JSON_FULL_METADATA);
        changeset.addRequest(create);

        for (int i = 3; i <= 4; i++) {
            // Create Customer into the changeset
            targetURI = new ODataURIBuilder(testODataServiceRootURL).appendEntitySetSegment("Customer");
            create = ODataCUDRequestFactory.getEntityCreateRequest(
                    targetURI.build(),
                    getSampleCustomerProfile(100 + i, "Sample customer", false));
            create.setFormat(ODataPubFormat.ATOM);
            changeset.addRequest(create);
        }

        final ODataBatchResponse response = payload.getResponse();
        assertEquals(202, response.getStatusCode());
        assertEquals("Accepted", response.getStatusMessage());

        final Iterator<ODataBatchResponseItem> iter = response.getBody();
        final ODataChangesetResponseItem chgResponseItem = (ODataChangesetResponseItem) iter.next();

        ODataResponse res = chgResponseItem.next();
        assertEquals(404, res.getStatusCode());
        assertEquals("Not Found", res.getStatusMessage());
        assertEquals(new Integer(3), Integer.valueOf(res.getHeader("Content-ID").iterator().next()));
        assertFalse(chgResponseItem.hasNext());
    }

    @Test
    public void batchRequest() {
        // create your request
        final ODataBatchRequest request = ODataBatchRequestFactory.getBatchRequest(testODataServiceRootURL);

        final BatchRequestPayload payload = request.execute();

        // -------------------------------------------
        // Add retrieve item
        // -------------------------------------------
        ODataRetrieve retrieve = payload.addRetrieve();

        // prepare URI
        ODataURIBuilder targetURI = new ODataURIBuilder(testODataServiceRootURL);
        targetURI.appendEntityTypeSegment(TEST_CUSTOMER).expand("Logins").select("CustomerId,Logins/Username");

        // create new request
        ODataEntityRequest query = ODataRetrieveRequestFactory.getEntityRequest(targetURI.build());
        query.setDataServiceVersion("2.0");
        query.setMaxDataServiceVersion("3.0");
        query.setFormat(ODataPubFormat.ATOM);

        retrieve.setRequest(query);
        // -------------------------------------------

        // -------------------------------------------
        // Add changeset item
        // -------------------------------------------
        final ODataChangeset changeset = payload.addChangeset();

        // Update Product into the changeset
        targetURI = new ODataURIBuilder(testODataServiceRootURL).appendEntityTypeSegment("Product(-10)");
        final URI editLink = targetURI.build();

        final ODataEntity merge = ODataFactory.newEntity("Microsoft.Test.OData.Services.AstoriaDefaultService.Product");
        merge.setEditLink(editLink);

        merge.addProperty(ODataFactory.newPrimitiveProperty(
                "Description", new ODataPrimitiveValue.Builder().setText("new description from batch").build()));

        final ODataEntityUpdateRequest changes =
                ODataCUDRequestFactory.getEntityUpdateRequest(editLink, UpdateType.MERGE, merge);
        changes.setFormat(ODataPubFormat.JSON_FULL_METADATA);
        changes.setIfMatch(getETag(editLink));

        changeset.addRequest(changes);

        // Create Customer into the changeset
        targetURI = new ODataURIBuilder(testODataServiceRootURL).appendEntitySetSegment("Customer");
        final ODataEntity original = getSampleCustomerProfile(100, "Sample customer", false);
        final ODataEntityCreateRequest create =
                ODataCUDRequestFactory.getEntityCreateRequest(targetURI.build(), original);
        create.setFormat(ODataPubFormat.ATOM);
        changeset.addRequest(create);
        // -------------------------------------------

        // -------------------------------------------
        // Add retrieve item
        // -------------------------------------------
        retrieve = payload.addRetrieve();

        // prepare URI
        targetURI = new ODataURIBuilder(testODataServiceRootURL).appendEntityTypeSegment("Product(-10)");

        // create new request
        query = ODataRetrieveRequestFactory.getEntityRequest(targetURI.build());
        query.setDataServiceVersion("3.0");

        retrieve.setRequest(query);
        // -------------------------------------------

        final ODataBatchResponse response = payload.getResponse();
        assertEquals(202, response.getStatusCode());
        assertEquals("Accepted", response.getStatusMessage());

        final Iterator<ODataBatchResponseItem> iter = response.getBody();

        // retrive the first item (ODataRetrieve)
        ODataBatchResponseItem item = iter.next();
        assertTrue(item instanceof ODataRetrieveResponseItem);

        ODataRetrieveResponseItem retitem = (ODataRetrieveResponseItem) item;
        ODataResponse res = retitem.next();
        assertTrue(res instanceof ODataEntityResponseImpl);
        assertEquals(200, res.getStatusCode());
        assertEquals("OK", res.getStatusMessage());

        ODataEntityResponseImpl entres = (ODataEntityResponseImpl) res;
        ODataEntity entity = entres.getBody();
        assertEquals(new Integer(-10), entity.getProperty("CustomerId").getPrimitiveValue().<Integer>toCastValue());

        // retrive the second item (ODataChangeset)
        item = iter.next();
        assertTrue(item instanceof ODataChangesetResponseItem);

        ODataChangesetResponseItem chgitem = (ODataChangesetResponseItem) item;
        res = chgitem.next();
        assertTrue(res instanceof ODataEntityUpdateResponse);
        assertEquals(204, res.getStatusCode());
        assertEquals("No Content", res.getStatusMessage());

        res = chgitem.next();
        assertTrue(res instanceof ODataEntityCreateResponse);
        assertEquals(201, res.getStatusCode());
        assertEquals("Created", res.getStatusMessage());

        ODataEntityCreateResponse createres = (ODataEntityCreateResponse) res;
        entity = createres.getBody();
        assertEquals(new Integer(100), entity.getProperty("CustomerId").getPrimitiveValue().<Integer>toCastValue());

        // retrive the third item (ODataRetrieve)
        item = iter.next();
        assertTrue(item instanceof ODataRetrieveResponseItem);

        retitem = (ODataRetrieveResponseItem) item;
        res = retitem.next();
        assertTrue(res instanceof ODataEntityResponseImpl);
        assertEquals(200, res.getStatusCode());
        assertEquals("OK", res.getStatusMessage());

        entres = (ODataEntityResponseImpl) res;
        entity = entres.getBody();
        assertEquals("new description from batch",
                entity.getProperty("Description").getPrimitiveValue().<String>toCastValue());

        assertFalse(iter.hasNext());
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

    /**
     * To be used for debug purposes.
     */
    private static class StreamingThread extends Thread {

        private final ODataStreamingMgt streaming;

        public StreamingThread(final ODataStreamingMgt streaming) {
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

        public BatchStreamingThread(final BatchRequestPayload streaming) {
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

                LOG.debug("Batch request {}", builder.toString());

                assertTrue(builder.toString().contains("Content-Id:2"));
                assertTrue(builder.toString().contains("GET " + servicesODataServiceRootURL));
            } catch (IOException e) {
                fail();
            }
        }
    }
}
