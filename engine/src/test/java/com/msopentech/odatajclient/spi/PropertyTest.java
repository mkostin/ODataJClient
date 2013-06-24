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

import static com.msopentech.odatajclient.spi.AbstractTest.testODataServiceRootURL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataPropertyRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataValueRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.ODataCollectionValue;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.types.ODataPropertyFormat;
import com.msopentech.odatajclient.engine.types.ODataValueFormat;
import java.io.IOException;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is the unit test class to check basic entity operations.
 */
public class PropertyTest extends AbstractTest {

    private void readPrimitiveProperty(final ODataPropertyFormat format) throws IOException {
        ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment("Customer(-10)").appendStructuralSegment("CustomerId");

        ODataPropertyRequest req = ODataRetrieveRequestFactory.getPropertyRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataQueryResponse<ODataProperty> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final ODataProperty property = res.getBody();
        debugODataProperty(property, "Retrieved property");

        assertNotNull(property);
        assertTrue(property.getValue() instanceof ODataPrimitiveValue);
        assertEquals("-10", ((ODataPrimitiveValue) property.getValue()).toString());
    }

    private void readComplexProperty(final ODataPropertyFormat format) throws IOException {
        ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment("Customer(-10)").appendStructuralSegment("PrimaryContactInfo");

        ODataPropertyRequest req = ODataRetrieveRequestFactory.getPropertyRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataQueryResponse<ODataProperty> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final ODataProperty property = res.getBody();
        debugODataProperty(property, "Retrieved property");

        assertNotNull(property);
        assertTrue(property.getValue() instanceof ODataComplexValue);
        assertEquals(6, ((ODataComplexValue) property.getValue()).size());
    }

    private void readCollectionProperty(final ODataPropertyFormat format) throws IOException {
        ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment("Customer(-10)").appendStructuralSegment("BackupContactInfo");

        ODataPropertyRequest req = ODataRetrieveRequestFactory.getPropertyRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataQueryResponse<ODataProperty> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final ODataProperty property = res.getBody();
        debugODataProperty(property, "Retrieved property");

        assertNotNull(property);
        assertTrue(property.getValue() instanceof ODataCollectionValue);
        assertEquals(9, ((ODataCollectionValue) property.getValue()).size());
    }

    @Test
    public void readXmlCollectionProperty() throws Exception {
        readCollectionProperty(ODataPropertyFormat.XML);
    }

    @Test
    @Ignore
    public void readJSONCollectionProperty() throws Exception {
        readCollectionProperty(ODataPropertyFormat.JSON);
    }

    @Test
    public void readXmlComplexProperty() throws Exception {
        readComplexProperty(ODataPropertyFormat.XML);
    }

    @Test
    @Ignore
    public void readJSONComplexProperty() throws Exception {
        readComplexProperty(ODataPropertyFormat.JSON);
    }

    @Test
    public void readXmlPrimitiveProperty() throws Exception {
        readPrimitiveProperty(ODataPropertyFormat.XML);
    }

    @Test
    @Ignore
    public void readJSONPrimitiveProperty() throws Exception {
        readPrimitiveProperty(ODataPropertyFormat.JSON);
    }

    @Test
    public void readPropertyValue() throws IOException {
        ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment("Customer(-10)").appendStructuralSegment("CustomerId").
                appendValueSegment("$value");

        ODataValueRequest req = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
        req.setFormat(ODataValueFormat.TEXT);

        final ODataQueryResponse<ODataValue> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final ODataValue value = res.getBody();
        debugODataValue(value, "Retrieved property");

        assertNotNull(value);
        assertEquals("-10", value.toString());
    }
}
