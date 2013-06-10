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
package com.msopentech.odatajclient.engine.communication.request;

import com.msopentech.odatajclient.engine.communication.response.ODataMetadataResponse;
import java.net.URI;
import java.util.concurrent.Future;

/**
 * This class implements an OData metadata request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getMetadataRequest(java.lang.String)
 */
public class ODataMetadataRequest extends ODataRequestImpl
        implements ODataBasicRequest<ODataMetadataResponse> {

    /**
     * Constructor.
     *
     * @param serviceRoot query URI.
     */
    ODataMetadataRequest(final URI uri) {
        // set method .... If cofigured X-HTTP-METHOD header will be used.
        super(Method.GET);
        // set uri ...
        this.uri = uri;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataMetadataResponse execute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Future<ODataMetadataResponse> asyncExecute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
