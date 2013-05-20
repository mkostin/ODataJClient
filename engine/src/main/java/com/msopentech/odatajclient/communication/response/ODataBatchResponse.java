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
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a response to a batch request.
 *
 * @see ODataResponseFactory#getBatchResponse()
 * @see com.msopentech.odatajclient.communication.request.ODataBatchRequest
 */
public class ODataBatchResponse extends ODataResponse {

    /**
     * Batch response content.
     */
    private final List<ODataResponse> batch = new ArrayList<ODataResponse>();

    /**
     * Constructor.
     */
    ODataBatchResponse() {
    }

    /**
     * Adds a query response to the batch response.
     * <p>
     * Each query response is about a query request submitted embedded into a batch request.
     *
     * @param response query response to be added.
     * @return the current batch response.
     *
     * @see ODataQueryResponse.
     */
    protected ODataBatchResponse addResponse(final ODataQueryResponse response) {
        batch.add(response);
        return this;
    }

    /**
     * Add a changeset response to the batch response.
     *
     * @param response changeset response to be added.
     * @return the current batch response.
     */
    protected ODataBatchResponse addResponse(final ODataChangesetResponse response) {
        batch.add(response);
        return this;
    }

    /**
     * Get all responses related to the batch request.
     *
     * @return a result set of ODataResponse instances.
     */
    @Override
    public ODataResultSet getBody() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
