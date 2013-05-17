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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ODataHeader {

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

    private final ODataFormat format;

    private final Map<String, String> headers = new HashMap<String, String>();

    public ODataHeader() {
        // initialize header from configuration or by using defaul parameters
        this.format = ODataFormat.ATOM;
    }

    public ODataFormat getFormat() {
        return format;
    }

    public ODataHeader setHeader(final String name, final String value) {
        headers.put(name, value);
        return this;
    }

    public Set<Map.Entry<String, String>> getHeaders() {
        return headers.entrySet();
    }

    public String getHeader(final String name) {
        return headers.get(name);
    }
}
