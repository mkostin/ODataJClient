package com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types;

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
import com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.*;
import com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.*;

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


@EntityType(name = "Product",
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
public interface Product extends Serializable {

    
    
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
    public java.io.InputStream getPicture();

    public void setPicture(final java.io.InputStream _picture);

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
    public Integer getProductId();

    public void setProductId(final Integer _productId);

    
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
    public String getDescription();

    public void setDescription(final String _description);

    
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
    public com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.Dimensions getDimensions();

    public void setDimensions(final com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.Dimensions _dimensions);

    
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
    public String getBaseConcurrency();

    public void setBaseConcurrency(final String _baseConcurrency);

    
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
    public com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.ConcurrencyInfo getComplexConcurrency();

    public void setComplexConcurrency(final com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.ConcurrencyInfo _complexConcurrency);

    
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
    public com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.AuditInfo getNestedComplexConcurrency();

    public void setNestedComplexConcurrency(final com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.AuditInfo _nestedComplexConcurrency);

    

    @NavigationProperty(name = "RelatedProducts", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Product_RelatedProducts", 
                fromRole = "Product", 
                toRole = "RelatedProducts",
                containsTarget = false,
                onDelete = Action.NONE)
    public com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.ProductCollection getRelatedProducts();

    public void setRelatedProducts(final com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.ProductCollection _relatedProducts);


    @NavigationProperty(name = "Detail", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Product_Detail", 
                fromRole = "Product", 
                toRole = "Detail",
                containsTarget = false,
                onDelete = Action.NONE)
    public com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.ProductDetail getDetail();

    public void setDetail(final com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.ProductDetail _detail);


    @NavigationProperty(name = "Reviews", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Product_Reviews", 
                fromRole = "Product", 
                toRole = "Reviews",
                containsTarget = false,
                onDelete = Action.NONE)
    public com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.ProductReviewCollection getReviews();

    public void setReviews(final com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.ProductReviewCollection _reviews);


    @NavigationProperty(name = "Photos", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Product_Photos", 
                fromRole = "Product", 
                toRole = "Photos",
                containsTarget = false,
                onDelete = Action.NONE)
    public com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.ProductPhotoCollection getPhotos();

    public void setPhotos(final com.msopentech.odatajclient.proxy.actionoverloadingservice.microsoft.test.odata.services.astoriadefaultservice.types.ProductPhotoCollection _photos);


    @FunctionImport(name = "RetrieveProduct"      ,
                    returnType = "Edm.Int32")
    Integer retrieveProduct(
        );

}
