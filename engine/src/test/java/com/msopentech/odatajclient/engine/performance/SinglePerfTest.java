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
package com.msopentech.odatajclient.engine.performance;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msopentech.odatajclient.engine.AbstractTest;
import com.msopentech.odatajclient.engine.data.Deserializer;
import com.msopentech.odatajclient.engine.data.ODataBinder;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ResourceFactory;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

public class SinglePerfTest extends AbstractTest {

    /**
     * Factory for StAX de-serialization.
     */
    private static final XMLInputFactory XMLIF = XMLInputFactory.newInstance();

    private static final Map<ODataPubFormat, String> input = new HashMap<ODataPubFormat, String>(2);

    @BeforeClass
    public static void setInput() {
        try {
            input.put(ODataPubFormat.ATOM,
                    IOUtils.toString(SinglePerfTest.class.getResourceAsStream("../Customer_-10.xml")));
            input.put(ODataPubFormat.JSON,
                    IOUtils.toString(SinglePerfTest.class.getResourceAsStream("../Customer_-10.json")));
        } catch (Exception e) {
            fail("Could not load sample file");
        }
    }

    @Test
    public void readAtomViaLowerlevelLibs() throws Exception {
        final XMLStreamReader xmler = XMLIF.createXMLStreamReader(
                IOUtils.toInputStream(input.get(ODataPubFormat.ATOM)));
        final JAXBContext context = JAXBContext.newInstance(AtomEntry.class);

        final AtomEntry entry = context.createUnmarshaller().unmarshal(xmler, AtomEntry.class).getValue();
        assertNotNull(entry);
    }

    @Test
    public void readJSONViaLowerlevelLibs() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();

        final JsonNode entry = mapper.readTree(IOUtils.toInputStream(input.get(ODataPubFormat.JSON)));
        assertNotNull(entry);
    }

    private void readViaODataJClient(final ODataPubFormat format) {
        final ODataEntity entity = ODataBinder.getODataEntity(
                Deserializer.toEntry(IOUtils.toInputStream(input.get(format)),
                ResourceFactory.entryClassForFormat(format)));
        assertNotNull(entity);
    }

    @Test
    public void readAtomViaOdataJClient() {
        readViaODataJClient(ODataPubFormat.ATOM);
    }

    @Test
    public void readJSONViaOdataJClient() {
        readViaODataJClient(ODataPubFormat.JSON);
    }

    @Test
    public void writeAtomViaLowerlevelLibs() {
        // TODO
    }

    @Test
    public void writeJSONViaLowerlevelLibs() {
        // TODO    
    }

    @Test
    public void writeAtomViaOdataJClient() {
        // TODO
    }

    @Test
    public void writeJSONViaOdataJClient() {
        // TODO
    }
}
