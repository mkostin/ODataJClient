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

public class EntityContainerDeserializer extends JsonDeserializer<EntityContainer> {

    @Override
    public EntityContainer deserialize(final JsonParser jp, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        final EntityContainer entityContainer = new EntityContainer();

        for (; jp.getCurrentToken() != JsonToken.END_OBJECT; jp.nextToken()) {
            final JsonToken token = jp.getCurrentToken();
            if (token == JsonToken.FIELD_NAME) {
                if ("Name".equals(jp.getCurrentName())) {
                    entityContainer.setName(jp.nextTextValue());
                } else if ("Extends".equals(jp.getCurrentName())) {
                    entityContainer.setExtends(jp.nextTextValue());
                } else if ("LazyLoadingEnabled".equals(jp.getCurrentName())) {
                    entityContainer.setLazyLoadingEnabled(Boolean.valueOf(jp.nextTextValue()));
                } else if ("IsDefaultEntityContainer".equals(jp.getCurrentName())) {
                    entityContainer.setDefaultEntityContainer(Boolean.valueOf(jp.nextTextValue()));
                } else if ("EntitySet".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    entityContainer.getEntitySets().add(jp.getCodec().readValue(jp, EntitySet.class));
                } else if ("AssociationSet".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    entityContainer.getAssociationSets().add(jp.getCodec().readValue(jp, AssociationSet.class));
                } else if ("FunctionImport".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    entityContainer.getFunctionImports().add(jp.getCodec().readValue(jp, FunctionImport.class));
                } else if ("Singleton".equals(jp.getCurrentName())) {
                    jp.nextToken();
                    entityContainer.getSingletons().add(jp.getCodec().readValue(jp, Singleton.class));
                }
            }
        }

        return entityContainer;
    }
}
