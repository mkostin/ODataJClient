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

import com.msopentech.odatajclient.engine.client.ODataClient;
import com.msopentech.odatajclient.engine.client.ODataRestClient;
import com.msopentech.odatajclient.engine.communication.request.ODataQueryRequest;
import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataURI;

public class QueryUsageTest {

    private final ODataClient client = new ODataRestClient();

    public void usageTest() {
        // prepare URI
        final ODataURI uri = new ODataURI("http://services.odata.org/OData/Odata.svc");
        uri.appendEntityTypeSegment("Products(0)").expand("Supplier").select("Rating,Supplier/Name");

        // create new request
        final ODataQueryRequest request = ODataRequestFactory.getQueryRequest(uri);

        // execute request
        final ODataQueryResponse res = client.<ODataQueryResponse>execute(request);

        // retrieve and process the query result
        for (ODataEntity entity : res.<ODataEntity>getBody()) {
            // .................
        }
    }
}
