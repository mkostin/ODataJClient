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
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
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

    /**
     * Recursive step for DOM content generation.
     *
     * @see #getContent(com.fasterxml.jackson.databind.JsonNode)
     *
     * @param document root of the DOM document being built
     * @param parent parent of the nodes being generated during this step
     * @param node JSON node to be used as source for DOM elements
     */
    private void buildSubtree(final Document document, final Element parent, final JsonNode node) {
        Iterator<String> fieldNameItor = node.fieldNames();
        Iterator<JsonNode> nodeItor = node.elements();
        while (nodeItor.hasNext()) {
            JsonNode child = nodeItor.next();
            String name = fieldNameItor.hasNext() ? fieldNameItor.next() : "";

            // TODO: see issue #74
            if (name.startsWith("#")) {
                break;
            }

            // no name? array item
            if (name.isEmpty()) {
                Element element = document.createElementNS(ODataConstants.NS_DATASERVICES,
                        ODataConstants.PREFIX_DATASERVICES + ODataConstants.ELEM_ELEMENT);
                parent.appendChild(element);

                if (child.isValueNode()) {
                    element.appendChild(document.createTextNode(child.asText()));
                }

                if (child.isContainerNode()) {
                    buildSubtree(document, element, child);
                }
            } else if (!name.contains("@") && !JSONConstants.TYPE.equals(name)) {
                Element property = document.createElementNS(
                        ODataConstants.NS_DATASERVICES, ODataConstants.PREFIX_DATASERVICES + name);
                parent.appendChild(property);

                boolean typeSet = false;
                if (node.hasNonNull(name + "@" + JSONConstants.TYPE)) {
                    property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                            node.get(name + "@" + JSONConstants.TYPE).textValue());
                    typeSet = true;
                }

                if (child.isNull()) {
                    property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_NULL,
                            Boolean.toString(true));
                } else if (child.isValueNode()) {
                    if (!typeSet && child.isInt()) {
                        property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                                EdmSimpleType.INT_32.toString());
                    }
                    property.appendChild(document.createTextNode(child.asText()));
                } else if (child.isContainerNode()) {
                    if (child.hasNonNull(JSONConstants.TYPE)) {
                        property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                                child.get(JSONConstants.TYPE).textValue());
                    }

                    buildSubtree(document, property, child);
                }
            }
        }
    }

    /**
     * Recursively builds DOM content out of JSON subtree rooted at given node.
     *
     * @param node root node of the content JSON subtree
     * @return DOM subtree generated from the given JSON
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created
     */
    private Element getContent(final JsonNode node) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element properties = document.createElementNS(
                ODataConstants.NS_METADATA, ODataConstants.ELEM_PROPERTIES);

        buildSubtree(document, properties, node);

        return properties;
    }

    private String setInlineEntry(final String name, final String suffix, final ObjectNode tree,
            final ObjectCodec codec, final JSONLink link) throws IOException {

        String entryNamePrefix = name.substring(0, name.indexOf(suffix));
        if (tree.has(entryNamePrefix)) {
            link.setInlineEntry(tree.path(entryNamePrefix).traverse(codec).readValuesAs(JSONEntry.class).next());
        }
        return entryNamePrefix;
    }

    @Override
    public JSONEntry deserialize(final JsonParser jp, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        ObjectNode tree = (ObjectNode) jp.getCodec().readTree(jp);

        final boolean isMediaEntry =
                tree.hasNonNull(JSONConstants.MEDIAREAD_LINK) && tree.hasNonNull(JSONConstants.MEDIA_CONTENT_TYPE);

        JSONEntry jsonEntry = new JSONEntry();

        if (tree.hasNonNull(JSONConstants.METADATA)) {
            jsonEntry.setMetadata(URI.create(tree.get(JSONConstants.METADATA).textValue()));
            tree.remove(JSONConstants.METADATA);
        }

        if (tree.hasNonNull(JSONConstants.TYPE)) {
            jsonEntry.setType(tree.get(JSONConstants.TYPE).textValue());
            tree.remove(JSONConstants.TYPE);
        }

        if (tree.hasNonNull(JSONConstants.ID)) {
            jsonEntry.setId(tree.get(JSONConstants.ID).textValue());
            tree.remove(JSONConstants.ID);
        }

        if (tree.hasNonNull(JSONConstants.ETAG)) {
            jsonEntry.setEtag(tree.get(JSONConstants.ETAG).textValue());
            tree.remove(JSONConstants.ETAG);
        }

        if (tree.hasNonNull(JSONConstants.READ_LINK)) {
            jsonEntry.setSelfLink(new JSONLink(null,
                    ODataConstants.SELF_LINK_REL,
                    tree.get(JSONConstants.READ_LINK).textValue()));
            tree.remove(JSONConstants.READ_LINK);
        }

        if (tree.hasNonNull(JSONConstants.EDIT_LINK)) {
            jsonEntry.setEditLink(new JSONLink(null,
                    ODataConstants.EDIT_LINK_REL,
                    tree.get(JSONConstants.EDIT_LINK).textValue()));
            tree.remove(JSONConstants.EDIT_LINK);
        }

        if (tree.hasNonNull(JSONConstants.MEDIAREAD_LINK)) {
            jsonEntry.setMediaContentSource(tree.get(JSONConstants.MEDIAREAD_LINK).textValue());
            tree.remove(JSONConstants.MEDIAREAD_LINK);
        }
        if (tree.hasNonNull(JSONConstants.MEDIAEDIT_LINK)) {
            jsonEntry.addMediaEditLink(new JSONLink(null, null,
                    tree.get(JSONConstants.MEDIAEDIT_LINK).textValue()));
            tree.remove(JSONConstants.MEDIAEDIT_LINK);
        }
        if (tree.hasNonNull(JSONConstants.MEDIA_CONTENT_TYPE)) {
            jsonEntry.setMediaContentType(tree.get(JSONConstants.MEDIA_CONTENT_TYPE).textValue());
            tree.remove(JSONConstants.MEDIA_CONTENT_TYPE);
        }

        Set<String> toRemove = new HashSet<String>();
        Iterator<Map.Entry<String, JsonNode>> itor = tree.fields();
        while (itor.hasNext()) {
            Map.Entry<String, JsonNode> entry = itor.next();
            if (entry.getKey().endsWith(JSONConstants.NAVIGATION_LINK_SUFFIX)) {
                JSONLink link = new JSONLink(getTitle(entry),
                        ODataConstants.NAVIGATION_LINK_REL + getTitle(entry),
                        entry.getValue().textValue());
                jsonEntry.addNavigationLink(link);
                toRemove.add(entry.getKey());

                toRemove.add(setInlineEntry(entry.getKey(),
                        JSONConstants.NAVIGATION_LINK_SUFFIX, tree, jp.getCodec(), link));
            } else if (entry.getKey().endsWith(JSONConstants.ASSOCIATION_LINK_SUFFIX)) {
                jsonEntry.addAssociationLink(new JSONLink(getTitle(entry),
                        ODataConstants.ASSOCIATION_LINK_REL + getTitle(entry),
                        entry.getValue().textValue()));
                toRemove.add(entry.getKey());
            } else if (entry.getKey().endsWith(JSONConstants.MEDIAEDIT_LINK_SUFFIX)) {
                JSONLink link = new JSONLink(getTitle(entry),
                        ODataConstants.MEDIA_EDIT_LINK_REL + getTitle(entry),
                        entry.getValue().textValue());
                jsonEntry.addMediaEditLink(link);
                toRemove.add(entry.getKey());

                toRemove.add(setInlineEntry(entry.getKey(),
                        JSONConstants.MEDIAEDIT_LINK_SUFFIX, tree, jp.getCodec(), link));
            }
        }
        tree.remove(toRemove);

        try {
            final Element content = getContent(tree);
            if (isMediaEntry) {
                jsonEntry.setMediaEntryProperties(content);
            } else {
                jsonEntry.setContent(content);
            }
        } catch (ParserConfigurationException e) {
            throw new JsonParseException("Cannot build entry content", jp.getCurrentLocation(), e);
        }

        return jsonEntry;
    }
}
