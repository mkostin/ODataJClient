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
package com.msopentech.odatajclient.engine.data.metadata.edm;

import com.msopentech.odatajclient.engine.data.ODataDuration;
import com.msopentech.odatajclient.engine.data.ODataTimestamp;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Geospatial;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.GeospatialCollection;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.LineString;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiLineString;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiPoint;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiPolygon;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Point;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Polygon;
import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Represent the primitive types of the Entity Data Model (EDM).
 *
 * @see http://dl.windowsazure.com/javadoc/com/microsoft/windowsazure/services/table/models/EdmType.html
 * <p>
 * For an overview of the available EDM primitive data types and names, see the <a
 * href="http://www.odata.org/developers/protocols/overview#AbstractTypeSystem">Primitive Data Types</a> section of the
 * <a href="http://www.odata.org/developers/protocols/overview">OData Protocol Overview</a>.
 * </p>
 * <p>
 * The Abstract Type System used to define the primitive types supported by OData is defined in detail in <a
 * href="http://msdn.microsoft.com/en-us/library/dd541474.aspx">[MC-CSDL] (section 2.2.1).</a>
 * </p>
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EDMSimpleType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Binary"/>
 *     &lt;enumeration value="Boolean"/>
 *     &lt;enumeration value="Byte"/>
 *     &lt;enumeration value="DateTime"/>
 *     &lt;enumeration value="DateTimeOffset"/>
 *     &lt;enumeration value="Time"/>
 *     &lt;enumeration value="Decimal"/>
 *     &lt;enumeration value="Double"/>
 *     &lt;enumeration value="Single"/>
 *     &lt;enumeration value="Geography"/>
 *     &lt;enumeration value="GeographyPoint"/>
 *     &lt;enumeration value="GeographyLineString"/>
 *     &lt;enumeration value="GeographyPolygon"/>
 *     &lt;enumeration value="GeographyMultiPoint"/>
 *     &lt;enumeration value="GeographyMultiLineString"/>
 *     &lt;enumeration value="GeographyMultiPolygon"/>
 *     &lt;enumeration value="GeographyCollection"/>
 *     &lt;enumeration value="Geometry"/>
 *     &lt;enumeration value="GeometryPoint"/>
 *     &lt;enumeration value="GeometryLineString"/>
 *     &lt;enumeration value="GeometryPolygon"/>
 *     &lt;enumeration value="GeometryMultiPoint"/>
 *     &lt;enumeration value="GeometryMultiLineString"/>
 *     &lt;enumeration value="GeometryMultiPolygon"/>
 *     &lt;enumeration value="GeometryCollection"/>
 *     &lt;enumeration value="Guid"/>
 *     &lt;enumeration value="Int16"/>
 *     &lt;enumeration value="Int32"/>
 *     &lt;enumeration value="Int64"/>
 *     &lt;enumeration value="String"/>
 *     &lt;enumeration value="SByte"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "EDMSimpleType")
@XmlEnum
public enum EdmSimpleType {

    /**
     * The absence of a value.
     */
    @XmlEnumValue("Null")
    NULL("Null", Void.class),
    /**
     * An array of bytes.
     */
    @XmlEnumValue("Binary")
    BINARY("Binary", byte[].class),
    /**
     * A Boolean value.
     */
    @XmlEnumValue("Boolean")
    BOOLEAN("Boolean", Boolean.class),
    /**
     * Unsigned 8-bit integer value.
     */
    @XmlEnumValue("Byte")
    BYTE("Byte", Integer.class),
    /**
     * A signed 8-bit integer value.
     */
    @XmlEnumValue("SByte")
    S_BYTE("SByte", Byte.class),
    /**
     * A 64-bit value expressed as Coordinated Universal Time (UTC).
     */
    @XmlEnumValue("DateTime")
    DATE_TIME("DateTime", ODataTimestamp.class, "yyyy-MM-dd'T'HH:mm:ss"),
    /**
     * Date and time as an Offset in minutes from GMT.
     */
    @XmlEnumValue("DateTimeOffset")
    DATE_TIME_OFFSET("DateTimeOffset", ODataTimestamp.class, "yyyy-MM-dd'T'HH:mm:ss"),
    /**
     * The time of day with values ranging from 0:00:00.x to 23:59:59.y, where x and y depend upon the precision.
     */
    @XmlEnumValue("Time")
    TIME("Time", ODataDuration.class),
    /**
     * Numeric values with fixed precision and scale.
     */
    @XmlEnumValue("Decimal")
    DECIMAL("Decimal", BigDecimal.class, "#.#######################"),
    /**
     * A floating point number with 7 digits precision.
     */
    @XmlEnumValue("Single")
    SINGLE("Single", Float.class, "#.#######E0"),
    /**
     * A 64-bit double-precision floating point value.
     */
    @XmlEnumValue("Double")
    DOUBLE("Double", Double.class, "#.#######################E0"),
    @XmlEnumValue("Geography")
    GEOGRAPHY("Geography", Geospatial.class),
    @XmlEnumValue("GeographyPoint")
    GEOGRAPHY_POINT("GeographyPoint", Point.class),
    @XmlEnumValue("GeographyLineString")
    GEOGRAPHY_LINE_STRING("GeographyLineString", LineString.class),
    @XmlEnumValue("GeographyPolygon")
    GEOGRAPHY_POLYGON("GeographyPolygon", Polygon.class),
    @XmlEnumValue("GeographyMultiPoint")
    GEOGRAPHY_MULTI_POINT("GeographyMultiPoint", MultiPoint.class),
    @XmlEnumValue("GeographyMultiLineString")
    GEOGRAPHY_MULTI_LINE_STRING("GeographyMultiLineString", MultiLineString.class),
    @XmlEnumValue("GeographyMultiPolygon")
    GEOGRAPHY_MULTI_POLYGON("GeographyMultiPolygon", MultiPolygon.class),
    @XmlEnumValue("GeographyCollection")
    GEOGRAPHY_COLLECTION("GeographyCollection", GeospatialCollection.class),
    @XmlEnumValue("Geometry")
    GEOMETRY("Geometry", Geospatial.class),
    @XmlEnumValue("GeometryPoint")
    GEOMETRY_POINT("GeometryPoint", Point.class),
    @XmlEnumValue("GeometryLineString")
    GEOMETRY_LINE_STRING("GeometryLineString", LineString.class),
    @XmlEnumValue("GeometryPolygon")
    GEOMETRY_POLYGON("GeometryPolygon", Polygon.class),
    @XmlEnumValue("GeometryMultiPoint")
    GEOMETRY_MULTI_POINT("GeometryMultiPoint", MultiPoint.class),
    @XmlEnumValue("GeometryMultiLineString")
    GEOMETRY_MULTI_LINE_STRING("GeometryMultiLineString", MultiLineString.class),
    @XmlEnumValue("GeometryMultiPolygon")
    GEOMETRY_MULTI_POLYGON("GeometryMultiPolygon", MultiPolygon.class),
    @XmlEnumValue("GeometryCollection")
    GEOMETRY_COLLECTION("GeometryCollection", GeospatialCollection.class),
    /**
     * A 128-bit globally unique identifier.
     */
    @XmlEnumValue("Guid")
    GUID("Guid", UUID.class),
    /**
     * A 16-bit integer value.
     */
    @XmlEnumValue("Int16")
    INT_16("Int16", Short.class),
    /**
     * A 32-bit integer value.
     */
    @XmlEnumValue("Int32")
    INT_32("Int32", Integer.class),
    /**
     * A 64-bit integer value.
     */
    @XmlEnumValue("Int64")
    INT_64("Int64", Long.class),
    /**
     * A UTF-16-encoded value.
     * String values may be up to 64 KB in size.
     */
    @XmlEnumValue("String")
    STRING("String", String.class),
    /**
     * Resource stream (for media entities).
     */
    @XmlEnumValue("Stream")
    STREAM("Stream", URI.class);

    private final String value;

    private final Class<?> clazz;

    private final String pattern;

    /**
     * Constructor.
     *
     * @param value value.
     * @param clazz type.
     */
    EdmSimpleType(final String value, final Class<?> clazz) {
        this.value = value;
        this.clazz = clazz;
        this.pattern = null;
    }

    /**
     * Constructor.
     *
     * @param value value.
     * @param clazz type.
     * @param pattern pattern.
     */
    EdmSimpleType(final String value, final Class<?> clazz, final String pattern) {
        this.value = value;
        this.clazz = clazz;
        this.pattern = pattern;
    }

    /**
     * Gets value.
     *
     * @return value.
     */
    public String value() {
        return value;
    }

    /**
     * Gets pattern.
     *
     * @return pattern.
     */
    public String pattern() {
        return pattern;
    }

    /**
     * Gets corresponding java type.
     *
     * @return java type.
     */
    public Class<?> javaType() {
        return this.clazz;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String toString() {
        return namespace() + "." + value;
    }

    public boolean isGeospatial() {
        return name().startsWith("GEO");
    }

    public static boolean isGeospatial(final String type) {
        return type != null && type.startsWith(namespace() + ".Geo");
    }

    /**
     * Gets <tt>EdmSimpleType</tt> from string.
     *
     * @param value string value type.
     * @return <tt>EdmSimpleType</tt> object.
     */
    public static EdmSimpleType fromValue(final String value) {
        final String noNsValue = value.substring(4);
        for (EdmSimpleType edmSimpleType : EdmSimpleType.values()) {
            if (edmSimpleType.value.equals(noNsValue)) {
                return edmSimpleType;
            }
        }
        throw new IllegalArgumentException(value);
    }

    /**
     * Gets namespace.
     *
     * @return namespace.
     */
    public static String namespace() {
        return "Edm";
    }
}
