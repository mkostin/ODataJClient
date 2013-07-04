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
package com.msopentech.odatajclient.engine.communication.request.cud;

import com.msopentech.odatajclient.engine.communication.request.ODataStreamingManagement;
import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.net.URI;

public abstract class ODataStreamedEntityRequestImpl<V extends ODataResponse, T extends ODataStreamingManagement<V>>
        extends ODataStreamedRequestImpl<V, T> {

    private ODataPubFormat format;

    public ODataStreamedEntityRequestImpl(final Method method, URI uri) {
        super(method, uri);
        setAccept(getFormat().toString());
    }

    /**
     * Returns resource representation format.
     *
     * @return the configured format (<code>ODataFormat.JSON_FULL_METADATA</code> will be the default value unless
     * differently specified).
     */
    public final ODataPubFormat getFormat() {
        return format == null ? ODataPubFormat.JSON_FULL_METADATA : format;
    }

    /**
     * Override configured request format.
     *
     * @param format request format.
     * @see ODataFormat
     */
    public final void setFormat(final ODataPubFormat format) {
        this.format = format;
        setAccept(format.toString());
    }
}
