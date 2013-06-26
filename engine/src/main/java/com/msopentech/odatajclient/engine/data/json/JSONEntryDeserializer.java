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
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    private String setInlineEntry(final String name, final String suffix, final ObjectNode tree,
            final ObjectCodec codec, final JSONLink link) throws IOException {

        final String entryNamePrefix = name.substring(0, name.indexOf(suffix));
        if (tree.has(entryNamePrefix)) {
            link.setInlineEntry(tree.path(entryNamePrefix).traverse(codec).readValuesAs(JSONEntry.class).next());
        }
        return entryNamePrefix;
    }

    @Override
    public JSONEntry deserialize(final JsonParser parser, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        final ObjectNode tree = (ObjectNode) parser.getCodec().readTree(parser);

        final boolean isMediaEntry =
                tree.hasNonNull(JSONConstants.MEDIAREAD_LINK) && tree.hasNonNull(JSONConstants.MEDIA_CONTENT_TYPE);

        final JSONEntry entry = new JSONEntry();

        if (tree.hasNonNull(JSONConstants.METADATA)) {
            entry.setMetadata(URI.create(tree.get(JSONConstants.METADATA).textValue()));
            tree.remove(JSONConstants.METADATA);
        }

        if (tree.hasNonNull(JSONConstants.TYPE)) {
            entry.setType(tree.get(JSONConstants.TYPE).textValue());
            tree.remove(JSONConstants.TYPE);
        }

        if (tree.hasNonNull(JSONConstants.ID)) {
            entry.setId(tree.get(JSONConstants.ID).textValue());
            tree.remove(JSONConstants.ID);
        }

        if (tree.hasNonNull(JSONConstants.ETAG)) {
            entry.setEtag(tree.get(JSONConstants.ETAG).textValue());
            tree.remove(JSONConstants.ETAG);
        }

        if (tree.hasNonNull(JSONConstants.READ_LINK)) {
            entry.setSelfLink(new JSONLink(null,
                    ODataConstants.SELF_LINK_REL,
                    tree.get(JSONConstants.READ_LINK).textValue()));
            tree.remove(JSONConstants.READ_LINK);
        }

        if (tree.hasNonNull(JSONConstants.EDIT_LINK)) {
            entry.setEditLink(new JSONLink(null,
                    ODataConstants.EDIT_LINK_REL,
                    tree.get(JSONConstants.EDIT_LINK).textValue()));
            tree.remove(JSONConstants.EDIT_LINK);
        }

        if (tree.hasNonNull(JSONConstants.MEDIAREAD_LINK)) {
            entry.setMediaContentSource(tree.get(JSONConstants.MEDIAREAD_LINK).textValue());
            tree.remove(JSONConstants.MEDIAREAD_LINK);
        }
        if (tree.hasNonNull(JSONConstants.MEDIAEDIT_LINK)) {
            entry.addMediaEditLink(new JSONLink(null, null,
                    tree.get(JSONConstants.MEDIAEDIT_LINK).textValue()));
            tree.remove(JSONConstants.MEDIAEDIT_LINK);
        }
        if (tree.hasNonNull(JSONConstants.MEDIA_CONTENT_TYPE)) {
            entry.setMediaContentType(tree.get(JSONConstants.MEDIA_CONTENT_TYPE).textValue());
            tree.remove(JSONConstants.MEDIA_CONTENT_TYPE);
        }

        final Set<String> toRemove = new HashSet<String>();
        final Iterator<Map.Entry<String, JsonNode>> itor = tree.fields();
        while (itor.hasNext()) {
            final Map.Entry<String, JsonNode> fields = itor.next();
            if (fields.getKey().endsWith(JSONConstants.NAVIGATION_LINK_SUFFIX)) {
                final JSONLink link = new JSONLink(getTitle(fields),
                        ODataConstants.NAVIGATION_LINK_REL + getTitle(fields),
                        fields.getValue().textValue());
                entry.addNavigationLink(link);
                toRemove.add(fields.getKey());

                toRemove.add(setInlineEntry(fields.getKey(),
                        JSONConstants.NAVIGATION_LINK_SUFFIX, tree, parser.getCodec(), link));
            } else if (fields.getKey().endsWith(JSONConstants.ASSOCIATION_LINK_SUFFIX)) {
                entry.addAssociationLink(new JSONLink(getTitle(fields),
                        ODataConstants.ASSOCIATION_LINK_REL + getTitle(fields),
                        fields.getValue().textValue()));
                toRemove.add(fields.getKey());
            } else if (fields.getKey().endsWith(JSONConstants.MEDIAEDIT_LINK_SUFFIX)) {
                final JSONLink link = new JSONLink(getTitle(fields),
                        ODataConstants.MEDIA_EDIT_LINK_REL + getTitle(fields),
                        fields.getValue().textValue());
                entry.addMediaEditLink(link);
                toRemove.add(fields.getKey());

                toRemove.add(setInlineEntry(fields.getKey(),
                        JSONConstants.MEDIAEDIT_LINK_SUFFIX, tree, parser.getCodec(), link));
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
