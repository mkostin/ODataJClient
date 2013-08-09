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

@EntityType(value="MappedEntityType",
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
public class MappedEntityType extends AbstractType {

    
    @Key
    @Property(name = "Id", 
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
    private Integer _id;
    
    public Integer getId() {
        return _id;
    }

    public void setId(final Integer _id) {
        this._id = _id;
    }

    
    @Property(name = "Href", 
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
    private String _href;
    
    public String getHref() {
        return _href;
    }

    public void setHref(final String _href) {
        this._href = _href;
    }

    
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
    private String _title;
    
    public String getTitle() {
        return _title;
    }

    public void setTitle(final String _title) {
        this._title = _title;
    }

    
    @Property(name = "HrefLang", 
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
    private String _hrefLang;
    
    public String getHrefLang() {
        return _hrefLang;
    }

    public void setHrefLang(final String _hrefLang) {
        this._hrefLang = _hrefLang;
    }

    
    @Property(name = "Type", 
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
    private String _type;
    
    public String getType() {
        return _type;
    }

    public void setType(final String _type) {
        this._type = _type;
    }

    
    @Property(name = "Length", 
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
    private Integer _length;
    
    public Integer getLength() {
        return _length;
    }

    public void setLength(final Integer _length) {
        this._length = _length;
    }

    
    @Property(name = "BagOfPrimitiveToLinks", 
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
    private Collection<String> _bagOfPrimitiveToLinks;
    
    public Collection<String> getBagOfPrimitiveToLinks() {
        return _bagOfPrimitiveToLinks;
    }

    public void setBagOfPrimitiveToLinks(final Collection<String> _bagOfPrimitiveToLinks) {
        this._bagOfPrimitiveToLinks = _bagOfPrimitiveToLinks;
    }

    
    @Property(name = "Logo", 
                type = "Edm.Binary", 
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
    private byte[] _logo;
    
    public byte[] getLogo() {
        return _logo;
    }

    public void setLogo(final byte[] _logo) {
        this._logo = _logo;
    }

    
    @Property(name = "BagOfDecimals", 
                type = "Collection(Edm.Decimal)", 
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
    private Collection<BigDecimal> _bagOfDecimals;
    
    public Collection<BigDecimal> getBagOfDecimals() {
        return _bagOfDecimals;
    }

    public void setBagOfDecimals(final Collection<BigDecimal> _bagOfDecimals) {
        this._bagOfDecimals = _bagOfDecimals;
    }

    
    @Property(name = "BagOfDoubles", 
                type = "Collection(Edm.Double)", 
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
    private Collection<Double> _bagOfDoubles;
    
    public Collection<Double> getBagOfDoubles() {
        return _bagOfDoubles;
    }

    public void setBagOfDoubles(final Collection<Double> _bagOfDoubles) {
        this._bagOfDoubles = _bagOfDoubles;
    }

    
    @Property(name = "BagOfSingles", 
                type = "Collection(Edm.Single)", 
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
    private Collection<Float> _bagOfSingles;
    
    public Collection<Float> getBagOfSingles() {
        return _bagOfSingles;
    }

    public void setBagOfSingles(final Collection<Float> _bagOfSingles) {
        this._bagOfSingles = _bagOfSingles;
    }

    
    @Property(name = "BagOfBytes", 
                type = "Collection(Edm.Byte)", 
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
    private Collection<Integer> _bagOfBytes;
    
    public Collection<Integer> getBagOfBytes() {
        return _bagOfBytes;
    }

    public void setBagOfBytes(final Collection<Integer> _bagOfBytes) {
        this._bagOfBytes = _bagOfBytes;
    }

    
    @Property(name = "BagOfInt16s", 
                type = "Collection(Edm.Int16)", 
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
    private Collection<Short> _bagOfInt16s;
    
    public Collection<Short> getBagOfInt16s() {
        return _bagOfInt16s;
    }

    public void setBagOfInt16s(final Collection<Short> _bagOfInt16s) {
        this._bagOfInt16s = _bagOfInt16s;
    }

    
    @Property(name = "BagOfInt32s", 
                type = "Collection(Edm.Int32)", 
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
    private Collection<Integer> _bagOfInt32s;
    
    public Collection<Integer> getBagOfInt32s() {
        return _bagOfInt32s;
    }

    public void setBagOfInt32s(final Collection<Integer> _bagOfInt32s) {
        this._bagOfInt32s = _bagOfInt32s;
    }

    
    @Property(name = "BagOfInt64s", 
                type = "Collection(Edm.Int64)", 
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
    private Collection<Long> _bagOfInt64s;
    
    public Collection<Long> getBagOfInt64s() {
        return _bagOfInt64s;
    }

    public void setBagOfInt64s(final Collection<Long> _bagOfInt64s) {
        this._bagOfInt64s = _bagOfInt64s;
    }

    
    @Property(name = "BagOfGuids", 
                type = "Collection(Edm.Guid)", 
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
    private Collection<UUID> _bagOfGuids;
    
    public Collection<UUID> getBagOfGuids() {
        return _bagOfGuids;
    }

    public void setBagOfGuids(final Collection<UUID> _bagOfGuids) {
        this._bagOfGuids = _bagOfGuids;
    }

    
    @Property(name = "BagOfDateTime", 
                type = "Collection(Edm.DateTime)", 
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
    private Collection<Timestamp> _bagOfDateTime;
    
    public Collection<Timestamp> getBagOfDateTime() {
        return _bagOfDateTime;
    }

    public void setBagOfDateTime(final Collection<Timestamp> _bagOfDateTime) {
        this._bagOfDateTime = _bagOfDateTime;
    }

    
    @Property(name = "BagOfComplexToCategories", 
                type = "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.ComplexToCategory)", 
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
    private Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ComplexToCategory> _bagOfComplexToCategories;
    
    public Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ComplexToCategory> getBagOfComplexToCategories() {
        return _bagOfComplexToCategories;
    }

    public void setBagOfComplexToCategories(final Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ComplexToCategory> _bagOfComplexToCategories) {
        this._bagOfComplexToCategories = _bagOfComplexToCategories;
    }

    
    @Property(name = "ComplexPhone", 
                type = "Microsoft.Test.OData.Services.AstoriaDefaultService.Phone", 
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
                fcSourcePath = "PhoneNumber",
                fcTargetPath = "SyndicationRights",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = true)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Phone _complexPhone;
    
    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Phone getComplexPhone() {
        return _complexPhone;
    }

    public void setComplexPhone(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Phone _complexPhone) {
        this._complexPhone = _complexPhone;
    }

    
    @Property(name = "ComplexContactDetails", 
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
                fcSourcePath = "WorkPhone/Extension",
                fcTargetPath = "SyndicationSummary",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = true)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ContactDetails _complexContactDetails;
    
    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ContactDetails getComplexContactDetails() {
        return _complexContactDetails;
    }

    public void setComplexContactDetails(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ContactDetails _complexContactDetails) {
        this._complexContactDetails = _complexContactDetails;
    }

    
}
