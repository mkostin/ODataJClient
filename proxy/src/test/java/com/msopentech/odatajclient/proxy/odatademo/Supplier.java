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
package com.msopentech.odatajclient.proxy.odatademo;

import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.annotations.Key;
import com.msopentech.odatajclient.proxy.api.annotations.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import com.msopentech.odatajclient.engine.data.metadata.EdmContentKind;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Point;
import com.msopentech.odatajclient.proxy.api.impl.AbstractType;

@EntityType("Supplier")
public class Supplier extends AbstractType {

    private static final long serialVersionUID = 374015664635616215L;

    @Key
    @Property(name = "ID", type = "Edm.Int32", nullable = false)
    private Integer id;

    @Property(name = "Name", type = "Edm.String",
            fcTargetPath = "SyndicationTitle", fcContentKind = EdmContentKind.text, fcKeepInContent = true)
    private String name;

    @Property(name = "Address", type = "ODataDemo.Address")
    private Address address;

    @Property(name = "Location", type = "Edm.GeographyPoint", srid = "Variable")
    private Point location;

    @Property(name = "Concurrency", type = "Edm.Int32", nullable = false)
    private Integer concurrency;

    @NavigationProperty(name = "Products", relationship = "ODataDemo.Product_Supplier_Supplier_Products",
            fromRole = "Supplier_Products", toRole = "Product_Supplier")
    private Products Products;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Integer getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(Integer concurrency) {
        this.concurrency = concurrency;
    }

    public Products getProducts() {
        return Products;
    }

    public void setProducts(Products Products) {
        this.Products = Products;
    }
}
