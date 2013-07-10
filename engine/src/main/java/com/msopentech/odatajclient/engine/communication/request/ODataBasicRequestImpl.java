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

import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import com.msopentech.odatajclient.engine.utils.Configuration;
import com.msopentech.odatajclient.engine.utils.ODataBatchConstants;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Basic request abstract implementation.
 *
 * @param <V> OData response type corresponding to the request implementation.
 */
public abstract class ODataBasicRequestImpl<V extends ODataResponse, T extends Enum<T>>
        extends ODataRequestImpl
        implements ODataBasicRequest<V, T> {

    /**
     * OData resource representation format.
     */
    private T format = null;

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Integer.valueOf(Configuration.getProperty(Configuration.REQ_EXEC_POOL_SIZE, "10")));

    /**
     * Constructor.
     *
     * @param method request method.
     * @param uri OData request URI.
     */
    public ODataBasicRequestImpl(final Method method) {
        super(method);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFormat() {
        return format == null ? Configuration.getFormat().name() : format.name();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFormat(final T format) {
        this.format = format;
        setAccept(format.toString());
        setContentType(format.toString());
    }

    @Override
    public final Future<V> asyncExecute() {
        return pool.submit(new Callable<V>() {

            @Override
            public V call() throws Exception {
                return execute();
            }
        });
    }

    /**
     * Gets payload as a byte array.
     *
     * @return byte array representation of the entire payload.
     */
    protected abstract InputStream getPayload();

    /**
     * Serializes the full request into the given batch request.
     *
     * @param req destination batch request.
     */
    public void batch(final ODataBatchRequest req) {
        batch(req, null);
    }

    public void batch(final ODataBatchRequest req, final String contentId) {
        try {
            req.rawAppend(getFullHeaders());
            if (StringUtils.isNotBlank(contentId)) {
                req.rawAppend((ODataBatchConstants.CHANGESET_CONTENT_ID_NAME + ": " + contentId).getBytes());
                req.rawAppend(ODataStreamer.CRLF);
            }
            req.rawAppend(ODataStreamer.CRLF);

            final InputStream payload = getPayload();
            if (payload != null) {
                req.rawAppend(IOUtils.toByteArray(getPayload()));
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
