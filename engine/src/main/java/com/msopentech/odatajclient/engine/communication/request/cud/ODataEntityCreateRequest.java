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
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataBinder;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;

/**
 * This class implements an OData create request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getCreateRequest(com.msopentech.odatajclient.engine.data.ODataURI,
 * com.msopentech.odatajclient.engine.data.ODataEntity)
 */
public class ODataEntityCreateRequest extends ODataBasicRequestImpl<ODataEntityCreateResponse, ODataFormat>
        implements ODataBatchableRequest {

    /**
     * Entity to be created.
     */
    private final ODataEntity entity;

    /**
     * Constructor.
     *
     * @param targetURI entity set URI.
     * @param entity entity to be created.
     */
    ODataEntityCreateRequest(final URI targetURI, final ODataEntity entity) {
        // set method ... . If cofigured X-HTTP-METHOD header will be used.
        super(Method.POST, targetURI);
        this.entity = entity;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataEntityCreateResponse execute() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SerializationUtils.serializeEntry(ODataBinder.getEntry(entity,
                ResourceFactory.entryClassForFormat(ODataFormat.valueOf(getFormat()))), baos);

        final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        request.setHeader(ODataHeader.HeaderName.accept.toString(), getAccept());
        request.setHeader(ODataHeader.HeaderName.contentType.toString(), getContentType());

        ((HttpPost) request).setEntity(new InputStreamEntity(bais, -1));

        try {
            final HttpResponse res = client.execute(request);
            return new ODataEntityCreateResponseImpl(client, res);
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

    private class ODataEntityCreateResponseImpl extends ODataResponseImpl implements ODataEntityCreateResponse {

        private ODataEntity entity = null;

        public ODataEntityCreateResponseImpl(final HttpClient client, final HttpResponse res) {
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
