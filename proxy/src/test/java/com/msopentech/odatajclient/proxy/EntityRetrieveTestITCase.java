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
package com.msopentech.odatajclient.proxy;

import static com.msopentech.odatajclient.proxy.AbstractTest.testDefaultServiceRootURL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.msopentech.odatajclient.engine.data.ODataTimestamp;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Geospatial;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Geospatial.Type;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiLineString;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Point;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.DefaultContainer;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.AllSpatialTypes;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.ComputerDetail;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.ConcurrencyInfo;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.ContactDetails;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Customer;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.CustomerInfo;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Message;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.MessageKey;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Order;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.junit.Test;

/**
 * This is the unit test class to check entity retrieve operations.
 */
public class EntityRetrieveTestITCase extends AbstractTest {

    @Test
    public void read() {
        readCustomer(getDefaultContainer(testDefaultServiceRootURL), -10);
    }

    @Test
    public void navigate() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final Order order = container.getOrder().get(-9);
        assertNotNull(order);
        assertEquals(Integer.valueOf(-9), order.getOrderId());

        final ConcurrencyInfo concurrency = order.getConcurrency();
        assertNotNull(concurrency);
        assertEquals("2012-02-12T11:32:50.5072026", concurrency.getQueriedDateTime().toString());
        assertEquals(Integer.valueOf(78), order.getCustomerId());
    }

    @Test
    public void withGeospatial() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final AllSpatialTypes allSpatialTypes = container.getAllGeoTypesSet().get(-10);
        assertNotNull(allSpatialTypes);
        assertEquals(Integer.valueOf(-10), allSpatialTypes.getId());

        final MultiLineString geogMultiLine = allSpatialTypes.getGeogMultiLine();
        assertNotNull(geogMultiLine);
        assertEquals(Type.MULTILINESTRING, geogMultiLine.getType());
        assertEquals(Geospatial.Dimension.GEOGRAPHY, geogMultiLine.getDimension());
        assertFalse(geogMultiLine.isEmpty());

        final Point geogPoint = allSpatialTypes.getGeogPoint();
        assertNotNull(geogPoint);
        assertEquals(Type.POINT, geogPoint.getType());
        assertEquals(Geospatial.Dimension.GEOGRAPHY, geogPoint.getDimension());
        assertEquals(52.8606, geogPoint.getY(), 0);
        assertEquals(173.334, geogPoint.getX(), 0);
    }

    // TODO: test for lazy and eager
    @Test
    public void withInlineEntry() {
        final Customer customer = readCustomer(getDefaultContainer(testDefaultServiceRootURL), -10);
        final CustomerInfo customerInfo = customer.getInfo();
        assertNotNull(customerInfo);
        assertEquals(Integer.valueOf(11), customerInfo.getCustomerInfoId());
    }

    // TODO: test for lazy and eager
    @Test
    public void withInlineFeed() {
        final Customer customer = readCustomer(getDefaultContainer(testDefaultServiceRootURL), -10);
        final Collection<Order> orders = customer.getOrders();
        assertFalse(orders.isEmpty());
    }

    // TODO: implement policy for bindable operations
    @Test
    public void withActions() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final ComputerDetail computerDetail = container.getComputerDetail().get(-10);
        assertEquals(Integer.valueOf(-10), computerDetail.getComputerDetailId());

        try {
            // TODO: assert method resetComputerDetailsSpecifications(....) into computerDetail object
            assertNotNull(container.getClass().getMethod("resetComputerDetailsSpecifications",
                    ComputerDetail.class, Collection.class, ODataTimestamp.class));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void multiKey() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final MessageKey messageKey = new MessageKey();
        messageKey.setFromUsername("1");
        messageKey.setMessageId(-10);

        final Message message = container.getMessage().get(messageKey);
        assertNotNull(message);
        assertEquals("1", message.getFromUsername());
    }

    @Test
    public void addProperty() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.Customer customers =
                container.getCustomer();
        final Customer customer = customers.newEntity();

        final ContactDetails cd = new ContactDetails();
        cd.setAlternativeNames(Arrays.asList("alternative1", "alternative2"));

        final ContactDetails bcd = new ContactDetails();
        bcd.setAlternativeNames(Arrays.asList("alternative3", "alternative4"));

        customer.setCustomerId(100);
        customer.setPrimaryContactInfo(cd);
        customer.setBackupContactInfo(Collections.<ContactDetails>singletonList(bcd));

        assertEquals(Integer.valueOf(100), customer.getCustomerId());
        assertNotNull(customer.getPrimaryContactInfo().getAlternativeNames());
        assertEquals(2, customer.getPrimaryContactInfo().getAlternativeNames().size());
        assertTrue(customer.getPrimaryContactInfo().getAlternativeNames().contains("alternative1"));
        assertEquals(2, customer.getBackupContactInfo().iterator().next().getAlternativeNames().size());
        assertTrue(customer.getBackupContactInfo().iterator().next().getAlternativeNames().contains("alternative4"));
    }

    private Customer readCustomer(final DefaultContainer container, int id) {
        final Customer customer = container.getCustomer().get(-10);
        assertNotNull(customer);
        assertEquals(Integer.valueOf(id), customer.getCustomerId());

        return customer;
    }
}
