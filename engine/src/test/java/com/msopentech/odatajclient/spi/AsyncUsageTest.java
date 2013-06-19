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

import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityCreateRequest;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.utils.ODataFactory;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncUsageTest {

    public void asyncCreateTest() {
        // provide the target URI
        final ODataURIBuilder targetURI = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");
        targetURI.appendEntitySetSegment("Products");

        // build the new object
        final ODataEntity newEntity = ODataFactory.newEntity("Java Code");
        // newEntity.set ...

        // create your request
        final ODataEntityCreateRequest request =
                ODataCUDRequestFactory.getEntityCreateRequest(targetURI.build(), newEntity);

        // execute the request
        request.execute();

        final Future<ODataEntityCreateResponse> res = request.asyncExecute();

        if (res.isDone()) {
            try {
                // retrieve and process execution results
                int statusCode = res.get().getStatusCode();
                String statusMessage = res.get().getStatusMessage();

                // .....
            } catch (InterruptedException ex) {
                // ...
            } catch (ExecutionException ex) {
                // ...
            }
        }
    }
}
