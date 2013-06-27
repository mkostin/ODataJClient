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

import com.msopentech.odatajclient.engine.client.http.HttpClientException;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.types.ODataMediaFormat;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;

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
        super(query);
        setAccept(ContentType.APPLICATION_OCTET_STREAM.getMimeType());
        setContentType(ContentType.APPLICATION_OCTET_STREAM.getMimeType());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataRetrieveResponse<InputStream> execute() {
        final HttpResponse res = doExecute();
        return new ODataMediaResponseImpl(client, res);
    }

    protected class ODataMediaResponseImpl extends ODataRetrieveResponseImpl {

        private ODataMediaResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

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
