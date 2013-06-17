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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataQueryResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataItem;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This is the unit test class to check basic entity operations.
 */
public class EntityTest {

    @Test
    public void readAtomEntry() throws Exception {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(
                "http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc");
        uriBuilder.appendEntityTypeSegment("Categories(1)");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setContentType(ODataFormat.ATOM.toString());

        final InputStream is = req.rowExecute();

        JAXBContext context = JAXBContext.newInstance(AtomEntry.class);
        @SuppressWarnings("unchecked")
        AtomEntry entry = ((JAXBElement<AtomEntry>) context.createUnmarshaller().unmarshal(is)).getValue();
        assertNotNull(entry);
        assertEquals(uriBuilder.build().toURL().toExternalForm(), entry.getId().getContent());
        assertEquals("ODataDemo.Category", entry.getCategory().getTerm());

        Element xmlContent = entry.getAtomContent().getXMLContent();
        assertEquals("properties", xmlContent.getLocalName());

        boolean entered = false;
        boolean checkID = false;
        for (int i = 0; i < xmlContent.getChildNodes().getLength(); i++) {
            entered = true;

            Node property = xmlContent.getChildNodes().item(i);
            if ("ID".equals(property.getLocalName())) {
                checkID = true;

                assertEquals("Edm.Int32", property.getAttributes().item(0).getTextContent());
                assertEquals("1", property.getTextContent());
            }
        }
        assertTrue(entered);
        assertTrue(checkID);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(entry, System.out);
    }

    @Test
    public void readODataEntity() {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(
                "http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc");
        uriBuilder.appendEntityTypeSegment("Products(1)");

        final ODataEntityRequest req = ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build());
        req.setContentType(ODataFormat.ATOM.toString());

        final ODataQueryResponse<ODataEntity> res = req.execute();
        final ODataEntity entity = res.getBody();

        assertNotNull(entity);
        assertEquals("ODataDemo.Product", entity.getName());
        assertEquals("http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc/Products(1)",
                entity.getEditLink().toASCIIString());

        assertEquals(4, entity.getAllLinks().size());
        assertNotNull(entity.getAtomExtensions());
        assertEquals("http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc/Products(1)",
                entity.getAtomExtensions().getId());
        assertEquals("Low fat milk", entity.getAtomExtensions().getSummary());

        boolean check = false;

        for (ODataItem link : entity.getAllLinks()) {
            if ("Category".equals(link.getName())
                    && "http://services.odata.org/V3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc/Products(1)/Category".
                    equals(link.getLink().toASCIIString())) {
                check = true;
            }
        }

        assertTrue(check);
    }
}
