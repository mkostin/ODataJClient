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

import com.msopentech.odatajclient.engine.client.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataBasicRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import java.net.URI;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;

/**
 * This class implements an OData delete request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getDeleteRequest(com.msopentech.odatajclient.engine.data.ODataURI)
 */
public class ODataDeleteRequest extends ODataBasicRequestImpl<ODataDeleteResponse>
        implements ODataBatchableRequest {

    /**
     * Constructor.
     *
     * @param uri URI of the entity to be deleted.
     */
    ODataDeleteRequest(final URI uri) {
        // set method ... . If cofigured X-HTTP-METHOD header will be used.
        super(Method.DELETE);
        // set uri ...
        this.uri = uri;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataDeleteResponse execute() {
        final WebClient client = WebClient.create(this.uri);
        final Response res = client.
                accept(getContentType()).type(getContentType()).delete();
        return new ODataDeleteResponseImpl(res);
    }

    /**
     * {@inheritDoc }
     * <p>
     * No payload: empty byte array will be returned.
     */
    @Override
    protected byte[] getPayload() {
        return new byte[0];
    }

    private class ODataDeleteResponseImpl extends ODataResponseImpl implements ODataDeleteResponse {

        public ODataDeleteResponseImpl(Response res) {
            super(res);
            res.close();
        }
    }
}
