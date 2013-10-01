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
import java.io.IOException;
import org.junit.Test;

import com.msopentech.odatajclient.engine.communication.ODataClientErrorException;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataValueRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.format.ODataValueFormat;


public class PropertyValueTestITCase extends AbstractTest{
	//retrieves Edm.Int32
	@Test
	public  void retreiveIntPropertyValueTest() {
		ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendEntityTypeSegment("Product").appendKeySegment(-10).appendStructuralSegment("ProductId").appendValueSegment();
		final ODataValueRequest req = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
		req.setFormat(ODataValueFormat.TEXT);
		final ODataValue value = req.execute().getBody();
		assertNotNull(value);
		assertEquals(-10,Integer.parseInt(value.toString()));
	}
	//retrieves boolean
	@Test
	public  void retreiveBooleanPropertyValueTest() {
		ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendEntityTypeSegment("Product").appendKeySegment(-10).appendStructuralSegment("ProductId").appendValueSegment();
		final ODataValueRequest req = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
		req.setFormat(ODataValueFormat.TEXT);
		final ODataValue value = req.execute().getBody();
		assertNotNull(value);
		assertEquals(-10,Integer.parseInt(value.toString()));
	}
	//retrieves Edm.String 
	@Test
	public  void retreiveStringPropertyValueTest() {
		ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendEntityTypeSegment("Product").appendKeySegment(-10).appendStructuralSegment("Description").appendValueSegment();
		final ODataValueRequest req = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
		req.setFormat(ODataValueFormat.TEXT);
		final ODataValue value = req.execute().getBody();
		assertNotNull(value);
		assertEquals("onesusjnzuzrmzhqankkugdrftiukzkzqaggsfdmtvineulehkrbpu",value.toString());
	}
	
	// date from a complex structure. 
	@Test
	public  void retreiveDatePropertyValueTest() {
		ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendEntityTypeSegment("Product").appendKeySegment(-10).appendStructuralSegment("NestedComplexConcurrency/ModifiedDate").appendValueSegment();
		//System.out.println(uriBuilder.build());
		final ODataValueRequest req = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
		req.setFormat(ODataValueFormat.TEXT);
		final ODataValue value = req.execute().getBody();
		assertNotNull(value);
		assertEquals("9999-12-31T23:59:59.9999999",value.toString());
	}
	//decimal type test
	@Test
	public  void retreiveDecimalPropertyValueTest() {
		ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendEntityTypeSegment("Product").appendKeySegment(-10).appendStructuralSegment("Dimensions/Height").appendValueSegment();
		//System.out.println(uriBuilder.build());
		final ODataValueRequest req = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
		req.setFormat(ODataValueFormat.TEXT);
		final ODataValue value = req.execute().getBody();
		assertNotNull(value);
		assertEquals("-0.492988348718789",value.toString());
	}
	//binary test with json format 
	@Test
	public  void retreiveBinaryPropertyValueTest() throws IOException {
		 ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendNavigationLinkSegment("ProductPhoto(PhotoId=-3,ProductId=-3)").appendStructuralSegment("Photo");
		ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
		req.setAccept("application/json");
		System.out.println(uriBuilder.build());
		ODataRetrieveResponse<ODataEntity> res = req.execute();
		assertEquals(200, res.getStatusCode());
		ODataEntity entitySet = res.getBody();
		assertNotNull(entitySet);
		assertEquals("fi653p3+MklA/LdoBlhWgnMTUUEo8tEgtbMXnF0a3CUNL9BZxXpSRiD9ebTnmNR0zWPjJVIDx4tdmCnq55XrJh+RW9aI/b34wAogK3kcORw=",
				entitySet.getProperties().get(0).getValue().toString());
	}
	//binary test with atom format.
	@Test(expected = ODataClientErrorException.class)
	public  void retreiveBinaryPropertyValueTestWithAtom() throws IOException {
		 ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendNavigationLinkSegment("ProductPhoto(PhotoId=-3,ProductId=-3)").appendStructuralSegment("Photo");
		ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
		req.setAccept("application/atom+xml");
		System.out.println(uriBuilder.build());
		ODataRetrieveResponse<ODataEntity> res = req.execute();
		assertEquals(200, res.getStatusCode());
		ODataEntity entitySet = res.getBody();
		assertNotNull(entitySet);
		assertEquals("fi653p3+MklA/LdoBlhWgnMTUUEo8tEgtbMXnF0a3CUNL9BZxXpSRiD9ebTnmNR0zWPjJVIDx4tdmCnq55XrJh+RW9aI/b34wAogK3kcORw=",
				entitySet.getProperties().get(0).getValue().toString());
	}
	// binary data with xml. Unable to deserialize JSON
	@Test(expected = IllegalArgumentException.class)
	public  void retreiveBinaryPropertyValueTestWithXML() throws IOException {
		 ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendNavigationLinkSegment("ProductPhoto(PhotoId=-3,ProductId=-3)").appendStructuralSegment("Photo");
		ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
		req.setAccept("application/xml");
		System.out.println(uriBuilder.build());
		ODataRetrieveResponse<ODataEntity> res = req.execute();
		assertEquals(200, res.getStatusCode());
		ODataEntity entitySet = res.getBody();
		assertNotNull(entitySet);
		assertEquals("fi653p3+MklA/LdoBlhWgnMTUUEo8tEgtbMXnF0a3CUNL9BZxXpSRiD9ebTnmNR0zWPjJVIDx4tdmCnq55XrJh+RW9aI/b34wAogK3kcORw=",
				entitySet.getProperties().get(0).getValue().toString());
	}
	//collection of primitives
	@Test
	public  void retreiveCollectionPropertyValueTest() {
		ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendEntityTypeSegment("Product").appendKeySegment(-10).appendStructuralSegment("ComplexConcurrency/QueriedDateTime").appendValueSegment();
		System.out.println(uriBuilder.build());
		final ODataValueRequest req = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
		req.setFormat(ODataValueFormat.TEXT);
		final ODataValue value = req.execute().getBody();
		if(value.isPrimitive()){
			assertNotNull(value);
			assertEquals("2013-01-10T06:27:51.1667673",value.toString());
		}
	}
	// No resource found error. Token is not available under ComplexConcurrency
	@Test
	public  void retreiveNullPropertyValueTest() {
		ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendEntityTypeSegment("Product").appendKeySegment(-10).appendStructuralSegment("ComplexConcurrency/Token").appendValueSegment();
		System.out.println(uriBuilder.build());
		final ODataValueRequest req = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
		try{
			final ODataValue value = req.execute().getBody();
			System.out.println(value);
		}catch(ODataClientErrorException e){
			assertEquals(404,e.getStatusLine().getStatusCode());
		}
	}
}
