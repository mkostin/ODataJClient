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
package com.msopentech.odatajclient.proxy.api.types;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

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
 */
public enum EdmSimpleType {

    /**
     * The absence of a value.
     */
    Null(void.class),
    /**
     * An array of bytes.
     */
    Binary(Byte[].class),
    /**
     * A Boolean value.
     */
    Boolean(Boolean.class),
    /**
     * Unsigned 8-bit integer value.
     */
    Byte(Byte.class),
    /**
     * A 64-bit value expressed as Coordinated Universal Time (UTC).
     */
    DateTime(Date.class, "yyyy-mm-ddThh:mm[:ss[.fffffff]]"),
    /**
     * Numeric values with fixed precision and scale.
     */
    Decimal(Float.class, "[0-9]+.[0-9]+M|m"),
    /**
     * A 64-bit double-precision floating point value.
     */
    Double(Double.class),
    /**
     * A 128-bit globally unique identifier.
     */
    Guid(UUID.class),
    /**
     * A 16-bit integer value.
     */
    Int16(Short.class),
    /**
     * A 32-bit integer value.
     */
    Int32(Integer.class),
    /**
     * A 64-bit integer value.
     */
    Int64(Long.class),
    /**
     * A signed 8-bit integer value.
     */
    SByte(Byte.class),
    /**
     * A floating point number with 7 digits precision.
     */
    Single(Float.class, "[0-9]+.[0-9]+f"),
    /**
     * A UTF-16-encoded value.
     * String values may be up to 64 KB in size.
     */
    String(String.class),
    /**
     * The time of day with values ranging from 0:00:00.x to 23:59:59.y, where x and y depend upon the precision.
     */
    Time(Date.class, "hh:mm[:ss[.fffffff]]"),
    /**
     * Date and time as an Offset in minutes from GMT.
     */
    DateTimeOffset(Date.class, "yyyy-mm-ddThh:mm[:ss[.fffffff]]Z"),
    /**
     * Resource stream (for media entities).
     */
    Stream(InputStream.class);

    private final Class<?> clazz;

    private final String pattern;

    private EdmSimpleType(final Class<?> clazz) {
        this.clazz = clazz;
        this.pattern = null;
    }

    private EdmSimpleType(final Class<?> clazz, final String pattern) {
        this.clazz = clazz;
        this.pattern = pattern;
    }

    public Class<?> getJavaType() {
        return this.clazz;
    }

    public String getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return "Edm." + name();
    }

    public static EdmSimpleType fromName(final String name) {
        EdmSimpleType match = null;
        for (EdmSimpleType type : values()) {
            if (type.toString().equals(name)) {
                match = type;
            }
        }
        return match;
    }
}
