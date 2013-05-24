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

import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import com.msopentech.odatajclient.proxy.api.query.AsyncEntityQuery;
import com.msopentech.odatajclient.proxy.api.query.AsyncQuery;
import com.msopentech.odatajclient.proxy.odatademo.AsyncDemoService;
import com.msopentech.odatajclient.proxy.odatademo.Product;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncProxyUsageTest {

    private EntityContainerFactory entityContainerFactory;

    public void entityQuery() {
        AsyncDemoService demoService = entityContainerFactory.getEntityContainer(AsyncDemoService.class);

        // Typed query for products with price < 10.00, to be execute asynchronously
        AsyncEntityQuery<Product> productQuery = demoService.getProducts().createQuery(Product.class);
        productQuery.setFilter("Price lt 10.00");

        Future<List<Product>> matchingProductsFuture = productQuery.asyncGetResultList();

        // ... do something with matchingProductsFuture
    }

    public void untypedQuery() {
        AsyncDemoService demoService = entityContainerFactory.getEntityContainer(AsyncDemoService.class);

        // Untyped query for names of products with price < 10.00, to be execute asynchronously
        AsyncQuery query = demoService.getProducts().createQuery();
        query.setFilter("Price lt 10.00");
        query.setSelect("Name");

        Future<List<? extends Serializable>> matchFuture = query.asyncGetResultList();

        // ... do something with matchFuture
    }

    public void get() {
        AsyncDemoService demoService = entityContainerFactory.getEntityContainer(AsyncDemoService.class);

        // Take the product with key value 3 asynchronously
        Future<Product> product3 = demoService.getProducts().get(3);
    }

    public void invoke() throws InterruptedException, ExecutionException {
        AsyncDemoService demoService = entityContainerFactory.getEntityContainer(AsyncDemoService.class);

        // invoke GetProductsByRating asynchronously
        Future<Collection<Product>> productsFuture = demoService.getProductsByRating(15);
        while (!productsFuture.isDone()) {
            Thread.sleep(1000);
        }
        Collection<Product> products = productsFuture.get();
    }
}
