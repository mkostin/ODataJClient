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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a single changeset of a bach request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getChangesetRequest().I
 */
public class ODataChangesetRequest extends ODataRequest {

    /**
     * Changeset content.
     */
    public final List<ODataRequest> changeset = new ArrayList<ODataRequest>();

    /**
     * Contructor.
     * <p>
     * A changeset request doesn't specify any HTTP method.
     */
    ODataChangesetRequest() {
        // changeset doesn't require method specification ...
        super(Method.POST);
    }

    /**
     * Add a create request to the changeset.
     *
     * @param request create request to be added.
     * @return the current updated changeset request.
     */
    public ODataChangesetRequest addRequest(final ODataCreateRequest request) {
        changeset.add(request);
        return this;
    }

    /**
     * Add an update request to the changeset.
     *
     * @param request update request to be added.
     * @return the current updated changeset request.
     */
    public ODataChangesetRequest addRequest(final ODataUpdateRequest request) {
        changeset.add(request);
        return this;
    }

    /**
     * Add a delete request to the changeset.
     *
     * @param request delete request to be added.
     * @return the current updated changeset request.
     */
    public ODataChangesetRequest addRequest(final ODataDeleteRequest request) {
        changeset.add(request);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getBody() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
