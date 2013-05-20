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

/**
 * OData response factory.
 */
public class ODataResponseFactory {

    /**
     * Gets a response object instance for a CUD request.
     *
     * @return response object.
     */
    public static ODataResponse getODataCUDResponse() {
        return new ODataCUDResponse();
    }

    /**
     * Gets a response object instance for a query request.
     *
     * @return response object.
     */
    public static ODataResponse getODataQueryResponse() {
        return new ODataQueryResponse();
    }

    /**
     * Gets a response object instance for a batch request.
     *
     * @return response object.
     */
    public static ODataBatchResponse getBatchResponse() {
        return new ODataBatchResponse();
    }

    /**
     * Gets a response object instance for a specific changeset of a batch request.
     *
     * @return response obejct.
     */
    public static ODataChangesetResponse getChangesetResponse() {
        return new ODataChangesetResponse();
    }
}
