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
package com.msopentech.odatajclient.spi;

import com.msopentech.odatajclient.communication.request.ODataRequest;
import com.msopentech.odatajclient.communication.response.ODataResponse;
import com.msopentech.odatajclient.types.ODataFormat;

/**
 * RESTFull OData client implementation.
 */
public class ODataRestClient implements ODataClient {

    public ODataRestClient() {
    }

    /**
     * Execute the given request.
     *
     * @param request Request.
     * @return Response.
     */
    @Override
    public ODataResponse execute(final ODataRequest request) {
        return null;
    }
}
