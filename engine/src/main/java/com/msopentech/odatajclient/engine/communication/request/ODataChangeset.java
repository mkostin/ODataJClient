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

import com.msopentech.odatajclient.engine.communication.header.ODataHeader.HeaderName;
import com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method;
import java.io.PipedOutputStream;
import java.util.UUID;

/**
 * Changeset wrapper for the corresponding batch item.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getChangesetRequest()
 */
public class ODataChangeset extends ODataBatchRequestItem {

    private static String CONTENT_TYPE_MULTIPART = "multipart/mixed";

    private int contentId = 0;

    private final String boundary;

    /**
     * Constructor.
     * <p>
     * Send a changeset header.
     *
     * @param os piped output stream to be used to serialize.
     */
    ODataChangeset(final PipedOutputStream os) {
        super(os);

        // create a random UUID value for boundary
        boundary = "changeset_" + UUID.randomUUID().toString();

        stream((HeaderName.contentType.toString() + ": " + CONTENT_TYPE_MULTIPART
                + "; boundary: " + boundary).getBytes());

        newLine();
        newLine();
    }

    /**
     * Close changeset item an send changeset request footer.
     */
    @Override
    protected void closeItem() {
        // stream close-delimiter
        newLine();
        stream(("--" + boundary + "--").getBytes());
        newLine();
        newLine();
    }

    /**
     * Serialize and send the given request.
     * <p>
     * An IllegalArgumentException is thrown in case of the given request is a GET.
     *
     * @param request request to be serialized.
     * @return current changeset item instance.
     */
    public ODataChangeset addRequest(final ODataBatchableRequest request) {
        if (!isOpen()) {
            throw new IllegalStateException("Current batch item is closed");
        }

        if (((ODataRequestImpl) request).getMethod() == Method.GET) {
            throw new IllegalArgumentException("Invalid request. GET method not allowed in changeset");
        }

        contentId++;

        // preamble
        newLine();

        // stream batch-boundary
        stream(("--" + boundary).getBytes());
        newLine();

        // stream the request
        streamRequestHeader(request, contentId);

        stream(("< payload ....>").getBytes());
        newLine();

        return this;
    }
}
