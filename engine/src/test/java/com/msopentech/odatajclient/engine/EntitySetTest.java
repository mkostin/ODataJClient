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
package com.msopentech.odatajclient.engine;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import com.msopentech.odatajclient.engine.data.Deserializer;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ResourceFactory;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class EntitySetTest extends AbstractTest {

    private void read(final ODataPubFormat format) throws IOException {
        final InputStream input = getClass().getResourceAsStream("Customer." + getSuffix(format));
        final ODataEntitySet entitySet = client.getODataBinder().getODataEntitySet(
                Deserializer.toFeed(input, ResourceFactory.feedClassForFormat(format)));
        assertNotNull(entitySet);

        assertEquals(2, entitySet.getEntities().size());
        assertNotNull(entitySet.getNext());

        final ODataEntitySet written = client.getODataBinder().getODataEntitySet(
                client.getODataBinder().getFeed(entitySet, ResourceFactory.feedClassForFormat(format)));
        assertEquals(entitySet, written);
    }

    @Test
    public void fromAtom() throws IOException {
        read(ODataPubFormat.ATOM);
    }

    @Test
    public void fromJSON() throws IOException {
        read(ODataPubFormat.JSON);
    }
}
