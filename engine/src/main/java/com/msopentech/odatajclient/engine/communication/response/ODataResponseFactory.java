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

/**
 * OData response factory.
 */
public class ODataResponseFactory {

    /**
     * Gets a response object instance for an entity create request.
     *
     * @return response object.
     */
    public static ODataCreateResponse getCreateResponse() {
        return new ODataCreateResponse();
    }

    /**
     * Gets a response object instance for a media entity create request.
     *
     * @return response object.
     */
    public static ODataMediaEntityCreateResponse getMediaEntityCreateResponse() {
        return new ODataMediaEntityCreateResponse();
    }

    /**
     * Gets a response object instance for a media entity update request.
     *
     * @return response object.
     */
    public static ODataMediaEntityUpdateResponse getMediaEntityUpdateResponse() {
        return new ODataMediaEntityUpdateResponse();
    }

    /**
     * Gets a response object instance for an update request.
     *
     * @return response object.
     */
    public static ODataUpdateResponse getUpdateResponse() {
        return new ODataUpdateResponse();
    }

    /**
     * Gets a response object instance for a delete request.
     *
     * @return response object.
     */
    public static ODataDeleteResponse getDeleteResponse() {
        return new ODataDeleteResponse();
    }

    /**
     * Gets a response object instance for a query request.
     *
     * @return response object.
     */
    public static ODataQueryResponse getQueryResponse() {
        return new ODataQueryResponse();
    }

    /**
     * Gets a response object instance for a query request returning a single result item.
     *
     * @return response object.
     */
    public static ODataSingleResultResponse getSingleResultResponse() {
        return new ODataSingleResultResponse();
    }

    /**
     * Gets a response object instance for an invoke request.
     *
     * @return response object.
     */
    public static ODataInvokeResponse getInvokeResponse() {
        return new ODataInvokeResponse();
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
     * Gets a changeset batch item instance.
     *
     * @return ODataChangeset instance.
     */
    public static ODataChangeset getChangesetBatchItem() {
        return new ODataChangeset();
    }

    /**
     * Gets a retrieve batch item instance.
     *
     * @return ODataRetrieve instance.
     */
    public static ODataRetrieve getRetrieveBatchItem(final ODataQueryResponse response) {
        return new ODataRetrieve(response);
    }

    /**
     * Gets a response object instance for a metadata request.
     *
     * @return ODataMetadataResponse instance.
     */
    public static ODataMetadataResponse getMetadataResponse() {
        return new ODataMetadataResponse();
    }

    /**
     * Gets a response object instance for a link operation request.
     *
     * @return response object.
     */
    public static ODataLinkOperationResponse getLinkOperationResponse() {
        return new ODataLinkOperationResponse();
    }
}
