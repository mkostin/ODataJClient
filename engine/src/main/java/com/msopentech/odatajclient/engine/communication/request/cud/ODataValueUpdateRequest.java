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
import com.msopentech.odatajclient.engine.communication.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataBasicRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataValueUpdateResponse;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.format.ODataValueFormat;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.InputStreamEntity;

/**
 * This class implements an OData update entity property value request.
 * Get instance by using ODataCUDRequestFactory.
 *
 * @see ODataCUDRequestFactory#getUpdateValueRequest(com.msopentech.odatajclient.engine.data.ODataURI,
 * com.msopentech.odatajclient.engine.data.ODataValue)
 */
public class ODataValueUpdateRequest extends ODataBasicRequestImpl<ODataValueUpdateResponse, ODataValueFormat>
        implements ODataBatchableRequest {

    /**
     * Value to be created.
     */
    private final ODataValue value;

    /**
     * Constructor.
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param value value to be created.
     */
    ODataValueUpdateRequest(final URI targetURI, final UpdateType type, final ODataValue value) {
        // set method ... . If cofigured X-HTTP-METHOD header will be used.
        super(type.getMethod(), targetURI);
        // set request body
        this.value = value;
    }

    @Override
    public ODataValueUpdateResponseImpl execute() {
        final InputStream input = getPayload();
        ((HttpEntityEnclosingRequestBase) request).setEntity(new InputStreamEntity(input, -1));

        try {
            final HttpResponse res = doExecute();
            return new ODataValueUpdateResponseImpl(client, res);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected InputStream getPayload() {
        return IOUtils.toInputStream(value.toString());
    }

    private class ODataValueUpdateResponseImpl extends ODataResponseImpl implements ODataValueUpdateResponse {

        private ODataValue value = null;

        private ODataValueUpdateResponseImpl() {
        }

        private ODataValueUpdateResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        @Override
        public ODataValue getBody() {
            if (value == null) {
                try {
                    value = new ODataPrimitiveValue.Builder().
                            setType(ODataValueFormat.valueOf(getFormat()) == ODataValueFormat.TEXT
                            ? EdmSimpleType.STRING : EdmSimpleType.STREAM).
                            setText(IOUtils.toString(getRawResponse())).
                            build();
                } catch (IOException e) {
                    throw new HttpClientException(e);
                } finally {
                    this.close();
                }
            }
            return value;
        }
    }
}
