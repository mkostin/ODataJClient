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

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract representation of a batch request item.
 * Get instance by using ODataRequestFactory.
 */
public abstract class ODataBatchRequestItem extends ODataStreamer {

    /**
     * item content type.
     */
    protected static String CONTENT_TYPE = "Content-Type: application/http";

    /**
     * item transfer encoding.
     */
    protected static String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding: binary";

    /**
     * Content id header name.
     */
    private static String CONTENT_ID_NAME = "Content-Id";

    /**
     * Batched requests.
     */
    protected final List<ODataRequest> requests = new ArrayList<ODataRequest>();

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
        stream(CONTENT_TYPE.getBytes());
        newLine();
        stream(CONTENT_TRANSFER_ENCODING.getBytes());
        newLine();
        stream((CONTENT_ID_NAME + ":" + contentId).getBytes());
        newLine();
        newLine();
    }

    protected void streamRequestHeader(final ODataBatchableRequest request) {
        //stream batch content type
        stream(CONTENT_TYPE.getBytes());
        newLine();
        stream(CONTENT_TRANSFER_ENCODING.getBytes());
        newLine();
        newLine();

        stream(request.toByteArray());
        newLine();
    }

    protected abstract void closeItem();
}
