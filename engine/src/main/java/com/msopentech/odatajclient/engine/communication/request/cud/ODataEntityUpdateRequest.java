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

import com.msopentech.odatajclient.engine.client.http.HttpClientException;
import com.msopentech.odatajclient.engine.client.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.communication.header.ODataHeader;
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
import java.net.URI;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.InputStreamEntity;

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
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SerializationUtils.serializeEntry(ODataBinder.getEntry(changes,
                ResourceFactory.entryClassForFormat(ODataFormat.valueOf(getFormat()))), baos);

        final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        request.setHeader(ODataHeader.HeaderName.accept.toString(), getAccept());
        request.setHeader(ODataHeader.HeaderName.contentType.toString(), getContentType());

        ((HttpEntityEnclosingRequestBase) request).setEntity(new InputStreamEntity(bais, -1));
        
        try {
            final HttpResponse res = client.execute(request);
            return new ODataEntityUpdateResponseImpl(client, res);
        } catch (IOException e) {
            throw new HttpClientException(e);
        } catch (RuntimeException e) {
            this.request.abort();
            throw new HttpClientException(e);
        } finally {
            IOUtils.closeQuietly(baos);
            IOUtils.closeQuietly(bais);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected byte[] getPayload() {
        return new byte[0];
    }

    private class ODataEntityUpdateResponseImpl extends ODataResponseImpl implements ODataEntityUpdateResponse {

        private ODataEntity entity = null;

        public ODataEntityUpdateResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        @Override
        public ODataEntity getBody() {
            if (entity == null) {
                try {
                    entity = ODataReader.getEntity(res.getEntity().getContent(), ODataFormat.valueOf(getFormat()));
                } catch (IOException e) {
                    throw new HttpClientException(e);
                } finally {
                    this.close();
                }
            }
            return entity;
        }
    }
}
