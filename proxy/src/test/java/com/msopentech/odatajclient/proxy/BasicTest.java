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
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.Customer;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.DefaultContainer;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.Message;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.AllSpatialTypes;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Car;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ConcurrencyInfo;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Order;
import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.junit.BeforeClass;
import org.junit.Test;

public class BasicTest {

    private static EntityContainerFactory entityContainerFactory;

    @BeforeClass
    public static void setup() {
        entityContainerFactory = new EntityContainerFactory();
        entityContainerFactory.setServiceRoot("http://10.10.10.6:8080/DefaultService.svc");
    }

    @Test
    public void count() {
        final DefaultContainer container = entityContainerFactory.getEntityContainer(DefaultContainer.class);
        assertNotNull(container);

        final Message message = container.getMessage();

        assertNotNull(message);
        assertEquals(Long.valueOf(10L), message.count());

        final Customer customers = container.getCustomer();
        assertNotNull(customers);
        assertTrue(customers.count() > 0);
    }

    @Test
    public void get() {
        final DefaultContainer container = entityContainerFactory.getEntityContainer(DefaultContainer.class);
        assertNotNull(container);

        final Order order = container.getOrder().get(-9);
        assertNotNull(order);
        assertTrue(-9 == order.getOrderId());

        final ConcurrencyInfo concurrency = order.getConcurrency();
        assertNotNull(concurrency);
        assertEquals("2012-02-12T11:32:50", new SimpleDateFormat(
                EdmSimpleType.DATE_TIME.pattern()).format(new Date(concurrency.getQueriedDateTime().getTime())));
        assertTrue(78 == order.getCustomerId());

        final Car car = container.getCar().get(11);
        assertNotNull(car);
        assertEquals("cenbviijieljtrtdslbuiqubcvhxhzenidqdnaopplvlqc", car.getDescription());

        final AllSpatialTypes geo = container.getAllGeoTypesSet().get(-10);
        assertNotNull(geo);
        assertEquals(52.8606, geo.getGeogPoint().getY(), 0);
        assertEquals(173.334, geo.getGeogPoint().getX(), 0);
    }

    @Test
    public void getAll() {
        final DefaultContainer container = entityContainerFactory.getEntityContainer(DefaultContainer.class);
        assertNotNull(container);

        final Iterable<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer> customers =
                container.getCustomer().getAll();
        assertNotNull(customers);

        final Iterator<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer> itor =
                customers.iterator();

        int count = 0;
        while (itor.hasNext()) {
            assertNotNull(itor.next());
            count++;
        }
        assertTrue(2 == count);
    }
}
