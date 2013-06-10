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
import java.io.OutputStream;

public abstract class ODataBasicRequestImpl<V extends ODataResponse>
        extends ODataRequestImpl
        implements ODataBasicRequest<V> {

    public ODataBasicRequestImpl(Method method) {
        super(method);
    }

    protected abstract byte[] getPayload();

    public void batch(final ODataBatchRequest req) {

        try {
            final OutputStream os = req.getOutputStream();

            os.write(toByteArray());
            os.write(ODataStreamer.CRLF);
            os.write(getPayload());

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
