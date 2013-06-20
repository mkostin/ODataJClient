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
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import com.msopentech.odatajclient.engine.utils.SerializationUtils;
import java.io.InputStream;
import java.net.URI;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This is the unit test class to check basic entity operations.
 */
public class EntityTest extends AbstractTest {

    private void readEntry(final ODataFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("Customer(-10)");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final InputStream is = req.rowExecute();

        EntryResource entry = SerializationUtils.deserializeEntry(is, format.getEntryClass());
        assertNotNull(entry);

        debugEntry(entry, "Just read");

        assertEquals(uriBuilder.build().toASCIIString(), entry.getId());
        assertEquals("Microsoft.Test.OData.Services.AstoriaDefaultService.Customer", entry.getType());
        assertNotNull(entry.getBaseURI());

        final Element content = entry.getContent();
        assertEquals(ODataConstants.ELEM_PROPERTIES, content.getNodeName());

        boolean entered = false;
        boolean checked = false;
        for (int i = 0; i < content.getChildNodes().getLength(); i++) {
            entered = true;

            Node property = content.getChildNodes().item(i);
            if ("PrimaryContactInfo".equals(property.getLocalName())) {
                checked = true;

                assertEquals("Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails",
                        property.getAttributes().item(0).getTextContent());
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
        readEntry(ODataFormat.JSON_FULL_METADATA);
    }

    private void readODataEntity(final ODataFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(
                "http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc");
        uriBuilder.appendEntityTypeSegment("Products(1)");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataQueryResponse<ODataEntity> res = req.execute();
        final ODataEntity entity = res.getBody();

        assertNotNull(entity);
        assertEquals("ODataDemo.Product", entity.getName());
        assertEquals("http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc/Products(1)",
                entity.getEditLink().toASCIIString());

        assertEquals(2, entity.getNavigationLinks().size());
        assertEquals(2, entity.getAssociationLinks().size());

        boolean check = false;

        for (ODataLink link : entity.getNavigationLinks()) {
            if ("Category".equals(link.getName())
                    && "http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc/Products(1)/Category".
                    equals(link.getLink().toASCIIString())) {
                check = true;
            }
        }

        assertTrue(check);
    }

    @Test
    public void readODataEntityFromAtom() {
        readODataEntity(ODataFormat.ATOM);
    }

    @Test
    public void readODataEntityFromJSON() {
        readODataEntity(ODataFormat.JSON_FULL_METADATA);
    }

    private void createODataEntity(final ODataFormat format, final int id) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("Customer");

        final String sampleName = "Sample customer";

        ODataEntity original = getSampleCustomerProfile(id, sampleName);

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

        // check defined properties equality
        checkProperties(original.getProperties(), created.getProperties());

        final URI selflLink = created.getLink();
        assertNotNull(selflLink);

        final ODataDeleteRequest deleteReq = ODataRequestFactory.getDeleteRequest(createRes.getBody().getEditLink());
        ODataDeleteResponse deleteRes = deleteReq.execute();

        assertEquals(204, deleteRes.getStatusCode());
        assertEquals("No Content", deleteRes.getStatusMessage());

        deleteRes.close();

        final ODataEntityRequest retrieveReq = ODataRetrieveRequestFactory.getEntityRequest(selflLink);
        retrieveReq.setFormat(ODataFormat.ATOM);

        final ODataQueryResponse<ODataEntity> retrieveRes = retrieveReq.execute();
        assertEquals(404, retrieveRes.getStatusCode());

        Throwable t = null;
        try {
            retrieveRes.getBody();
        } catch (Exception e) {
            t = e;
        }

        assertNotNull(t);

        retrieveRes.close();
    }

    @Test
    public void createODataEntityAsAtom() {
        createODataEntity(ODataFormat.ATOM, 1);
    }

    public void createODataEntityAsJSON() {
        createODataEntity(ODataFormat.JSON, 2);
    }
}
