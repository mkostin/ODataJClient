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

import com.msopentech.odatajclient.engine.communication.request.ODataMetadataRequest;
import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataMetadataResponse;
import com.msopentech.odatajclient.engine.data.metadata.EntityContainer;
import com.msopentech.odatajclient.engine.data.metadata.EntityType;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;

public class MetadataUsageTest {

    public void usageTest() {
        // create new request
        final ODataMetadataRequest request =
                ODataRequestFactory.getMetadataRequest("http://services.odata.org/OData/Odata.svc");

        // execute and retrtieve response
        final ODataMetadataResponse res = request.execute();

        // get access to metadata object
        EdmMetadata metadata = res.getBody();

        // (sample) access EntityType
        EntityType entityType = metadata.getSchema(0).getEntityTypes().get(1);

        // (sample) access EntityContainer
        EntityContainer entityContainer = metadata.getSchema(0).getEntityContainers().get(0);
    }
}
