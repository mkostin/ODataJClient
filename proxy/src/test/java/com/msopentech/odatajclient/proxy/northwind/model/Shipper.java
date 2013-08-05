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
import java.util.List;

@EntityType("Shipper")
public class Shipper implements Serializable {

    private static final long serialVersionUID = 1547009210288579766L;

    @Key
    @Property(name = "ShipperID", type = "Edm.Int32", nullable = false)
    private Integer shipperID;

    @Property(name = "CompanyName", type = "Edm.String", nullable = false)
    private String companyName;

    @Property(name = "Phone", type = "Edm.String", nullable = true)
    private String phone;

    @NavigationProperty(name = "Orders", relationship = "NorthwindModel.FK_Orders_Shippers",
            fromRole = "Shippers", toRole = "Orders")
    private List<Order> orders;

    public Integer getShipperID() {
        return shipperID;
    }

    public void setShipperID(Integer shipperID) {
        this.shipperID = shipperID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
