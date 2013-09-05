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

import static com.msopentech.odatajclient.proxy.AbstractTest.container;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Customer;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.Message;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Car;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Computer;
import java.io.IOException;
import java.util.Iterator;
import org.junit.Test;

/**
 * This is the unit test class to check basic feed operations.
 */
public class EntitySetTestITCase extends AbstractTest {

    @Test
    public void count() {
        final Message message = container.getMessage();

        assertNotNull(message);
        assertEquals(Long.valueOf(10L), message.count());

        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.Customer customers =
                container.getCustomer();

        assertNotNull(customers);
        assertTrue(customers.count() > 0);
    }

    @Test
    public void getAll() {
        int count = 0;
        for (Customer customer : container.getCustomer().getAll()) {
            assertNotNull(customer);
            count++;
        }
        assertTrue(count >= 10);
    }

    @Test
    public void readEntitySetWithNextLink() {
        int count = 0;
        for (Customer customer : container.getCustomer().getAll()) {
            assertNotNull(customer);
            count++;
        }
        assertTrue(count >= 10);

        int iterating = 0;
        for (Customer customer : container.getCustomer()) {
            assertNotNull(customer);
            iterating++;
        }
        assertEquals(count, iterating);
    }

    @Test
    public void readODataEntitySet() throws IOException {
        assertTrue(container.getCar().count() >= 10);

        final Iterable<Car> car = container.getCar().getAll();
        assertNotNull(car);

        final Iterator<Car> itor = car.iterator();

        int count = 0;
        while (itor.hasNext()) {
            assertNotNull(itor.next());
            count++;
        }
        assertTrue(count >= 10);
    }

    @Test
    public void readEntitySetIterator() {
        int count = 0;
        for (Computer computer : container.getComputer()) {
            assertNotNull(computer);
            count++;
        }
        assertTrue(count >= 10);
    }
}
