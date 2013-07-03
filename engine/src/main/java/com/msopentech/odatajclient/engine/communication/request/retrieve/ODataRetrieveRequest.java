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

import com.msopentech.odatajclient.engine.communication.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataBasicRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import java.io.InputStream;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This is an abstract representation of an OData retrieve query request returning one or more result item.
 * Get instance by using ODataRequestFactory.
 */
abstract class ODataRetrieveRequest<V, T extends Enum<T>>
        extends ODataBasicRequestImpl<ODataRetrieveResponse<V>, T>
        implements ODataBatchableRequest {

    /**
     * Private constructor.
     *
     * @param query query to be executed.
     */
    ODataRetrieveRequest(final URI query) {
        super(Method.GET, query);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public abstract ODataRetrieveResponse<V> execute();

    /**
     * {@inheritDoc }
     * <p>
     * This kind of request doesn't have any payload: null will be returned.
     */
    @Override
    protected InputStream getPayload() {
        return null;
    }

    protected abstract class ODataRetrieveResponseImpl extends ODataResponseImpl implements ODataRetrieveResponse<V> {

        protected ODataRetrieveResponseImpl() {
        }

        protected ODataRetrieveResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        @Override
        public abstract V getBody();
    }
}
