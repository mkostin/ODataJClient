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
 * Changeset wrapper for the corresponding batch item.
 * Get instance by using ODataResponseFactory.
 *
 * @see ODataResponseFactory#getChangesetBatchItem()
 */
public class ODataChangeset extends ODataBatchResponseItem {

    /**
     * Constructor.
     */
    ODataChangeset() {
    }

    /**
     * Add a create response to the changeset.
     *
     * @param response create response to be added.
     * @return the current updated changeset.
     */
    public ODataChangeset addResponse(final ODataCreateEntityResponse response) {
        responses.add(response);
        return this;
    }

    /**
     * Add an update response to the changeset.
     *
     * @param response update response to be added.
     * @return the current updated changeset.
     */
    public ODataChangeset addResponse(final ODataUpdateEntityResponse response) {
        responses.add(response);
        return this;
    }

    /**
     * Add a delete response to the changeset.
     *
     * @param response delete request to be added.
     * @return the current updated changeset.
     */
    public ODataChangeset addResponse(final ODataDeleteResponse response) {
        responses.add(response);
        return this;
    }
}
