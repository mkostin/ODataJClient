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
package com.msopentech.odatajclient.proxy;

import static org.junit.Assert.assertNotNull;

import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.DefaultContainer;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTest {

    /**
     * Logger.
     */
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractTest.class);

    protected static final String TEST_PRODUCT_TYPE = "Microsoft.Test.OData.Services.AstoriaDefaultService.Product";

    protected static final String servicesODataServiceRootURL =
            "http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc/";

    protected static String testDefaultServiceRootURL;

    protected static String testActionOverloadingServiceRootURL;

    protected static String testKeyAsSegmentServiceRootURL;

    protected static String testODataWriterDefaultServiceRootURL;

    protected static String testOpenTypesServiceRootURL;

    protected static String testPrimitiveKeysServiceRootURL;

    protected static String testLargeModelServiceRootURL;

    @BeforeClass
    public static void setUpODataServiceRoot() throws IOException {
        String testBaseURL = null;

        InputStream propStream = null;
        try {
            propStream = AbstractTest.class.getResourceAsStream("/test.properties");
            final Properties props = new Properties();
            props.load(propStream);

            testBaseURL = props.getProperty("test.base.url");
        } catch (Exception e) {
            LOG.error("Could not load test.properties", e);
        } finally {
            if (propStream != null) {
                propStream.close();
            }
        }
        assertNotNull("Check value for the 'test.base.url' property", testBaseURL);

        testDefaultServiceRootURL = testBaseURL + "/DefaultService.svc";
        testActionOverloadingServiceRootURL = testBaseURL + "/ActionOverloadingService.svc";
        testKeyAsSegmentServiceRootURL = testBaseURL + "/KeyAsSegmentService.svc";
        testODataWriterDefaultServiceRootURL = testBaseURL + "/ODataWriterDefaultService.svc";
        testOpenTypesServiceRootURL = testBaseURL + "/OpenTypesService.svc";
        testPrimitiveKeysServiceRootURL = testBaseURL + "/PrimitiveKeysService.svc";
        testLargeModelServiceRootURL = testBaseURL + "/LargeModelService.svc";
    }

    protected static DefaultContainer getDefaultContainer(final String serviceRootURL) {
        final DefaultContainer container = EntityContainerFactory.newInstance(serviceRootURL)
                .getEntityContainer(DefaultContainer.class);
        assertNotNull(container);

        return container;
    }
}
