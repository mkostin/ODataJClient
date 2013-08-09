package com.msopentech.odatajclient.proxy.AstoriaDefaultService.types;

import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.annotations.Key;
import com.msopentech.odatajclient.proxy.api.annotations.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import com.msopentech.odatajclient.proxy.api.impl.AbstractType;
import com.msopentech.odatajclient.engine.data.metadata.EdmContentKind;
import com.msopentech.odatajclient.engine.data.metadata.edm.ConcurrencyMode;
import com.msopentech.odatajclient.engine.data.metadata.edm.Action;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.*;

// EdmSimpleType property imports
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
import java.sql.Timestamp;
import java.util.UUID;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;

@EntityType(value="Order",
        openType = false,
        hasStream = false,
        isAbstract = false,
        baseType = "",
        fcSourcePath = "",
        fcTargetPath = "",
        fcContentKind = EdmContentKind.text,
        fcNSPrefix = "",
        fcNSURI = "",
        fcKeepInContent = false)
public class Order extends AbstractType {

    
    @Key
    @Property(name = "OrderId", 
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
    private Integer _orderId;
    
    public Integer getOrderId() {
        return _orderId;
    }

    public void setOrderId(final Integer _orderId) {
        this._orderId = _orderId;
    }

    
    @Property(name = "CustomerId", 
                type = "Edm.Int32", 
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
    private Integer _customerId;
    
    public Integer getCustomerId() {
        return _customerId;
    }

    public void setCustomerId(final Integer _customerId) {
        this._customerId = _customerId;
    }

    
    @Property(name = "Concurrency", 
                type = "Microsoft.Test.OData.Services.AstoriaDefaultService.ConcurrencyInfo", 
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
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ConcurrencyInfo _concurrency;
    
    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ConcurrencyInfo getConcurrency() {
        return _concurrency;
    }

    public void setConcurrency(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ConcurrencyInfo _concurrency) {
        this._concurrency = _concurrency;
    }

    

    @NavigationProperty(name = "Login", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Order_Login", 
                fromRole = "Order", 
                toRole = "Login",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Login _login;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Login getLogin() {
        return _login;
    }

    public void setLogin(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Login _login) {
        this._login = _login;
    }


    @NavigationProperty(name = "Customer", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Order_Customer", 
                fromRole = "Order", 
                toRole = "Customer",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer _customer;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer getCustomer() {
        return _customer;
    }

    public void setCustomer(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer _customer) {
        this._customer = _customer;
    }

}
