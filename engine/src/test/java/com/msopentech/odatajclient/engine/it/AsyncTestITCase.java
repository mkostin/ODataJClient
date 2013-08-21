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
package com.msopentech.odatajclient.engine.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataMediaRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataMediaEntityCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataStreamedRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityUpdateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataMediaEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class AsyncTestITCase extends AbstractTest {

    @Test
    public void retrieveEntitySet() throws InterruptedException, ExecutionException {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntitySetSegment("Product");
        final Future<ODataRetrieveResponse<ODataEntitySet>> futureRes =
                ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build()).asyncExecute();
        assertNotNull(futureRes);

        while (!futureRes.isDone()) {
        }

        final ODataRetrieveResponse<ODataEntitySet> res = futureRes.get();
        assertNotNull(res);
        assertEquals(200, res.getStatusCode());
        assertFalse(res.getBody().getEntities().isEmpty());
    }

    @Test
    public void updateEntity() throws InterruptedException, ExecutionException {
        final URI uri = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("Product").appendKeySegment(-10).build();

        final ODataRetrieveResponse<ODataEntity> entityRes = ODataRetrieveRequestFactory.getEntityRequest(uri).execute();
        final ODataEntity entity = entityRes.getBody();
        entity.getAssociationLinks().clear();
        entity.getNavigationLinks().clear();
        entity.getEditMediaLinks().clear();
        entity.getProperty("Description").setValue(
                new ODataPrimitiveValue.Builder().setText("AsyncTest#updateEntity").build());

        final ODataEntityUpdateRequest updateReq =
                ODataCUDRequestFactory.getEntityUpdateRequest(uri, UpdateType.MERGE, entity);
        updateReq.setIfMatch(entityRes.getEtag());
        final Future<ODataEntityUpdateResponse> futureRes = updateReq.asyncExecute();

        while (!futureRes.isDone()) {
        }

        final ODataEntityUpdateResponse res = futureRes.get();
        assertNotNull(res);
        assertEquals(204, res.getStatusCode());
    }

    /**
     * @see MediaEntityTest#createMediaEntity(com.msopentech.odatajclient.engine.format.ODataPubFormat)
     */
    @Test
    public void createMediaEntity() throws InterruptedException, ExecutionException, IOException {
        ODataURIBuilder builder = new ODataURIBuilder(testDefaultServiceRootURL).appendEntitySetSegment("Car");

        final String TO_BE_UPDATED = "async buffered stream sample";
        final InputStream input = IOUtils.toInputStream(TO_BE_UPDATED);

        final ODataMediaEntityCreateRequest createReq =
                ODataStreamedRequestFactory.getMediaEntityCreateRequest(builder.build(), input);

        final ODataMediaEntityCreateRequest.MediaEntityCreateStreamManager streamManager = createReq.execute();
        final Future<ODataMediaEntityCreateResponse> futureCreateRes = streamManager.getAsyncResponse();

        while (!futureCreateRes.isDone()) {
        }
        final ODataMediaEntityCreateResponse createRes = futureCreateRes.get();

        assertEquals(201, createRes.getStatusCode());

        final ODataEntity created = createRes.getBody();
        assertNotNull(created);
        assertEquals(2, created.getProperties().size());

        final int id = "VIN".equals(created.getProperties().get(0).getName())
                ? created.getProperties().get(0).getPrimitiveValue().<Integer>toCastValue()
                : created.getProperties().get(1).getPrimitiveValue().<Integer>toCastValue();

        builder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("Car").appendKeySegment(id).appendValueSegment();

        final ODataMediaRequest retrieveReq = ODataRetrieveRequestFactory.getMediaRequest(builder.build());

        final ODataRetrieveResponse<InputStream> retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());
        assertEquals(TO_BE_UPDATED, IOUtils.toString(retrieveRes.getBody()));
    }
}
