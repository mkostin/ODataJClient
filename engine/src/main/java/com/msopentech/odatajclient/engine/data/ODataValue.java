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
 * Abstract representation of an OData entity property value.
 */
public abstract class ODataValue implements Serializable {

    private static final long serialVersionUID = 7445422004232581877L;

    public boolean isPrimitive() {
        return (this instanceof ODataPrimitiveValue);
    }

    public ODataPrimitiveValue asPrimitive() {
        return isPrimitive() ? (ODataPrimitiveValue) this : null;
    }

    public boolean isComplex() {
        return (this instanceof ODataComplexValue);
    }

    public ODataComplexValue asComplex() {
        return isComplex() ? (ODataComplexValue) this : null;
    }

    public boolean isCollection() {
        return (this instanceof ODataCollectionValue);
    }

    public ODataCollectionValue asCollection() {
        return isCollection() ? (ODataCollectionValue) this : null;
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
