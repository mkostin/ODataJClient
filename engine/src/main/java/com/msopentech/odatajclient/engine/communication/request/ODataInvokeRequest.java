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
package com.msopentech.odatajclient.engine.communication.request;

import com.msopentech.odatajclient.engine.client.request.ODataRequestFactory;
import java.io.InputStream;
import java.util.Map;

/**
 * This class implements an OData invoke operation request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getInvokeActionRequest(com.msopentech.odatajclient.engine.data.ODataURI, java.util.Map)
 * @see ODataRequestFactory#getInvokeFunctionRequest(com.msopentech.odatajclient.engine.data.ODataURI)
 * @see ODataRequestFactory#getInvokeLegacyRequest(
 * com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method,
 * com.msopentech.odatajclient.engine.data.ODataURI, java.util.Map)
 */
public interface ODataInvokeRequest extends ODataRequest {

    /**
     * {@inheritDoc}
     */
    @Override
    InputStream getBody();
}
