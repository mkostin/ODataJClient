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
import com.msopentech.odatajclient.engine.utils.NoValidEntityFound;
import com.msopentech.odatajclient.engine.utils.ODataReader;
import java.net.URI;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;

/**
 * This class implements an OData retrieve query request returning a single entity.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getEntityRequest(java.lang.String)
 */
public class ODataEntityRequest extends ODataQueryRequest<ODataEntity> {

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
        final WebClient client = WebClient.create(this.uri);
        final Response res = client.accept(getContentType()).get();
        return new ODataEntitySetResponseImpl(res);
    }

    protected class ODataEntitySetResponseImpl extends ODataQueryResponseImpl {

        private ODataEntity entity = null;

        private ODataEntitySetResponseImpl(final Response res) {
            super(res);
        }

        @Override
        public ODataEntity getBody() {
            try {
                if (entity == null) {
                    entity = ODataReader.deserializeEntity(is);
                }
                return entity;
            } catch (NoValidEntityFound e) {
                LOG.error("Error reading entry", e);
                throw new IllegalStateException(e);
            } finally {
                res.close();
            }
        }
    }
}
