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

import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract representation of a response item about a batch request.
 */
public abstract class ODataBatchResponseItem implements Iterator<ODataResponse> {

    /**
     * Logger.
     */
    protected static final Logger LOG = LoggerFactory.getLogger(ODataBatchResponseItem.class);

    protected final Map<String, ODataResponse> responses = new HashMap<String, ODataResponse>();

    protected Iterator<ODataResponse> expectedItemsIterator;

    private final boolean changeset;

    protected ODataBatchLineIterator batchLineIterator;

    protected String boundary;

    protected boolean closed = false;

    public ODataBatchResponseItem(boolean isChangeset) {
        this.changeset = isChangeset;
    }

    void addResponse(final String contentId, final ODataResponse res) {
        if (closed) {
            throw new IllegalStateException("Invalid batch item because explicitely closed");
        }
        responses.put(contentId, res);
    }

    void initFromBatch(final ODataBatchLineIterator batchLineIterator, final String boundary) {
        if (closed) {
            throw new IllegalStateException("Invalid batch item because explicitely closed");
        }
        LOG.debug("Init from batch - boundary '{}'", boundary);
        this.batchLineIterator = batchLineIterator;
        this.boundary = boundary;
    }

    protected ODataResponse getResponse(final String contentId) {
        if (closed) {
            throw new IllegalStateException("Invalid batch item because explicitely closed");
        }
        return responses.get(contentId);
    }

    protected Iterator<ODataResponse> getResponseIterator() {
        if (closed) {
            throw new IllegalStateException("Invalid batch item because explicitely closed");
        }
        return responses.values().iterator();
    }

    /**
     * Returns 'TRUE' if the item is a changeset.
     *
     * @return 'TRUE' if the item is a changeset; 'FALSE' otherwise.
     */
    public final boolean isChangeset() {
        return changeset;
    }

    public void close() {
        for (ODataResponse response : responses.values()) {
            response.close();
        }
        closed = true;
    }
}
