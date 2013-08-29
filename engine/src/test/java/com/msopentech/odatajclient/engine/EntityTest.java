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
package com.msopentech.odatajclient.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.data.Deserializer;
import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.ODataBinder;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataOperation;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ResourceFactory;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.io.InputStream;
import java.util.Collections;
import org.junit.Test;

public class EntityTest extends AbstractTest {

    private void readAndWrite(final ODataPubFormat format) {
        final InputStream input = getClass().getResourceAsStream("Customer_-10." + getSuffix(format));
        final ODataEntity entity = ODataBinder.getODataEntity(
                Deserializer.toEntry(input, ResourceFactory.entryClassForFormat(format)));
        assertNotNull(entity);

        assertEquals("Microsoft.Test.OData.Services.AstoriaDefaultService.Customer", entity.getName());
        assertTrue(entity.getEditLink().toASCIIString().endsWith("/Customer(-10)"));
        assertEquals(5, entity.getNavigationLinks().size());
        assertEquals(2, entity.getEditMediaLinks().size());

        boolean check = false;

        for (ODataLink link : entity.getNavigationLinks()) {
            if ("Wife".equals(link.getName()) && (link.getLink().toASCIIString().endsWith("/Customer(-10)/Wife"))) {
                check = true;
            }
        }

        assertTrue(check);

        final ODataEntity written = ODataBinder.getODataEntity(
                ODataBinder.getEntry(entity, ResourceFactory.entryClassForFormat(format)));
        assertEquals(entity, written);
    }

    @Test
    public void fromAtom() {
        readAndWrite(ODataPubFormat.ATOM);
    }

    @Test
    public void fromJSON() {
        readAndWrite(ODataPubFormat.JSON_FULL_METADATA);
    }

    private void readGeospatial(final ODataPubFormat format) {
        final InputStream input = getClass().getResourceAsStream("AllGeoTypesSet_-8." + getSuffix(format));
        final ODataEntity entity = ODataBinder.getODataEntity(
                Deserializer.toEntry(input, ResourceFactory.entryClassForFormat(format)));
        assertNotNull(entity);

        boolean found = false;
        for (ODataProperty property : entity.getProperties()) {
            if ("GeogMultiLine".equals(property.getName())) {
                found = true;
                assertTrue(property.hasPrimitiveValue());
                assertEquals(EdmSimpleType.GEOGRAPHY_MULTI_LINE_STRING.toString(),
                        property.getPrimitiveValue().getTypeName());
            }
        }
        assertTrue(found);

        final ODataEntity written = ODataBinder.getODataEntity(
                ODataBinder.getEntry(entity, ResourceFactory.entryClassForFormat(format)));
        assertEquals(entity, written);
    }

    @Test
    public void withGeospatialFromAtom() {
        readGeospatial(ODataPubFormat.ATOM);
    }

    @Test
    public void withGeospatialFromJSON() {
        // this needs to be full, otherwise there is no mean to recognize geospatial types
        readGeospatial(ODataPubFormat.JSON_FULL_METADATA);
    }

    private void withActions(final ODataPubFormat format) {
        final InputStream input = getClass().getResourceAsStream("ComputerDetail_-10." + getSuffix(format));
        final ODataEntity entity = ODataBinder.getODataEntity(
                Deserializer.toEntry(input, ResourceFactory.entryClassForFormat(format)));
        assertNotNull(entity);

        assertEquals(1, entity.getOperations().size());
        assertEquals("ResetComputerDetailsSpecifications", entity.getOperations().get(0).getTitle());

        final ODataEntity written = ODataBinder.getODataEntity(
                ODataBinder.getEntry(entity, ResourceFactory.entryClassForFormat(format)));
        entity.setOperations(Collections.<ODataOperation>emptyList());
        assertEquals(entity, written);
    }

    @Test
    public void withActionsFromAtom() {
        withActions(ODataPubFormat.ATOM);
    }

    @Test
    public void withActionsFromJSON() {
        // this needs to be full, otherwise actions will not be provided
        withActions(ODataPubFormat.JSON_FULL_METADATA);
    }

    private void mediaEntity(final ODataPubFormat format) {
        final InputStream input = getClass().getResourceAsStream("Car_16." + getSuffix(format));
        final ODataEntity entity = ODataBinder.getODataEntity(
                Deserializer.toEntry(input, ResourceFactory.entryClassForFormat(format)));
        assertNotNull(entity);
        assertTrue(entity.isMediaEntity());
        assertNotNull(entity.getMediaContentSource());
        assertNotNull(entity.getMediaContentType());

        
        final ODataEntity written = ODataBinder.getODataEntity(
                ODataBinder.getEntry(entity, ResourceFactory.entryClassForFormat(format)));
        assertEquals(entity, written);
    }

    @Test
    public void mediaEntityFromAtom() {
        mediaEntity(ODataPubFormat.ATOM);
    }

    @Test
    public void mediaEntityFromJSON() {
        mediaEntity(ODataPubFormat.JSON_FULL_METADATA);
    }
}
