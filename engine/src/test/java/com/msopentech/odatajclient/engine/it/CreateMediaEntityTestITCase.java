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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataDeleteRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataMediaRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataMediaEntityCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataMediaEntityCreateRequest.MediaEntityCreateStreamManager;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataStreamedRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityUpdateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataMediaEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.format.ODataMediaFormat;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;

public class CreateMediaEntityTestITCase extends AbstractTest{

	// create media entity with json
	@Test
	public void createMediaWithJSON() {
		ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA ;
		String contentType = "application/json;odata=fullmetadata";
		String prefer = "return-content";
		String fileType = "../images/image1.jpg";
		try {
			createMediaEntity(format, contentType, prefer, fileType);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	// with png image
	@Test
	public void createMediaWithJSONAndPng() {
		ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA ;
		String contentType = "application/json;odata=fullmetadata";
		String prefer = "return-content";
		String fileType = "../images/image1.png";
		try {
			createMediaEntity(format, contentType, prefer, fileType);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	// with a long image
	@Test
	public void createMediaWithJSONBigImage() {
		ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA ;
		String contentType = "application/json;odata=fullmetadata";
		String prefer = "return-content";
		String fileType = "../images/renault.jpg";
		try {
			createMediaEntity(format, contentType, prefer, fileType);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	// create media entity with json no metadata.
	@Test
	public void createMediaWithJSONNoMetadata() {
		ODataPubFormat format = ODataPubFormat.JSON_NO_METADATA ;
		String contentType = "application/json;odata=nometadata";
		String prefer = "return-content";
		String fileType = "../images/image1.jpg";
		try {
			createMediaEntity(format, contentType, prefer, fileType);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	// with JSON minimal meta data
	@Test
	public void createMediaWithJSONMinimalMetadata() {
		ODataPubFormat format = ODataPubFormat.JSON ;
		String contentType = "application/json";
		String prefer = "return-content";
		String fileType = "../images/image1.jpg";
		try {
			createMediaEntity(format, contentType, prefer, fileType);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	// create media with atom 
	@Test
	public void createMediaWithATOM() {
		ODataPubFormat format = ODataPubFormat.ATOM ;
		String contentType = "application/atom+xml";
		String prefer = "return-content";
		String fileType = "../images/image1.png";
		try {
			createMediaEntity(format, contentType, prefer, fileType);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	// create video with atom
	@Test
	public void createVideoWithATOM() {
		ODataPubFormat format = ODataPubFormat.ATOM ;
		String contentType = "application/atom+xml";
		String prefer = "return-content";
		String fileType = "../images/big_buck_bunny.mp4";
		try {
			createMediaEntity(format, contentType, prefer, fileType);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	// create video with json
	@Test
	public void createVideoWithJSON() {
		ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA ;
		String contentType = "application/json;odata=fullmetadata";
		String prefer = "return-content";
		String fileType = "../images/big_buck_bunny.mp4";
		try {
			createMediaEntity(format, contentType, prefer, fileType);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	// code to create a media entity. 
	// Step 1 - POST an entity
	// Step 2 - PUT other properties in that entity.
	public void createMediaEntity(final ODataPubFormat format, final String contentType, final String prefer, final String fileType) throws IOException{
		try{
			final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).appendEntitySetSegment("Car");
			InputStream  inputStream = new FileInputStream(fileType);
			final ODataMediaEntityCreateRequest createReq =
		                ODataStreamedRequestFactory.getMediaEntityCreateRequest(uriBuilder.build(), inputStream);
			createReq.setFormat(format);
			createReq.setContentType(contentType);
	        createReq.setPrefer(prefer);
	        final MediaEntityCreateStreamManager streamManager = createReq.execute();
	        final ODataMediaEntityCreateResponse createRes = streamManager.getResponse();
	        if(prefer.equals("return-content")){
	        	System.out.println(createRes.getStatusCode());
		        assertEquals(201,createRes.getStatusCode());
		        
		        final ODataEntity createdEntity = createRes.getBody();
		        assertNotNull(createdEntity);
		       assertEquals(2,createdEntity.getProperties().size());
		        System.out.println(createdEntity.getProperties().size());
		        // get the vin property of the entity created
		        final int id = "VIN".equals(createdEntity.getProperties().get(0).getName())
		                ? createdEntity.getProperties().get(0).getPrimitiveValue().<Integer>toCastValue()
		                : createdEntity.getProperties().get(1).getPrimitiveValue().<Integer>toCastValue();
		        System.out.println("Id - "+id);
		        uriBuilder.appendKeySegment(id).appendValueSegment();
		        // get the stream value that got created  
		        final ODataMediaRequest retrieveReq = ODataRetrieveRequestFactory.getMediaRequest(uriBuilder.build());
		        retrieveReq.setFormat(ODataMediaFormat.WILDCARD);
		        final ODataRetrieveResponse<InputStream> retrieveRes = retrieveReq.execute();
		        assertEquals(200,retrieveRes.getStatusCode());
			    assertNotNull(retrieveRes.getBody());
			    String etag = retrieveRes.getEtag();
		        System.out.println("Tag "+etag);
		        //System.out.println(IOUtils.toString(retrieveRes.getBody()));
		        // get the entity created 
		        final ODataEntity entity =
		                ODataFactory.newEntity("Microsoft.Test.OData.Services.AstoriaDefaultService.Car");
		        
		        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(new ODataURIBuilder(testDefaultServiceRootURL).
		                appendEntityTypeSegment("Car").appendKeySegment(id).build());
		        req.setFormat(format);
	
		        final ODataRetrieveResponse<ODataEntity> res = req.execute();
		        final ODataEntity entityToBeUpdated = res.getBody();
		        assertNotNull(entity);		        
		        String propertyName = "Description";
		        final String newValue = "New renault car - " + propertyName;
	
		        ODataProperty propertyValue = entityToBeUpdated.getProperty(propertyName);
	
		        if (propertyValue != null) {
		        	entityToBeUpdated.removeProperty(propertyValue);
		        }
		        // add new value for the property
		        entityToBeUpdated.addProperty(ODataFactory.newPrimitiveProperty(propertyName,
		        new ODataPrimitiveValue.Builder().setText(newValue).setType(EdmSimpleType.STRING).build()));
		        
		        UpdateType type = UpdateType.REPLACE;
		        // update the entity in the server
		        ODataEntityUpdateRequest updateReq = ODataCUDRequestFactory.getEntityUpdateRequest(type, entityToBeUpdated);
		        req.setFormat(format);
		        if (StringUtils.isNotBlank(etag)) {
		            req.setIfMatch(etag); 
		        }
		        ODataEntityUpdateResponse updateRes = updateReq.execute();
		        assertEquals(204,updateRes.getStatusCode());
		        
		        final ODataEntityRequest afterUpdateReq = ODataRetrieveRequestFactory.getEntityRequest(new ODataURIBuilder(testDefaultServiceRootURL).
		                appendEntityTypeSegment("Car").appendKeySegment(id).build());
		        
		        req.setFormat(format);
		        //assert whether the original value is equal to the actual value that got updated.
		        final ODataRetrieveResponse<ODataEntity> afterUpdateRes = afterUpdateReq.execute();
		        final ODataEntity entityAfterUpdate = afterUpdateRes.getBody();
		        System.out.println(entityAfterUpdate.getProperty("Description").getValue());
		        assertEquals(newValue,entityAfterUpdate.getProperty("Description").getValue().toString());
		        
		        // delete the entity
		        ODataURIBuilder deleteUriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		        		appendEntityTypeSegment("Car("+id+")");
		        ODataDeleteRequest deleteReq = ODataCUDRequestFactory.getDeleteRequest(deleteUriBuilder.build());
		        deleteReq.setFormat(format);
		        deleteReq.setContentType(contentType);
		        ODataDeleteResponse deleteRes = deleteReq.execute();
		        assertEquals(204,deleteRes.getStatusCode());
	        }
	        else{
	        	assertEquals(204,createRes.getStatusCode());
	        }
		} catch(Exception e){
			if ((format.equals(ODataPubFormat.JSON) || (format.equals(ODataPubFormat.JSON_NO_METADATA))  && e.getMessage().equals("No edit link found")) ){
				assertTrue(true);
			}
			else{
				fail(e.getMessage());
			}
		}catch(AssertionError e){
			fail(e.getMessage());
		}
	}
}
