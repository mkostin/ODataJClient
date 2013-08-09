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
package com.msopentech.odatajclient.engine.communication.request.retrieve;

import com.msopentech.odatajclient.engine.communication.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataObjectWrapper;
import com.msopentech.odatajclient.engine.utils.Configuration;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements a generic OData retrieve query request.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getGenericRetrieveRequest(java.net.URI) 
 */
public class ODataGenericRetrieveRequest extends ODataRawRequest {

    /**
     * Accepted format.
     */
    private String format;

    /**
     * Constructor.
     * @param uri query request.
     */
    public ODataGenericRetrieveRequest(final URI uri) {
        super(uri);
    }

    /**
     * Sets accepted format.
     * @param format format.
     */
    public void setFormat(final String format) {
        this.format = format;
        setAccept(format);
        setContentType(format);
    }

    /**
     * Gets accepted format.
     * @return format.
     */
    public String getFormat() {
        return format == null ? Configuration.getDefaultPubFormat().toString() : format;
    }

    /**
     * Executes the query.
     * @return query response.
     */
    public ODataRetrieveResponse<ODataObjectWrapper> execute() {
        return new ODataGenericResponseImpl(client, doExecute());
    }

    /**
     * Query response implementation about a generic query request.
     */
    protected class ODataGenericResponseImpl extends ODataResponseImpl
            implements ODataRetrieveResponse<ODataObjectWrapper> {

        /**
         * Retrieved object wrapper.
         */
        private ODataObjectWrapper obj = null;

        /**
         * Constructor.
         * <p>
         * Just to create response templates to be initialized from batch.
         */
        private ODataGenericResponseImpl() {
        }

        /**
         * Constructor.
         *
         * @param client HTTP client.
         * @param res HTTP response.
         */
        private ODataGenericResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public ODataObjectWrapper getBody() {
            if (obj == null) {
                try {
                    obj = new ODataObjectWrapper(getRawResponse(), getFormat());
                } finally {
                    this.close();
                }
            }
            return obj;
        }
    }
}
