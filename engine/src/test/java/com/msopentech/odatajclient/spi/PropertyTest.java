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

import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataPropertyUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataPropertyRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataValueRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataPropertyUpdateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.ODataCollectionValue;
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

    @Test
    public void replaceComplexPropertyAsXML() throws IOException {
        updateComplexProperty(ODataPropertyFormat.XML, UpdateType.REPLACE);
    }

    @Test
    public void replaceComplexPropertyAsJSON() throws IOException {
        updateComplexProperty(ODataPropertyFormat.JSON_FULL_METADATA, UpdateType.REPLACE);
    }

    @Test
    @Ignore
    public void patchComplexPropertyAsXML() throws IOException {
        updateComplexProperty(ODataPropertyFormat.XML, UpdateType.PATCH);
    }

    @Test
    @Ignore
    public void patchComplexPropertyAsJSON() throws IOException {
        updateComplexProperty(ODataPropertyFormat.JSON_FULL_METADATA, UpdateType.PATCH);
    }

    @Test
    @Ignore
    public void mergeComplexPropertyAsXML() throws IOException {
        updateComplexProperty(ODataPropertyFormat.XML, UpdateType.MERGE);
    }

    @Test
    @Ignore
    public void mergeComplexPropertyAsJSON() throws IOException {
        updateComplexProperty(ODataPropertyFormat.JSON_FULL_METADATA, UpdateType.MERGE);
    }

    private void updateComplexProperty(final ODataPropertyFormat format, final UpdateType type) throws IOException {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment("Customer(-9)").appendStructuralSegment("PrimaryContactInfo");

        ODataPropertyRequest retrieveReq = ODataRetrieveRequestFactory.getPropertyRequest(uriBuilder.build());
        retrieveReq.setFormat(format);

        ODataQueryResponse<ODataProperty> retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());

        ODataProperty primaryContactInfo = retrieveRes.getBody();
        primaryContactInfo = new ODataProperty("PrimaryContactInfo", primaryContactInfo.getComplexValue());

        final String newItem = "new item " + System.currentTimeMillis();

        final ODataCollectionValue originalValue =
                primaryContactInfo.getComplexValue().get("EmailBag").getCollectionValue();

        final int origSize = originalValue.size();

        originalValue.add(new ODataPrimitiveValue.Builder().setText(newItem).build());
        assertEquals(origSize + 1, originalValue.size());

        final ODataPropertyUpdateRequest updateReq =
                ODataRequestFactory.getComplexUpdateRequest(uriBuilder.build(), type, primaryContactInfo);
        updateReq.setFormat(format);

        ODataPropertyUpdateResponse updateRes = updateReq.execute();
        assertEquals(204, updateRes.getStatusCode());

        retrieveReq = ODataRetrieveRequestFactory.getPropertyRequest(uriBuilder.build());
        retrieveReq.setFormat(format);

        retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());

        primaryContactInfo = retrieveRes.getBody();

        assertEquals(origSize + 1, primaryContactInfo.getComplexValue().get("EmailBag").getCollectionValue().size());
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

    /**
     * @see PrimitiveValueTest
     */
    private ODataProperty readPrimitiveProperty(final ODataPropertyFormat format) throws IOException {
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

        return property;
    }

    private ODataProperty readComplexProperty(final ODataPropertyFormat format) throws IOException {
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

        return property;
    }

    private ODataProperty readCollectionProperty(final ODataPropertyFormat format) throws IOException {
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

        return property;
    }
}
