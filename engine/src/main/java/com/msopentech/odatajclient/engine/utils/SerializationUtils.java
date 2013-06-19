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
package com.msopentech.odatajclient.engine.utils;

import static com.msopentech.odatajclient.engine.utils.ODataBinder.LOG;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class SerializationUtils {

    public static <T extends EntryResource> void serializeEntry(final T obj, final OutputStream out) {
        serializeEntry(obj, new OutputStreamWriter(out));
    }

    public static <T extends EntryResource> void serializeEntry(final T obj, final Writer writer) {
        if (obj.getClass().equals(AtomEntry.class)) {
            serializeAsAtom(obj, obj.getClass(), writer);
        } else {
            serializeAsJSON(obj, writer);
        }
    }

    private static void serializeAsAtom(final Object obj, final Class<?> reference, final Writer writer) {
        try {
            JAXBContext context = JAXBContext.newInstance(reference);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(obj, writer);
        } catch (JAXBException e) {
            LOG.error("Failure serializing Atom object", e);
            throw new IllegalArgumentException("While serializing Atom object", e);
        }
    }

    private static void serializeAsJSON(final Object obj, final Writer writer) {
        try {
            ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.writeValue(writer, obj);
        } catch (IOException e) {
            LOG.error("Failure serializing JSON object", e);
            throw new IllegalArgumentException("While serializing JSON object", e);
        }
    }
}
