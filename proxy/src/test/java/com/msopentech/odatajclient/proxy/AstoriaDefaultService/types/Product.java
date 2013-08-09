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

@EntityType(value="Product",
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
public class Product extends AbstractType {

    
    
    @Property(name = "Picture", 
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
    private URI _picture;
    
    public URI getPicture() {
        return _picture;
    }

    public void setPicture(final URI _picture) {
        this._picture = _picture;
    }

    @Key
    @Property(name = "ProductId", 
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
    private Integer _productId;
    
    public Integer getProductId() {
        return _productId;
    }

    public void setProductId(final Integer _productId) {
        this._productId = _productId;
    }

    
    @Property(name = "Description", 
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
    private String _description;
    
    public String getDescription() {
        return _description;
    }

    public void setDescription(final String _description) {
        this._description = _description;
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

    
    @Property(name = "BaseConcurrency", 
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
                concurrencyMode = ConcurrencyMode.FIXED,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    private String _baseConcurrency;
    
    public String getBaseConcurrency() {
        return _baseConcurrency;
    }

    public void setBaseConcurrency(final String _baseConcurrency) {
        this._baseConcurrency = _baseConcurrency;
    }

    
    @Property(name = "ComplexConcurrency", 
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
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ConcurrencyInfo _complexConcurrency;
    
    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ConcurrencyInfo getComplexConcurrency() {
        return _complexConcurrency;
    }

    public void setComplexConcurrency(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ConcurrencyInfo _complexConcurrency) {
        this._complexConcurrency = _complexConcurrency;
    }

    
    @Property(name = "NestedComplexConcurrency", 
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
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.AuditInfo _nestedComplexConcurrency;
    
    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.AuditInfo getNestedComplexConcurrency() {
        return _nestedComplexConcurrency;
    }

    public void setNestedComplexConcurrency(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.AuditInfo _nestedComplexConcurrency) {
        this._nestedComplexConcurrency = _nestedComplexConcurrency;
    }

    

    @NavigationProperty(name = "RelatedProducts", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Product_RelatedProducts", 
                fromRole = "Product", 
                toRole = "RelatedProducts",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Product _relatedProducts;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Product getRelatedProducts() {
        return _relatedProducts;
    }

    public void setRelatedProducts(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Product _relatedProducts) {
        this._relatedProducts = _relatedProducts;
    }


    @NavigationProperty(name = "Detail", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Product_Detail", 
                fromRole = "Product", 
                toRole = "Detail",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ProductDetail _detail;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ProductDetail getDetail() {
        return _detail;
    }

    public void setDetail(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ProductDetail _detail) {
        this._detail = _detail;
    }


    @NavigationProperty(name = "Reviews", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Product_Reviews", 
                fromRole = "Product", 
                toRole = "Reviews",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ProductReview _reviews;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ProductReview getReviews() {
        return _reviews;
    }

    public void setReviews(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ProductReview _reviews) {
        this._reviews = _reviews;
    }


    @NavigationProperty(name = "Photos", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Product_Photos", 
                fromRole = "Product", 
                toRole = "Photos",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ProductPhoto _photos;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ProductPhoto getPhotos() {
        return _photos;
    }

    public void setPhotos(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ProductPhoto _photos) {
        this._photos = _photos;
    }

}
