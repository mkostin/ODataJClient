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
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.Timestamp;
import java.util.UUID;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;

@EntityType(value="Login",
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
public interface Login extends Serializable {

    
    @Key
    @Property(name = "Username", 
                type = "Edm.String", 
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
    public String getUsername();

    public void setUsername(final String _username);

    
    @Property(name = "CustomerId", 
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
    public Integer getCustomerId();

    public void setCustomerId(final Integer _customerId);

    

    @NavigationProperty(name = "Customer", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Login_Customer", 
                fromRole = "Login", 
                toRole = "Customer",
                containsTarget = false,
                onDelete = Action.NONE)
    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer getCustomer();

    public void setCustomer(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer _customer);


    @NavigationProperty(name = "LastLogin", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Login_LastLogin", 
                fromRole = "Login", 
                toRole = "LastLogin",
                containsTarget = false,
                onDelete = Action.NONE)
    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.LastLogin getLastLogin();

    public void setLastLogin(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.LastLogin _lastLogin);


    @NavigationProperty(name = "SentMessages", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Login_SentMessages", 
                fromRole = "Login", 
                toRole = "SentMessages",
                containsTarget = false,
                onDelete = Action.NONE)
    public Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Message> getSentMessages();

    public void setSentMessages(final Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Message> _sentMessages);


    @NavigationProperty(name = "ReceivedMessages", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Login_ReceivedMessages", 
                fromRole = "Login", 
                toRole = "ReceivedMessages",
                containsTarget = false,
                onDelete = Action.NONE)
    public Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Message> getReceivedMessages();

    public void setReceivedMessages(final Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Message> _receivedMessages);


    @NavigationProperty(name = "Orders", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Login_Orders", 
                fromRole = "Login", 
                toRole = "Orders",
                containsTarget = false,
                onDelete = Action.NONE)
    public Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Order> getOrders();

    public void setOrders(final Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Order> _orders);

}
