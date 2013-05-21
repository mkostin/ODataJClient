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
package com.msopentech.odatajclient.engine.communication.response;

import com.msopentech.odatajclient.engine.communication.header.ODataHeader;
import com.msopentech.odatajclient.engine.utils.ODataResultSet;
import java.util.Map;

/**
 * Abstract representation of an OData response.
 */
public abstract class ODataResponse {

    /**
     * Response status code.
     */
    protected int statusCode;

    /**
     * Response status message.
     */
    protected String statusMessage;

    /**
     * Response header.
     */
    protected ODataHeader header;

    /**
     * Constructor.
     */
    public ODataResponse() {
        // initialize a default header from configuration
        this.header = new ODataHeader();
    }

    /**
     * Adds
     * <code>ETag</code> OData request header.
     *
     * @param value header value.
     * @see com.msopentech.odatajclient.engine.communication.header.ODataHeader.HeaderName#etag
     */
    protected void setETag(final String value) {
        header.setHeader(ODataHeader.HeaderName.etag, value);
    }

    /**
     * Adds
     * <code>Location</code> OData request header.
     *
     * @param value header value.
     * @see com.msopentech.odatajclient.engine.communication.header.ODataHeader.HeaderName#location
     */
    protected void setLocation(final String value) {
        header.setHeader(ODataHeader.HeaderName.location, value);
    }

    /**
     * Adds
     * <code>DataServiceId</code> OData request header.
     *
     * @param value header value.
     * @see com.msopentech.odatajclient.engine.communication.header.ODataHeader.HeaderName#dataServiceId
     */
    protected void setDataServiceId(final String value) {
        header.setHeader(ODataHeader.HeaderName.dataServiceId, value);
    }

    /**
     * Adds
     * <code>Preference-Applied</code> OData request header.
     *
     * @param value header value.
     * @see com.msopentech.odatajclient.engine.communication.header.ODataHeader.HeaderName#preferenceApplied
     */
    protected void setPreferenceApplied(final String value) {
        header.setHeader(ODataHeader.HeaderName.preferenceApplied, value);
    }

    /**
     * Adds
     * <code>Retry-After</code> OData request header.
     *
     * @param value header value.
     * @see com.msopentech.odatajclient.engine.communication.header.ODataHeader.HeaderName#retryAfter
     */
    protected void setRetryAfter(final String value) {
        header.setHeader(ODataHeader.HeaderName.retryAfter, value);
    }

    public Map<String, String> getHeaders() {
        return header.getHeaders();
    }

    public String getHeader(final String name) {
        return header.getHeader(name);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public abstract ODataResultSet getBody();
}
