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

import com.msopentech.odatajclient.engine.communication.request.ODataBasicRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataLinkOperationResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataWriter;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import java.io.InputStream;
import java.net.URI;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.InputStreamEntity;

/**
 * This class implements an update link OData request.
 * It encapsulates two different request: the former remove link request and the latter add link request.
 * <p>
 * Get instance by using ODataCUDRequestFactory.
 *
 * @see ODataCUDRequestFactory#getLinkUpdateRequest(java.net.URI,
 * com.msopentech.odatajclient.engine.communication.request.UpdateType,
 * com.msopentech.odatajclient.engine.data.ODataLink)
 */
public class ODataLinkUpdateRequest extends ODataBasicRequestImpl<ODataLinkOperationResponse, ODataFormat>
        implements ODataBatchableRequest {

    /**
     * Entity to be linked.
     */
    private final ODataLink link;

    /**
     * Constructor.
     *
     * @param targetURI entity URI.
     * @param linkToBeRemoved link to be removed.
     * @param link entity to be linked.
     */
    ODataLinkUpdateRequest(final URI targetURI, final UpdateType type, final ODataLink link) {
        // set method ... . If cofigured X-HTTP-METHOD header will be used.
        super(type.getMethod(), targetURI);
        // set request body
        this.link = link;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataLinkOperationResponse execute() {
        final InputStream input = getPayload();
        ((HttpEntityEnclosingRequestBase) request).setEntity(new InputStreamEntity(input, -1));

        try {
            final HttpResponse res = doExecute();
            return new ODataLinkUpdateResponseImpl(client, res);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected InputStream getPayload() {
        return ODataWriter.writeLink(link, ODataFormat.valueOf(getFormat()));
    }

    /**
     * This class implements the response to an OData link operation request.
     */
    public class ODataLinkUpdateResponseImpl extends ODataResponseImpl implements ODataLinkOperationResponse {

        public ODataLinkUpdateResponseImpl() {
        }

        public ODataLinkUpdateResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }
    }
}
