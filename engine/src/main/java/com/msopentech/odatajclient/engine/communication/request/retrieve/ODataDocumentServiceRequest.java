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

import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.ODataDocumentService;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.types.ODataDocumentServiceFormat;
import java.io.InputStream;
import java.net.URI;
import javax.ws.rs.core.Response;

/**
 * This class implements an OData document service request.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getDocumentServiceRequest(java.lang.String)
 */
public class ODataDocumentServiceRequest extends ODataQueryRequest<ODataDocumentService, ODataDocumentServiceFormat> {

    /**
     * Constructor.
     *
     * @param serviceRoot query URI.
     */
    ODataDocumentServiceRequest(final URI uri) {
        super(uri);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataQueryResponse<ODataDocumentService> execute() {
        return new ODataServcieResponsImpl(client.get());
    }

    /**
     * {@inheritDoc }
     * <p>
     * This kind of request doesn't have any kind of payload: an empty byte array will be returned.
     */
    @Override
    protected byte[] getPayload() {
        return new byte[0];
    }

    protected class ODataServcieResponsImpl extends ODataQueryResponseImpl {

        private ODataDocumentService documentService = null;

        private ODataServcieResponsImpl(final Response res) {
            super(res);
        }

        @Override
        public ODataDocumentService getBody() {
            try {
                if (documentService == null) {
                    documentService = ODataReader.deserializeDocumentService(
                            res.readEntity(InputStream.class), ODataDocumentServiceFormat.valueOf(getFormat()));
                }
                return documentService;
            } finally {
                res.close();
            }
        }
    }
}
