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
package com.msopentech.odatajclient.engine.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.client.http.HttpMethod;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataMetadataRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.EdmType;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer.FunctionImport;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityType;
import com.msopentech.odatajclient.engine.data.metadata.edm.Schema;
import java.util.List;
import org.junit.Test;

public class MetadataTestITCase extends AbstractTest {

    @Test
    public void retrieveAndParseEdmTypes() {
        final ODataMetadataRequest req = ODataRetrieveRequestFactory.getMetadataRequest(testDefaultServiceRootURL);

        final ODataRetrieveResponse<EdmMetadata> res = req.execute();
        final EdmMetadata metadata = res.getBody();
        assertNotNull(metadata);

        final EdmType orderCollection =
                new EdmType(metadata, "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.Order)");
        assertNotNull(orderCollection);
        assertTrue(orderCollection.isCollection());
        assertFalse(orderCollection.isSimpleType());
        assertFalse(orderCollection.isEnumType());
        assertFalse(orderCollection.isComplexType());
        assertTrue(orderCollection.isEntityType());

        final EntityType order = orderCollection.getEntityType();
        assertNotNull(order);
        assertEquals("Order", order.getName());

        final EdmType stream = new EdmType(metadata, "Edm.Stream");
        assertNotNull(stream);
        assertFalse(stream.isCollection());
        assertTrue(stream.isSimpleType());
        assertFalse(stream.isEnumType());
        assertFalse(stream.isComplexType());
        assertFalse(stream.isEntityType());

        final List<FunctionImport> functionImports =
                metadata.getSchemas().get(0).getEntityContainers().get(0).getFunctionImports();
        int legacyGetters = 0;
        int legacyPosters = 0;
        int actions = 0;
        int functions = 0;
        for (FunctionImport functionImport : functionImports) {
            if (HttpMethod.GET.name().equals(functionImport.getHttpMethod())) {
                legacyGetters++;
            } else if (HttpMethod.POST.name().equals(functionImport.getHttpMethod())) {
                legacyPosters++;
            } else if (functionImport.getHttpMethod() == null) {
                if (functionImport.isIsSideEffecting()) {
                    actions++;
                } else {
                    functions++;
                }
            }
        }
        assertEquals(6, legacyGetters);
        assertEquals(1, legacyPosters);
        assertEquals(5, actions);
        assertEquals(0, functions);
    }

    @Test
    public void large() {
        final EdmMetadata metadata = ODataRetrieveRequestFactory.
                getMetadataRequest(testLargeModelServiceRootURL).execute().getBody();
        assertNotNull(metadata);

        assertEquals(400, metadata.getSchemas().get(0).getEntityContainers().get(0).getEntitySets().size());
    }

    @Test
    public void multipleSchemas() {
        final EdmMetadata metadata = ODataRetrieveRequestFactory.
                getMetadataRequest("http://services.odata.org/v3/(S(ledws2f0sdc3gg2r0h51iex0))/Northwind/Northwind.svc").
                execute().getBody();
        assertNotNull(metadata);

        final Schema first = metadata.getSchema("NorthwindModel");
        assertNotNull(first);

        final Schema second = metadata.getSchema("ODataWebV3.Northwind.Model");
        assertNotNull(second);

        final EntityContainer entityContainer = second.getDefaultEntityContainer();
        assertNotNull(entityContainer);
        assertEquals("NorthwindEntities", entityContainer.getName());
    }
}
