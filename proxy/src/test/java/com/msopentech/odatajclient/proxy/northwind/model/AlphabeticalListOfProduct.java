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

import com.msopentech.odatajclient.proxy.meta.EntityType;
import com.msopentech.odatajclient.proxy.meta.Key;
import com.msopentech.odatajclient.proxy.meta.KeyClass;
import com.msopentech.odatajclient.proxy.meta.Property;
import java.io.Serializable;

@EntityType("Alphabetical_list_of_product")
@KeyClass(AlphabeticalListOfProductKey.class)
public class AlphabeticalListOfProduct implements Serializable {

    private static final long serialVersionUID = 3938909825204778338L;

    @Key
    @Property(name = "ProductID", type = "Edm.Int32", nullable = false)
    private Integer productID;

    @Key
    @Property(name = "ProductName", type = "Edm.String", nullable = false)
    private String productName;

    @Property(name = "SupplierID", type = "Edm.Int32", nullable = true)
    private Integer supplierID;

    @Property(name = "CategoryID", type = "Edm.Int32", nullable = true)
    private Integer categoryID;

    @Property(name = "QuantityPerUnit", type = "Edm.String", nullable = true)
    private String quantityPerUnit;

    @Property(name = "UnitPrice", type = "Edm.Decimal", nullable = true)
    private Float unitPrice;

    @Property(name = "UnitsInStock", type = "Edm.Int16", nullable = true)
    private Short unitsInStock;

    @Property(name = "UnitsOnOrder", type = "Edm.Int16", nullable = true)
    private Short unitsOnOrder;

    @Property(name = "ReorderLevel", type = "Edm.Int16", nullable = true)
    private Short reorderLevel;

    @Key
    @Property(name = "Discontinued", type = "Edm.Boolean", nullable = false)
    private Boolean discontinued;

    @Key
    @Property(name = "CategoryName", type = "Edm.String", nullable = false)
    private String categoryName;

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Short getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Short unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public Short getUnitsOnOrder() {
        return unitsOnOrder;
    }

    public void setUnitsOnOrder(Short unitsOnOrder) {
        this.unitsOnOrder = unitsOnOrder;
    }

    public Short getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Short reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
