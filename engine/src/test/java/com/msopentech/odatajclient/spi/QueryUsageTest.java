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

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;

public class QueryUsageTest {

    public void usageTest() {
        // prepare URI
        final ODataURIBuilder uri = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");
        uri.appendEntityTypeSegment("Products(0)").expand("Supplier").select("Rating,Supplier/Name");

        // create new request
        final ODataEntityRequest request = ODataRetrieveRequestFactory.getEntityRequest(uri.build());

        // execute and retrieve query response
        final ODataQueryResponse<ODataEntity> res = request.execute();

        final ODataEntity entity = res.getBody();

        // ....
    }
}
