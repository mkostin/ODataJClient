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
 * This class implements a response to a specific query request.
 *
 * @see ODataResponseFactory#getODataQueryResponse()
 * @see com.msopentech.odatajclient.engine.communication.request.ODataQueryRequest
 */
public class ODataQueryResponse extends ODataResponse {

    ODataQueryResponse() {
    }

    /**
     * Gets query result objects.
     *
     * @return query result objects.
     */
    public <T> ODataResultSet<T> getBody() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
