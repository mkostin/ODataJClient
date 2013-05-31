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
package com.msopentech.odatajclient.engine.client.request;

import com.msopentech.odatajclient.engine.communication.request.*;
import java.io.InputStream;
import java.net.URI;

/**
 * This class implements an OData metadata request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getMetadataRequest(java.lang.String)
 */
class ODataMetadataRequestImpl extends ODataRequestImpl implements ODataMetadataRequest {

    /**
     * Constructor.
     *
     * @param query query URI.
     */
    ODataMetadataRequestImpl(final URI query) {
        // set method .... If cofigured X-HTTP-METHOD header will be used.
        super(Method.GET);
        // set uri ...
        this.uri = query;
    }

    /**
     * Unsupported operation.
     */
    @Override
    public InputStream getBody() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
