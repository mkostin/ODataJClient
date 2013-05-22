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
package com.msopentech.odatajclient.proxy.northwind.model;

import com.msopentech.odatajclient.proxy.api.EntityType;
import com.msopentech.odatajclient.proxy.api.Key;
import com.msopentech.odatajclient.proxy.api.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.Property;

import java.io.Serializable;
import java.util.List;

@EntityType("Category")
public class Category implements Serializable {

    private static final long serialVersionUID = -8008887746599186882L;

    @Key
    @Property(name = "CategoryID", type = "Edm.Int32", nullable = false)
    private Integer categoryID;

    @Property(name = "CategoryName", type = "Edm.String", nullable = false)
    private String categoryName;

    @Property(name = "Description", type = "Edm.String", nullable = true)
    private String description;

    @Property(name = "Picture", type = "Edm.Binary", nullable = true)
    private Byte[] picture;

    @NavigationProperty(name = "Products", relationship = "NorthwindModel.FK_Products_Categories",
            fromRole = "Categories", toRole = "Products")
    private List<Product> products;

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte[] getPicture() {
        return picture;
    }

    public void setPicture(Byte[] picture) {
        this.picture = picture;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
