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
import com.msopentech.odatajclient.engine.communication.request.ODataAddLinkRequest;
import com.msopentech.odatajclient.engine.communication.request.ODataCreateEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.ODataDeleteRequest;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataURI;
import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.ODataUpdateEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.response.ODataCreateEntityResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataLinkOperationResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataUpdateEntityResponse;
import com.msopentech.odatajclient.engine.data.ODataCollectionValue;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataEntityAtomExtensions;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.types.EdmSimpleType;
import com.msopentech.odatajclient.engine.utils.EntityFactory;

public class CUDUsageTest {

    private final ODataClient client = new ODataRestClient();

    public void createTest() {
        // provide the target URI
        final ODataURI targetURI = new ODataURI("http://services.odata.org/OData/Odata.svc");
        targetURI.appendEntitySetSegment("Products");

        // build the new object
        final ODataEntity newEntity = EntityFactory.newEntity("Java Code");

        // Add a complex property
        final ODataComplexValue addressValue = new ODataComplexValue();
        addressValue.add(new ODataProperty("city", new ODataPrimitiveValue("XXX", EdmSimpleType.String)));
        addressValue.add(new ODataProperty("street", new ODataPrimitiveValue("YYY", EdmSimpleType.String)));

        newEntity.addProperty(new ODataProperty("Address", addressValue));

        // Add a collection property
        final ODataCollectionValue preferredColors = new ODataCollectionValue();
        preferredColors.add(new ODataPrimitiveValue("red", EdmSimpleType.String));
        preferredColors.add(new ODataPrimitiveValue("yellow", EdmSimpleType.String));

        newEntity.addProperty(new ODataProperty("PreferredColors", preferredColors));
        // newEntity.set ...

        // just for example, add atom extensions
        ODataEntityAtomExtensions atomExtensions = new ODataEntityAtomExtensions();
        atomExtensions.setSummary("OData client Java framework");
        newEntity.setAtomExtensions(atomExtensions);

        // create your request
        final ODataCreateEntityRequest request = ODataRequestFactory.getCreateEntityRequest(targetURI, newEntity);

        // execute the request
        ODataCreateEntityResponse res = client.<ODataCreateEntityResponse>execute(request);

        // retrieve created entity
        ODataEntity created = res.getBody();

        // retrieve and process execution results
        int statusCode = res.getStatusCode();
        String statusMessage = res.getStatusMessage();

        // .....
    }

    public void createLinkTest() {
        // provide the source URI
        final ODataURI sourceURI = new ODataURI("http://services.odata.org/OData/Odata.svc");
        sourceURI.appendEntityTypeSegment("Products(1)");

        // provide the target URI
        final ODataURI targetURI = new ODataURI("http://services.odata.org/OData/Odata.svc");
        targetURI.appendEntityTypeSegment("Suppliers(5)");

        // build the new link
        final ODataLink newLink = EntityFactory.newEntityLink("Supplier", targetURI);

        // create your request
        final ODataAddLinkRequest request = ODataRequestFactory.getAddLinkRequest(sourceURI, newLink);

        // execute the request
        ODataLinkOperationResponse res = client.<ODataLinkOperationResponse>execute(request);

        // retrieve and process execution results
        int statusCode = res.getStatusCode();
        String statusMessage = res.getStatusMessage();

        // .....
    }

    public void updateTest() {
        // provide the target URI
        final ODataURI targetURI = new ODataURI("http://services.odata.org/OData/Odata.svc");
        targetURI.appendEntityTypeSegment("Products(2)");

        // build the new object to change Rating value
        final ODataEntity changes = EntityFactory.newEntity("Java Code");
        changes.addProperty(new ODataProperty("Rating", new ODataPrimitiveValue(3, EdmSimpleType.Int32)));

        // create your request
        final ODataUpdateEntityRequest request = ODataRequestFactory.getUpdateEntityRequest(targetURI, changes, UpdateType.PATCH);

        // execute the request
        ODataUpdateEntityResponse res = client.<ODataUpdateEntityResponse>execute(request);

        // retrieve update object if returned
        ODataEntity updated = res.getBody();

        // retrieve and process execution results
        int statusCode = res.getStatusCode();
        String statusMessage = res.getStatusMessage();

        // .....
    }

    public void deleteTest() {
        // provide the target URI
        final ODataURI targetURI = new ODataURI("http://services.odata.org/OData/Odata.svc");
        targetURI.appendEntityTypeSegment("Products(2)");

        // create your request
        final ODataDeleteRequest request = ODataRequestFactory.getDeleteRequest(targetURI);

        // execute the request
        ODataDeleteResponse res = client.<ODataDeleteResponse>execute(request);

        // retrieve and process execution results
        int statusCode = res.getStatusCode();
        String statusMessage = res.getStatusMessage();

        // .....
    }
}
