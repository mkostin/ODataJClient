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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataGenericRetrieveRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.Deserializer;
import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataInlineEntity;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.data.ODataBinder;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataInlineEntitySet;
import com.msopentech.odatajclient.engine.data.ODataObjectWrapper;
import com.msopentech.odatajclient.engine.data.ResourceFactory;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This is the unit test class to check entity retrieve operations.
 */
public class EntityRetrieveTest extends AbstractTest {

    protected String getServiceRoot() {
        return testDefaultServiceRootURL;
    }

    private void readEntry(final ODataPubFormat format) throws IOException {
        // ---------------------------------------------
        // Read Car(16)
        // ---------------------------------------------
        ODataURIBuilder uriBuilder = new ODataURIBuilder(getServiceRoot());
        uriBuilder.appendEntityTypeSegment("Car").appendKeySegment(16);

        ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        InputStream input = req.rawExecute();

        EntryResource entry = Deserializer.toEntry(input, ResourceFactory.entryClassForFormat(format));
        assertNotNull(entry);

        input.close();

        debugEntry(entry, "Just read");
        // ---------------------------------------------


        // ---------------------------------------------
        // Read Customer(-10)
        // ---------------------------------------------
        uriBuilder = new ODataURIBuilder(getServiceRoot()).
                appendEntityTypeSegment("Customer").appendKeySegment(-10);

        req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        input = req.rawExecute();

        entry = Deserializer.toEntry(input, ResourceFactory.entryClassForFormat(format));
        assertNotNull(entry);

        input.close();

        debugEntry(entry, "Just read");
        // ---------------------------------------------

        if (ODataPubFormat.JSON_FULL_METADATA == format || ODataPubFormat.ATOM == format) {
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

            final Node property = content.getChildNodes().item(i);
            if ("PrimaryContactInfo".equals(property.getLocalName())) {
                checked = true;

                if (ODataPubFormat.JSON_FULL_METADATA == format || ODataPubFormat.ATOM == format) {
                    assertEquals("Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails",
                            ((Element) property).getAttribute(ODataConstants.ATTR_TYPE));
                }
            }
        }
        assertTrue(entered);
        assertTrue(checked);
    }

    @Test
    public void atomEntry() throws IOException {
        readEntry(ODataPubFormat.ATOM);
    }

    @Test
    public void jsonEntry() throws IOException {
        readEntry(ODataPubFormat.JSON);
    }

    private void read(final ODataPubFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(getServiceRoot()).
                appendEntityTypeSegment("Customer").appendKeySegment(-10);

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataRetrieveResponse<ODataEntity> res = req.execute();
        final ODataEntity entity = res.getBody();
        assertNotNull(entity);

        if (ODataPubFormat.JSON_FULL_METADATA == format || ODataPubFormat.ATOM == format) {
            assertEquals("Microsoft.Test.OData.Services.AstoriaDefaultService.Customer", entity.getName());
            assertEquals(getServiceRoot() + "/Customer(-10)", entity.getEditLink().toASCIIString());
            assertEquals(5, entity.getNavigationLinks().size());
            assertEquals(2, entity.getEditMediaLinks().size());

            boolean check = false;

            for (ODataLink link : entity.getNavigationLinks()) {
                if ("Wife".equals(link.getName())
                        && (getServiceRoot() + "/Customer(-10)/Wife").equals(link.getLink().toASCIIString())) {

                    check = true;
                }
            }

            assertTrue(check);
        }
    }

    @Test
    public void fromAtom() {
        read(ODataPubFormat.ATOM);
    }

    @Test
    public void fromJSON() {
        read(ODataPubFormat.JSON);
    }

    private void withGeospatial(final ODataPubFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(getServiceRoot()).
                appendEntityTypeSegment("AllGeoTypesSet").appendKeySegment(-8);

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataRetrieveResponse<ODataEntity> res = req.execute();
        final ODataEntity entity = res.getBody();

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
    }

    @Test
    public void withGeospatialFromAtom() {
        withGeospatial(ODataPubFormat.ATOM);
    }

    @Test
    public void withGeospatialFromJSON() {
        // this needs to be full, otherwise there is no mean to recognize geospatial types
        withGeospatial(ODataPubFormat.JSON_FULL_METADATA);
    }

    private void withInlineEntry(final ODataPubFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(getServiceRoot()).
                appendEntityTypeSegment("Customer").appendKeySegment(-10).expand("Info");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataRetrieveResponse<ODataEntity> res = req.execute();
        final ODataEntity entity = res.getBody();

        assertNotNull(entity);
        assertEquals("Microsoft.Test.OData.Services.AstoriaDefaultService.Customer", entity.getName());
        assertEquals(getServiceRoot() + "/Customer(-10)", entity.getEditLink().toASCIIString());

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
    public void withInlineEntryFromAtom() {
        withInlineEntry(ODataPubFormat.ATOM);
    }

    @Test
    public void withInlineEntryFromJSON() {
        // this needs to be full, otherwise there is no mean to recognize links
        withInlineEntry(ODataPubFormat.JSON_FULL_METADATA);
    }

    private void withInlineFeed(final ODataPubFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(getServiceRoot()).
                appendEntityTypeSegment("Customer").appendKeySegment(-10).expand("Orders");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataRetrieveResponse<ODataEntity> res = req.execute();
        final ODataEntity entity = res.getBody();
        assertNotNull(entity);

        boolean found = false;

        for (ODataLink link : entity.getNavigationLinks()) {
            if (link instanceof ODataInlineEntitySet) {
                final ODataEntitySet inline = ((ODataInlineEntitySet) link).getEntitySet();
                assertNotNull(inline);

                debugFeed(ODataBinder.getFeed(inline, ResourceFactory.feedClassForFormat(format)), "Just read");

                found = true;
            }
        }

        assertTrue(found);
    }

    @Test
    public void withInlineFeedFromAtom() {
        withInlineFeed(ODataPubFormat.ATOM);
    }

    @Test
    public void withInlineFeedFromJSON() {
        // this needs to be full, otherwise there is no mean to recognize links
        withInlineFeed(ODataPubFormat.JSON_FULL_METADATA);
    }

    private void withActions(final ODataPubFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(getServiceRoot()).
                appendEntityTypeSegment("ComputerDetail").appendKeySegment(-10);

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataRetrieveResponse<ODataEntity> res = req.execute();
        final ODataEntity entity = res.getBody();
        assertNotNull(entity);

        assertEquals(1, entity.getOperations().size());
        assertEquals("ResetComputerDetailsSpecifications", entity.getOperations().get(0).getTitle());
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

    private void genericRequest(final ODataPubFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(getServiceRoot()).
                appendEntityTypeSegment("Car").appendKeySegment(16);

        final ODataGenericRetrieveRequest req =
                ODataRetrieveRequestFactory.getGenericRetrieveRequest(uriBuilder.build());
        req.setFormat(format.toString());

        final ODataRetrieveResponse<ODataObjectWrapper> res = req.execute();

        final ODataObjectWrapper wrapper = res.getBody();

        final ODataEntitySet entitySet = wrapper.getODataEntitySet();
        assertNull(entitySet);

        final ODataEntity entity = wrapper.getODataEntity();
        assertNotNull(entity);
    }

    @Test
    public void genericRequestAsAtom() {
        genericRequest(ODataPubFormat.ATOM);
    }

    @Test
    public void genericRequestAsJSON() {
        // this needs to be full, otherwise actions will not be provided
        genericRequest(ODataPubFormat.JSON_FULL_METADATA);
    }

    private void multiKey(final ODataPubFormat format) {
        final LinkedHashMap<String, Object> multiKey = new LinkedHashMap<String, Object>();
        multiKey.put("FromUsername", "1");
        multiKey.put("MessageId", -10);

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(getServiceRoot()).
                appendEntityTypeSegment("Message").appendKeySegment(multiKey);

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataRetrieveResponse<ODataEntity> res = req.execute();
        final ODataEntity entity = res.getBody();
        assertNotNull(entity);
        assertEquals("1", entity.getProperty("FromUsername").getPrimitiveValue().<String>toCastValue());
    }

    @Test
    public void multiKeyAsAtom() {
        multiKey(ODataPubFormat.ATOM);
    }

    @Test
    public void multiKeyAsJSON() {
        multiKey(ODataPubFormat.JSON_FULL_METADATA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void issue99() {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(getServiceRoot()).
                appendEntitySetSegment("Car");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(ODataPubFormat.JSON);

        // this statement should cause an IllegalArgumentException bearing JsonParseException
        // since we are attempting to parse an EntitySet as if it was an Entity
        req.execute().getBody();
    }
}
