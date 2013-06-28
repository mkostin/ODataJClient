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

import static com.msopentech.odatajclient.spi.AbstractTest.TEST_CUSTOMER;
import static com.msopentech.odatajclient.spi.AbstractTest.testODataServiceRootURL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataLinkCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataLinkRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataLinkOperationResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataLinkType;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.types.ODataPropertyFormat;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

/**
 * This is the unit test class to check basic link operations.
 */
public class LinkTest extends AbstractTest {

    private List<URI> doRetrieveLinkURIs(final ODataPropertyFormat format) throws IOException {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment(TEST_CUSTOMER);

        final ODataLinkRequest req = ODataRetrieveRequestFactory.getLinkRequest(uriBuilder.build(), "Logins");
        req.setFormat(format);

        final ODataRetrieveResponse<List<URI>> res = req.execute();
        assertEquals(200, res.getStatusCode());

        return res.getBody();
    }

    private void retrieveLinkURIs(final ODataPropertyFormat format) throws IOException {
        final List<URI> links = doRetrieveLinkURIs(format);
        assertEquals(2, links.size());
        assertTrue(links.contains(URI.create(testODataServiceRootURL + "/Login('1')")));
        assertTrue(links.contains(URI.create(testODataServiceRootURL + "/Login('4')")));
    }

    @Test
    public void retrieveXMLLinkURIs() throws IOException {
        retrieveLinkURIs(ODataPropertyFormat.XML);
    }

    @Test
    public void retrieveJSONLinkURIs() throws IOException {
        retrieveLinkURIs(ODataPropertyFormat.JSON);
    }

    private void createLink(final ODataPropertyFormat format) throws IOException {
        final List<URI> before = doRetrieveLinkURIs(format);

        final ODataLink newLink = ODataFactory.newLink(null, URI.create(testODataServiceRootURL + "/Login('3')"),
                ODataLinkType.ASSOCIATION);

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntityTypeSegment(TEST_CUSTOMER).appendLinksSegment("Logins");

        final ODataLinkCreateRequest req = ODataCUDRequestFactory.getLinkCreateRequest(uriBuilder.build(), newLink);
        req.setFormat(format);

        final ODataLinkOperationResponse res = req.execute();
        assertEquals(204, res.getStatusCode());

        final List<URI> after = doRetrieveLinkURIs(format);
        assertEquals(before.size() + 1, after.size());

        after.removeAll(before);
        assertEquals(Collections.singletonList(newLink.getLink()), after);

        assertEquals(204, ODataCUDRequestFactory.getDeleteRequest(
                new ODataURIBuilder(testODataServiceRootURL).appendEntityTypeSegment(TEST_CUSTOMER).
                appendLinksSegment("Logins('3')").build()).execute().getStatusCode());
    }

    @Test
    public void createXMLLink() throws IOException {
        createLink(ODataPropertyFormat.XML);
    }

    @Test
    public void createJSONLink() throws IOException {
        createLink(ODataPropertyFormat.JSON);
    }
}
