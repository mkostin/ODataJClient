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

import com.msopentech.odatajclient.engine.communication.request.ODataBasicRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.OperationType;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataInvokeResponse;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.types.ODataPubFormat;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import org.apache.commons.io.IOUtils;

/**
 * This class implements an OData invoke operation request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getInvokeActionRequest(com.msopentech.odatajclient.engine.data.ODataURI, java.util.Map)
 * @see ODataRequestFactory#getInvokeFunctionRequest(com.msopentech.odatajclient.engine.data.ODataURI)
 * @see ODataRequestFactory#getInvokeLegacyRequest(
 * com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method,
 * com.msopentech.odatajclient.engine.data.ODataURI, java.util.Map)
 */
public class ODataInvokeRequest extends ODataBasicRequestImpl<ODataInvokeResponse, ODataPubFormat>
        implements ODataBatchableRequest {

    /**
     * Operation type.
     */
    private final OperationType type;

    /**
     * Function parameters.
     */
    final Map<String, ODataValue> parameters;

    /**
     * Constructor.
     *
     * @param method HTTP method of the request. If configured X-HTTP-METHOD header will be used.
     * @param uri URI that identifies the operation.
     * @param type requested operation type.
     */
    ODataInvokeRequest(
            final Method method,
            final URI uri,
            final OperationType type) {
        super(method, uri);
        this.type = type;
        this.parameters = null;
    }

    /**
     * Constructor.
     *
     * @param method HTTP method of the request. If configured X-HTTP-METHOD header will be used.
     * @param uri URI that identifies the operation.
     * @param type requested operation type.
     * @param parameters function parameters.
     */
    ODataInvokeRequest(
            final Method method,
            final URI uri,
            final OperationType type,
            final Map<String, ODataValue> parameters) {
        super(method, uri);
        this.type = type;
        this.parameters = parameters;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataInvokeResponse execute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected InputStream getPayload() {
        final StringBuilder builder = new StringBuilder();

        if (parameters != null) {
            for (Map.Entry<String, ODataValue> param : parameters.entrySet()) {
                builder.append(param.getKey()).append("=").append(param.getValue().toString());
            }
        }

        return IOUtils.toInputStream(builder.toString());
    }
}
