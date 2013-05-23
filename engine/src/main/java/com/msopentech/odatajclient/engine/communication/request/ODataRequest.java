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

import com.msopentech.odatajclient.engine.communication.header.ODataHeader;
import com.msopentech.odatajclient.engine.data.ODataURI;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import com.msopentech.odatajclient.engine.utils.Configuration;
import java.io.InputStream;
import java.util.Collection;

/**
 * Abstract representation of an OData request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory.
 */
public abstract class ODataRequest {

    /**
     * Supported HTTP methods.
     */
    public enum Method {

        NONE,
        GET,
        POST,
        DELETE,
        PUT,
        PATCH,
        MERGE

    }

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
    protected ODataURI uri;

    /**
     * Constructor.
     *
     * @param method HTTP request method. If cofigured X-HTTP-METHOD header will be used.
     */
    protected ODataRequest(final Method method) {
        this.method = method;
        // initialize a default header from configuration
        this.header = new ODataHeader();
        // take format from configuration
        this.format = new Configuration().getFormat();
    }

    /**
     * Returns resource representation format.
     *
     * @return the configured format (<code>Atom</code> will be the default value unless differently specified).
     */
    public ODataFormat getFormat() {
        return format;
    }

    /**
     * Override configured request format.
     *
     * @param format request format.
     *
     * @see ODataFormat
     */
    public void setFormat(final ODataFormat format) {
        this.format = format;
    }

    /**
     * Returns OData request target URI.
     *
     * @return OData request target URI.
     */
    public ODataURI getUri() {
        return uri;
    }

    /**
     * Gets all OData request header names.
     *
     * @return all request header names.
     */
    public Collection<String> getHeaderNames() {
        return header.getHeaderNames();
    }

    /**
     * Gets the value of the OData request header identified by the given name.
     *
     * @param name name of the OData request header to be retrieved.
     * @return header value.
     */
    public String getHeader(final String name) {
        return header.getHeader(name);
    }

    /**
     * Adds
     * <code>MaxDataServiceVersion</code> OData request header.
     *
     * @param value header value.
     * @see com.msopentech.odatajclient.engine.communication.header.ODataHeader.HeaderName#maxDataServiceVersion
     */
    public void setMaxDataServiceVersion(final String value) {
        header.setHeader(ODataHeader.HeaderName.maxDataServiceVersion, value);
    }

    /**
     * Adds
     * <code>MinDataServiceVersion</code> OData request header.
     *
     * @param value header value.
     * @see com.msopentech.odatajclient.engine.communication.header.ODataHeader.HeaderName#minDataServiceVersion
     */
    public void setMinDataServiceVersion(final String value) {
        header.setHeader(ODataHeader.HeaderName.minDataServiceVersion, value);
    }

    /**
     * Adds
     * <code>Accept</code> OData request header.
     *
     * @param value header value.
     * @see com.msopentech.odatajclient.engine.communication.header.ODataHeader.HeaderName#accept
     */
    public void setAccept(final String value) {
        header.setHeader(ODataHeader.HeaderName.accept, value);
    }

    /**
     * Adds
     * <code>If-Match</code> OData request header.
     *
     * @param value header value.
     * @see com.msopentech.odatajclient.engine.communication.header.ODataHeader.HeaderName#ifMatch
     */
    public void setIfMatch(final String value) {
        header.setHeader(ODataHeader.HeaderName.ifMatch, value);
    }

    /**
     * Adds
     * <code>If-None-Match</code> OData request header.
     *
     * @param value header value.
     * @see com.msopentech.odatajclient.engine.communication.header.ODataHeader.HeaderName#ifNoneMatch
     */
    public void setIfNoneMatch(final String value) {
        header.setHeader(ODataHeader.HeaderName.ifNoneMatch, value);
    }

    /**
     * Adds
     * <code>Prefer</code> OData request header.
     *
     * @param value header value.
     * @see com.msopentech.odatajclient.engine.communication.header.ODataHeader.HeaderName#prefer
     */
    public void setPrefer(final String value) {
        header.setHeader(ODataHeader.HeaderName.prefer, value);
    }

    /**
     * Adds
     * <code>X-HTTP-METHOD</code> OData request header.
     *
     * @param value header value.
     * @see com.msopentech.odatajclient.engine.communication.header.ODataHeader.HeaderName#xHttpMethod
     */
    public void setCHTTPMethod(final String value) {
        header.setHeader(ODataHeader.HeaderName.xHttpMethod, value);
    }

    /**
     * Adds a custom OData request header.
     *
     * @param name header name.
     * @param value header value.
     */
    public void addCustomHeader(final String name, final String value) {
        header.setHeader(name, value);
    }

    /**
     * Gets request body.
     *
     * @return request body stream.
     */
    public abstract InputStream getBody();
}
