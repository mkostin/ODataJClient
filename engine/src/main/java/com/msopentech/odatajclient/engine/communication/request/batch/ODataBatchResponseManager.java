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

import com.msopentech.odatajclient.engine.communication.response.ODataBatchResponse;
import com.msopentech.odatajclient.engine.utils.ODataBatchConstants;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ODataBatchResponseManager implements Iterator<ODataBatchRequestItem> {

    private enum BatchItemType {

        NONE,
        CHANGESET,
        ITEM

    }

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ODataBatchResponseManager.class);

    private final Pattern RESPONSE_PATTERN = Pattern.compile("HTTP/\\d\\.\\d (\\d+) (.*)", Pattern.CASE_INSENSITIVE);

    private final LineIterator lineIterator;

    private String boundary;

    private BatchItemType nextItem = BatchItemType.NONE;

    private ODataBatchRequestItem current;

    public ODataBatchResponseManager(final ODataBatchResponse res) {
        try {
            this.lineIterator = IOUtils.lineIterator(res.getRawResponse(), "UTF-8");
            init(res);
            retrieveNext();
        } catch (IOException e) {
            LOG.error("Error parsing batch response", e);
            throw new IllegalStateException(e);
        }
    }

    private void init(final ODataBatchResponse res) {
        // search for boundary

        // to be sure to ignore case
        final Collection<String> headers = res.getHeaderNames();

        for (Iterator<String> iter = headers.iterator(); iter.hasNext() && boundary == null;) {
            final String header = iter.next();
            if (header.equalsIgnoreCase(HttpHeaders.CONTENT_TYPE)) {
                boundary = getBoundaryFromHeader(res.getHeader(header));
            }
        }

        consume();
    }

    private void retrieveNext() {
        final Map<String, Collection<String>> headers =
                new TreeMap<String, Collection<String>>(String.CASE_INSENSITIVE_ORDER);
        ODataBatchUtilities.readHeaders(lineIterator, headers);

        if (headers.get(HttpHeaders.CONTENT_TYPE).contains(ODataBatchConstants.MULTIPART_CONTENT_TYPE)) {
            nextItem = BatchItemType.CHANGESET;
        } else if (headers.get(HttpHeaders.CONTENT_TYPE).contains(ODataBatchConstants.ITEM_CONTENT_TYPE)) {
            nextItem = BatchItemType.ITEM;
        } else {
            nextItem = BatchItemType.NONE;
        }
    }

    private void consume() {
        while (lineIterator.hasNext() && !lineIterator.nextLine().equals(boundary)) {
            // do nothing till the start
        }
    }

    private String getBoundaryFromHeader(final Collection<String> contentType) {
        final String boundaryKey = ODataBatchConstants.BOUNDARY + "=";

        if (contentType == null || contentType.isEmpty() || !contentType.toString().contains(boundaryKey)) {
            throw new IllegalArgumentException("Invalid content type");
        }

        final String headerValue = contentType.toString();

        final int start = headerValue.indexOf(boundaryKey) + boundaryKey.length() + 1;
        int end = headerValue.indexOf(";", start);

        if (end < 0) {
            end = headerValue.indexOf("]", start);
        }

        return headerValue.substring(start, end);
    }

    @Override
    public boolean hasNext() {
        return nextItem != BatchItemType.NONE;
    }

    @Override
    public ODataBatchRequestItem next() {
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
