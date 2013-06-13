/*
 * Copyright 2013 MS OpenTech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msopentech.odatajclient.engine.communication.request.invoke;

import com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method;
import com.msopentech.odatajclient.engine.communication.request.OperationType;
import com.msopentech.odatajclient.engine.data.ODataValue;
import java.net.URI;
import java.util.Map;

/**
 * OData request factory class.
 */
public class ODataInvokeRequestFactory {

    /**
     * Gets an invoke action request instance.
     *
     * @param uri URI that identifies the action.
     * @param parameters required input parameters.
     * @return new ODataInvokeRequest instance.
     */
    public static ODataInvokeRequest getInvokeActionRequest(final URI uri, Map<String, ODataValue> parameters) {
        return new ODataInvokeRequest(Method.POST, uri, OperationType.ACTION);
    }

    /**
     * Gets an invoke function request instance.
     *
     * @param uri URI that identifies the function.
     * @return new ODataInvokeRequest instance.
     */
    public static ODataInvokeRequest getInvokeFunctionRequest(final URI uri) {
        return new ODataInvokeRequest(Method.GET, uri, OperationType.FUNCTION);
    }

    /**
     * Gets an invoke legacy operation request instance.
     *
     * @param method HTTP method of the request.
     * @param uri URI that identifies the function.
     * @param parameters required input parameters.
     * @return new ODataInvokeRequest instance.
     */
    public static ODataInvokeRequest getInvokeLegacyRequest(final Method method, final URI uri) {
        return new ODataInvokeRequest(method, uri, OperationType.LEGACY);
    }
}
