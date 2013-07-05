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

import static com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchResponseItem.LOG;
import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Retrieve response wrapper for the corresponding batch item.
 */
public class ODataRetrieveResponseItem extends ODataBatchResponseItem {

    public static final String RETRIEVE_CONTENT_ID = "__RETRIEVE__";

    public ODataRetrieveResponseItem() {
        super(false);
    }

    @Override
    public boolean hasNext() {
        if (closed) {
            throw new IllegalStateException("Invalid request - the item has been closed");
        }

        if (expectedItemsIterator == null) {
            expectedItemsIterator = responses.values().iterator();
        }

        return expectedItemsIterator.hasNext();
    }

    @Override
    public ODataResponse next() {
        if (closed) {
            throw new IllegalStateException("Invalid request - the item has been closed");
        }

        if (!hasNext()) {
            throw new NoSuchElementException("No item found");
        }

        final Map.Entry<Integer, String> responseLine = ODataBatchUtilities.readResponseLine(batchLineIterator);
        LOG.debug("Retrieved item response {}", responseLine);

        final Map<String, Collection<String>> headers = ODataBatchUtilities.readHeaders(batchLineIterator);
        LOG.debug("Retrieved item headers {}", headers);

        return expectedItemsIterator.next().initFromBatch(responseLine, headers, batchLineIterator, boundary);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation not supported.");
    }
}
