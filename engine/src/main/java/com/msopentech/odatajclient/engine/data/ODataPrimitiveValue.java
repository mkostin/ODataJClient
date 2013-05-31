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

import com.msopentech.odatajclient.engine.types.EdmSimpleType;
import java.io.Serializable;

/**
 * OData primitive property value.
 */
public class ODataPrimitiveValue extends ODataValue {

    /**
     * Actual value.
     */
    final Serializable value;

    /**
     * Value type.
     */
    final EdmSimpleType typeName;

    /**
     * Constructor.
     *
     * @param value actual value.
     * @param typeName primitive value type.
     */
    public ODataPrimitiveValue(final Serializable value, final EdmSimpleType typeName) {
        this.value = value;
        this.typeName = typeName;
    }

    /**
     * Constructor.
     *
     * @param value actual value.
     * @param typeName primitive value type.
     */
    public ODataPrimitiveValue(final Serializable value, final String typeName) {
        this.value = value;
        this.typeName = EdmSimpleType.valueOf(typeName);
    }

    /**
     * Gets type name.
     *
     * @return type name.
     */
    public String getTypeName() {
        return typeName.name();
    }
}
