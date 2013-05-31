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
 * This class implements an OData stream create/update request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getStreamRequest(com.msopentech.odatajclient.engine.data.ODataURI, java.io.InputStream)
 */
class ODataUpdateStreamRequestImpl extends ODataRequestImpl implements ODataUpdateStreamRequest {

    /**
     * Stream to be updated.
     */
    private final InputStream stream;

    /**
     * Constructor.
     *
     * @param targetURI target URI.
     * @param stream stream to be updated.
     */
    ODataUpdateStreamRequestImpl(final URI targetURI, final InputStream stream) {
        super(Method.PUT);
        this.stream = stream;
        this.uri = targetURI;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getBody() {
        return stream;
    }
}
