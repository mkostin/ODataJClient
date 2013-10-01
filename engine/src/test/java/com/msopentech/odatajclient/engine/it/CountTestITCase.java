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
import org.junit.Test;

import com.msopentech.odatajclient.engine.communication.ODataClientErrorException;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataValueRequest;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.format.ODataValueFormat;


public class CountTestITCase extends AbstractTest{
	//counts the total number of customers
	@Test
	public void entityCount() {
		System.out.println(testDefaultServiceRootURL);
		ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendEntityTypeSegment("Customer").appendCountSegment();
		final ODataValueRequest req = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
		req.setFormat(ODataValueFormat.TEXT);
		try{
			final ODataValue value = req.execute().getBody();
			assertEquals(10,Integer.parseInt(value.toString()));
		}catch(ODataClientErrorException e){
			System.out.println(e.getStatusLine().getStatusCode());
		}
	}
	//returns 415 error for invalid header.
	@Test
	public void invalidAccept() {
		final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
		appendEntityTypeSegment("Customer").appendCountSegment();
		System.out.println(uriBuilder.build());
		final ODataValueRequest req = ODataRetrieveRequestFactory.getValueRequest(uriBuilder.build());
		req.setFormat(ODataValueFormat.TEXT);
		req.setAccept("application/json;odata=fullmetadata");
		try{
			final ODataValue value = req.execute().getBody();
			System.out.println(value);
		}catch(ODataClientErrorException e){
			assertEquals(415,e.getStatusLine().getStatusCode());
		}
	}
}
