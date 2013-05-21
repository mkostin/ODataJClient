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
import java.io.InputStream;
import java.util.Map;

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
     * OData request method.
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
     * @param method HTTP request method.
     */
    protected ODataRequest(final Method method) {
        this.method = method;
        // initialize a default header from configuration
        this.header = new ODataHeader();
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
     * Gets all OData request headers.
     *
     * @return unmodifiable map containing all request headers.
     */
    public Map<String, String> getHeaders() {
        return header.getHeaders();
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
     */
    public void setMaxDataServiceVersion(final String value) {
        header.setHeader(ODataHeader.HeaderName.maxDataServiceVersion.toString(), value);
    }

    /**
     * Adds
     * <code>MinDataServiceVersion</code> OData request header.
     *
     * @param value header value.
     */
    public void setMinDataServiceVersion(final String value) {
        header.setHeader(ODataHeader.HeaderName.minDataServiceVersion.toString(), value);
    }

    /**
     * Adds
     * <code>Accept</code> OData request header.
     *
     * @param value header value.
     */
    public void setAccept(final String value) {
        header.setHeader(ODataHeader.HeaderName.accept.toString(), value);
    }

    /**
     * Adds
     * <code>If-Match</code> OData request header.
     *
     * @param value header value.
     */
    public void setIfMatch(final String value) {
        header.setHeader(ODataHeader.HeaderName.ifMatch.toString(), value);
    }

    /**
     * Adds
     * <code>If-None-Match</code> OData request header.
     *
     * @param value header value.
     */
    public void setIfNoneMatch(final String value) {
        header.setHeader(ODataHeader.HeaderName.ifNoneMatch.toString(), value);
    }

    /**
     * Adds
     * <code>Preferred</code> OData request header.
     *
     * @param value header value.
     */
    public void setPreferred(final String value) {
        header.setHeader(ODataHeader.HeaderName.preferred.toString(), value);
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
