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
import com.msopentech.odatajclient.engine.data.metadata.edm.TEntityType;
import org.junit.Test;

public class MetadataTest {

    @Test
    public void retrieveAndParseEdmTypes() throws Exception {
        final ODataMetadataRequest req = ODataRetrieveRequestFactory.getMetadataRequest(
                "http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc");

        final EdmMetadata metadata = new EdmMetadata(req.rowExecute());
        assertNotNull(metadata);

        EdmType productCollection = new EdmType(metadata, "Collection(ODataDemo.Product)");
        assertNotNull(productCollection);
        assertTrue(productCollection.isCollection());
        assertFalse(productCollection.isSimpleType());
        assertFalse(productCollection.isEnumType());
        assertFalse(productCollection.isComplexType());
        assertTrue(productCollection.isEntityType());

        TEntityType product = productCollection.getEntityType();
        assertNotNull(product);
        assertEquals("Product", product.getName());

        EdmType stream = new EdmType(metadata, "Edm.Stream");
        assertNotNull(stream);
        assertFalse(stream.isCollection());
        assertTrue(stream.isSimpleType());
        assertFalse(stream.isEnumType());
        assertFalse(stream.isComplexType());
        assertFalse(stream.isEntityType());
    }
}
