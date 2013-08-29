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
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Geospatial;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import com.msopentech.odatajclient.engine.utils.XMLUtils;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

final class GeospatialJSONHandler {

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
        final NodeList poses = node.getElementsByTagName(ODataConstants.ELEM_POS);
        for (int i = 0; i < poses.getLength(); i++) {
            jgen.writeStartArray();
            serializePoint(jgen, poses.item(i));
            jgen.writeEndArray();
        }
    }

    private static void serializePolygon(final JsonGenerator jgen, final Element node) throws IOException {
        final NodeList exts = node.getElementsByTagName(ODataConstants.ELEM_POLYGON_EXTERIOR);
        if (exts.getLength() > 0) {
            final Element exterior = (Element) exts.item(0);

            jgen.writeStartArray();
            serializeLineString(jgen,
                    (Element) exterior.getElementsByTagName(ODataConstants.ELEM_POLYGON_LINEARRING).item(0));
            jgen.writeEndArray();

        }
        final NodeList ints = node.getElementsByTagName(ODataConstants.ELEM_POLYGON_INTERIOR);
        if (ints.getLength() > 0) {
            final Element interior = (Element) ints.item(0);

            jgen.writeStartArray();
            serializeLineString(jgen,
                    (Element) interior.getElementsByTagName(ODataConstants.ELEM_POLYGON_LINEARRING).item(0));
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
                root = (Element) node.getElementsByTagName(ODataConstants.ELEM_POINT).item(0);

                jgen.writeArrayFieldStart(ODataConstants.JSON_COORDINATES);
                serializePoint(jgen, node.getElementsByTagName(ODataConstants.ELEM_POS).item(0));
                jgen.writeEndArray();
                break;

            case GEOGRAPHY_MULTI_POINT:
            case GEOMETRY_MULTI_POINT:
                root = (Element) node.getElementsByTagName(ODataConstants.ELEM_MULTIPOINT).item(0);

                jgen.writeArrayFieldStart(ODataConstants.JSON_COORDINATES);

                final Element pMembs =
                        (Element) root.getElementsByTagName(ODataConstants.ELEM_POINTMEMBERS).item(0);
                if (pMembs != null) {
                    final NodeList points = pMembs.getElementsByTagName(ODataConstants.ELEM_POINT);
                    for (int i = 0; i < points.getLength(); i++) {
                        jgen.writeStartArray();
                        serializePoint(jgen,
                                ((Element) points.item(i)).getElementsByTagName(ODataConstants.ELEM_POS).item(0));
                        jgen.writeEndArray();
                    }
                }

                jgen.writeEndArray();
                break;

            case GEOGRAPHY_LINE_STRING:
            case GEOMETRY_LINE_STRING:
                root = (Element) node.getElementsByTagName(ODataConstants.ELEM_LINESTRING).item(0);

                jgen.writeArrayFieldStart(ODataConstants.JSON_COORDINATES);
                serializeLineString(jgen, root);
                jgen.writeEndArray();
                break;

            case GEOGRAPHY_MULTI_LINE_STRING:
            case GEOMETRY_MULTI_LINE_STRING:
                root = (Element) node.getElementsByTagName(ODataConstants.ELEM_MULTILINESTRING).item(0);

                jgen.writeArrayFieldStart(ODataConstants.JSON_COORDINATES);

                final Element lMembs =
                        (Element) root.getElementsByTagName(ODataConstants.ELEM_LINESTRINGMEMBERS).item(0);
                if (lMembs != null) {
                    final NodeList lineStrs = lMembs.getElementsByTagName(ODataConstants.ELEM_LINESTRING);
                    for (int i = 0; i < lineStrs.getLength(); i++) {
                        jgen.writeStartArray();
                        serializeLineString(jgen, (Element) lineStrs.item(i));
                        jgen.writeEndArray();
                    }
                }

                jgen.writeEndArray();
                break;

            case GEOGRAPHY_POLYGON:
            case GEOMETRY_POLYGON:
                root = (Element) node.getElementsByTagName(ODataConstants.ELEM_POLYGON).item(0);

                jgen.writeArrayFieldStart(ODataConstants.JSON_COORDINATES);
                serializePolygon(jgen, root);
                jgen.writeEndArray();
                break;

            case GEOGRAPHY_MULTI_POLYGON:
            case GEOMETRY_MULTI_POLYGON:
                root = (Element) node.getElementsByTagName(ODataConstants.ELEM_MULTIPOLYGON).item(0);

                jgen.writeArrayFieldStart(ODataConstants.JSON_COORDINATES);

                final Element mpMembs =
                        (Element) root.getElementsByTagName(ODataConstants.ELEM_SURFACEMEMBERS).item(0);
                if (mpMembs != null) {
                    final NodeList pols = mpMembs.getElementsByTagName(ODataConstants.ELEM_POLYGON);
                    for (int i = 0; i < pols.getLength(); i++) {
                        jgen.writeStartArray();
                        serializePolygon(jgen, (Element) pols.item(i));
                        jgen.writeEndArray();
                    }
                }

                jgen.writeEndArray();
                break;

            case GEOGRAPHY_COLLECTION:
            case GEOMETRY_COLLECTION:
                root = (Element) node.getElementsByTagName(ODataConstants.ELEM_GEOCOLLECTION).item(0);

                final Node cMembs = root.getElementsByTagName(ODataConstants.ELEM_GEOMEMBERS).item(0);
                if (cMembs != null) {
                    jgen.writeArrayFieldStart(ODataConstants.JSON_GEOMETRIES);

                    for (Node geom : XMLUtils.getChildNodes(cMembs, Node.ELEMENT_NODE)) {
                        final Element fakeParent = node.getOwnerDocument().createElementNS(
                                ODataConstants.NS_DATASERVICES, ODataConstants.PREFIX_DATASERVICES + "fake");
                        fakeParent.appendChild(geom);

                        final EdmSimpleType callAsType = XMLUtils.simpleTypeForNode(
                                edmSimpleType == EdmSimpleType.GEOGRAPHY_COLLECTION
                                ? Geospatial.Dimension.GEOGRAPHY : Geospatial.Dimension.GEOMETRY,
                                geom);

                        jgen.writeStartObject();
                        serialize(jgen, fakeParent, callAsType.toString());
                        jgen.writeEndObject();
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
