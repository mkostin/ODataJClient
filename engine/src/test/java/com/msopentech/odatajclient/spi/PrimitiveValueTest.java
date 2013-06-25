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
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Point;
import com.msopentech.odatajclient.engine.types.ODataPropertyFormat;
import java.sql.Timestamp;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class PrimitiveValueTest extends AbstractTest {

    private ODataPrimitiveValue readPropertyValue(final String serviceRootURL,
            final String entity, final String propertyName) {

        ODataURIBuilder uriBuilder = new ODataURIBuilder(serviceRootURL);
        uriBuilder.appendEntityTypeSegment(entity).appendStructuralSegment(propertyName);

        ODataPropertyRequest req = ODataRetrieveRequestFactory.getPropertyRequest(uriBuilder.build());
        req.setFormat(ODataPropertyFormat.XML);

        final ODataQueryResponse<ODataProperty> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final ODataProperty property = res.getBody();
        assertNotNull(property);
        assertTrue(property.hasPrimitiveValue());

        assertNotNull(property.getPrimitiveValue());
        return property.getPrimitiveValue();
    }

    @Test
    public void string() {
        ODataPrimitiveValue opv = readPropertyValue(testODataServiceRootURL, "Customer(-10)", "CustomerId");
        assertEquals(EdmSimpleType.INT_32.toString(), opv.getTypeName());

        Integer value = opv.<Integer>toCastValue();
        assertNotNull(value);
        assertTrue(-10 == value);
    }

    @Test
    public void int32() {
        ODataPrimitiveValue opv = readPropertyValue(testODataServiceRootURL, "Product(-10)", "Description");
        assertEquals(EdmSimpleType.STRING.toString(), opv.getTypeName());

        String value = opv.<String>toCastValue();
        assertNotNull(value);
        assertEquals("onesusjnzuzrmzhqankkugdrftiukzkzqaggsfdmtvineulehkrbpu", value);
    }

    @Test
    public void decimal() {
        ODataPrimitiveValue opv = readPropertyValue(testODataServiceRootURL, "Product(-10)", "Dimensions/Width");
        assertEquals(EdmSimpleType.DECIMAL.toString(), opv.getTypeName());

        Float value = opv.<Float>toCastValue();
        assertNotNull(value);
        assertTrue(-79228162514264337593543950335F == value);
    }

    @Test
    public void datetime() {
        ODataPrimitiveValue opv = readPropertyValue(testODataServiceRootURL,
                "Product(-10)", "ComplexConcurrency/QueriedDateTime");
        assertEquals(EdmSimpleType.DATE_TIME.toString(), opv.getTypeName());

        Timestamp value = opv.<Timestamp>toCastValue();
        assertNotNull(value);
        assertEquals("2013-01-10T06:27:51.1667673", opv.toString());
    }

    @Test
    public void guid() {
        ODataPrimitiveValue opv = readPropertyValue(testODataServiceRootURL,
                "MessageAttachment(guid'1126a28b-a4af-4bbd-bf0a-2b2c22635565')", "AttachmentId");
        assertEquals(EdmSimpleType.GUID.toString(), opv.getTypeName());

        UUID value = opv.<UUID>toCastValue();
        assertNotNull(value);
        assertEquals("1126a28b-a4af-4bbd-bf0a-2b2c22635565", opv.toString());
    }

    @Test
    public void binary() {
        ODataPrimitiveValue opv = readPropertyValue(testODataServiceRootURL,
                "MessageAttachment(guid'1126a28b-a4af-4bbd-bf0a-2b2c22635565')", "Attachment");
        assertEquals(EdmSimpleType.BINARY.toString(), opv.getTypeName());

        byte[] value = opv.<byte[]>toCastValue();
        assertNotNull(value);
        assertTrue(value.length > 0);
        assertTrue(Base64.isBase64(opv.toString()));
    }

    @Test
    public void geographyPoint() {
        ODataPrimitiveValue opv = readPropertyValue(
                "http://services.odata.org/v3/(S(ds4nnexwejbv4fq3nqsx5vd1))/OData/OData.svc/",
                "Suppliers(1)", "Location");
        assertEquals(EdmSimpleType.GEOGRAPHY_POINT.toString(), opv.getTypeName());

        Point value = opv.<Point>toCastValue();
        assertNotNull(value);
        assertEquals(47.6472206115723, value.getX(), 0);
        assertEquals(-122.107711791992, value.getY(), 0);
        assertEquals("47.6472206115723 -122.107711791992", opv.toString());
    }
}
