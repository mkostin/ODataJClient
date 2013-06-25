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
package com.msopentech.odatajclient.spi;

import static org.junit.Assert.assertEquals;

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataDocumentServiceRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.ODataDocumentService;
import com.msopentech.odatajclient.engine.types.ODataDocumentServiceFormat;
import java.net.URI;
import org.junit.Ignore;
import org.junit.Test;

public class DocumentServiceTest extends AbstractTest {

    private void retrieveDocumentService(final ODataDocumentServiceFormat format) {
        final ODataDocumentServiceRequest req =
                ODataRetrieveRequestFactory.getDocumentServiceRequest(testODataServiceRootURL);
        req.setFormat(format);

        final ODataQueryResponse<ODataDocumentService> res = req.execute();
        assertEquals(200, res.getStatusCode());

        ODataDocumentService documentService = res.getBody();
        assertEquals(22, documentService.size());

        assertEquals(URI.create(testODataServiceRootURL + "/ComputerDetail"),
                documentService.getEntitySetURI("ComputerDetail"));
    }

    @Test
    public void retrieveDocumentServiceAsXML() {
        retrieveDocumentService(ODataDocumentServiceFormat.XML);
    }

    @Test
    @Ignore
    public void retrieveDocumentServiceAsJSON() {
        retrieveDocumentService(ODataDocumentServiceFormat.JSON);
    }
}
