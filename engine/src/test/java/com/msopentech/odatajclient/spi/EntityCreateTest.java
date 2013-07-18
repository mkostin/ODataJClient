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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.msopentech.odatajclient.engine.communication.ODataClientErrorException;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataDeleteRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataInlineEntity;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

/**
 * This is the unit test class to check create entity operations.
 */
public class EntityCreateTest extends AbstractTest {

    private ODataEntity createODataEntity(final ODataPubFormat format, final ODataEntity original) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("Customer");

        debugODataEntity(original, "About to create");

        final ODataEntityCreateRequest createReq =
                ODataCUDRequestFactory.getEntityCreateRequest(uriBuilder.build(), original);
        createReq.setFormat(format);

        final ODataEntityCreateResponse createRes = createReq.execute();
        assertEquals(201, createRes.getStatusCode());
        assertEquals("Created", createRes.getStatusMessage());

        final ODataEntity created = createRes.getBody();
        assertNotNull(created);

        debugODataEntity(created, "Just created");

        return created;
    }

    private ODataEntity compareEntities(
            final ODataPubFormat format,
            final ODataEntity original,
            final int actualObjectId,
            final Collection<String> expands) {

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment("Customer(" + actualObjectId + ")");

        // search expanded
        if (expands != null) {
            for (String expand : expands) {
                uriBuilder.expand(expand);
            }
        }

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataRetrieveResponse<ODataEntity> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final ODataEntity actual = res.getBody();
        assertNotNull(actual);

        // check defined links
        checkLinks(original.getAssociationLinks(), actual.getAssociationLinks());
        checkLinks(original.getEditMediaLinks(), actual.getEditMediaLinks());
        checkLinks(original.getNavigationLinks(), actual.getNavigationLinks());

        // check defined properties equality
        checkProperties(original.getProperties(), actual.getProperties());

        return actual;
    }

    private void clean(final ODataPubFormat format, final ODataEntity created, final boolean includeInline) {
        final List<ODataEntity> toBeDeleted = new ArrayList<ODataEntity>();
        toBeDeleted.add(created);

        if (includeInline) {
            for (ODataLink link : created.getNavigationLinks()) {
                if (link instanceof ODataInlineEntity) {
                    final ODataEntity inline = ((ODataInlineEntity) link).getEntity();
                    toBeDeleted.add(inline);
                }
            }
        }

        assertFalse(toBeDeleted.isEmpty());

        for (ODataEntity entity : toBeDeleted) {
            final URI selflLink = entity.getLink();
            assertNotNull(selflLink);

            final URI editLink = entity.getEditLink();
            assertNotNull(editLink);

            final ODataDeleteRequest deleteReq = ODataCUDRequestFactory.getDeleteRequest(editLink);
            final ODataDeleteResponse deleteRes = deleteReq.execute();

            assertEquals(204, deleteRes.getStatusCode());
            assertEquals("No Content", deleteRes.getStatusMessage());

            deleteRes.close();
            
            final ODataEntityRequest retrieveReq = ODataRetrieveRequestFactory.getEntityRequest(selflLink);
            // bug that needs to be fixed on the SampleService - cannot get entity not found with header
            // Accept: application/json;odata=minimalmetadata
            retrieveReq.setFormat(format == ODataPubFormat.JSON_FULL_METADATA? ODataPubFormat.JSON: format);

            Exception exception = null;
            try {
                retrieveReq.execute();
                fail();
            } catch (ODataClientErrorException e) {
                exception = e;
                assertEquals(404, e.getStatusLine().getStatusCode());
            }
            assertNotNull(exception);
        }
    }

    private ODataEntity createWithNavigationLink(final ODataPubFormat format, final int id) {
        final String sampleName = "Sample customer";

        final ODataEntity original = getSampleCustomerProfile(id, sampleName, false);
        original.addLink(ODataFactory.newEntityNavigationLink(
                "Info", URI.create(testODataServiceRootURL + "/CustomerInfo(12)")));

        final ODataEntity created = createODataEntity(format, original);
        // now, compare the created one with the actual one and go deeply into the associated customer info.....
        final ODataEntity actual = compareEntities(format, created, id, null);

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment("Customer(" + id + ")").appendEntityTypeSegment("Info");

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
    public void createODataEntityAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final int id = 1;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", false);

        createODataEntity(format, original);
        final ODataEntity actual = compareEntities(format, original, id, null);

        clean(format, actual, false);
    }

    @Test
    public void createODataEntityAsJSON() {
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final int id = 2;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", false);

        createODataEntity(format, original);
        final ODataEntity actual = compareEntities(format, original, id, null);

        clean(format, actual, false);
    }

    @Test
    public void createWithInlineAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final int id = 3;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", true);

        createODataEntity(format, original);
        final ODataEntity actual = compareEntities(format, original, id, Collections.<String>singleton("Info"));

        clean(format, actual, true);
    }

    @Test
    public void createWithInlineAsJSON() {
        // this needs to be full, otherwise there is no mean to recognize links
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final int id = 4;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", true);

        createODataEntity(format, original);
        final ODataEntity actual = compareEntities(format, original, id, Collections.<String>singleton("Info"));

        clean(format, actual, true);
    }

    @Test
    public void createWithNavigationAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final ODataEntity actual = createWithNavigationLink(format, 5);
        clean(format, actual, false);
    }

    @Test
    public void createWithNavigationAsJSON() {
        // this needs to be full, otherwise there is no mean to recognize links
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final ODataEntity actual = createWithNavigationLink(format, 6);
        clean(format, actual, false);
    }
}
