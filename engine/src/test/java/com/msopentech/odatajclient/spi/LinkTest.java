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

import static com.msopentech.odatajclient.spi.AbstractTest.testODataServiceRootURL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataLinkRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.types.ODataPropertyFormat;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.junit.Test;

/**
 * This is the unit test class to check basic entity operations.
 */
public class LinkTest extends AbstractTest {

    private void retrieveLinkURIs(final ODataPropertyFormat format) throws IOException {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment(TEST_CUSTOMER);

        final ODataLinkRequest req = ODataRetrieveRequestFactory.getLinkRequest(uriBuilder.build(), "Logins");
        req.setFormat(format);

        final ODataQueryResponse<List<URI>> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final List<URI> links = res.getBody();
        assertEquals(2, links.size());
        assertTrue(links.contains(URI.create(testODataServiceRootURL + "/Login('1')")));
        assertTrue(links.contains(URI.create(testODataServiceRootURL + "/Login('4')")));
    }

    @Test
    public void retrieveXmlLinkURIs() throws Exception {
        retrieveLinkURIs(ODataPropertyFormat.XML);
    }

    @Test
    public void retrieveJSONLinkURIs() throws Exception {
        retrieveLinkURIs(ODataPropertyFormat.JSON);
    }
}
