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

import com.msopentech.odatajclient.engine.communication.request.ODataStreamer;
import com.msopentech.odatajclient.engine.utils.ODataBatchConstants;

/**
 * Abstract representation of a batch request item.
 * Get instance by using ODataRequestFactory.
 */
public abstract class ODataBatchRequestItem extends ODataStreamer {

    protected boolean hasStreamedSomething = false;

    private boolean open = false;

    protected ODataBatchRequest req;

    public ODataBatchRequestItem(final ODataBatchRequest req) {
        super(req.getOutputStream());
        this.open = true;
        this.req = req;
    }

    public boolean isOpen() {
        return open;
    }

    public void close() {
        closeItem();
        open = false;
    }

    protected void streamRequestHeader(final ODataBatchableRequest request, final int contentId) {
        //stream batch content type
        stream(ODataBatchConstants.ITEM_CONTENT_TYPE_LINE.getBytes());
        newLine();
        stream(ODataBatchConstants.ITEM_TRANSFER_ENCODING_LINE.getBytes());
        newLine();
        stream((ODataBatchConstants.CHANGESET_CONTENT_ID_NAME + ":" + contentId).getBytes());
        newLine();
        newLine();
    }

    protected void streamRequestHeader(final ODataBatchableRequest request) {
        //stream batch content type
        stream(ODataBatchConstants.ITEM_CONTENT_TYPE_LINE.getBytes());
        newLine();
        stream(ODataBatchConstants.ITEM_TRANSFER_ENCODING_LINE.getBytes());
        newLine();
        newLine();

        stream(request.toByteArray());
        newLine();
    }

    public boolean hasStreamedSomething() {
        return hasStreamedSomething;
    }

    protected abstract void closeItem();
}
