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
 * OData entity property.
 */
public class ODataProperty implements Serializable {

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
     * Value type.
     */
    final EdmSimpleType type;

    /**
     * Constructor.
     *
     * @param name property name.
     * @param value property value.
     * @param type property value type.
     */
    public ODataProperty(final String name, final ODataValue value, final EdmSimpleType type) {
        this.name = name;
        this.value = value;
        this.type = type;
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
}
