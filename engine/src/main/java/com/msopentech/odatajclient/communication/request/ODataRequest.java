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
package com.msopentech.odatajclient.communication.request;

import com.msopentech.odatajclient.communication.header.ODataHeader;
import com.msopentech.odatajclient.data.ODataURI;
import java.io.InputStream;
import java.util.Map;

public abstract class ODataRequest {

    public enum Method {

        GET,
        POST,
        DELETE,
        PUT,
        PATCH,
        MERGE

    }

    final protected Method method;

    protected ODataHeader header;

    protected ODataURI uri;

    protected ODataRequest(final Method method) {
        this.method = method;
        // initialize a default header from configuration
        this.header = new ODataHeader();
    }

    public ODataURI getUri() {
        return uri;
    }

    public Map<String, String> getHeaders() {
        return header.getHeaders();
    }

    public String getHeader(final String name) {
        return header.getHeader(name);
    }

    public void setMaxDataServiceVersion(final String value) {
        header.setHeader(ODataHeader.HeaderName.maxDataServiceVersion.toString(), value);
    }

    public void setMinDataServiceVersion(final String value) {
        header.setHeader(ODataHeader.HeaderName.minDataServiceVersion.toString(), value);
    }

    public void setAccept(final String value) {
        header.setHeader(ODataHeader.HeaderName.accept.toString(), value);
    }

    public void setIfMatch(final String value) {
        header.setHeader(ODataHeader.HeaderName.ifMatch.toString(), value);
    }

    public void setIfNoneMatch(final String value) {
        header.setHeader(ODataHeader.HeaderName.ifNoneMatch.toString(), value);
    }

    public void setPreferred(final String value) {
        header.setHeader(ODataHeader.HeaderName.preferred.toString(), value);
    }

    public void addCustomHeader(final String name, final String value) {
        header.setHeader(name, value);
    }

    /**
     * Get request body.
     *
     * @return request body stream.
     */
    public abstract InputStream getBody();
}
