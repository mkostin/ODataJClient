package com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types;

import com.msopentech.odatajclient.proxy.api.annotations.ComplexType;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import com.msopentech.odatajclient.proxy.api.impl.AbstractType;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.*;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.*;

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

@ComplexType("Dimensions")
public class Dimensions extends AbstractType {

    private BigDecimal _width;

    @Property(name = "Width", type = "Edm.Decimal", nullable = false)
    public BigDecimal getWidth() {
        return _width;
    }

    public void setWidth(final BigDecimal _width) {
        this._width = _width;
    }
    private BigDecimal _height;

    @Property(name = "Height", type = "Edm.Decimal", nullable = false)
    public BigDecimal getHeight() {
        return _height;
    }

    public void setHeight(final BigDecimal _height) {
        this._height = _height;
    }
    private BigDecimal _depth;

    @Property(name = "Depth", type = "Edm.Decimal", nullable = false)
    public BigDecimal getDepth() {
        return _depth;
    }

    public void setDepth(final BigDecimal _depth) {
        this._depth = _depth;
    }
}