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
import com.msopentech.odatajclient.proxy.api.query.EntityQuery;
import com.msopentech.odatajclient.proxy.api.query.Query;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.DefaultContainer;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.Customer;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.CustomerCollection;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.Order;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.OrderCollection;
import java.io.Serializable;
import java.util.List;

public class ProxyUsageTest {

    private EntityContainerFactory entityContainerFactory;

    public void entityQuery() {
        DefaultContainer container = entityContainerFactory.getEntityContainer(DefaultContainer.class);

        // Typed query for orders with id < 10.00, to be execute
        EntityQuery<Order> orderQuery = container.getOrder().createQuery(Order.class);
        orderQuery.setFilter("OrderId lt 10.00");

        List<Order> matchingOrdersFuture = orderQuery.getResultList();

        // ... do something with matchingOrdersFuture


        // Typed query for CustomerId of orders with OrderId < 10.00
        orderQuery = container.getOrder().createQuery(Order.class);
        orderQuery.setFilter("OrderId lt 10.00");
        orderQuery.setSelect("CustomerId");

        List<Order> matchingCategories = orderQuery.getResultList();

        // ... do something with matchingOrders
    }

    public void untypedQuery() {
        DefaultContainer container = entityContainerFactory.getEntityContainer(DefaultContainer.class);

        // Untyped query for names of order with id < 10.00, to be execute
        Query query = container.getOrder().createQuery();
        query.setFilter("OrderId lt 10.00");
        query.setSelect("CustomerId");

        List<? extends Serializable> matchFuture = query.getResultList();

        // ... do something with results
    }

    public void get() {
        DefaultContainer container = entityContainerFactory.getEntityContainer(DefaultContainer.class);

        // Take the order with key value -9
        Order order = container.getOrder().get(-9);
    }

    public void create() {
        DefaultContainer container = entityContainerFactory.getEntityContainer(DefaultContainer.class);

        // create Order (local)
        Order order = container.getOrder().newOrder();
        order.setCustomerId(-10);

        // create Customer (local)
        Customer customer = container.getCustomer().newCustomer();
        customer.setName("a name");

        // link customer and order
        OrderCollection orderCollection = container.getOrder().newOrderCollection();
        orderCollection.add(order);
        customer.setOrders(orderCollection);
        order.setCustomer(customer);

        // any flush() will generate actual operations on the OData service
        container.flush();
    }

    public void invoke() {
        DefaultContainer container = entityContainerFactory.getEntityContainer(DefaultContainer.class);

        // invoke GetProductsByRating
        CustomerCollection customers = container.getSpecificCustomer("xxx");
    }
}
