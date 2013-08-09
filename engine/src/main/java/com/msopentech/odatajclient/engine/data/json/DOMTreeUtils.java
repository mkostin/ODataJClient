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
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * DOM tree utilities class.
 */
final class DOMTreeUtils {

    private DOMTreeUtils() {
        // Empty private constructor for static utility classes
    }

    private static boolean isGeospatial(final String type) {
        return type.startsWith(EdmSimpleType.namespace() + ".Geo");
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
    public static void buildSubtree(final Element parent, final JsonNode node) {
        final Iterator<String> fieldNameItor = node.fieldNames();
        final Iterator<JsonNode> nodeItor = node.elements();
        while (nodeItor.hasNext()) {
            final JsonNode child = nodeItor.next();
            final String name = fieldNameItor.hasNext() ? fieldNameItor.next() : "";

            // no name? array item
            if (name.isEmpty()) {
                final Element element = parent.getOwnerDocument().createElementNS(ODataConstants.NS_DATASERVICES,
                        ODataConstants.PREFIX_DATASERVICES + ODataConstants.ELEM_ELEMENT);
                parent.appendChild(element);

                if (child.isValueNode()) {
                    if (child.isNull()) {
                        element.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_NULL,
                                Boolean.toString(true));
                    } else {
                        element.appendChild(parent.getOwnerDocument().createTextNode(child.asText()));
                    }
                }

                if (child.isContainerNode()) {
                    buildSubtree(element, child);
                }
            } else if (!name.contains("@") && !ODataConstants.JSON_TYPE.equals(name)) {
                final Element property = parent.getOwnerDocument().createElementNS(
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
                    if (!typeSet) {
                        if (child.isShort()) {
                            property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                                    EdmSimpleType.INT_16.toString());
                        }
                        if (child.isInt()) {
                            property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                                    EdmSimpleType.INT_32.toString());
                        }
                        if (child.isLong()) {
                            property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                                    EdmSimpleType.INT_64.toString());
                        }
                        if (child.isBigDecimal()) {
                            property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                                    EdmSimpleType.DECIMAL.toString());
                        }
                        if (child.isFloat()) {
                            property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                                    EdmSimpleType.SINGLE.toString());
                        }                        
                        if (child.isDouble()) {
                            property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                                    EdmSimpleType.DOUBLE.toString());
                        }  
                        if (child.isBoolean()) {
                            property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                                    EdmSimpleType.BOOLEAN.toString());
                        }
                        if (child.isTextual()) {
                            property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                                    EdmSimpleType.STRING.toString());
                        }
                    }

                    property.appendChild(parent.getOwnerDocument().createTextNode(child.asText()));
                } else if (child.isContainerNode()) {
                    if (!typeSet && child.hasNonNull(ODataConstants.JSON_TYPE)) {
                        property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                                child.get(ODataConstants.JSON_TYPE).textValue());
                    }

                    final String type = property.getAttribute(ODataConstants.ATTR_TYPE);
                    if (StringUtils.isNotBlank(type) && isGeospatial(type)) {
                        if (EdmSimpleType.GEOGRAPHY.toString().equals(type)
                                || EdmSimpleType.GEOMETRY.toString().equals(type)) {

                            final String geoType = child.get(ODataConstants.TYPE).textValue();
                            property.setAttributeNS(ODataConstants.NS_METADATA, ODataConstants.ATTR_TYPE,
                                    geoType.startsWith("Geo")
                                    ? EdmSimpleType.namespace() + "." + geoType
                                    : type + geoType);
                        }

                        if (child.has(ODataConstants.JSON_COORDINATES) || child.has(ODataConstants.JSON_GEOMETRIES)) {
                            GeospatialUtils.deserialize(
                                    child, property, property.getAttribute(ODataConstants.ATTR_TYPE));
                        }
                    } else {
                        buildSubtree(property, child);
                    }
                }
            }
        }
    }

    /**
     * Serializes DOM content as JSON.
     *
     * @param jgen JSON generator.
     * @param content content.
     * @throws IOException in case of write error.
     */
    public static void writeContent(final JsonGenerator jgen, final Node content) throws IOException {
        for (Node child : XMLUtils.getChildNodes(content, Node.ELEMENT_NODE)) {
            final String childName = XMLUtils.getSimpleName(child);

            final Node typeAttr = child.getAttributes().getNamedItem(ODataConstants.ATTR_TYPE);
            if (typeAttr != null && isGeospatial(typeAttr.getTextContent())) {
                jgen.writeObjectFieldStart(childName);

                GeospatialUtils.serialize(jgen, (Element) child, typeAttr.getTextContent());

                jgen.writeEndObject();
            } else if (XMLUtils.hasOnlyTextChildNodes(child)) {
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
                if (XMLUtils.hasElementsChildNode(child)) {
                    jgen.writeArrayFieldStart(childName);

                    for (Node nephew : XMLUtils.getChildNodes(child, Node.ELEMENT_NODE)) {
                        if (XMLUtils.hasOnlyTextChildNodes(nephew)) {
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
