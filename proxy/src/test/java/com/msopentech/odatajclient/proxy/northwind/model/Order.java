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
import com.msopentech.odatajclient.proxy.api.annotations.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EntityType("Order")
public class Order implements Serializable {

    private static final long serialVersionUID = 4277537757213052062L;

    @Key
    @Property(name = "OrderID", type = "Edm.Int32", nullable = false)
    private Integer orderID;

    @Property(name = "CustomerID", type = "Edm.String", nullable = true)
    private String customerID;

    @Property(name = "EmployeeID", type = "Edm.Int32", nullable = true)
    private Integer employeeID;

    @Property(name = "OrderDate", type = "Edm.DateTime", nullable = true)
    private Date orderDate;

    @Property(name = "RequiredDate", type = "Edm.DateTime", nullable = true)
    private Date requiredDate;

    @Property(name = "ShippedDate", type = "Edm.DateTime", nullable = true)
    private Date shippedDate;

    @Property(name = "ShipVia", type = "Edm.Int32", nullable = true)
    private Integer shipVia;

    @Property(name = "Freight", type = "Edm.Decimal", nullable = true)
    private Float freight;

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

    @NavigationProperty(name = "Customer", relationship = "NorthwindModel.FK_Orders_Customers",
            fromRole = "Orders", toRole = "Customers")
    private Customer customer;

    @NavigationProperty(name = "Employee", relationship = "NorthwindModel.FK_Orders_Employees",
            fromRole = "Orders", toRole = "Employees")
    private Employee employee;

    @NavigationProperty(name = "Order_Details", relationship = "NorthwindModel.FK_Order_Details_Orders",
            fromRole = "Orders", toRole = "Order_Details")
    private List<OrderDetail> orderDetails;

    @NavigationProperty(name = "Shipper", relationship = "NorthwindModel.FK_Orders_Shippers",
            fromRole = "Orders", toRole = "Shippers")
    private Shipper Shipper;

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
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

    public Integer getShipVia() {
        return shipVia;
    }

    public void setShipVia(Integer shipVia) {
        this.shipVia = shipVia;
    }

    public Float getFreight() {
        return freight;
    }

    public void setFreight(Float freight) {
        this.freight = freight;
    }

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Shipper getShipper() {
        return Shipper;
    }

    public void setShipper(Shipper Shipper) {
        this.Shipper = Shipper;
    }
}
