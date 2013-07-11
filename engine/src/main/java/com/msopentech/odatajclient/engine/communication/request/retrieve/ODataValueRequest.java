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
package com.msopentech.odatajclient.engine.communication.request.retrieve;

import com.msopentech.odatajclient.engine.client.http.HttpClientException;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.format.ODataValueFormat;
import java.io.IOException;
import java.net.URI;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData entity property value query request.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getValueRequest(java.net.URI)
 */
public class ODataValueRequest extends ODataRetrieveRequest<ODataValue, ODataValueFormat> {

    /**
     * Private constructor.
     *
     * @param query query to be executed.
     */
    ODataValueRequest(final URI query) {
        super(query);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataRetrieveResponse<ODataValue> execute() {
        final HttpResponse res = doExecute();
        return new ODataValueResponseImpl(client, res);
    }

    protected class ODataValueResponseImpl extends ODataRetrieveResponseImpl {

        private ODataValue value = null;

        private ODataValueResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        @Override
        public ODataValue getBody() {
            if (value == null) {
                try {
                    value = new ODataPrimitiveValue.Builder().
                            setType(ODataValueFormat.valueOf(getFormat()) == ODataValueFormat.TEXT
                            ? EdmSimpleType.STRING : EdmSimpleType.STREAM).
                            setText(IOUtils.toString(res.getEntity().getContent())).
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
