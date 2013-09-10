package com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice;

import com.msopentech.odatajclient.engine.client.http.HttpMethod;
import com.msopentech.odatajclient.engine.data.metadata.edm.ParameterMode;
import com.msopentech.odatajclient.proxy.api.annotations.EntityContainer;
import com.msopentech.odatajclient.proxy.api.annotations.FunctionImport;
import com.msopentech.odatajclient.proxy.api.annotations.Parameter;
import com.msopentech.odatajclient.proxy.api.AbstractAsyncContainer;
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
import java.util.concurrent.Future;

@EntityContainer(name = "DefaultContainer", 
  isDefaultEntityContainer = true)
public interface AsyncDefaultContainer extends AbstractAsyncContainer {

    AsyncAllGeoTypesSet getAllGeoTypesSet();

    AsyncAllGeoCollectionTypesSet getAllGeoCollectionTypesSet();

    AsyncCustomer getCustomer();

    AsyncLogin getLogin();

    AsyncRSAToken getRSAToken();

    AsyncPageView getPageView();

    AsyncLastLogin getLastLogin();

    AsyncMessage getMessage();

    AsyncMessageAttachment getMessageAttachment();

    AsyncOrder getOrder();

    AsyncOrderLine getOrderLine();

    AsyncProduct getProduct();

    AsyncProductDetail getProductDetail();

    AsyncProductReview getProductReview();

    AsyncProductPhoto getProductPhoto();

    AsyncCustomerInfo getCustomerInfo();

    AsyncComputer getComputer();

    AsyncComputerDetail getComputerDetail();

    AsyncDriver getDriver();

    AsyncLicense getLicense();

    AsyncMappedEntityType getMappedEntityType();

    AsyncCar getCar();

    AsyncPerson getPerson();

    AsyncPersonMetadata getPersonMetadata();


      @FunctionImport(name = "GetPrimitiveString"     ,
                    httpMethod = HttpMethod.GET ,
                    returnType = "Edm.String")
    Future<String> getPrimitiveString(
    );
        @FunctionImport(name = "GetSpecificCustomer" , 
                    entitySet = Customer.class    ,
                    httpMethod = HttpMethod.GET ,
                    returnType = "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.Customer)")
    Future<com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.CustomerCollection> getSpecificCustomer(
        @Parameter(name = "Name", type = "Edm.String") String name
    );
        @FunctionImport(name = "GetCustomerCount"     ,
                    httpMethod = HttpMethod.GET ,
                    returnType = "Edm.Int32")
    Future<Integer> getCustomerCount(
    );
        @FunctionImport(name = "GetArgumentPlusOne"     ,
                    httpMethod = HttpMethod.GET ,
                    returnType = "Edm.Int32")
    Future<Integer> getArgumentPlusOne(
        @Parameter(name = "arg1", type = "Edm.Int32") Integer arg1
    );
        @FunctionImport(name = "EntityProjectionReturnsCollectionOfComplexTypes"     ,
                    httpMethod = HttpMethod.GET ,
                    returnType = "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails)")
    Future<Collection<com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.ContactDetails>> entityProjectionReturnsCollectionOfComplexTypes(
    );
        @FunctionImport(name = "ResetDataSource"     ,
                    httpMethod = HttpMethod.POST )
    void resetDataSource(
    );
        @FunctionImport(name = "InStreamErrorGetCustomer" , 
                    entitySet = Customer.class    ,
                    httpMethod = HttpMethod.GET ,
                    returnType = "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.Customer)")
    Future<com.msopentech.odatajclient.proxy.defaultservice.microsoft.test.odata.services.astoriadefaultservice.types.CustomerCollection> inStreamErrorGetCustomer(
    );
            }
