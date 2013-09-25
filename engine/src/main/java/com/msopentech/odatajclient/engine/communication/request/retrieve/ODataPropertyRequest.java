/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.engine.communication.request.retrieve;

import com.msopentech.odatajclient.engine.client.http.HttpClientException;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData entity property query request.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getPropertyRequest(java.net.URI)
 */
public class ODataPropertyRequest extends ODataRetrieveRequest<ODataProperty, ODataFormat> {

    /**
     * Private constructor.
     *
     * @param query query to be executed.
     */
    ODataPropertyRequest(final URI query) {
        super(ODataFormat.class, query);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataRetrieveResponse<ODataProperty> execute() {
        final HttpResponse res = doExecute();
        return new ODataPropertyResponseImpl(client, res);
    }

    protected class ODataPropertyResponseImpl extends ODataRetrieveResponseImpl {

        private ODataProperty property = null;

        /**
         * Constructor.
         * <p>
         * Just to create response templates to be initialized from batch.
         */
        private ODataPropertyResponseImpl() {
        }

        /**
         * Constructor.
         *
         * @param client HTTP client.
         * @param res HTTP response.
         */
        private ODataPropertyResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public ODataProperty getBody() {
            if (property == null) {
                try {
                    property = ODataReader.readProperty(
                            res.getEntity().getContent(), ODataFormat.fromString(getContentType()));
                } catch (IOException e) {
                    throw new HttpClientException(e);
                } finally {
                    this.close();
                }
            }
            return property;
        }
    }
}
