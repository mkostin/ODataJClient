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
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import java.io.InputStream;
import java.net.URI;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import org.apache.cxf.jaxrs.client.WebClient;

/**
 * This class implements a metadata query request.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getMetadataRequest(java.lang.String)
 */
public class ODataMetadataRequest extends ODataQueryRequest<EdmMetadata> {

    /**
     * Constructor.
     *
     * @param serviceRoot query URI.
     */
    ODataMetadataRequest(final URI uri) {
        super(uri);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataQueryResponse<EdmMetadata> execute() {
        final WebClient client = WebClient.create(uri.toASCIIString());
        return new ODataMetadataResponsImpl(client.get());

    }

    protected class ODataMetadataResponsImpl extends ODataQueryResponseImpl {

        private EdmMetadata metadata = null;

        private ODataMetadataResponsImpl(final Response res) {
            super(res);
            res.close();
        }

        @Override
        public EdmMetadata getBody() {
            try {
                if (metadata == null) {
                    metadata = new EdmMetadata(res.readEntity(InputStream.class));
                }
                return metadata;
            } catch (JAXBException e) {
                LOG.error("Error unmarshalling metadata info", e);
                throw new IllegalStateException(e);
            } finally {
                res.close();
            }
        }
    }
}
