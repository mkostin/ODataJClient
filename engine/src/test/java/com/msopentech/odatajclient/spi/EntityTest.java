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
import static org.junit.Assert.assertFalse;

import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataDeleteRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataInlineEntity;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import com.msopentech.odatajclient.engine.data.ODataBinder;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ResourceFactory;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import com.msopentech.odatajclient.engine.utils.SerializationUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This is the unit test class to check basic entity operations.
 */
public class EntityTest extends AbstractTest {

    private void readEntry(final ODataFormat format) throws IOException {
        // ---------------------------------------------
        // Read Car(16)
        // ---------------------------------------------
        ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment("Car(16)");

        ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        InputStream is = req.rowExecute();

        EntryResource entry = SerializationUtils.deserializeEntry(is, ResourceFactory.entryClassForFormat(format));
        assertNotNull(entry);

        is.close();

        debugEntry(entry, "Just read");
        // ---------------------------------------------


        // ---------------------------------------------
        // Read Customer(-10)
        // ---------------------------------------------
        uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment("Customer(-10)");

        req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        is = req.rowExecute();

        entry = SerializationUtils.deserializeEntry(is, ResourceFactory.entryClassForFormat(format));
        assertNotNull(entry);

        is.close();

        debugEntry(entry, "Just read");
        // ---------------------------------------------

        if (ODataFormat.JSON_FULL_METADATA == format || ODataFormat.ATOM == format) {
            assertEquals(uriBuilder.build().toASCIIString(), entry.getId());
            assertEquals("Microsoft.Test.OData.Services.AstoriaDefaultService.Customer", entry.getType());
            assertNotNull(entry.getBaseURI());
        }

        final Element content = entry.getContent();
        assertEquals(ODataConstants.ELEM_PROPERTIES, content.getNodeName());

        boolean entered = false;
        boolean checked = false;
        for (int i = 0; i < content.getChildNodes().getLength(); i++) {
            entered = true;

            Node property = content.getChildNodes().item(i);
            if ("PrimaryContactInfo".equals(property.getLocalName())) {
                checked = true;

                if (ODataFormat.JSON_FULL_METADATA == format || ODataFormat.ATOM == format) {
                    assertEquals("Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails",
                            ((Element) property).getAttribute(ODataConstants.ATTR_TYPE));
                }
            }
        }
        assertTrue(entered);
        assertTrue(checked);
    }

    @Test
    public void readAtomEntry() throws Exception {
        readEntry(ODataFormat.ATOM);
    }

    @Test
    public void readJSONEntry() throws Exception {
        readEntry(ODataFormat.JSON);
    }

    private void readODataEntity(final ODataFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment("Customer(-10)");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataQueryResponse<ODataEntity> res = req.execute();
        final ODataEntity entity = res.getBody();

        assertNotNull(entity);

        if (ODataFormat.JSON_FULL_METADATA == format || ODataFormat.ATOM == format) {
            assertEquals("Microsoft.Test.OData.Services.AstoriaDefaultService.Customer", entity.getName());
            assertEquals(testODataServiceRootURL + "/Customer(-10)", entity.getEditLink().toASCIIString());
            assertEquals(5, entity.getNavigationLinks().size());
            assertEquals(2, entity.getEditMediaLinks().size());

            boolean check = false;

            for (ODataLink link : entity.getNavigationLinks()) {
                if ("Wife".equals(link.getName())
                        && (testODataServiceRootURL + "/Customer(-10)/Wife").equals(link.getLink().toASCIIString())) {
                    check = true;
                }
            }

            assertTrue(check);
        }
    }

    @Test
    public void readODataEntityFromAtom() {
        readODataEntity(ODataFormat.ATOM);
    }

    @Test
    public void readODataEntityFromJSON() {
        readODataEntity(ODataFormat.JSON);
    }

    private void readODataEntityWithInline(final ODataFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment("Customer(-10)").expand("Info");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataQueryResponse<ODataEntity> res = req.execute();
        final ODataEntity entity = res.getBody();

        assertNotNull(entity);
        assertEquals("Microsoft.Test.OData.Services.AstoriaDefaultService.Customer", entity.getName());
        assertEquals(testODataServiceRootURL + "/Customer(-10)", entity.getEditLink().toASCIIString());

        assertEquals(5, entity.getNavigationLinks().size());
        assertTrue(entity.getAssociationLinks().isEmpty());

        boolean found = false;

        for (ODataLink link : entity.getNavigationLinks()) {
            if (link instanceof ODataInlineEntity) {
                final ODataEntity inline = ((ODataInlineEntity) link).getEntity();
                assertNotNull(inline);

                debugEntry(ODataBinder.getEntry(inline, ResourceFactory.entryClassForFormat(format)), "Just read");

                final List<ODataProperty> properties = inline.getProperties();
                assertEquals(2, properties.size());

                assertTrue(properties.get(0).getName().equals("CustomerInfoId")
                        || properties.get(1).getName().equals("CustomerInfoId"));
                assertTrue(properties.get(0).getValue().toString().equals("11")
                        || properties.get(1).getValue().toString().equals("11"));

                found = true;
            }
        }

        assertTrue(found);
    }

    @Test
    public void readODataEntityWithInlineFromAtom() {
        readODataEntityWithInline(ODataFormat.ATOM);
    }

    @Test
    public void readODataEntityWithInlineFromJSON() {
        // this needs to be full, otherwise there is no mean to recognize links
        readODataEntityWithInline(ODataFormat.JSON_FULL_METADATA);
    }

    private ODataEntity createODataEntity(final ODataFormat format, final int id, final boolean withInlineInfo) {
        final String sampleName = "Sample customer";
        final ODataEntity original = getSampleCustomerProfile(id, sampleName, withInlineInfo);
        return createODataEntity(format, original);
    }

    private ODataEntity createODataEntity(
            final ODataFormat format, ODataEntity original) {
        ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("Customer");

        debugODataEntity(original, "About to create");

        final ODataEntityCreateRequest createReq =
                ODataRequestFactory.getEntityCreateRequest(uriBuilder.build(), original);
        createReq.setFormat(format);

        final ODataEntityCreateResponse createRes = createReq.execute();

        assertEquals(201, createRes.getStatusCode());
        assertEquals("Created", createRes.getStatusMessage());

        ODataEntity created = createRes.getBody();
        assertNotNull(created);

        debugODataEntity(created, "Just created");

        return created;
    }

    private ODataEntity compareEntities(
            final ODataFormat format,
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

        final ODataQueryResponse<ODataEntity> res = req.execute();
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

    private void clean(final ODataFormat format, final ODataEntity created, final boolean includeInline) {
        final List<ODataEntity> toBeDeleted = new ArrayList<ODataEntity>();
        toBeDeleted.add(created);

        if (includeInline) {
            for (ODataLink link : created.getNavigationLinks()) {
                if (link instanceof ODataInlineEntity) {
                    ODataEntity inline = ((ODataInlineEntity) link).getEntity();
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

            final ODataDeleteRequest deleteReq = ODataRequestFactory.getDeleteRequest(editLink);
            ODataDeleteResponse deleteRes = deleteReq.execute();

            assertEquals(204, deleteRes.getStatusCode());
            assertEquals("No Content", deleteRes.getStatusMessage());

            deleteRes.close();

            final ODataEntityRequest retrieveReq = ODataRetrieveRequestFactory.getEntityRequest(selflLink);
            retrieveReq.setFormat(format);

            final ODataQueryResponse<ODataEntity> retrieveRes = retrieveReq.execute();
            assertEquals(404, retrieveRes.getStatusCode());
            retrieveRes.close();
        }
    }

    private ODataEntity createWithNavigationLink(final ODataFormat format, final int id) {
        final String sampleName = "Sample customer";

        ODataEntity original = getSampleCustomerProfile(id, sampleName, false);
        original.addLink(ODataFactory.newEntityNavigationLink(
                "Info", URI.create(testODataServiceRootURL + "/CustomerInfo(12)")));

        ODataEntity created = createODataEntity(format, original);
        // now, compare the created one with the actula one and go deeply into the associated customer info.....
        ODataEntity actual = compareEntities(format, created, id, null);

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment("Customer(" + id + ")").appendEntityTypeSegment("Info");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataQueryResponse<ODataEntity> res = req.execute();
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
        final ODataFormat format = ODataFormat.ATOM;
        final int id = 1;
        final String sampleName = "Sample customer";
        final ODataEntity original = getSampleCustomerProfile(id, sampleName, false);

        createODataEntity(format, original);
        ODataEntity actual = compareEntities(format, original, id, null);

        clean(format, actual, false);
    }

    @Test
    public void createODataEntityAsJSON() {
        final ODataFormat format = ODataFormat.JSON_FULL_METADATA;
        final int id = 2;
        final String sampleName = "Sample customer";
        final ODataEntity original = getSampleCustomerProfile(id, sampleName, false);

        createODataEntity(format, original);
        ODataEntity actual = compareEntities(format, original, id, null);

        clean(format, actual, false);
    }

    @Test
    public void createWithInlineAsAtom() {
        final ODataFormat format = ODataFormat.ATOM;
        final int id = 3;
        final String sampleName = "Sample customer";
        final ODataEntity original = getSampleCustomerProfile(id, sampleName, true);

        createODataEntity(format, original);
        ODataEntity actual = compareEntities(format, original, id, Collections.<String>singleton("Info"));

        clean(format, actual, true);
    }

    @Test
    public void createWithInlineAsJSON() {
        // this needs to be full, otherwise there is no mean to recognize links
        final ODataFormat format = ODataFormat.JSON_FULL_METADATA;
        final int id = 4;
        final String sampleName = "Sample customer";
        final ODataEntity original = getSampleCustomerProfile(id, sampleName, true);

        createODataEntity(format, original);
        ODataEntity actual = compareEntities(format, original, id, Collections.<String>singleton("Info"));

        clean(format, actual, true);
    }

    @Test
    public void createWithNavigationAsAtom() {
        final ODataFormat format = ODataFormat.ATOM;
        ODataEntity actual = createWithNavigationLink(format, 5);
        clean(format, actual, false);
    }

    @Test
    @Ignore
    public void createWithNavigationAsJSON() {
        // this needs to be full, otherwise there is no mean to recognize links
        final ODataFormat format = ODataFormat.JSON_FULL_METADATA;
        ODataEntity actual = createWithNavigationLink(format, 6);
        clean(format, actual, false);
    }
}
