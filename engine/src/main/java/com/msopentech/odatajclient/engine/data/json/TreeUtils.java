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
import com.fasterxml.jackson.databind.JsonNode;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import com.msopentech.odatajclient.engine.utils.XMLUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

final class TreeUtils {

    private TreeUtils() {
        // Empty private constructor for static utility classes
    }

    /**
     * Recursively builds DOM content out of JSON subtree rooted at given node.
     *
     * @see #getContent(com.fasterxml.jackson.databind.JsonNode)
     *
     * @param document root of the DOM document being built
     * @param parent parent of the nodes being generated during this step
     * @param node JSON node to be used as source for DOM elements
     */
    public static void buildSubtree(final Document document, final Element parent, final JsonNode node) {
        final Iterator<String> fieldNameItor = node.fieldNames();
        final Iterator<JsonNode> nodeItor = node.elements();
        while (nodeItor.hasNext()) {
            final JsonNode child = nodeItor.next();
            final String name = fieldNameItor.hasNext() ? fieldNameItor.next() : "";

            // no name? array item
            if (name.isEmpty()) {
                final Element element = document.createElementNS(ODataConstants.NS_DATASERVICES,
                        ODataConstants.PREFIX_DATASERVICES + ODataConstants.ELEM_ELEMENT);
                parent.appendChild(element);

                if (child.isValueNode()) {
                    if (child.isNull()) {
                        element.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_NULL,
                                Boolean.toString(true));
                    } else {
                        element.appendChild(document.createTextNode(child.asText()));
                    }
                }

                if (child.isContainerNode()) {
                    buildSubtree(document, element, child);
                }
            } else if (!name.contains("@") && !ODataConstants.JSON_TYPE.equals(name)) {
                final Element property = document.createElementNS(
                        ODataConstants.NS_DATASERVICES, ODataConstants.PREFIX_DATASERVICES + name);
                parent.appendChild(property);

                boolean typeSet = false;
                if (node.hasNonNull(name + "@" + ODataConstants.JSON_TYPE)) {
                    property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                            node.get(name + "@" + ODataConstants.JSON_TYPE).textValue());
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
                    if (child.hasNonNull(ODataConstants.JSON_TYPE)) {
                        property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                                child.get(ODataConstants.JSON_TYPE).textValue());
                    }

                    buildSubtree(document, property, child);
                }
            }
        }
    }

    public static List<Node> getChildNodes(final Node node, final short nodetype) {
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

    public static boolean hasElementsChildNode(final Node node) {
        boolean found = false;

        for (Node child : getChildNodes(node, Node.ELEMENT_NODE)) {
            if (ODataConstants.ELEM_ELEMENT.equals(XMLUtils.getSimpleName(child))) {
                found = true;
            }
        }

        return found;
    }

    public static boolean hasOnlyTextChildNodes(final Node node) {
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

    public static void writeContent(final JsonGenerator jgen, final Node content) throws IOException {
        for (Node child : getChildNodes(content, Node.ELEMENT_NODE)) {
            final String childName = XMLUtils.getSimpleName(child);
            if (hasOnlyTextChildNodes(child)) {
                if (child.hasChildNodes()) {
                    jgen.writeStringField(childName, child.getChildNodes().item(0).getNodeValue());
                } else {
                    if (child.getAttributes().getNamedItem(ODataConstants.ATTR_NULL) == null) {
                        jgen.writeArrayFieldStart(childName);
                        jgen.writeEndArray();
                    } else {
                        jgen.writeNullField(childName);
                    }
                }
            } else {
                if (hasElementsChildNode(child)) {
                    jgen.writeArrayFieldStart(childName);

                    for (Node nephew : getChildNodes(child, Node.ELEMENT_NODE)) {
                        if (hasOnlyTextChildNodes(nephew)) {
                            jgen.writeString(nephew.getChildNodes().item(0).getNodeValue());
                        } else {
                            jgen.writeStartObject();
                            writeContent(jgen, nephew);
                            jgen.writeEndObject();
                        }
                    }

                    jgen.writeEndArray();
                } else {
                    jgen.writeObjectFieldStart(childName);
                    writeContent(jgen, child);
                    jgen.writeEndObject();
                }
            }
        }
    }
}
