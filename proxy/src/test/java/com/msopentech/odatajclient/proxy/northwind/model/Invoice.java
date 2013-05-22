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
import java.util.Date;

@EntityType("Invoice")
@KeyClass(InvoiceKey.class)
public class Invoice implements Serializable {

    private static final long serialVersionUID = 6866197983423605284L;

    @Property(name = "ShipName", type = "Edm.String", nullable = true)
    private String shipName;

    @Property(name = "ShipAddress", type = "Edm.String", nullable = true)
    private String shipAddress;

    @Property(name = "ShipCity", type = "Edm.String", nullable = true)
    private String shipCity;

    @Property(name = "ShipRegion", type = "Edm.String", nullable = true)
    private String shipRegion;

    @Property(name = "ShipPostalCode", type = "Edm.String", nullable = true)
    private String shipPostalCode;

    @Property(name = "ShipCountry", type = "Edm.String", nullable = true)
    private String shipCountry;

    @Property(name = "CustomerID", type = "Edm.String", nullable = true)
    private String customerID;

    @Key
    @Property(name = "CustomerName", type = "Edm.String", nullable = false)
    private String customerName;

    @Property(name = "Address", type = "Edm.String", nullable = true)
    private String address;

    @Property(name = "City", type = "Edm.String", nullable = true)
    private String city;

    @Property(name = "Region", type = "Edm.String", nullable = true)
    private String region;

    @Property(name = "PostalCode", type = "Edm.String", nullable = true)
    private String postalCode;

    @Property(name = "Country", type = "Edm.String", nullable = true)
    private String country;

    @Key
    @Property(name = "Salesperson", type = "Edm.String", nullable = false)
    private String salesperson;

    @Key
    @Property(name = "OrderID", type = "Edm.Int32", nullable = false)
    private Integer orderID;

    @Property(name = "OrderDate", type = "Edm.DateTime", nullable = true)
    private Date orderDate;

    @Property(name = "RequiredDate", type = "Edm.DateTime", nullable = true)
    private Date requiredDate;

    @Property(name = "ShippedDate", type = "Edm.DateTime", nullable = true)
    private Date shippedDate;

    @Key
    @Property(name = "ShipperName", type = "Edm.String", nullable = false)
    private String shipperName;

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

    @Property(name = "Freight", type = "Edm.Decimal", nullable = true)
    private Float freight;

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipRegion() {
        return shipRegion;
    }

    public void setShipRegion(String shipRegion) {
        this.shipRegion = shipRegion;
    }

    public String getShipPostalCode() {
        return shipPostalCode;
    }

    public void setShipPostalCode(String shipPostalCode) {
        this.shipPostalCode = shipPostalCode;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(String salesperson) {
        this.salesperson = salesperson;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
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

    public Float getFreight() {
        return freight;
    }

    public void setFreight(Float freight) {
        this.freight = freight;
    }
}
