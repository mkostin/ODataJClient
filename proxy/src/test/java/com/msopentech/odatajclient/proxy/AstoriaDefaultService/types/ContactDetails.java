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

@ComplexType("ContactDetails")
public class ContactDetails extends AbstractType {

    @Property(name = "EmailBag", type = "Collection(Edm.String)", nullable = false)
    private Collection<String> _emailBag;

    public Collection<String> getEmailBag() {
        return _emailBag;
    }

    public void setEmailBag(final Collection<String> _emailBag) {
        this._emailBag = _emailBag;
    }
    @Property(name = "AlternativeNames", type = "Collection(Edm.String)", nullable = false)
    private Collection<String> _alternativeNames;

    public Collection<String> getAlternativeNames() {
        return _alternativeNames;
    }

    public void setAlternativeNames(final Collection<String> _alternativeNames) {
        this._alternativeNames = _alternativeNames;
    }
    @Property(name = "ContactAlias", type = "Microsoft.Test.OData.Services.AstoriaDefaultService.Aliases", nullable = true)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Aliases _contactAlias;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Aliases getContactAlias() {
        return _contactAlias;
    }

    public void setContactAlias(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Aliases _contactAlias) {
        this._contactAlias = _contactAlias;
    }
    @Property(name = "HomePhone", type = "Microsoft.Test.OData.Services.AstoriaDefaultService.Phone", nullable = true)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Phone _homePhone;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Phone getHomePhone() {
        return _homePhone;
    }

    public void setHomePhone(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Phone _homePhone) {
        this._homePhone = _homePhone;
    }
    @Property(name = "WorkPhone", type = "Microsoft.Test.OData.Services.AstoriaDefaultService.Phone", nullable = true)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Phone _workPhone;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Phone getWorkPhone() {
        return _workPhone;
    }

    public void setWorkPhone(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Phone _workPhone) {
        this._workPhone = _workPhone;
    }
    @Property(name = "MobilePhoneBag", type = "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.Phone)", nullable = false)
    private Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Phone> _mobilePhoneBag;

    public Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Phone> getMobilePhoneBag() {
        return _mobilePhoneBag;
    }

    public void setMobilePhoneBag(final Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Phone> _mobilePhoneBag) {
        this._mobilePhoneBag = _mobilePhoneBag;
    }
}