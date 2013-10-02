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

import static org.junit.Assert.*;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.msopentech.odatajclient.engine.client.http.HttpMethod;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataDeleteRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataPropertyUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataValueUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataPropertyRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataValueRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataPropertyUpdateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataValueUpdateResponse;
import com.msopentech.odatajclient.engine.data.ODataCollectionValue;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.format.ODataValueFormat;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.utils.Configuration;

public class PropertyUpdateITCase extends AbstractTest {
    // update complex property

    public void updateComplexProperty(
            final ODataFormat format,
            final String contentType,
            final String prefer,
            final UpdateType type,
            final String entity,
            final String propertyType) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment(entity).appendKeySegment(-9).appendStructuralSegment(propertyType);

        ODataPropertyRequest retrieveReq = ODataRetrieveRequestFactory.getPropertyRequest(uriBuilder.build());
        retrieveReq.setFormat(format);
        retrieveReq.setContentType(contentType);
        retrieveReq.setPrefer(prefer);
        ODataRetrieveResponse<ODataProperty> retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());

        String etag = retrieveRes.getEtag();
        final ODataComplexValue dimensions = new ODataComplexValue(
                "Microsoft.Test.OData.Services.AstoriaDefaultService.Dimensions");
        dimensions.add(ODataFactory.newPrimitiveProperty("Width",
                new ODataPrimitiveValue.Builder().setText("-1.12").setType(EdmSimpleType.Decimal).build()));
        dimensions.add(ODataFactory.newPrimitiveProperty("Height",
                new ODataPrimitiveValue.Builder().setText("-1.12").setType(EdmSimpleType.Decimal).build()));
        dimensions.add(ODataFactory.newPrimitiveProperty("Depth",
                new ODataPrimitiveValue.Builder().setText("-1.12").setType(EdmSimpleType.Decimal).build()));

        ODataProperty dimensionToBeUpdated = ODataFactory.newComplexProperty("Dimensions",
                dimensions);

        final ODataPropertyUpdateRequest req = ODataCUDRequestFactory.getPropertyComplexValueUpdateRequest(uriBuilder.
                build(), type, dimensionToBeUpdated);
        if (Configuration.isUseXHTTPMethod()) {
            assertEquals(HttpMethod.POST, req.getMethod());
        } else {
            assertEquals(type.getMethod(), req.getMethod());
        }
        req.setFormat(format);
        req.setContentType(contentType);
        req.setPrefer(prefer);
        if (StringUtils.isNotBlank(etag)) {
            req.setIfMatch(etag);
        }
        final ODataPropertyUpdateResponse res = req.execute();

        if (prefer.equals("return-content")) {
            assertEquals(200, res.getStatusCode());
            ODataProperty property = res.getBody();
            ODataComplexValue value = property.getComplexValue();
            assertEquals(dimensions.get("Depth").getValue().toString(), value.get("Depth").getValue().toString());
            assertEquals(dimensions.get("Width").getValue().toString(), value.get("Width").getValue().toString());
            assertEquals(dimensions.get("Height").getValue().toString(), value.get("Height").getValue().toString());
        } else {
            assertEquals(204, res.getStatusCode());
        }
    }
    //update property with json full metadata

    @Test
    public void updatePropertyWithJSON() {
        final ODataFormat format = ODataFormat.JSON_FULL_METADATA;
        final String contentType = "application/json;odata=fullmetadata";
        final String prefer = "return-content";
        final String entitySet = "Product";
        final String propertyType = "Dimensions";
        final UpdateType replace = UpdateType.REPLACE;
        final UpdateType merge = UpdateType.MERGE;
        final UpdateType patch = UpdateType.PATCH;

        final String collectionEntity = "Customer";
        final String collectionPropertyType = "EmailBag";
        final String primitivePropertyType = "PhoneNumber";
        try {
            // update complex property
            updateComplexProperty(format, contentType, prefer, replace, entitySet, propertyType);
            updateComplexProperty(format, contentType, prefer, merge, entitySet, propertyType);
            updateComplexProperty(format, contentType, prefer, patch, entitySet, propertyType);

            // update collection property
            updateCollectionProperty(format, contentType, prefer, replace, collectionEntity, collectionPropertyType);
            updateCollectionProperty(format, contentType, prefer, merge, collectionEntity, collectionPropertyType);
            updateCollectionProperty(format, contentType, prefer, patch, collectionEntity, collectionPropertyType);

            // update primitive property
            updatePrimitiveProperty(format, contentType, prefer, replace, collectionEntity, primitivePropertyType);
            updatePrimitiveProperty(format, contentType, prefer, merge, collectionEntity, primitivePropertyType);
            updatePrimitiveProperty(format, contentType, prefer, patch, collectionEntity, primitivePropertyType);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    //update property with json minimal metadata

    @Test
    public void updatePropertyWithJSONMinimal() {
        final ODataFormat format = ODataFormat.JSON;
        final String contentType = "application/json;odata=minimalmetadata";
        final String prefer = "return-content";
        final String entitySet = "Product";
        final String propertyType = "Dimensions";
        final UpdateType replace = UpdateType.REPLACE;
        final UpdateType merge = UpdateType.MERGE;
        final UpdateType patch = UpdateType.PATCH;

        final String collectionEntity = "Customer";
        final String collectionPropertyType = "EmailBag";
        final String primitivePropertyType = "PhoneNumber";
        try {
            // update complex property
            updateComplexProperty(format, contentType, prefer, replace, entitySet, propertyType);
            updateComplexProperty(format, contentType, prefer, merge, entitySet, propertyType);
            updateComplexProperty(format, contentType, prefer, patch, entitySet, propertyType);

            // update collection property
            updateCollectionProperty(format, contentType, prefer, replace, collectionEntity, collectionPropertyType);
            updateCollectionProperty(format, contentType, prefer, merge, collectionEntity, collectionPropertyType);
            updateCollectionProperty(format, contentType, prefer, patch, collectionEntity, collectionPropertyType);

            // update primitive property
            updatePrimitiveProperty(format, contentType, prefer, replace, collectionEntity, primitivePropertyType);
            updatePrimitiveProperty(format, contentType, prefer, merge, collectionEntity, primitivePropertyType);
            updatePrimitiveProperty(format, contentType, prefer, patch, collectionEntity, primitivePropertyType);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    //update property with json no metadata

    @Test
    public void updatePropertyWithJSONNoMetadata() {
        final ODataFormat format = ODataFormat.JSON_FULL_METADATA;
        final String contentType = "application/json;odata=fullmetadata";
        final String prefer = "return-content";
        final String entitySet = "Product";
        final String propertyType = "Dimensions";
        final UpdateType replace = UpdateType.REPLACE;
        final UpdateType merge = UpdateType.MERGE;
        final UpdateType patch = UpdateType.PATCH;

        final String collectionEntity = "Customer";
        final String collectionPropertyType = "EmailBag";
        final String primitivePropertyType = "PhoneNumber";
        try {
            // update complex property
            updateComplexProperty(format, contentType, prefer, replace, entitySet, propertyType);
            updateComplexProperty(format, contentType, prefer, merge, entitySet, propertyType);
            updateComplexProperty(format, contentType, prefer, patch, entitySet, propertyType);

            // update collection property
            updateCollectionProperty(format, contentType, prefer, replace, collectionEntity, collectionPropertyType);
            updateCollectionProperty(format, contentType, prefer, merge, collectionEntity, collectionPropertyType);
            updateCollectionProperty(format, contentType, prefer, patch, collectionEntity, collectionPropertyType);

            // update primitive property
            updatePrimitiveProperty(format, contentType, prefer, replace, collectionEntity, primitivePropertyType);
            updatePrimitiveProperty(format, contentType, prefer, merge, collectionEntity, primitivePropertyType);
            updatePrimitiveProperty(format, contentType, prefer, patch, collectionEntity, primitivePropertyType);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    //update with xml format

    @Test
    public void updatePropertyWithXML() {
        final ODataFormat format = ODataFormat.XML;
        final String contentType = "application/xml";
        final String prefer = "return-content";
        final String entitySet = "ComputerDetail";
        final String propertyType = "Dimensions";
        final UpdateType replace = UpdateType.REPLACE;
        final UpdateType merge = UpdateType.MERGE;
        final UpdateType patch = UpdateType.PATCH;

        final String collectionEntity = "Customer";
        final String collectionPropertyType = "AlternativeNames";

        final String primitivePropertyType = "PhoneNumber";
        try {
            // update complex property
            updateComplexProperty(format, contentType, prefer, replace, entitySet, propertyType);
            updateComplexProperty(format, contentType, prefer, merge, entitySet, propertyType);
            updateComplexProperty(format, contentType, prefer, patch, entitySet, propertyType);

            // update collection property
            updateCollectionProperty(format, contentType, prefer, replace, collectionEntity, collectionPropertyType);
            updateCollectionProperty(format, contentType, prefer, merge, collectionEntity, collectionPropertyType);
            updateCollectionProperty(format, contentType, prefer, patch, collectionEntity, collectionPropertyType);

            // update primitive property
            updatePrimitiveProperty(format, contentType, prefer, replace, collectionEntity, primitivePropertyType);
            updatePrimitiveProperty(format, contentType, prefer, merge, collectionEntity, primitivePropertyType);
            updatePrimitiveProperty(format, contentType, prefer, patch, collectionEntity, primitivePropertyType);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    // update with XML format and json contentType

    @Test
    public void updatePropertyWithXMLAndJSON() {
        final ODataFormat format = ODataFormat.XML;
        final String contentType = "application/json;odata=fullmetadata";
        final String prefer = "return-content";
        final String entitySet = "ComputerDetail";
        final String propertyType = "Dimensions";
        final UpdateType replace = UpdateType.REPLACE;
        final UpdateType merge = UpdateType.MERGE;
        final UpdateType patch = UpdateType.PATCH;

        final String collectionEntity = "Customer";
        final String collectionPropertyType = "AlternativeNames";
        final String primitivePropertyType = "PhoneNumber";
        try {
            // update complex property
            updateComplexProperty(format, contentType, prefer, replace, entitySet, propertyType);
            updateComplexProperty(format, contentType, prefer, merge, entitySet, propertyType);
            updateComplexProperty(format, contentType, prefer, patch, entitySet, propertyType);

            // update collection property
            updateCollectionProperty(format, contentType, prefer, replace, collectionEntity, collectionPropertyType);
            updateCollectionProperty(format, contentType, prefer, merge, collectionEntity, collectionPropertyType);
            updateCollectionProperty(format, contentType, prefer, patch, collectionEntity, collectionPropertyType);

            // update primitive property
            updatePrimitiveProperty(format, contentType, prefer, replace, collectionEntity, primitivePropertyType);
            updatePrimitiveProperty(format, contentType, prefer, merge, collectionEntity, primitivePropertyType);
            updatePrimitiveProperty(format, contentType, prefer, patch, collectionEntity, primitivePropertyType);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    // update operation with xml content type header

    @Test
    public void updatePropertyWithJSONAndXML() {
        final ODataFormat format = ODataFormat.JSON_FULL_METADATA;
        final String contentType = "application/xml";
        final String prefer = "return-content";
        final String entitySet = "ComputerDetail";
        final String propertyType = "Dimensions";
        final UpdateType replace = UpdateType.REPLACE;
        final UpdateType merge = UpdateType.MERGE;
        final UpdateType patch = UpdateType.PATCH;

        final String collectionEntity = "Customer";
        final String collectionPropertyType = "AlternativeNames";
        final String primitivePropertyType = "PhoneNumber";
        try {
            // update complex property
            updateComplexProperty(format, contentType, prefer, replace, entitySet, propertyType);
            updateComplexProperty(format, contentType, prefer, merge, entitySet, propertyType);
            updateComplexProperty(format, contentType, prefer, patch, entitySet, propertyType);

            // update collection property
            updateCollectionProperty(format, contentType, prefer, replace, collectionEntity, collectionPropertyType);
            updateCollectionProperty(format, contentType, prefer, merge, collectionEntity, collectionPropertyType);
            updateCollectionProperty(format, contentType, prefer, patch, collectionEntity, collectionPropertyType);

            // update primitive property
            updatePrimitiveProperty(format, contentType, prefer, replace, collectionEntity, primitivePropertyType);
            updatePrimitiveProperty(format, contentType, prefer, merge, collectionEntity, primitivePropertyType);
            updatePrimitiveProperty(format, contentType, prefer, patch, collectionEntity, primitivePropertyType);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    // update collection property

    public void updateCollectionProperty(
            final ODataFormat format,
            final String contentType,
            final String prefer,
            final UpdateType type,
            final String entity,
            final String propertyType) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment(entity).appendKeySegment(-9).appendStructuralSegment("PrimaryContactInfo").
                appendStructuralSegment(propertyType);

        ODataPropertyRequest retrieveReq = ODataRetrieveRequestFactory.getPropertyRequest(uriBuilder.build());
        retrieveReq.setFormat(format);
        retrieveReq.setContentType(contentType);
        retrieveReq.setPrefer(prefer);
        ODataRetrieveResponse<ODataProperty> retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());
        String etag = retrieveRes.getEtag();

        ODataProperty updateProperty = ODataFactory.newCollectionProperty(propertyType,
                retrieveRes.getBody().getCollectionValue());

        final String newItem = "new update " + System.currentTimeMillis();

        final ODataCollectionValue originalValue = updateProperty.getCollectionValue();

        final int origSize = originalValue.size();

        originalValue.add(new ODataPrimitiveValue.Builder().setText(newItem).build());
        assertEquals(origSize + 1, originalValue.size());
        final ODataPropertyUpdateRequest updateReq =
                ODataCUDRequestFactory.getPropertyCollectionValueUpdateRequest(uriBuilder.build(), updateProperty);
        if (Configuration.isUseXHTTPMethod()) {
            assertEquals(HttpMethod.POST, updateReq.getMethod());
        } else {
            assertEquals(HttpMethod.PUT, updateReq.getMethod());
        }
        updateReq.setFormat(format);
        updateReq.setContentType(contentType);
        updateReq.setPrefer(prefer);
        if (StringUtils.isNotBlank(etag)) {
            updateReq.setIfMatch(etag);
        }
        ODataPropertyUpdateResponse updateRes = updateReq.execute();
        if (prefer.equals("return-content")) {
            assertEquals(200, updateRes.getStatusCode());
            updateProperty = updateRes.getBody();
            assertEquals(origSize + 1, updateProperty.getCollectionValue().size());
        } else {
            assertEquals(204, updateRes.getStatusCode());
        }
    }
    // update primitive property

    public void updatePrimitiveProperty(
            final ODataFormat format,
            final String contentType,
            final String prefer,
            final UpdateType type,
            final String entity,
            final String propertyType) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment(entity).appendKeySegment(-9).
                appendStructuralSegment("PrimaryContactInfo")
                .appendStructuralSegment("HomePhone")
                .appendStructuralSegment(propertyType);

        ODataPropertyRequest retrieveReq = ODataRetrieveRequestFactory.getPropertyRequest(uriBuilder.build());
        retrieveReq.setFormat(format);
        retrieveReq.setContentType(contentType);
        retrieveReq.setPrefer(prefer);
        ODataRetrieveResponse<ODataProperty> retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());
        String etag = retrieveRes.getEtag();
        ODataProperty updateProperty = retrieveRes.getBody();
        final String oldItem = updateProperty.getPrimitiveValue().<String>toCastValue();
        final String newItem = "new update " + System.currentTimeMillis();
        assertNotEquals(oldItem, newItem);

        updateProperty = ODataFactory.newPrimitiveProperty(propertyType,
                new ODataPrimitiveValue.Builder().setText(newItem).build());

        final ODataPropertyUpdateRequest updateReq =
                ODataCUDRequestFactory.getPropertyPrimitiveValueUpdateRequest(uriBuilder.build(), updateProperty);
        if (Configuration.isUseXHTTPMethod()) {
            assertEquals(HttpMethod.POST, updateReq.getMethod());
        } else {
            assertEquals(HttpMethod.PUT, updateReq.getMethod());
        }
        updateReq.setFormat(format);
        updateReq.setContentType(contentType);
        updateReq.setPrefer(prefer);
        if (StringUtils.isNotBlank(etag)) {
            updateReq.setIfMatch(etag);
        }
        ODataPropertyUpdateResponse updateRes = updateReq.execute();
        if (prefer.equals("return-content")) {
            assertEquals(200, updateRes.getStatusCode());
            updateProperty = updateRes.getBody();
            assertEquals(newItem, updateProperty.getPrimitiveValue().toString());
        } else {
            assertEquals(204, updateRes.getStatusCode());
        }
    }
    // update String property value

    public void updateStringPropertyValue(
            final ODataValueFormat format,
            final String contentType,
            final String prefer,
            final UpdateType type) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("Customer").appendKeySegment(-8).
                appendStructuralSegment("PrimaryContactInfo").
                appendStructuralSegment("WorkPhone").
                appendStructuralSegment("PhoneNumber").
                appendValueSegment();
        ODataValueRequest retrieveReq = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
        retrieveReq.setFormat(format);
        retrieveReq.setPrefer(prefer);
        ODataRetrieveResponse<ODataValue> retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());
        String etag = retrieveRes.getEtag();
        ODataValue phoneNumber = retrieveRes.getBody();
        assertNotNull(phoneNumber);

        final String oldItem = phoneNumber.asPrimitive().<String>toCastValue();
        final String newItem = "new msg value " + System.currentTimeMillis();

        assertNotEquals(oldItem, newItem);

        final ODataPrimitiveValue newVal = new ODataPrimitiveValue.Builder().setText(newItem).build();
        final ODataValueUpdateRequest updateReq =
                ODataCUDRequestFactory.getValueUpdateRequest(uriBuilder.build(), type, newVal);
        updateReq.setFormat(ODataValueFormat.TEXT);
        updateReq.setPrefer(prefer);
        if (StringUtils.isNotBlank(etag)) {
            updateReq.setIfMatch(etag);
        }
        final ODataValueUpdateResponse updateRes = updateReq.execute();
        if (prefer.equals("return-content")) {
            assertEquals(200, updateRes.getStatusCode());
            phoneNumber = updateRes.getBody();
            assertNotNull(phoneNumber);
            assertEquals(newItem, phoneNumber.asPrimitive().<String>toCastValue());
        } else {
            assertEquals(204, updateRes.getStatusCode());
        }
        retrieveReq = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
        retrieveReq.setFormat(format);
        retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());

        phoneNumber = retrieveRes.getBody();
        assertNotNull(phoneNumber);

        assertEquals(newItem, phoneNumber.asPrimitive().<String>toCastValue());
    }
    // update string property value 

    @Test
    public void stringPropertyValue() {
        final ODataValueFormat format = ODataValueFormat.TEXT;
        final String contentType = "application/json";
        final String prefer = "return-content";
        final UpdateType replace = UpdateType.REPLACE;
        final UpdateType merge = UpdateType.MERGE;
        final UpdateType patch = UpdateType.PATCH;

        try {
            // update string property value with different update types
            updateStringPropertyValue(format, contentType, prefer, merge);
            updateStringPropertyValue(format, contentType, prefer, replace);
            updateStringPropertyValue(format, contentType, prefer, patch);

        } catch (Exception e) {
            LOG.error("", e);
            fail(e.getMessage());
        }
    }
    // update date property value

    public static void updateDatePropertyValue(
            final ODataValueFormat format,
            final String contentType,
            final String prefer,
            final UpdateType type) {
        try {
            final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                    appendEntityTypeSegment("ComputerDetail").appendKeySegment(-10).
                    appendStructuralSegment("PurchaseDate").
                    appendValueSegment();
            ODataValueRequest retrieveReq = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
            retrieveReq.setFormat(format);
            retrieveReq.setPrefer(prefer);
            ODataRetrieveResponse<ODataValue> retrieveRes = retrieveReq.execute();
            assertEquals(200, retrieveRes.getStatusCode());
            String etag = retrieveRes.getEtag();
            ODataValue date = retrieveRes.getBody();
            assertNotNull(date);
            final String newItem = "2005-02-09T23:59:59.9999999";
            final ODataPrimitiveValue newVal = new ODataPrimitiveValue.Builder().setText(newItem).build();
            final ODataValueUpdateRequest updateReq =
                    ODataCUDRequestFactory.getValueUpdateRequest(uriBuilder.build(), type, newVal);
            updateReq.setFormat(ODataValueFormat.TEXT);
            updateReq.setPrefer(prefer);
            if (StringUtils.isNotBlank(etag)) {
                updateReq.setIfMatch(etag);
            }
            final ODataValueUpdateResponse updateRes = updateReq.execute();

            if (prefer.equals("return-content")) {
                assertEquals(200, updateRes.getStatusCode());
                ODataValue val = updateRes.getBody();
                assertNotNull(val);
                assertEquals(newItem, val.asPrimitive().<String>toCastValue());
            } else {
                assertEquals(204, updateRes.getStatusCode());
            }
            retrieveReq = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
            retrieveReq.setFormat(format);
            retrieveRes = retrieveReq.execute();
            assertEquals(200, retrieveRes.getStatusCode());
            date = retrieveRes.getBody();
            assertNotNull(date);
            assertEquals(newItem, date.asPrimitive().<String>toCastValue());
        } catch (Exception e) {
            LOG.error("", e);
            fail(e.getMessage());
        }
    }
    // update string property value 

    @Test
    public void datePropertyValue() {
        final ODataValueFormat format = ODataValueFormat.TEXT;
        final String contentType = "application/json";
        final String prefer = "return-content";
        final UpdateType replace = UpdateType.REPLACE;
        final UpdateType merge = UpdateType.MERGE;
        final UpdateType patch = UpdateType.PATCH;

        try {
            // update date property value with different update types
            updateDatePropertyValue(format, contentType, prefer, merge);
            updateDatePropertyValue(format, contentType, prefer, replace);
            updateDatePropertyValue(format, contentType, prefer, patch);

        } catch (Exception e) {
            LOG.error("", e);
            fail(e.getMessage());
        }
    }

    // update date property value
    public static void updateIntegerPropertyValue(
            final ODataValueFormat format,
            final String contentType,
            final String prefer,
            final UpdateType type) {
        try {
            final HashMap<String, Object> multiKey = new HashMap<String, Object>();
            multiKey.put("OrderId", -10);
            multiKey.put("ProductId", -10);
            final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                    appendEntityTypeSegment("OrderLine").appendKeySegment(multiKey).
                    appendStructuralSegment("Quantity").
                    appendValueSegment();
            ODataValueRequest retrieveReq = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
            retrieveReq.setFormat(format);
            retrieveReq.setPrefer(prefer);
            ODataRetrieveResponse<ODataValue> retrieveRes = retrieveReq.execute();
            assertEquals(200, retrieveRes.getStatusCode());
            String etag = retrieveRes.getEtag();
            ODataValue integerValue = retrieveRes.getBody();
            assertNotNull(integerValue);

            final int newItem = 4444;

            final ODataPrimitiveValue newVal = new ODataPrimitiveValue.Builder().setValue(newItem).setType(
                    EdmSimpleType.Int32).build();
            final ODataValueUpdateRequest updateReq =
                    ODataCUDRequestFactory.getValueUpdateRequest(uriBuilder.build(), type, newVal);
            updateReq.setFormat(ODataValueFormat.TEXT);
            updateReq.setPrefer(prefer);
            if (StringUtils.isNotBlank(etag)) {
                updateReq.setIfMatch(etag);
            }
            final ODataValueUpdateResponse updateRes = updateReq.execute();
            if (prefer.equals("return-content")) {
                assertEquals(200, updateRes.getStatusCode());
                ODataValue val = updateRes.getBody();
                assertNotNull(val);
                assertEquals(newItem, Integer.parseInt(val.toString()));
            } else {
                assertEquals(204, updateRes.getStatusCode());
            }
            retrieveReq = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
            retrieveReq.setFormat(format);
            retrieveRes = retrieveReq.execute();
            assertEquals(200, retrieveRes.getStatusCode());
            integerValue = retrieveRes.getBody();
            assertNotNull(integerValue);
            assertEquals(newItem, Integer.parseInt(integerValue.toString()));
        } catch (Exception e) {
            LOG.error("", e);
            fail(e.getMessage());
        }
    }
    // update int property value 

    @Test
    public void intPropertyValue() {
        final ODataValueFormat format = ODataValueFormat.TEXT;
        final String contentType = "application/json";
        final String prefer = "return-content";
        final UpdateType replace = UpdateType.REPLACE;
        final UpdateType merge = UpdateType.MERGE;
        final UpdateType patch = UpdateType.PATCH;

        try {
            // update int property value with different update types
            updateIntegerPropertyValue(format, contentType, prefer, merge);
            updateIntegerPropertyValue(format, contentType, prefer, replace);
            updateIntegerPropertyValue(format, contentType, prefer, patch);

        } catch (Exception e) {
            LOG.error("", e);
            fail(e.getMessage());
        }
    }

    // update decimal property value
    public void updateDecimalPropertyValue(
            final ODataValueFormat format,
            final String contentType,
            final String prefer,
            final UpdateType type) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("ComputerDetail").appendKeySegment(-9).
                appendStructuralSegment("Dimensions").
                appendStructuralSegment("Width").
                appendValueSegment();
        ODataValueRequest retrieveReq = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
        retrieveReq.setFormat(format);
        retrieveReq.setPrefer(prefer);
        ODataRetrieveResponse<ODataValue> retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());
        String etag = retrieveRes.getEtag();
        ODataValue decimalValue = retrieveRes.getBody();
        assertNotNull(decimalValue);

        final String oldItem = decimalValue.asPrimitive().<String>toCastValue();
        final String newItem = "-3.43" + System.currentTimeMillis();

        assertNotEquals(oldItem, newItem);

        final ODataPrimitiveValue newVal = new ODataPrimitiveValue.Builder().setText(newItem).build();
        final ODataValueUpdateRequest updateReq =
                ODataCUDRequestFactory.getValueUpdateRequest(uriBuilder.build(), type, newVal);
        updateReq.setFormat(ODataValueFormat.TEXT);
        updateReq.setPrefer(prefer);
        if (StringUtils.isNotBlank(etag)) {
            updateReq.setIfMatch(etag);
        }
        final ODataValueUpdateResponse updateRes = updateReq.execute();
        if (prefer.equals("return-content")) {
            assertEquals(200, updateRes.getStatusCode());
        } else {
            assertEquals(204, updateRes.getStatusCode());
        }
        retrieveReq = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
        retrieveReq.setFormat(format);
        retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());

        decimalValue = retrieveRes.getBody();
        assertNotNull(decimalValue);

        assertEquals(newItem, decimalValue.asPrimitive().<String>toCastValue());
    }
    // update string property value 

    @Test
    public void decimalPropertyValue() {
        final ODataValueFormat format = ODataValueFormat.TEXT;
        final String contentType = "application/json";
        final String prefer = "return-content";
        final UpdateType replace = UpdateType.REPLACE;
        final UpdateType merge = UpdateType.MERGE;
        final UpdateType patch = UpdateType.PATCH;

        try {
            // update decimal property value with different update types
            updateDecimalPropertyValue(format, contentType, prefer, merge);
            updateDecimalPropertyValue(format, contentType, prefer, replace);
            updateDecimalPropertyValue(format, contentType, prefer, patch);

        } catch (Exception e) {
            LOG.error("", e);
            fail(e.getMessage());
        }
    }
    // update decimal property value

    public void updateBooleanPropertyValue(
            final ODataValueFormat format,
            final String contentType,
            final String prefer,
            final UpdateType type) {
        final HashMap<String, Object> multiKey = new HashMap<String, Object>();
        multiKey.put("MessageId", -1);
        multiKey.put("FromUsername", "10");
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("Message").appendKeySegment(multiKey).
                appendStructuralSegment("IsRead").
                appendValueSegment();
        ODataValueRequest retrieveReq = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
        retrieveReq.setFormat(format);
        retrieveReq.setPrefer(prefer);
        ODataRetrieveResponse<ODataValue> retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());
        String etag = retrieveRes.getEtag();
        ODataValue booleanValue = retrieveRes.getBody();
        final boolean oldItem = Boolean.valueOf(booleanValue.toString());
        final ODataPrimitiveValue newVal = new ODataPrimitiveValue.Builder().setValue(!oldItem).setType(
                EdmSimpleType.Boolean).build();
        final ODataValueUpdateRequest updateReq =
                ODataCUDRequestFactory.getValueUpdateRequest(uriBuilder.build(), type, newVal);
        updateReq.setFormat(ODataValueFormat.TEXT);
        updateReq.setPrefer(prefer);
        if (StringUtils.isNotBlank(etag)) {
            updateReq.setIfMatch(etag);
        }
        final ODataValueUpdateResponse updateRes = updateReq.execute();
        if (prefer.equals("return-content")) {
            assertEquals(200, updateRes.getStatusCode());
        } else {
            assertEquals(204, updateRes.getStatusCode());
        }
        retrieveReq = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
        retrieveReq.setFormat(format);
        retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());

        ODataValue val = retrieveRes.getBody();
        assertEquals(!oldItem, Boolean.valueOf(val.toString()));
    }
    // update boolean property value 

    @Test
    public void booleanPropertyValue() {
        final ODataValueFormat format = ODataValueFormat.TEXT;
        final String contentType = "application/json";
        final String prefer = "return-content";
        final UpdateType replace = UpdateType.REPLACE;
        final UpdateType merge = UpdateType.MERGE;
        final UpdateType patch = UpdateType.PATCH;

        try {
            // update boolean property value with different update types
            updateBooleanPropertyValue(format, contentType, prefer, merge);
            updateBooleanPropertyValue(format, contentType, prefer, replace);
            updateBooleanPropertyValue(format, contentType, prefer, patch);

        } catch (Exception e) {
            LOG.error("", e);
            fail(e.getMessage());
        }
    }

    // update date property value
    public static void updatePrimaryKeyPropertyValue(
            final ODataValueFormat format,
            final String contentType,
            final String prefer,
            final UpdateType type) {
        try {
            final HashMap<String, Object> multiKey = new HashMap<String, Object>();
            multiKey.put("OrderId", -10);
            multiKey.put("ProductId", -10);
            final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                    appendEntityTypeSegment("OrderLine").appendKeySegment(multiKey).
                    appendStructuralSegment("OrderId").
                    appendValueSegment();
            ODataValueRequest retrieveReq = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
            retrieveReq.setFormat(format);
            retrieveReq.setPrefer(prefer);
            ODataRetrieveResponse<ODataValue> retrieveRes = retrieveReq.execute();
            assertEquals(200, retrieveRes.getStatusCode());
            String etag = retrieveRes.getEtag();
            ODataValue integerValue = retrieveRes.getBody();
            assertNotNull(integerValue);

            final int newItem = 32;

            final ODataPrimitiveValue newVal = new ODataPrimitiveValue.Builder().setValue(newItem).setType(
                    EdmSimpleType.Int32).build();
            final ODataValueUpdateRequest updateReq =
                    ODataCUDRequestFactory.getValueUpdateRequest(uriBuilder.build(), type, newVal);
            updateReq.setFormat(ODataValueFormat.TEXT);
            updateReq.setPrefer(prefer);
            if (StringUtils.isNotBlank(etag)) {
                updateReq.setIfMatch(etag);
            }
            final ODataValueUpdateResponse updateRes = updateReq.execute();
            if (prefer.equals("return-content")) {
                assertEquals(200, updateRes.getStatusCode());
                ODataValue val = retrieveRes.getBody();
                assertNotNull(val);
                assertEquals(newItem, Integer.parseInt(val.toString()));
            } else {
                assertEquals(204, updateRes.getStatusCode());
            }

            retrieveReq = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
            retrieveReq.setFormat(format);
            retrieveRes = retrieveReq.execute();
            assertEquals(200, retrieveRes.getStatusCode());
            integerValue = retrieveRes.getBody();
            assertNotNull(integerValue);
            assertEquals(newItem, Integer.parseInt(integerValue.toString()));
        } catch (Exception e) {
            // trying to update primary key will throw 400 error.
        }
    }
    // update int property value 

    @Test
    public void primaryKeyPropertyValue() {
        final ODataValueFormat format = ODataValueFormat.TEXT;
        final String contentType = "application/json";
        final String prefer = "return-content";
        final UpdateType replace = UpdateType.REPLACE;
        final UpdateType merge = UpdateType.MERGE;
        final UpdateType patch = UpdateType.PATCH;

        try {
            // update int property value with different update types
            updatePrimaryKeyPropertyValue(format, contentType, prefer, merge);
            updatePrimaryKeyPropertyValue(format, contentType, prefer, replace);
            updatePrimaryKeyPropertyValue(format, contentType, prefer, patch);

        } catch (Exception e) {
            // trying to update primary key will throw 400 error.
        }
    }
    // delete a non nullable property

    public void deleteNonNullableproperty(final ODataPubFormat format, final String contentType) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("Customer").appendKeySegment(-10).
                appendStructuralSegment("CustomerId").
                appendValueSegment();
        ODataDeleteRequest deleteReq = ODataCUDRequestFactory.getDeleteRequest(uriBuilder.build());
        deleteReq.setFormat(format);
        deleteReq.setContentType(contentType);
        ODataDeleteResponse deleteRes = deleteReq.execute();
    }
    // delete operation with JSON full metadata

    @Test
    public void testDeletePrimaryKeyWithJSON() {
        ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        String contentType = "application/json;odata=fullemtadata";
        try {
            deleteNonNullableproperty(format, contentType);
        } catch (Exception e) {
            // cannot delete primary key property. Hence this test return 400 status.
        }
    }
    // delete operation with ATOM

    @Test
    public void testDeletePrimaryKeyWithATOM() {
        ODataPubFormat format = ODataPubFormat.ATOM;
        String contentType = "application/atom+xml";
        try {
            deleteNonNullableproperty(format, contentType);
        } catch (Exception e) {
            // cannot delete primary key property. Hence this test return 400 status.
        }
    }
    // delete operation with atom accept and json contentType header

    @Test
    public void testDeletePrimaryKeyWithATOMAndJSON() {
        ODataPubFormat format = ODataPubFormat.ATOM;
        String contentType = "application/json;odata=fullmetadata";
        try {
            deleteNonNullableproperty(format, contentType);
        } catch (Exception e) {
            // cannot delete primary key property. Hence this test return 400 status.
        }
    }
    // delete operation with json accept and atom contentType header

    @Test
    public void testDeletePrimaryKeyWithJSONAndATOM() {
        ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        String contentType = "application/atom+xml";
        try {
            deleteNonNullableproperty(format, contentType);
        } catch (Exception e) {
            // cannot delete primary key property. Hence this test return 400 status.
        }
    }
    // delete operation with json no metadata

    @Test
    public void testDeletePrimaryKeyWithJSONNoMetadata() {
        ODataPubFormat format = ODataPubFormat.JSON_NO_METADATA;
        String contentType = "application/json;odata=nometadata";
        try {
            deleteNonNullableproperty(format, contentType);
        } catch (Exception e) {
            // cannot delete primary key property. Hence this test return 400 status.
        }
    }

    // delete a nullable property
    public void deleteNullableProperty(final ODataPubFormat format, final String contentType) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("Customer").appendKeySegment(-10).
                appendStructuralSegment("Name").
                appendValueSegment();
        ODataDeleteRequest deleteReq = ODataCUDRequestFactory.getDeleteRequest(uriBuilder.build());
        deleteReq.setFormat(format);
        deleteReq.setContentType(contentType);
        ODataDeleteResponse deleteRes = deleteReq.execute();
        assertEquals(204, deleteRes.getStatusCode());
    }
    // delete operation with JSON full metadata

    @Test
    public void testDeleteNullableWithJSON() {
        ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        String contentType = "application/json;odata=fullemtadata";
        try {
            deleteNullableProperty(format, contentType);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    // delete operation with ATOM

    @Test
    public void testDeleteNullableWithATOM() {
        ODataPubFormat format = ODataPubFormat.ATOM;
        String contentType = "application/atom+xml";
        try {
            deleteNullableProperty(format, contentType);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    // delete operation with atom accept and json contentType header

    @Test
    public void testDeleteNullableWithATOMAndJSON() {
        ODataPubFormat format = ODataPubFormat.ATOM;
        String contentType = "application/json;odata=fullmetadata";
        try {
            deleteNullableProperty(format, contentType);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    // delete operation with json accept and atom contentType header

    @Test
    public void testDeleteNullableWithJSONAndATOM() {
        ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        String contentType = "application/atom+xml";
        try {
            deleteNullableProperty(format, contentType);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    // delete operation with json no metadata

    @Test
    public void testDeleteNullableWithJSONNoMetadata() {
        ODataPubFormat format = ODataPubFormat.JSON_NO_METADATA;
        String contentType = "application/json;odata=nometadata";
        try {
            deleteNullableProperty(format, contentType);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
