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

import java.io.IOException;
import java.io.InputStream;

import com.msopentech.odatajclient.engine.client.ODataClient;
import com.msopentech.odatajclient.engine.data.json.AbstractJSONFeed;
import com.msopentech.odatajclient.engine.data.json.JSONV4Entry;
import com.msopentech.odatajclient.engine.data.json.JSONV4Feed;
import com.msopentech.odatajclient.engine.data.metadata.edm.v4.V4Edmx;

public class V4Deserializer extends AbstractDeserializer {

    private static final long serialVersionUID = 8593081342440470415L;

    public V4Deserializer(final ODataClient client) {
        super(client);
    }

    @Override
    public V4Edmx toMetadata(final InputStream input) {
        try {
            return getXmlMapper().readValue(input, V4Edmx.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not parse as Edmx document", e);
        }
    }

    @Override
    protected JSONV4Entry toJSONEntry(final InputStream input) {
        try {
            return getObjectMapper().readValue(input, JSONV4Entry.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON entry", e);
        }
    }
    
    @Override
    protected AbstractJSONFeed toJSONFeed(InputStream input) {
        try {
            return getObjectMapper().readValue(input, JSONV4Feed.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("While deserializing JSON feed", e);
        }
    }

}
