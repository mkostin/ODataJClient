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
import com.msopentech.odatajclient.engine.communication.request.ODataInvokeRequest;
import com.msopentech.odatajclient.engine.data.ODataURI;
import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataInvokeResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.types.EdmSimpleType;
import java.util.Collections;
import java.util.Map;

public class InvokeUsageTest {

    private final ODataClient client = new ODataRestClient();

    public void invokeFunction() {
        // provide the target URI
        final ODataURI targetURI = new ODataURI("http://services.odata.org/OData/Odata.svc");
        targetURI.appendFunctionSegment("GetProductsByRating").addQueryParameter("rating", "4");

        // create your request
        final ODataInvokeRequest request = ODataRequestFactory.getInvokeFunctionRequest(targetURI);

        // execute the request
        ODataInvokeResponse res = client.<ODataInvokeResponse>execute(request);

        for (ODataEntity result : res.<ODataEntity>getBody()) {
            // process retrieved entity ...
        }

        // .....
    }

    public void invokeBindableAction() {
        // provide the target URI
        final ODataURI targetURI = new ODataURI("http://services.odata.org/OData/Odata.svc");
        targetURI.appendEntityTypeSegment("Product(0)").appendActionSegment("UpdateProductRating");

        final ODataPrimitiveValue value = new ODataPrimitiveValue(2, EdmSimpleType.Int32);
        final Map<String, ODataValue> parameters = Collections.<String, ODataValue>singletonMap("rating", value);

        // create your request
        final ODataInvokeRequest request = ODataRequestFactory.getInvokeActionRequest(targetURI, parameters);

        // execute the request
        ODataInvokeResponse res = client.<ODataInvokeResponse>execute(request);

        // .....
    }
}
