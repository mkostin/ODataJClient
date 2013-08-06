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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Point;
import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import com.msopentech.odatajclient.proxy.odatademo.Address;
import com.msopentech.odatajclient.proxy.odatademo.Categories;
import com.msopentech.odatajclient.proxy.odatademo.DemoService;
import com.msopentech.odatajclient.proxy.odatademo.Product;
import com.msopentech.odatajclient.proxy.odatademo.Products;
import com.msopentech.odatajclient.proxy.odatademo.Supplier;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import org.junit.BeforeClass;
import org.junit.Test;

public class BasicTest {

    private static EntityContainerFactory entityContainerFactory;

    @BeforeClass
    public static void setup() {
        entityContainerFactory = new EntityContainerFactory();
        entityContainerFactory.setServiceRoot(
                "http://services.odata.org/v3/(S(s41okrymbq32h4l0imhdvhay))/OData/OData.svc/");
    }

    @Test
    public void count() {
        final DemoService demoService = entityContainerFactory.getEntityContainer(DemoService.class);
        assertNotNull(demoService);

        final Products products = demoService.getProducts();
        assertNotNull(products);
        assertTrue(11 == products.count());

        final Categories categories = demoService.getCategories();
        assertNotNull(categories);
        assertTrue(3 == categories.count());
    }

    @Test
    public void get() {
        final DemoService demoService = entityContainerFactory.getEntityContainer(DemoService.class);
        assertNotNull(demoService);

        final Product product = demoService.getProducts().get(1);
        assertNotNull(product);
        assertTrue(1 == product.getId());
        assertNull(product.getDiscontinuedDate());
        assertEquals("1995-10-01T00:00:00",
                new SimpleDateFormat(EdmSimpleType.DATE_TIME.pattern()).format(product.getReleaseDate()));
        assertTrue(3 == product.getRating());
        assertEquals(BigDecimal.valueOf(3.5), product.getPrice());

        final Supplier supplier = demoService.getSuppliers().get(0);
        assertNotNull(supplier);
        final Address address = supplier.getAddress();
        assertNotNull(address);
        assertEquals("98074", address.getZipCode());
        final Point location = supplier.getLocation();
        assertNotNull(location);
        assertEquals(47.6316604614258, location.getX(), 0);
    }

    @Test
    public void getAll() {
        final DemoService demoService = entityContainerFactory.getEntityContainer(DemoService.class);
        assertNotNull(demoService);

        final Iterable<Product> products = demoService.getProducts().getAll();
        assertNotNull(products);

        final Iterator<Product> itor = products.iterator();
        int count = 0;
        while (itor.hasNext()) {
            assertNotNull(itor.next());
            count++;
        }
        assertTrue(11 == count);
    }
}
