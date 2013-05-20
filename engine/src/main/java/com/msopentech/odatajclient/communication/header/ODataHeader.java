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
package com.msopentech.odatajclient.communication.header;

import com.msopentech.odatajclient.types.ODataFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ODataHeader wraps OData request/response headers.
 *
 * @see com.msopentech.odatajclient.communication.request.ODataRequest
 * @see com.msopentech.odatajclient.communication.response.ODataResponse
 */
public class ODataHeader {

    /**
     * Major OData request/response header names.
     */
    public enum HeaderName {

        dataServiceVersion("DataServiceVersion"),
        contentType("Content-Type"),
        maxDataServiceVersion("MaxDataServiceVersion"),
        minDataServiceVersion("MinDataServiceVersion"),
        accept("Accept"),
        ifMatch("If-Match"),
        ifNoneMatch("If-None-Match"),
        preferred("Preferred"),
        etag("ETag"),
        location("Location"),
        dataServiceId("DataServiceId"),
        preferenceApplied("Preference-Applied"),
        retryAfter("Retry-After");

        private final String headerName;

        private HeaderName(final String headerName) {
            this.headerName = headerName;
        }

        @Override
        public String toString() {
            return headerName;
        }
    }

    /**
     * OData resource representation format.
     */
    private final ODataFormat format;

    /**
     * OData request/response heder key/value pairs.
     */
    private final Map<String, String> headers = new HashMap<String, String>();

    /**
     * Constructor.
     */
    public ODataHeader() {
        // initialize header from configuration or by using defaul parameters
        this.format = ODataFormat.ATOM;
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
     * Add the specified header.
     *
     * @param name header key.
     * @param value header value.
     * @return the current updated header instance.
     */
    public ODataHeader setHeader(final String name, final String value) {
        headers.put(name, value);
        return this;
    }

    /**
     * Gets all headers.
     *
     * @return unmodifiable map containing all headers.
     */
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    /**
     * Gets the value of the header identified by the given name.
     *
     * @param name name of the header to be retrieved.
     * @return header value.
     */
    public String getHeader(final String name) {
        return headers.get(name);
    }
}
