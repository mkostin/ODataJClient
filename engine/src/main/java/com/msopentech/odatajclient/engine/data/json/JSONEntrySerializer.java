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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Writes out JSON string from <tt>JSONEntry</tt>.
 *
 * @see JSONEntry
 */
public class JSONEntrySerializer extends JsonSerializer<JSONEntry> {

    private String getSimpleName(Node node) {
        return node.getLocalName() == null
                ? node.getNodeName().substring(node.getNodeName().indexOf(':') + 1)
                : node.getLocalName();
    }

    private List<Node> getChildNodes(Node node, short nodetype) {
        List<Node> result = new ArrayList<Node>();
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == nodetype) {
                result.add(child);
            }
        }
        return result;
    }

    private boolean hasElementsChildNode(Node node) {
        boolean found = false;

        for (Node child : getChildNodes(node, Node.ELEMENT_NODE)) {
            if (ODataConstants.ELEM_ELEMENT.equals(getSimpleName(child))) {
                found = true;
            }
        }

        return found;
    }

    private boolean hasOnlyTextChildNodes(Node node) {
        boolean result = true;
        NodeList children = node.getChildNodes();
        for (int i = 0; result && i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() != Node.TEXT_NODE) {
                result = false;
            }
        }

        return result;
    }

    private void writeEntryContent(JsonGenerator jgen, Node content) throws IOException {
        for (Node child : getChildNodes(content, Node.ELEMENT_NODE)) {
            String childName = getSimpleName(child);
            if (hasOnlyTextChildNodes(child)) {
                if (child.hasChildNodes()) {
                    jgen.writeStringField(childName, child.getChildNodes().item(0).getNodeValue());
                } else {
                    jgen.writeNullField(childName);
                }
            } else {
                if (hasElementsChildNode(child)) {
                    jgen.writeArrayFieldStart(childName);

                    for (Node nephew : getChildNodes(child, Node.ELEMENT_NODE)) {
                        if (hasOnlyTextChildNodes(nephew)) {
                            jgen.writeString(nephew.getChildNodes().item(0).getNodeValue());
                        } else {
                            jgen.writeStartObject();
                            writeEntryContent(jgen, nephew);
                            jgen.writeEndObject();
                        }
                    }

                    jgen.writeEndArray();
                } else {
                    jgen.writeObjectFieldStart(childName);
                    writeEntryContent(jgen, child);
                    jgen.writeEndObject();
                }
            }
        }
    }

    @Override
    public void serialize(JSONEntry value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();

        if (value.getMetadata() != null) {
            jgen.writeStringField(JSONConstants.METADATA, value.getMetadata().toURL().toExternalForm());
        }
        if (StringUtils.isNotBlank(value.getType())) {
            jgen.writeStringField(JSONConstants.TYPE, value.getType());
        }
        if (value.getId() != null) {
            jgen.writeStringField(JSONConstants.ID, value.getId());
        }
        if (StringUtils.isNotBlank(value.getEtag())) {
            jgen.writeStringField(JSONConstants.ETAG, value.getEtag());
        }

        if (value.getSelfLink() != null) {
            jgen.writeStringField(JSONConstants.READ_LINK, value.getSelfLink());
        }

        if (value.getEditLink() != null) {
            jgen.writeStringField(JSONConstants.EDIT_LINK, value.getEditLink());
        }

        for (JSONLink link : value.getNavigationLinks()) {
            jgen.writeStringField(link.getTitle() + JSONConstants.NAVIGATION_LINK_SUFFIX, link.getHref());
        }
        for (JSONLink link : value.getAssociationLinks()) {
            jgen.writeStringField(link.getTitle() + JSONConstants.ASSOCIATION_LINK_SUFFIX, link.getHref());
        }
        for (JSONLink link : value.getMediaEditLinks()) {
            jgen.writeStringField(link.getTitle() + JSONConstants.MEDIAEDIT_LINK_SUFFIX, link.getHref());
        }

        writeEntryContent(jgen, value.getContent());

        jgen.writeEndObject();
    }
}
