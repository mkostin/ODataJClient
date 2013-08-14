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

import com.msopentech.odatajclient.proxy.AstoriaDefaultService.Customer;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.DefaultContainer;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Car;
import java.io.IOException;
import java.util.Iterator;
import org.junit.Test;

/**
 * This is the unit test class to check basic feed operations.
 */
public class EntitySetTest extends AbstractTest {

    @Test
    public void count() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final com.msopentech.odatajclient.proxy.AstoriaDefaultService.Message message = container.getMessage();

        assertNotNull(message);
        assertEquals(Long.valueOf(10L), message.count());

        final Customer customers = container.getCustomer();
        assertNotNull(customers);
        assertTrue(customers.count() > 0);
    }

    @Test
    public void readEntitySetWithNextLink() {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        final Customer customers = container.getCustomer();

        int count = 0;
        for (com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer customer : customers.getAll()) {
            assertNotNull(customer);
            count++;
        }
        assertEquals(2, count);

        // TODO: add next link  and assert for assertNotNull(customers.getNext()) and URI;
        // final URI expected = URI.create(testDefaultServiceRootURL + "/Customer?$skiptoken=-9");
        // final URI found = URIUtils.getURI(testDefaultServiceRootURL, feed.getNext().toASCIIString());
        // assertEquals(expected, found);
    }

    @Test
    public void readODataEntitySet() throws IOException {
        final DefaultContainer container = getDefaultContainer(testDefaultServiceRootURL);
        // TODO: add top(2).skip(4) feature.
        final com.msopentech.odatajclient.proxy.AstoriaDefaultService.Car cars = container.getCar();
        assertNotNull(cars);
        assertTrue(cars.count() >= 10);

        final Iterable<Car> car = cars.getAll();
        assertNotNull(car);

        final Iterator<Car> itor = car.iterator();

        int count = 0;
        while (itor.hasNext()) {
            assertNotNull(itor.next());
            count++;
        }
        assertTrue(count>=10);
    }

    // TODO: what about etityset iterator?
    @Test
    public void readODataEntitySetIterator() {
    }
}
