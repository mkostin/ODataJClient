/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.engine.communication.request.batch;

import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;

/**
 * OData batch request factory class.
 */
public final class ODataBatchRequestFactory {

    private ODataBatchRequestFactory() {
        // Empty private constructor for static utility classes
    }

    /**
     * Gets a batch request object instance.
     *
     * @param serviceRoot service root.
     * @return new ODataBatchRequest instance.
     */
    public static ODataBatchRequest getBatchRequest(final String serviceRoot) {
        return new ODataBatchRequest(new ODataURIBuilder(serviceRoot).appendBatchSegment().build());
    }
}
