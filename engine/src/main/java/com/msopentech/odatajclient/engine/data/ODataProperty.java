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

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * OData entity property.
 */
public class ODataProperty implements Serializable {

    public enum PropertyType {

        PRIMITIVE,
        COLLECTION,
        COMPLEX,
        EMPTY

    }

    private static final long serialVersionUID = 926939448778950450L;

    /**
     * Property name.
     */
    final String name;

    /**
     * Property value.
     */
    final ODataValue value;

    /**
     * Constructor.
     *
     * @param name property name.
     * @param value property value.
     */
    public ODataProperty(final String name, final ODataValue value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns property name.
     *
     * @return property name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns property value.
     *
     * @return property value.
     */
    public ODataValue getValue() {
        return value;
    }

    public boolean hasNullValue() {
        return this.value == null;
    }

    public boolean hasPrimitiveValue() {
        return !hasNullValue() && this.value.isPrimitive();
    }

    public ODataPrimitiveValue getPrimitiveValue() {
        return hasPrimitiveValue() ? this.value.asPrimitive() : null;
    }

    public boolean hasComplexValue() {
        return !hasNullValue() && this.value.isComplex();
    }

    public ODataComplexValue getComplexValue() {
        return hasComplexValue() ? this.value.asComplex() : null;
    }

    public boolean hasCollectionValue() {
        return !hasNullValue() && this.value.isCollection();
    }

    public ODataCollectionValue getCollectionValue() {
        return hasCollectionValue() ? this.value.asCollection() : null;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
