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

import java.util.List;
import org.junit.Test;

import com.msopentech.odatajclient.engine.communication.ODataClientErrorException;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntitySetRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataCollectionValue;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;


public class FunctionsTestITCase extends AbstractTest{
	//function returns a reference 
	private void refReturnFunction(final ODataPubFormat format, final String accept) {
		final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendEntitySetSegment("GetSpecificCustomer?CustomerId=-8");
		final ODataEntitySetRequest req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
		req.setFormat(format);
		req.setAccept(accept);
		try{
			final ODataRetrieveResponse<ODataEntitySet> res = req.execute();
			assertEquals(200,res.getStatusCode());
			final ODataEntitySet entitySet = res.getBody();
			assertNotNull(res.getBody());
			final List<ODataEntity> entity = entitySet.getEntities();
			assertEquals(1, entity.size());
			if(accept.equals("application/json;odata-fullmetadata") ||
			accept.equals("application/json;odata-minimalmetadata")){
				assertEquals("Microsoft.Test.OData.Services.AstoriaDefaultService.Customer",
						entity.get(0).getName());
			}
		}catch(ODataClientErrorException e){
			if(e.getStatusLine().getStatusCode() == 415){
				assertEquals(415,e.getStatusLine().getStatusCode() );
			}
			if(e.getStatusLine().getStatusCode() == 404){
				assertEquals(404,e.getStatusLine().getStatusCode() );
			}
			if(e.getStatusLine().getStatusCode() == 404){
				assertEquals(400,e.getStatusLine().getStatusCode() );
			}
		}
	}
	//CustomerIds is an invalid query ID. Returns ODataClientErrorException
	private void withInvalidQuery(final ODataPubFormat format, final String accept) {
		final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendEntitySetSegment("GetSpecificCustomer?CustomerIds=-10");
		final ODataEntitySetRequest req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
		req.setFormat(format);
		req.setAccept(accept);
		try{
			final ODataRetrieveResponse<ODataEntitySet> res = req.execute();
			assertEquals(200,res.getStatusCode());
			final ODataEntitySet entitySet = res.getBody();
			assertNotNull(res.getBody());
			final List<ODataEntity> entity = entitySet.getEntities();
			assertEquals(1, entity.size());
		}catch(ODataClientErrorException e){
			if(e.getStatusLine().getStatusCode() == 415){
				assertEquals(415,e.getStatusLine().getStatusCode() );
			}
			if(e.getStatusLine().getStatusCode() == 404){
				assertEquals(404,e.getStatusLine().getStatusCode() );
			}
			if(e.getStatusLine().getStatusCode() == 404){
				assertEquals(400,e.getStatusLine().getStatusCode() );
			}
		}
	}
	//function returning collection of complex types
	private void collectionFunction(final ODataPubFormat format, final String accept) {
		final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendEntitySetSegment("EntityProjectionReturnsCollectionOfComplexTypes");
		System.out.println(uriBuilder.build());
		final ODataEntitySetRequest req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
		req.setFormat(format);
		req.setAccept(accept);
		try{
			final ODataRetrieveResponse<ODataEntitySet> res = req.execute();
			final ODataEntitySet entitySet = res.getBody();
			final List<ODataEntity> entity = entitySet.getEntities();
			assertEquals(entity.size(),10);
			for(int i=0; i<entity.size(); i++){
				List<ODataProperty> prop = entity.get(i).getProperties();
				for(int j=0;j<prop.size();j++){
					ODataProperty property = prop.get(j);
					if (property.hasPrimitiveValue()) {
						final ODataPrimitiveValue value = property.getPrimitiveValue();
			        	assertTrue(value.isPrimitive());	
			        } 
			        else if (property.hasComplexValue()) {
			        	final ODataComplexValue value = property.getComplexValue();
			        	assertTrue(value.isComplex());
			        } 
			        else if (property.hasCollectionValue()) {
			        	final ODataCollectionValue value = property.getCollectionValue();
			        	assertTrue(value.isCollection());
			        }
				}
			}
		}catch(ODataClientErrorException e){
			if(e.getStatusLine().getStatusCode() == 415){
				assertEquals(415,e.getStatusLine().getStatusCode() );
			}
			else if(e.getStatusLine().getStatusCode() == 404){
				assertEquals(404,e.getStatusLine().getStatusCode() );
			}
			else if(e.getStatusLine().getStatusCode() == 400){
				assertEquals(400,e.getStatusLine().getStatusCode() );
			}
		}
	}
	//with atom header
	@Test
	public void atomTest(){
		refReturnFunction(ODataPubFormat.ATOM, "application/atom+xml");
		withInvalidQuery(ODataPubFormat.ATOM, "application/atom+xml");
		collectionFunction(ODataPubFormat.ATOM, "application/atom+xml");
	}
	//with json header
	@Test
	public void jsonTest(){
		refReturnFunction(ODataPubFormat.JSON, "application/json");
		withInvalidQuery(ODataPubFormat.JSON, "application/json");
		collectionFunction(ODataPubFormat.JSON, "application/json");
	}
	//with json header full metadata
	@Test
	public void jsonFullMetadataTest(){
		refReturnFunction(ODataPubFormat.JSON_FULL_METADATA, "application/json;odata=fullmetadata");
		collectionFunction(ODataPubFormat.JSON_FULL_METADATA, "application/json;odata=fullmetadata");
	}
	//with json header no metadata
	@Test
	public void jsonNoMetadataTest(){
		refReturnFunction(ODataPubFormat.JSON_NO_METADATA, "application/json;odata=nometadata");
		collectionFunction(ODataPubFormat.JSON_NO_METADATA, "application/json;odata=nometadata");
	}
	//with json header minimal metadata
	@Test(expected = IllegalArgumentException.class)
	public void jsonMinimalMetadataTest(){
		refReturnFunction(ODataPubFormat.JSON, "application/json;odata=minimal");
		collectionFunction(ODataPubFormat.JSON, "application/json;odata=minimal");
		withInvalidQuery(ODataPubFormat.JSON, "application/json;odata=minimal");
	}
	//with accept type as xml
	@Test(expected = NullPointerException.class)
	public void xmlTest(){
		collectionFunction(ODataPubFormat.ATOM, "application/xml");
	}
	//with null accept header and atom format
	@Test(expected = IllegalArgumentException.class)
	public void nullAcceptTest(){
		withInvalidQuery(ODataPubFormat.ATOM, null);
		collectionFunction(ODataPubFormat.ATOM, null);
		withInvalidQuery(ODataPubFormat.ATOM, null);
	}
	//with null accept header and json format
	@Test
	public void nullAcceptWithJSONTest(){
		withInvalidQuery(ODataPubFormat.JSON, null);
		collectionFunction(ODataPubFormat.JSON, null);
		withInvalidQuery(ODataPubFormat.JSON, null);
	}
	//null pub format
	@Test(expected = NullPointerException.class)
	public void nullFormatTest(){
		withInvalidQuery(null, "application/json");
		collectionFunction(null, "application/json");
		withInvalidQuery(null, "application/json");
	}
	
}
