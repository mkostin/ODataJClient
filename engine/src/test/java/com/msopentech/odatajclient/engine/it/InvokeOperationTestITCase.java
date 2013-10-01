/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.engine.it;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataDeleteRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.invoke.ODataInvokeRequest;
import com.msopentech.odatajclient.engine.communication.request.invoke.ODataInvokeRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataInvokeResponse;
import com.msopentech.odatajclient.engine.data.ODataCollectionValue;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataNoContent;
import com.msopentech.odatajclient.engine.data.ODataOperation;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.EdmType;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer.FunctionImport;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.utils.URIUtils;

public class InvokeOperationTestITCase extends AbstractTest{
	// get operation with no parameters
	private void invokeOperationWithNoParameters(final ODataPubFormat format,
			final String contentType,
			final String prefer) {
		ODataProperty property;
        final EdmMetadata metadata =
        		ODataRetrieveRequestFactory.getMetadataRequest(testDefaultServiceRootURL).execute().getBody();
        assertNotNull(metadata);

        final EntityContainer container = metadata.getSchema(0).getEntityContainers().get(0);
        System.out.println("container "+container);
        //get primitive value property
        FunctionImport funcImp = container.getFunctionImport("GetPrimitiveString");

        ODataURIBuilder builder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendFunctionImportSegment(URIUtils.rootFunctionImportURISegment(container, funcImp));

        ODataInvokeRequest<ODataProperty> req =
                ODataInvokeRequestFactory.getInvokeRequest(builder.build(), metadata, funcImp);
        req.setFormat(format);
        req.setContentType(contentType);
        req.setPrefer(prefer);
        ODataInvokeResponse<ODataProperty> res = req.execute();
        if(prefer.equals("return-content")){
        	assertNotNull(res);
            property = res.getBody();
            assertNotNull(property);
            System.out.println(property.getPrimitiveValue().<String>toCastValue());
            assertEquals("Foo", property.getPrimitiveValue().<String>toCastValue());
        }
        else{
        	assertEquals(204,res.getStatusCode());
        }
        //get collection of complex type property
        funcImp = container.getFunctionImport("EntityProjectionReturnsCollectionOfComplexTypes");

        builder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendFunctionImportSegment(URIUtils.rootFunctionImportURISegment(container, funcImp));

        req = ODataInvokeRequestFactory.getInvokeRequest(builder.build(), metadata, funcImp);
        req.setFormat(format);
        req.setContentType(contentType);
        req.setPrefer(prefer);
        res = req.execute();
        if(prefer.equals("return-content")){
        	assertNotNull(res);
        	property = res.getBody();
            assertNotNull(property);
            assertTrue(property.hasCollectionValue());
            System.out.println( property.getCollectionValue().getTypeName());
            if(!format.equals(ODataPubFormat.JSON_NO_METADATA)){
            	assertEquals("Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails)",
                property.getCollectionValue().getTypeName());
            }
        }
        else{
        	assertEquals(204,res.getStatusCode());
        }
    }
	
	// get operation with no parameters and format as JSON full metadata
	@Test
	public void invokeNoParamWithJSON(){
		ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
		String contentType = "application/json;odata=fullmetadata";
		String prefer = "return-content";
		try{
			invokeOperationWithNoParameters(format,contentType,prefer);
		}catch(Exception e){
			fail(e.getMessage());
		}catch(AssertionError e){
			fail(e.getMessage());
		}
	}
	// get operation with no parameters and format as ATOM
	@Test
	public void invokeNoParamWithATOM(){
		ODataPubFormat format = ODataPubFormat.ATOM;
		String contentType = "application/atom+xml";
		String prefer = "return-content";
		try{
			invokeOperationWithNoParameters(format,contentType,prefer);
		}catch(Exception e){
			fail(e.getMessage());
		}catch(AssertionError e){
			fail(e.getMessage());
		}
	}
	// get operation with no parameters and format as JSON minimal metadata
	@Test
	public void invokeNoParamWithJSONMinimal(){
		ODataPubFormat format = ODataPubFormat.JSON;
		String contentType = "application/json";
		String prefer = "return-content";
		try{
			invokeOperationWithNoParameters(format,contentType,prefer);
		}catch(Exception e){
			fail(e.getMessage());
		}catch(AssertionError e){
			fail(e.getMessage());
		}
	}
	// get operation with no parameters and format as JSON no metadata
	@Test
	public void invokeNoParamWithJSONNoMetadata(){
		ODataPubFormat format = ODataPubFormat.JSON_NO_METADATA;
		String contentType = "application/json;odata=nometadata";
		String prefer = "return-content";
		try{
			invokeOperationWithNoParameters(format,contentType,prefer);
		}catch(Exception e){
			fail(e.getMessage());
		}catch(AssertionError e){
			fail(e.getMessage());
		}
	}
	// get operation with no parameters, format as JSON and content type header as ATOM
	@Test
	public void invokeNoParamWithJSONAndATOM(){
		ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
		String contentType = "application/atom+xml";
		String prefer = "return-content";
		try{
			invokeOperationWithNoParameters(format,contentType,prefer);
		}catch(Exception e){
			fail(e.getMessage());
		}catch(AssertionError e){
			fail(e.getMessage());
		}
	}
	// get operation with no parameters, format as ATOM and content type header as JSON
	@Test
	public void invokeNoParamWithATOMAndJSON(){
		ODataPubFormat format = ODataPubFormat.ATOM;
		String contentType = "application/json;odata=fullmetadata";
		String prefer = "return-content";
		try{
			invokeOperationWithNoParameters(format,contentType,prefer);
		}catch(Exception e){
			fail(e.getMessage());
		}catch(AssertionError e){
			fail(e.getMessage());
		}
	}
	// get operation with no parameters
	private void invokeOperationWithParameters(
			final ODataPubFormat format,
			final String contentType,
			final String prefer) {
        // primitive result
        EdmMetadata metadata =
                ODataRetrieveRequestFactory.getMetadataRequest(testDefaultServiceRootURL).execute().getBody();
        assertNotNull(metadata);

        EntityContainer container = metadata.getSchema(0).getEntityContainers().get(0);
        FunctionImport imp = container.getFunctionImport("GetArgumentPlusOne");

        ODataURIBuilder builder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendFunctionImportSegment(URIUtils.rootFunctionImportURISegment(container, imp));

        EdmType type = new EdmType(imp.getParameters().get(0).getType());
        ODataPrimitiveValue argument = new ODataPrimitiveValue.Builder().
                setType(type.getSimpleType()).
                setValue(33).
                build();
        Map<String, ODataValue> parameters = new HashMap<String, ODataValue>();
        parameters.put(imp.getParameters().get(0).getName(), argument);

        final ODataInvokeRequest<ODataProperty> primitiveReq =
                ODataInvokeRequestFactory.getInvokeRequest(builder.build(), metadata, imp,
                parameters);
        primitiveReq.setFormat(format);
        primitiveReq.setContentType(contentType);
        primitiveReq.setPrefer(prefer);
        final ODataInvokeResponse<ODataProperty> primitiveRes = primitiveReq.execute();
        assertNotNull(primitiveRes);

        final ODataProperty property = primitiveRes.getBody();
        assertNotNull(property);
        assertEquals(Integer.valueOf(34), property.getPrimitiveValue().<Integer>toCastValue());

        // feed operation
        metadata = ODataRetrieveRequestFactory.getMetadataRequest(testDefaultServiceRootURL).execute().getBody();
        assertNotNull(metadata);

        container = metadata.getSchema(0).getEntityContainers().get(0);
        imp = container.getFunctionImport("GetSpecificCustomer");

        builder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendFunctionImportSegment(URIUtils.rootFunctionImportURISegment(container, imp));

        type = new EdmType(imp.getParameters().get(0).getType());
        argument = new ODataPrimitiveValue.Builder().
                setType(type.getSimpleType()).
                setText(StringUtils.EMPTY).
                build();
        parameters = new LinkedHashMap<String, ODataValue>();
        parameters.put(imp.getParameters().get(0).getName(), argument);

        final ODataInvokeRequest<ODataEntitySet> feedReq =
                ODataInvokeRequestFactory.getInvokeRequest(builder.build(), metadata, imp, parameters);
        feedReq.setFormat(format);
        feedReq.setContentType(contentType);
        feedReq.setPrefer(prefer);
        final ODataInvokeResponse<ODataEntitySet> feedRes = feedReq.execute();
        assertNotNull(feedRes);

        final ODataEntitySet feed = feedRes.getBody();
        assertNotNull(feed);

        final ODataProperty id = feed.getEntities().get(0).getProperty("CustomerId");
        assertNotNull(id);
    }
	// get operation with parameters, format as JSON full metadata
	@Test
	public void invokeParamWithJSON(){
		ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
		String contentType = "application/json;odata=fullmetadata";
		String prefer = "return-content";
		try{
			invokeOperationWithParameters(format,contentType,prefer);
		}catch(Exception e){
			fail(e.getMessage());
		}catch(AssertionError e){
			fail(e.getMessage());
		}
	}
	// get operation with parameters, format as ATOM 
	@Test
	public void invokeParamWithATOM(){
		ODataPubFormat format = ODataPubFormat.ATOM;
		String contentType = "application/atom+xml";
		String prefer = "return-content";
		try{
			invokeOperationWithParameters(format,contentType,prefer);
		}catch(Exception e){
			fail(e.getMessage());
		}catch(AssertionError e){
			fail(e.getMessage());
		}
	}
	
	// create a product
	private ODataEntity createProduct(
			final ODataPubFormat format,
			final String contentType, final int id) {
        final ODataEntity product = ODataFactory.newEntity(
                "Microsoft.Test.OData.Services.AstoriaDefaultService.Product");
        product.addProperty(ODataFactory.newPrimitiveProperty("ProductId", new ODataPrimitiveValue.Builder().
                setValue(id).setType(EdmSimpleType.INT_32).build()));
        product.addProperty(ODataFactory.newPrimitiveProperty("Description", new ODataPrimitiveValue.Builder().
                setText("Test Product").build()));
        product.addProperty(ODataFactory.newPrimitiveProperty("BaseConcurrency", new ODataPrimitiveValue.Builder().
                setText("Test Base Concurrency").setType(EdmSimpleType.STRING).build()));
       
        final ODataComplexValue dimensions = new ODataComplexValue(
                "Microsoft.Test.OData.Services.AstoriaDefaultService.Dimensions");       
        dimensions.add(ODataFactory.newPrimitiveProperty("Width",
                new ODataPrimitiveValue.Builder().setText("10.11").setType(EdmSimpleType.DECIMAL).build()));
        dimensions.add(ODataFactory.newPrimitiveProperty("Height",
                new ODataPrimitiveValue.Builder().setText("10.11").setType(EdmSimpleType.DECIMAL).build()));
        dimensions.add(ODataFactory.newPrimitiveProperty("Depth",
        new ODataPrimitiveValue.Builder().setText("10.11").setType(EdmSimpleType.DECIMAL).build()));
        
        product.addProperty(ODataFactory.newComplexProperty("Dimensions",
        		dimensions));

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("Product");

        final ODataEntityCreateRequest createReq =
                ODataCUDRequestFactory.getEntityCreateRequest(uriBuilder.build(), product);
        createReq.setFormat(format);
        createReq.setContentType(contentType);
        
        
        final ODataEntityCreateResponse createRes = createReq.execute();
        System.out.println("Etag ----"+createRes.getEtag());
        assertEquals(201, createRes.getStatusCode());

        return createRes.getBody();
    }
	// delete the created feed
    private void delete(final ODataPubFormat format,final String contentType, final Integer id, final String tag, final  String feed) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment(feed).appendKeySegment(id);

        final ODataDeleteRequest deleteReq = ODataCUDRequestFactory.getDeleteRequest(uriBuilder.build());
       deleteReq.setFormat(format);
       deleteReq.setContentType(contentType);
       if (StringUtils.isNotBlank(tag)) {
    	   deleteReq.setIfMatch(tag); 
       }
        final ODataDeleteResponse deleteRes = deleteReq.execute();
        assertEquals(204, deleteRes.getStatusCode());
    }
    
    // post operation with parameters
    private void boundPostWithParameters(
    		final ODataPubFormat format,
    		final String contentType,
    		final String prefer
    		) {
        final ODataEntity created = createProduct(format,contentType,1905);
        assertNotNull(created);
        final Integer createdId = created.getProperty("ProductId").getPrimitiveValue().<Integer>toCastValue();
        assertNotNull(createdId);
        System.out.println(created.getOperations().get(0));
        final ODataOperation action = created.getOperations().get(0);

        final EdmMetadata metadata =
                ODataRetrieveRequestFactory.getMetadataRequest(testDefaultServiceRootURL).execute().getBody();
        assertNotNull(metadata);
        final EntityContainer container = metadata.getSchema(0).getEntityContainers().get(0);
        final FunctionImport funcImp = container.getFunctionImport(action.getTitle());
        final ODataComplexValue dimensions = new ODataComplexValue(
                "Microsoft.Test.OData.Services.AstoriaDefaultService.Dimensions");       
        dimensions.add(ODataFactory.newPrimitiveProperty("Width",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.DECIMAL).setText("99.11").build()));
        dimensions.add(ODataFactory.newPrimitiveProperty("Height",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.DECIMAL).setText("99.11").build()));
        dimensions.add(ODataFactory.newPrimitiveProperty("Depth",
        new ODataPrimitiveValue.Builder().setType(EdmSimpleType.DECIMAL).setText("99.11").build()));
        
        Map<String,ODataValue> parameters = new LinkedHashMap<String, ODataValue>();
        parameters.put(funcImp.getParameters().get(1).getName(), dimensions);
        
        final ODataInvokeRequest<ODataNoContent> req = ODataInvokeRequestFactory.getInvokeRequest(
                action.getTarget(), metadata, funcImp,parameters);
        req.setFormat(format);
        req.setContentType(contentType);
        req.setPrefer(prefer);
        
        final ODataInvokeResponse<ODataNoContent> res = req.execute();
        assertNotNull(res);
        assertEquals(204, res.getStatusCode());

        
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("Product").appendKeySegment(createdId);
        final ODataEntityRequest retrieveRes = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        retrieveRes.setFormat(format);
        retrieveRes.setContentType(contentType);
        final ODataEntity read = retrieveRes.execute().getBody();

        ODataComplexValue value = read.getProperty("Dimensions").getComplexValue();
        System.out.println(value.get("Depth").getValue());
        assertEquals(dimensions.get("Depth").getValue(),value.get("Depth").getValue());
        assertEquals(dimensions.get("Width").getValue(),value.get("Width").getValue());
        assertEquals(dimensions.get("Height").getValue(),value.get("Height").getValue());
        delete(format,contentType, createdId,created.getETag(),"Product");
        
    }
    // test post operation with parameters and with JSON header
    @Test
    public void invokeOperationParamWithJSON(){
    	final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
    	final String contentType = "application/json;odata=fullmetadata";
    	final String prefer = "return-content";
    	try{
    		boundPostWithParameters(format,contentType,prefer);
    	}catch(Exception e){
    		fail(e.getMessage());
    	}catch(AssertionError e){
    		fail(e.getMessage());
    	}
    }
    // test post operation with parameters and with ATOM header
    @Test
    public void invokeOperationParamWithATOM(){
    	final ODataPubFormat format = ODataPubFormat.ATOM;
    	final String contentType = "application/atom+xml";
    	final String prefer = "return-content";
    	try{
    		boundPostWithParameters(format,contentType,prefer);
    	}catch(Exception e){
    		fail(e.getMessage());
    	}catch(AssertionError e){
    		fail(e.getMessage());
    	}
    }
    // create an employee
    private ODataEntity createEmployee(final ODataPubFormat format, final String contentType, final String prefer, final int id) {
        final ODataEntity employee = ODataFactory.newEntity(
                "Microsoft.Test.OData.Services.AstoriaDefaultService.Employee");
        employee.addProperty(ODataFactory.newPrimitiveProperty("PersonId", new ODataPrimitiveValue.Builder().
                setValue(id).setType(EdmSimpleType.INT_32).build()));
        employee.addProperty(ODataFactory.newPrimitiveProperty("Name", new ODataPrimitiveValue.Builder().
                setText("Test employee").build()));
        employee.addProperty(ODataFactory.newPrimitiveProperty("ManagersPersonId", new ODataPrimitiveValue.Builder().
                setText("1111").setType(EdmSimpleType.INT_32).build()));
        employee.addProperty(ODataFactory.newPrimitiveProperty("Salary", new ODataPrimitiveValue.Builder().
                setText("5999").setType(EdmSimpleType.INT_32).build()));
        employee.addProperty(ODataFactory.newPrimitiveProperty("Title", new ODataPrimitiveValue.Builder().
                setText("Developer").build()));

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("Person");

        final ODataEntityCreateRequest createReq =
                ODataCUDRequestFactory.getEntityCreateRequest(uriBuilder.build(), employee);
        createReq.setFormat(format);
        createReq.setContentType(contentType);
        createReq.setPrefer(prefer);
        final ODataEntityCreateResponse createRes = createReq.execute();
        assertEquals(201, createRes.getStatusCode());
        return createRes.getBody();
    }
    // post operation  
    private void boundPost(
    		final ODataPubFormat format,
    		final String contentType,
    		final String prefer,
    		final int id) {
        final ODataEntity created = createEmployee(format,contentType,prefer,id);
        assertNotNull(created);
        final Integer createdId = created.getProperty("PersonId").getPrimitiveValue().<Integer>toCastValue();
        assertNotNull(createdId);

        final ODataOperation action = created.getOperations().get(0);

        final EdmMetadata metadata =
                ODataRetrieveRequestFactory.getMetadataRequest(testDefaultServiceRootURL).execute().getBody();
        assertNotNull(metadata);

        final EntityContainer container = metadata.getSchema(0).getEntityContainers().get(0);
        final FunctionImport funcImp = container.getFunctionImport(action.getTitle());

        final ODataInvokeRequest<ODataNoContent> req = ODataInvokeRequestFactory.getInvokeRequest(
                action.getTarget(), metadata, funcImp);
        req.setFormat(format);
        req.setContentType(contentType);
        req.setPrefer(prefer);
        final ODataInvokeResponse<ODataNoContent> res = req.execute();
        assertNotNull(res);
        assertEquals(204, res.getStatusCode());

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("Person").appendKeySegment(createdId);
        final ODataEntityRequest retrieveRes = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        retrieveRes.setFormat(format);
        retrieveRes.setContentType(contentType);
        retrieveRes.setPrefer(prefer);
        final ODataEntity read = retrieveRes.execute().getBody();
        assertEquals("0", read.getProperty("Salary").getPrimitiveValue().toString());
        assertTrue(read.getProperty("Title").getPrimitiveValue().toString().endsWith("[Sacked]"));

        delete(format,contentType, createdId,created.getETag(),"Person");
    }
    
 // test post operation without parameters and with JSON header
    @Test
    public void invokeOperationWithJSON(){
    	final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
    	final String contentType = "application/json;odata=fullmetadata";
    	final String prefer = "return-content";
    	try{
    		boundPost(format,contentType,prefer,2222);
    	}catch(Exception e){
    		fail(e.getMessage());
    	}catch(AssertionError e){
    		fail(e.getMessage());
    	}
    }
    // test post operation without parameters and with ATOM header
    @Test
    public void invokeOperationWithATOM(){
    	final ODataPubFormat format = ODataPubFormat.ATOM;
    	final String contentType = "application/atom+xml";
    	final String prefer = "return-content";
    	try{
    		boundPost(format,contentType,prefer,2223);
    	}catch(Exception e){
    		if(e.getMessage().equals("Unsupported media type requested. [HTTP/1.1 415 Unsupported Media Type]")){
    			assertTrue(true);
    		}
    		else{
    			fail(e.getMessage());
    		}
    	}catch(AssertionError e){
    		fail(e.getMessage());
    	}
    }
   // create an entity under feed 'Computer detail'
    private ODataEntity createComputerDetail(final ODataPubFormat format, final String contentType, final String prefer, final int id) {
        	final ODataEntity entity =
	                ODataFactory.newEntity("Microsoft.Test.OData.Services.AstoriaDefaultService.ComputerDetail");
        	entity.addProperty(ODataFactory.newPrimitiveProperty("Manufacturer",
	                new ODataPrimitiveValue.Builder().setText("manufacturer name").setType(EdmSimpleType.STRING).build()));

	        entity.addProperty(ODataFactory.newPrimitiveProperty("ComputerDetailId",
	                new ODataPrimitiveValue.Builder().setText(String.valueOf(id)).setType(EdmSimpleType.INT_32).build()));
	        
	        entity.addProperty(ODataFactory.newPrimitiveProperty("Model",
	                new ODataPrimitiveValue.Builder().setText("Model Name").setType(EdmSimpleType.STRING).build()));
	        
	    final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("ComputerDetail");

        final ODataEntityCreateRequest createReq =
                ODataCUDRequestFactory.getEntityCreateRequest(uriBuilder.build(), entity);
        createReq.setFormat(format);
        createReq.setContentType(contentType);
        createReq.setPrefer(prefer);
        final ODataEntityCreateResponse createRes = createReq.execute();
        assertEquals(201, createRes.getStatusCode());
        return createRes.getBody();
    }
    // post operation with parameters
    private void boundPostWithParametersComputer(
    		final ODataPubFormat format,
    		final String contentType,
    		final String prefer,
    		final int id) {
        final ODataEntity created = createComputerDetail(format,contentType,prefer,id);
        assertNotNull(created);
        final Integer createdId = created.getProperty("ComputerDetailId").getPrimitiveValue().<Integer>toCastValue();
        System.out.println(createdId);
        assertNotNull(createdId);

        final ODataOperation action = created.getOperations().get(0);

        final EdmMetadata metadata =
                ODataRetrieveRequestFactory.getMetadataRequest(testDefaultServiceRootURL).execute().getBody();
        assertNotNull(metadata);

        final EntityContainer container = metadata.getSchema(0).getEntityContainers().get(0);
        final FunctionImport funcImp = container.getFunctionImport(action.getTitle());
        final ODataCollectionValue specification = new ODataCollectionValue(("Collection(Edm.String)"));
        specification.add(new ODataPrimitiveValue.Builder().setType(EdmSimpleType.STRING).setText("specification1").build());
        specification.add(new ODataPrimitiveValue.Builder().setType(EdmSimpleType.STRING).setText("specification2").build());
        specification.add(new ODataPrimitiveValue.Builder().setType(EdmSimpleType.STRING).setText("specification3").build());
        
        ODataValue argument = new ODataPrimitiveValue.Builder().
                setType(EdmSimpleType.DATE_TIME).
                setText("2011-11-11T23:59:59.9999999").
                build();
        Map<String, ODataValue> parameters = new LinkedHashMap<String, ODataValue>();
        parameters.put(funcImp.getParameters().get(1).getName(), specification);
        parameters.put(funcImp.getParameters().get(2).getName(), argument);
        
        final ODataInvokeRequest<ODataNoContent> req = ODataInvokeRequestFactory.getInvokeRequest(
                action.getTarget(), metadata, funcImp,parameters);
        req.setFormat(format);
        req.setContentType(contentType);
        req.setPrefer(prefer);
        final ODataInvokeResponse<ODataNoContent> res = req.execute();
        assertNotNull(res);
        assertEquals(204, res.getStatusCode());

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("ComputerDetail").appendKeySegment(createdId);
        final ODataEntityRequest retrieveRes = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        retrieveRes.setFormat(format);
        retrieveRes.setContentType(contentType);
        retrieveRes.setPrefer(prefer);
        final ODataEntity read = retrieveRes.execute().getBody();
        assertEquals("2011-11-11T23:59:59.9999999",read.getProperty("PurchaseDate").getValue().toString());
        delete(format,contentType, createdId,created.getETag(),"ComputerDetail");
    }
    // test with json
    @Test
    public void boundPostComputerDetailWithJSON(){
    	final ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
    	final String contentType = "application/json;odata=fullmetadata";
    	final String prefer = "return-content";
    	try{
    		boundPostWithParametersComputer(format,contentType,prefer,2235);
    	}catch(Exception e){
    		fail(e.getMessage());
    	}catch(AssertionError e){
    		fail(e.getMessage());
    	}
    }
    // test with atom
    @Test
    public void boundPostComputerDetailWithATOM(){
    	final ODataPubFormat format = ODataPubFormat.ATOM;
    	final String contentType = "application/atom+xml";
    	final String prefer = "return-content";
    	try{
    		boundPostWithParametersComputer(format,contentType,prefer,2235);
    	}catch(Exception e){
    		fail(e.getMessage());
    	}catch(AssertionError e){
    		fail(e.getMessage());
    	}
    }
}
