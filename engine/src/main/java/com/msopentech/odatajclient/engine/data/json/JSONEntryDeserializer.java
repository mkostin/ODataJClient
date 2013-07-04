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
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.msopentech.odatajclient.engine.data.ODataOperation;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Parse JSON string into <tt>JSONEntry</tt>.
 * If metadata information is available, the corresponding JSONEntry fields and content will be populated.
 *
 * @see JSONEntry
 */
public class JSONEntryDeserializer extends JsonDeserializer<JSONEntry> {

    private String getTitle(final Map.Entry<String, JsonNode> entry) {
        return entry.getKey().substring(0, entry.getKey().indexOf('@'));
    }

    private String setInline(final String name, final String suffix, final ObjectNode tree,
            final ObjectCodec codec, final JSONLink link) throws IOException {

        final String entryNamePrefix = name.substring(0, name.indexOf(suffix));
        if (tree.has(entryNamePrefix)) {
            final JsonNode inline = tree.path(entryNamePrefix);

            if (inline instanceof ObjectNode) {
                link.setInlineEntry(inline.traverse(codec).readValuesAs(JSONEntry.class).next());
            }

            if (inline instanceof ArrayNode) {
                final JSONFeed feed = new JSONFeed();
                final Iterator<JsonNode> entries = ((ArrayNode) inline).elements();
                while (entries.hasNext()) {
                    feed.addEntry(entries.next().traverse(codec).readValuesAs(JSONEntry.class).next());
                }

                link.setInlineFeed(feed);
            }
        }
        return entryNamePrefix;
    }

    @Override
    public JSONEntry deserialize(final JsonParser parser, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        final ObjectNode tree = (ObjectNode) parser.getCodec().readTree(parser);

        final boolean isMediaEntry =
                tree.hasNonNull(ODataConstants.JSON_MEDIAREAD_LINK)
                && tree.hasNonNull(ODataConstants.JSON_MEDIA_CONTENT_TYPE);

        final JSONEntry entry = new JSONEntry();

        if (tree.hasNonNull(ODataConstants.JSON_METADATA)) {
            entry.setMetadata(URI.create(tree.get(ODataConstants.JSON_METADATA).textValue()));
            tree.remove(ODataConstants.JSON_METADATA);
        }

        if (tree.hasNonNull(ODataConstants.JSON_TYPE)) {
            entry.setType(tree.get(ODataConstants.JSON_TYPE).textValue());
            tree.remove(ODataConstants.JSON_TYPE);
        }

        if (tree.hasNonNull(ODataConstants.JSON_ID)) {
            entry.setId(tree.get(ODataConstants.JSON_ID).textValue());
            tree.remove(ODataConstants.JSON_ID);
        }

        if (tree.hasNonNull(ODataConstants.JSON_ETAG)) {
            entry.setEtag(tree.get(ODataConstants.JSON_ETAG).textValue());
            tree.remove(ODataConstants.JSON_ETAG);
        }

        if (tree.hasNonNull(ODataConstants.JSON_READ_LINK)) {
            entry.setSelfLink(new JSONLink(null,
                    ODataConstants.SELF_LINK_REL,
                    tree.get(ODataConstants.JSON_READ_LINK).textValue()));
            tree.remove(ODataConstants.JSON_READ_LINK);
        }

        if (tree.hasNonNull(ODataConstants.JSON_EDIT_LINK)) {
            entry.setEditLink(new JSONLink(null,
                    ODataConstants.EDIT_LINK_REL,
                    tree.get(ODataConstants.JSON_EDIT_LINK).textValue()));
            tree.remove(ODataConstants.JSON_EDIT_LINK);
        }

        if (tree.hasNonNull(ODataConstants.JSON_MEDIAREAD_LINK)) {
            entry.setMediaContentSource(tree.get(ODataConstants.JSON_MEDIAREAD_LINK).textValue());
            tree.remove(ODataConstants.JSON_MEDIAREAD_LINK);
        }
        if (tree.hasNonNull(ODataConstants.JSON_MEDIAEDIT_LINK)) {
            entry.addMediaEditLink(new JSONLink(null, null,
                    tree.get(ODataConstants.JSON_MEDIAEDIT_LINK).textValue()));
            tree.remove(ODataConstants.JSON_MEDIAEDIT_LINK);
        }
        if (tree.hasNonNull(ODataConstants.JSON_MEDIA_CONTENT_TYPE)) {
            entry.setMediaContentType(tree.get(ODataConstants.JSON_MEDIA_CONTENT_TYPE).textValue());
            tree.remove(ODataConstants.JSON_MEDIA_CONTENT_TYPE);
        }

        final Set<String> toRemove = new HashSet<String>();
        final Iterator<Map.Entry<String, JsonNode>> itor = tree.fields();
        while (itor.hasNext()) {
            final Map.Entry<String, JsonNode> fields = itor.next();
            
            if (fields.getKey().endsWith(ODataConstants.JSON_NAVIGATION_LINK_SUFFIX)) {
                final JSONLink link = new JSONLink(getTitle(fields),
                        ODataConstants.NAVIGATION_LINK_REL + getTitle(fields),
                        fields.getValue().textValue());
                entry.addNavigationLink(link);
                toRemove.add(fields.getKey());

                toRemove.add(setInline(fields.getKey(),
                        ODataConstants.JSON_NAVIGATION_LINK_SUFFIX, tree, parser.getCodec(), link));
            } else if (fields.getKey().endsWith(ODataConstants.JSON_ASSOCIATION_LINK_SUFFIX)) {
                entry.addAssociationLink(new JSONLink(getTitle(fields),
                        ODataConstants.ASSOCIATION_LINK_REL + getTitle(fields),
                        fields.getValue().textValue()));
                toRemove.add(fields.getKey());
            } else if (fields.getKey().endsWith(ODataConstants.JSON_MEDIAEDIT_LINK_SUFFIX)) {
                final JSONLink link = new JSONLink(getTitle(fields),
                        ODataConstants.MEDIA_EDIT_LINK_REL + getTitle(fields),
                        fields.getValue().textValue());
                entry.addMediaEditLink(link);
                toRemove.add(fields.getKey());

                toRemove.add(setInline(fields.getKey(),
                        ODataConstants.JSON_MEDIAEDIT_LINK_SUFFIX, tree, parser.getCodec(), link));
            } else if (fields.getKey().charAt(0) == '#') {
                final ODataOperation operation = new ODataOperation();
                operation.setMetadataAnchor(fields.getKey());

                final ObjectNode opNode = (ObjectNode) tree.get(fields.getKey());
                operation.setTitle(opNode.get(ODataConstants.ATTR_TITLE).asText());
                operation.setTarget(URI.create(opNode.get(ODataConstants.ATTR_TARGET).asText()));

                entry.addOperation(operation);

                toRemove.add(fields.getKey());
            }
        }
        tree.remove(toRemove);

        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.newDocument();

            final Element properties = document.createElementNS(
                    ODataConstants.NS_METADATA, ODataConstants.ELEM_PROPERTIES);

            TreeUtils.buildSubtree(document, properties, tree);

            if (isMediaEntry) {
                entry.setMediaEntryProperties(properties);
            } else {
                entry.setContent(properties);
            }
        } catch (ParserConfigurationException e) {
            throw new JsonParseException("Cannot build entry content", parser.getCurrentLocation(), e);
        }

        return entry;
    }
}
