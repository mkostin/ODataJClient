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

import java.io.InputStream;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataMediaRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataMediaEntityUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataMediaEntityUpdateRequest.MediaEntityUpdateStreamManager;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataStreamedRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataMediaEntityUpdateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;

public class MediaEntityUpdateTestITCase extends AbstractTest{

	private void updateMediaEntity(
			final ODataPubFormat format,
			final String contentType,
			final String prefer,
			final String image,
			final int id) throws Exception {
        ODataURIBuilder builder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("Car").appendKeySegment(id).appendValueSegment();

        final InputStream input = IOUtils.toInputStream(image);

        final ODataMediaEntityUpdateRequest updateReq =
                ODataStreamedRequestFactory.getMediaEntityUpdateRequest(builder.build(), input);
        updateReq.setFormat(format);
        updateReq.setPrefer(prefer);
        final URI uri = new ODataURIBuilder(testDefaultServiceRootURL).
	            appendEntityTypeSegment("Car").appendKeySegment(id).build();
	    final String etag = getETag(uri);
	    if (StringUtils.isNotBlank(etag)) {
        	updateReq.setIfMatch(etag);
        }
        final MediaEntityUpdateStreamManager streamManager = updateReq.execute();
        final ODataMediaEntityUpdateResponse updateRes = streamManager.getResponse();
        assertEquals(204, updateRes.getStatusCode());
        builder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntityTypeSegment("Car").appendKeySegment(id).appendValueSegment();

        final ODataMediaRequest retrieveReq = ODataRetrieveRequestFactory.getMediaRequest(builder.build());

        final ODataRetrieveResponse<InputStream> retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());
        assertEquals(image, IOUtils.toString(retrieveRes.getBody()));
    }
	// update media with JSON full metadata
	@Test
	public void updateMediaWithJSON(){
		ODataPubFormat format = ODataPubFormat.JSON_FULL_METADATA;
		String contentType = "application/json;odata=fullmetadata";
		String prefer = "return-content";
		String media1 = "../images/big_buck_bunny.mp4";
		String media2 = "../images/renault.jpg";
		String media3 = "../images/image1.png";
		String media4 = "../images/20051210-w50s.flv";		
		int id = 11;
		try{
			updateMediaEntity(format,contentType,prefer,media1,id);
			updateMediaEntity(format,contentType,prefer,media2,id);
			updateMediaEntity(format,contentType,prefer,media3,id);
			updateMediaEntity(format,contentType,prefer,media4,id);
		}catch(Exception e){
			fail(e.getMessage());
		}
		catch(AssertionError e){
			fail(e.getMessage());
		}
	}
	// update media with ATOM
	@Test
	public void updateMediaWithATOM(){
		ODataPubFormat format = ODataPubFormat.ATOM;
		String contentType = "application/atom+xml";
		String prefer = "return-content";
		String media = "../images/big_buck_bunny.mp4";
		int id = 12;
		try{
			updateMediaEntity(format,contentType,prefer,media,id);
		}catch(Exception e){
			fail(e.getMessage());
		}
		catch(AssertionError e){
			fail(e.getMessage());
		}
	}
	// update media with JSON minimla metadata
	@Test
	public void updateMediaWithJSONMinimal(){
		ODataPubFormat format = ODataPubFormat.JSON;
		String contentType = "application/json";
		String prefer = "return-content";
		String media = "../images/big_buck_bunny.mp4";
		int id = 13;
		try{
			updateMediaEntity(format,contentType,prefer,media,id);
		}catch(Exception e){
			fail(e.getMessage());
		}
		catch(AssertionError e){
			fail(e.getMessage());
		}
	}
	// update media with JSON no metadata
	@Test
	public void updateMediaWithJSONNoMetadata(){
		ODataPubFormat format = ODataPubFormat.JSON_NO_METADATA;
		String contentType = "application/json;odata=nometadata";
		String prefer = "return-content";
		String media = "../images/big_buck_bunny.mp4";
		int id = 14;
		try{
			updateMediaEntity(format,contentType,prefer,media,id);
		}catch(Exception e){
			fail(e.getMessage());
		}
		catch(AssertionError e){
			fail(e.getMessage());
		}
	}
}
