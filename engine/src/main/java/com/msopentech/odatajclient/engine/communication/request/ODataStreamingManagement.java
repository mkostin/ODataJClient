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

import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.Future;
import org.apache.commons.io.IOUtils;

/**
 * OData request payload management abstract class.
 *
 * @param <T> OData response type corresponding to the request implementation.
 */
public abstract class ODataStreamingManagement<T extends ODataResponse> extends ODataStreamer {

    /**
     * Body input stream.
     */
    private final PipedInputStream body;

    /**
     * Default body input stream.
     */
    private final InputStream def;

    /**
     * Constructor.
     */
    public ODataStreamingManagement() {
        this(new PipedOutputStream());
    }

    /**
     * Constructor.
     *
     * @param os piped output stream.
     */
    public ODataStreamingManagement(final PipedOutputStream os) {
        super(os);

        try {
            this.body = new PipedInputStream(getBodyStreamWriter());
            this.def = this.body;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Constructor.
     *
     * @param is payload input stream.
     */
    public ODataStreamingManagement(final InputStream is) {
        super(null);
        this.body = null;
        this.def = is;
    }

    /**
     * Gets payload stream.
     *
     * @return payload stream.
     */
    public InputStream getBody() {
        return this.body == null ? this.def : this.body;
    }

    /**
     * Closes piped output stream.
     */
    public void finalizeBody() {
        IOUtils.closeQuietly(getBodyStreamWriter());
    }

    /**
     * Closes the payload input stream and gets back the OData response.
     *
     * @return OData response.
     */
    public abstract T getResponse();

    /**
     * Closes the payload input stream and ask for an asynchronous response.
     *
     * @return <code>Future&lt;ODataResponse&gt;</code> about the executed request.
     */
    public abstract Future<T> asyncResponse();
}
