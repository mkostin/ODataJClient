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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for EDMSimpleType.
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
public enum EDMSimpleType {

    @XmlEnumValue("Binary")
    BINARY("Binary"),
    @XmlEnumValue("Boolean")
    BOOLEAN("Boolean"),
    @XmlEnumValue("Byte")
    BYTE("Byte"),
    @XmlEnumValue("DateTime")
    DATE_TIME("DateTime"),
    @XmlEnumValue("DateTimeOffset")
    DATE_TIME_OFFSET("DateTimeOffset"),
    @XmlEnumValue("Time")
    TIME("Time"),
    @XmlEnumValue("Decimal")
    DECIMAL("Decimal"),
    @XmlEnumValue("Double")
    DOUBLE("Double"),
    @XmlEnumValue("Single")
    SINGLE("Single"),
    @XmlEnumValue("Geography")
    GEOGRAPHY("Geography"),
    @XmlEnumValue("GeographyPoint")
    GEOGRAPHY_POINT("GeographyPoint"),
    @XmlEnumValue("GeographyLineString")
    GEOGRAPHY_LINE_STRING("GeographyLineString"),
    @XmlEnumValue("GeographyPolygon")
    GEOGRAPHY_POLYGON("GeographyPolygon"),
    @XmlEnumValue("GeographyMultiPoint")
    GEOGRAPHY_MULTI_POINT("GeographyMultiPoint"),
    @XmlEnumValue("GeographyMultiLineString")
    GEOGRAPHY_MULTI_LINE_STRING("GeographyMultiLineString"),
    @XmlEnumValue("GeographyMultiPolygon")
    GEOGRAPHY_MULTI_POLYGON("GeographyMultiPolygon"),
    @XmlEnumValue("GeographyCollection")
    GEOGRAPHY_COLLECTION("GeographyCollection"),
    @XmlEnumValue("Geometry")
    GEOMETRY("Geometry"),
    @XmlEnumValue("GeometryPoint")
    GEOMETRY_POINT("GeometryPoint"),
    @XmlEnumValue("GeometryLineString")
    GEOMETRY_LINE_STRING("GeometryLineString"),
    @XmlEnumValue("GeometryPolygon")
    GEOMETRY_POLYGON("GeometryPolygon"),
    @XmlEnumValue("GeometryMultiPoint")
    GEOMETRY_MULTI_POINT("GeometryMultiPoint"),
    @XmlEnumValue("GeometryMultiLineString")
    GEOMETRY_MULTI_LINE_STRING("GeometryMultiLineString"),
    @XmlEnumValue("GeometryMultiPolygon")
    GEOMETRY_MULTI_POLYGON("GeometryMultiPolygon"),
    @XmlEnumValue("GeometryCollection")
    GEOMETRY_COLLECTION("GeometryCollection"),
    @XmlEnumValue("Guid")
    GUID("Guid"),
    @XmlEnumValue("Int16")
    INT_16("Int16"),
    @XmlEnumValue("Int32")
    INT_32("Int32"),
    @XmlEnumValue("Int64")
    INT_64("Int64"),
    @XmlEnumValue("String")
    STRING("String"),
    @XmlEnumValue("SByte")
    S_BYTE("SByte");

    private final String value;

    EDMSimpleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EDMSimpleType fromValue(String v) {
        for (EDMSimpleType c : EDMSimpleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
