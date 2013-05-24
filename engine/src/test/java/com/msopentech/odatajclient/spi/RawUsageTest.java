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

import com.msopentech.odatajclient.engine.client.ODataClient;
import com.msopentech.odatajclient.engine.client.ODataRestClient;
import com.msopentech.odatajclient.engine.communication.request.ODataQueryRequest;
import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataURI;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import com.msopentech.odatajclient.engine.utils.NoSuchEntityFound;
import com.msopentech.odatajclient.engine.utils.ODataReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class RawUsageTest {

    private final ODataClient client = new ODataRestClient();

    public void usageFullDeSerialization() {
        // prepare URI
        final ODataURI uri = new ODataURI("http://services.odata.org/OData/Odata.svc");
        uri.appendEntitySetSegment("Products").expand("Supplier").select("Rating,Supplier/Name");

        // create new request
        final ODataQueryRequest request = ODataRequestFactory.getQueryRequest(uri);

        // execute request
        final InputStream is = client.rawExecute(request);

        try {
            // let's suppose to want to deserialize, one shot, an entire atom feed
            ODataReader reader = new ODataReader(ODataFormat.ATOM);

            Collection<ODataEntity> entityes = reader.deserialize(is);

            // retrieve and process the query result
            for (ODataEntity entity : entityes) {
                // .................
            }

        } catch (NoSuchEntityFound ex) {
            // ....
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    // ignore
                }
            }
        }
    }

    public void usageStepByStepDeSerialization() {
        // prepare URI
        final ODataURI uri = new ODataURI("http://services.odata.org/OData/Odata.svc");
        uri.appendEntitySetSegment("Products").expand("Supplier").select("Rating,Supplier/Name");

        // create new request
        final ODataQueryRequest request = ODataRequestFactory.getQueryRequest(uri);

        // execute request
        final InputStream is = client.rawExecute(request);

        try {
            // let's suppose to want to deserialize, step-by-step, an entire atom feed
            ODataReader reader = new ODataReader(ODataFormat.ATOM);

            while (is.available() > 0) {
                // retrieve and process the new result item
                ODataEntity entity = reader.deserializeEntity(is);
                // .................
            }
        } catch (IOException ex) {
            // ....
        } catch (NoSuchEntityFound ex) {
            // ....
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    // ignore
                }
            }
        }
    }
}
