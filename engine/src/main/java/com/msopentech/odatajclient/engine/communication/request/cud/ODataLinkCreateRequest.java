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
import com.msopentech.odatajclient.engine.communication.request.ODataBasicRequestImpl;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;

/**
 * This class implements an insert link OData request.
 * Get instance by using ODataCUDRequestFactory.
 *
 * @see ODataCUDRequestFactory#getLinkCreateRequest(java.net.URI, com.msopentech.odatajclient.engine.data.ODataLink)
 */
public class ODataLinkCreateRequest extends ODataBasicRequestImpl<ODataLinkOperationResponse, ODataFormat>
        implements ODataBatchableRequest {

    /**
     * OData entity to be linked.
     */
    private final ODataLink link;

    /**
     * Constructor.
     *
     * @param targetURI entity set URI.
     * @param link entity to be linked.
     */
    ODataLinkCreateRequest(final URI targetURI, final ODataLink link) {
        super(HttpMethod.POST, targetURI);
        // set request body
        this.link = link;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ODataLinkOperationResponse execute() {
        final InputStream input = getPayload();
        ((HttpPost) request).setEntity(new InputStreamEntity(input, -1));

        try {
            return new ODataLinkCreateResponseImpl(client, doExecute());
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected InputStream getPayload() {
        return ODataWriter.writeLink(link, ODataFormat.valueOf(getFormat()));
    }

    /**
     * This class implements the response to an OData link operation request.
     */
    private class ODataLinkCreateResponseImpl extends ODataResponseImpl implements ODataLinkOperationResponse {

        /**
         * Constructor.
         * <p>
         * Just to create response templates to be initialized from batch.
         */
        private ODataLinkCreateResponseImpl() {
        }

        /**
         * Constructor.
         *
         * @param client HTTP client.
         * @param res HTTP response.
         */
        private ODataLinkCreateResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }
    }
}
