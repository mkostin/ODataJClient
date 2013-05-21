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
import java.util.Date;

@EntityType("Sales_Totals_by_Amount")
@KeyClass(SalesTotalsByAmountKey.class)
public class SalesTotalsByAmount implements Serializable {

    private static final long serialVersionUID = -4817787365594256477L;

    @Property(name = "SaleAmount", type = "Edm.Decimal", nullable = true)
    private Float saleAmount;

    @Key
    @Property(name = "OrderID", type = "Edm.Int32", nullable = false)
    private Integer orderID;

    @Key
    @Property(name = "CompanyName", type = "Edm.String", nullable = false)
    private String companyName;

    @Property(name = "ShippedDate", type = "Edm.DateTime", nullable = true)
    private Date shippedDate;

    public Float getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(Float saleAmount) {
        this.saleAmount = saleAmount;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }
}
