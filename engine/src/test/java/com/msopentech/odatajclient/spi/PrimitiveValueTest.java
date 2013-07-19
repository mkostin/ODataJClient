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
package com.msopentech.odatajclient.spi;

import static com.msopentech.odatajclient.spi.AbstractTest.testODataServiceRootURL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntitySetRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataPropertyRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Geospatial;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.GeospatialCollection;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.LineString;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiLineString;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiPoint;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiPolygon;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Point;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Polygon;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class PrimitiveValueTest extends AbstractTest {

    private ODataPrimitiveValue readPropertyValue(final String serviceRootURL,
            final String entity, final String propertyName) {

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(serviceRootURL);
        uriBuilder.appendEntityTypeSegment(entity).appendStructuralSegment(propertyName);

        final ODataPropertyRequest req = ODataRetrieveRequestFactory.getPropertyRequest(uriBuilder.build());
        req.setFormat(ODataFormat.XML);

        final ODataRetrieveResponse<ODataProperty> res = req.execute();
        assertEquals(200, res.getStatusCode());

        final ODataProperty property = res.getBody();
        assertNotNull(property);
        assertTrue(property.hasPrimitiveValue());

        assertNotNull(property.getPrimitiveValue());
        return property.getPrimitiveValue();
    }

    @Test
    public void string() {
        final ODataPrimitiveValue opv = readPropertyValue(testODataServiceRootURL, TEST_CUSTOMER, "CustomerId");
        assertEquals(EdmSimpleType.INT_32.toString(), opv.getTypeName());

        final Integer value = opv.<Integer>toCastValue();
        assertNotNull(value);
        assertTrue(-10 == value);
    }

    @Test
    public void int32() {
        final ODataPrimitiveValue opv = readPropertyValue(testODataServiceRootURL, "Product(-9)", "Description");
        assertEquals(EdmSimpleType.STRING.toString(), opv.getTypeName());

        final String value = opv.<String>toCastValue();
        assertNotNull(value);
        assertEquals("kdcuklu", value);
    }

    @Test
    public void decimal() {
        final ODataPrimitiveValue opv = readPropertyValue(testODataServiceRootURL, TEST_PRODUCT, "Dimensions/Width");
        assertEquals(EdmSimpleType.DECIMAL.toString(), opv.getTypeName());

        final BigDecimal value = opv.<BigDecimal>toCastValue();
        assertNotNull(value);
        assertTrue(new BigDecimal("-79228162514264337593543950335").equals(value));
    }

    @Test
    public void datetime() {
        final ODataPrimitiveValue opv = readPropertyValue(testODataServiceRootURL,
                TEST_PRODUCT, "ComplexConcurrency/QueriedDateTime");
        assertEquals(EdmSimpleType.DATE_TIME.toString(), opv.getTypeName());

        final Timestamp value = opv.<Timestamp>toCastValue();
        assertNotNull(value);
        assertEquals("2013-01-10T06:27:51.1667673", opv.toString());
    }

    @Test
    public void guid() {
        final ODataPrimitiveValue opv = readPropertyValue(testODataServiceRootURL,
                "MessageAttachment(guid'1126a28b-a4af-4bbd-bf0a-2b2c22635565')", "AttachmentId");
        assertEquals(EdmSimpleType.GUID.toString(), opv.getTypeName());

        final UUID value = opv.<UUID>toCastValue();
        assertNotNull(value);
        assertEquals("1126a28b-a4af-4bbd-bf0a-2b2c22635565", opv.toString());
    }

    @Test
    public void binary() {
        final ODataPrimitiveValue opv = readPropertyValue(testODataServiceRootURL,
                "MessageAttachment(guid'1126a28b-a4af-4bbd-bf0a-2b2c22635565')", "Attachment");
        assertEquals(EdmSimpleType.BINARY.toString(), opv.getTypeName());

        final byte[] value = opv.<byte[]>toCastValue();
        assertNotNull(value);
        assertTrue(value.length > 0);
        assertTrue(Base64.isBase64(opv.toString()));
    }

    @Test
    public void geographyPoint() {
        final ODataPrimitiveValue opv =
                readPropertyValue(testODataServiceRootURL, "AllGeoTypesSet(-10)", "GeogPoint");
        assertEquals(EdmSimpleType.GEOGRAPHY_POINT.toString(), opv.getTypeName());

        final Point point = opv.<Point>toCastValue();
        assertNotNull(point);
        assertEquals(Geospatial.Dimension.GEOGRAPHY, point.getDimension());

        assertEquals(52.8606, point.getX(), 0);
        assertEquals(173.334, point.getY(), 0);
    }

    @Test
    public void geometryPoint() {
        final ODataPrimitiveValue opv =
                readPropertyValue(testODataServiceRootURL, "AllGeoTypesSet(-9)", "GeomPoint");
        assertEquals(EdmSimpleType.GEOMETRY_POINT.toString(), opv.getTypeName());

        final Point point = opv.<Point>toCastValue();
        assertNotNull(point);
        assertEquals(Geospatial.Dimension.GEOMETRY, point.getDimension());

        assertEquals(4369367.0586663447, point.getX(), 0);
        assertEquals(6352015.6916818349, point.getY(), 0);
    }

    @Test
    public void geographyLineString() {
        final ODataPrimitiveValue opv =
                readPropertyValue(testODataServiceRootURL, "AllGeoTypesSet(-10)", "GeogLine");
        assertEquals(EdmSimpleType.GEOGRAPHY_LINE_STRING.toString(), opv.getTypeName());

        final LineString lineString = opv.<LineString>toCastValue();
        assertNotNull(lineString);
        assertEquals(Geospatial.Dimension.GEOGRAPHY, lineString.getDimension());

        final List<Point> points = new ArrayList<Point>();

        for (Point point : lineString) {
            points.add(point);
        }

        assertEquals(4, points.size());

        assertEquals(40.5, points.get(0).getX(), 0);
        assertEquals(40.5, points.get(0).getY(), 0);

        assertEquals(30.5, points.get(1).getX(), 0);
        assertEquals(30.5, points.get(1).getY(), 0);

        assertEquals(20.5, points.get(2).getX(), 0);
        assertEquals(40.5, points.get(2).getY(), 0);

        assertEquals(10.5, points.get(3).getX(), 0);
        assertEquals(30.5, points.get(3).getY(), 0);
    }

    @Test
    public void geometryLineString() {
        final ODataPrimitiveValue opv =
                readPropertyValue(testODataServiceRootURL, "AllGeoTypesSet(-10)", "Geom");
        assertEquals(EdmSimpleType.GEOMETRY_LINE_STRING.toString(), opv.getTypeName());

        final LineString lineString = opv.<LineString>toCastValue();
        assertNotNull(lineString);
        assertEquals(Geospatial.Dimension.GEOMETRY, lineString.getDimension());

        final List<Point> points = new ArrayList<Point>();

        for (Point point : lineString) {
            points.add(point);
        }

        assertEquals(4, points.size());

        assertEquals(1, points.get(0).getX(), 0);
        assertEquals(1, points.get(0).getY(), 0);

        assertEquals(3, points.get(1).getX(), 0);
        assertEquals(3, points.get(1).getY(), 0);

        assertEquals(2, points.get(2).getX(), 0);
        assertEquals(4, points.get(2).getY(), 0);

        assertEquals(2, points.get(3).getX(), 0);
        assertEquals(0, points.get(3).getY(), 0);
    }

    @Test
    public void geometryMultiPoint() {
        final ODataPrimitiveValue opv =
                readPropertyValue(testODataServiceRootURL, "AllGeoTypesSet(-7)", "GeomMultiPoint");
        assertEquals(EdmSimpleType.GEOMETRY_MULTI_POINT.toString(), opv.getTypeName());

        final MultiPoint multiPoint = opv.<MultiPoint>toCastValue();
        assertNotNull(multiPoint);
        assertEquals(Geospatial.Dimension.GEOMETRY, multiPoint.getDimension());

        final List<Point> points = new ArrayList<Point>();

        for (Point point : multiPoint) {
            points.add(point);
        }

        assertEquals(1, points.size());

        assertEquals(0, points.get(0).getX(), 0);
        assertEquals(0, points.get(0).getY(), 0);
    }

    @Test
    public void geographyMultiPoint() {
        final ODataPrimitiveValue opv =
                readPropertyValue(testODataServiceRootURL, "AllGeoTypesSet(-7)", "GeogMultiPoint");
        assertEquals(EdmSimpleType.GEOGRAPHY_MULTI_POINT.toString(), opv.getTypeName());

        final MultiPoint multiPoint = opv.<MultiPoint>toCastValue();
        assertNotNull(multiPoint);
        assertEquals(Geospatial.Dimension.GEOGRAPHY, multiPoint.getDimension());

        final List<Point> points = new ArrayList<Point>();

        for (Point point : multiPoint) {
            points.add(point);
        }

        assertEquals(1, points.size());

        assertEquals(47.38, points.get(0).getX(), 0);
        assertEquals(-122.7, points.get(0).getY(), 0);
    }

    @Test
    public void geometryMultiLine() {
        final ODataPrimitiveValue opv =
                readPropertyValue(testODataServiceRootURL, "AllGeoTypesSet(-6)", "GeomMultiLine");
        assertEquals(EdmSimpleType.GEOMETRY_MULTI_LINE_STRING.toString(), opv.getTypeName());

        final MultiLineString multiLine = opv.<MultiLineString>toCastValue();
        assertNotNull(multiLine);
        assertEquals(Geospatial.Dimension.GEOMETRY, multiLine.getDimension());

        final List<LineString> lines = new ArrayList<LineString>();

        for (LineString point : multiLine) {
            lines.add(point);
        }

        assertEquals(2, lines.size());

        final List<Point> points = new ArrayList<Point>();
        for (Point point : lines.get(0)) {
            points.add(point);
        }

        assertEquals(3, points.size());
        assertEquals(10, points.get(0).getX(), 0);
        assertEquals(10, points.get(0).getY(), 0);
        assertEquals(20, points.get(1).getX(), 0);
        assertEquals(20, points.get(1).getY(), 0);
        assertEquals(10, points.get(2).getX(), 0);
        assertEquals(40, points.get(2).getY(), 0);

        points.clear();

        for (Point point : lines.get(1)) {
            points.add(point);
        }

        assertEquals(4, points.size());
        assertEquals(40, points.get(0).getX(), 0);
        assertEquals(40, points.get(0).getY(), 0);
        assertEquals(30, points.get(1).getX(), 0);
        assertEquals(30, points.get(1).getY(), 0);
        assertEquals(40, points.get(2).getX(), 0);
        assertEquals(20, points.get(2).getY(), 0);
        assertEquals(30, points.get(3).getX(), 0);
        assertEquals(10, points.get(3).getY(), 0);
    }

    @Test
    public void geographyMultiLine() {
        final ODataPrimitiveValue opv =
                readPropertyValue(testODataServiceRootURL, "AllGeoTypesSet(-7)", "GeogMultiLine");
        assertEquals(EdmSimpleType.GEOGRAPHY_MULTI_LINE_STRING.toString(), opv.getTypeName());

        final MultiLineString multiLine = opv.<MultiLineString>toCastValue();
        assertNotNull(multiLine);
        assertEquals(Geospatial.Dimension.GEOGRAPHY, multiLine.getDimension());

        final List<LineString> lines = new ArrayList<LineString>();

        for (LineString point : multiLine) {
            lines.add(point);
        }

        assertEquals(2, lines.size());

        final List<Point> points = new ArrayList<Point>();
        for (Point point : lines.get(0)) {
            points.add(point);
        }

        assertEquals(3, points.size());
        assertEquals(10, points.get(0).getX(), 0);
        assertEquals(10, points.get(0).getY(), 0);
        assertEquals(20, points.get(1).getX(), 0);
        assertEquals(20, points.get(1).getY(), 0);
        assertEquals(40, points.get(2).getX(), 0);
        assertEquals(10, points.get(2).getY(), 0);

        points.clear();

        for (Point point : lines.get(1)) {
            points.add(point);
        }

        assertEquals(4, points.size());
        assertEquals(40, points.get(0).getX(), 0);
        assertEquals(40, points.get(0).getY(), 0);
        assertEquals(30, points.get(1).getX(), 0);
        assertEquals(30, points.get(1).getY(), 0);
        assertEquals(20, points.get(2).getX(), 0);
        assertEquals(40, points.get(2).getY(), 0);
        assertEquals(10, points.get(3).getX(), 0);
        assertEquals(30, points.get(3).getY(), 0);
    }

    @Test
    public void geometryPolygon() {
        final ODataPrimitiveValue opv =
                readPropertyValue(testODataServiceRootURL, "AllGeoTypesSet(-6)", "GeomPolygon");
        assertEquals(EdmSimpleType.GEOMETRY_POLYGON.toString(), opv.getTypeName());

        final Polygon polygon = opv.<Polygon>toCastValue();
        assertNotNull(polygon);
        assertEquals(Geospatial.Dimension.GEOMETRY, polygon.getDimension());

        final List<Point> points = new ArrayList<Point>();

        assertTrue(polygon.getInterior().isEmpty());

        for (Point point : polygon.getExterior()) {
            points.add(point);
        }

        assertEquals(4, points.size());
        assertEquals(30, points.get(0).getX(), 0);
        assertEquals(20, points.get(0).getY(), 0);
        assertEquals(10, points.get(1).getX(), 0);
        assertEquals(40, points.get(1).getY(), 0);
        assertEquals(45, points.get(2).getX(), 0);
        assertEquals(40, points.get(2).getY(), 0);
        assertEquals(30, points.get(3).getX(), 0);
        assertEquals(20, points.get(3).getY(), 0);
    }

    @Test
    public void geographyPolygon() {
        final ODataPrimitiveValue opv =
                readPropertyValue(testODataServiceRootURL, "AllGeoTypesSet(-5)", "GeogPolygon");
        assertEquals(EdmSimpleType.GEOGRAPHY_POLYGON.toString(), opv.getTypeName());

        final Polygon polygon = opv.<Polygon>toCastValue();
        assertNotNull(polygon);
        assertEquals(Geospatial.Dimension.GEOGRAPHY, polygon.getDimension());

        final List<Point> points = new ArrayList<Point>();

        assertTrue(polygon.getInterior().isEmpty());

        for (Point point : polygon.getExterior()) {
            points.add(point);
        }

        assertEquals(5, points.size());
        assertEquals(5, points.get(0).getX(), 0);
        assertEquals(15, points.get(0).getY(), 0);
        assertEquals(10, points.get(1).getX(), 0);
        assertEquals(40, points.get(1).getY(), 0);
        assertEquals(20, points.get(2).getX(), 0);
        assertEquals(10, points.get(2).getY(), 0);
        assertEquals(10, points.get(3).getX(), 0);
        assertEquals(5, points.get(3).getY(), 0);
        assertEquals(5, points.get(4).getX(), 0);
        assertEquals(15, points.get(4).getY(), 0);
    }

    @Test
    public void geometryMultiPolygon() {
        final ODataPrimitiveValue opv =
                readPropertyValue(testODataServiceRootURL, "AllGeoTypesSet(-3)", "GeomMultiPolygon");
        assertEquals(EdmSimpleType.GEOMETRY_MULTI_POLYGON.toString(), opv.getTypeName());

        final MultiPolygon multiPolygon = opv.<MultiPolygon>toCastValue();
        assertNotNull(multiPolygon);
        assertEquals(Geospatial.Dimension.GEOMETRY, multiPolygon.getDimension());

        final List<Polygon> polygons = new ArrayList<Polygon>();

        for (Polygon polygon : multiPolygon) {
            polygons.add(polygon);
        }

        assertEquals(2, polygons.size());

        Polygon polygon = polygons.get(0);
        assertTrue(polygon.getInterior().isEmpty());

        final List<Point> points = new ArrayList<Point>();

        for (Point point : polygon.getExterior()) {
            points.add(point);
        }

        assertEquals(4, points.size());
        assertEquals(40, points.get(0).getX(), 0);
        assertEquals(40, points.get(0).getY(), 0);
        assertEquals(20, points.get(1).getX(), 0);
        assertEquals(45, points.get(1).getY(), 0);
        assertEquals(45, points.get(2).getX(), 0);
        assertEquals(30, points.get(2).getY(), 0);
        assertEquals(40, points.get(3).getX(), 0);
        assertEquals(40, points.get(3).getY(), 0);

        polygon = polygons.get(1);

        points.clear();

        for (Point point : polygon.getExterior()) {
            points.add(point);
        }

        assertEquals(6, points.size());
        assertEquals(20, points.get(0).getX(), 0);
        assertEquals(35, points.get(0).getY(), 0);
        assertEquals(45, points.get(1).getX(), 0);
        assertEquals(20, points.get(1).getY(), 0);
        assertEquals(30, points.get(2).getX(), 0);
        assertEquals(5, points.get(2).getY(), 0);
        assertEquals(10, points.get(3).getX(), 0);
        assertEquals(10, points.get(3).getY(), 0);
        assertEquals(10, points.get(4).getX(), 0);
        assertEquals(30, points.get(4).getY(), 0);
        assertEquals(20, points.get(5).getX(), 0);
        assertEquals(35, points.get(5).getY(), 0);

        points.clear();

        for (Point point : polygon.getInterior()) {
            points.add(point);
        }

        assertEquals(4, points.size());
        assertEquals(30, points.get(0).getX(), 0);
        assertEquals(20, points.get(0).getY(), 0);
        assertEquals(20, points.get(1).getX(), 0);
        assertEquals(25, points.get(1).getY(), 0);
        assertEquals(20, points.get(2).getX(), 0);
        assertEquals(15, points.get(2).getY(), 0);
        assertEquals(30, points.get(3).getX(), 0);
        assertEquals(20, points.get(3).getY(), 0);
    }

    @Test
    public void geographyMultiPolygon() {
        final ODataPrimitiveValue opv =
                readPropertyValue(testODataServiceRootURL, "AllGeoTypesSet(-5)", "GeogMultiPolygon");
        assertEquals(EdmSimpleType.GEOGRAPHY_MULTI_POLYGON.toString(), opv.getTypeName());

        final MultiPolygon multiPolygon = opv.<MultiPolygon>toCastValue();
        assertNotNull(multiPolygon);
        assertEquals(Geospatial.Dimension.GEOGRAPHY, multiPolygon.getDimension());

        final List<Polygon> polygons = new ArrayList<Polygon>();

        for (Polygon polygon : multiPolygon) {
            polygons.add(polygon);
        }

        assertEquals(2, polygons.size());

        Polygon polygon = polygons.get(0);
        assertTrue(polygon.getInterior().isEmpty());

        final List<Point> points = new ArrayList<Point>();

        for (Point point : polygon.getExterior()) {
            points.add(point);
        }

        assertEquals(4, points.size());
        assertEquals(40, points.get(0).getX(), 0);
        assertEquals(40, points.get(0).getY(), 0);
        assertEquals(45, points.get(1).getX(), 0);
        assertEquals(20, points.get(1).getY(), 0);
        assertEquals(30, points.get(2).getX(), 0);
        assertEquals(45, points.get(2).getY(), 0);
        assertEquals(40, points.get(3).getX(), 0);
        assertEquals(40, points.get(3).getY(), 0);

        polygon = polygons.get(1);

        points.clear();

        for (Point point : polygon.getExterior()) {
            points.add(point);
        }

        assertEquals(6, points.size());
        assertEquals(35, points.get(0).getX(), 0);
        assertEquals(20, points.get(0).getY(), 0);
        assertEquals(20, points.get(1).getX(), 0);
        assertEquals(45, points.get(1).getY(), 0);
        assertEquals(5, points.get(2).getX(), 0);
        assertEquals(30, points.get(2).getY(), 0);
        assertEquals(10, points.get(3).getX(), 0);
        assertEquals(10, points.get(3).getY(), 0);
        assertEquals(30, points.get(4).getX(), 0);
        assertEquals(10, points.get(4).getY(), 0);
        assertEquals(35, points.get(5).getX(), 0);
        assertEquals(20, points.get(5).getY(), 0);

        points.clear();

        for (Point point : polygon.getInterior()) {
            points.add(point);
        }

        assertEquals(4, points.size());
        assertEquals(20, points.get(0).getX(), 0);
        assertEquals(30, points.get(0).getY(), 0);
        assertEquals(25, points.get(1).getX(), 0);
        assertEquals(20, points.get(1).getY(), 0);
        assertEquals(15, points.get(2).getX(), 0);
        assertEquals(20, points.get(2).getY(), 0);
        assertEquals(20, points.get(3).getX(), 0);
        assertEquals(30, points.get(3).getY(), 0);
    }

    @Test
    public void geometryCollection() {
        final ODataPrimitiveValue opv =
                readPropertyValue(testODataServiceRootURL, "AllGeoTypesSet(-1)", "GeomCollection");
        assertEquals(EdmSimpleType.GEOMETRY_COLLECTION.toString(), opv.getTypeName());

        final GeospatialCollection multiPolygon = opv.<GeospatialCollection>toCastValue();
        assertNotNull(multiPolygon);
        assertEquals(Geospatial.Dimension.GEOMETRY, multiPolygon.getDimension());
    }

    @Test
    public void geographyCollection() {
        final ODataPrimitiveValue opv =
                readPropertyValue(testODataServiceRootURL, "AllGeoTypesSet(-2)", "GeogCollection");
        assertEquals(EdmSimpleType.GEOGRAPHY_COLLECTION.toString(), opv.getTypeName());

        final GeospatialCollection multiPolygon = opv.<GeospatialCollection>toCastValue();
        assertNotNull(multiPolygon);
        assertEquals(Geospatial.Dimension.GEOGRAPHY, multiPolygon.getDimension());
    }

    @Test
    public void readAllGeoEntitySet() {
        ODataURIBuilder uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("AllGeoTypesSet");

        ODataEntitySetRequest req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
        req.setFormat(ODataPubFormat.ATOM);
        assertNotNull(req.execute().getBody());

        req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
        req.setFormat(ODataPubFormat.JSON);
        assertNotNull(req.execute().getBody());

        req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
        req.setFormat(ODataPubFormat.JSON_FULL_METADATA);
        assertNotNull(req.execute().getBody());

        req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
        req.setFormat(ODataPubFormat.JSON_NO_METADATA);
        assertNotNull(req.execute().getBody());

        uriBuilder = new ODataURIBuilder(testODataServiceRootURL);
        uriBuilder.appendEntitySetSegment("AllGeoCollectionTypesSet");

        req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
        req.setFormat(ODataPubFormat.ATOM);
        assertNotNull(req.execute().getBody());

        req = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build());
        req.setFormat(ODataPubFormat.JSON);
        assertNotNull(req.execute().getBody());
    }
}
