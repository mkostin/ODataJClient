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
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData link query request.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getLinkRequest(java.net.URI, java.lang.String)
 */
public class ODataLinkRequest extends ODataRetrieveRequest<List<URI>, ODataFormat> {

    /**
     * Private constructor.
     *
     * @param targetURI target URI.
     * @param linkName link name.
     */
    ODataLinkRequest(final URI targetURI, final String linkName) {
        super(new ODataURIBuilder(targetURI.toASCIIString()).appendLinksSegment(linkName).build());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataRetrieveResponse<List<URI>> execute() {
        final HttpResponse res = doExecute();
        return new ODataEntitySetResponseImpl(client, res);
    }

    protected class ODataEntitySetResponseImpl extends ODataRetrieveResponseImpl {

        private List<URI> links = null;

        /**
         * Constructor.
         * <p>
         * Just to create response templates to be initialized from batch.
         */
        private ODataEntitySetResponseImpl() {
        }

        /**
         * Constructor.
         *
         * @param client HTTP client.
         * @param res HTTP response.
         */
        private ODataEntitySetResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public List<URI> getBody() {
            if (links == null) {
                try {
                    links = ODataReader.readLinks(
                            res.getEntity().getContent(), ODataFormat.valueOf(getFormat()));
                } catch (IOException e) {
                    throw new HttpClientException(e);
                } finally {
                    this.close();
                }
            }
            return links;
        }
    }
}
