/*
 * Copyright 2013 MS OpenTech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msopentech.odatajclient.spi;

import static com.msopentech.odatajclient.spi.AbstractTest.testODataServiceRootURL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import com.msopentech.odatajclient.engine.communication.ODataClientErrorException;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataMediaRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataMediaEntityCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataMediaEntityCreateRequest.MediaEntityCreateStreamManager;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataMediaEntityUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataMediaEntityUpdateRequest.MediaEntityUpdateStreamManager;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataStreamUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataStreamUpdateRequest.StreamUpdateStreamManager;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataStreamedRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataMediaEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataMediaEntityUpdateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataStreamUpdateResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.format.ODataMediaFormat;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class MediaEntityTest extends AbstractTest {

    @Test
    public void readMediaEntity() throws Exception {
        final ODataURIBuilder builder = new ODataURIBuilder(testODataServiceRootURL).
                appendEntityTypeSegment("Car(12)").appendValueSegment();

        final ODataMediaRequest retrieveReq = ODataRetrieveRequestFactory.getMediaRequest(builder.build());
        retrieveReq.setFormat(ODataMediaFormat.WILDCARD);

        final ODataRetrieveResponse<InputStream> retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());
        assertTrue(IOUtils.toString(retrieveRes.getBody()).isEmpty());
    }

    @Test(expected = ODataClientErrorException.class)
    public void readMediaWithXmlError() throws Exception {
        final ODataURIBuilder builder = new ODataURIBuilder(testODataServiceRootURL).
                appendEntityTypeSegment("Car(12)").appendValueSegment();

        final ODataMediaRequest retrieveReq = ODataRetrieveRequestFactory.getMediaRequest(builder.build());
        retrieveReq.setFormat(ODataMediaFormat.APPLICATION_OCTET_STREAM);

        retrieveReq.execute();
    }

    @Test(expected = ODataClientErrorException.class)
    public void readMediaWithJsonError() throws Exception {
        final ODataURIBuilder builder = new ODataURIBuilder(testODataServiceRootURL).
                appendEntityTypeSegment("Car(12)").appendValueSegment();

        final ODataMediaRequest retrieveReq = ODataRetrieveRequestFactory.getMediaRequest(builder.build());
        retrieveReq.setFormat(ODataMediaFormat.APPLICATION_JSON);

        retrieveReq.execute();
    }

    @Test
    public void updateMediaEntityAsAtom() throws Exception {
        updateMediaEntity(ODataPubFormat.ATOM, 14);
    }

    @Test
    public void updateMediaEntityAsJson() throws Exception {
        updateMediaEntity(ODataPubFormat.JSON, 15);
    }

    @Test
    public void createMediaEntityAsAtom() throws Exception {
        createMediaEntity(ODataPubFormat.ATOM);
    }

    @Test
    public void createMediaEntityAsJson() throws Exception {
        createMediaEntity(ODataPubFormat.JSON);
    }

    @Test
    public void updateNamedStream() throws Exception {
        ODataURIBuilder builder = new ODataURIBuilder(testODataServiceRootURL).
                appendEntityTypeSegment("Car(16)").appendStructuralSegment("Photo");

        final String TO_BE_UPDATED = "buffered stream sample";
        final InputStream input = new ByteArrayInputStream(TO_BE_UPDATED.getBytes());

        final ODataStreamUpdateRequest updateReq =
                ODataStreamedRequestFactory.getStreamUpdateRequest(builder.build(), input);

        final StreamUpdateStreamManager streamManager = updateReq.execute();
        final ODataStreamUpdateResponse updateRes = streamManager.getResponse();
        updateRes.close();
        assertEquals(204, updateRes.getStatusCode());

        final ODataMediaRequest retrieveReq = ODataRetrieveRequestFactory.getMediaRequest(builder.build());

        final ODataRetrieveResponse<InputStream> retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());
        assertEquals(TO_BE_UPDATED, IOUtils.toString(retrieveRes.getBody()));
    }

    private void updateMediaEntity(final ODataPubFormat format, final int id) throws Exception {
        ODataURIBuilder builder = new ODataURIBuilder(testODataServiceRootURL);
        builder.appendEntitySetSegment("Car(" + id + ")").appendValueSegment();

        final String TO_BE_UPDATED = "new buffered stream sample";
        final InputStream input = IOUtils.toInputStream(TO_BE_UPDATED);

        final ODataMediaEntityUpdateRequest updateReq =
                ODataStreamedRequestFactory.getMediaEntityUpdateRequest(builder.build(), input);
        updateReq.setFormat(format);

        final MediaEntityUpdateStreamManager streamManager = updateReq.execute();
        final ODataMediaEntityUpdateResponse updateRes = streamManager.getResponse();
        assertEquals(204, updateRes.getStatusCode());

        builder = new ODataURIBuilder(testODataServiceRootURL).
                appendEntityTypeSegment("Car(" + id + ")").appendValueSegment();

        final ODataMediaRequest retrieveReq = ODataRetrieveRequestFactory.getMediaRequest(builder.build());

        final ODataRetrieveResponse<InputStream> retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());
        assertEquals(TO_BE_UPDATED, IOUtils.toString(retrieveRes.getBody()));
    }

    private void createMediaEntity(final ODataPubFormat format) throws Exception {
        ODataURIBuilder builder = new ODataURIBuilder(testODataServiceRootURL);
        builder.appendEntitySetSegment("Car");

        final String TO_BE_UPDATED = "buffered stream sample";
        final InputStream input = IOUtils.toInputStream(TO_BE_UPDATED);

        final ODataMediaEntityCreateRequest createReq =
                ODataStreamedRequestFactory.getMediaEntityCreateRequest(builder.build(), input);
        createReq.setFormat(format);

        final MediaEntityCreateStreamManager streamManager = createReq.execute();
        final ODataMediaEntityCreateResponse createRes = streamManager.getResponse();
        assertEquals(201, createRes.getStatusCode());

        final ODataEntity created = createRes.getBody();
        assertNotNull(created);
        assertEquals(2, created.getProperties().size());

        final int id = "VIN".equals(created.getProperties().get(0).getName())
                ? created.getProperties().get(0).getPrimitiveValue().<Integer>toCastValue()
                : created.getProperties().get(1).getPrimitiveValue().<Integer>toCastValue();

        builder = new ODataURIBuilder(testODataServiceRootURL);
        builder.appendEntityTypeSegment("Car(" + id + ")").appendValueSegment();

        final ODataMediaRequest retrieveReq = ODataRetrieveRequestFactory.getMediaRequest(builder.build());

        final ODataRetrieveResponse<InputStream> retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());
        assertEquals(TO_BE_UPDATED, IOUtils.toString(retrieveRes.getBody()));
    }
}
