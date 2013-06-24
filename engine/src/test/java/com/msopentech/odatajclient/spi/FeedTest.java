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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntitySetRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.FeedResource;
import com.msopentech.odatajclient.engine.data.ODataBinder;
import com.msopentech.odatajclient.engine.data.ODataFeed;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.ResourceFactory;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import com.msopentech.odatajclient.engine.utils.SerializationUtils;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is the unit test class to check basic entity operations.
 */
public class FeedTest extends AbstractTest {

    private void readFeed(final ODataFormat format) throws IOException {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("Car").top(2).skip(4);

        final ODataEntitySetRequest req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
        req.setFormat(format);

        final InputStream is = req.rowExecute();

        final FeedResource feed =
                SerializationUtils.deserializeFeed(is, ResourceFactory.feedClassForFormat(format));
        assertNotNull(feed);

        debugFeed(feed, "Just (raw)retrieved feed");

        assertEquals(2, feed.getEntries().size());

        is.close();
    }

    @Test
    public void readAtomFeed() throws Exception {
        readFeed(ODataFormat.ATOM);
    }

    @Test
    public void readJSONFeed() throws Exception {
        readFeed(ODataFormat.JSON);
    }

    private void readODataFeed(final ODataFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("Car").top(3);

        final ODataEntitySetRequest req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataQueryResponse<ODataFeed> res = req.execute();
        final ODataFeed feed = res.getBody();

        assertNotNull(feed);

        debugFeed(ODataBinder.getFeed(feed, ResourceFactory.feedClassForFormat(format)), "Just retrieved feed");

        assertEquals(3, feed.getEntities().size());
    }

    @Test
    public void readODataFeedFromAtom() {
        readODataFeed(ODataFormat.ATOM);
    }

    @Test
    @Ignore
    public void readODataFeedFromJSON() {
        readODataFeed(ODataFormat.JSON);
    }
}
