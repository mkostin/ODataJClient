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
package com.msopentech.odatajclient.engine.communication.request.batch;

import com.msopentech.odatajclient.engine.communication.request.ODataRequest;
import com.msopentech.odatajclient.engine.communication.request.ODataRequestImpl;

/**
 * Retrieve request wrapper for the corresponding batch item.
 */
public class ODataRetrieve extends ODataBatchRequestItem {

    private final ODataRetrieveResponseItem expectedResItem;

    /**
     * Constructor.
     * <p>
     * Send GET request header.
     *
     * @param os piped output stream to be used to serialize.
     */
    ODataRetrieve(final ODataBatchRequest req, final ODataRetrieveResponseItem expectedResItem) {
        super(req);
        this.expectedResItem = expectedResItem;
    }

    /**
     * Close retrieve statement an send retrieve request footer.
     */
    @Override
    protected void closeItem() {
        // nop
    }

    /**
     * Serialize and send the given request.
     * <p>
     * An IllegalArgumentException is thrown in case of the given request is not a GET.
     *
     * @param request request to be serialized.
     * @return current retrieve item instance.
     */
    public ODataRetrieve setRequest(final ODataBatchableRequest request) {
        if (!isOpen()) {
            throw new IllegalStateException("Current batch item is closed");
        }

        if (((ODataRequestImpl) request).getMethod() != ODataRequest.Method.GET) {
            throw new IllegalArgumentException("Invalid request. Only GET method is allowed");
        }

        hasStreamedSomething = true;

        // stream the request
        streamRequestHeader(request);

        // close before in order to avoid any further setRequest calls.
        close();

        // add request to the list
        expectedResItem.addResponse(
                ODataRetrieveResponseItem.RETRIEVE_CONTENT_ID, ((ODataRequestImpl) request).getResponseTemplate());

        return this;
    }
}
