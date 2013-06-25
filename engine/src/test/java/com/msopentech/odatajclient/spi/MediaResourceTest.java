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

import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataStreamUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataStreamUpdateRequest.StreamUpdateRequestPayload;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataMediaRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataStreamUpdateResponse;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.Test;

public class MediaResourceTest extends AbstractTest {

    @Test
    public void updateMediaResource() throws Exception {
        ODataURIBuilder builder = new ODataURIBuilder(testODataServiceRootURL);
        builder.appendStructuralSegment("Car(14)").appendActionSegment("Photo").appendStructuralSegment("");

        final String TO_BE_UPDATED = "buffered stream sample";
        final InputStream input = new ByteArrayInputStream(TO_BE_UPDATED.getBytes());

        final ODataStreamUpdateRequest updateReq = ODataRequestFactory.getStreamUpdateRequest(builder.build(), input);

        final StreamUpdateRequestPayload payload = updateReq.execute();
        ODataStreamUpdateResponse updateRes = payload.getResponse();
        updateRes.close();
        assertEquals(204, updateRes.getStatusCode());

        builder = new ODataURIBuilder(testODataServiceRootURL);
        builder.appendStructuralSegment("Car(14)").appendActionSegment("Photo").appendStructuralSegment("");

        final ODataMediaRequest retrieveReq = ODataRetrieveRequestFactory.getMediaRequest(builder.build());

        final ODataQueryResponse<InputStream> retrieveRes = retrieveReq.execute();
        assertEquals(200, retrieveRes.getStatusCode());

        final StringBuilder resBuilder = new StringBuilder();

        InputStream ii = retrieveRes.getBody();
        byte[] buff = new byte[1024];
        int length = 0;

        while ((length = ii.read(buff)) >= 0) {
            resBuilder.append(new String(buff, 0, length, "UTF-8"));
        }

        assertEquals(TO_BE_UPDATED, resBuilder.toString().trim());
    }
}
