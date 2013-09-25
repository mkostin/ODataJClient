/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types;

import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.annotations.Key;
import com.msopentech.odatajclient.proxy.api.annotations.KeyRef;
import com.msopentech.odatajclient.proxy.api.annotations.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import com.msopentech.odatajclient.proxy.api.annotations.FunctionImport;
import com.msopentech.odatajclient.proxy.api.annotations.Parameter;
import com.msopentech.odatajclient.engine.data.metadata.edm.ParameterMode;
import com.msopentech.odatajclient.proxy.api.AbstractComplexType;
import com.msopentech.odatajclient.proxy.api.AbstractOpenType;
import com.msopentech.odatajclient.engine.data.metadata.EdmContentKind;
import com.msopentech.odatajclient.engine.data.metadata.edm.ConcurrencyMode;
import com.msopentech.odatajclient.engine.data.metadata.edm.Action;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.*;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.*;

// EdmSimpleType property imports
import com.msopentech.odatajclient.engine.data.ODataDuration;
import com.msopentech.odatajclient.engine.data.ODataTimestamp;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Geospatial;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.GeospatialCollection;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.LineString;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiLineString;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiPoint;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiPolygon;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Point;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Polygon;
import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Collection;


@EntityType(name = "Employee",
        openType = false,
        hasStream = false,
        isAbstract = false,
        baseType = "Microsoft.Test.OData.Services.AstoriaDefaultService.Person",
        fcSourcePath = "",
        fcTargetPath = "",
        fcContentKind = EdmContentKind.text,
        fcNSPrefix = "",
        fcNSURI = "",
        fcKeepInContent = false)
public interface Employee extends com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.Person {


    
    @Property(name = "ManagersPersonId", 
                type = "Edm.Int32", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    public Integer getManagersPersonId();

    public void setManagersPersonId(final Integer _managersPersonId);

    
    @Property(name = "Salary", 
                type = "Edm.Int32", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    public Integer getSalary();

    public void setSalary(final Integer _salary);

    
    @Property(name = "Title", 
                type = "Edm.String", 
                nullable = true,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    public String getTitle();

    public void setTitle(final String _title);

    

    @NavigationProperty(name = "Manager", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Employee_Manager", 
                fromRole = "Employee", 
                toRole = "Manager",
                containsTarget = false,
                onDelete = Action.NONE)
    public com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.Employee getManager();

    public void setManager(final com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.Employee _manager);


    @FunctionImport(name = "Sack"      )
    void sack(
        );

}
