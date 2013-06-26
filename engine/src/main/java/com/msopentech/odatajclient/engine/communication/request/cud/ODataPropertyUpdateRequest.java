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
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataPropertyUpdateResponse;
import com.msopentech.odatajclient.engine.data.ODataBinder;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.types.ODataPropertyFormat;
import com.msopentech.odatajclient.engine.utils.SerializationUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;

/**
 * This class implements an OData update entity property request.
 * Get instance by using ODataCUDRequestFactory.
 *
 * @see ODataCUDRequestFactory#getUpdatePropertyRequest(com.msopentech.odatajclient.engine.data.ODataURI,
 * com.msopentech.odatajclient.engine.data.ODataValue)
 */
public class ODataPropertyUpdateRequest extends ODataBasicRequestImpl<ODataPropertyUpdateResponse, ODataPropertyFormat>
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

    /**
     * {@inheritDoc }
     */
    @Override
    protected byte[] getPayload() {
        return new byte[0];
    }

    @Override
    public ODataPropertyUpdateResponse execute() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SerializationUtils.serializeProperty(
                ODataBinder.getProperty(property), ODataPropertyFormat.valueOf(getFormat()), baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        client.accept(getContentType()).type(getContentType());
        WebClient.getConfig(client).getRequestContext().put("use.async.http.conduit", true);

        final Response res = client.invoke(getMethod().name(), bais);

        try {
            baos.flush();
            baos.close();
            bais.close();
        } catch (IOException e) {
            LOG.error("While closing input / output streams for the request execution", e);
        }

        return new ODataPropertyUpdateResponseImpl(res);
    }

    private class ODataPropertyUpdateResponseImpl extends ODataResponseImpl implements ODataPropertyUpdateResponse {

        private ODataProperty property = null;

        public ODataPropertyUpdateResponseImpl(Response res) {
            super(res);
        }

        @Override
        public ODataProperty getBody() {
            try {
                if (property == null) {
                    property = ODataReader.getProperty(
                            res.readEntity(InputStream.class), ODataPropertyFormat.valueOf(getFormat()));
                }
                return property;
            } finally {
                res.close();
            }
        }
    }
}
