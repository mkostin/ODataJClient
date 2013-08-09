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
import com.msopentech.odatajclient.engine.communication.response.ODataEntityUpdateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataWriter;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.io.InputStream;
import java.net.URI;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.InputStreamEntity;

/**
 * This class implements an OData update request.
 * Get instance by using ODataCUDRequestFactory.
 *
 * @see ODataCUDRequestFactory#getEntityUpdateRequest(java.net.URI,
 * com.msopentech.odatajclient.engine.communication.request.UpdateType,
 * com.msopentech.odatajclient.engine.data.ODataEntity)
 */
public class ODataEntityUpdateRequest extends ODataBasicRequestImpl<ODataEntityUpdateResponse, ODataPubFormat>
        implements ODataBatchableRequest {

    /**
     * Changes to be applied.
     */
    private final ODataEntity changes;

    /**
     * Constructor.
     *
     * @param method request method.
     * @param uri URI of the entity to be updated.
     * @param changes changes to be applied.
     */
    ODataEntityUpdateRequest(final Method method, final URI uri, final ODataEntity changes) {
        super(method, uri);
        this.changes = changes;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataEntityUpdateResponse execute() {
        final InputStream input = getPayload();
        ((HttpEntityEnclosingRequestBase) request).setEntity(new InputStreamEntity(input, -1));

        try {
            return new ODataEntityUpdateResponseImpl(client, doExecute());
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected InputStream getPayload() {
        return ODataWriter.writeEntity(changes, ODataPubFormat.valueOf(getFormat()));
    }

    /**
     * Response class about an ODataEntityUpdateRequest.
     */
    private class ODataEntityUpdateResponseImpl extends ODataResponseImpl implements ODataEntityUpdateResponse {

        /**
         * Constructor.
         * <p>
         * Just to create response templates to be initialized from batch.
         */
        private ODataEntityUpdateResponseImpl() {
        }

        /**
         * Constructor.
         *
         * @param client HTTP client.
         * @param res HTTP response.
         */
        private ODataEntityUpdateResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
            this.close();
        }
    }
}
