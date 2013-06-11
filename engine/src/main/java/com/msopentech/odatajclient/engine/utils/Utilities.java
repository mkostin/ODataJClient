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
package com.msopentech.odatajclient.engine.utils;

import com.msopentech.odatajclient.engine.types.EdmSimpleType;
import java.io.Serializable;

public class Utilities {

    /**
     * Convert a string value into the specified object instance.
     *
     * @param value value to be converted.
     * @param type conversion type.
     * @return converted value object.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T convert(final String value, final EdmSimpleType type) {
        return (T) new String();
    }

    /**
     * Convert a generic object value into the relative String representation
     *
     * @param <T> actual value type.
     * @param value value to be converted.
     * @param type current value type.
     * @return String representation of the value.
     */
    public static <T extends Serializable> String convert(final T value, final EdmSimpleType type) {
        return new String();
    }
}
