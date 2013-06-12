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
package com.msopentech.odatajclient.engine.communication.request;

import com.msopentech.odatajclient.engine.communication.response.ODataMetadataResponse;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.edmx.TDataServices;
import com.msopentech.odatajclient.engine.data.metadata.edmx.TEdmx;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Future;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.apache.cxf.jaxrs.client.WebClient;

/**
 * This class implements an OData metadata request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getMetadataRequest(java.lang.String)
 */
public class ODataMetadataRequest extends ODataRequestImpl
        implements ODataBasicRequest<ODataMetadataResponse> {

    /**
     * Constructor.
     *
     * @param serviceRoot query URI.
     */
    ODataMetadataRequest(final URI uri) {
        // set method .... If cofigured X-HTTP-METHOD header will be used.
        super(Method.GET);
        // set uri ...
        this.uri = uri;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataMetadataResponse execute() {
        final WebClient client = WebClient.create(uri.toASCIIString());
        return new ODataMetadataResponsImpl(client.get());

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Future<ODataMetadataResponse> asyncExecute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private class ODataMetadataResponsImpl implements ODataMetadataResponse {

        private final Response res;

        public ODataMetadataResponsImpl(final Response res) {
            this.res = res;
        }

        @Override
        public EdmMetadata getBody() {
            try {
                JAXBContext context = JAXBContext.newInstance(TEdmx.class);
                @SuppressWarnings("unchecked")
                TEdmx edmx = ((JAXBElement<TEdmx>) context.createUnmarshaller().unmarshal(
                        res.readEntity(InputStream.class))).getValue();

                TDataServices dataservices = null;
                for (JAXBElement<?> edmxContent : edmx.getContent()) {
                    if (TDataServices.class.equals(edmxContent.getDeclaredType())) {
                        dataservices = (TDataServices) edmxContent.getValue();
                    }
                }
                if (dataservices == null) {
                    throw new IllegalArgumentException("No <DataServices/> element found");
                }

                return new EdmMetadata(dataservices);
            } catch (JAXBException e) {
                LOG.error("Error unmarshalling metadata info", e);
                throw new IllegalStateException(e);
            } finally {
                res.close();
            }
        }

        @Override
        public Collection<String> getHeaderNames() {
            return res.getHeaders() == null ? new HashSet<String>() : res.getHeaders().keySet();
        }

        @Override
        public String getHeader(final String name) {
            return res.getHeaderString(name);
        }

        @Override
        public int getStatusCode() {
            return res.getStatus();
        }

        @Override
        public String getStatusMessage() {
            return res.getStatusInfo().getReasonPhrase();
        }
    }
}
