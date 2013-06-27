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
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.ODataServiceDocument;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.types.ODataServiceDocumentFormat;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData service document request.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getServiceDocumentRequest(java.lang.String)
 */
public class ODataServiceDocumentRequest extends ODataQueryRequest<ODataServiceDocument, ODataServiceDocumentFormat> {

    /**
     * Constructor.
     *
     * @param serviceRoot query URI.
     */
    ODataServiceDocumentRequest(final URI uri) {
        super(uri);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataQueryResponse<ODataServiceDocument> execute() {
        final HttpResponse res = doExecute();
        return new ODataServiceResponseImpl(client, res);
    }

    protected class ODataServiceResponseImpl extends ODataQueryResponseImpl {

        private ODataServiceDocument serviceDocument = null;

        private ODataServiceResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        @Override
        public ODataServiceDocument getBody() {
            if (serviceDocument == null) {
                try {
                    serviceDocument = ODataReader.readServiceDocument(
                            res.getEntity().getContent(), ODataServiceDocumentFormat.valueOf(getFormat()));
                } catch (IOException e) {
                    throw new HttpClientException(e);
                } finally {
                    this.close();
                }
            }
            return serviceDocument;
        }
    }
}
