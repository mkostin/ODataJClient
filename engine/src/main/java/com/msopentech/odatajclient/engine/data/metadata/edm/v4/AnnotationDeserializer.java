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
package com.msopentech.odatajclient.engine.data.metadata.edm.v4;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.dataformat.xml.deser.FromXmlParser;
import com.msopentech.odatajclient.engine.data.metadata.edm.AbstractEdmDeserializer;
import java.io.IOException;

public class AnnotationDeserializer extends AbstractEdmDeserializer<Annotation> {

    @Override
    protected Annotation doDeserialize(final JsonParser jp, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        final Annotation annotation = new Annotation();

        // Replace as per Issue #155
        // for (; jp.getCurrentToken() != JsonToken.END_OBJECT; jp.nextToken()) {
        for (; jp.getCurrentToken() != JsonToken.END_OBJECT
                || !"Annotation".equals(((FromXmlParser) jp).getStaxReader().getLocalName()); jp.nextToken()) {

            final JsonToken token = jp.getCurrentToken();
            if (token == JsonToken.FIELD_NAME) {
                if ("Term".equals(jp.getCurrentName())) {
                    annotation.setTerm(jp.nextTextValue());
                } else if ("Qualifier".equals(jp.getCurrentName())) {
                    annotation.setQualifier(jp.nextTextValue());
                } else if (AnnotationConstantExpressionType.fromString(jp.getCurrentName()) != null) {
                    annotation.setConstantExpressionType(
                            AnnotationConstantExpressionType.fromString(jp.getCurrentName()));
                    if (jp.getCurrentName().equals(((FromXmlParser) jp).getStaxReader().getLocalName())) {
                        jp.nextToken();
                    }
                    annotation.setConstantExpressionValue(jp.nextTextValue());
                } else {
                    // Issue #155
                }
            }
        }

        return annotation;
    }

}
