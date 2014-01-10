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
package com.msopentech.odatajclient.engine.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.communication.request.cud.AbstractCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.AbstractRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataObjectFactory;
import com.msopentech.odatajclient.engine.data.ODataGeospatialValue;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.uri.AbstractURIBuilder;
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
        final EdmMetadata metadata = client.getRetrieveRequestFactory().
                getMetadataRequest(testOpenTypeServiceRootURL).execute().getBody();

        assertTrue(metadata.getSchemas().get(0).getEntityType("Row").isOpenType());
        assertTrue(metadata.getSchemas().get(0).getEntityType("IndexedRow").isOpenType());
        assertTrue(metadata.getSchemas().get(0).getEntityType("RowIndex").isOpenType());
    }

    private ODataEntity readRow(final ODataPubFormat format, final String uuid) {
        final AbstractURIBuilder builder = client.getURIBuilder(testOpenTypeServiceRootURL).
                appendEntityTypeSegment("Row").appendKeySegment(UUID.fromString(uuid));
        return read(format, builder.build());
    }

    private void read(final ODataPubFormat format) {
        ODataEntity row = readRow(format, "71f7d0dc-ede4-45eb-b421-555a2aa1e58f");
        assertEquals(EdmSimpleType.Double.toString(), row.getProperty("Double").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.Guid.toString(), row.getProperty("Id").getPrimitiveValue().getTypeName());

        row = readRow(format, "672b8250-1e6e-4785-80cf-b94b572e42b3");
        assertEquals(EdmSimpleType.Decimal.toString(), row.getProperty("Decimal").getPrimitiveValue().getTypeName());
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

        ODataEntity row = ODataObjectFactory.newEntity("Microsoft.Test.OData.Services.OpenTypesService.Row");
        row.addProperty(ODataObjectFactory.newPrimitiveProperty("Id",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.Guid).setValue(guid).build()));
        row.addProperty(ODataObjectFactory.newPrimitiveProperty("aString",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.String).setValue("string").build()));
        row.addProperty(ODataObjectFactory.newPrimitiveProperty("aBoolean",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.Boolean).setValue(true).build()));
        row.addProperty(ODataObjectFactory.newPrimitiveProperty("aLong",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.Int64).setValue(15L).build()));
        row.addProperty(ODataObjectFactory.newPrimitiveProperty("aDouble",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.Double).setValue(1.5D).build()));
        row.addProperty(ODataObjectFactory.newPrimitiveProperty("aByte",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.SByte).setValue(Byte.MAX_VALUE).build()));
        row.addProperty(ODataObjectFactory.newPrimitiveProperty("aDate",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.DateTime).
                setValue(new Date()).build()));

        final Point point = new Point(Geospatial.Dimension.GEOGRAPHY);
        point.setX(1.2);
        point.setY(2.1);
        row.addProperty(ODataObjectFactory.newPrimitiveProperty("aPoint",
                new ODataGeospatialValue.Builder().setType(EdmSimpleType.GeographyPoint).
                setValue(point).build()));
        final List<Point> points = new ArrayList<Point>();
        points.add(point);
        points.add(point);
        final MultiPoint multipoint = new MultiPoint(Geospatial.Dimension.GEOMETRY, points);
        row.addProperty(ODataObjectFactory.newPrimitiveProperty("aMultiPoint",
                new ODataGeospatialValue.Builder().setType(EdmSimpleType.GeometryMultiPoint).
                setValue(multipoint).build()));
        final LineString lineString = new LineString(Geospatial.Dimension.GEOMETRY, points);
        row.addProperty(ODataObjectFactory.newPrimitiveProperty("aLineString",
                new ODataGeospatialValue.Builder().setType(EdmSimpleType.GeometryLineString).
                setValue(lineString).build()));
        final List<LineString> lineStrings = new ArrayList<LineString>();
        lineStrings.add(lineString);
        lineStrings.add(lineString);
        final MultiLineString multiLineString = new MultiLineString(Geospatial.Dimension.GEOGRAPHY, lineStrings);
        row.addProperty(ODataObjectFactory.newPrimitiveProperty("aMultiLineString",
                new ODataGeospatialValue.Builder().setType(EdmSimpleType.GeometryMultiLineString).
                setValue(multiLineString).build()));
        final Point otherPoint = new Point(Geospatial.Dimension.GEOGRAPHY);
        otherPoint.setX(3.4);
        otherPoint.setY(4.3);
        points.set(1, otherPoint);
        points.add(otherPoint);
        points.add(point);
        final Polygon polygon =
                new Polygon(Geospatial.Dimension.GEOGRAPHY, points, points);
        row.addProperty(ODataObjectFactory.newPrimitiveProperty("aPolygon",
                new ODataGeospatialValue.Builder().setType(EdmSimpleType.GeographyPolygon).
                setValue(polygon).build()));
        final List<Polygon> polygons = new ArrayList<Polygon>();
        polygons.add(polygon);
        polygons.add(polygon);
        final MultiPolygon multiPolygon = new MultiPolygon(Geospatial.Dimension.GEOGRAPHY, polygons);
        row.addProperty(ODataObjectFactory.newPrimitiveProperty("aMultiPolygon",
                new ODataGeospatialValue.Builder().setType(EdmSimpleType.GeographyMultiPolygon).
                setValue(multiPolygon).build()));
        final List<Geospatial> geospatials = new ArrayList<Geospatial>();
        geospatials.add(otherPoint);
        geospatials.add(polygon);
        geospatials.add(multiLineString);
        geospatials.add(multiPolygon);
        final GeospatialCollection geoColl = new GeospatialCollection(Geospatial.Dimension.GEOGRAPHY, geospatials);
        row.addProperty(ODataObjectFactory.newPrimitiveProperty("aCollection",
                new ODataGeospatialValue.Builder().setType(EdmSimpleType.GeographyCollection).
                setValue(geoColl).build()));

        final ODataComplexValue contactDetails =
                new ODataComplexValue("Microsoft.Test.OData.Services.OpenTypesService.ContactDetails");
        contactDetails.add(ODataObjectFactory.newPrimitiveProperty("FirstContacted",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.Binary).setValue("text".getBytes()).build()));
        contactDetails.add(ODataObjectFactory.newPrimitiveProperty("LastContacted",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.DateTimeOffset).
                setText("2001-04-05T05:05:05.001+00:01").build()));
        contactDetails.add(ODataObjectFactory.newPrimitiveProperty("Contacted",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.DateTime).
                setText("2001-04-05T05:05:04.001").build()));
        contactDetails.add(ODataObjectFactory.newPrimitiveProperty("GUID",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.Guid).
                setValue(UUID.randomUUID()).build()));
        contactDetails.add(ODataObjectFactory.newPrimitiveProperty("PreferedContactTime",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.Time).
                setText("-P9DT51M10.5063807S").build()));
        contactDetails.add(ODataObjectFactory.newPrimitiveProperty("Byte",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.Byte).setValue(Integer.valueOf(241)).build()));
        contactDetails.add(ODataObjectFactory.newPrimitiveProperty("SignedByte",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.SByte).setValue(Byte.MAX_VALUE).build()));
        contactDetails.add(ODataObjectFactory.newPrimitiveProperty("Double",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.Double).setValue(Double.MAX_VALUE).build()));
        contactDetails.add(ODataObjectFactory.newPrimitiveProperty("Single",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.Single).setValue(Float.MAX_VALUE).build()));
        contactDetails.add(ODataObjectFactory.newPrimitiveProperty("Short",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.Int16).setValue(Short.MAX_VALUE).build()));
        contactDetails.add(ODataObjectFactory.newPrimitiveProperty("Int",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.Int32).setValue(Integer.MAX_VALUE).build()));
        contactDetails.add(ODataObjectFactory.newPrimitiveProperty("Long",
                new ODataPrimitiveValue.Builder().setType(EdmSimpleType.Int64).setValue(Long.MAX_VALUE).build()));
        row.addProperty(ODataObjectFactory.newComplexProperty("aContact", contactDetails));

        final ODataEntityCreateRequest createReq = client.getCUDRequestFactory().
                getEntityCreateRequest(client.getURIBuilder(testOpenTypeServiceRootURL).
                appendEntityTypeSegment("Row").build(), row);
        createReq.setFormat(format);
        final ODataEntityCreateResponse createRes = createReq.execute();
        assertEquals(201, createRes.getStatusCode());

        row = readRow(format, guid.toString());
        assertNotNull(row);
        assertEquals(EdmSimpleType.Guid.toString(), row.getProperty("Id").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.String.toString(), row.getProperty("aString").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.Boolean.toString(), row.getProperty("aBoolean").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.Int64.toString(), row.getProperty("aLong").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.Double.toString(), row.getProperty("aDouble").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.SByte.toString(), row.getProperty("aByte").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.DateTime.toString(), row.getProperty("aDate").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GeographyPoint.toString(),
                row.getProperty("aPoint").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GeometryMultiPoint.toString(),
                row.getProperty("aMultiPoint").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GeometryLineString.toString(),
                row.getProperty("aLineString").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GeometryMultiLineString.toString(),
                row.getProperty("aMultiLineString").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GeographyPolygon.toString(),
                row.getProperty("aPolygon").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GeographyMultiPolygon.toString(),
                row.getProperty("aMultiPolygon").getPrimitiveValue().getTypeName());
        assertEquals(EdmSimpleType.GeographyCollection.toString(),
                row.getProperty("aCollection").getPrimitiveValue().getTypeName());
        assertEquals("Microsoft.Test.OData.Services.OpenTypesService.ContactDetails",
                row.getProperty("aContact").getComplexValue().getTypeName());
        assertEquals(EdmSimpleType.SByte.toString(),
                row.getProperty("aContact").getComplexValue().get("SignedByte").getPrimitiveValue().getTypeName());

        final ODataDeleteResponse deleteRes = client.getCUDRequestFactory().getDeleteRequest(row.getEditLink()).execute();
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
