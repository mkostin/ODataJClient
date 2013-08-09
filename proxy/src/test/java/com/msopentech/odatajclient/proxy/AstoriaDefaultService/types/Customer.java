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

@EntityType(value="Customer",
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
public class Customer extends AbstractType {

    
    
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
    private URI _thumbnail;
    
    public URI getThumbnail() {
        return _thumbnail;
    }

    public void setThumbnail(final URI _thumbnail) {
        this._thumbnail = _thumbnail;
    }

    
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
    private URI _video;
    
    public URI getVideo() {
        return _video;
    }

    public void setVideo(final URI _video) {
        this._video = _video;
    }

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
    private Integer _customerId;
    
    public Integer getCustomerId() {
        return _customerId;
    }

    public void setCustomerId(final Integer _customerId) {
        this._customerId = _customerId;
    }

    
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
    private String _name;
    
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }

    
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
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ContactDetails _primaryContactInfo;
    
    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ContactDetails getPrimaryContactInfo() {
        return _primaryContactInfo;
    }

    public void setPrimaryContactInfo(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ContactDetails _primaryContactInfo) {
        this._primaryContactInfo = _primaryContactInfo;
    }

    
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
    private Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ContactDetails> _backupContactInfo;
    
    public Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ContactDetails> getBackupContactInfo() {
        return _backupContactInfo;
    }

    public void setBackupContactInfo(final Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ContactDetails> _backupContactInfo) {
        this._backupContactInfo = _backupContactInfo;
    }

    
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
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.AuditInfo _auditing;
    
    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.AuditInfo getAuditing() {
        return _auditing;
    }

    public void setAuditing(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.AuditInfo _auditing) {
        this._auditing = _auditing;
    }

    

    @NavigationProperty(name = "Orders", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Customer_Orders", 
                fromRole = "Customer", 
                toRole = "Orders",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Order _orders;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Order getOrders() {
        return _orders;
    }

    public void setOrders(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Order _orders) {
        this._orders = _orders;
    }


    @NavigationProperty(name = "Logins", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Customer_Logins", 
                fromRole = "Customer", 
                toRole = "Logins",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Login _logins;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Login getLogins() {
        return _logins;
    }

    public void setLogins(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Login _logins) {
        this._logins = _logins;
    }


    @NavigationProperty(name = "Husband", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Customer_Husband", 
                fromRole = "Customer", 
                toRole = "Husband",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer _husband;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer getHusband() {
        return _husband;
    }

    public void setHusband(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer _husband) {
        this._husband = _husband;
    }


    @NavigationProperty(name = "Wife", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Customer_Wife", 
                fromRole = "Customer", 
                toRole = "Wife",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer _wife;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer getWife() {
        return _wife;
    }

    public void setWife(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer _wife) {
        this._wife = _wife;
    }


    @NavigationProperty(name = "Info", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Customer_Info", 
                fromRole = "Customer", 
                toRole = "Info",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.CustomerInfo _info;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.CustomerInfo getInfo() {
        return _info;
    }

    public void setInfo(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.CustomerInfo _info) {
        this._info = _info;
    }

}
