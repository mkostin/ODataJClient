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
import com.msopentech.odatajclient.proxy.meta.NavigationProperty;
import com.msopentech.odatajclient.proxy.meta.Property;
import java.io.Serializable;

@EntityType("Order_Detail")
@KeyClass(OrderDetailKey.class)
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = -1668782289469309698L;

    @Key
    @Property(name = "OrderID", type = "Edm.Int32", nullable = false)
    private Integer orderID;

    @Key
    @Property(name = "ProductID", type = "Edm.Int32", nullable = false)
    private Integer productID;

    @Property(name = "UnitPrice", type = "Edm.Decimal", nullable = false)
    private Float unitPrice;

    @Property(name = "Quantity", type = "Edm.Int16", nullable = false)
    private Short quantity;

    @Property(name = "Discount", type = "Edm.Single", nullable = false)
    private Float discount;

    @NavigationProperty(name = "Order", relationship = "NorthwindModel.FK_Order_Details_Orders",
            fromRole = "Order_Details", toRole = "Orders")
    private Order order;

    @NavigationProperty(name = "Product", relationship = "NorthwindModel.FK_Order_Details_Products",
            fromRole = "Order_Details", toRole = "Products")
    private Product product;

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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
