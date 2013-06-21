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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntitySetRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.FeedResource;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataLink;
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

    private void readFeedResource(final ODataFormat format) throws IOException {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("Car");

        final ODataEntitySetRequest req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
        req.setFormat(format);

        final InputStream is = req.rowExecute();

        final FeedResource feed =
                SerializationUtils.deserializeFeed(is, ResourceFactory.feedClassForEntry(format.getEntryClass()));
        assertNotNull(feed);

        debugFeed(feed, "Just read");

        is.close();
    }

    @Test
    public void readAtomFeed() throws Exception {
        readFeedResource(ODataFormat.ATOM);
    }

    @Test
    @Ignore
    public void readJSONFeed() throws Exception {
        readFeedResource(ODataFormat.JSON);
    }

    private void readODataFeed(final ODataFormat format) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(
                "http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc");
        uriBuilder.appendEntityTypeSegment("Products(1)");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setFormat(format);

        final ODataQueryResponse<ODataEntity> res = req.execute();
        final ODataEntity entity = res.getBody();

        assertNotNull(entity);

        if (ODataFormat.JSON_FULL_METADATA == format || ODataFormat.ATOM == format) {
            assertEquals("ODataDemo.Product", entity.getName());
            assertEquals("http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc/Products(1)",
                    entity.getEditLink().toASCIIString());
            assertEquals(2, entity.getNavigationLinks().size());
            assertEquals(2, entity.getAssociationLinks().size());

            boolean check = false;

            for (ODataLink link : entity.getNavigationLinks()) {
                if ("Category".equals(link.getName())
                        && "http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc/Products(1)/Category".
                        equals(link.getLink().toASCIIString())) {
                    check = true;
                }
            }

            assertTrue(check);
        }
    }

    @Test
    @Ignore
    public void readODataEntityFromAtom() {
        readODataFeed(ODataFormat.ATOM);
    }

    @Test
    @Ignore
    public void readODataEntityFromJSON() {
        readODataFeed(ODataFormat.JSON);
    }
}
