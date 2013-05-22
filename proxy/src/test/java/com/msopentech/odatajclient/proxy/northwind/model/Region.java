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

@EntityType("Region")
public class Region implements Serializable {

    private static final long serialVersionUID = -5805818982808569329L;

    @Key
    @Property(name = "RegionID", type = "Edm.Int32", nullable = false)
    private Integer regionID;

    @Property(name = "RegionDescription", type = "Edm.String", nullable = false)
    private String regionDescription;

    @NavigationProperty(name = "Territories", relationship = "NorthwindModel.FK_Territories_Region",
            fromRole = "Region", toRole = "Territories")
    private List<Territory> territories;

    public Integer getRegionID() {
        return regionID;
    }

    public void setRegionID(Integer regionID) {
        this.regionID = regionID;
    }

    public String getRegionDescription() {
        return regionDescription;
    }

    public void setRegionDescription(String regionDescription) {
        this.regionDescription = regionDescription;
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(List<Territory> territories) {
        this.territories = territories;
    }
}
