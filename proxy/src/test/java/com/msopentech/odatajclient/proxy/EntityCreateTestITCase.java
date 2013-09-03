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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.DefaultContainer;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Customer;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Order;
import java.util.Collections;
import org.junit.Test;

/**
 * This is the unit test class to check entity retrieve operations.
 */
public class EntityCreateTestITCase extends AbstractTest {

    @Test
    public void create() {
        final String sampleName = "sample customer from proxy";
        final Integer id = 100;

        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        getSampleCustomerProfile(id, sampleName, container);
        container.getCustomer().flush();

        Customer actual = readCustomer(container, id);
        checKSampleCustomerProfile(actual, id, sampleName);

        container.getCustomer().delete(Collections.<Customer>singleton(actual));
        actual = container.getCustomer().get(id);
        assertNull(actual);

        entityContext.detachAll();
        actual = container.getCustomer().get(id);
        assertNotNull(actual);

        container.getCustomer().delete(Collections.<Customer>singleton(actual));
        container.getCustomer().flush();

        actual = container.getCustomer().get(id);
        assertNull(actual);

        entityContext.detachAll();
        actual = container.getCustomer().get(id);
        assertNull(actual);
    }

    @Test
    public void createWithNavigation() {
        final String sampleName = "sample customer from proxy with navigation";
        final Integer id = 101;

        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final Customer original = getSampleCustomerProfile(id, sampleName, container);
        original.setInfo(container.getCustomerInfo().get(16));
        container.getCustomer().flush();

        Customer actual = readCustomer(container, id);
        checKSampleCustomerProfile(actual, id, sampleName);
        assertEquals(Integer.valueOf(16), actual.getInfo().getCustomerInfoId());

        container.getCustomer().delete(Collections.<Customer>singleton(actual));
        container.getCustomer().flush();

        actual = container.getCustomer().get(id);
        assertNull(actual);
    }

    @Test
    public void createWithBackNavigation() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final String sampleName = "sample customer from proxy with back navigation";
        final Integer id = 102;

        Order order = container.getOrder().newEntity();
        order.setCustomerId(id);
        order.setOrderId(id); // same id ...

        final Customer customer = getSampleCustomerProfile(id, sampleName, container);

        customer.setOrders(Collections.<Order>singleton(order));
        order.setCustomer(customer);
        container.getCustomer().flush();

        assertEquals(id, order.getOrderId());
        assertEquals(id, order.getCustomerId());

        Customer actual = readCustomer(container, id);
        checKSampleCustomerProfile(actual, id, sampleName);

        assertEquals(1, actual.getOrders().size());
        assertEquals(id, actual.getOrders().iterator().next().getOrderId());
        assertEquals(id, actual.getOrders().iterator().next().getCustomerId());

        order = container.getOrder().get(id);
        assertNotNull(order);
        assertEquals(id, order.getCustomer().getCustomerId());

        container.getOrder().delete(actual.getOrders());
        container.getCustomer().flush();

        order = container.getOrder().get(id);
        assertNull(order);

        actual = readCustomer(container, id);
        assertTrue(actual.getOrders().isEmpty());

        container.getCustomer().delete(Collections.<Customer>singleton(actual));
        container.getCustomer().flush();

        actual = container.getCustomer().get(id);
        assertNull(actual);
    }
}
