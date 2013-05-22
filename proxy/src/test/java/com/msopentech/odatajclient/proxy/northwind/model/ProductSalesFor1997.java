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
import com.msopentech.odatajclient.proxy.api.KeyClass;
import com.msopentech.odatajclient.proxy.api.Property;
import java.io.Serializable;

@EntityType("Product_Sales_for_1997")
@KeyClass(ProductSalesFor1997Key.class)
public class ProductSalesFor1997 implements Serializable {

    private static final long serialVersionUID = -3399668268466940733L;

    @Key
    @Property(name = "CategoryName", type = "Edm.String", nullable = false)
    private String categoryName;

    @Key
    @Property(name = "ProductName", type = "Edm.String", nullable = false)
    private String productName;

    @Property(name = "ProductSales", type = "Edm.Decimal", nullable = true)
    private Float productSales;

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
