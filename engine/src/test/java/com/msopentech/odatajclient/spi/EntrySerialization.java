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

import com.sap.core.odata.api.ep.EntityProviderException;
import com.sap.core.odata.core.commons.ContentType;
import com.sap.core.odata.core.exception.ODataRuntimeException;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.Test;

public class EntrySerialization {

    private static final String DEFAULT_CHARSET = ContentType.CHARSET_UTF_8;

    private static final String XML_VERSION = "1.0";

    @Test
    public void atom() throws EntityProviderException {
        OutputStream outStream = null;
        ODataRuntimeException cachedException = null;

        try {
//            CircleStreamBuffer csb = new CircleStreamBuffer();
//            outStream = csb.getOutputStream();
//            XMLStreamWriter writer = XMLOutputFactory.newInstance().createXMLStreamWriter(outStream, DEFAULT_CHARSET);
//
//            ODataEntity entity = EntityFactory.newEntity("Product");
//            entity.addProperty(new ODataProperty("Rating", new ODataPrimitiveValue(3, EdmSimpleType.Int32)));
//
//            ODataEntityProviderPropertiesBuilder propBuilder = new ODataEntityProviderPropertiesBuilder();
//            propBuilder.serviceRoot(new ODataURIBuilder("http://services.odata.org/OData/Odata.svc").build());
//
//            AtomEntryEntityProducer as = new AtomEntryEntityProducer(propBuilder.build());
//
//            as.append(writer, entity, true, false);
//
//            writer.flush();
//            outStream.flush();
//            outStream.close();
//
//            final InputStream is = csb.getInputStream();
//
//            byte[] buff = new byte[1024];
//
//            while (is.read(buff) >= 0) {
//                System.out.print(new String(buff));
//            }
//
//            is.close();
//
//        } catch (XMLStreamException e) {
//            cachedException = new ODataRuntimeException(e);
//            throw cachedException;
//        } catch (IOException e) {
//            cachedException = new ODataRuntimeException(e);
//            throw cachedException;
        } finally {// NOPMD (suppress DoNotThrowExceptionInFinally)
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    if (cachedException != null) {
                        throw cachedException;
                    } else {
                        throw new ODataRuntimeException(e);
                    }
                }
            }
        }
    }
}
