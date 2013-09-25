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
package com.msopentech.odatajclient.engine.data.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Geospatial;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import com.msopentech.odatajclient.engine.utils.XMLUtils;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

final class GeospatialJSONHandler {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(GeospatialJSONHandler.class);

    private GeospatialJSONHandler() {
        // Empty private constructor for static utility classes
    }

    private static Element deserializePoint(final Document document, final Iterator<JsonNode> itor) {
        final Element point = document.createElementNS(ODataConstants.NS_GML, ODataConstants.ELEM_POINT);

        final Element ppos = document.createElementNS(ODataConstants.NS_GML, ODataConstants.ELEM_POS);
        point.appendChild(ppos);
        if (itor.hasNext()) {
            ppos.appendChild(document.createTextNode(itor.next().asText() + " " + itor.next().asText()));
        }

        return point;
    }

    private static void appendPoses(final Element parent, final Document document, final Iterator<JsonNode> itor) {
        while (itor.hasNext()) {
            final Iterator<JsonNode> lineItor = itor.next().elements();
            final Element pos = document.createElementNS(ODataConstants.NS_GML, ODataConstants.ELEM_POS);
            parent.appendChild(pos);
            pos.appendChild(document.createTextNode(lineItor.next().asText() + " " + lineItor.next().asText()));
        }
    }

    private static Element deserializeLineString(final Document document, final Iterator<JsonNode> itor) {
        final Element lineString = document.createElementNS(ODataConstants.NS_GML, ODataConstants.ELEM_LINESTRING);
        if (!itor.hasNext()) {
            lineString.appendChild(document.createElementNS(ODataConstants.NS_GML, ODataConstants.ELEM_POSLIST));
        }

        appendPoses(lineString, document, itor);

        return lineString;
    }

    private static Element deserializePolygon(final Document document, final Iterator<JsonNode> itor) {
        final Element polygon = document.createElementNS(ODataConstants.NS_GML, ODataConstants.ELEM_POLYGON);

        if (itor.hasNext()) {
            final Iterator<JsonNode> extItor = itor.next().elements();
            final Element exterior = document.createElementNS(
                    ODataConstants.NS_GML, ODataConstants.ELEM_POLYGON_EXTERIOR);
            polygon.appendChild(exterior);
            final Element extLR = document.createElementNS(
                    ODataConstants.NS_GML, ODataConstants.ELEM_POLYGON_LINEARRING);
            exterior.appendChild(extLR);

            appendPoses(extLR, document, extItor);
        }

        if (itor.hasNext()) {
            final Iterator<JsonNode> intItor = itor.next().elements();
            final Element interior = document.createElementNS(
                    ODataConstants.NS_GML, ODataConstants.ELEM_POLYGON_INTERIOR);
            polygon.appendChild(interior);
            final Element intLR = document.createElementNS(
                    ODataConstants.NS_GML, ODataConstants.ELEM_POLYGON_LINEARRING);
            interior.appendChild(intLR);

            appendPoses(intLR, document, intItor);
        }

        return polygon;
    }

    public static void deserialize(final JsonNode node, final Element parent, final String type) {
        final Iterator<JsonNode> cooItor = node.has(ODataConstants.JSON_COORDINATES)
                ? node.get(ODataConstants.JSON_COORDINATES).elements()
                : Collections.<JsonNode>emptyList().iterator();

        Element root = null;
        final EdmSimpleType edmSimpleType = EdmSimpleType.fromValue(type);
        switch (edmSimpleType) {
            case GEOGRAPHY_POINT:
            case GEOMETRY_POINT:
                root = deserializePoint(parent.getOwnerDocument(), cooItor);
                break;

            case GEOGRAPHY_MULTI_POINT:
            case GEOMETRY_MULTI_POINT:
                root = parent.getOwnerDocument().createElementNS(ODataConstants.NS_GML, ODataConstants.ELEM_MULTIPOINT);
                if (cooItor.hasNext()) {
                    final Element pointMembers = parent.getOwnerDocument().createElementNS(
                            ODataConstants.NS_GML, ODataConstants.ELEM_POINTMEMBERS);
                    root.appendChild(pointMembers);
                    while (cooItor.hasNext()) {
                        final Iterator<JsonNode> mpItor = cooItor.next().elements();
                        pointMembers.appendChild(deserializePoint(parent.getOwnerDocument(), mpItor));
                    }
                }
                break;

            case GEOGRAPHY_LINE_STRING:
            case GEOMETRY_LINE_STRING:
                root = deserializeLineString(parent.getOwnerDocument(), cooItor);
                break;

            case GEOGRAPHY_MULTI_LINE_STRING:
            case GEOMETRY_MULTI_LINE_STRING:
                root = parent.getOwnerDocument().createElementNS(
                        ODataConstants.NS_GML, ODataConstants.ELEM_MULTILINESTRING);
                if (cooItor.hasNext()) {
                    final Element lineStringMembers = parent.getOwnerDocument().createElementNS(
                            ODataConstants.NS_GML, ODataConstants.ELEM_LINESTRINGMEMBERS);
                    root.appendChild(lineStringMembers);
                    while (cooItor.hasNext()) {
                        final Iterator<JsonNode> mlsItor = cooItor.next().elements();
                        lineStringMembers.appendChild(deserializeLineString(parent.getOwnerDocument(), mlsItor));
                    }
                }
                break;

            case GEOGRAPHY_POLYGON:
            case GEOMETRY_POLYGON:
                root = deserializePolygon(parent.getOwnerDocument(), cooItor);
                break;

            case GEOGRAPHY_MULTI_POLYGON:
            case GEOMETRY_MULTI_POLYGON:
                root = parent.getOwnerDocument().createElementNS(
                        ODataConstants.NS_GML, ODataConstants.ELEM_MULTIPOLYGON);
                if (cooItor.hasNext()) {
                    final Element surfaceMembers = parent.getOwnerDocument().createElementNS(
                            ODataConstants.NS_GML, ODataConstants.ELEM_SURFACEMEMBERS);
                    root.appendChild(surfaceMembers);
                    while (cooItor.hasNext()) {
                        final Iterator<JsonNode> mpItor = cooItor.next().elements();
                        surfaceMembers.appendChild(deserializePolygon(parent.getOwnerDocument(), mpItor));
                    }
                }
                break;

            case GEOGRAPHY_COLLECTION:
            case GEOMETRY_COLLECTION:
                root = parent.getOwnerDocument().createElementNS(
                        ODataConstants.NS_GML, ODataConstants.ELEM_GEOCOLLECTION);
                if (node.has(ODataConstants.JSON_GEOMETRIES)) {
                    final Iterator<JsonNode> geoItor = node.get(ODataConstants.JSON_GEOMETRIES).elements();
                    if (geoItor.hasNext()) {
                        final Element geometryMembers = parent.getOwnerDocument().createElementNS(
                                ODataConstants.NS_GML, ODataConstants.ELEM_GEOMEMBERS);
                        root.appendChild(geometryMembers);

                        while (geoItor.hasNext()) {
                            final JsonNode geo = geoItor.next();
                            final String collItemType = geo.get(ODataConstants.ATTR_TYPE).asText();
                            final String callAsType;
                            if (EdmSimpleType.GEOGRAPHY_COLLECTION.value().equals(collItemType)
                                    || EdmSimpleType.GEOMETRY_COLLECTION.value().equals(collItemType)) {

                                callAsType = collItemType;
                            } else {
                                callAsType =
                                        (edmSimpleType == EdmSimpleType.GEOGRAPHY_COLLECTION ? "Geography" : "Geometry")
                                        + collItemType;
                            }

                            deserialize(geo, geometryMembers, EdmSimpleType.namespace() + "." + callAsType);
                        }
                    }
                }
                break;

            default:
        }

        if (root != null) {
            parent.appendChild(root);
            if (node.has(ODataConstants.JSON_CRS)) {
                root.setAttribute(ODataConstants.ATTR_SRSNAME,
                        ODataConstants.JSON_GIS_URLPREFIX
                        + node.get(ODataConstants.JSON_CRS).get(ODataConstants.PROPERTIES).get(ODataConstants.NAME).
                        asText().split(":")[1]);
            }
        }
    }

    private static void serializeCrs(final JsonGenerator jgen, final Node node) throws IOException {
        if (node.getAttributes().getNamedItem(ODataConstants.ATTR_SRSNAME) != null) {
            final String srsName = node.getAttributes().getNamedItem(ODataConstants.ATTR_SRSNAME).getTextContent();
            final int prefIdx = srsName.indexOf(ODataConstants.JSON_GIS_URLPREFIX);
            final String crsValue = srsName.substring(prefIdx + ODataConstants.JSON_GIS_URLPREFIX.length());

            jgen.writeObjectFieldStart(ODataConstants.JSON_CRS);
            jgen.writeStringField(ODataConstants.ATTR_TYPE, ODataConstants.NAME);
            jgen.writeObjectFieldStart(ODataConstants.PROPERTIES);
            jgen.writeStringField(ODataConstants.NAME, "EPSG:" + crsValue);
            jgen.writeEndObject();
            jgen.writeEndObject();
        }
    }

    private static void serializePoint(final JsonGenerator jgen, final Node node) throws IOException {
        for (String coord : node.getTextContent().split(" ")) {
            jgen.writeNumber(coord);
        }
    }

    private static void serializeLineString(final JsonGenerator jgen, final Element node) throws IOException {
        for (Element element : XMLUtils.getChildElements(node, ODataConstants.ELEM_POS)) {
            jgen.writeStartArray();
            serializePoint(jgen, element);
            jgen.writeEndArray();
        }
    }

    private static void serializePolygon(final JsonGenerator jgen, final Element node) throws IOException {
        for (Element exterior : XMLUtils.getChildElements(node, ODataConstants.ELEM_POLYGON_EXTERIOR)) {
            jgen.writeStartArray();
            serializeLineString(jgen,
                    XMLUtils.getChildElements(exterior, ODataConstants.ELEM_POLYGON_LINEARRING).get(0));
            jgen.writeEndArray();

        }
        for (Element interior : XMLUtils.getChildElements(node, ODataConstants.ELEM_POLYGON_INTERIOR)) {
            jgen.writeStartArray();
            serializeLineString(jgen,
                    XMLUtils.getChildElements(interior, ODataConstants.ELEM_POLYGON_LINEARRING).get(0));
            jgen.writeEndArray();

        }
    }

    public static void serialize(final JsonGenerator jgen, final Element node, final String type) throws IOException {
        final EdmSimpleType edmSimpleType = EdmSimpleType.fromValue(type);

        if (edmSimpleType.equals(EdmSimpleType.GEOGRAPHY_COLLECTION)
                || edmSimpleType.equals(EdmSimpleType.GEOMETRY_COLLECTION)) {

            jgen.writeStringField(ODataConstants.ATTR_TYPE, EdmSimpleType.GEOMETRY_COLLECTION.value());
        } else {
            final int yIdx = edmSimpleType.value().indexOf('y');
            final String itemType = edmSimpleType.value().substring(yIdx + 1);
            jgen.writeStringField(ODataConstants.ATTR_TYPE, itemType);
        }

        Element root = null;
        switch (edmSimpleType) {
            case GEOGRAPHY_POINT:
            case GEOMETRY_POINT:
                root = XMLUtils.getChildElements(node, ODataConstants.ELEM_POINT).get(0);

                jgen.writeArrayFieldStart(ODataConstants.JSON_COORDINATES);
                serializePoint(jgen, XMLUtils.getChildElements(root, ODataConstants.ELEM_POS).get(0));
                jgen.writeEndArray();
                break;

            case GEOGRAPHY_MULTI_POINT:
            case GEOMETRY_MULTI_POINT:
                root = XMLUtils.getChildElements(node, ODataConstants.ELEM_MULTIPOINT).get(0);

                jgen.writeArrayFieldStart(ODataConstants.JSON_COORDINATES);

                final List<Element> pMembs = XMLUtils.getChildElements(root, ODataConstants.ELEM_POINTMEMBERS);
                if (pMembs != null && !pMembs.isEmpty()) {
                    for (Element point : XMLUtils.getChildElements(pMembs.get(0), ODataConstants.ELEM_POINT)) {
                        jgen.writeStartArray();
                        serializePoint(jgen, XMLUtils.getChildElements(point, ODataConstants.ELEM_POS).get(0));
                        jgen.writeEndArray();
                    }
                }

                jgen.writeEndArray();
                break;

            case GEOGRAPHY_LINE_STRING:
            case GEOMETRY_LINE_STRING:
                root = XMLUtils.getChildElements(node, ODataConstants.ELEM_LINESTRING).get(0);

                jgen.writeArrayFieldStart(ODataConstants.JSON_COORDINATES);
                serializeLineString(jgen, root);
                jgen.writeEndArray();
                break;

            case GEOGRAPHY_MULTI_LINE_STRING:
            case GEOMETRY_MULTI_LINE_STRING:
                root = XMLUtils.getChildElements(node, ODataConstants.ELEM_MULTILINESTRING).get(0);

                jgen.writeArrayFieldStart(ODataConstants.JSON_COORDINATES);

                final List<Element> lMembs = XMLUtils.getChildElements(root, ODataConstants.ELEM_LINESTRINGMEMBERS);
                if (lMembs != null && !lMembs.isEmpty()) {
                    for (Element lineStr : XMLUtils.getChildElements(lMembs.get(0), ODataConstants.ELEM_LINESTRING)) {
                        jgen.writeStartArray();
                        serializeLineString(jgen, lineStr);
                        jgen.writeEndArray();
                    }
                }

                jgen.writeEndArray();
                break;

            case GEOGRAPHY_POLYGON:
            case GEOMETRY_POLYGON:
                root = XMLUtils.getChildElements(node, ODataConstants.ELEM_POLYGON).get(0);

                jgen.writeArrayFieldStart(ODataConstants.JSON_COORDINATES);
                serializePolygon(jgen, root);
                jgen.writeEndArray();
                break;

            case GEOGRAPHY_MULTI_POLYGON:
            case GEOMETRY_MULTI_POLYGON:
                root = XMLUtils.getChildElements(node, ODataConstants.ELEM_MULTIPOLYGON).get(0);

                jgen.writeArrayFieldStart(ODataConstants.JSON_COORDINATES);

                final List<Element> mpMembs = XMLUtils.getChildElements(root, ODataConstants.ELEM_SURFACEMEMBERS);
                if (mpMembs != null & !mpMembs.isEmpty()) {
                    for (Element pol : XMLUtils.getChildElements(mpMembs.get(0), ODataConstants.ELEM_POLYGON)) {
                        jgen.writeStartArray();
                        serializePolygon(jgen, pol);
                        jgen.writeEndArray();
                    }
                }

                jgen.writeEndArray();
                break;

            case GEOGRAPHY_COLLECTION:
            case GEOMETRY_COLLECTION:
                root = XMLUtils.getChildElements(node, ODataConstants.ELEM_GEOCOLLECTION).get(0);

                final List<Element> cMembs = XMLUtils.getChildElements(root, ODataConstants.ELEM_GEOMEMBERS);
                if (cMembs != null && !cMembs.isEmpty()) {
                    jgen.writeArrayFieldStart(ODataConstants.JSON_GEOMETRIES);

                    for (Node geom : XMLUtils.getChildNodes(cMembs.get(0), Node.ELEMENT_NODE)) {
                        try {
                            final DocumentBuilder builder = ODataConstants.DOC_BUILDER_FACTORY.newDocumentBuilder();
                            final Document doc = builder.newDocument();

                            final Element fakeParent = doc.createElementNS(
                                    ODataConstants.NS_DATASERVICES, ODataConstants.PREFIX_DATASERVICES + "fake");
                            fakeParent.appendChild(doc.importNode(geom, true));

                            final EdmSimpleType callAsType = XMLUtils.simpleTypeForNode(
                                    edmSimpleType == EdmSimpleType.GEOGRAPHY_COLLECTION
                                    ? Geospatial.Dimension.GEOGRAPHY : Geospatial.Dimension.GEOMETRY,
                                    geom);

                            jgen.writeStartObject();
                            serialize(jgen, fakeParent, callAsType.toString());
                            jgen.writeEndObject();
                        } catch (Exception e) {
                            LOG.warn("While serializing {}", XMLUtils.getSimpleName(geom), e);
                        }
                    }

                    jgen.writeEndArray();
                }
                break;

            default:
        }

        if (root != null) {
            serializeCrs(jgen, root);
        }
    }
}
