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

@EntityType(value="AllSpatialTypes",
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
public class AllSpatialTypes extends AbstractType {

    
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

    
    @Property(name = "Geog", 
                type = "Edm.Geography", 
                nullable = true,
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
    private Geospatial _geog;
    
    public Geospatial getGeog() {
        return _geog;
    }

    public void setGeog(final Geospatial _geog) {
        this._geog = _geog;
    }

    
    @Property(name = "GeogPoint", 
                type = "Edm.GeographyPoint", 
                nullable = true,
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
    private Point _geogPoint;
    
    public Point getGeogPoint() {
        return _geogPoint;
    }

    public void setGeogPoint(final Point _geogPoint) {
        this._geogPoint = _geogPoint;
    }

    
    @Property(name = "GeogLine", 
                type = "Edm.GeographyLineString", 
                nullable = true,
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
    private LineString _geogLine;
    
    public LineString getGeogLine() {
        return _geogLine;
    }

    public void setGeogLine(final LineString _geogLine) {
        this._geogLine = _geogLine;
    }

    
    @Property(name = "GeogPolygon", 
                type = "Edm.GeographyPolygon", 
                nullable = true,
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
    private Polygon _geogPolygon;
    
    public Polygon getGeogPolygon() {
        return _geogPolygon;
    }

    public void setGeogPolygon(final Polygon _geogPolygon) {
        this._geogPolygon = _geogPolygon;
    }

    
    @Property(name = "GeogCollection", 
                type = "Edm.GeographyCollection", 
                nullable = true,
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
    private GeospatialCollection _geogCollection;
    
    public GeospatialCollection getGeogCollection() {
        return _geogCollection;
    }

    public void setGeogCollection(final GeospatialCollection _geogCollection) {
        this._geogCollection = _geogCollection;
    }

    
    @Property(name = "GeogMultiPoint", 
                type = "Edm.GeographyMultiPoint", 
                nullable = true,
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
    private MultiPoint _geogMultiPoint;
    
    public MultiPoint getGeogMultiPoint() {
        return _geogMultiPoint;
    }

    public void setGeogMultiPoint(final MultiPoint _geogMultiPoint) {
        this._geogMultiPoint = _geogMultiPoint;
    }

    
    @Property(name = "GeogMultiLine", 
                type = "Edm.GeographyMultiLineString", 
                nullable = true,
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
    private MultiLineString _geogMultiLine;
    
    public MultiLineString getGeogMultiLine() {
        return _geogMultiLine;
    }

    public void setGeogMultiLine(final MultiLineString _geogMultiLine) {
        this._geogMultiLine = _geogMultiLine;
    }

    
    @Property(name = "GeogMultiPolygon", 
                type = "Edm.GeographyMultiPolygon", 
                nullable = true,
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
    private MultiPolygon _geogMultiPolygon;
    
    public MultiPolygon getGeogMultiPolygon() {
        return _geogMultiPolygon;
    }

    public void setGeogMultiPolygon(final MultiPolygon _geogMultiPolygon) {
        this._geogMultiPolygon = _geogMultiPolygon;
    }

    
    @Property(name = "Geom", 
                type = "Edm.Geometry", 
                nullable = true,
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
    private Geospatial _geom;
    
    public Geospatial getGeom() {
        return _geom;
    }

    public void setGeom(final Geospatial _geom) {
        this._geom = _geom;
    }

    
    @Property(name = "GeomPoint", 
                type = "Edm.GeometryPoint", 
                nullable = true,
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
    private Point _geomPoint;
    
    public Point getGeomPoint() {
        return _geomPoint;
    }

    public void setGeomPoint(final Point _geomPoint) {
        this._geomPoint = _geomPoint;
    }

    
    @Property(name = "GeomLine", 
                type = "Edm.GeometryLineString", 
                nullable = true,
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
    private LineString _geomLine;
    
    public LineString getGeomLine() {
        return _geomLine;
    }

    public void setGeomLine(final LineString _geomLine) {
        this._geomLine = _geomLine;
    }

    
    @Property(name = "GeomPolygon", 
                type = "Edm.GeometryPolygon", 
                nullable = true,
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
    private Polygon _geomPolygon;
    
    public Polygon getGeomPolygon() {
        return _geomPolygon;
    }

    public void setGeomPolygon(final Polygon _geomPolygon) {
        this._geomPolygon = _geomPolygon;
    }

    
    @Property(name = "GeomCollection", 
                type = "Edm.GeometryCollection", 
                nullable = true,
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
    private GeospatialCollection _geomCollection;
    
    public GeospatialCollection getGeomCollection() {
        return _geomCollection;
    }

    public void setGeomCollection(final GeospatialCollection _geomCollection) {
        this._geomCollection = _geomCollection;
    }

    
    @Property(name = "GeomMultiPoint", 
                type = "Edm.GeometryMultiPoint", 
                nullable = true,
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
    private MultiPoint _geomMultiPoint;
    
    public MultiPoint getGeomMultiPoint() {
        return _geomMultiPoint;
    }

    public void setGeomMultiPoint(final MultiPoint _geomMultiPoint) {
        this._geomMultiPoint = _geomMultiPoint;
    }

    
    @Property(name = "GeomMultiLine", 
                type = "Edm.GeometryMultiLineString", 
                nullable = true,
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
    private MultiLineString _geomMultiLine;
    
    public MultiLineString getGeomMultiLine() {
        return _geomMultiLine;
    }

    public void setGeomMultiLine(final MultiLineString _geomMultiLine) {
        this._geomMultiLine = _geomMultiLine;
    }

    
    @Property(name = "GeomMultiPolygon", 
                type = "Edm.GeometryMultiPolygon", 
                nullable = true,
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
    private MultiPolygon _geomMultiPolygon;
    
    public MultiPolygon getGeomMultiPolygon() {
        return _geomMultiPolygon;
    }

    public void setGeomMultiPolygon(final MultiPolygon _geomMultiPolygon) {
        this._geomMultiPolygon = _geomMultiPolygon;
    }

    
}
