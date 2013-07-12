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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityUpdateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import java.net.URI;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.Test;

public class AsyncTest extends AbstractTest {

    @Test
    public void retrieveEntitySet() throws InterruptedException, ExecutionException {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL).
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
        // Products has some numbers, whose formatting is locale-sensitive
        Locale.setDefault(Locale.ENGLISH);

        final URI uri = new ODataURIBuilder(testODataServiceRootURL).appendEntityTypeSegment(TEST_PRODUCT).build();

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
}
