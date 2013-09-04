package com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice;

import com.msopentech.odatajclient.proxy.api.AbstractEntitySet;
import com.msopentech.odatajclient.proxy.api.annotations.EntitySet;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKey;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKeyElement;
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


@EntitySet(name = "Driver")
public interface Driver extends AbstractEntitySet<com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Driver, String, com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.DriverCollection> {

    com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.DriverCollection getAllDriver();
    com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Driver newDriver();
    com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.DriverCollection newDriverCollection();
}
