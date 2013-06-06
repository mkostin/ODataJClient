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

import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import java.io.PipedOutputStream;

/**
 * Retrieve request wrapper for the corresponding batch item.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getRetrieveBatchItem()
 */
public class ODataRetrieve extends ODataBatchRequestItem {

    /**
     * Constructor.
     */
    ODataRetrieve(final PipedOutputStream os) {
        super(os);
    }

    @Override
    protected void closeItem() {
        // serialize and stream close statement
    }

    public <T extends ODataStreamingManagement, V extends ODataResponse> ODataRetrieve setRequest(ODataBatchableRequest request) {
        if (((ODataRequestImpl) request).getMethod() != ODataRequest.Method.GET) {
            throw new IllegalArgumentException("Invalid request. Only GET method is allowed");
        }

//        ((ODataRequestImpl) request).initializeBatchItem(os);
        return this;
    }
}
