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
import com.msopentech.odatajclient.engine.communication.header.ODataHeader;
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.ODataFeed;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData EntitySet query request.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getEntitySetRequest(java.net.URI)
 */
public class ODataEntitySetRequest extends ODataQueryRequest<ODataFeed, ODataFormat> {

    private ODataFeed feed = null;

    /**
     * Private constructor.
     *
     * @param query query to be executed.
     */
    ODataEntitySetRequest(final URI query) {
        super(query);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataQueryResponse<ODataFeed> execute() {
        request.setHeader(ODataHeader.HeaderName.accept.toString(), getAccept());
        try {
            final HttpResponse res = client.execute(request);
            return new ODataEntitySetResponseImpl(client, res);
        } catch (IOException e) {
            throw new HttpClientException(e);
        } catch (RuntimeException e) {
            this.request.abort();
            throw new HttpClientException(e);
        }
    }

    protected class ODataEntitySetResponseImpl extends ODataQueryResponseImpl {

        private ODataEntitySetResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        @Override
        @SuppressWarnings("unchecked")
        public ODataFeed getBody() {
            if (feed == null) {
                try {
                    feed = ODataReader.readFeed(res.getEntity().getContent(), ODataFormat.valueOf(getFormat()));
                } catch (IOException e) {
                    throw new HttpClientException(e);
                } finally {
                    this.close();
                }
            }
            return feed;
        }
    }
}
