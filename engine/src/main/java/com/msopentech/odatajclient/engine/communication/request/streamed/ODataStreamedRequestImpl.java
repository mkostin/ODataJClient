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
package com.msopentech.odatajclient.engine.communication.request.streamed;

import com.msopentech.odatajclient.engine.communication.request.ODataRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataStreamer;
import com.msopentech.odatajclient.engine.communication.request.ODataStreamManager;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import com.msopentech.odatajclient.engine.utils.Configuration;
import com.msopentech.odatajclient.engine.utils.ODataBatchConstants;
import com.msopentech.odatajclient.engine.utils.Wrapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;

/**
 * Streamed OData request abstract class.
 *
 * @param <V> OData response type corresponding to the request implementation.
 * @param <T> OData request payload type corresponding to the request implementation.
 */
public abstract class ODataStreamedRequestImpl<V extends ODataResponse, T extends ODataStreamManager<V>>
        extends ODataRequestImpl implements ODataStreamedRequest<V, T> {

    /**
     * OData payload stream manager.
     */
    protected ODataStreamManager<V> streamManager;

    /**
     * Wrapper for actual streamed request's future.
     * This holds information about the HTTP request / response currently open.
     */
    protected final Wrapper<Future<HttpResponse>> futureWrapper = new Wrapper<Future<HttpResponse>>();

    /**
     * Constructor.
     *
     * @param method OData request HTTP method.
     * @param uri OData request URI.
     */
    public ODataStreamedRequestImpl(final Method method, final URI uri) {
        super(method, uri);
        setAccept(ContentType.APPLICATION_OCTET_STREAM.getMimeType());
        setContentType(ContentType.APPLICATION_OCTET_STREAM.getMimeType());
    }

    /**
     * Gets OData request payload management object.
     *
     * @return OData request payload management object.
     */
    protected abstract T getStreamManager();

    /**
     * {@inheritDoc }
     */
    @Override
    @SuppressWarnings("unchecked")
    public T execute() {
        streamManager = getStreamManager();

        ((HttpEntityEnclosingRequestBase) request).setEntity(new InputStreamEntity(streamManager.getBody(), -1));

        futureWrapper.setWrapped(Configuration.getExecutor().submit(new Callable<HttpResponse>() {

            @Override
            public HttpResponse call() throws Exception {
                return doExecute();
            }
        }));

        // returns the stream manager object
        return (T) streamManager;
    }

    /**
     * Writes (and consume) the request onto the given batch stream.
     * <p>
     * Please note that this method will consume the request (execution won't be possible anymore).
     *
     * @param req destination batch request.
     */
    public void batch(final ODataBatchRequest req) {
        batch(req, null);
    }

    public void batch(final ODataBatchRequest req, final String contentId) {
        final InputStream input = getStreamManager().getBody();

        try {
            // finalize the body
            getStreamManager().finalizeBody();

            req.rawAppend(toByteArray());
            if (StringUtils.isNotBlank(contentId)) {
                req.rawAppend((ODataBatchConstants.CHANGESET_CONTENT_ID_NAME + ": " + contentId).getBytes());
                req.rawAppend(ODataStreamer.CRLF);
            }
            req.rawAppend(ODataStreamer.CRLF);

            req.rawAppend(IOUtils.toByteArray(input));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }
}
