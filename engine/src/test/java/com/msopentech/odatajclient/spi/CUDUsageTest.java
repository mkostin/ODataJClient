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

import com.msopentech.odatajclient.engine.communication.request.cud.ODataInsertLinkRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataDeleteRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityCreateRequest;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataStreamCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataStreamCreateRequest.StreamCreateRequestPayload;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataLinkOperationResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataStreamCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityUpdateResponse;
import com.msopentech.odatajclient.engine.data.ODataCollectionValue;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CUDUsageTest {

    public void createTest() {
        // provide the target URI
        final ODataURIBuilder targetURI = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");
        targetURI.appendEntitySetSegment("Products");

        // build the new object
        final ODataEntity newEntity = ODataFactory.newEntity("Java Code");

        // Add a complex property
        final ODataComplexValue addressValue = new ODataComplexValue("Address");
        addressValue.add(new ODataProperty("city", new ODataPrimitiveValue.Builder().setText("XXX").build()));
        addressValue.add(new ODataProperty("street", new ODataPrimitiveValue.Builder().setText("YYY").build()));

        newEntity.addProperty(new ODataProperty("Address", addressValue));

        // Add a collection property
        final ODataCollectionValue preferredColors = new ODataCollectionValue("Colors");
        preferredColors.add(new ODataPrimitiveValue.Builder().setText("red").build());
        preferredColors.add(new ODataPrimitiveValue.Builder().setText("yellow").build());

        newEntity.addProperty(new ODataProperty("PreferredColors", preferredColors));
        // newEntity.set ...

        // create your request
        final ODataEntityCreateRequest request =
                ODataCUDRequestFactory.getEntityCreateRequest(targetURI.build(), newEntity);

        // execute and retrieve response
        ODataEntityCreateResponse res = request.execute();

        // retrieve created entity
        ODataEntity created = res.getBody();

        // retrieve and process execution results
        int statusCode = res.getStatusCode();
        String statusMessage = res.getStatusMessage();

        // .....
    }

    public void createLinkTest() {
        // provide the source URI
        final ODataURIBuilder sourceURI = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");
        sourceURI.appendEntityTypeSegment("Products(1)");

        // provide the target URI
        final ODataURIBuilder targetURI = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");
        targetURI.appendEntityTypeSegment("Suppliers(5)");

        // build the new link
        final ODataLink newLink = ODataFactory.newEntityNavigationLink("Supplier", targetURI.build());

        // create your request
        final ODataInsertLinkRequest request = ODataCUDRequestFactory.getInsertLinkRequest(sourceURI.build(), newLink);

        // execute and retrieve response
        ODataLinkOperationResponse res = request.execute();

        // retrieve and process execution results
        int statusCode = res.getStatusCode();
        String statusMessage = res.getStatusMessage();

        // .....
    }

    public void updateTest() {
        // provide the target URI
        final ODataURIBuilder targetURI = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");
        targetURI.appendEntityTypeSegment("Products(2)");

        // build the new object to change Rating value
        final ODataEntity changes = ODataFactory.newEntity("Java Code");
        changes.addProperty(new ODataProperty("Rating",
                new ODataPrimitiveValue.Builder().setText("3").setType(EdmSimpleType.INT_32).build()));

        // create your request
        final ODataEntityUpdateRequest request =
                ODataCUDRequestFactory.getEntityUpdateRequest(targetURI.build(), UpdateType.PATCH, changes);

        // execute and retrieve response
        ODataEntityUpdateResponse res = request.execute();

        // retrieve update object if returned
        ODataEntity updated = res.getBody();

        // retrieve and process execution results
        int statusCode = res.getStatusCode();
        String statusMessage = res.getStatusMessage();

        // .....
    }

    public void deleteTest() {
        // provide the target URI
        final ODataURIBuilder targetURI = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");
        targetURI.appendEntityTypeSegment("Products(2)");

        // create your request
        final ODataDeleteRequest request = ODataCUDRequestFactory.getDeleteRequest(targetURI.build());

        // execute and retrieve response
        ODataDeleteResponse res = request.execute();

        // retrieve and process execution results
        int statusCode = res.getStatusCode();
        String statusMessage = res.getStatusMessage();

        // .....
    }

    public void createNamedStream() throws FileNotFoundException {
        final ODataURIBuilder targetURI = new ODataURIBuilder("http://services.odata.org/OData/Odata.svc");

        final ODataStreamCreateRequest req =
                ODataCUDRequestFactory.getStreamCreateRequest(targetURI.build(), new FileInputStream("/tmp/photo.png"));

        StreamCreateRequestPayload payload = req.execute();
        ODataStreamCreateResponse response = payload.getResponse();
    }
}
