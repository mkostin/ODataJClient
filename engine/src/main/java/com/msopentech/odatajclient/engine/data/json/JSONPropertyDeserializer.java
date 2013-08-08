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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.io.IOException;
import java.net.URI;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Parse JSON string into <tt>JSONProperty</tt>.
 *
 * @see JSONProperty
 */
public class JSONPropertyDeserializer extends JsonDeserializer<JSONProperty> {

    /**
     * {@inheritDoc }
     */
    @Override
    public JSONProperty deserialize(final JsonParser parser, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        final ObjectNode tree = (ObjectNode) parser.getCodec().readTree(parser);

        final JSONProperty property = new JSONProperty();

        if (tree.hasNonNull(ODataConstants.JSON_METADATA)) {
            property.setMetadata(URI.create(tree.get(ODataConstants.JSON_METADATA).textValue()));
            tree.remove(ODataConstants.JSON_METADATA);
        }

        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.newDocument();

            final Element content = document.createElement(ODataConstants.ELEM_PROPERTY);

            if (property.getMetadata() != null) {
                final String metadataURI = property.getMetadata().toASCIIString();
                final int dashIdx = metadataURI.lastIndexOf('#');
                if (dashIdx != -1) {
                    content.setAttribute(ODataConstants.ATTR_TYPE, metadataURI.substring(dashIdx + 1));
                }
            }

            JsonNode subtree = null;
            if (tree.has(ODataConstants.JSON_VALUE)) {
                final JsonNode value = tree.get(ODataConstants.JSON_VALUE);
                if (value.isValueNode()) {
                    content.appendChild(document.createTextNode(value.asText()));
                } else {
                    subtree = tree.get(ODataConstants.JSON_VALUE);
                }
            } else {
                subtree = tree;
            }

            if (subtree != null) {
                DOMTreeUtils.buildSubtree(content, subtree);
            }

            property.setContent(content);
        } catch (ParserConfigurationException e) {
            throw new JsonParseException("Cannot build property", parser.getCurrentLocation(), e);
        }

        return property;
    }
}
