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
package com.msopentech.odatajclient.engine.client.response;

import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import javax.ws.rs.core.Response;

/**
 * Abstract representation of an OData response.
 */
public abstract class ODataResponseImpl implements ODataResponse {

    protected final Response res;

    public ODataResponseImpl(final Response res) {
        this.res = res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getHeaderNames() {
        return res.getHeaders() == null ? new HashSet<String>() : res.getHeaders().keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHeader(final String name) {
        return res.getHeaderString(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStatusCode() {
        return res.getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStatusMessage() {
        return res.getStatusInfo().getReasonPhrase();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void close() {
        res.close();
    }

    @Override
    public InputStream getRawResponse() {
        return res.readEntity(InputStream.class);
    }
    
    
}
