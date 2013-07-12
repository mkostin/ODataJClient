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

import com.msopentech.odatajclient.engine.communication.header.ODataHeaders;
import com.msopentech.odatajclient.engine.communication.response.ODataBatchResponse;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ODataBatchResponseManager implements Iterator<ODataBatchResponseItem> {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ODataBatchResponseManager.class);

    private final ODataBatchLineIterator batchLineIterator;

    private final String batchBoundary;

    private final Iterator<ODataBatchResponseItem> expectedItemsIterator;

    private ODataBatchResponseItem current = null;

    public ODataBatchResponseManager(final ODataBatchResponse res, final List<ODataBatchResponseItem> expectedItems) {
        try {
            this.expectedItemsIterator = expectedItems.iterator();
            this.batchLineIterator = new ODataBatchLineIterator(
                    IOUtils.lineIterator(res.getRawResponse(), ODataConstants.UTF8));

            // search for boundary
            batchBoundary = ODataBatchUtilities.getBoundaryFromHeader(
                    res.getHeader(ODataHeaders.HeaderName.contentType));
            LOG.debug("Retrieved batch response bondary '{}'", batchBoundary);
        } catch (IOException e) {
            LOG.error("Error parsing batch response", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean hasNext() {
        return expectedItemsIterator.hasNext();
    }

    @Override
    public ODataBatchResponseItem next() {
        if (current != null) {
            current.close();
        }

        if (!hasNext()) {
            throw new NoSuchElementException("No item found");
        }

        current = expectedItemsIterator.next();

        final Map<String, Collection<String>> nextItemHeaders =
                ODataBatchUtilities.nextItemHeaders(batchLineIterator, batchBoundary);

        switch (ODataBatchUtilities.getItemType(nextItemHeaders)) {
            case CHANGESET:
                if (!current.isChangeset()) {
                    throw new IllegalStateException("Unexpected batch item");
                }

                current.initFromBatch(
                        batchLineIterator,
                        ODataBatchUtilities.getBoundaryFromHeader(
                        nextItemHeaders.get(ODataHeaders.HeaderName.contentType.toString())));
                break;

            case RETRIEVE:
                if (current.isChangeset()) {
                    throw new IllegalStateException("Unexpected batch item");
                }

                current.initFromBatch(
                        batchLineIterator,
                        batchBoundary);
                break;
            default:
                throw new IllegalStateException("Expected item not found");
        }

        return current;
    }

    /**
     * Unsupported operation.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove operation is not supported");
    }
}
