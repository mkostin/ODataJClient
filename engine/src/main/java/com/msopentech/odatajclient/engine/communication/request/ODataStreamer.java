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

import static com.msopentech.odatajclient.engine.communication.request.ODataStreamingManagement.LOG;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedOutputStream;

/**
 * Streamer utility object.
 */
public abstract class ODataStreamer {

    /**
     * CR/LF.
     */
    public static final byte[] CRLF = {13, 10};

    /**
     * OutputStream to be used to write objects to the stream.
     */
    protected final PipedOutputStream bodyStreamWriter;

    public ODataStreamer(final PipedOutputStream bodyStreamWriter) {
        this.bodyStreamWriter = bodyStreamWriter;
    }

    /**
     * Writes the gibe byte array onto the output stream provided at instantiation time.
     *
     * @param src byte array to be written.
     */
    protected void stream(final byte[] src) {
        new Writer(src, bodyStreamWriter).run();
    }

    /**
     * Stream CR/LF.
     */
    protected void newLine() {
        stream(CRLF);
    }

    /**
     * Writer thread.
     */
    private class Writer implements Runnable {

        final OutputStream os;

        final byte[] src;

        public Writer(final byte[] src, final OutputStream os) {
            this.os = os;
            this.src = src;
        }

        @Override
        public void run() {
            try {
                os.write(src);
            } catch (IOException e) {
                LOG.error("Error streaming object", e);
            }
        }
    }
}
