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

import static com.msopentech.odatajclient.spi.AbstractTest.LOG;
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
import com.msopentech.odatajclient.engine.data.ODataCollectionValue;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.data.json.JSONEntry;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import com.msopentech.odatajclient.engine.utils.ODataBinder;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import com.msopentech.odatajclient.engine.utils.ODataFactory;
import com.msopentech.odatajclient.engine.utils.SerializationUtils;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This is the unit test class to check basic entity operations.
 */
public class EntityTest extends AbstractTest {

    @Test
    public void readAtomEntry() throws Exception {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(
                "http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc");
        uriBuilder.appendEntityTypeSegment("Categories(1)");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setContentType(ODataFormat.ATOM.toString());

        final InputStream is = req.rowExecute();

        JAXBContext context = JAXBContext.newInstance(AtomEntry.class);
        @SuppressWarnings("unchecked")
        AtomEntry entry = ((JAXBElement<AtomEntry>) context.createUnmarshaller().unmarshal(is)).getValue();
        assertNotNull(entry);
        assertEquals(uriBuilder.build().toASCIIString(), entry.getId());
        assertEquals("ODataDemo.Category", entry.getType());

        Element xmlContent = entry.getContent();
        assertEquals(ODataConstants.ELEM_PROPERTIES, xmlContent.getNodeName());

        boolean entered = false;
        boolean checkID = false;
        for (int i = 0; i < xmlContent.getChildNodes().getLength(); i++) {
            entered = true;

            Node property = xmlContent.getChildNodes().item(i);
            if ("ID".equals(property.getLocalName())) {
                checkID = true;

                assertEquals("Edm.Int32", property.getAttributes().item(0).getTextContent());
                assertEquals("1", property.getTextContent());
            }
        }
        assertTrue(entered);
        assertTrue(checkID);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(entry, System.out);
    }

    @Test
    public void readODataEntity() {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(
                "http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc");
        uriBuilder.appendEntityTypeSegment("Products(1)");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setContentType(ODataFormat.ATOM.toString());

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

    private void createODataEntity(final ODataFormat format, final int id) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("Customer");

        ODataEntity entity = ODataFactory.newEntity("Customer");

        // add some attributes
        final ODataPrimitiveValue nameValue =
                new ODataPrimitiveValue("ODataEntity sample create", EdmSimpleType.STRING);
        ODataProperty name = new ODataProperty("Name", nameValue);
        entity.addProperty(name);

        final ODataPrimitiveValue keyValue = new ODataPrimitiveValue(id, EdmSimpleType.INT_32);
        ODataProperty key = new ODataProperty("CustomerId", keyValue);
        entity.addProperty(key);

        // add colection ...
        ODataCollectionValue backupContactInfoValue = new ODataCollectionValue(
                "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails)");
        ODataProperty backupContactInfo = new ODataProperty("BackupContactInfo", backupContactInfoValue);
        entity.addProperty(backupContactInfo);

        // add complex ....
        ODataComplexValue contactDetails = new ODataComplexValue(
                "Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails");
        backupContactInfoValue.add(contactDetails);

        ODataCollectionValue alternativeNamesValue = new ODataCollectionValue("Collection(Edm.String)");
        alternativeNamesValue.add(new ODataPrimitiveValue("myname", EdmSimpleType.STRING));
        contactDetails.add(new ODataProperty("AlternativeNames", alternativeNamesValue));

        ODataCollectionValue emailBagValue = new ODataCollectionValue("Collection(Edm.String)");
        emailBagValue.add(new ODataPrimitiveValue("myname@mydomain.com", EdmSimpleType.STRING));
        contactDetails.add(new ODataProperty("EmailBag", emailBagValue));

        ODataComplexValue contactAliasValue = new ODataComplexValue(
                "Microsoft.Test.OData.Services.AstoriaDefaultService.Aliases");
        contactDetails.add(new ODataProperty("ContactAlias", contactAliasValue));

        alternativeNamesValue = new ODataCollectionValue("Collection(Edm.String)");
        alternativeNamesValue.add(new ODataPrimitiveValue("myAlternativeName", EdmSimpleType.STRING));
        contactAliasValue.add(new ODataProperty("AlternativeNames", alternativeNamesValue));

        // log before create entity
        if (LOG.isDebugEnabled()) {
            StringWriter writer = new StringWriter();
            SerializationUtils.serializeEntry(ODataBinder.getEntry(entity, AtomEntry.class), writer);
            writer.flush();
            LOG.debug("About to create (Atom)\n{}", writer.toString());

//            writer = new StringWriter();
//            SerializationUtils.serializeEntry(ODataBinder.getEntry(entity, JSONEntry.class), writer);
//            writer.flush();
//            LOG.debug("About to create (JSON)\n{}", writer.toString());
        }

        final ODataEntityCreateRequest createReq =
                ODataRequestFactory.getEntityCreateRequest(uriBuilder.build(), entity);
        createReq.setContentType(format.toString());

        final ODataEntityCreateResponse createRes = createReq.execute();

        assertEquals(201, createRes.getStatusCode());
        assertEquals("Created", createRes.getStatusMessage());

        entity = createRes.getBody();
        assertNotNull(entity);

        // log create entity
        if (LOG.isDebugEnabled()) {
            StringWriter writer = new StringWriter();
            SerializationUtils.serializeEntry(ODataBinder.getEntry(entity, AtomEntry.class), writer);
            writer.flush();
            LOG.debug("Just created (Atom)\n{}", writer.toString());

//            writer = new StringWriter();
//            SerializationUtils.serializeEntry(ODataBinder.getEntry(entity, JSONEntry.class), writer);
//            writer.flush();
//            LOG.debug("Just created (JSON)\n{}", writer.toString());
        }

        // retrieve key object
        key = null;
        backupContactInfo = null;

        for (ODataProperty prop : entity.getProperties()) {
            if (prop.getName().equals("CustomerId")) {
                key = prop;
            } else if (prop.getName().equals("BackupContactInfo")) {
                backupContactInfo = prop;
            }
        }

        assertNotNull(key);
        assertEquals(String.valueOf(id), ((ODataPrimitiveValue) key.getValue()).toString());

        assertNotNull(backupContactInfo);
        assertTrue(backupContactInfo.getValue() instanceof ODataCollectionValue);
        backupContactInfoValue = (ODataCollectionValue) backupContactInfo.getValue();
        assertEquals(1, backupContactInfoValue.size());
        contactDetails = (ODataComplexValue) backupContactInfoValue.iterator().next();
        assertEquals("myname@mydomain.com",
                ((ODataCollectionValue) contactDetails.get("EmailBag").getValue()).iterator().next().toString());

        final URI selflLink = entity.getLink();
        assertNotNull(selflLink);

        final ODataDeleteRequest deleteReq = ODataRequestFactory.getDeleteRequest(createRes.getBody().getEditLink());
        ODataDeleteResponse deleteRes = deleteReq.execute();

        assertEquals(204, deleteRes.getStatusCode());
        assertEquals("No Content", deleteRes.getStatusMessage());

        deleteRes.close();

        final ODataEntityRequest retrieveReq = ODataRetrieveRequestFactory.getEntityRequest(selflLink);
        retrieveReq.setContentType(ODataFormat.ATOM.toString());

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

    @Test @Ignore
    public void createODataEntityAsJSON() {
        createODataEntity(ODataFormat.JSON, 2);
    }
}
