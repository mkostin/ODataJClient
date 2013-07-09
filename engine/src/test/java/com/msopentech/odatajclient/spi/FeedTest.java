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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntitySetRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.Deserializer;
import com.msopentech.odatajclient.engine.data.FeedResource;
import com.msopentech.odatajclient.engine.data.ODataBinder;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.ResourceFactory;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.utils.URIUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import org.junit.Test;

/**
 * This is the unit test class to check basic feed operations.
 */
public class FeedTest extends AbstractTest {

    @Test
    public void readAtomFeed() throws IOException {
        readFeed(ODataPubFormat.ATOM);
    }

    @Test
    public void readJSONFeed() throws IOException {
        readFeed(ODataPubFormat.JSON);
    }

    @Test
    public void readODataEntitySetFromAtom() {
        readODataEntitySet(ODataPubFormat.ATOM);
    }

    @Test
    public void readODataEntitySetFromJSON() {
        readODataEntitySet(ODataPubFormat.JSON);
    }

    @Test
    public void readODataEntitySetWithNextFromAtom() {
        readEntitySetWithNextLink(ODataPubFormat.ATOM);
    }

    @Test
    public void readODataEntitySetWithNextFromJSON() {
        readEntitySetWithNextLink(ODataPubFormat.JSON_FULL_METADATA);
    }

    private void readEntitySetWithNextLink(final ODataPubFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("Customer");

        final ODataEntitySetRequest req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataRetrieveResponse<ODataEntitySet> res = req.execute();
        final ODataEntitySet feed = res.getBody();

        assertNotNull(feed);

        debugFeed(ODataBinder.getFeed(feed, ResourceFactory.feedClassForFormat(format)), "Just retrieved feed");

        assertEquals(2, feed.getEntities().size());
        assertNotNull(feed.getNext());

        final URI expected = URI.create(testODataServiceRootURL + "/Customer?$skiptoken=-9");
        final URI found = URIUtils.getURI(testODataServiceRootURL, feed.getNext().toASCIIString());

        assertEquals(expected, found);
    }

    private void readFeed(final ODataPubFormat format) throws IOException {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("Car").top(2).skip(4);

        final ODataEntitySetRequest req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
        req.setFormat(format);

        final InputStream is = req.rawExecute();

        final FeedResource feed = Deserializer.toFeed(is, ResourceFactory.feedClassForFormat(format));
        assertNotNull(feed);

        debugFeed(feed, "Just (raw)retrieved feed");

        assertEquals(2, feed.getEntries().size());

        is.close();
    }

    private void readODataEntitySet(final ODataPubFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("Car").top(3);

        final ODataEntitySetRequest req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataRetrieveResponse<ODataEntitySet> res = req.execute();
        final ODataEntitySet feed = res.getBody();

        assertNotNull(feed);

        debugFeed(ODataBinder.getFeed(feed, ResourceFactory.feedClassForFormat(format)), "Just retrieved feed");

        assertEquals(3, feed.getEntities().size());
    }
}
