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

import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.annotations.Key;
import com.msopentech.odatajclient.proxy.api.annotations.KeyClass;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import java.io.Serializable;

@EntityType("Products_by_Category")
@KeyClass(ProductsByCategoryKey.class)
public class ProductsByCategory implements Serializable {

    private static final long serialVersionUID = -7387489771343233765L;

    @Key
    @Property(name = "CategoryName", type = "Edm.String", nullable = false)
    private String categoryName;

    @Key
    @Property(name = "ProductName", type = "Edm.String", nullable = false)
    private String productName;

    @Property(name = "QuantityPerUnit", type = "Edm.String", nullable = true)
    private String quantityPerUnit;

    @Property(name = "UnitsInStock", type = "Edm.Int16", nullable = true)
    private Short unitsInStock;

    @Key
    @Property(name = "Discontinued", type = "Edm.Boolean", nullable = false)
    private Boolean discontinued;

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

    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public Short getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Short unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
    }
}
