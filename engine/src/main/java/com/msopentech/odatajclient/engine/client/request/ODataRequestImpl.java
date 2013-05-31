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
import com.msopentech.odatajclient.engine.communication.header.ODataHeader;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import com.msopentech.odatajclient.engine.utils.Configuration;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;

/**
 * Abstract representation of an OData request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory.
 */
public abstract class ODataRequestImpl implements ODataRequest {

    /**
     * OData resource representation format.
     */
    private ODataFormat format;

    /**
     * OData request method.
     * <p>
     * If configured a X-HTTP-METHOD header will be used.
     * In this case the actual method will be
     * <code>POST</code>.
     */
    final protected Method method;

    /**
     * OData request header.
     */
    protected ODataHeader header;

    /**
     * Target URI.
     */
    protected URI uri;

    /**
     * Constructor.
     *
     * @param method HTTP request method. If configured X-HTTP-METHOD header will be used.
     */
    protected ODataRequestImpl(final Method method) {
        this.method = method;
        // initialize a default header from configuration
        this.header = new ODataHeader();
        // take format from configuration
        this.format = new Configuration().getFormat();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ODataFormat getFormat() {
        return format;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFormat(final ODataFormat format) {
        this.format = format;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URI getURI() {
        return uri;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getHeaderNames() {
        return header.getHeaderNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHeader(final String name) {
        return header.getHeader(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxDataServiceVersion(final String value) {
        header.setHeader(ODataHeader.HeaderName.maxDataServiceVersion, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMinDataServiceVersion(final String value) {
        header.setHeader(ODataHeader.HeaderName.minDataServiceVersion, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAccept(final String value) {
        header.setHeader(ODataHeader.HeaderName.accept, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIfMatch(final String value) {
        header.setHeader(ODataHeader.HeaderName.ifMatch, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIfNoneMatch(final String value) {
        header.setHeader(ODataHeader.HeaderName.ifNoneMatch, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPrefer(final String value) {
        header.setHeader(ODataHeader.HeaderName.prefer, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCHTTPMethod(final String value) {
        header.setHeader(ODataHeader.HeaderName.xHttpMethod, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCustomHeader(final String name, final String value) {
        header.setHeader(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract InputStream getBody();
}
