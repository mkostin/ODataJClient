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

@ComplexType("ConcurrencyInfo")
public class ConcurrencyInfo extends AbstractType {

    @Property(name = "Token", type = "Edm.String", nullable = true)
    private String _token;

    public String getToken() {
        return _token;
    }

    public void setToken(final String _token) {
        this._token = _token;
    }
    @Property(name = "QueriedDateTime", type = "Edm.DateTime", nullable = true)
    private Timestamp _queriedDateTime;

    public Timestamp getQueriedDateTime() {
        return _queriedDateTime;
    }

    public void setQueriedDateTime(final Timestamp _queriedDateTime) {
        this._queriedDateTime = _queriedDateTime;
    }
}