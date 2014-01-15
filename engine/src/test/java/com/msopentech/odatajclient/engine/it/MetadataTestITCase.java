/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.engine.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import org.junit.Test;

public class MetadataTestITCase extends AbstractTest {

    @Test
    public void large() {
        final EdmMetadata metadata = client.getRetrieveRequestFactory().
                getMetadataRequest(testLargeModelServiceRootURL).execute().getBody();
        assertNotNull(metadata);

        assertEquals(400, metadata.getSchemas().get(0).getEntityContainers().get(0).getEntitySets().size());
    }
}
