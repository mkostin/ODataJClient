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
package com.msopentech.odatajclient.engine.client.response;

import com.msopentech.odatajclient.engine.communication.response.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract representation of a response item about a batch request.
 */
abstract class ODataBatchResponseItemImpl implements ODataBatchResponseItem {

    /**
     * Batch item response.
     */
    private final List<ODataResponse> responses = new ArrayList<ODataResponse>();

    /**
     * Add a new response (for internal usage only).
     *
     * @param response response to be added.
     */
    protected final void addResponse(final ODataResponse response) {
        responses.add(response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<ODataResponse> getResponse() {
        return responses.iterator();
    }
}
