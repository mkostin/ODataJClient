package com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types;

import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.annotations.Key;
import com.msopentech.odatajclient.proxy.api.annotations.KeyRef;
import com.msopentech.odatajclient.proxy.api.annotations.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import com.msopentech.odatajclient.proxy.api.impl.AbstractType;
import com.msopentech.odatajclient.engine.data.metadata.EdmContentKind;
import com.msopentech.odatajclient.engine.data.metadata.edm.ConcurrencyMode;
import com.msopentech.odatajclient.engine.data.metadata.edm.Action;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.*;

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


@EntityType(name = "Customer",
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
public interface Customer extends Serializable {

    
    
    @Property(name = "Thumbnail", 
                type = "Edm.Stream", 
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
    public URI getThumbnail();

    public void setThumbnail(final URI _thumbnail);

    
    @Property(name = "Video", 
                type = "Edm.Stream", 
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
    public URI getVideo();

    public void setVideo(final URI _video);

    @Key
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

    
    @Property(name = "Name", 
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
                fcTargetPath = "SyndicationSummary",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)    
    public String getName();

    public void setName(final String _name);

    
    @Property(name = "PrimaryContactInfo", 
                type = "Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails", 
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
    public com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.ContactDetails getPrimaryContactInfo();

    public void setPrimaryContactInfo(final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.ContactDetails _primaryContactInfo);

    
    @Property(name = "BackupContactInfo", 
                type = "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails)", 
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
    public Collection<com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.ContactDetails> getBackupContactInfo();

    public void setBackupContactInfo(final Collection<com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.ContactDetails> _backupContactInfo);

    
    @Property(name = "Auditing", 
                type = "Microsoft.Test.OData.Services.AstoriaDefaultService.AuditInfo", 
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
    public com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.AuditInfo getAuditing();

    public void setAuditing(final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.AuditInfo _auditing);

    

    @NavigationProperty(name = "Orders", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Customer_Orders", 
                fromRole = "Customer", 
                toRole = "Orders",
                containsTarget = false,
                onDelete = Action.NONE)
    public Collection<com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Order> getOrders();

    public void setOrders(final Collection<com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Order> _orders);


    @NavigationProperty(name = "Logins", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Customer_Logins", 
                fromRole = "Customer", 
                toRole = "Logins",
                containsTarget = false,
                onDelete = Action.NONE)
    public Collection<com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Login> getLogins();

    public void setLogins(final Collection<com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Login> _logins);


    @NavigationProperty(name = "Husband", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Customer_Husband", 
                fromRole = "Customer", 
                toRole = "Husband",
                containsTarget = false,
                onDelete = Action.NONE)
    public com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Customer getHusband();

    public void setHusband(final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Customer _husband);


    @NavigationProperty(name = "Wife", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Customer_Wife", 
                fromRole = "Customer", 
                toRole = "Wife",
                containsTarget = false,
                onDelete = Action.NONE)
    public com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Customer getWife();

    public void setWife(final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Customer _wife);


    @NavigationProperty(name = "Info", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Customer_Info", 
                fromRole = "Customer", 
                toRole = "Info",
                containsTarget = false,
                onDelete = Action.NONE)
    public com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.CustomerInfo getInfo();

    public void setInfo(final com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.CustomerInfo _info);

}
