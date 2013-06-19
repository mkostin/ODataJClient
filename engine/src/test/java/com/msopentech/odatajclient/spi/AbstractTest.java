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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import com.msopentech.odatajclient.engine.data.ODataCollectionValue;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.utils.ODataFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTest {

    /**
     * Logger.
     */
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractTest.class);

    protected static String testODataServiceRootURL;

    @BeforeClass
    public static void setUpODataServiceRoot() throws IOException {
        InputStream propStream = null;
        try {
            propStream = AbstractTest.class.getResourceAsStream("/test.properties");
            Properties props = new Properties();
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

                checkPropertyValue(prop.getValue(), actualProp.getValue());
            } else {
                // nothing ... maybe :FC_KeepInContent="false"
                // ..... no assert can be done ....
            }
        }
    }

    protected void checkPropertyValue(final ODataValue original, final ODataValue actual) {
        assertNotNull(original);
        assertNotNull(actual);
        assertEquals(original.getClass().getSimpleName(), actual.getClass().getSimpleName());

        if (original instanceof ODataComplexValue) {
            final List<ODataProperty> originalFileds = new ArrayList<ODataProperty>();
            for (ODataProperty prop : (ODataComplexValue) original) {
                originalFileds.add(prop);
            }

            final List<ODataProperty> actualFileds = new ArrayList<ODataProperty>();
            for (ODataProperty prop : (ODataComplexValue) actual) {
                actualFileds.add(prop);
            }

            checkProperties(originalFileds, actualFileds);
        } else if (original instanceof ODataCollectionValue) {
            assertTrue(((ODataCollectionValue) original).size() <= ((ODataCollectionValue) actual).size());

            boolean found = false;
            for (ODataValue originalValue : (ODataCollectionValue) original) {
                for (ODataValue actualValue : (ODataCollectionValue) actual) {
                    try {
                        checkPropertyValue(originalValue, actualValue);
                        found = true;
                    } catch (AssertionError ignore) {
                        // ignore
                    }
                }
            }

            assertTrue("Found " + actual + " but expected " + original, found);
        } else {
            assertEquals("Primitive value type  mismatch",
                    ((ODataPrimitiveValue) original).getTypeName(), ((ODataPrimitiveValue) actual).getTypeName());
            assertEquals("Primitive value  mismatch",
                    ((ODataPrimitiveValue) original).toString(), ((ODataPrimitiveValue) actual).toString());
        }
    }

    protected ODataEntity getSampleCustomerProfile(final int id, final String sampleName) {
        ODataEntity entity = ODataFactory.newEntity("Customer");

        // add name attribute
        final ODataPrimitiveValue nameValue = new ODataPrimitiveValue(sampleName, EdmSimpleType.STRING);
        ODataProperty name = new ODataProperty("Name", nameValue);
        entity.addProperty(name);

        // add key attribute
        final ODataPrimitiveValue keyValue = new ODataPrimitiveValue(id, EdmSimpleType.INT_32);
        ODataProperty key = new ODataProperty("CustomerId", keyValue);
        entity.addProperty(key);

        // add BackupContactInfo attribute (collection)
        ODataCollectionValue backupContactInfoValue = new ODataCollectionValue(
                "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails)");
        ODataProperty backupContactInfo = new ODataProperty("BackupContactInfo", backupContactInfoValue);
        entity.addProperty(backupContactInfo);

        // add BackupContactInfo.ContactDetails attribute (complex)
        ODataComplexValue contactDetails = new ODataComplexValue(
                "Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails");
        backupContactInfoValue.add(contactDetails);

        // add BackupContactInfo.ContactDetails.AlternativeNames attribute (collection)
        ODataCollectionValue alternativeNamesValue = new ODataCollectionValue("Collection(Edm.String)");
        alternativeNamesValue.add(new ODataPrimitiveValue("myname", EdmSimpleType.STRING));
        contactDetails.add(new ODataProperty("AlternativeNames", alternativeNamesValue));

        // add BackupContactInfo.ContactDetails.EmailBag attribute (collection)
        ODataCollectionValue emailBagValue = new ODataCollectionValue("Collection(Edm.String)");
        emailBagValue.add(new ODataPrimitiveValue("myname@mydomain.com", EdmSimpleType.STRING));
        contactDetails.add(new ODataProperty("EmailBag", emailBagValue));

        // add BackupContactInfo.ContactDetails.ContactAlias attribute (complex)
        ODataComplexValue contactAliasValue = new ODataComplexValue(
                "Microsoft.Test.OData.Services.AstoriaDefaultService.Aliases");
        contactDetails.add(new ODataProperty("ContactAlias", contactAliasValue));

        // add BackupContactInfo.ContactDetails.ContactAlias.AlternativeNames attribute (collection)
        alternativeNamesValue = new ODataCollectionValue("Collection(Edm.String)");
        alternativeNamesValue.add(new ODataPrimitiveValue("myAlternativeName", EdmSimpleType.STRING));
        contactAliasValue.add(new ODataProperty("AlternativeNames", alternativeNamesValue));

        return entity;
    }
}
