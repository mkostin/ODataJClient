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
package com.msopentech.odatajclient.engine.data;

import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.ComposedGeospatial;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Geospatial;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Geospatial.Dimension;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.GeospatialCollection;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.LineString;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiLineString;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiPoint;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiPolygon;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Point;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Polygon;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * OData primitive property value.
 */
public class ODataPrimitiveValue extends ODataValue {

    private static final long serialVersionUID = 2841837627899878223L;

    /**
     * Primitive value builder.
     */
    public static class Builder {

        private final ODataPrimitiveValue opv;

        /**
         * Constructor.
         */
        public Builder() {
            this.opv = new ODataPrimitiveValue();
        }

        /**
         * Sets the given value provided as a text.
         *
         * @param text value.
         * @return the current builder.
         */
        public Builder setText(final String text) {
            this.opv.text = text;
            return this;
        }

        /**
         * Sets the actual object value.
         *
         * @param value value.
         * @return the current builder.
         */
        public Builder setValue(final Object value) {
            this.opv.value = value;
            return this;
        }

        /**
         * Sets actual value type.
         *
         * @param type type.
         * @return the current builder.
         */
        public Builder setType(final EdmSimpleType type) {
            if (type == EdmSimpleType.STREAM) {
                throw new IllegalArgumentException("Cannot build a primitive value for "
                        + EdmSimpleType.STREAM.toString());
            }
            this.opv.type = type;
            return this;
        }

        /**
         * Builds the primitive value.
         *
         * @return <code>ODataPrimitiveValue</code> object.
         */
        public ODataPrimitiveValue build() {
            if (this.opv.text == null && this.opv.value == null) {
                throw new IllegalArgumentException("Must provide either text or value");
            }
            if (this.opv.text != null && this.opv.value != null) {
                throw new IllegalArgumentException("Cannot provide both text and value");
            }

            if (this.opv.type == null) {
                this.opv.type = EdmSimpleType.STRING;
            }

            if (this.opv.value != null && !this.opv.type.javaType().isAssignableFrom(this.opv.value.getClass())) {
                throw new IllegalArgumentException("Provided value is not compatible with " + this.opv.type.toString());
            }

            if (this.opv.text != null) {
                this.opv.parseText();
            }
            if (this.opv.value != null) {
                this.opv.formatValue();
            }

            return this.opv;
        }
    }

    /**
     * Text value.
     */
    private String text;

    /**
     * Actual value.
     */
    private Object value;

    /**
     * Value type.
     */
    private EdmSimpleType type;

    /**
     * Private constructor, need to use the builder to instantiate this class.
     *
     * @see Builder
     */
    private ODataPrimitiveValue() {
        super();
    }

    /**
     * Parses given text as object value.
     */
    private void parseText() {
        switch (this.type) {
            case NULL:
                this.value = null;
                break;

            case BINARY:
                this.value = Base64.decodeBase64(this.toString());
                break;

            case S_BYTE:
                this.value = Byte.parseByte(this.toString());
                break;

            case BOOLEAN:
                this.value = Boolean.parseBoolean(this.toString());
                break;

            case DATE_TIME:
            case DATE_TIME_OFFSET:
            case TIME:
                final String dateText = this.toString();
                final String[] dateParts = dateText.split("\\.");
                final SimpleDateFormat sdf = new SimpleDateFormat(this.type.pattern());
                try {
                    this.value = new Timestamp(sdf.parse(dateParts[0]).getTime());
                    if (dateParts.length > 1) {
                        this.<Timestamp>toCastValue().setNanos(Integer.parseInt(dateParts[1]));
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException(
                            "Cannot parse " + this.toString() + " as " + this.type.toString(), e);
                }
                break;

            case DECIMAL:
                this.value = new BigDecimal(this.toString());
                break;

            case SINGLE:
                this.value = Float.parseFloat(this.toString());
                break;

            case DOUBLE:
                this.value = Double.parseDouble(this.toString());
                break;

            case GUID:
                this.value = UUID.fromString(this.toString());
                break;

            case INT_16:
                this.value = Short.parseShort(this.toString());
                break;

            case BYTE:
            case INT_32:
                this.value = Integer.parseInt(this.toString());
                break;

            case INT_64:
                this.value = Long.parseLong(this.toString());
                break;

            case STREAM:
                this.value = URI.create(this.toString());
                break;

            case STRING:
                this.value = this.toString();
                break;

            case GEOGRAPHY_POINT:
            case GEOMETRY_POINT:
                final String[] points = getGeospatialsInfo(this.toString()).getValue().split("\\|");

                if (points == null || points.length == 0) {
                    throw new IllegalArgumentException("No points found in " + this.toString());
                }
                this.value = getPoint(points[0], getDimension());
                break;

            case GEOGRAPHY_MULTI_POINT:
            case GEOMETRY_MULTI_POINT:
                Dimension dimension = getDimension();
                this.value = new MultiPoint(
                        dimension, getPoints(getGeospatialsInfo(this.toString()).getValue(), dimension));
                break;

            case GEOGRAPHY_LINE_STRING:
            case GEOMETRY_LINE_STRING:
                dimension = getDimension();
                this.value = new LineString(
                        dimension, getPoints(getGeospatialsInfo(this.toString()).getValue(), dimension));
                break;

            case GEOGRAPHY_MULTI_LINE_STRING:
            case GEOMETRY_MULTI_LINE_STRING:
                dimension = getDimension();
                this.value = new MultiLineString(
                        dimension, getLineStrings(getGeospatialsInfo(this.toString()).getValue(), dimension));
                break;

            case GEOGRAPHY_POLYGON:
            case GEOMETRY_POLYGON:
                dimension = getDimension();
                this.value = getPolygon(getGeospatialsInfo(this.toString()).getValue(), dimension);
                break;

            case GEOGRAPHY_MULTI_POLYGON:
            case GEOMETRY_MULTI_POLYGON:
                dimension = getDimension();
                this.value = new MultiPolygon(
                        dimension, getPolygons(getGeospatialsInfo(this.toString()).getValue(), dimension));
                break;

            case GEOGRAPHY_COLLECTION:
            case GEOMETRY_COLLECTION:
                dimension = getDimension();
                this.value = getCollection(getGeospatialsInfo(this.toString()).getValue(), dimension);
                break;

            case GEOGRAPHY:
            case GEOMETRY:
                throw new IllegalArgumentException(
                        "Geometry is not an instantiable type. "
                        + "An entity can declare a property to be of type Geometry. "
                        + "An instance of an entity MUST NOT have a value of type Geometry. "
                        + "Each value MUST be of some subtype.");

            default:
        }
    }

    /**
     * Format given value as text.
     */
    private void formatValue() {
        switch (this.type) {
            case NULL:
                this.text = StringUtils.EMPTY;
                break;

            case BINARY:
                this.text = Base64.encodeBase64String(this.<byte[]>toCastValue());
                break;

            case S_BYTE:
                this.text = this.<Byte>toCastValue().toString();
                break;

            case BOOLEAN:
                this.text = this.<Boolean>toCastValue().toString();
                break;

            case DATE_TIME:
            case DATE_TIME_OFFSET:
            case TIME:
                final Timestamp timestamp = this.<Timestamp>toCastValue();
                final SimpleDateFormat sdf = new SimpleDateFormat(this.type.pattern());
                final StringBuilder formatted = new StringBuilder().append(sdf.format(timestamp));
                if (timestamp.getNanos() > 0) {
                    formatted.append('.').append(String.valueOf(timestamp.getNanos()));
                }
                this.text = formatted.toString();
                break;

            case DECIMAL:
                final DecimalFormat decf = new DecimalFormat(this.type.pattern());
                this.text = decf.format(this.<BigDecimal>toCastValue());
                break;

            case SINGLE:
                final DecimalFormat sf = new DecimalFormat(this.type.pattern());
                this.text = sf.format(this.<Float>toCastValue());
                break;

            case DOUBLE:
                final DecimalFormat df = new DecimalFormat(this.type.pattern());
                this.text = df.format(this.<Double>toCastValue());
                break;

            case GUID:
                this.text = this.<UUID>toCastValue().toString();
                break;

            case INT_16:
                this.text = this.<Short>toCastValue().toString();
                break;

            case BYTE:
            case INT_32:
                this.text = this.<Integer>toCastValue().toString();
                break;

            case INT_64:
                this.text = this.<Long>toCastValue().toString();
                break;

            case STREAM:
                this.text = this.<URI>toCastValue().toASCIIString();
                break;

            case STRING:
                this.text = this.<String>toCastValue();
                break;

            case GEOGRAPHY_POINT:
            case GEOMETRY_POINT:
                this.text = getGeospatialAsString(this.<Point>toCastValue());
                break;

            case GEOMETRY_MULTI_POINT:
            case GEOGRAPHY_MULTI_POINT:
                this.text = getGeospatialAsString(this.<MultiPoint>toCastValue());
                break;

            case GEOMETRY_LINE_STRING:
            case GEOGRAPHY_LINE_STRING:
                this.text = getGeospatialAsString(this.<LineString>toCastValue());
                break;

            case GEOMETRY_MULTI_LINE_STRING:
            case GEOGRAPHY_MULTI_LINE_STRING:
                this.text = getGeospatialAsString(this.<MultiLineString>toCastValue());
                break;

            case GEOGRAPHY_POLYGON:
            case GEOMETRY_POLYGON:
                this.text = getGeospatialAsString(this.<Polygon>toCastValue());
                break;

            case GEOGRAPHY_MULTI_POLYGON:
            case GEOMETRY_MULTI_POLYGON:
                this.text = getGeospatialAsString(this.<MultiPolygon>toCastValue());
                break;

            case GEOGRAPHY_COLLECTION:
            case GEOMETRY_COLLECTION:
                this.text = getGeospatialAsString(this.<GeospatialCollection>toCastValue());
                break;

            case GEOGRAPHY:
            case GEOMETRY:
                throw new IllegalArgumentException(
                        "Geometry is not an instantiable type. "
                        + "An entity can declare a property to be of type Geometry. "
                        + "An instance of an entity MUST NOT have a value of type Geometry. "
                        + "Each value MUST be of some subtype.");

            default:
        }
    }

    /**
     * Gets type name.
     *
     * @return type name.
     */
    public String getTypeName() {
        return type.toString();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String toString() {
        return this.text;
    }

    /**
     * Gets actual primitive value.
     *
     * @return
     */
    public Object toValue() {
        return this.value;
    }

    /**
     * Casts primitive value.
     *
     * @param <T> cast.
     * @return casted value.
     */
    @SuppressWarnings("unchecked")
    public <T> T toCastValue() {
        return (T) type.javaType().cast(toValue());
    }

    private Dimension getDimension() {
        switch (this.type) {
            case GEOGRAPHY:
            case GEOGRAPHY_COLLECTION:
            case GEOGRAPHY_LINE_STRING:
            case GEOGRAPHY_MULTI_LINE_STRING:
            case GEOGRAPHY_POINT:
            case GEOGRAPHY_MULTI_POINT:
            case GEOGRAPHY_POLYGON:
            case GEOGRAPHY_MULTI_POLYGON:
                return Geospatial.Dimension.GEOGRAPHY;
            default:
                return Geospatial.Dimension.GEOMETRY;
        }
    }

    private Map.Entry<String, String> getGeospatialsInfo(final String src) {
        int last = src.lastIndexOf(":");

        if (last < 0) {
            throw new IllegalArgumentException("Invalid geospatials data " + src);
        }

        try {
            return new AbstractMap.SimpleEntry<String, String>(
                    src.substring(0, last).trim(),
                    URLDecoder.decode(src.substring(last + 1, src.length()), "UTF-8").trim());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Point getPoint(final String src, final Dimension dimension) {
        final String[] element = src.split(" ");
        if (element == null || element.length != 2) {
            throw new IllegalArgumentException("No X Y coordinates found in " + src);
        }
        final Point point = new Point(dimension);
        point.setX(Double.parseDouble(element[0].trim()));
        point.setY(Double.parseDouble(element[1].trim()));

        return point;
    }

    private List<Point> getPoints(final String src, final Dimension dimension) {
        final String[] elements = src.split("\\|");
        if (elements == null || elements.length == 0) {
            throw new IllegalArgumentException("No points found in " + src);
        }

        final List<Point> points = new ArrayList<Point>();

        for (String coordinates : elements) {
            points.add(getPoint(coordinates, dimension));
        }

        return points;
    }

    private String getPointsAsString(final ComposedGeospatial<Point> points) {
        final StringBuilder textBuilder = new StringBuilder();
        for (Point point : points) {
            if (textBuilder.length() > 0) {
                textBuilder.append('|');
            }
            textBuilder.append(point.getX()).append(' ').append(point.getY());
        }
        return textBuilder.toString();
    }

    private List<LineString> getLineStrings(final String src, final Dimension dimension) {
        final String[] elements = src.split(";");
        if (elements == null || elements.length == 0) {
            throw new IllegalArgumentException("No lineString found in " + src);
        }

        final List<LineString> strings = new ArrayList<LineString>();

        for (String points : elements) {
            strings.add(new LineString(dimension, getPoints(points, dimension)));
        }

        return strings;
    }

    private String getLineStringsAsString(final ComposedGeospatial<LineString> strings) {
        final StringBuilder textBuilder = new StringBuilder();
        for (LineString string : strings) {
            if (textBuilder.length() > 0) {
                textBuilder.append(';');
            }
            textBuilder.append(getPointsAsString(string));
        }
        return textBuilder.toString();
    }

    private Polygon getPolygon(final String src, final Dimension dimension) {
        final String[] elements = src.split(":");
        if (elements == null || elements.length != 2) {
            throw new IllegalArgumentException("Invalid polygon found in " + src);
        }

        return new Polygon(dimension,
                elements[0].trim().isEmpty() ? Collections.<Point>emptyList() : getPoints(elements[0], dimension),
                elements[1].trim().isEmpty() ? Collections.<Point>emptyList() : getPoints(elements[1], dimension));
    }

    private String getPolygonAsString(final Polygon polygon) {
        final StringBuilder textBuilder = new StringBuilder();

        final ComposedGeospatial<Point> interior = polygon.getInterior();
        final ComposedGeospatial<Point> exterior = polygon.getExterior();

        if (!interior.isEmpty()) {
            textBuilder.append(getPointsAsString(interior));
        }

        textBuilder.append(":");

        if (!exterior.isEmpty()) {
            textBuilder.append(getPointsAsString(exterior));
        }

        return textBuilder.toString();
    }

    private List<Polygon> getPolygons(final String src, final Dimension dimension) {
        final String[] elements = src.split(";");
        if (elements == null || elements.length == 0) {
            throw new IllegalArgumentException("No polygons found in " + src);
        }

        final List<Polygon> polygons = new ArrayList<Polygon>();

        for (String element : elements) {
            polygons.add(getPolygon(element, dimension));
        }

        return polygons;
    }

    private String getPolygonsAsString(final ComposedGeospatial<Polygon> polygons) {
        final StringBuilder textBuilder = new StringBuilder();
        for (Polygon polygon : polygons) {
            if (textBuilder.length() > 0) {
                textBuilder.append(';');
            }
            textBuilder.append(getPolygonAsString(polygon));
        }
        return textBuilder.toString();
    }

    private GeospatialCollection getCollection(final String src, final Dimension dimension) {
        final String[] elements = src.split(",");
        if (elements == null || elements.length == 0) {
            throw new IllegalArgumentException("No geo collection found in " + src);
        }

        final List<Geospatial> geo = new ArrayList<Geospatial>();

        for (String element : elements) {
            final Map.Entry<String, String> info = getGeospatialsInfo(element);
            if (ODataConstants.ELEM_POINT.equals(info.getKey())) {
                geo.add(getPoint(info.getValue(), dimension));
            } else if (ODataConstants.ELEM_LINESTRING.equals(info.getKey())) {
                geo.add(new LineString(dimension, getPoints(info.getValue(), dimension)));
            } else if (ODataConstants.ELEM_MULTIPOINT.equals(info.getKey())) {
                geo.add(new MultiPoint(dimension, getPoints(info.getValue(), dimension)));
            } else if (ODataConstants.ELEM_MULTILINESTRING.equals(info.getKey())) {
                geo.add(new MultiLineString(dimension, getLineStrings(info.getValue(), dimension)));
            } else if (ODataConstants.ELEM_POLYGON.equals(info.getKey())) {
                geo.add(getPolygon(info.getValue(), dimension));
            } else if (ODataConstants.ELEM_MULTIPOLYGON.equals(info.getKey())) {
                geo.add(new MultiPolygon(dimension, getPolygons(info.getValue(), dimension)));
            } else if (ODataConstants.ELEM_GEOCOLLECTION.equals(info.getKey())) {
                final List<Geospatial> geospatials = new ArrayList<Geospatial>();
                if (!info.getValue().isEmpty()) {
                    for (Geospatial geospatial : getCollection(info.getValue(), dimension)) {
                        geospatials.add(geospatial);
                    }
                }
                geo.add(new GeospatialCollection(dimension, geospatials));
            }
        }

        return new GeospatialCollection(dimension, geo);
    }

    private String getCollectionAsString(final ComposedGeospatial<Geospatial> geospatials) {
        final StringBuilder textBuilder = new StringBuilder();
        for (Geospatial geospatial : geospatials) {
            if (textBuilder.length() > 0) {
                textBuilder.append(',');
            }
            textBuilder.append(getGeospatialAsString(geospatial));
        }
        return textBuilder.toString();
    }

    @SuppressWarnings("unchecked")
    private String getGeospatialAsString(final Geospatial geospatial) {
        final String name;
        final String info;
        switch (geospatial.getType()) {
            case GEOSPATIALCOLLECTION:
                name = ODataConstants.ELEM_GEOCOLLECTION;
                info = getCollectionAsString((ComposedGeospatial<Geospatial>) geospatial);
                break;
            case LINESTRING:
                name = ODataConstants.ELEM_LINESTRING;
                info = getPointsAsString((ComposedGeospatial<Point>) geospatial);
                break;
            case MULTILINESTRING:
                name = ODataConstants.ELEM_MULTILINESTRING;
                info = getLineStringsAsString((ComposedGeospatial<LineString>) geospatial);
                break;
            case MULTIPOINT:
                name = ODataConstants.ELEM_MULTIPOINT;
                info = getPointsAsString((ComposedGeospatial<Point>) geospatial);
                break;
            case MULTIPOLYGON:
                name = ODataConstants.ELEM_MULTIPOLYGON;
                info = getPolygonsAsString((ComposedGeospatial<Polygon>) geospatial);
                break;
            case POINT:
                name = ODataConstants.ELEM_POINT;
                info = ((Point) geospatial).getX() + " " + ((Point) geospatial).getY();
                break;
            case POLYGON:
                name = ODataConstants.ELEM_POLYGON;
                info = getPolygonAsString((Polygon) geospatial);
                break;
            default:
                throw new IllegalArgumentException("Invalid geospatial");
        }
        try {
            return name + ":" + URLEncoder.encode(info, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
