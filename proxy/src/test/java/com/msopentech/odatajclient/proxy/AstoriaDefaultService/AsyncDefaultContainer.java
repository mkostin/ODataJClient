package com.msopentech.odatajclient.proxy.AstoriaDefaultService;

import com.msopentech.odatajclient.engine.data.metadata.edm.ParameterMode;
import com.msopentech.odatajclient.proxy.api.annotations.EntityContainer;
import com.msopentech.odatajclient.proxy.api.annotations.FunctionImport;
import com.msopentech.odatajclient.proxy.api.annotations.Parameter;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.*;

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
import java.util.concurrent.Future;

@EntityContainer(name = "DefaultContainer", isDefaultEntityContainer = true)
public interface AsyncDefaultContainer {

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


    @FunctionImport(name = "GetPrimitiveString" , returnType = "Edm.String")
    Future<String> getPrimitiveString(
    );

    @FunctionImport(name = "GetSpecificCustomer", entitySet = Customer.class , returnType = "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.Customer)")
    Future<Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer>> getSpecificCustomer(
        @Parameter(name = "Name", type = "Edm.String") String name
    );

    @FunctionImport(name = "GetCustomerCount" , returnType = "Edm.Int32")
    Future<Integer> getCustomerCount(
    );

    @FunctionImport(name = "GetArgumentPlusOne" , returnType = "Edm.Int32")
    Future<Integer> getArgumentPlusOne(
        @Parameter(name = "arg1", type = "Edm.Int32") Integer arg1
    );

    @FunctionImport(name = "EntityProjectionReturnsCollectionOfComplexTypes" , returnType = "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails)")
    Future<Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ContactDetails>> entityProjectionReturnsCollectionOfComplexTypes(
    );

    @FunctionImport(name = "ResetDataSource" )
    void resetDataSource(
    );

    @FunctionImport(name = "InStreamErrorGetCustomer", entitySet = Customer.class , returnType = "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.Customer)")
    Future<Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Customer>> inStreamErrorGetCustomer(
    );

    @FunctionImport(name = "IncreaseSalaries" )
    void increaseSalaries(
        @Parameter(name = "employees", type = "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.Employee)") Collection<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Employee> employees, 
        @Parameter(name = "n", type = "Edm.Int32") Integer n
    );

    @FunctionImport(name = "Sack" )
    void sack(
        @Parameter(name = "employee", type = "Microsoft.Test.OData.Services.AstoriaDefaultService.Employee") com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Employee employee
    );

    @FunctionImport(name = "GetComputer", entitySet = Computer.class , returnType = "Microsoft.Test.OData.Services.AstoriaDefaultService.Computer")
    Future<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Computer> getComputer(
        @Parameter(name = "computer", type = "Microsoft.Test.OData.Services.AstoriaDefaultService.Computer") com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Computer computer
    );

    @FunctionImport(name = "ChangeProductDimensions" )
    void changeProductDimensions(
        @Parameter(name = "product", type = "Microsoft.Test.OData.Services.AstoriaDefaultService.Product") com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Product product, 
        @Parameter(name = "dimensions", type = "Microsoft.Test.OData.Services.AstoriaDefaultService.Dimensions") com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Dimensions dimensions
    );

    @FunctionImport(name = "ResetComputerDetailsSpecifications" )
    void resetComputerDetailsSpecifications(
        @Parameter(name = "computerDetail", type = "Microsoft.Test.OData.Services.AstoriaDefaultService.ComputerDetail") com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ComputerDetail computerDetail, 
        @Parameter(name = "specifications", type = "Collection(Edm.String)") Collection<String> specifications, 
        @Parameter(name = "purchaseTime", type = "Edm.DateTime") Timestamp purchaseTime
    );

}
