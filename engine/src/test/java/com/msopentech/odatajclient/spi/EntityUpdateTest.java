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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityUpdateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.utils.Configuration;
import java.net.URI;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * This is the unit test class to check entity update operations.
 */
public class EntityUpdateTest extends AbstractTest {

    @Test
    public void mergeODataEntityAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final URI uri = new ODataURIBuilder(testODataServiceRootURL).appendEntityTypeSegment(TEST_PRODUCT).build();
        final String etag = getETag(uri);
        final ODataEntity merge = ODataFactory.newEntity(TEST_PRODUCT_TYPE);
        merge.setEditLink(uri);
        updateEntityDescription(format, merge, UpdateType.MERGE, etag);
    }

    @Test
    public void mergeODataEntityAsJson() {
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final URI uri = new ODataURIBuilder(testODataServiceRootURL).appendEntityTypeSegment(TEST_PRODUCT).build();
        final String etag = getETag(uri);
        final ODataEntity merge = ODataFactory.newEntity(TEST_PRODUCT_TYPE);
        merge.setEditLink(uri);
        updateEntityDescription(format, merge, UpdateType.MERGE, etag);
    }

    @Test
    public void patchODataEntityAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final URI uri = new ODataURIBuilder(testODataServiceRootURL).appendEntityTypeSegment(TEST_PRODUCT).build();
        final String etag = getETag(uri);
        final ODataEntity patch = ODataFactory.newEntity(TEST_PRODUCT_TYPE);
        patch.setEditLink(uri);
        updateEntityDescription(format, patch, UpdateType.PATCH, etag);
    }

    @Test
    public void patchODataEntityAsJson() {
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final URI uri = new ODataURIBuilder(testODataServiceRootURL).appendEntityTypeSegment(TEST_PRODUCT).build();
        final String etag = getETag(uri);
        final ODataEntity patch = ODataFactory.newEntity(TEST_PRODUCT_TYPE);
        patch.setEditLink(uri);
        updateEntityDescription(format, patch, UpdateType.PATCH, etag);
    }

    @Test
    public void replaceODataEntityAsAtom() {
        final ODataPubFormat format = ODataPubFormat.ATOM;
        final ODataEntity changes = readODataEntity(format, "Car(14)");
        updateEntityDescription(format, changes, UpdateType.REPLACE);
    }

    @Test
    public void replaceODataEntityAsJson() {
        final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
        final ODataEntity changes = readODataEntity(format, "Car(14)");
        updateEntityDescription(format, changes, UpdateType.REPLACE);
    }

    protected void updateEntityDescription(
            final ODataPubFormat format, final ODataEntity changes, final UpdateType type) {

        updateEntityDescription(format, changes, type, null);
    }

    protected void updateEntityDescription(
            final ODataPubFormat format, final ODataEntity changes, final UpdateType type, final String etag) {

        final URI editLink = changes.getEditLink();
        final String entityTypeSeg = editLink.toASCIIString().substring(
                editLink.toASCIIString().lastIndexOf("/") + 1, editLink.toASCIIString().length());

        final String newm = "New description(" + System.currentTimeMillis() + ")";

        ODataProperty description = changes.getProperty("Description");

        final String oldm;
        if (description == null) {
            oldm = null;
        } else {
            oldm = description.getValue().toString();
            changes.removeProperty(description);
        }

        assertNotEquals(newm, oldm);

        changes.addProperty(ODataFactory.newPrimitiveProperty("Description",
                new ODataPrimitiveValue.Builder().setText(newm).build()));

        final ODataEntityUpdateRequest req = ODataCUDRequestFactory.getEntityUpdateRequest(type, changes);
        if (Configuration.isUseXHTTPMethod()) {
            assertEquals(Method.POST, req.getMethod());
        } else {
            assertEquals(type.getMethod(), req.getMethod());
        }
        req.setFormat(format);

        if (StringUtils.isNotBlank(etag)) {
            req.setIfMatch(etag); // Product include ETag header into the response .....
        }

        final ODataEntityUpdateResponse res = req.execute();
        assertEquals(204, res.getStatusCode());

        final ODataEntity actual = readODataEntity(format, entityTypeSeg);

        description = null;

        for (ODataProperty prop : actual.getProperties()) {
            if (prop.getName().equals("Description")) {
                description = prop;
            }
        }

        assertNotNull(description);
        assertEquals(newm, description.getValue().toString());
    }

    private ODataEntity readODataEntity(final ODataPubFormat format, final String entitySegment) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment(entitySegment);

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataRetrieveResponse<ODataEntity> res = req.execute();
        final ODataEntity entity = res.getBody();

        assertNotNull(entity);

        if (ODataPubFormat.JSON_FULL_METADATA == format || ODataPubFormat.ATOM == format) {
            assertEquals(req.getURI(), entity.getEditLink());
        }

        return entity;
    }
}
