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
package com.msopentech.odatajclient.engine.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataGeospatialValue;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Geospatial;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.GeospatialCollection;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.LineString;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiLineString;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiPoint;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiPolygon;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Point;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Polygon;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.Test;

public class OpenTypeTestITCase extends AbstractTest {

    @Test
    public void checkOpenTypeEntityTypesExist() {
        final EdmMetadata metadata = ODataRetrieveRequestFactory.
                getMetadataRequest(testOpenTypeServiceRootURL).execute().getBody();

        assertTrue(metadata.getSchemas().get(0).getEntityType("Row").isOpenType());
        assertTrue(metadata.getSchemas().get(0).getEntityType("IndexedRow").isOpenType());
        assertTrue(metadata.getSchemas().get(0).getEntityType("RowIndex").isOpenType());
    }

    private ODataEntity readRow(final ODataPubFormat format, final String uuid) {
        final ODataURIBuilder builder = new ODataURIBuilder(testOpenTypeServiceRootURL).
                appendEntityTypeSegment("Row").appendKeySegment(UUID.fromString(uuid));
        return read(format, builder.build());
    }

    private void read(final ODataPubFormat format) {
        ODataEntity row = readRow(format, "71f7d0dc-ede4-45eb-b421-555a2aa1e58f");
        assertEquals(EdmSimpleType.DOUBLE.toString(), row.getProperty("Double").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GUID.toString(), row.getProperty("Id").getPrimitiveValue().getTypeName());

        row = readRow(format, "672b8250-1e6e-4785-80cf-b94b572e42b3");
        assertEquals(EdmSimpleType.DECIMAL.toString(), row.getProperty("Decimal").getPrimitiveValue().getTypeName());
    }

    @Test
    public void readAsAtom() {
        read(ODataPubFormat.ATOM);
    }

    @Test
    public void readAsJSON() {
        read(ODataPubFormat.JSON_FULL_METADATA);
    }

    private void cud(final ODataPubFormat format) {
        final UUID guid = UUID.randomUUID();

        ODataEntity row = ODataFactory.newEntity("Microsoft.Test.OData.Services.OpenTypesService.Row");
        row.addProperty(ODataFactory.newPrimitiveProperty("Id",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.GUID).setValue(guid).build()));
        row.addProperty(ODataFactory.newPrimitiveProperty("aString",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.STRING).setValue("string").build()));
        row.addProperty(ODataFactory.newPrimitiveProperty("aBoolean",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.BOOLEAN).setValue(true).build()));
        row.addProperty(ODataFactory.newPrimitiveProperty("aLong",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.INT_64).setValue(15L).build()));
        row.addProperty(ODataFactory.newPrimitiveProperty("aDouble",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.DOUBLE).setValue(1.5D).build()));
        row.addProperty(ODataFactory.newPrimitiveProperty("aByte",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.S_BYTE).setValue(Byte.MAX_VALUE).build()));
        row.addProperty(ODataFactory.newPrimitiveProperty("aDate",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.DATE_TIME).
                setValue(new Date()).build()));

        final Point point = new Point(Geospatial.Dimension.GEOGRAPHY);
        point.setX(1.2);
        point.setY(2.1);
        row.addProperty(ODataFactory.newPrimitiveProperty("aPoint",
                new ODataGeospatialValue.Builder().setType(EdmSimpleType.GEOGRAPHY_POINT).
                setValue(point).build()));
        final List<Point> points = new ArrayList<Point>();
        points.add(point);
        points.add(point);
        final MultiPoint multipoint = new MultiPoint(Geospatial.Dimension.GEOMETRY, points);
        row.addProperty(ODataFactory.newPrimitiveProperty("aMultiPoint",
                new ODataGeospatialValue.Builder().setType(EdmSimpleType.GEOMETRY_MULTI_POINT).
                setValue(multipoint).build()));
        final LineString lineString = new LineString(Geospatial.Dimension.GEOMETRY, points);
        row.addProperty(ODataFactory.newPrimitiveProperty("aLineString",
                new ODataGeospatialValue.Builder().setType(EdmSimpleType.GEOMETRY_LINE_STRING).
                setValue(lineString).build()));
        final List<LineString> lineStrings = new ArrayList<LineString>();
        lineStrings.add(lineString);
        lineStrings.add(lineString);
        final MultiLineString multiLineString = new MultiLineString(Geospatial.Dimension.GEOGRAPHY, lineStrings);
        row.addProperty(ODataFactory.newPrimitiveProperty("aMultiLineString",
                new ODataGeospatialValue.Builder().setType(EdmSimpleType.GEOMETRY_MULTI_LINE_STRING).
                setValue(multiLineString).build()));
        final Point otherPoint = new Point(Geospatial.Dimension.GEOGRAPHY);
        otherPoint.setX(3.4);
        otherPoint.setY(4.3);
        points.set(1, otherPoint);
        points.add(otherPoint);
        points.add(point);
        final Polygon polygon =
                new Polygon(Geospatial.Dimension.GEOGRAPHY, points, points);
        row.addProperty(ODataFactory.newPrimitiveProperty("aPolygon",
                new ODataGeospatialValue.Builder().setType(EdmSimpleType.GEOGRAPHY_POLYGON).
                setValue(polygon).build()));
        final List<Polygon> polygons = new ArrayList<Polygon>();
        polygons.add(polygon);
        polygons.add(polygon);
        final MultiPolygon multiPolygon = new MultiPolygon(Geospatial.Dimension.GEOGRAPHY, polygons);
        row.addProperty(ODataFactory.newPrimitiveProperty("aMultiPolygon",
                new ODataGeospatialValue.Builder().setType(EdmSimpleType.GEOGRAPHY_MULTI_POLYGON).
                setValue(multiPolygon).build()));
        final List<Geospatial> geospatials = new ArrayList<Geospatial>();
        geospatials.add(otherPoint);
        geospatials.add(polygon);
        geospatials.add(multiLineString);
        geospatials.add(multiPolygon);
        final GeospatialCollection geoColl = new GeospatialCollection(Geospatial.Dimension.GEOGRAPHY, geospatials);
        row.addProperty(ODataFactory.newPrimitiveProperty("aCollection",
                new ODataGeospatialValue.Builder().setType(EdmSimpleType.GEOGRAPHY_COLLECTION).
                setValue(geoColl).build()));

        final ODataComplexValue contactDetails =
                new ODataComplexValue("Microsoft.Test.OData.Services.OpenTypesService.ContactDetails");
        contactDetails.add(ODataFactory.newPrimitiveProperty("FirstContacted",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.BINARY).setValue("text".getBytes()).build()));
        contactDetails.add(ODataFactory.newPrimitiveProperty("LastContacted",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.DATE_TIME_OFFSET).
                setText("2001-04-05T05:05:05.001+00:01").build()));
        contactDetails.add(ODataFactory.newPrimitiveProperty("Contacted",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.DATE_TIME).
                setText("2001-04-05T05:05:04.001").build()));
        contactDetails.add(ODataFactory.newPrimitiveProperty("GUID",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.GUID).
                setValue(UUID.randomUUID()).build()));
        contactDetails.add(ODataFactory.newPrimitiveProperty("PreferedContactTime",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.TIME).
                setText("-P9DT51M10.5063807S").build()));
        contactDetails.add(ODataFactory.newPrimitiveProperty("Byte",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.BYTE).setValue(Integer.valueOf(241)).build()));
        contactDetails.add(ODataFactory.newPrimitiveProperty("SignedByte",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.S_BYTE).setValue(Byte.MAX_VALUE).build()));
        contactDetails.add(ODataFactory.newPrimitiveProperty("Double",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.DOUBLE).setValue(Double.MAX_VALUE).build()));
        contactDetails.add(ODataFactory.newPrimitiveProperty("Single",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.SINGLE).setValue(Float.MAX_VALUE).build()));
        contactDetails.add(ODataFactory.newPrimitiveProperty("Short",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.INT_16).setValue(Short.MAX_VALUE).build()));
        contactDetails.add(ODataFactory.newPrimitiveProperty("Int",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.INT_32).setValue(Integer.MAX_VALUE).build()));
        contactDetails.add(ODataFactory.newPrimitiveProperty("Long",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.INT_64).setValue(Long.MAX_VALUE).build()));
        row.addProperty(ODataFactory.newComplexProperty("aContact", contactDetails));

        final ODataEntityCreateRequest createReq = ODataCUDRequestFactory.
                getEntityCreateRequest(new ODataURIBuilder(testOpenTypeServiceRootURL).
                appendEntityTypeSegment("Row").build(), row);
        createReq.setFormat(format);
        final ODataEntityCreateResponse createRes = createReq.execute();
        assertEquals(201, createRes.getStatusCode());

        row = readRow(format, guid.toString());
        assertNotNull(row);
        assertEquals(EdmSimpleType.GUID.toString(), row.getProperty("Id").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.STRING.toString(), row.getProperty("aString").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.BOOLEAN.toString(), row.getProperty("aBoolean").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.INT_64.toString(), row.getProperty("aLong").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.DOUBLE.toString(), row.getProperty("aDouble").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.S_BYTE.toString(), row.getProperty("aByte").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.DATE_TIME.toString(), row.getProperty("aDate").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GEOGRAPHY_POINT.toString(),
                row.getProperty("aPoint").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GEOMETRY_MULTI_POINT.toString(),
                row.getProperty("aMultiPoint").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GEOMETRY_LINE_STRING.toString(),
                row.getProperty("aLineString").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GEOMETRY_MULTI_LINE_STRING.toString(),
                row.getProperty("aMultiLineString").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GEOGRAPHY_POLYGON.toString(),
                row.getProperty("aPolygon").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GEOGRAPHY_MULTI_POLYGON.toString(),
                row.getProperty("aMultiPolygon").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GEOGRAPHY_COLLECTION.toString(),
                row.getProperty("aCollection").getPrimitiveValue().getTypeName());
        assertEquals("Microsoft.Test.OData.Services.OpenTypesService.ContactDetails",
                row.getProperty("aContact").getComplexValue().getTypeName());
        assertEquals(EdmSimpleType.S_BYTE.toString(),
                row.getProperty("aContact").getComplexValue().get("SignedByte").getPrimitiveValue().getTypeName());

        final ODataDeleteResponse deleteRes = ODataCUDRequestFactory.getDeleteRequest(row.getEditLink()).execute();
        assertEquals(204, deleteRes.getStatusCode());
    }

    @Test
    public void cudAsAtom() {
        cud(ODataPubFormat.ATOM);
    }

    @Test
    public void cudAsJSON() {
        cud(ODataPubFormat.JSON_FULL_METADATA);
    }
}
