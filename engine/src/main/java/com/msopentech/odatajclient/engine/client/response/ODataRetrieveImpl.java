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

import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieve;
import java.util.Iterator;

/**
 * Retrieve response wrapper for the corresponding batch item.
 * Get instance by using ODataResponseFactory.
 *
 * @see ODataResponseFactory#getRetrieveBatchItem(
 * com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse)
 */
class ODataRetrieveImpl extends ODataBatchResponseItemImpl implements ODataRetrieve {

    /**
     * Constructor.
     */
    ODataRetrieveImpl(final ODataQueryResponse response) {
        super.addResponse(response);
    }

    @Override
    public Iterator<ODataResponse> getResponse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
