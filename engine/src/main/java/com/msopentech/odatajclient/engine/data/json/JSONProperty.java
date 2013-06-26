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
package com.msopentech.odatajclient.engine.data.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.net.URI;
import org.w3c.dom.Element;

/**
 * A single property (primitive, complex or collection) represented via JSON.
 */
@JsonSerialize(using = JSONPropertySerializer.class)
@JsonDeserialize(using = JSONPropertyDeserializer.class)
public class JSONProperty extends AbstractJSONObject {

    private static final long serialVersionUID = 553414431536637434L;

    private URI metadata;

    private Element content;

    public URI getMetadata() {
        return metadata;
    }

    public void setMetadata(final URI metadata) {
        this.metadata = metadata;
    }

    public Element getContent() {
        return content;
    }

    public void setContent(final Element content) {
        this.content = content;
    }
}
