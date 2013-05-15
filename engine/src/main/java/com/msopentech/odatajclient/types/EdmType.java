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
package com.msopentech.odatajclient.types;

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
public enum EdmType {

    /**
     * An array of bytes up to 64 KB in size.
     */
    Binary(Byte[].class),
    /**
     * A Boolean value.
     */
    Boolean(Boolean.class),
    /**
     * A 64-bit value expressed as Coordinated Universal Time (UTC).
     * The supported range begins from 12:00 midnight, January 1, 1601 A.D. (C.E.), UTC.
     * The range ends at December 31, 9999.
     */
    DateTime(Date.class),
    /**
     * A 64-bit double-precision floating point value.
     */
    Double(Double.class),
    /**
     * A 128-bit globally unique identifier.
     */
    Guid(UUID.class),
    /**
     * A 32-bit integer value.
     */
    Int32(Integer.class),
    /**
     * A 64-bit integer value.
     */
    Int64(Long.class),
    /**
     * A UTF-16-encoded value.
     * String values may be up to 64 KB in size.
     */
    String(String.class);

    private final Class<?> clazz;

    private EdmType(final Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getJavaClass() {
        return this.clazz;
    }

    @Override
    public String toString() {
        return "Edm." + name();
    }

    public static EdmType fromName(final String name) {
        EdmType match = null;
        for (EdmType type : values()) {
            if (type.toString().equals(name)) {
                match = type;
            }
        }
        return match;
    }
}
