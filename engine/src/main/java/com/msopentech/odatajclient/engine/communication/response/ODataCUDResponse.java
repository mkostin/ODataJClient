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

import com.msopentech.odatajclient.engine.utils.ODataResultSet;

/**
 * This class implements the response to an Odata Create/Update/Delete request.
 *
 * @see ODataResponseFactory#getODataCUDResponse()
 * @see com.msopentech.odatajclient.communication.request.ODataCreateRequest
 * @see com.msopentech.odatajclient.communication.request.ODataUpdateRequest
 * @see com.msopentech.odatajclient.communication.request.ODataDeleteRequest
 */
class ODataCUDResponse extends ODataResponse {

    /**
     * Gets created/update/deleted object reference or created/updfated object itself.
     * <p>
     * The returned value depends on the header
     * <code>Prefer</code> header value:
     * <ul>
     * <li><code>return-content</code> for full access to the created/updated entity;</li>
     * <li><code>return-no-content</code> for no content (default value).</li>
     * </ul>
     *
     * @return created/updated/deleted object reference or created/updated object.
     * @see com.msopentech.odatajclient.communication.header.ODataHeader.
     */
    @Override
    public ODataResultSet getBody() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
