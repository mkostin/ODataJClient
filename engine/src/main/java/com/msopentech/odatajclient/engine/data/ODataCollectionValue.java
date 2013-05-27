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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * OData collection property value.
 */
public class ODataCollectionValue extends ODataValue implements Iterable<ODataValue> {

    final Set<ODataValue> values = new HashSet<ODataValue>();

    /**
     * Adds value to the collection.
     *
     * @param value value to be added.
     */
    public void add(final ODataPrimitiveValue value) {
        values.add(value);
    }

    /**
     * Value iterator.
     *
     * @return value iterator.
     */
    @Override
    public Iterator<ODataValue> iterator() {
        return values.iterator();
    }
}
