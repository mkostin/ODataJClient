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

import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.types.ODataPropertyFormat;
import java.io.InputStream;
import java.net.URI;
import javax.ws.rs.core.Response;

/**
 * This class implements an OData entity property query request.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getPropertyRequest(java.net.URI)
 */
public class ODataPropertyRequest extends ODataQueryRequest<ODataProperty, ODataPropertyFormat> {

    /**
     * Private constructor.
     *
     * @param query query to be executed.
     */
    ODataPropertyRequest(final URI query) {
        super(query);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataQueryResponse<ODataProperty> execute() {
        final Response res = client.accept(getContentType()).get();
        return new ODataEntitySetResponseImpl(res);
    }

    protected class ODataEntitySetResponseImpl extends ODataQueryResponseImpl {

        private ODataProperty property = null;

        private ODataEntitySetResponseImpl(final Response res) {
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
