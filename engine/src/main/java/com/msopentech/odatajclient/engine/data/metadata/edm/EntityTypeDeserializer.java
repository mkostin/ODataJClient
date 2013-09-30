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

public class EntityTypeDeserializer extends JsonDeserializer<EntityType> {

    @Override
    public EntityType deserialize(final JsonParser jp, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        final EntityType entityType = new EntityType();

        for (; jp.getCurrentToken() != JsonToken.END_OBJECT; jp.nextToken()) {
            final JsonToken token = jp.getCurrentToken();
            if (token == JsonToken.FIELD_NAME) {
                if ("Name".equals(jp.getCurrentName())) {
                    entityType.setName(jp.nextTextValue());
                } else if ("Abstract".equals(jp.getCurrentName())) {
                    entityType.setAbstractEntityType(Boolean.valueOf(jp.nextTextValue()));
                } else if ("BaseType".equals(jp.getCurrentName())) {
                    entityType.setBaseType(jp.nextTextValue());
                } else if ("OpenType".equals(jp.getCurrentName())) {
                    entityType.setOpenType(Boolean.valueOf(jp.nextTextValue()));
                } else if ("HasStream".equals(jp.getCurrentName())) {
                    entityType.setHasStream(Boolean.valueOf(jp.nextTextValue()));
                } else if ("Key".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    entityType.setKey(jp.getCodec().readValue(jp, EntityKey.class));
                } else if ("Property".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    entityType.getProperties().add(jp.getCodec().readValue(jp, Property.class));
                } else if ("NavigationProperty".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    entityType.getNavigationProperties().add(jp.getCodec().readValue(jp, NavigationProperty.class));
                }
            }
        }

        return entityType;
    }
}
