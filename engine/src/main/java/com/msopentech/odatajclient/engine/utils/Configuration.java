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

import com.msopentech.odatajclient.engine.format.ODataPubFormat;

/**
 * Configuration wrapper.
 */
public final class Configuration {

    public static final String FORMAT = "format";

    public static final String REQ_EXEC_POOL_SIZE = "REQ_EXEC_POOL_SIZE";

    private Configuration() {
        // Empty private constructor for static utility classes
    }

    /**
     * Get given configuration property.
     *
     * @param key key value of the property to be retrieved.
     * @param def default value to be used in case of the given key doesn't exist.
     * @return property value if exists; default value if does not exist.
     */
    public static String getProperty(final String key, final String def) {
        return def;
    }

    /**
     * Get configured OData format.
     * If this configuration parameter doesn't exist the JSON_FULL_METADATA format will be used as default.
     *
     * @return configured OData format if exists; JSON_FULL_METADATA format otherwise.
     * @see ODataPubFormat#JSON_FULL_METADATA
     */
    public static ODataPubFormat getFormat() {
        return ODataPubFormat.valueOf(getProperty(FORMAT, "JSON_FULL_METADATA"));
    }
}
