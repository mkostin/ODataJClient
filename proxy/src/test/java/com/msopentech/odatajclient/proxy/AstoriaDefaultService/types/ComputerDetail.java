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

@EntityType(value="ComputerDetail",
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
public class ComputerDetail extends AbstractType {

    
    @Key
    @Property(name = "ComputerDetailId", 
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
    private Integer _computerDetailId;
    
    public Integer getComputerDetailId() {
        return _computerDetailId;
    }

    public void setComputerDetailId(final Integer _computerDetailId) {
        this._computerDetailId = _computerDetailId;
    }

    
    @Property(name = "Manufacturer", 
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
                fcTargetPath = "SyndicationAuthorEmail",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = true)
    private String _manufacturer;
    
    public String getManufacturer() {
        return _manufacturer;
    }

    public void setManufacturer(final String _manufacturer) {
        this._manufacturer = _manufacturer;
    }

    
    @Property(name = "Model", 
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
                fcTargetPath = "SyndicationAuthorUri",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = true)
    private String _model;
    
    public String getModel() {
        return _model;
    }

    public void setModel(final String _model) {
        this._model = _model;
    }

    
    @Property(name = "Serial", 
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
    private String _serial;
    
    public String getSerial() {
        return _serial;
    }

    public void setSerial(final String _serial) {
        this._serial = _serial;
    }

    
    @Property(name = "SpecificationsBag", 
                type = "Collection(Edm.String)", 
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
    private Collection<String> _specificationsBag;
    
    public Collection<String> getSpecificationsBag() {
        return _specificationsBag;
    }

    public void setSpecificationsBag(final Collection<String> _specificationsBag) {
        this._specificationsBag = _specificationsBag;
    }

    
    @Property(name = "PurchaseDate", 
                type = "Edm.DateTime", 
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
    private Timestamp _purchaseDate;
    
    public Timestamp getPurchaseDate() {
        return _purchaseDate;
    }

    public void setPurchaseDate(final Timestamp _purchaseDate) {
        this._purchaseDate = _purchaseDate;
    }

    
    @Property(name = "Dimensions", 
                type = "Microsoft.Test.OData.Services.AstoriaDefaultService.Dimensions", 
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
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Dimensions _dimensions;
    
    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Dimensions getDimensions() {
        return _dimensions;
    }

    public void setDimensions(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Dimensions _dimensions) {
        this._dimensions = _dimensions;
    }

    

    @NavigationProperty(name = "Computer", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.ComputerDetail_Computer", 
                fromRole = "ComputerDetail", 
                toRole = "Computer",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Computer _computer;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Computer getComputer() {
        return _computer;
    }

    public void setComputer(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Computer _computer) {
        this._computer = _computer;
    }

}
