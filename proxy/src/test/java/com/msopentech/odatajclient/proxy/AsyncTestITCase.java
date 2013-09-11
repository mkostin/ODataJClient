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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.msopentech.odatajclient.proxy.api.AsyncQuery;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.Employee;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.EmployeeCollection;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.Product;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.ProductCollection;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.SpecialEmployee;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.SpecialEmployeeCollection;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.Test;

public class AsyncTestITCase extends AbstractTest {

    @Test
    public void retrieveEntitySet() throws InterruptedException, ExecutionException {
        final Future<ProductCollection> futureProds = asyncContainer.getProduct().getAll();
        assertNotNull(futureProds);

        while (!futureProds.isDone()) {
        }

        final ProductCollection products = futureProds.get();
        assertNotNull(products);
        assertFalse(products.isEmpty());
        for (Product product : products) {
            assertNotNull(product);
        }
    }

    @Test
    public void updateEntity() throws InterruptedException, ExecutionException {
        final String random = UUID.randomUUID().toString();

        final Product product = container.getProduct().get(-10);
        product.setDescription("AsyncTest#updateEntity " + random);

        final Future<Void> futureFlush = asyncContainer.flush();
        assertNotNull(futureFlush);

        while (!futureFlush.isDone()) {
        }

        assertEquals("AsyncTest#updateEntity " + random, asyncContainer.getProduct().get(-10).get().getDescription());
    }

    @Test
    public void polymorphQuery() throws Exception {
        final AsyncQuery<Employee, EmployeeCollection> queryEmployee =
                asyncContainer.getPerson().createQuery(EmployeeCollection.class);

        Exception ex = null;
        try {
            queryEmployee.getResult();
            fail();
        } catch (UnsupportedOperationException e) {
            ex = e;
        }
        assertNotNull(ex);

        assertEquals(7, queryEmployee.asyncGetResult().get().size());

        final AsyncQuery<SpecialEmployee, SpecialEmployeeCollection> querySpecialEmployee =
                asyncContainer.getPerson().createQuery(SpecialEmployeeCollection.class);

        ex = null;
        try {
            querySpecialEmployee.getResult();
            fail();
        } catch (UnsupportedOperationException e) {
            ex = e;
        }
        assertNotNull(ex);

        assertEquals(4, querySpecialEmployee.asyncGetResult().get().size());
    }
}
