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
package com.msopentech.odatajclient.engine.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msopentech.odatajclient.engine.client.ODataClient;
import com.msopentech.odatajclient.engine.data.json.InjectableSerializerProvider;

abstract class AbstractJacksonMarshaller {

    protected final ODataClient client;

    public AbstractJacksonMarshaller(final ODataClient client) {
        this.client = client;
    }

    protected ObjectMapper getObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        mapper.setInjectableValues(new InjectableValues.Std().addValue(ODataClient.class, client));

        mapper.setSerializerProvider(new InjectableSerializerProvider(mapper.getSerializerProvider(),
                mapper.getSerializationConfig().withAttribute(ODataClient.class, client),
                mapper.getSerializerFactory()));

        return mapper;
    }

}
