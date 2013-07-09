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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.msopentech.odatajclient.engine.communication.ODataClientErrorException;
import com.msopentech.odatajclient.engine.communication.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataBasicRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.junit.Test;

/**
 * This is the unit test class to check basic entity operations.
 */
public class ErrorTest extends AbstractTest {

    private class ErrorGeneratingRequest extends ODataBasicRequestImpl<ODataEntityCreateResponse, ODataPubFormat> {

        public ErrorGeneratingRequest(final Method method, final URI uri) {
            super(method, uri);
        }

        @Override
        protected InputStream getPayload() {
            return new ByteArrayInputStream(new byte[0]);
        }

        @Override
        public ODataEntityCreateResponse execute() {
            final HttpResponse res = doExecute();
            return new ODataEntityCreateResponseImpl(client, res);
        }

        private class ODataEntityCreateResponseImpl extends ODataResponseImpl implements ODataEntityCreateResponse {

            public ODataEntityCreateResponseImpl(final HttpClient client, final HttpResponse res) {
                super(client, res);
            }

            @Override
            public ODataEntity getBody() {
                return new ODataEntity("Invalid") {

                    private static final long serialVersionUID = 5538299138211283825L;

                };
            }
        }
    }

    private void stacktraceError(final ODataPubFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("Customer");

        final ErrorGeneratingRequest errorReq =
                new ErrorGeneratingRequest(ODataRequest.Method.POST, uriBuilder.build());
        errorReq.setFormat(format);

        ODataClientErrorException ocee = null;
        try {
            errorReq.execute();
        } catch (ODataClientErrorException e) {
            LOG.error("ODataClientErrorException found", e);
            ocee = e;
        }
        assertNotNull(ocee);
        assertEquals(400, ocee.getStatusLine().getStatusCode());
        assertNotNull(ocee.getCause());
        assertNotNull(ocee.getODataError());
    }

    @Test
    public void xmlStacktraceError() {
        stacktraceError(ODataPubFormat.ATOM);
    }

    @Test
    public void jsonStacktraceError() {
        stacktraceError(ODataPubFormat.JSON);
    }

    private void notfoundError(final ODataPubFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("Customer(154)");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        ODataClientErrorException ocee = null;
        try {
            req.execute();
        } catch (ODataClientErrorException e) {
            LOG.error("ODataClientErrorException found", e);
            ocee = e;
        }
        assertNotNull(ocee);
        assertEquals(404, ocee.getStatusLine().getStatusCode());
        assertNull(ocee.getCause());
        assertNotNull(ocee.getODataError());
    }

    @Test
    public void xmlNotfoundError() {
        notfoundError(ODataPubFormat.ATOM);
    }

    @Test
    public void jsonNotfoundError() {
        notfoundError(ODataPubFormat.JSON);
    }
}
