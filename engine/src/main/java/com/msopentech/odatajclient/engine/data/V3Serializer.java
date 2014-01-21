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

import java.io.Writer;

import com.msopentech.odatajclient.engine.client.ODataClient;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.data.atom.AtomFeed;
import com.msopentech.odatajclient.engine.data.json.JSONV3Entry;
import com.msopentech.odatajclient.engine.data.json.JSONV3Feed;

public class V3Serializer extends AbstractSerializer {

    private static final long serialVersionUID = -8861908250297989806L;

    public V3Serializer(final ODataClient client) {
        super(client);
    }

    @Override
    public <T extends FeedResource> void feed(T obj, Writer writer) {
        if (obj instanceof AtomFeed) {
            atom((AtomFeed) obj, writer);
        } else {
            json((JSONV3Feed) obj, writer);
        }
    }

    @Override
    public <T extends EntryResource> void entry(T obj, Writer writer) {
        if (obj instanceof AtomEntry) {
            atom((AtomEntry) obj, writer);
        } else {
            json((JSONV3Entry) obj, writer);
        }
    }

}
