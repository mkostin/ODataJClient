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
import org.slf4j.LoggerFactory;

public abstract class ODataStreamingManagement<T extends ODataResponse> extends ODataStreamer {

    /**
     * Logger.
     */
    protected static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ODataStreamingManagement.class);

    /**
     * Body input stream.
     */
    private final PipedInputStream body;

    /**
     * Default body input stream.
     */
    private final InputStream def;

    public ODataStreamingManagement() {
        super(new PipedOutputStream());

        try {
            this.body = new PipedInputStream(bodyStreamWriter);
            this.def = this.body;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public ODataStreamingManagement(final PipedOutputStream os) {
        super(os);

        try {
            this.body = new PipedInputStream(bodyStreamWriter);
            this.def = this.body;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public ODataStreamingManagement(final InputStream is) {
        super(null);
        this.body = null;
        this.def = is;
    }

    public InputStream getBody() {
        return this.body == null ? this.def : this.body;
    }

    public void finalizeBody() throws IOException {
        if (bodyStreamWriter != null) {
            bodyStreamWriter.flush();
            bodyStreamWriter.close();
        }
    }

    public abstract T getResponse();

    public abstract Future<T> asyncResponse();
}
