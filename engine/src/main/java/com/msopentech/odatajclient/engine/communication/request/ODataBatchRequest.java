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

import com.msopentech.odatajclient.engine.communication.request.ODataBatchRequest.BatchRequestPayload;
import com.msopentech.odatajclient.engine.communication.response.ODataBatchResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import java.net.URI;
import java.util.concurrent.Future;

/**
 * This class implements a batch request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getBatchRequest().
 */
public class ODataBatchRequest extends ODataStreamedRequestImpl<ODataBatchResponse, BatchRequestPayload> {

    /**
     * Constructor.
     */
    ODataBatchRequest(final URI uri) {
        super(Method.POST);
        this.uri = uri;
    }

    @Override
    public BatchRequestPayload execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static class BatchRequestPayload extends ODataStreamingManagement<ODataBatchResponse> {

        /**
         * Batch request current item.
         */
        private ODataBatchRequestItem currentItem = null;

        /**
         * Gets a changeset batch item instance.
         * A changeset can be submitted embedded into a batch request only.
         *
         * @return ODataChangeset instance.
         */
        public ODataChangeset addChangeset() {
            closeCurrentItem();
            currentItem = new ODataChangeset(bodyStreamWriter);
            return (ODataChangeset) currentItem;
        }

        /**
         * Gets a retrieve batch item instance.
         * A retrieve item can be submitted embedded into a batch request only.
         *
         * @return ODataRetrieve instance.
         */
        public ODataRetrieve addRetrieve() {
            closeCurrentItem();
            currentItem = new ODataRetrieve(bodyStreamWriter);
            return (ODataRetrieve) currentItem;
        }

        private BatchRequestPayload() {
        }

        public BatchRequestPayload setEntity(final ODataEntity entity) {
            return this;
        }

        private void closeCurrentItem() {
            if (currentItem != null && currentItem.isOpen()) {
                if (currentItem instanceof ODataChangeset) {
                } else {
                }
                currentItem.close();
            }
        }

        @Override
        public ODataBatchResponse getResponse() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Future<ODataBatchResponse> asyncResponse() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
