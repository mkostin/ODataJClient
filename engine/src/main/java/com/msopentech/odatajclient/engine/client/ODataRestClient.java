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
package com.msopentech.odatajclient.engine.client;

import com.msopentech.odatajclient.engine.communication.request.ODataRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import java.io.InputStream;
import java.util.concurrent.Future;

/**
 * RESTFul OData client implementation.
 */
public class ODataRestClient implements ODataClient {

    public ODataRestClient() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ODataResponse> T execute(final ODataRequest request) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream rawExecute(ODataRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ODataResponse> Future<T> asyncExecute(ODataRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Future<InputStream> asyncRawExecute(ODataRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
