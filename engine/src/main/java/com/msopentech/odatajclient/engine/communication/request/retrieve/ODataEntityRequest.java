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
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import java.io.InputStream;
import java.net.URI;
import javax.ws.rs.core.Response;

/**
 * This class implements an OData retrieve query request returning a single entity.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getEntityRequest(java.lang.String)
 */
public class ODataEntityRequest extends ODataQueryRequest<ODataEntity, ODataFormat> {

    /**
     * Private constructor.
     *
     * @param query query to be executed.
     */
    ODataEntityRequest(final URI query) {
        super(query);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataQueryResponse<ODataEntity> execute() {
        final Response res = client.accept(getContentType()).get();
        return new ODataEntityResponseImpl(res);
    }

    protected class ODataEntityResponseImpl extends ODataQueryResponseImpl {

        private ODataEntity entity = null;

        private ODataEntityResponseImpl(final Response res) {
            super(res);
        }

        @Override
        public ODataEntity getBody() {
            try {
                if (entity == null) {
                    entity = ODataReader.deserializeEntity(
                            res.readEntity(InputStream.class), ODataFormat.valueOf(getFormat()));
                }
                return entity;
            } finally {
                res.close();
            }
        }
    }
}
