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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * OData complex property value.
 */
public class ODataComplexValue extends ODataPropertyValue implements Iterable<Map.Entry<String, ODataPrimitiveValue>> {

    final Map<String, ODataPrimitiveValue> values = new HashMap<String, ODataPrimitiveValue>();

    /**
     * Adds field value to the complex type.
     *
     * @param name name of the field to be added.
     * @param value value of the field to be added.
     */
    public void add(final String name, final ODataPrimitiveValue value) {
    }

    /**
     * Gets field value.
     *
     * @param name field name.
     * @return field value.
     */
    public ODataPrimitiveValue get(final String name) {
        return values.get(name);
    }

    /**
     * Complex property field iterator.
     *
     * @return field iterator.
     */
    @Override
    public Iterator<Map.Entry<String, ODataPrimitiveValue>> iterator() {
        return values.entrySet().iterator();
    }
}
