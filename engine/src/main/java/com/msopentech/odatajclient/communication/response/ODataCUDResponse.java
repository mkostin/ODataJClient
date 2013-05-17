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

import com.msopentech.odatajclient.utils.ODataResultSet;

class ODataCUDResponse extends ODataResponse {

    /**
     * Get modified/deleted/created object reference or modified/created object itself.
     * The returned value depends from the request header
     * <code>Prefer</code> value:
     * 'return-content' for modified/created entity; 'return-no-content' (default value) for no content.
     *
     * @return modified/created/deleted object reference or modified/created object.
     */
    @Override
    public ODataResultSet getBody() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
