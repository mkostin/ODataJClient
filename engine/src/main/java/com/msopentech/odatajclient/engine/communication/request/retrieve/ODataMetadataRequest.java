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
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements a metadata query request.
 * Get instance by using ODataRetrieveRequestFactory.
 *
 * @see ODataRetrieveRequestFactory#getMetadataRequest(java.lang.String)
 */
public class ODataMetadataRequest extends ODataRetrieveRequest<EdmMetadata, ODataFormat> {

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
    public ODataRetrieveResponse<EdmMetadata> execute() {
        final HttpResponse res = doExecute();
        return new ODataMetadataResponsImpl(client, res);
    }

    protected class ODataMetadataResponsImpl extends ODataRetrieveResponseImpl {

        private EdmMetadata metadata = null;

        private ODataMetadataResponsImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
        }

        @Override
        public EdmMetadata getBody() {
            if (metadata == null) {
                try {
                    metadata = ODataReader.readMetadata(res.getEntity().getContent());
                } catch (IOException e) {
                    throw new HttpClientException(e);
                } finally {
                    this.close();
                }
            }
            return metadata;
        }
    }
}
