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

import java.util.Collection;

/**
 * Abstract representation of an OData response.
 */
public interface ODataResponse {

    /**
     * Gets header names.
     *
     * @return response header names.
     */
    Collection<String> getHeaderNames();

    /**
     * Gets header value of the given header.
     *
     * @param name header to be retrieved.
     * @return response header value.
     */
    String getHeader(final String name);

    /**
     * Gets status code.
     *
     * @return status code.
     */
    int getStatusCode();

    /**
     * Gets status message.
     *
     * @return status message.
     */
    String getStatusMessage();
}
