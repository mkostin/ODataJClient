package com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice;

import com.msopentech.odatajclient.proxy.api.AbstractEntitySet;
import com.msopentech.odatajclient.proxy.api.annotations.EntitySet;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKey;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKeyElement;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.*;
import com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.*;

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


@EntitySet(name = "Person")
public interface Person extends AbstractEntitySet<com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.Person, Integer, com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.PersonCollection> {

    com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.Person newPerson();
    com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.PersonCollection newPersonCollection();
    com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.Contractor newContractor();
    com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.ContractorCollection newContractorCollection();
    com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.Employee newEmployee();
    com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.EmployeeCollection newEmployeeCollection();
    com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.SpecialEmployee newSpecialEmployee();
    com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.SpecialEmployeeCollection newSpecialEmployeeCollection();
}
