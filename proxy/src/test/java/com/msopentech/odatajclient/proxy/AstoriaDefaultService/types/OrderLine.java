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

@EntityType(value="OrderLine",
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
public class OrderLine extends AbstractType {

        
    
    @Property(name = "OrderLineStream", 
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
    private URI _orderLineStream;
    
    public URI getOrderLineStream() {
        return _orderLineStream;
    }

    public void setOrderLineStream(final URI _orderLineStream) {
        this._orderLineStream = _orderLineStream;
    }

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

    
    @Property(name = "Quantity", 
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
    private Integer _quantity;
    
    public Integer getQuantity() {
        return _quantity;
    }

    public void setQuantity(final Integer _quantity) {
        this._quantity = _quantity;
    }

    
    @Property(name = "ConcurrencyToken", 
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
    private String _concurrencyToken;
    
    public String getConcurrencyToken() {
        return _concurrencyToken;
    }

    public void setConcurrencyToken(final String _concurrencyToken) {
        this._concurrencyToken = _concurrencyToken;
    }

    

    @NavigationProperty(name = "Order", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.OrderLine_Order", 
                fromRole = "OrderLine", 
                toRole = "Order",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Order _order;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Order getOrder() {
        return _order;
    }

    public void setOrder(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Order _order) {
        this._order = _order;
    }


    @NavigationProperty(name = "Product", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.OrderLine_Product", 
                fromRole = "OrderLine", 
                toRole = "Product",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Product _product;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Product getProduct() {
        return _product;
    }

    public void setProduct(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Product _product) {
        this._product = _product;
    }

}
