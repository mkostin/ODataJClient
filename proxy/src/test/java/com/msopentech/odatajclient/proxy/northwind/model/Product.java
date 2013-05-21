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
import com.msopentech.odatajclient.proxy.meta.NavigationProperty;
import com.msopentech.odatajclient.proxy.meta.Property;

import java.io.Serializable;
import java.util.List;

@EntityType("Product")
public class Product implements Serializable {

    private static final long serialVersionUID = -3330817707680703127L;

    @Key
    @Property(name = "ProductID", type = "Edm.Int32", nullable = false)
    private Integer productID;

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

    @Property(name = "Discontinued", type = "Edm.Boolean", nullable = false)
    private Boolean Discontinued;

    @NavigationProperty(name = "Category", relationship = "NorthwindModel.FK_Products_Categories",
            fromRole = "Products", toRole = "Categories")
    private Category Category;

    @NavigationProperty(name = "Order_Details", relationship = "NorthwindModel.FK_Order_Details_Products",
            fromRole = "Products", toRole = "Order_Details")
    private List<OrderDetail> orderDetails;

    @NavigationProperty(name = "Supplier", relationship = "NorthwindModel.FK_Products_Suppliers",
            fromRole = "Products", toRole = "Suppliers")
    private Supplier supplier;

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
        return Discontinued;
    }

    public void setDiscontinued(Boolean Discontinued) {
        this.Discontinued = Discontinued;
    }

    public Category getCategory() {
        return Category;
    }

    public void setCategory(Category Category) {
        this.Category = Category;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
