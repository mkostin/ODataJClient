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
package com.msopentech.odatajclient.proxy.northwind.model;

import com.msopentech.odatajclient.proxy.meta.EntityType;
import com.msopentech.odatajclient.proxy.meta.Key;
import com.msopentech.odatajclient.proxy.meta.KeyClass;
import com.msopentech.odatajclient.proxy.meta.Property;
import java.io.Serializable;

@EntityType("Sales_by_Category")
@KeyClass(SalesByCategoryKey.class)
public class SalesByCategory implements Serializable {

    private static final long serialVersionUID = 8129365257909645415L;

    @Key
    @Property(name = "CategoryID", type = "Edm.Int32", nullable = false)
    private Integer categoryID;

    @Key
    @Property(name = "CategoryName", type = "Edm.String", nullable = false)
    private String categoryName;

    @Key
    @Property(name = "ProductName", type = "Edm.String", nullable = false)
    private String productName;

    @Property(name = "ProductSales", type = "Edm.Decimal", nullable = true)
    private Float productSales;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getProductSales() {
        return productSales;
    }

    public void setProductSales(Float productSales) {
        this.productSales = productSales;
    }
}
