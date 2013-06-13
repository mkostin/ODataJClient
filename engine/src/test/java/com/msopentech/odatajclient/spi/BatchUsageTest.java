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

import com.msopentech.odatajclient.engine.communication.request.ODataBatchRequest;
import com.msopentech.odatajclient.engine.communication.request.ODataBatchRequest.BatchRequestPayload;
import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.ODataUpdateEntityRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataBatchResponse;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.ODataChangeset;
import com.msopentech.odatajclient.engine.communication.request.ODataQueryRequest;
import com.msopentech.odatajclient.engine.communication.request.ODataRetrieve;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.metadata.edm.EDMSimpleType;
import com.msopentech.odatajclient.engine.utils.EntityFactory;

public class BatchUsageTest {

    public void batchTest() {
        // create your request
        final ODataBatchRequest request =
                ODataRequestFactory.getBatchRequest("http://services.odata.org/OData/Odata.svc");

        // execute the request
        final BatchRequestPayload payload = request.execute();

        // -------------------------------------------
        // Add retrieve item
        // -------------------------------------------
        final ODataRetrieve retrieve = payload.addRetrieve();

        // prepare URI
        final ODataURIBuilder uri = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");
        uri.appendEntityTypeSegment("Products(0)").expand("Supplier").select("Rating,Supplier/Name");

        // create new request
        final ODataQueryRequest query = ODataRequestFactory.getQueryRequest(uri.build());

        retrieve.setRequest(query);
        // -------------------------------------------

        // -------------------------------------------
        // Add changeset item
        // -------------------------------------------
        final ODataChangeset changeset = payload.addChangeset();

        // provide the target URI
        final ODataURIBuilder targetURI = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");
        targetURI.appendEntityTypeSegment("Products(2)");

        // build the new object to change Rating value
        final ODataEntity changes = EntityFactory.newEntity("Java Code");
        changes.addProperty(new ODataProperty("Rating", new ODataPrimitiveValue(3, EDMSimpleType.INT_32)));

        // create your request
        final ODataUpdateEntityRequest change =
                ODataRequestFactory.getUpdateEntityRequest(targetURI.build(), UpdateType.PATCH, changes);

        changeset.addRequest(change);

        // ... more changeset items here
        // -------------------------------------------

        // add more batch items (retrieve/changeset) here ...

        final ODataBatchResponse res = payload.getResponse();
    }
}
