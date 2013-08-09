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

@ComplexType("ComplexToCategory")
public class ComplexToCategory extends AbstractType {

    @Property(name = "Term", type = "Edm.String", nullable = true)
    private String _term;

    public String getTerm() {
        return _term;
    }

    public void setTerm(final String _term) {
        this._term = _term;
    }
    @Property(name = "Scheme", type = "Edm.String", nullable = true)
    private String _scheme;

    public String getScheme() {
        return _scheme;
    }

    public void setScheme(final String _scheme) {
        this._scheme = _scheme;
    }
    @Property(name = "Label", type = "Edm.String", nullable = true)
    private String _label;

    public String getLabel() {
        return _label;
    }

    public void setLabel(final String _label) {
        this._label = _label;
    }
}