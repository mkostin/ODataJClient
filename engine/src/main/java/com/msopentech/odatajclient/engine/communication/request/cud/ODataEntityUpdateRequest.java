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
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityUpdateResponse;
import com.msopentech.odatajclient.engine.data.ODataBinder;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.data.ResourceFactory;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import com.msopentech.odatajclient.engine.utils.SerializationUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import javax.ws.rs.core.Response;

/**
 * This class implements an OData update request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getUpdateRequest(com.msopentech.odatajclient.engine.data.ODataURI,
 * com.msopentech.odatajclient.engine.data.ODataEntity,
 * com.msopentech.odatajclient.engine.communication.request.UpdateType)
 */
public class ODataEntityUpdateRequest extends ODataBasicRequestImpl<ODataEntityUpdateResponse, ODataFormat>
        implements ODataBatchableRequest {

    /**
     * Changes to be applied.
     */
    private final ODataEntity changes;

    /**
     * Constructor.
     *
     * @param uri URI of the entity to be updated.
     * @param type update type.
     * @param changes changes to be applied.
     */
    ODataEntityUpdateRequest(final URI uri, final UpdateType type, final ODataEntity changes) {
        // set method .... If cofigured X-HTTP-METHOD header will be used.
        super(type.getMethod(), uri);
        this.changes = changes;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataEntityUpdateResponse execute() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SerializationUtils.serializeEntry(ODataBinder.getEntry(changes,
                ResourceFactory.entryClassForFormat(ODataFormat.valueOf(getFormat()))), baos);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        client.accept(getContentType()).type(getContentType());
        client.header("X-HTTP-Method", getMethod().name());
//        WebClient.getConfig(client).getRequestContext().put("use.async.http.conduit", true);

//        final Response res = client.invoke(getMethod().name(), bais);
        final Response res = client.post(bais);

        try {
            baos.flush();
            baos.close();
            bais.close();
        } catch (IOException e) {
            LOG.error("While closing input / output streams for the request execution", e);
        }

        return new ODataEntityUpdateResponseImpl(res);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected byte[] getPayload() {
        return null;
    }

    private class ODataEntityUpdateResponseImpl extends ODataResponseImpl implements ODataEntityUpdateResponse {

        private ODataEntity entity = null;

        public ODataEntityUpdateResponseImpl(Response res) {
            super(res);
        }

        @Override
        public ODataEntity getBody() {
            if (entity == null) {
                try {
                    entity = ODataReader.getEntity(
                            res.readEntity(InputStream.class), ODataFormat.valueOf(getFormat()));
                } finally {
                    res.close();
                }
            }
            return entity;
        }
    }
}
