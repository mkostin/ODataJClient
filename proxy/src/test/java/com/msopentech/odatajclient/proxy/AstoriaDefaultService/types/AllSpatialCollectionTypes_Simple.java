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

@EntityType(value="AllSpatialCollectionTypes_Simple",
        openType = false,
        hasStream = false,
        isAbstract = false,
        baseType = "Microsoft.Test.OData.Services.AstoriaDefaultService.AllSpatialCollectionTypes",
        fcSourcePath = "",
        fcTargetPath = "",
        fcContentKind = EdmContentKind.text,
        fcNSPrefix = "",
        fcNSURI = "",
        fcKeepInContent = false)
public class AllSpatialCollectionTypes_Simple extends AbstractType {


    
    @Property(name = "ManyGeogPoint", 
                type = "Collection(Edm.GeographyPoint)", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "Variable",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    private Collection<Point> _manyGeogPoint;
    
    public Collection<Point> getManyGeogPoint() {
        return _manyGeogPoint;
    }

    public void setManyGeogPoint(final Collection<Point> _manyGeogPoint) {
        this._manyGeogPoint = _manyGeogPoint;
    }

    
    @Property(name = "ManyGeogLine", 
                type = "Collection(Edm.GeographyLineString)", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "Variable",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    private Collection<LineString> _manyGeogLine;
    
    public Collection<LineString> getManyGeogLine() {
        return _manyGeogLine;
    }

    public void setManyGeogLine(final Collection<LineString> _manyGeogLine) {
        this._manyGeogLine = _manyGeogLine;
    }

    
    @Property(name = "ManyGeogPolygon", 
                type = "Collection(Edm.GeographyPolygon)", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "Variable",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    private Collection<Polygon> _manyGeogPolygon;
    
    public Collection<Polygon> getManyGeogPolygon() {
        return _manyGeogPolygon;
    }

    public void setManyGeogPolygon(final Collection<Polygon> _manyGeogPolygon) {
        this._manyGeogPolygon = _manyGeogPolygon;
    }

    
    @Property(name = "ManyGeomPoint", 
                type = "Collection(Edm.GeometryPoint)", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "Variable",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    private Collection<Point> _manyGeomPoint;
    
    public Collection<Point> getManyGeomPoint() {
        return _manyGeomPoint;
    }

    public void setManyGeomPoint(final Collection<Point> _manyGeomPoint) {
        this._manyGeomPoint = _manyGeomPoint;
    }

    
    @Property(name = "ManyGeomLine", 
                type = "Collection(Edm.GeometryLineString)", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "Variable",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    private Collection<LineString> _manyGeomLine;
    
    public Collection<LineString> getManyGeomLine() {
        return _manyGeomLine;
    }

    public void setManyGeomLine(final Collection<LineString> _manyGeomLine) {
        this._manyGeomLine = _manyGeomLine;
    }

    
    @Property(name = "ManyGeomPolygon", 
                type = "Collection(Edm.GeometryPolygon)", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "Variable",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    private Collection<Polygon> _manyGeomPolygon;
    
    public Collection<Polygon> getManyGeomPolygon() {
        return _manyGeomPolygon;
    }

    public void setManyGeomPolygon(final Collection<Polygon> _manyGeomPolygon) {
        this._manyGeomPolygon = _manyGeomPolygon;
    }

    
}
