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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataMetadataRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.EdmType;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityType;
import org.junit.Test;

public class MetadataTest extends AbstractTest {

    @Test
    public void retrieveAndParseEdmTypes() throws Exception {
        final ODataMetadataRequest req = ODataRetrieveRequestFactory.getMetadataRequest(testODataServiceRootURL);

        final EdmMetadata metadata = new EdmMetadata(req.rowExecute());
        assertNotNull(metadata);

        final EdmType orderCollection =
                new EdmType(metadata, "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.Order)");
        assertNotNull(orderCollection);
        assertTrue(orderCollection.isCollection());
        assertFalse(orderCollection.isSimpleType());
        assertFalse(orderCollection.isEnumType());
        assertFalse(orderCollection.isComplexType());
        assertTrue(orderCollection.isEntityType());

        EntityType order = orderCollection.getEntityType();
        assertNotNull(order);
        assertEquals("Order", order.getName());

        EdmType stream = new EdmType(metadata, "Edm.Stream");
        assertNotNull(stream);
        assertFalse(stream.isCollection());
        assertTrue(stream.isSimpleType());
        assertFalse(stream.isEnumType());
        assertFalse(stream.isComplexType());
        assertFalse(stream.isEntityType());
    }
}
