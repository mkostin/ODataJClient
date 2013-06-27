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

import com.msopentech.odatajclient.engine.client.http.HttpClientException;
import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * Abstract representation of an OData response.
 */
public abstract class ODataResponseImpl implements ODataResponse {

    protected final HttpClient client;

    protected final HttpResponse res;

    public ODataResponseImpl(final HttpClient client, final HttpResponse res) {
        this.client = client;
        this.res = res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getHeaderNames() {
        final List<String> headerNames = new ArrayList<String>();

        for (Header header : res.getAllHeaders()) {
            headerNames.add(header.getName());
        }

        return headerNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getHeader(final String name) {
        final List<String> headerValues = new ArrayList<String>();

        for (Header header : res.getHeaders(name)) {
            headerValues.add(header.getValue());
        }

        return headerValues;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStatusCode() {
        return res.getStatusLine().getStatusCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStatusMessage() {
        return res.getStatusLine().getReasonPhrase();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void close() {
        this.client.getConnectionManager().shutdown();
    }

    @Override
    public InputStream getRawResponse() {
        try {
            return res.getEntity().getContent();
        } catch (Exception e) {
            throw new HttpClientException("While extracting raw response", e);
        }
    }
}
