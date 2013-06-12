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

import com.msopentech.odatajclient.engine.communication.request.ODataMetadataRequest;
import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataMetadataResponse;
import com.msopentech.odatajclient.engine.sap.JAXBEdmProvider;
import com.sap.core.odata.api.edm.EdmEntityContainer;
import com.sap.core.odata.api.edm.EdmEntitySet;
import com.sap.core.odata.api.edm.provider.EdmProvider;
import com.sap.core.odata.api.edm.provider.EntityContainerInfo;
import com.sap.core.odata.api.edm.provider.EntitySet;
import com.sap.core.odata.api.ep.EntityProviderException;
import com.sap.core.odata.api.ep.EntityProviderReadProperties;
import com.sap.core.odata.api.ep.entry.ODataEntry;
import com.sap.core.odata.api.exception.ODataException;
import com.sap.core.odata.core.edm.parser.EdmxProvider;
import com.sap.core.odata.core.edm.provider.EdmEntityContainerImplProv;
import com.sap.core.odata.core.edm.provider.EdmEntitySetImplProv;
import com.sap.core.odata.core.edm.provider.EdmImplProv;
import com.sap.core.odata.core.ep.consumer.XmlEntityConsumer;
import java.io.InputStream;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

/**
 * This is a temporary class, to be removed once client is actually implemented.
 * TODO: remove
 */
public class AATemporaryTest {

    private EdmxProvider sapParseEdmMetadata() throws EntityProviderException {
        return new EdmxProvider().parse(AATemporaryTest.class.getResourceAsStream("/Northwind-metadata.xml"), true);
    }

    private JAXBEdmProvider jaxbParseEdmMetadata() throws JAXBException {
        final ODataMetadataRequest metadataRequest = ODataRequestFactory.getMetadataRequest(
                "http://services.odata.org/v3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc");
        final ODataMetadataResponse md = metadataRequest.execute();
        return new JAXBEdmProvider(md.getBody().getSchemas());
    }

    public ODataEntry readODataEntry(EdmProvider edmProvider, final String url, final String container,
            final String set) throws ODataException {

        EdmImplProv edmImplProv = new EdmImplProv(edmProvider);

        EntityContainerInfo containerInfo = edmProvider.getEntityContainerInfo(container);
        EdmEntityContainer entityContainer = new EdmEntityContainerImplProv(edmImplProv, containerInfo);

        EntitySet entitySet = edmProvider.getEntitySet(container, set);
        EdmEntitySet edmEntitySet = new EdmEntitySetImplProv(edmImplProv, entitySet, entityContainer);

        WebClient client = WebClient.create(url);

        InputStream stream = client.accept(MediaType.APPLICATION_ATOM_XML).get(InputStream.class);

        return new XmlEntityConsumer().readEntry(edmEntitySet,
                stream,
                EntityProviderReadProperties.init().build());
    }

    @Test
    public void sapRead() throws Exception {
        EdmxProvider edmxProvider = sapParseEdmMetadata();
        System.out.println("EDM Provider: " + edmxProvider);
        System.out.println("ODataEntry: " + readODataEntry(edmxProvider,
                "http://services.odata.org/v3/(S(zrdgvnc1lzrkqfivykmk5leb))/Northwind/Northwind.svc/Categories(1)",
                "NorthwindEntities", "Categories"));
    }

    @Test
    public void jaxbRead() throws Exception {
        JAXBEdmProvider jaxbEdmProvider = jaxbParseEdmMetadata();
        System.out.println("EDM Provider: " + jaxbEdmProvider);
        System.out.println("ODataEntry: " + readODataEntry(jaxbEdmProvider,
                "http://services.odata.org/v3/(S(csquyjnoaywmz5xcdbfhlc1p))/OData/OData.svc/Categories(1)",
                "DemoService", "Categories"));
    }
}
