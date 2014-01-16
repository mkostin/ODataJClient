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
package com.msopentech.odatajclient.engine.data.metadata.edm;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class SchemaDeserializer extends JsonDeserializer<Schema> {

    @Override
    public Schema deserialize(final JsonParser jp, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        final Schema schema = new Schema();

        for (; jp.getCurrentToken() != JsonToken.END_OBJECT; jp.nextToken()) {
            final JsonToken token = jp.getCurrentToken();
            if (token == JsonToken.FIELD_NAME) {
                if ("Namespace".equals(jp.getCurrentName())) {
                    schema.setNamespace(jp.nextTextValue());
                } else if ("Alias".equals(jp.getCurrentName())) {
                    schema.setAlias(jp.nextTextValue());
                } else if ("Using".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    schema.getUsings().add(jp.getCodec().readValue(jp, Using.class));
                } else if ("Association".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    schema.getAssociations().add(jp.getCodec().readValue(jp, Association.class));
                } else if ("ComplexType".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    schema.getComplexTypes().add(jp.getCodec().readValue(jp, ComplexType.class));
                } else if ("EntityType".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    schema.getEntityTypes().add(jp.getCodec().readValue(jp, EntityType.class));
                } else if ("EnumType".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    schema.getEnumTypes().add(jp.getCodec().readValue(jp, EnumType.class));
                } else if ("ValueTerm".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    schema.getValueTerms().add(jp.getCodec().readValue(jp, ValueTerm.class));
                } else if ("EntityContainer".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    schema.getEntityContainers().add(jp.getCodec().readValue(jp, EntityContainer.class));
                } else if ("Annotations".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    schema.getAnnotations().add(jp.getCodec().readValue(jp, Annotations.class));
                } else if ("Action".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    schema.getOdataActions().add(jp.getCodec().readValue(jp, V4Action.class));
                }
            }
        }

        return schema;
    }
}
