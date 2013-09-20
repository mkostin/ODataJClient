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
import com.msopentech.odatajclient.engine.format.ODataMediaFormat;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData media query request.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getMediaRequest(java.net.URI)
 */
public class ODataMediaRequest extends ODataRetrieveRequest<InputStream, ODataMediaFormat> {

    /**
     * Private constructor.
     *
     * @param query query to be executed.
     */
    ODataMediaRequest(final URI query) {
        super(ODataMediaFormat.class, query);
        setAccept(ODataMediaFormat.APPLICATION_OCTET_STREAM.toString());
        setContentType(ODataMediaFormat.APPLICATION_OCTET_STREAM.toString());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataRetrieveResponse<InputStream> execute() {
        final HttpResponse res = doExecute();
        return new ODataMediaResponseImpl(client, res);
    }

    /**
     * Response class about an ODataMediaRequest.
     */
    protected class ODataMediaResponseImpl extends ODataRetrieveResponseImpl {

        /**
         * Constructor.
         * <p>
         * Just to create response templates to be initialized from batch.
         */
        private ODataMediaResponseImpl() {
        }

        /**
         * Constructor.
         *
         * @param client HTTP client.
         * @param res HTTP response.
         */
        private ODataMediaResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public InputStream getBody() {
            try {
                return res.getEntity().getContent();
            } catch (IOException e) {
                throw new HttpClientException(e);
            } finally {
                this.close();
            }
        }
    }
}
