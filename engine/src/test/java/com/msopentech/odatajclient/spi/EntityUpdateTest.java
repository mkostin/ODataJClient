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

import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityUpdateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is the unit test class to check basic entity operations.
 */
public class EntityUpdateTest extends AbstractTest {

    @Test
    @Ignore
    public void mergeODataEntityAsAtom() {
        final ODataFormat format = ODataFormat.ATOM;
        final ODataEntity changes = ODataFactory.newEntity("Microsoft.Test.OData.Services.AstoriaDefaultService.Car");
        changes.setEditLink(new ODataURIBuilder(testODataServiceRootURL).appendEntityTypeSegment("Car(14)").build());
        updateCarEntity(format, changes, UpdateType.MERGE);
    }

    @Test
    @Ignore
    public void mergeODataEntityAsJson() {
        final ODataFormat format = ODataFormat.JSON_FULL_METADATA;
        final ODataEntity changes = ODataFactory.newEntity("Microsoft.Test.OData.Services.AstoriaDefaultService.Car");
        changes.setEditLink(new ODataURIBuilder(testODataServiceRootURL).appendEntityTypeSegment("Car(14)").build());
        updateCarEntity(format, changes, UpdateType.MERGE);
    }

    @Test
    @Ignore
    public void patchODataEntityAsAtom() {
        final ODataFormat format = ODataFormat.ATOM;
        final ODataEntity changes = ODataFactory.newEntity("Microsoft.Test.OData.Services.AstoriaDefaultService.Car");
        changes.setEditLink(new ODataURIBuilder(testODataServiceRootURL).appendEntityTypeSegment("Car(14)").build());
        updateCarEntity(format, changes, UpdateType.PATCH);
    }

    @Test
    @Ignore
    public void patchODataEntityAsJson() {
        final ODataFormat format = ODataFormat.JSON_FULL_METADATA;
        final ODataEntity changes = ODataFactory.newEntity("Microsoft.Test.OData.Services.AstoriaDefaultService.Car");
        changes.setEditLink(new ODataURIBuilder(testODataServiceRootURL).appendEntityTypeSegment("Car(14)").build());
        updateCarEntity(format, changes, UpdateType.PATCH);
    }

    @Test
    public void replaceODataEntityAsAtom() {
        final ODataFormat format = ODataFormat.ATOM;
        final ODataEntity changes = readODataEntity(format, "Car(14)");
        updateCarEntity(format, changes, UpdateType.REPLACE);
    }

    @Test
    public void replaceODataEntityAsJson() {
        final ODataFormat format = ODataFormat.JSON_FULL_METADATA;
        final ODataEntity changes = readODataEntity(format, "Car(14)");
        updateCarEntity(format, changes, UpdateType.REPLACE);
    }

    public void updateCarEntity(final ODataFormat format, final ODataEntity changes, final UpdateType type) {
        final String newMsg = "New description(" + System.currentTimeMillis() + ")";

        ODataProperty description = null;

        for (ODataProperty prop : changes.getProperties()) {
            if (prop.getName().equals("Description")) {
                description = prop;
            }
        }

        final String oldMsg;
        if (description == null) {
            oldMsg = null;
        } else {
            oldMsg = description.getValue().toString();
            changes.removeProperty(description);
        }

        assertNotEquals(newMsg, oldMsg);

        changes.addProperty(
                new ODataProperty("Description", new ODataPrimitiveValue.Builder().setText(newMsg).build()));

        ODataEntityUpdateRequest req =
                ODataRequestFactory.getEntityUpdateRequest(changes.getEditLink(), type, changes);
        req.setFormat(format);

        ODataEntityUpdateResponse res = req.execute();
        
        debugInputStream(res.getRawResponse(), "KKKKKKKKZ");
        
        assertEquals(204, res.getStatusCode());

        final ODataEntity actual = readODataEntity(format, "Car(14)");
        assertEquals(2, actual.getProperties().size());

        description = null;

        for (ODataProperty prop : actual.getProperties()) {
            if (prop.getName().equals("Description")) {
                description = prop;
            }
        }

        assertNotNull(description);
        assertEquals(newMsg, description.getValue().toString());

        actual.removeProperty(description);
        actual.addProperty(
                new ODataProperty("Description", new ODataPrimitiveValue.Builder().setText(oldMsg).build()));

        req = ODataRequestFactory.getEntityUpdateRequest(changes.getEditLink(), type, changes);
        req.setFormat(format);

        res = req.execute();
        assertEquals(204, res.getStatusCode());
    }

    private ODataEntity readODataEntity(final ODataFormat format, final String entitySegment) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment(entitySegment);

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataQueryResponse<ODataEntity> res = req.execute();
        final ODataEntity entity = res.getBody();

        assertNotNull(entity);

        if (ODataFormat.JSON_FULL_METADATA == format || ODataFormat.ATOM == format) {
            assertEquals(req.getURI(), entity.getEditLink());
        }

        return entity;
    }
}
