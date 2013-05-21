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

@EntityType("Order_Details_Extended")
@KeyClass(OrderDetailsExtendedKey.class)
public class OrderDetailsExtended implements Serializable {

    private static final long serialVersionUID = -5671018270444135740L;

    @Key
    @Property(name = "OrderID", type = "Edm.Int32", nullable = false)
    private Integer orderID;

    @Key
    @Property(name = "ProductID", type = "Edm.Int32", nullable = false)
    private Integer productID;

    @Key
    @Property(name = "ProductName", type = "Edm.String", nullable = false)
    private String productName;

    @Key
    @Property(name = "UnitPrice", type = "Edm.Decimal", nullable = false)
    private Float unitPrice;

    @Key
    @Property(name = "Quantity", type = "Edm.Int16", nullable = false)
    private Short quantity;

    @Key
    @Property(name = "Discount", type = "Edm.Single", nullable = false)
    private Float discount;

    @Property(name = "ExtendedPrice", type = "Edm.Decimal", nullable = true)
    private Float extendedPrice;

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

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

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getExtendedPrice() {
        return extendedPrice;
    }

    public void setExtendedPrice(Float extendedPrice) {
        this.extendedPrice = extendedPrice;
    }
}
