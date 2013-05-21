/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.msopentech.odatajclient.proxy;

import com.msopentech.odatajclient.proxy.meta.query.EntityQuery;
import com.msopentech.odatajclient.proxy.meta.query.Query;
import com.msopentech.odatajclient.proxy.odatademo.Category;
import com.msopentech.odatajclient.proxy.odatademo.DemoService;
import com.msopentech.odatajclient.proxy.odatademo.Product;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ProxyUsageTest {

    public void entityQuery() {
        DemoService demoService = null; // an instance of DemoService is provided by the context

        // Typed query for products with price < 10.00
        EntityQuery<Product> productQuery = demoService.getProducts().createQuery(Product.class);
        productQuery.setFilter("Price lt 10.00");

        List<Product> matchingProducts = productQuery.getResultList();

        // ... do something with matchingProducts

        // Typed query for categories of products with price < 10.00
        EntityQuery<Category> categoryQuery = demoService.getProducts().createQuery(Category.class);
        productQuery.setFilter("Price lt 10.00");
        productQuery.setSelect("Category");

        List<Category> matchingCategories = categoryQuery.getResultList();

        // ... do something with matchingCategories
    }

    public void untypedQuery() {
        DemoService demoService = null; // an instance of DemoService is provided by the context

        // Untyped query for names of products with price < 10.00
        Query query = demoService.getProducts().createQuery();
        query.setFilter("Price lt 10.00");
        query.setSelect("Name");

        List<? extends Serializable> match = query.getResultList();

        // ... do something with match
    }
    
    public void get() {
        DemoService demoService = null; // an instance of DemoService is provided by the context

        // Take the product with key value 3
        Product product3 = demoService.getProducts().get(3);
    }
    
    public void create() {
        DemoService demoService = null; // an instance of DemoService is provided by the context

        // create Category (local)
        Category category = new Category();
        category.setName("a category name");        
        category = demoService.getCategories().save(category);
        
        // create Product (local)
        Product product = new Product();
        product.setName("a name");
        product.setDescription("a description");
        product.setPrice(11F);
        product.setReleaseDate(new Date());        
        product = demoService.getProducts().save(product);
        
        // link product and category
        product.setCategory(category);
        category.setProducts(Collections.singletonList(product));
        
        // any flush() will generate actual operations on the OData service
        demoService.getProducts().flush();
    }
    
    public void invoke() {
        DemoService demoService = null; // an instance of DemoService is provided by the context

        // invoke GetProductsByRating
        Collection<Product> products = demoService.getProductsByRating(15);
    }
}
