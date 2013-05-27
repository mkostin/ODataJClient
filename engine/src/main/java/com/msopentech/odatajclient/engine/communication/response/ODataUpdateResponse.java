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

import com.msopentech.odatajclient.engine.data.ODataEntity;

/**
 * This class implements the response to an OData update request.
 *
 * @see ODataResponseFactory#getUpdateResponse()
 * @see com.msopentech.odatajclient.communication.request.ODataUpdateRequest
 */
public class ODataUpdateResponse extends ODataResponse {

    ODataUpdateResponse() {
    }

    /**
     * Gets updated object.
     * <p>
     * The returned value depends on the
     * <code>Prefer</code> header attribute value:
     * <ul>
     * <li><code>return-content</code> for full access to the updated entity;</li>
     * <li><code>return-no-content</code> for no content.</li>
     * </ul>
     *
     * @return updated object reference or updated object.
     * @see com.msopentech.odatajclient.engine.communication.header.ODataHeader.HeaderName#prefer
     */
    public ODataEntity getBody() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
