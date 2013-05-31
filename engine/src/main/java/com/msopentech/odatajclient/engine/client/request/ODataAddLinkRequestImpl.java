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
import com.msopentech.odatajclient.engine.data.ODataLink;
import java.io.InputStream;
import java.net.URI;

/**
 * This class implements an create link OData request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getAddLinkRequest(com.msopentech.odatajclient.engine.data.ODataURI,
 * com.msopentech.odatajclient.engine.data.ODataLink)
 */
class ODataAddLinkRequestImpl extends ODataRequestImpl implements ODataAddLinkRequest {

    /**
     * OData entity to be linked.
     */
    private final ODataLink entityToBeAdded;

    /**
     * Constructor.
     *
     * @param targetURI entity set URI.
     * @param entityToBeAdded entity to be linked.
     */
    ODataAddLinkRequestImpl(final URI targetURI, final ODataLink entityToBeAdded) {
        // set method ... . If cofigured X-HTTP-METHOD header will be used.
        super(Method.POST);
        // set target uri
        this.uri = targetURI;
        // set request body
        this.entityToBeAdded = entityToBeAdded;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getBody() {
        return null; // entityToBeAdded.getLink().toStream();
    }
}
