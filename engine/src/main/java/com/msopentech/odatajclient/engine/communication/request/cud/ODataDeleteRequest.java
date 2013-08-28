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
package com.msopentech.odatajclient.engine.communication.request.cud;

import com.msopentech.odatajclient.engine.client.http.HttpMethod;
import com.msopentech.odatajclient.engine.communication.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataBasicRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.io.InputStream;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData delete request.
 * Get instance by using ODataCUDRequestFactory.
 *
 * @see ODataCUDRequestFactory#getDeleteRequest(java.net.URI)
 */
public class ODataDeleteRequest extends ODataBasicRequestImpl<ODataDeleteResponse, ODataPubFormat>
        implements ODataBatchableRequest {

    /**
     * Constructor.
     *
     * @param method HTTP method to be used
     * @param uri URI of the entity to be deleted.
     */
    ODataDeleteRequest(final HttpMethod method, final URI uri) {
        super(method, uri);
    }

    /**
     * {@inheritDoc }
     * <p>
     * No payload: null will be returned.
     */
    @Override
    protected InputStream getPayload() {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataDeleteResponse execute() {
        return new ODataDeleteResponseImpl(client, doExecute());
    }

    /**
     * Response class about an ODataDeleteRequest.
     */
    private class ODataDeleteResponseImpl extends ODataResponseImpl implements ODataDeleteResponse {

        /**
         * Constructor.
         * <p>
         * Just to create response templates to be initialized from batch.
         */
        private ODataDeleteResponseImpl() {
        }

        /**
         * Constructor.
         *
         * @param client HTTP client.
         * @param res HTTP response.
         */
        private ODataDeleteResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
            this.close();
        }
    }
}
