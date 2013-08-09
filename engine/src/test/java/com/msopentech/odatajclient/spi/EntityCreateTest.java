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
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import java.net.URI;
import java.util.Collections;
import org.junit.Test;

/**
 * This is the unit test class to check create entity operations.
 */
public class EntityCreateTest extends AbstractTest {

    @Test
    public void createAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final int id = 1;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", false);

        createEntity(testDefaultServiceRootURL, format, original);
        final ODataEntity actual = compareEntities(testDefaultServiceRootURL, format, original, id, null);

        cleanAfterCreate(format, actual, false);
    }

    @Test
    public void createAsJSON() {
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final int id = 2;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", false);

        createEntity(testDefaultServiceRootURL, format, original);
        final ODataEntity actual = compareEntities(testDefaultServiceRootURL, format, original, id, null);

        cleanAfterCreate(format, actual, false);
    }

    @Test
    public void createWithInlineAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final int id = 3;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", true);

        createEntity(testDefaultServiceRootURL, format, original);
        final ODataEntity actual =
                compareEntities(testDefaultServiceRootURL, format, original, id, Collections.<String>singleton("Info"));

        cleanAfterCreate(format, actual, true);
    }

    @Test
    public void createWithInlineAsJSON() {
        // this needs to be full, otherwise there is no mean to recognize links
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final int id = 4;
        final ODataEntity original = getSampleCustomerProfile(id, "Sample customer", true);

        createEntity(testDefaultServiceRootURL, format, original);
        final ODataEntity actual =
                compareEntities(testDefaultServiceRootURL, format, original, id, Collections.<String>singleton("Info"));

        cleanAfterCreate(format, actual, true);
    }

    private ODataEntity createWithNavigationLink(final ODataPubFormat format, final int id) {
        final String sampleName = "Sample customer";

        final ODataEntity original = getSampleCustomerProfile(id, sampleName, false);
        original.addLink(ODataFactory.newEntityNavigationLink(
                "Info", URI.create(testDefaultServiceRootURL + "/CustomerInfo(12)")));

        final ODataEntity created = createEntity(testDefaultServiceRootURL, format, original);
        // now, compare the created one with the actual one and go deeply into the associated customer info.....
        final ODataEntity actual = compareEntities(testDefaultServiceRootURL, format, created, id, null);

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL);
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
}
