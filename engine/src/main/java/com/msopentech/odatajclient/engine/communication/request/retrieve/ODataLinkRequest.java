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
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.types.ODataPropertyFormat;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData link query request.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getLinkRequest(java.net.URI)
 */
public class ODataLinkRequest extends ODataQueryRequest<List<URI>, ODataPropertyFormat> {

    /**
     * Private constructor.
     *
     * @param query query to be executed.
     */
    ODataLinkRequest(final URI targetURI, final String linkName) {
        super(new ODataURIBuilder(targetURI.toASCIIString()).
                appendStructuralSegment("$links").
                appendStructuralSegment(linkName).build());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataQueryResponse<List<URI>> execute() {
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

        private List<URI> links = null;

        private ODataEntitySetResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        @Override
        public List<URI> getBody() {
            if (links == null) {
                try {
                    links = ODataReader.readLinks(
                            res.getEntity().getContent(), ODataPropertyFormat.valueOf(getFormat()));
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
