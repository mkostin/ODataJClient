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
import com.msopentech.odatajclient.engine.data.ODataLinkType;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import com.msopentech.odatajclient.engine.utils.XMLUtils;
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

    private List<Node> getChildNodes(final Node node, final short nodetype) {
        final List<Node> result = new ArrayList<Node>();
        final NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            final Node child = children.item(i);
            if (child.getNodeType() == nodetype) {
                result.add(child);
            }
        }
        return result;
    }

    private boolean hasElementsChildNode(final Node node) {
        boolean found = false;

        for (Node child : getChildNodes(node, Node.ELEMENT_NODE)) {
            if (ODataConstants.ELEM_ELEMENT.equals(XMLUtils.getSimpleName(child))) {
                found = true;
            }
        }

        return found;
    }

    private boolean hasOnlyTextChildNodes(final Node node) {
        boolean result = true;
        final NodeList children = node.getChildNodes();
        for (int i = 0; result && i < children.getLength(); i++) {
            final Node child = children.item(i);
            if (child.getNodeType() != Node.TEXT_NODE) {
                result = false;
            }
        }

        return result;
    }

    private void writeEntryContent(final JsonGenerator jgen, final Node content) throws IOException {
        for (Node child : getChildNodes(content, Node.ELEMENT_NODE)) {
            final String childName = XMLUtils.getSimpleName(child);
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
    public void serialize(final JSONEntry entry, final JsonGenerator jgen, final SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();

        if (entry.getMetadata() != null) {
            jgen.writeStringField(ODataConstants.JSON_METADATA, entry.getMetadata().toASCIIString());
        }
        if (StringUtils.isNotBlank(entry.getType())) {
            jgen.writeStringField(ODataConstants.JSON_TYPE, entry.getType());
        }
        if (entry.getId() != null) {
            jgen.writeStringField(ODataConstants.JSON_ID, entry.getId());
        }
        if (StringUtils.isNotBlank(entry.getEtag())) {
            jgen.writeStringField(ODataConstants.JSON_ETAG, entry.getEtag());
        }

        if (entry.getSelfLink() != null) {
            jgen.writeStringField(ODataConstants.JSON_READ_LINK, entry.getSelfLink().getHref());
        }

        if (entry.getEditLink() != null) {
            jgen.writeStringField(ODataConstants.JSON_EDIT_LINK, entry.getEditLink().getHref());
        }

        if (entry.getMediaContentSource() != null) {
            jgen.writeStringField(ODataConstants.JSON_MEDIAREAD_LINK, entry.getMediaContentSource());
        }
        if (entry.getMediaContentType() != null) {
            jgen.writeStringField(ODataConstants.JSON_MEDIA_CONTENT_TYPE, entry.getMediaContentType());
        }

        for (JSONLink link : entry.getNavigationLinks()) {
            if (link.getInlineEntry() != null) {
                jgen.writeObjectField(link.getTitle(), link.getInlineEntry());
            } else if (link.getInlineFeed() != null) {
                jgen.writeObjectField(link.getTitle(), link.getInlineFeed());
            } else {
                ODataLinkType type = null;
                try {
                    type = ODataLinkType.fromString(link.getRel(), link.getType());
                } catch (IllegalArgumentException e) {
                    // ignore   
                }

                if (type == ODataLinkType.ENTITY_SET_NAVIGATION) {
                    jgen.writeArrayFieldStart(link.getTitle() + ODataConstants.JSON_BIND_LINK_SUFFIX);
                    jgen.writeString(link.getHref());
                    jgen.writeEndArray();
                } else {
                    jgen.writeStringField(link.getTitle() + ODataConstants.JSON_BIND_LINK_SUFFIX, link.getHref());
                }
            }
        }
        for (JSONLink link : entry.getMediaEditLinks()) {
            if (link.getTitle() == null) {
                jgen.writeStringField(ODataConstants.JSON_MEDIAEDIT_LINK, link.getHref());
            }

            if (link.getInlineEntry() != null) {
                jgen.writeObjectField(link.getTitle(), link.getInlineEntry());
            }
            if (link.getInlineFeed() != null) {
                jgen.writeObjectField(link.getTitle(), link.getInlineFeed());
            }
        }

        if (entry.getMediaEntryProperties() == null) {
            writeEntryContent(jgen, entry.getContent());
        } else {
            writeEntryContent(jgen, entry.getMediaEntryProperties());
        }

        jgen.writeEndObject();
    }
}
