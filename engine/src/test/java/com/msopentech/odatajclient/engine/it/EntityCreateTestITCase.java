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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.msopentech.odatajclient.engine.client.http.NoContentException;
import com.msopentech.odatajclient.engine.communication.header.ODataHeaderValues;
import com.msopentech.odatajclient.engine.communication.header.ODataHeaders;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import org.junit.Test;

/**
 * This is the unit test class to check create entity operations.
 */
public class EntityCreateTestITCase extends AbstractTest {

    protected String getServiceRoot() {
        return testDefaultServiceRootURL;
    }

    @Test
    public void createAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final int id = 1;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", false);

        createEntity(getServiceRoot(), format, original);
        final ODataEntity actual = compareEntities(getServiceRoot(), format, original, id, null);

        cleanAfterCreate(format, actual, false);
    }

    @Test
    public void createAsJSON() {
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final int id = 2;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", false);

        createEntity(getServiceRoot(), format, original);
        final ODataEntity actual = compareEntities(getServiceRoot(), format, original, id, null);

        cleanAfterCreate(format, actual, false);
    }

    @Test
    public void createWithInlineAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final int id = 3;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", true);

        createEntity(getServiceRoot(), format, original);
        final ODataEntity actual =
                compareEntities(getServiceRoot(), format, original, id, Collections.<String>singleton("Info"));

        cleanAfterCreate(format, actual, true);
    }

    @Test
    public void createWithInlineAsJSON() {
        // this needs to be full, otherwise there is no mean to recognize links
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final int id = 4;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", true);

        createEntity(getServiceRoot(), format, original);
        final ODataEntity actual =
                compareEntities(getServiceRoot(), format, original, id, Collections.<String>singleton("Info"));

        cleanAfterCreate(format, actual, true);
    }

    @Test
    public void createInlineWithoutLinkAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final int id = 5;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", false);

        original.addLink(ODataFactory.newInlineEntity(
                "Info", null, getSampleCustomerInfo(id, "Sample Customer_Info")));

        createEntity(getServiceRoot(), format, original);
        final ODataEntity actual =
                compareEntities(getServiceRoot(), format, original, id, Collections.<String>singleton("Info"));

        boolean found = false;

        for (ODataLink link : actual.getNavigationLinks()) {
            assertNotNull(link.getLink());
            if (link.getLink().toASCIIString().endsWith("Customer(" + id + ")/Info")) {
                found = true;
            }
        }

        assertTrue(found);

        cleanAfterCreate(format, actual, true);
    }

    @Test
    public void createInlineWithoutLinkAsJSON() {
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final int id = 6;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", false);

        original.addLink(ODataFactory.newInlineEntity(
                "Info", null, getSampleCustomerInfo(id, "Sample Customer_Info")));

        createEntity(getServiceRoot(), format, original);
        final ODataEntity actual =
                compareEntities(getServiceRoot(), format, original, id, Collections.<String>singleton("Info"));

        boolean found = false;

        for (ODataLink link : actual.getNavigationLinks()) {
            assertNotNull(link.getLink());
            if (link.getLink().toASCIIString().endsWith("Customer(" + id + ")/Info")) {
                found = true;
            }
        }

        assertTrue(found);

        cleanAfterCreate(format, actual, true);
    }

    private ODataEntity createWithNavigationLink(final ODataPubFormat format, final int id) {
        final String sampleName = "Sample customer";

        final ODataEntity original = getSampleCustomerProfile(id, sampleName, false);
        original.addLink(ODataFactory.newEntityNavigationLink(
                "Info", URI.create(getServiceRoot() + "/CustomerInfo(12)")));

        final ODataEntity created = createEntity(getServiceRoot(), format, original);
        // now, compare the created one with the actual one and go deeply into the associated customer info.....
        final ODataEntity actual = compareEntities(getServiceRoot(), format, created, id, null);

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(getServiceRoot());
        uriBuilder.appendEntityTypeSegment("Customer").appendKeySegment(id).appendEntityTypeSegment("Info");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataRetrieveResponse<ODataEntity> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final ODataEntity info = res.getBody();
        assertNotNull(info);

        boolean found = false;

        for (ODataProperty prop : info.getProperties()) {
            if ("CustomerInfoId".equals(prop.getName())) {
                assertEquals("12", prop.getValue().toString());
                found = true;
            }
        }

        assertTrue(found);

        return actual;
    }

    @Test
    public void createWithNavigationAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final ODataEntity actual = createWithNavigationLink(format, 5);
        cleanAfterCreate(format, actual, false);
    }

    @Test
    public void createWithNavigationAsJSON() {
        // this needs to be full, otherwise there is no mean to recognize links
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final ODataEntity actual = createWithNavigationLink(format, 6);
        cleanAfterCreate(format, actual, false);
    }

    private void multiKey(final ODataPubFormat format) {
        final ODataEntity message = ODataFactory.newEntity(
                "Microsoft.Test.OData.Services.AstoriaDefaultService.Message");

        message.addProperty(ODataFactory.newPrimitiveProperty("MessageId",
                new ODataPrimitiveValue.Builder().setValue(1000).setType(EdmSimpleType.INT_32).build()));
        message.addProperty(ODataFactory.newPrimitiveProperty("FromUsername",
                new ODataPrimitiveValue.Builder().setValue("1").
                setType(EdmSimpleType.STRING).build()));
        message.addProperty(ODataFactory.newPrimitiveProperty("ToUsername",
                new ODataPrimitiveValue.Builder().setValue("xlodhxzzusxecbzptxlfxprneoxkn").
                setType(EdmSimpleType.STRING).build()));
        message.addProperty(ODataFactory.newPrimitiveProperty("Subject",
                new ODataPrimitiveValue.Builder().setValue("Test subject").
                setType(EdmSimpleType.STRING).build()));
        message.addProperty(ODataFactory.newPrimitiveProperty("Body",
                new ODataPrimitiveValue.Builder().setValue("Test body").
                setType(EdmSimpleType.STRING).build()));
        message.addProperty(ODataFactory.newPrimitiveProperty("IsRead",
                new ODataPrimitiveValue.Builder().setValue(false).setType(EdmSimpleType.BOOLEAN).build()));

        final ODataURIBuilder builder =
                new ODataURIBuilder(getServiceRoot()).appendEntitySetSegment("Message");
        final ODataEntityCreateRequest req = ODataCUDRequestFactory.getEntityCreateRequest(builder.build(), message);
        req.setFormat(format);

        final ODataEntityCreateResponse res = req.execute();
        assertNotNull(res);
        assertEquals(201, res.getStatusCode());

        final LinkedHashMap<String, Object> multiKey = new LinkedHashMap<String, Object>();
        multiKey.put("FromUsername", "1");
        multiKey.put("MessageId", 1000);

        final ODataDeleteResponse deleteRes = ODataCUDRequestFactory.
                getDeleteRequest(builder.appendKeySegment(multiKey).build()).execute();
        assertEquals(204, deleteRes.getStatusCode());
    }

    @Test
    public void multiKeyAsAtom() {
        multiKey(ODataPubFormat.ATOM);
    }

    @Test
    public void multiKeyAsJSON() {
        multiKey(ODataPubFormat.JSON);
    }

    @Test
    public void createReturnNoContent() {
        final int id = 1;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", false);

        final ODataEntityCreateRequest createReq = ODataCUDRequestFactory.getEntityCreateRequest(
                new ODataURIBuilder(getServiceRoot()).appendEntitySetSegment("Customer").build(), original);
        createReq.setPrefer(ODataHeaderValues.preferReturnNoContent);

        final ODataEntityCreateResponse createRes = createReq.execute();
        assertEquals(204, createRes.getStatusCode());
        assertEquals(ODataHeaderValues.preferReturnNoContent,
                createRes.getHeader(ODataHeaders.HeaderName.preferenceApplied).iterator().next());

        try {
            createRes.getBody();
            fail();
        } catch (NoContentException e) {
            assertNotNull(e);
        }

        final ODataDeleteResponse deleteRes = ODataCUDRequestFactory.getDeleteRequest(
                new ODataURIBuilder(getServiceRoot()).appendEntitySetSegment("Customer").appendKeySegment(id).build()).
                execute();
        assertEquals(204, deleteRes.getStatusCode());
    }
}
