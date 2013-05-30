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
package com.msopentech.odatajclient.engine.client.request;

import com.msopentech.odatajclient.engine.communication.request.ODataChangeset;
import com.msopentech.odatajclient.engine.communication.request.ODataCreateEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.ODataDeleteRequest;
import com.msopentech.odatajclient.engine.communication.request.ODataUpdateEntityRequest;

/**
 * Changeset wrapper for the corresponding batch item.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getChangesetRequest()
 */
class ODataChangesetImpl extends ODataBatchRequestItemImpl implements ODataChangeset {

    /**
     * Constructor.
     */
    ODataChangesetImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ODataChangeset addRequest(final ODataCreateEntityRequest request) {
        requests.add(request);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ODataChangeset addRequest(final ODataUpdateEntityRequest request) {
        requests.add(request);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ODataChangeset addRequest(final ODataDeleteRequest request) {
        requests.add(request);
        return this;
    }
}
