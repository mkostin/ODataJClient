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
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataPropertyRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataValueRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.types.ODataPropertyFormat;
import com.msopentech.odatajclient.engine.types.ODataValueFormat;
import java.io.IOException;
import org.junit.Test;

/**
 * This is the unit test class to check basic entity operations.
 */
public class PropertyTest extends AbstractTest {

    /**
     * @see PrimitiveValueTest
     */
    private void readPrimitiveProperty(final ODataPropertyFormat format) throws IOException {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment(TEST_CUSTOMER).appendStructuralSegment("CustomerId");

        final ODataPropertyRequest req = ODataRetrieveRequestFactory.getPropertyRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataQueryResponse<ODataProperty> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final ODataProperty property = res.getBody();
        debugODataProperty(property, "Retrieved property");

        assertNotNull(property);
        assertTrue(property.hasPrimitiveValue());
        assertEquals("-10", property.getPrimitiveValue().toString());
    }

    private void readComplexProperty(final ODataPropertyFormat format) throws IOException {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment(TEST_CUSTOMER).appendStructuralSegment("PrimaryContactInfo");

        final ODataPropertyRequest req = ODataRetrieveRequestFactory.getPropertyRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataQueryResponse<ODataProperty> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final ODataProperty property = res.getBody();
        debugODataProperty(property, "Retrieved property");

        assertNotNull(property);
        assertTrue(property.hasComplexValue());
        assertEquals(6, property.getComplexValue().size());
    }

    private void readCollectionProperty(final ODataPropertyFormat format) throws IOException {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment(TEST_CUSTOMER).appendStructuralSegment("BackupContactInfo");

        final ODataPropertyRequest req = ODataRetrieveRequestFactory.getPropertyRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataQueryResponse<ODataProperty> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final ODataProperty property = res.getBody();
        debugODataProperty(property, "Retrieved property");

        assertNotNull(property);
        assertTrue(property.hasCollectionValue());
        assertEquals(9, property.getCollectionValue().size());
    }

    @Test
    public void readXmlCollectionProperty() throws IOException {
        readCollectionProperty(ODataPropertyFormat.XML);
    }

    @Test
    public void readJSONCollectionProperty() throws IOException {
        readCollectionProperty(ODataPropertyFormat.JSON);
    }

    @Test
    public void readXmlComplexProperty() throws IOException {
        readComplexProperty(ODataPropertyFormat.XML);
    }

    @Test
    public void readJSONComplexProperty() throws IOException {
        readComplexProperty(ODataPropertyFormat.JSON);
    }

    @Test
    public void readXmlPrimitiveProperty() throws IOException {
        readPrimitiveProperty(ODataPropertyFormat.XML);
    }

    @Test
    public void readJSONPrimitiveProperty() throws IOException {
        readPrimitiveProperty(ODataPropertyFormat.JSON);
    }

    @Test
    public void readPropertyValue() throws IOException {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment(TEST_CUSTOMER).appendStructuralSegment("CustomerId").
                appendValueSegment("$value");

        final ODataValueRequest req = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
        req.setFormat(ODataValueFormat.TEXT);

        final ODataQueryResponse<ODataValue> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final ODataValue value = res.getBody();
        debugODataValue(value, "Retrieved property");

        assertNotNull(value);
        assertEquals("-10", value.toString());
    }

    @Test
    public void readCountValue() throws IOException {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment("Customer").appendValueSegment("$count");

        final ODataValueRequest req = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
        req.setFormat(ODataValueFormat.TEXT);

        final ODataQueryResponse<ODataValue> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final ODataValue value = res.getBody();
        debugODataValue(value, "Retrieved property");

        assertNotNull(value);
        // the following assert depends on the test execution order (use >= to be sure)
        assertTrue(Integer.valueOf(value.toString()) >= 10);
    }
}
