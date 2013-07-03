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

import com.msopentech.odatajclient.engine.communication.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataBasicRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.types.ODataPubFormat;
import java.io.InputStream;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData delete request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getDeleteRequest(com.msopentech.odatajclient.engine.data.ODataURI)
 */
public class ODataDeleteRequest extends ODataBasicRequestImpl<ODataDeleteResponse, ODataPubFormat>
        implements ODataBatchableRequest {

    /**
     * Constructor.
     *
     * @param uri URI of the entity to be deleted.
     */
    ODataDeleteRequest(final URI uri) {
        // set method ... . If cofigured X-HTTP-METHOD header will be used.
        super(Method.DELETE, uri);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataDeleteResponse execute() {
        final HttpResponse res = doExecute();
        return new ODataDeleteResponseImpl(client, res);
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

    private static class ODataDeleteResponseImpl extends ODataResponseImpl implements ODataDeleteResponse {

        private ODataDeleteResponseImpl() {
        }

        private ODataDeleteResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
            this.close();
        }
    }
}
