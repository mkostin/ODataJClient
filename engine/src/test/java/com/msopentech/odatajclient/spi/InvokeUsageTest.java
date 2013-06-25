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

import com.msopentech.odatajclient.engine.communication.request.invoke.ODataInvokeRequest;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.communication.request.invoke.ODataInvokeRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataInvokeResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import java.util.Collections;
import java.util.Map;

public class InvokeUsageTest {

    public void invokeFunction() {
        // provide the target URI
        final ODataURIBuilder targetURI = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");
        targetURI.appendFunctionSegment("GetProductsByRating").addQueryParameter("rating", "4");

        // create your request
        final ODataInvokeRequest request = ODataInvokeRequestFactory.getInvokeFunctionRequest(targetURI.build());

        // execute and retrieve response
        ODataInvokeResponse res = request.execute();

        for (ODataEntity result : res.<ODataEntity>getBody()) {
            // process retrieved entity ...
        }

        // .....
    }

    public void invokeBindableAction() {
        // provide the target URI
        final ODataURIBuilder targetURI = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");
        targetURI.appendEntityTypeSegment("Product(0)").appendActionSegment("UpdateProductRating");

        final ODataPrimitiveValue value = new ODataPrimitiveValue.Builder().
                setText("2").setType(EdmSimpleType.INT_32).build();
        final Map<String, ODataValue> parameters = Collections.<String, ODataValue>singletonMap("rating", value);

        // create your request
        final ODataInvokeRequest request = ODataInvokeRequestFactory.getInvokeActionRequest(targetURI.build(),
                parameters);

        // execute the request
        final ODataInvokeResponse res = request.execute();

        // .....
    }
}
