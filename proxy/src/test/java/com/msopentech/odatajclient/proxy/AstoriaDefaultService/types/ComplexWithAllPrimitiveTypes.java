package com.msopentech.odatajclient.proxy.AstoriaDefaultService.types;

import com.msopentech.odatajclient.proxy.api.annotations.ComplexType;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import com.msopentech.odatajclient.proxy.api.impl.AbstractType;

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

@ComplexType("ComplexWithAllPrimitiveTypes")
public class ComplexWithAllPrimitiveTypes extends AbstractType {

    @Property(name = "Binary", type = "Edm.Binary", nullable = true)
    private byte[] _binary;

    public byte[] getBinary() {
        return _binary;
    }

    public void setBinary(final byte[] _binary) {
        this._binary = _binary;
    }
    @Property(name = "Boolean", type = "Edm.Boolean", nullable = false)
    private Boolean _boolean;

    public Boolean getBoolean() {
        return _boolean;
    }

    public void setBoolean(final Boolean _boolean) {
        this._boolean = _boolean;
    }
    @Property(name = "Byte", type = "Edm.Byte", nullable = false)
    private Integer _byte;

    public Integer getByte() {
        return _byte;
    }

    public void setByte(final Integer _byte) {
        this._byte = _byte;
    }
    @Property(name = "DateTime", type = "Edm.DateTime", nullable = false)
    private Timestamp _dateTime;

    public Timestamp getDateTime() {
        return _dateTime;
    }

    public void setDateTime(final Timestamp _dateTime) {
        this._dateTime = _dateTime;
    }
    @Property(name = "Decimal", type = "Edm.Decimal", nullable = false)
    private BigDecimal _decimal;

    public BigDecimal getDecimal() {
        return _decimal;
    }

    public void setDecimal(final BigDecimal _decimal) {
        this._decimal = _decimal;
    }
    @Property(name = "Double", type = "Edm.Double", nullable = false)
    private Double _double;

    public Double getDouble() {
        return _double;
    }

    public void setDouble(final Double _double) {
        this._double = _double;
    }
    @Property(name = "Int16", type = "Edm.Int16", nullable = false)
    private Short _int16;

    public Short getInt16() {
        return _int16;
    }

    public void setInt16(final Short _int16) {
        this._int16 = _int16;
    }
    @Property(name = "Int32", type = "Edm.Int32", nullable = false)
    private Integer _int32;

    public Integer getInt32() {
        return _int32;
    }

    public void setInt32(final Integer _int32) {
        this._int32 = _int32;
    }
    @Property(name = "Int64", type = "Edm.Int64", nullable = false)
    private Long _int64;

    public Long getInt64() {
        return _int64;
    }

    public void setInt64(final Long _int64) {
        this._int64 = _int64;
    }
    @Property(name = "SByte", type = "Edm.SByte", nullable = false)
    private Byte _sByte;

    public Byte getSByte() {
        return _sByte;
    }

    public void setSByte(final Byte _sByte) {
        this._sByte = _sByte;
    }
    @Property(name = "String", type = "Edm.String", nullable = true)
    private String _string;

    public String getString() {
        return _string;
    }

    public void setString(final String _string) {
        this._string = _string;
    }
    @Property(name = "Single", type = "Edm.Single", nullable = false)
    private Float _single;

    public Float getSingle() {
        return _single;
    }

    public void setSingle(final Float _single) {
        this._single = _single;
    }
    @Property(name = "GeographyPoint", type = "Edm.GeographyPoint", nullable = true)
    private Point _geographyPoint;

    public Point getGeographyPoint() {
        return _geographyPoint;
    }

    public void setGeographyPoint(final Point _geographyPoint) {
        this._geographyPoint = _geographyPoint;
    }
    @Property(name = "GeometryPoint", type = "Edm.GeometryPoint", nullable = true)
    private Point _geometryPoint;

    public Point getGeometryPoint() {
        return _geometryPoint;
    }

    public void setGeometryPoint(final Point _geometryPoint) {
        this._geometryPoint = _geometryPoint;
    }
}