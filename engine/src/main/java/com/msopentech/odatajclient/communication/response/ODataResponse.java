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
package com.msopentech.odatajclient.communication.response;

import com.msopentech.odatajclient.communication.header.ODataHeader;

public abstract class ODataResponse {

    protected ODataHeader header;

    public ODataResponse() {
        // initialize a default header from configuration
        this.header = new ODataHeader();
    }

    public void setETag(final String value) {
        header.setHeader(ODataHeader.HeaderName.etag.toString(), value);
    }

    public void setLocation(final String value) {
        header.setHeader(ODataHeader.HeaderName.location.toString(), value);
    }

    public void setDataServiceId(final String value) {
        header.setHeader(ODataHeader.HeaderName.dataServiceId.toString(), value);
    }

    public void setPreferredApplied(final String value) {
        header.setHeader(ODataHeader.HeaderName.preferenceApplied.toString(), value);
    }

    public void setRetryAfter(final String value) {
        header.setHeader(ODataHeader.HeaderName.retryAfter.toString(), value);
    }
}
