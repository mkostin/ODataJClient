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

import com.msopentech.odatajclient.proxy.api.AbstractEntityCollection;
import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import com.msopentech.odatajclient.proxy.api.query.AsyncEntityQuery;
import com.msopentech.odatajclient.proxy.api.query.AsyncQuery;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.AsyncDefaultContainer;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.CustomerCollection;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Order;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncProxyUsageTest {

    private EntityContainerFactory entityContainerFactory;

    public void entityQuery() {
        AsyncDefaultContainer container = entityContainerFactory.getEntityContainer(AsyncDefaultContainer.class);

        // Typed query for orders with id < 10.00, to be execute asynchronously
        AsyncEntityQuery<Order> orderQuery = container.getOrder().createQuery(Order.class);
        orderQuery.setFilter("OrderId lt 10.00");

        Future<AbstractEntityCollection<Order>> matchingOrdersFuture = orderQuery.asyncGetResultList();

        // ... do something with matchingOrdersFuture
    }

    public void untypedQuery() {
        AsyncDefaultContainer container = entityContainerFactory.getEntityContainer(AsyncDefaultContainer.class);

        // Untyped query for names of order with id < 10.00, to be execute asynchronously
        AsyncQuery query = container.getOrder().createQuery();
        query.setFilter("OrderId lt 10.00");
        query.setSelect("CustomerId");

        Future<AbstractEntityCollection<Serializable>> matchFuture = query.asyncGetResultList();

        // ... do something with matchFuture
    }

    public void get() {
        AsyncDefaultContainer container = entityContainerFactory.getEntityContainer(AsyncDefaultContainer.class);

        // Take the order with key value -9 asynchronously
        Future<Order> order = container.getOrder().get(-9);
    }

    public void invoke() throws InterruptedException, ExecutionException {
        AsyncDefaultContainer container = entityContainerFactory.getEntityContainer(AsyncDefaultContainer.class);

        // invoke GetProductsByRating asynchronously
        Future<CustomerCollection> customersFuture = container.getSpecificCustomer("xxx");
        while (!customersFuture.isDone()) {
            Thread.sleep(1000);
        }
        CustomerCollection customers = customersFuture.get();
    }
}
