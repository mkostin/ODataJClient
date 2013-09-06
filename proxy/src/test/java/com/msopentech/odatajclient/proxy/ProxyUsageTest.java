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
import com.msopentech.odatajclient.proxy.api.query.Query;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.DefaultContainer;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Customer;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.CustomerCollection;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Order;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.OrderCollection;

public class ProxyUsageTest {

    private EntityContainerFactory entityContainerFactory;

    public void entityQuery() {
        DefaultContainer container = entityContainerFactory.getEntityContainer(DefaultContainer.class);
        // Query for orders with id < 10.00, to be execute
        Query<Order, OrderCollection> orderQuery = container.getOrder().createQuery();
        orderQuery.setFilter("OrderId lt 10.00");
        
        OrderCollection matchingOrders = orderQuery.getResult();

        // ... do something with matchingOrders
    }

    public void get() {
        DefaultContainer container = entityContainerFactory.getEntityContainer(DefaultContainer.class);

        // Take the order with key value -9
        Order order = container.getOrder().get(-9);
    }

    public void create() {
        DefaultContainer container = entityContainerFactory.getEntityContainer(DefaultContainer.class);

        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.Order orders =
                container.getOrder();

        // create Order (local)
        Order order = orders.newOrder();
        order.setCustomerId(-10);

        final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.Customer customers =
                container.getCustomer();
        // create Customer (local)
        Customer customer = customers.newCustomer();
        customer.setName("a name");

        // link customer and order
        OrderCollection orderCollection = orders.newOrderCollection();
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
