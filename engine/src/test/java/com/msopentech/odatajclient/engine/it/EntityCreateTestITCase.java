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

import static com.msopentech.odatajclient.engine.it.AbstractTest.testDefaultServiceRootURL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.msopentech.odatajclient.engine.client.http.NoContentException;
import com.msopentech.odatajclient.engine.communication.header.ODataHeaderValues;
import com.msopentech.odatajclient.engine.communication.header.ODataHeaders;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataDeleteRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntitySetRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataInlineEntitySet;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.utils.URIUtils;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
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

        createEntity(getServiceRoot(), format, original, "Customer");
        final ODataEntity actual = compareEntities(getServiceRoot(), format, original, id, null);

        cleanAfterCreate(format, actual, false, getServiceRoot());
    }

    @Test
    public void createAsJSON() {
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final int id = 2;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", false);

        createEntity(getServiceRoot(), format, original, "Customer");
        final ODataEntity actual = compareEntities(getServiceRoot(), format, original, id, null);

        cleanAfterCreate(format, actual, false, getServiceRoot());
    }

    @Test
    public void createWithInlineAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final int id = 3;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", true);

        createEntity(getServiceRoot(), format, original, "Customer");
        final ODataEntity actual =
                compareEntities(getServiceRoot(), format, original, id, Collections.<String>singleton("Info"));

        cleanAfterCreate(format, actual, true, getServiceRoot());
    }

    @Test
    public void createWithInlineAsJSON() {
        // this needs to be full, otherwise there is no mean to recognize links
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final int id = 4;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", true);

        createEntity(getServiceRoot(), format, original, "Customer");
        final ODataEntity actual =
                compareEntities(getServiceRoot(), format, original, id, Collections.<String>singleton("Info"));

        cleanAfterCreate(format, actual, true, getServiceRoot());
    }

    @Test
    public void createInlineWithoutLinkAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final int id = 5;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", false);

        original.addLink(ODataFactory.newInlineEntity(
                "Info", null, getSampleCustomerInfo(id, "Sample Customer_Info")));

        createEntity(getServiceRoot(), format, original, "Customer");
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

        cleanAfterCreate(format, actual, true, getServiceRoot());
    }

    @Test
    public void createInlineWithoutLinkAsJSON() {
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final int id = 6;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", false);

        original.addLink(ODataFactory.newInlineEntity(
                "Info", null, getSampleCustomerInfo(id, "Sample Customer_Info")));

        createEntity(getServiceRoot(), format, original, "Customer");
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

        cleanAfterCreate(format, actual, true, getServiceRoot());
    }

    @Test
    public void createWithNavigationAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final ODataEntity actual = createWithNavigationLink(format, 5);
        cleanAfterCreate(format, actual, false, getServiceRoot());
    }

    @Test
    public void createWithNavigationAsJSON() {
        // this needs to be full, otherwise there is no mean to recognize links
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final ODataEntity actual = createWithNavigationLink(format, 6);
        cleanAfterCreate(format, actual, false, getServiceRoot());
    }

    @Test
    public void createWithFeedNavigationAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final ODataEntity actual = createWithFeedNavigationLink(format, 7);
        cleanAfterCreate(format, actual, false, getServiceRoot());
    }

    @Test
    public void createWithFeedNavigationAsJSON() {
        // this needs to be full, otherwise there is no mean to recognize links
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final ODataEntity actual = createWithFeedNavigationLink(format, 8);
        cleanAfterCreate(format, actual, false, getServiceRoot());
    }

    @Test
    public void createWithBackNavigationAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final ODataEntity actual = createWithBackNavigationLink(format, 9);
        cleanAfterCreate(format, actual, true, getServiceRoot());
    }

    @Test
    public void createWithBackNavigationAsJSON() {
        // this needs to be full, otherwise there is no mean to recognize links
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final ODataEntity actual = createWithBackNavigationLink(format, 10);
        cleanAfterCreate(format, actual, true, getServiceRoot());
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

    private ODataEntity createWithFeedNavigationLink(final ODataPubFormat format, final int id) {
        final String sampleName = "Sample customer";
        final ODataEntity original = getSampleCustomerProfile(id, sampleName, false);

        final Set<Integer> keys = new HashSet<Integer>();
        keys.add(-100);
        keys.add(-101);

        for (Integer key : keys) {
            final ODataEntity order =
                    ODataFactory.newEntity("Microsoft.Test.OData.Services.AstoriaDefaultService.Order");

            order.addProperty(ODataFactory.newPrimitiveProperty("OrderId",
                    new ODataPrimitiveValue.Builder().setValue(key).setType(EdmSimpleType.INT_32).build()));

            final ODataEntityCreateRequest createReq = ODataCUDRequestFactory.getEntityCreateRequest(
                    new ODataURIBuilder(getServiceRoot()).appendEntitySetSegment("Order").build(), order);
            createReq.setFormat(format);

            original.addLink(ODataFactory.newFeedNavigationLink(
                    "Orders",
                    createReq.execute().getBody().getEditLink()));
        }

        final ODataEntity created = createEntity(getServiceRoot(), format, original, "Customer");
        // now, compare the created one with the actual one and go deeply into the associated customer info.....
        final ODataEntity actual = compareEntities(getServiceRoot(), format, created, id, null);

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(getServiceRoot());
        uriBuilder.appendEntityTypeSegment("Customer").appendKeySegment(id).appendEntityTypeSegment("Orders");

        final ODataEntitySetRequest req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataRetrieveResponse<ODataEntitySet> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final ODataEntitySet entitySet = res.getBody();
        assertNotNull(entitySet);
        assertEquals(2, entitySet.getCount());

        for (ODataEntity entity : entitySet.getEntities()) {
            final Integer key = entity.getProperty("OrderId").getPrimitiveValue().<Integer>toCastValue();
            assertTrue(keys.contains(key));
            keys.remove(key);

            final ODataDeleteRequest deleteReq = ODataCUDRequestFactory.getDeleteRequest(
                    URIUtils.getURI(getServiceRoot(), entity.getEditLink().toASCIIString()));

            deleteReq.setFormat(format);
            assertEquals(204, deleteReq.execute().getStatusCode());
        }

        return actual;
    }

    private ODataEntity createWithNavigationLink(final ODataPubFormat format, final int id) {
        final String sampleName = "Sample customer";

        final ODataEntity original = getSampleCustomerProfile(id, sampleName, false);
        original.addLink(ODataFactory.newEntityNavigationLink(
                "Info", URI.create(getServiceRoot() + "/CustomerInfo(12)")));

        final ODataEntity created = createEntity(getServiceRoot(), format, original, "Customer");
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

    private ODataEntity createWithBackNavigationLink(final ODataPubFormat format, final int id) {
        final String sampleName = "Sample customer";

        ODataEntity customer = getSampleCustomerProfile(id, sampleName, false);
        customer = createEntity(getServiceRoot(), format, customer, "Customer");

        ODataEntity order = ODataFactory.newEntity("Microsoft.Test.OData.Services.AstoriaDefaultService.Order");
        order.addProperty(ODataFactory.newPrimitiveProperty("CustomerId",
                new ODataPrimitiveValue.Builder().setValue(id).setType(EdmSimpleType.INT_32).build()));
        order.addProperty(ODataFactory.newPrimitiveProperty("OrderId",
                new ODataPrimitiveValue.Builder().setValue(id).setType(EdmSimpleType.INT_32).build()));

        order.addLink(ODataFactory.newEntityNavigationLink(
                "Customer", URIUtils.getURI(getServiceRoot(), customer.getEditLink().toASCIIString())));

        order = createEntity(getServiceRoot(), format, order, "Order");

        ODataEntity changes = ODataFactory.newEntity("Microsoft.Test.OData.Services.AstoriaDefaultService.Customer");
        changes.setEditLink(customer.getEditLink());
        changes.addLink(ODataFactory.newFeedNavigationLink(
                "Orders", URIUtils.getURI(getServiceRoot(), order.getEditLink().toASCIIString())));
        update(UpdateType.PATCH, changes, format, null);

        final ODataEntityRequest customerreq = ODataRetrieveRequestFactory.getEntityRequest(
                URIUtils.getURI(getServiceRoot(), order.getEditLink().toASCIIString() + "/Customer"));
        customerreq.setFormat(format);

        customer = customerreq.execute().getBody();

        assertEquals(
                Integer.valueOf(id), customer.getProperty("CustomerId").getPrimitiveValue().<Integer>toCastValue());

        final ODataEntitySetRequest orderreq = ODataRetrieveRequestFactory.getEntitySetRequest(
                URIUtils.getURI(getServiceRoot(), customer.getEditLink().toASCIIString() + "/Orders"));
        orderreq.setFormat(format);

        final ODataRetrieveResponse<ODataEntitySet> orderres = orderreq.execute();
        assertEquals(200, orderres.getStatusCode());

        assertEquals(Integer.valueOf(id),
                orderres.getBody().getEntities().get(0).getProperty("OrderId").getPrimitiveValue().
                <Integer>toCastValue());

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(
                URIUtils.getURI(getServiceRoot(), customer.getEditLink().toASCIIString() + "?$expand=Orders"));
        req.setFormat(format);

        customer = req.execute().getBody();

        boolean found = false;
        for (ODataLink link : customer.getNavigationLinks()) {
            if (link instanceof ODataInlineEntitySet && "Orders".equals(link.getName())) {
                found = true;
            }
        }
        assertTrue(found);

        return customer;
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
}
