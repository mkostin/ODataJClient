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
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    private final ExecutorService pool = Executors.newFixedThreadPool(10);

    /**
     * Constructor.
     *
     * @param method request method.
     * @param uri OData request URI.
     */
    public ODataBasicRequestImpl(final Method method, final URI uri) {
        super(method, uri);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFormat() {
        return format == null ? new Configuration().getProperty("format", "ATOM") : format.name();
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
    protected abstract byte[] getPayload();

    /**
     * Serializes the full request into the given batch request.
     *
     * @param req destination batch request.
     */
    public void batch(final ODataBatchRequest req) {

        try {
            req.rowAppend(toByteArray());
            req.rowAppend(ODataStreamer.CRLF);
            req.rowAppend(getPayload());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
