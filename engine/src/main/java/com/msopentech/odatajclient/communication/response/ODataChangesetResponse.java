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
 * This class implements a response to a changeset request.
 *
 * @see ODataResponseFactory#getChangesetResponse()
 * @see com.msopentech.odatajclient.communication.request.ODataChangesetRequest
 */
class ODataChangesetResponse extends ODataResponse {

    /**
     * Changeset response content.
     */
    private final List<ODataResponse> changeset = new ArrayList<ODataResponse>();

    /**
     * Adds a CUD response to the changeset.
     * <p>
     * Each CUD response is about a Create/Update/Delete request submitted embedded into a changeset of a batch request.
     *
     * @param response CUD response to be added.
     * @return the current changeset request instance.
     *
     * @see ODataCUDResponse.
     */
    protected ODataChangesetResponse addResponse(final ODataCUDResponse response) {
        changeset.add(response);
        return this;
    }

    /**
     * Get all responses related to the specific changeset.
     *
     * @return a result set of ODataResponse instances.
     */
    @Override
    public ODataResultSet getBody() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
