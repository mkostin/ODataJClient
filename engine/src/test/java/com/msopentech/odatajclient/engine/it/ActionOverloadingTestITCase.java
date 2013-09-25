/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.engine.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.msopentech.odatajclient.engine.communication.request.invoke.ODataInvokeRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataInvokeResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataNoContent;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer.FunctionImport;
import com.msopentech.odatajclient.engine.utils.URIUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Test;

public class ActionOverloadingTestITCase extends AbstractTest {

    @Test
    public void retrieveProducts() {
        final EdmMetadata metadata =
                ODataRetrieveRequestFactory.getMetadataRequest(testActionOverloadingServiceRootURL).execute().getBody();
        assertNotNull(metadata);

        final EntityContainer container = metadata.getSchema(0).getEntityContainers().get(0);
        assertNotNull(container);

        int execs = 0;
        for (FunctionImport funcImp : container.getFunctionImports("RetrieveProduct")) {
            final ODataInvokeResponse<ODataProperty> res;
            if (funcImp.getParameters().isEmpty()) {
                final ODataURIBuilder funcImpBuilder = new ODataURIBuilder(testActionOverloadingServiceRootURL).
                        appendFunctionImportSegment(URIUtils.rootFunctionImportURISegment(container, funcImp));

                res = ODataInvokeRequestFactory.<ODataProperty>getInvokeRequest(
                        funcImpBuilder.build(), metadata, funcImp).execute();
            } else if ("Microsoft.Test.OData.Services.AstoriaDefaultService.Product".
                    equals(funcImp.getParameters().get(0).getType())) {

                final ODataEntity product = ODataRetrieveRequestFactory.getEntityRequest(
                        new ODataURIBuilder(testActionOverloadingServiceRootURL).
                        appendEntityTypeSegment("Product").appendKeySegment(-10).build()).
                        execute().getBody();
                assertNotNull(product);

                res = ODataInvokeRequestFactory.<ODataProperty>getInvokeRequest(
                        product.getOperation("RetrieveProduct").getTarget(), metadata, funcImp).execute();
            } else if ("Microsoft.Test.OData.Services.AstoriaDefaultService.OrderLine".
                    equals(funcImp.getParameters().get(0).getType())) {

                final Map<String, Object> key = new LinkedHashMap<String, Object>(2);
                key.put("OrderId", -10);
                key.put("ProductId", -10);
                final ODataEntity orderLine = ODataRetrieveRequestFactory.getEntityRequest(
                        new ODataURIBuilder(testActionOverloadingServiceRootURL).
                        appendEntityTypeSegment("OrderLine").appendKeySegment(key).build()).
                        execute().getBody();
                assertNotNull(orderLine);

                res = ODataInvokeRequestFactory.<ODataProperty>getInvokeRequest(
                        orderLine.getOperation("RetrieveProduct").getTarget(), metadata, funcImp).execute();
            } else {
                res = null;
            }

            assertNotNull(res);
            assertEquals(200, res.getStatusCode());
            assertEquals(Integer.valueOf(-10), res.getBody().getPrimitiveValue().<Integer>toCastValue());
            execs++;
        }
        assertEquals(3, execs);
    }

    @Test
    public void increaseSalaries() {
        final EdmMetadata metadata =
                ODataRetrieveRequestFactory.getMetadataRequest(testActionOverloadingServiceRootURL).execute().getBody();
        assertNotNull(metadata);

        final EntityContainer container = metadata.getSchema(0).getEntityContainers().get(0);
        assertNotNull(container);

        int execs = 0;
        for (FunctionImport funcImp : container.getFunctionImports("IncreaseSalaries")) {
            final Map<String, ODataValue> parameters = new LinkedHashMap<String, ODataValue>(1);
            parameters.put("n",
                    new ODataPrimitiveValue.Builder().setType(EdmSimpleType.INT_32).setValue(5).build());

            final ODataInvokeResponse<ODataNoContent> res;
            if ("Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.Employee)".
                    equals(funcImp.getParameters().get(0).getType())) {

                final ODataURIBuilder builder = new ODataURIBuilder(testActionOverloadingServiceRootURL).
                        appendEntitySetSegment("Person").
                        appendStructuralSegment("Microsoft.Test.OData.Services.AstoriaDefaultService.Employee");

                final ODataEntitySet employees = ODataRetrieveRequestFactory.getEntitySetRequest(
                        builder.build()).execute().getBody();
                assertNotNull(employees);

                res = ODataInvokeRequestFactory.<ODataNoContent>getInvokeRequest(
                        builder.appendFunctionImportSegment(funcImp.getName()).build(), metadata, funcImp, parameters).
                        execute();
            } else if ("Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.SpecialEmployee)".
                    equals(funcImp.getParameters().get(0).getType())) {

                final ODataURIBuilder builder = new ODataURIBuilder(testActionOverloadingServiceRootURL).
                        appendEntitySetSegment("Person").
                        appendStructuralSegment("Microsoft.Test.OData.Services.AstoriaDefaultService.SpecialEmployee");

                final ODataEntitySet specialEmployees = ODataRetrieveRequestFactory.getEntitySetRequest(
                        builder.build()).execute().getBody();
                assertNotNull(specialEmployees);

                res = ODataInvokeRequestFactory.<ODataNoContent>getInvokeRequest(
                        builder.appendFunctionImportSegment(funcImp.getName()).build(), metadata, funcImp, parameters).
                        execute();
            } else {
                res = null;
            }

            assertNotNull(res);
            assertEquals(204, res.getStatusCode());
            execs++;
        }
        assertEquals(2, execs);
    }
}
