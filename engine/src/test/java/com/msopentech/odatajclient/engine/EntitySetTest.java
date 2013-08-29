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
package com.msopentech.odatajclient.engine;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import com.msopentech.odatajclient.engine.data.Deserializer;
import com.msopentech.odatajclient.engine.data.ODataBinder;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ResourceFactory;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class EntitySetTest extends AbstractTest {

    private void read(final ODataPubFormat format) throws IOException {
        final InputStream input = getClass().getResourceAsStream("Customer." + getSuffix(format));
        final ODataEntitySet entitySet = ODataBinder.getODataEntitySet(
                Deserializer.toFeed(input, ResourceFactory.feedClassForFormat(format)));
        assertNotNull(entitySet);

        assertEquals(2, entitySet.getEntities().size());
        assertNotNull(entitySet.getNext());

        final ODataEntitySet written = ODataBinder.getODataEntitySet(
                ODataBinder.getFeed(entitySet, ResourceFactory.feedClassForFormat(format)));
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
