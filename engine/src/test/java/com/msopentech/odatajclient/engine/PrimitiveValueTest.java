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
package com.msopentech.odatajclient.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataGeospatialValue;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.data.ODataTimestamp;
import com.msopentech.odatajclient.engine.data.ODataWriter;
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
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class PrimitiveValueTest extends AbstractTest {

    private String getFilename(final String entity, final String propertyName, final ODataFormat format) {
        return entity.replace('(', '_').replace(")", "")
                + "_" + propertyName.replaceAll("/", "_") + "." + getSuffix(format);
    }

    private ODataPrimitiveValue writePrimitiveValue(
            final ODataPrimitiveValue value, final ODataFormat format) {

        final ODataPrimitiveValue newValue;
        if (EdmSimpleType.isGeospatial(value.getTypeName())) {
            newValue = new ODataGeospatialValue.Builder().
                    setType(EdmSimpleType.fromValue(value.getTypeName())).
                    setTree(((ODataGeospatialValue) value).toTree()).build();
        } else {
            newValue = new ODataPrimitiveValue.Builder().
                    setType(EdmSimpleType.fromValue(value.getTypeName())).
                    setValue(value.toValue()).build();
        }

        final InputStream written = ODataWriter.writeProperty(
                ODataFactory.newPrimitiveProperty(ODataConstants.ELEM_PROPERTY, newValue),
                format);
        return readPrimitiveValue(written, format);
    }

    private ODataPrimitiveValue readPrimitiveValue(final InputStream input, final ODataFormat format) {
        final ODataProperty property = ODataReader.readProperty(input, format);
        assertNotNull(property);
        assertTrue(property.hasPrimitiveValue());
        assertNotNull(property.getPrimitiveValue());

        return property.getPrimitiveValue();
    }

    private ODataPrimitiveValue readPrimitiveValue(
            final String entity, final String propertyName, final ODataFormat format) {

        final ODataPrimitiveValue value = readPrimitiveValue(getClass().getResourceAsStream(
                getFilename(entity, propertyName, format)), format);

        if (EdmSimpleType.isGeospatial(value.getTypeName())) {
            assertEquals(value.toValue(), writePrimitiveValue(value, format).toValue());
        } else {
            assertEquals(value.toString(), writePrimitiveValue(value, format).toString());
        }

        return value;
    }

    private void int32(final ODataFormat format) {
        final ODataPrimitiveValue opv = readPrimitiveValue("Customer(-10)", "CustomerId", format);
        assertEquals(EdmSimpleType.INT_32.toString(), opv.getTypeName());

        final Integer value = opv.<Integer>toCastValue();
        assertNotNull(value);
        assertTrue(-10 == value);
    }

    @Test
    public void int32fromXML() {
        int32(ODataFormat.XML);
    }

    @Test
    public void int32fromJSON() {
        int32(ODataFormat.JSON);
    }

    private void string(final ODataFormat format) {
        final ODataPrimitiveValue opv = readPrimitiveValue("Product(-9)", "Description", format);
        assertEquals(EdmSimpleType.STRING.toString(), opv.getTypeName());

        final String value = opv.<String>toCastValue();
        assertNotNull(value);
        assertEquals("kdcuklu", value);

        assertEquals(opv, writePrimitiveValue(opv, format));
    }

    @Test
    public void stringfromXML() {
        string(ODataFormat.XML);
    }

    @Test
    public void stringfromJSON() {
        string(ODataFormat.JSON);
    }

    private void decimal(final ODataFormat format) {
        final ODataPrimitiveValue opv = readPrimitiveValue("Product(-10)", "Dimensions/Width", format);
        assertEquals(EdmSimpleType.DECIMAL.toString(), opv.getTypeName());

        final BigDecimal value = opv.<BigDecimal>toCastValue();
        assertNotNull(value);
        assertTrue(new BigDecimal("-79228162514264337593543950335").equals(value));
    }

    @Test
    public void decimalFromXML() {
        decimal(ODataFormat.XML);
    }

    @Test
    public void decimalFromJSON() {
        decimal(ODataFormat.JSON);
    }

    private void datetime(final ODataFormat format) {
        final ODataPrimitiveValue opv = readPrimitiveValue(
                "Product(-10)", "ComplexConcurrency/QueriedDateTime", format);
        assertEquals(EdmSimpleType.DATE_TIME.toString(), opv.getTypeName());

        final ODataTimestamp value = opv.<ODataTimestamp>toCastValue();
        assertNotNull(value);
        assertEquals("2013-01-10T06:27:51.1667673", opv.toString());
    }

    @Test
    public void datetimeFromXML() {
        datetime(ODataFormat.XML);
    }

    @Test
    public void datetimeFromJSON() {
        datetime(ODataFormat.JSON);
    }

    private void guid(final ODataFormat format) {
        final ODataPrimitiveValue opv = readPrimitiveValue(
                "MessageAttachment(guid'1126a28b-a4af-4bbd-bf0a-2b2c22635565')", "AttachmentId", format);
        assertEquals(EdmSimpleType.GUID.toString(), opv.getTypeName());

        final UUID value = opv.<UUID>toCastValue();
        assertNotNull(value);
        assertEquals("1126a28b-a4af-4bbd-bf0a-2b2c22635565", opv.toString());
    }

    @Test
    public void guidFromXML() {
        guid(ODataFormat.XML);
    }

    @Test
    public void guidFromJSON() {
        guid(ODataFormat.JSON);
    }

    private void binary(final ODataFormat format) {
        final ODataPrimitiveValue opv = readPrimitiveValue(
                "MessageAttachment(guid'1126a28b-a4af-4bbd-bf0a-2b2c22635565')", "Attachment", format);
        assertEquals(EdmSimpleType.BINARY.toString(), opv.getTypeName());

        final byte[] value = opv.<byte[]>toCastValue();
        assertNotNull(value);
        assertTrue(value.length > 0);
        assertTrue(Base64.isBase64(opv.toString()));
    }

    @Test
    public void binaryFromXML() {
        binary(ODataFormat.XML);
    }

    @Test
    public void binaryFromJSON() {
        binary(ODataFormat.JSON);
    }

    private void point(final ODataFormat format) {
        final ODataPrimitiveValue opv = readPrimitiveValue("AllGeoTypesSet(-10)", "GeogPoint", format);
        assertEquals(EdmSimpleType.GEOGRAPHY_POINT.toString(), opv.getTypeName());

        final Point point = opv.<Point>toCastValue();
        assertNotNull(point);
        assertEquals(Geospatial.Dimension.GEOGRAPHY, point.getDimension());

        assertEquals(52.8606, point.getX(), 0);
        assertEquals(173.334, point.getY(), 0);
    }

    @Test
    public void pointFromXML() {
        point(ODataFormat.XML);
    }

    @Test
    public void pointFromJSON() {
        point(ODataFormat.JSON);
    }

    private void lineString(final ODataFormat format) {
        final ODataPrimitiveValue opv = readPrimitiveValue("AllGeoTypesSet(-10)", "GeogLine", format);
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
    public void lineStringFromXML() {
        lineString(ODataFormat.XML);
    }

    @Test
    public void lineStringFromJSON() {
        lineString(ODataFormat.JSON);
    }

    private void multiPoint(final ODataFormat format) {
        final ODataPrimitiveValue opv = readPrimitiveValue("AllGeoTypesSet(-7)", "GeomMultiPoint", format);
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
    public void multiPointFromXML() {
        multiPoint(ODataFormat.XML);
    }

    @Test
    public void multiPointFromJSON() {
        multiPoint(ODataFormat.JSON);
    }

    private void multiLine(final ODataFormat format) {
        final ODataPrimitiveValue opv = readPrimitiveValue("AllGeoTypesSet(-6)", "GeomMultiLine", format);
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
    public void multiLineFromXML() {
        multiLine(ODataFormat.XML);
    }

    @Test
    public void multiLineFromJSON() {
        multiLine(ODataFormat.JSON);
    }

    private void polygon(final ODataFormat format) {
        final ODataPrimitiveValue opv = readPrimitiveValue("AllGeoTypesSet(-5)", "GeogPolygon", format);
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
    public void polygonFromXML() {
        polygon(ODataFormat.XML);
    }

    @Test
    public void polygonFromJSON() {
        polygon(ODataFormat.JSON);
    }

    private void multiPolygon(final ODataFormat format) {
        final ODataPrimitiveValue opv = readPrimitiveValue("AllGeoTypesSet(-3)", "GeomMultiPolygon", format);
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
    public void multiPolygonFromXML() {
        multiPolygon(ODataFormat.XML);
    }

    @Test
    public void multiPolygonFromJSON() {
        multiPolygon(ODataFormat.JSON);
    }

    private void geomCollection(final ODataFormat format) {
        final ODataPrimitiveValue opv = readPrimitiveValue("AllGeoTypesSet(-8)", "GeomCollection", format);
        assertEquals(EdmSimpleType.GEOMETRY_COLLECTION.toString(), opv.getTypeName());

        final GeospatialCollection collection = opv.<GeospatialCollection>toCastValue();
        assertNotNull(collection);
        assertEquals(Geospatial.Dimension.GEOMETRY, collection.getDimension());

        final Iterator<Geospatial> itor = collection.iterator();
        int count = 0;
        while (itor.hasNext()) {
            count++;

            final Geospatial geospatial = itor.next();
            if (count == 1) {
                assertTrue(geospatial instanceof Point);
            }
            if (count == 2) {
                assertTrue(geospatial instanceof LineString);
            }
        }
        assertEquals(2, count);
    }

    @Test
    public void geomCollectionFromXML() {
        geomCollection(ODataFormat.XML);
    }

    @Test
    public void geomCollectionFromJSON() {
        geomCollection(ODataFormat.JSON);
    }

    private void geogCollection(final ODataFormat format) {
        final ODataPrimitiveValue opv = readPrimitiveValue("AllGeoTypesSet(-5)", "GeogCollection", format);
        assertEquals(EdmSimpleType.GEOGRAPHY_COLLECTION.toString(), opv.getTypeName());

        final GeospatialCollection collection = opv.<GeospatialCollection>toCastValue();
        assertNotNull(collection);
        assertEquals(Geospatial.Dimension.GEOGRAPHY, collection.getDimension());

        final Iterator<Geospatial> itor = collection.iterator();
        int count = 0;
        while (itor.hasNext()) {
            count++;

            final Geospatial geospatial = itor.next();
            if (count == 1) {
                assertTrue(geospatial instanceof GeospatialCollection);
            }
            if (count == 2) {
                assertTrue(geospatial instanceof GeospatialCollection);
            }
        }
        assertEquals(2, count);
    }

    @Test
    public void geogCollectionFromXML() {
        geogCollection(ODataFormat.XML);
    }

    @Test
    public void geogCollectionFromJSON() {
        geogCollection(ODataFormat.JSON);
    }
}
