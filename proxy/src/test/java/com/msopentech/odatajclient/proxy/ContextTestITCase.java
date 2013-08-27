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
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import com.msopentech.odatajclient.proxy.api.context.AttachedEntityStatus;
import com.msopentech.odatajclient.proxy.api.context.EntityContext;
import com.msopentech.odatajclient.proxy.api.context.LinkContext;
import com.msopentech.odatajclient.proxy.api.impl.EntityTypeInvocationHandler;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.DefaultContainer;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.ContactDetails;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Customer;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.CustomerInfo;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Order;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Phone;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

/**
 * This is the unit test class to check entity retrieve operations.
 */
public class ContextTestITCase extends AbstractTest {

    private final EntityContext entityContext = EntityContainerFactory.getContext().getEntityContext();

    private final LinkContext linkContext = EntityContainerFactory.getContext().getLinkContext();

    @Test
    public void attachDetachNewEntity() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.Customer customers =
                container.getCustomer();
        final Customer customer1 = customers.newEntity();
        final Customer customer2 = customers.newEntity();

        final EntityTypeInvocationHandler source1 =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer1);
        final EntityTypeInvocationHandler source2 =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer2);

        assertTrue(entityContext.isAttached(source1));
        assertTrue(entityContext.isAttached(source2));

        entityContext.detach(source1);
        assertFalse(entityContext.isAttached(source1));
        assertTrue(entityContext.isAttached(source2));

        entityContext.detach(source2);
        assertFalse(entityContext.isAttached(source1));
        assertFalse(entityContext.isAttached(source2));
    }

    @Test
    public void attachDetachExistingEntity() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.Customer customers =
                container.getCustomer();
        final Customer customer1 = customers.get(-10);
        final Customer customer2 = customers.get(-9);
        final Customer customer3 = customers.get(-10);

        final EntityTypeInvocationHandler source1 =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer1);
        final EntityTypeInvocationHandler source2 =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer2);
        final EntityTypeInvocationHandler source3 =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer3);

        assertFalse(entityContext.isAttached(source1));
        assertFalse(entityContext.isAttached(source2));
        assertFalse(entityContext.isAttached(source3));

        entityContext.attach(source1);
        assertTrue(entityContext.isAttached(source1));
        assertFalse(entityContext.isAttached(source2));
        assertTrue(entityContext.isAttached(source3));

        entityContext.attach(source2);
        assertTrue(entityContext.isAttached(source1));
        assertTrue(entityContext.isAttached(source2));
        assertTrue(entityContext.isAttached(source3));

        try {
            entityContext.attach(source3);
            fail();
        } catch (IllegalStateException ignore) {
            // ignore
        }

        entityContext.detach(source1);
        assertFalse(entityContext.isAttached(source1));
        assertTrue(entityContext.isAttached(source2));
        assertFalse(entityContext.isAttached(source3));

        entityContext.detach(source2);
        assertFalse(entityContext.isAttached(source1));
        assertFalse(entityContext.isAttached(source2));
        assertFalse(entityContext.isAttached(source3));
    }

    @Test
    public void addInlineNavigationProperty() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.Customer customers =
                container.getCustomer();
        final Customer customer = customers.newEntity();

        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.CustomerInfo customerInfos =
                container.getCustomerInfo();
        final CustomerInfo customerInfo = customerInfos.newEntity();

        customer.setInfo(customerInfo);

        assertNotNull(customer.getInfo());

        final EntityTypeInvocationHandler source =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer);
        final EntityTypeInvocationHandler target =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customerInfo);

        assertTrue(entityContext.isAttached(source));
        assertEquals(AttachedEntityStatus.NEW, entityContext.getStatus(source));
        assertFalse(entityContext.isAttached(target));
        assertFalse(linkContext.isAttached(source, "Info", target));
        assertFalse(linkContext.isAttached(target, "Customer", source));

        entityContext.detachAll();
        linkContext.detachAll();

        assertFalse(entityContext.isAttached(source));
    }

    @Test
    public void linkTargetExisting() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.Customer customers =
                container.getCustomer();
        final Customer customer = customers.newEntity();

        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.CustomerInfo customerInfos =
                container.getCustomerInfo();
        final CustomerInfo customerInfo = customerInfos.get(11);

        customer.setInfo(customerInfo);

        assertNotNull(customer.getInfo());

        final EntityTypeInvocationHandler source =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer);
        final EntityTypeInvocationHandler target =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customerInfo);

        assertTrue(entityContext.isAttached(source));
        assertEquals(AttachedEntityStatus.NEW, entityContext.getStatus(source));
        assertTrue(entityContext.isAttached(target));
        assertEquals(AttachedEntityStatus.LINKED, entityContext.getStatus(target));
        assertTrue(linkContext.isAttached(source, "Info", target));
        assertFalse(linkContext.isAttached(target, "Customer", source));

        entityContext.detachAll();
        linkContext.detachAll();

        assertFalse(entityContext.isAttached(source));
        assertFalse(entityContext.isAttached(target));
        assertFalse(linkContext.isAttached(source, "Info", target));
    }

    @Test
    public void linkSourceExisting() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.Customer customers =
                container.getCustomer();
        final Customer customer = customers.get(-10);

        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.CustomerInfo customerInfos =
                container.getCustomerInfo();
        final CustomerInfo customerInfo = customerInfos.newEntity();

        customer.setInfo(customerInfo);

        assertNotNull(customer.getInfo());

        final EntityTypeInvocationHandler source =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer);
        final EntityTypeInvocationHandler target =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customerInfo);

        assertTrue(entityContext.isAttached(source));
        assertEquals(AttachedEntityStatus.CHANGED, entityContext.getStatus(source));
        assertTrue(entityContext.isAttached(target));
        assertEquals(AttachedEntityStatus.NEW, entityContext.getStatus(target));
        assertTrue(linkContext.isAttached(source, "Info", target));
        assertFalse(linkContext.isAttached(target, "Customer", source));

        entityContext.detachAll();
        linkContext.detachAll();

        assertFalse(entityContext.isAttached(source));
        assertFalse(entityContext.isAttached(target));
        assertFalse(linkContext.isAttached(source, "Info", target));
    }

    @Test
    public void linkBothExisting() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.Customer customers =
                container.getCustomer();
        final Customer customer = customers.get(-10);

        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.CustomerInfo customerInfos =
                container.getCustomerInfo();
        final CustomerInfo customerInfo = customerInfos.get(12);

        customer.setInfo(customerInfo);

        assertNotNull(customer.getInfo());

        final EntityTypeInvocationHandler source =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer);
        final EntityTypeInvocationHandler target =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customerInfo);

        assertTrue(entityContext.isAttached(source));
        assertEquals(AttachedEntityStatus.CHANGED, entityContext.getStatus(source));
        assertTrue(entityContext.isAttached(target));
        assertEquals(AttachedEntityStatus.LINKED, entityContext.getStatus(target));
        assertTrue(linkContext.isAttached(source, "Info", target));
        assertFalse(linkContext.isAttached(target, "Customer", source));

        entityContext.detachAll();
        linkContext.detachAll();

        assertFalse(entityContext.isAttached(source));
        assertFalse(entityContext.isAttached(target));
        assertFalse(linkContext.isAttached(source, "Info", target));
    }

    @Test
    public void linkEntitySet() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.Customer customers =
                container.getCustomer();
        final Customer customer = customers.newEntity();

        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.Order orders =
                container.getOrder();

        final List<Order> toBeLinked = new ArrayList<Order>();
        toBeLinked.add(orders.newEntity());
        toBeLinked.add(orders.newEntity());
        toBeLinked.add(orders.newEntity());

        customer.setOrders(toBeLinked);

        assertNotNull(customer.getOrders());
        assertEquals(3, customer.getOrders().size());

        final EntityTypeInvocationHandler source =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer);

        assertTrue(entityContext.isAttached(source));
        assertEquals(AttachedEntityStatus.NEW, entityContext.getStatus(source));
        assertTrue(linkContext.isAttached(source, "Orders"));
        assertEquals(3, linkContext.getLinkedEntities(source, "Orders").size());

        for (Order order : toBeLinked) {
            final EntityTypeInvocationHandler target =
                    (EntityTypeInvocationHandler) Proxy.getInvocationHandler(order);

            assertTrue(entityContext.isAttached(target));
            assertEquals(AttachedEntityStatus.NEW, entityContext.getStatus(target));
            assertFalse(linkContext.isAttached(target, "Customer", source));
        }

        entityContext.detachAll();
        linkContext.detachAll();

        assertFalse(entityContext.isAttached(source));
        assertFalse(linkContext.isAttached(source, "Orders"));

        for (Order order : toBeLinked) {
            assertFalse(entityContext.isAttached((EntityTypeInvocationHandler) Proxy.getInvocationHandler(order)));
        }
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

        final EntityTypeInvocationHandler source =
                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer);

        assertTrue(entityContext.isAttached(source));
        assertEquals(AttachedEntityStatus.NEW, entityContext.getStatus(source));

        entityContext.detachAll();
        linkContext.detachAll();

        assertFalse(entityContext.isAttached(source));
    }

    @Test
    public void readEntityInTheContext() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        CustomerInfo customerInfo = container.getCustomerInfo().get(16);
        customerInfo.setInformation("some other info ...");

        assertEquals("some other info ...", customerInfo.getInformation());

        customerInfo = container.getCustomerInfo().get(16);
        assertEquals("some other info ...", customerInfo.getInformation());

        entityContext.detachAll();
        customerInfo = container.getCustomerInfo().get(16);
        assertNotEquals("some other info ...", customerInfo.getInformation());
    }

    @Test
    public void readAllWithEntityInTheContext() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        CustomerInfo customerInfo = container.getCustomerInfo().get(16);
        customerInfo.setInformation("some other info ...");

        assertEquals("some other info ...", customerInfo.getInformation());

        Iterable<CustomerInfo> customerInfos = container.getCustomerInfo().getAll();

        boolean found = false;
        for (CustomerInfo info : customerInfos) {
            if (info.getCustomerInfoId() == 16) {
                assertEquals("some other info ...", customerInfo.getInformation());
                found = true;
            }
        }
        assertTrue(found);

        entityContext.detachAll();

        customerInfos = container.getCustomerInfo().getAll();

        found = false;
        for (CustomerInfo info : customerInfos) {
            if (info.getCustomerInfoId() == 16) {
                assertNotEquals("some other info ...", info.getInformation());
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void flushTest() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);

        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.Order orders =
                container.getOrder();

        final List<Integer> keys = new ArrayList<Integer>();
        keys.add(-200);
        keys.add(-201);
        keys.add(-202);

        final List<Order> toBeLinked = new ArrayList<Order>();
        for (Integer key : keys) {
            Order order = orders.newEntity();
            order.setOrderId(key);
            toBeLinked.add(order);
        }

        Customer customer = container.getCustomer().newEntity();
        customer.setName("samplename");

        customer.setOrders(toBeLinked);

        final CustomerInfo customerInfo = container.getCustomerInfo().get(16);
        customerInfo.setInformation("some new info ...");
        customer.setInfo(customerInfo);

        final ContactDetails cd = new ContactDetails();
        cd.setAlternativeNames(Arrays.asList("alternative1", "alternative2"));
        cd.setEmailBag(Collections.<String>singleton("myemail@mydomain.org"));
        cd.setMobilePhoneBag(Collections.<Phone>emptySet());

        final ContactDetails bcd = new ContactDetails();
        bcd.setAlternativeNames(Arrays.asList("alternative3", "alternative4"));
        bcd.setEmailBag(Collections.<String>emptySet());
        bcd.setMobilePhoneBag(Collections.<Phone>emptySet());

        customer.setPrimaryContactInfo(cd);
        customer.setBackupContactInfo(Collections.<ContactDetails>singletonList(bcd));

        assertTrue(entityContext.isAttached((EntityTypeInvocationHandler) Proxy.getInvocationHandler(customerInfo)));
        assertTrue(entityContext.isAttached((EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer)));
        for (Order linked : toBeLinked) {
            assertTrue(entityContext.isAttached((EntityTypeInvocationHandler) Proxy.getInvocationHandler(linked)));
        }

        container.getCustomer().flush();

        assertFalse(entityContext.isAttached((EntityTypeInvocationHandler) Proxy.getInvocationHandler(customerInfo)));
        assertFalse(entityContext.isAttached((EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer)));
        for (Order linked : toBeLinked) {
            assertFalse(entityContext.isAttached((EntityTypeInvocationHandler) Proxy.getInvocationHandler(linked)));
        }

        assertEquals("some new info ...", container.getCustomerInfo().get(16).getInformation());

        orders.delete(toBeLinked);
        container.getCustomer().delete(customer.getCustomerId());

        assertTrue(entityContext.isAttached((EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer)));
        for (Order linked : toBeLinked) {
            assertTrue(entityContext.isAttached((EntityTypeInvocationHandler) Proxy.getInvocationHandler(linked)));
        }

        container.getCustomer().flush();

        assertFalse(entityContext.isAttached((EntityTypeInvocationHandler) Proxy.getInvocationHandler(customer)));
        for (Order linked : toBeLinked) {
            assertFalse(entityContext.isAttached((EntityTypeInvocationHandler) Proxy.getInvocationHandler(linked)));
        }
    }
}
