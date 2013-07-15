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

import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData retrieve query request returning a single entity.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getEntityRequest(java.lang.String)
 */
public class ODataEntityRequest extends ODataRetrieveRequest<ODataEntity, ODataPubFormat> {

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
    public ODataRetrieveResponse<ODataEntity> execute() {
        return new ODataEntityResponseImpl(client, doExecute());
    }

    public class ODataEntityResponseImpl extends ODataRetrieveResponseImpl {

        private ODataEntity entity = null;

        public ODataEntityResponseImpl() {
            super();
        }

        private ODataEntityResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        @Override
        public ODataEntity getBody() {
            if (entity == null) {
                try {
                    entity = ODataReader.readEntity(getRawResponse(), ODataPubFormat.valueOf(getFormat()));
                } finally {
                    this.close();
                }
            }
            return entity;
        }
    }
}
