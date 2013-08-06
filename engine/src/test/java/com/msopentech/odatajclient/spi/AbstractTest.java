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

import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataDeleteRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataCollectionValue;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.FeedResource;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataInlineEntity;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.data.json.JSONEntry;
import com.msopentech.odatajclient.engine.data.ODataBinder;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.Serializer;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTest {

    /**
     * Logger.
     */
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractTest.class);

    protected static final String TEST_CUSTOMER = "Customer(-10)";

    protected static final String TEST_PRODUCT = "Product(-10)";

    protected static final String TEST_PRODUCT_TYPE = "Microsoft.Test.OData.Services.AstoriaDefaultService.Product";

    protected static final String servicesODataServiceRootURL =
            "http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc/";

    protected static String testODataServiceRootURL;

    @BeforeClass
    public static void setUpODataServiceRoot() throws IOException {
        InputStream propStream = null;
        try {
            propStream = AbstractTest.class.getResourceAsStream("/test.properties");
            final Properties props = new Properties();
            props.load(propStream);

            testODataServiceRootURL = props.getProperty("test.odata.serviceroot.url");
        } catch (Exception e) {
            LOG.error("Could not load test.properties", e);
        } finally {
            if (propStream != null) {
                propStream.close();
            }
        }
        assertNotNull("Check value for the 'testODataServiceRootURL' property", testODataServiceRootURL);
    }

    protected void checkLinks(final Collection<ODataLink> original, final Collection<ODataLink> actual) {
        assertTrue(original.size() <= actual.size());

        for (ODataLink originalLink : original) {
            ODataLink foundOriginal = null;
            ODataLink foundActual = null;

            for (ODataLink actualLink : actual) {

                if (actualLink.getType() == originalLink.getType()
                        && actualLink.getLink().toASCIIString().endsWith(originalLink.getLink().toASCIIString())
                        && actualLink.getName().equals(originalLink.getName())) {

                    foundOriginal = originalLink;
                    foundActual = actualLink;
                }
            }

            assertNotNull(foundOriginal);
            assertNotNull(foundActual);

            if (foundOriginal instanceof ODataInlineEntity && foundActual instanceof ODataInlineEntity) {
                final ODataEntity originalInline = ((ODataInlineEntity) foundOriginal).getEntity();
                assertNotNull(originalInline);

                final ODataEntity actualInline = ((ODataInlineEntity) foundActual).getEntity();
                assertNotNull(actualInline);

                checkProperties(originalInline.getProperties(), actualInline.getProperties());
            }
        }
    }

    protected void checkProperties(final Collection<ODataProperty> original, final Collection<ODataProperty> actual) {
        assertTrue(original.size() <= actual.size());

        // re-organize actual properties into a Map<String, ODataProperty>
        final Map<String, ODataProperty> actualProps = new HashMap<String, ODataProperty>(actual.size());

        for (ODataProperty prop : actual) {
            assertFalse(actualProps.containsKey(prop.getName()));
            actualProps.put(prop.getName(), prop);
        }

        assertTrue(actual.size() <= actualProps.size());

        for (ODataProperty prop : original) {
            assertNotNull(prop);
            if (actualProps.containsKey(prop.getName())) {
                final ODataProperty actualProp = actualProps.get(prop.getName());
                assertNotNull(actualProp);

                if (prop.getValue() != null && actualProp.getValue() != null) {
                    checkPropertyValue(prop.getName(), prop.getValue(), actualProp.getValue());
                }
            } else {
                // nothing ... maybe :FC_KeepInContent="false"
                // ..... no assert can be done ....
            }
        }
    }

    protected void checkPropertyValue(final String propertyName,
            final ODataValue original, final ODataValue actual) {

        assertNotNull("Null original value for " + propertyName, original);
        assertNotNull("Null actual value for " + propertyName, actual);

        assertEquals("Type mismatch for '" + propertyName + "'",
                original.getClass().getSimpleName(), actual.getClass().getSimpleName());

        if (original.isComplex()) {
            final List<ODataProperty> originalFileds = new ArrayList<ODataProperty>();
            for (ODataProperty prop : original.asComplex()) {
                originalFileds.add(prop);
            }

            final List<ODataProperty> actualFileds = new ArrayList<ODataProperty>();
            for (ODataProperty prop : (ODataComplexValue) actual) {
                actualFileds.add(prop);
            }

            checkProperties(originalFileds, actualFileds);
        } else if (original.isCollection()) {
            assertTrue(original.asCollection().size() <= actual.asCollection().size());

            boolean found = original.asCollection().isEmpty();

            for (ODataValue originalValue : original.asCollection()) {
                for (ODataValue actualValue : actual.asCollection()) {
                    try {
                        checkPropertyValue(propertyName, originalValue, actualValue);
                        found = true;
                    } catch (AssertionError ignore) {
                        // ignore
                    }
                }
            }

            assertTrue("Found " + actual + " but expected " + original, found);
        } else {
            assertTrue("Primitive value for '" + propertyName + "' type mismatch",
                    original.asPrimitive().getTypeName().equals(actual.asPrimitive().getTypeName()));

            assertEquals("Primitive value for '" + propertyName + "' mismatch",
                    original.asPrimitive().toString(), actual.asPrimitive().toString());
        }
    }

    protected ODataEntity getSampleCustomerInfo(final int id, final String sampleinfo) {
        final ODataEntity entity =
                ODataFactory.newEntity("Microsoft.Test.OData.Services.AstoriaDefaultService.CustomerInfo");
        entity.setMediaEntity(true);

        entity.addProperty(ODataFactory.newPrimitiveProperty("Information",
                new ODataPrimitiveValue.Builder().setText(sampleinfo).setType(EdmSimpleType.STRING).build()));

        return entity;
    }

    protected ODataEntity getSampleCustomerProfile(
            final int id, final String sampleName, final boolean withInlineInfo) {

        final ODataEntity entity =
                ODataFactory.newEntity("Microsoft.Test.OData.Services.AstoriaDefaultService.Customer");

        // add name attribute
        entity.addProperty(ODataFactory.newPrimitiveProperty("Name",
                new ODataPrimitiveValue.Builder().setText(sampleName).setType(EdmSimpleType.STRING).build()));

        // add key attribute
        entity.addProperty(ODataFactory.newPrimitiveProperty("CustomerId",
                new ODataPrimitiveValue.Builder().setText(String.valueOf(id)).setType(EdmSimpleType.INT_32).build()));

        // add BackupContactInfo attribute (collection)
        final ODataCollectionValue backupContactInfoValue = new ODataCollectionValue(
                "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails)");
        entity.addProperty(ODataFactory.newCollectionProperty("BackupContactInfo",
                backupContactInfoValue));

        // add BackupContactInfo.ContactDetails attribute (complex)
        final ODataComplexValue contactDetails = new ODataComplexValue(
                "Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails");
        backupContactInfoValue.add(contactDetails);

        // add BackupContactInfo.ContactDetails.AlternativeNames attribute (collection)
        final ODataCollectionValue altNamesValue = new ODataCollectionValue("Collection(Edm.String)");
        altNamesValue.add(new ODataPrimitiveValue.Builder().
                setText("myname").setType(EdmSimpleType.STRING).build());
        contactDetails.add(ODataFactory.newCollectionProperty("AlternativeNames", altNamesValue));

        // add BackupContactInfo.ContactDetails.EmailBag attribute (collection)
        final ODataCollectionValue emailBagValue = new ODataCollectionValue("Collection(Edm.String)");
        emailBagValue.add(new ODataPrimitiveValue.Builder().
                setText("myname@mydomain.com").setType(EdmSimpleType.STRING).build());
        contactDetails.add(ODataFactory.newCollectionProperty("EmailBag", emailBagValue));

        // add BackupContactInfo.ContactDetails.ContactAlias attribute (complex)
        final ODataComplexValue contactAliasValue = new ODataComplexValue(
                "Microsoft.Test.OData.Services.AstoriaDefaultService.Aliases");
        contactDetails.add(ODataFactory.newComplexProperty("ContactAlias", contactAliasValue));

        // add BackupContactInfo.ContactDetails.ContactAlias.AlternativeNames attribute (collection)
        final ODataCollectionValue aliasAltNamesValue = new ODataCollectionValue("Collection(Edm.String)");
        aliasAltNamesValue.add(new ODataPrimitiveValue.Builder().
                setText("myAlternativeName").setType(EdmSimpleType.STRING).build());
        contactAliasValue.add(ODataFactory.newCollectionProperty("AlternativeNames", aliasAltNamesValue));

        if (withInlineInfo) {
            entity.addLink(ODataFactory.newInlineEntity(
                    "Info",
                    URI.create("Customer(" + id + ")/Info"),
                    getSampleCustomerInfo(id, sampleName + "_Info")));
        }

        return entity;
    }

    protected void debugEntry(final EntryResource entry, final String message) {
        if (LOG.isDebugEnabled()) {
            final StringWriter writer = new StringWriter();
            Serializer.entry(entry, writer);
            writer.flush();
            LOG.debug(message + "\n{}", writer.toString());
        }
    }

    protected void debugFeed(final FeedResource feed, final String message) {
        if (LOG.isDebugEnabled()) {
            final StringWriter writer = new StringWriter();
            Serializer.feed(feed, writer);
            writer.flush();
            LOG.debug(message + "\n{}", writer.toString());
        }
    }

    protected void debugODataProperty(final ODataProperty property, final String message) {
        LOG.debug(message + "\n{}", property.toString());
    }

    protected void debugODataValue(final ODataValue value, final String message) {
        LOG.debug(message + "\n{}", value.toString());
    }

    protected void debugODataEntity(final ODataEntity entity, final String message) {
        if (LOG.isDebugEnabled()) {
            StringWriter writer = new StringWriter();
            Serializer.entry(ODataBinder.getEntry(entity, AtomEntry.class), writer);
            writer.flush();
            LOG.debug(message + " (Atom)\n{}", writer.toString());

            writer = new StringWriter();
            Serializer.entry(ODataBinder.getEntry(entity, JSONEntry.class), writer);
            writer.flush();
            LOG.debug(message + " (JSON)\n{}", writer.toString());
        }
    }

    protected void debugInputStream(final InputStream input, final String message) {
        if (LOG.isDebugEnabled()) {
            try {
                LOG.debug(message + "\n{}", IOUtils.toString(input));
            } catch (IOException e) {
                LOG.error("Error writing stream", e);
            } finally {
                IOUtils.closeQuietly(input);
            }
        }
    }

    protected String getETag(final URI uri) {
        final ODataRetrieveResponse<ODataEntity> res = ODataRetrieveRequestFactory.getEntityRequest(uri).execute();
        try {
            return res.getEtag();
        } finally {
            res.close();
        }
    }

    protected ODataEntity createEmployee(final ODataPubFormat format) {
        final ODataEntity employee = ODataFactory.newEntity(
                "Microsoft.Test.OData.Services.AstoriaDefaultService.Employee");

        employee.addProperty(ODataFactory.newPrimitiveProperty("PersonId", new ODataPrimitiveValue.Builder().
                setText("1244").setType(EdmSimpleType.INT_32).build()));
        employee.addProperty(ODataFactory.newPrimitiveProperty("Name", new ODataPrimitiveValue.Builder().
                setText("Test employee").build()));
        employee.addProperty(ODataFactory.newPrimitiveProperty("ManagersPersonId", new ODataPrimitiveValue.Builder().
                setText("3777").setType(EdmSimpleType.INT_32).build()));
        employee.addProperty(ODataFactory.newPrimitiveProperty("Salary", new ODataPrimitiveValue.Builder().
                setText("1000").setType(EdmSimpleType.INT_32).build()));
        employee.addProperty(ODataFactory.newPrimitiveProperty("Title", new ODataPrimitiveValue.Builder().
                setText("CEO").build()));

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL).
                appendEntityTypeSegment("Person");

        final ODataEntityCreateRequest createReq =
                ODataCUDRequestFactory.getEntityCreateRequest(uriBuilder.build(), employee);
        createReq.setFormat(format);
        final ODataEntityCreateResponse createRes = createReq.execute();
        assertEquals(201, createRes.getStatusCode());

        return createRes.getBody();
    }

    protected void deleteEmployee(final ODataPubFormat format, final Integer id) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL).
                appendEntityTypeSegment("Person").appendKeySegment(id);

        final ODataDeleteRequest deleteReq = ODataCUDRequestFactory.getDeleteRequest(uriBuilder.build());
        deleteReq.setFormat(format);
        final ODataDeleteResponse deleteRes = deleteReq.execute();
        assertEquals(204, deleteRes.getStatusCode());
    }
}
