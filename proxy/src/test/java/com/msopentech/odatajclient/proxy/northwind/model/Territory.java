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
import com.msopentech.odatajclient.proxy.api.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.Property;
import java.io.Serializable;
import java.util.List;

@EntityType("Territory")
public class Territory implements Serializable {

    private static final long serialVersionUID = 2044319527610920648L;

    @Key
    @Property(name = "TerritoryID", type = "Edm.String", nullable = false)
    private String territoryID;

    @Property(name = "TerritoryDescription", type = "Edm.String", nullable = false)
    private String territoryDescription;

    @Property(name = "RegionID", type = "Edm.Int32", nullable = false)
    private Integer regionID;

    @NavigationProperty(name = "Region", relationship = "NorthwindModel.FK_Territories_Region",
            fromRole = "Territories", toRole = "Region")
    private Region region;

    @NavigationProperty(name = "Employees", relationship = "NorthwindModel.EmployeeTerritories",
            fromRole = "Territories", toRole = "Employees")
    private List<Employee> employees;

    public String getTerritoryID() {
        return territoryID;
    }

    public void setTerritoryID(String territoryID) {
        this.territoryID = territoryID;
    }

    public String getTerritoryDescription() {
        return territoryDescription;
    }

    public void setTerritoryDescription(String territoryDescription) {
        this.territoryDescription = territoryDescription;
    }

    public Integer getRegionID() {
        return regionID;
    }

    public void setRegionID(Integer regionID) {
        this.regionID = regionID;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
