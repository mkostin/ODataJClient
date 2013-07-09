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
package com.msopentech.odatajclient.engine.communication.request.invoke;

import com.msopentech.odatajclient.engine.client.http.HttpClientException;
import com.msopentech.odatajclient.engine.communication.request.ODataBasicRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataInvokeResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataInvokeResult;
import com.msopentech.odatajclient.engine.data.ODataNoContent;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.ODataWriter;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.InputStreamEntity;

/**
 * This class implements an OData invoke operation request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataInvokeRequestFactory#getInvokeRequest(java.net.URI,
 * com.msopentech.odatajclient.engine.data.metadata.EdmMetadata,
 * com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer.FunctionImport)
 */
public class ODataInvokeRequest<T extends ODataInvokeResult>
        extends ODataBasicRequestImpl<ODataInvokeResponse<T>, ODataPubFormat>
        implements ODataBatchableRequest {

    private final Class<T> reference;

    /**
     * Function parameters.
     */
    private Map<String, ODataValue> parameters;

    private String format;

    /**
     * Constructor.
     *
     * @param reference reference class for invoke result
     * @param method HTTP method of the request. If configured X-HTTP-METHOD header will be used.
     * @param uri URI that identifies the operation.
     */
    ODataInvokeRequest(
            final Class<T> reference,
            final Method method,
            final URI uri) {

        super(method, uri);

        this.reference = reference;
        this.parameters = new HashMap<String, ODataValue>();
    }

    public void setParameters(final Map<String, ODataValue> parameters) {
        this.parameters.clear();
        if (parameters != null && !parameters.isEmpty()) {
            this.parameters.putAll(parameters);
        }
    }

    @Override
    public void setFormat(final ODataPubFormat format) {
        this.format = (reference.isAssignableFrom(ODataProperty.class) && format == ODataPubFormat.ATOM)
                ? ODataFormat.XML.toString()
                : format.toString();
        setAccept(this.format);
        setContentType(this.format);
    }

    @Override
    public String getFormat() {
        if (this.format == null) {
            setFormat(ODataPubFormat.valueOf(super.getFormat()));
        }

        return this.format;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected InputStream getPayload() {
        if (!this.parameters.isEmpty() && this.method == Method.POST) {
            // Additional, non-binding parameters MUST be sent as JSON
            final ODataEntity tmp = ODataFactory.newEntity("");
            for (Map.Entry<String, ODataValue> param : parameters.entrySet()) {
                tmp.addProperty(new ODataProperty(param.getKey(), param.getValue()));
            }

            return ODataWriter.writeEntity(tmp, ODataPubFormat.JSON);
        }

        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataInvokeResponse<T> execute() {
        final InputStream input = getPayload();

        if (!this.parameters.isEmpty()) {
            if (this.method == Method.GET) {
                final URIBuilder uriBuilder = new URIBuilder(this.uri);
                for (Map.Entry<String, ODataValue> param : parameters.entrySet()) {
                    if (!param.getValue().isPrimitive()) {
                        throw new IllegalArgumentException("Only primitive values can be passed via GET");
                    }

                    uriBuilder.addParameter(param.getKey(), param.getValue().toString());
                }
                try {
                    ((HttpRequestBase) this.request).setURI(uriBuilder.build());
                } catch (URISyntaxException e) {
                    throw new IllegalArgumentException("While adding GET parameters", e);
                }
            } else if (this.method == Method.POST) {
                ((HttpPost) request).setEntity(new InputStreamEntity(input, -1));

                setContentType(ODataPubFormat.JSON.toString());
            }
        }

        try {
            final HttpResponse res = doExecute();
            return new ODataInvokeResponseImpl(client, res);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    protected class ODataInvokeResponseImpl extends ODataResponseImpl implements ODataInvokeResponse<T> {

        private T invokeResult = null;

        private ODataInvokeResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        @Override
        @SuppressWarnings("unchecked")
        public T getBody() {
            if (invokeResult == null) {
                if (reference.isAssignableFrom(ODataNoContent.class)) {
                    invokeResult = (T) new ODataNoContent();
                }

                try {
                    if (reference.isAssignableFrom(ODataEntitySet.class)) {
                        invokeResult = (T) ODataReader.readEntitySet(res.getEntity().getContent(),
                                ODataPubFormat.fromString(getFormat()));
                    }
                    if (reference.isAssignableFrom(ODataEntity.class)) {
                        invokeResult = (T) ODataReader.readEntity(res.getEntity().getContent(),
                                ODataPubFormat.fromString(getFormat()));
                    }
                    if (reference.isAssignableFrom(ODataProperty.class)) {
                        invokeResult = (T) ODataReader.readProperty(res.getEntity().getContent(),
                                ODataFormat.fromString(getFormat()));
                    }
                } catch (IOException e) {
                    throw new HttpClientException(e);
                } finally {
                    this.close();
                }
            }
            return invokeResult;
        }
    }
}
