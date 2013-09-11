package com.msopentech.odatajclient.proxy.primitivekeysservice.microsoft.test.odata.services.primitivekeysservice;

import com.msopentech.odatajclient.engine.client.http.HttpMethod;
import com.msopentech.odatajclient.engine.data.metadata.edm.ParameterMode;
import com.msopentech.odatajclient.proxy.api.annotations.EntityContainer;
import com.msopentech.odatajclient.proxy.api.annotations.FunctionImport;
import com.msopentech.odatajclient.proxy.api.annotations.Parameter;
import com.msopentech.odatajclient.proxy.api.AbstractAsyncContainer;
import com.msopentech.odatajclient.proxy.primitivekeysservice.microsoft.test.odata.services.primitivekeysservice.*;
import com.msopentech.odatajclient.proxy.primitivekeysservice.microsoft.test.odata.services.primitivekeysservice.types.*;

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
import java.util.concurrent.Future;

@EntityContainer(name = "TestContext", 
  isDefaultEntityContainer = true)
public interface AsyncTestContext extends AbstractAsyncContainer {

    AsyncEdmBinarySet getEdmBinarySet();

    AsyncEdmBooleanSet getEdmBooleanSet();

    AsyncEdmByteSet getEdmByteSet();

    AsyncEdmDateTimeSet getEdmDateTimeSet();

    AsyncEdmDecimalSet getEdmDecimalSet();

    AsyncEdmDoubleSet getEdmDoubleSet();

    AsyncEdmSingleSet getEdmSingleSet();

    AsyncEdmGuidSet getEdmGuidSet();

    AsyncEdmInt16Set getEdmInt16Set();

    AsyncEdmInt32Set getEdmInt32Set();

    AsyncEdmInt64Set getEdmInt64Set();

    AsyncEdmStringSet getEdmStringSet();

    AsyncEdmTimeSet getEdmTimeSet();

    AsyncEdmDateTimeOffsetSet getEdmDateTimeOffsetSet();

    AsyncFolders getFolders();


}
