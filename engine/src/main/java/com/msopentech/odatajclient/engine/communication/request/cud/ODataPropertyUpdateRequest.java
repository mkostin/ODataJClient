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
import com.msopentech.odatajclient.engine.communication.request.ODataBasicRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataPropertyUpdateResponse;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.data.ODataWriter;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.InputStreamEntity;

/**
 * This class implements an OData update entity property request.
 * Get instance by using ODataCUDRequestFactory.
 *
 * @see ODataCUDRequestFactory#getUpdatePropertyRequest(com.msopentech.odatajclient.engine.data.ODataURI,
 * com.msopentech.odatajclient.engine.data.ODataValue)
 */
public class ODataPropertyUpdateRequest extends ODataBasicRequestImpl<ODataPropertyUpdateResponse, ODataFormat>
        implements ODataBatchableRequest {

    /**
     * Value to be created.
     */
    private final ODataProperty property;

    /**
     * Constructor.
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param property value to be created.
     */
    ODataPropertyUpdateRequest(final URI targetURI, final UpdateType type, final ODataProperty property) {
        // set method ... . If cofigured X-HTTP-METHOD header will be used.
        super(type.getMethod(), targetURI);
        // set request body
        this.property = property;
    }

    @Override
    public ODataPropertyUpdateResponse execute() {
        final InputStream input = getPayload();
        ((HttpEntityEnclosingRequestBase) request).setEntity(new InputStreamEntity(input, -1));

        try {
            final HttpResponse res = doExecute();
            return new ODataPropertyUpdateResponseImpl(client, res);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected InputStream getPayload() {
        return ODataWriter.writeProperty(property, ODataFormat.valueOf(getFormat()));
    }

    private class ODataPropertyUpdateResponseImpl extends ODataResponseImpl implements ODataPropertyUpdateResponse {

        private ODataProperty property = null;

        public ODataPropertyUpdateResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        @Override
        public ODataProperty getBody() {
            if (property == null) {
                try {
                    property = ODataReader.readProperty(
                            res.getEntity().getContent(), ODataFormat.valueOf(getFormat()));
                } catch (IOException e) {
                    throw new HttpClientException(e);
                } finally {
                    this.close();
                }
            }
            return property;
        }
    }
}
