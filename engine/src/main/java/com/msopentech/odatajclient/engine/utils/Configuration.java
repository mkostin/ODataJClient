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

import com.msopentech.odatajclient.engine.types.ODataPubFormat;

/**
 * Configuration wrapper.
 */
public class Configuration {

    /**
     * Get given configuration property.
     *
     * @param key key value of the property to be retrieved.
     * @param def default value to be used in case of the given key doesn't exist.
     * @return property value if exists; default value if does not exist.
     */
    public String getProperty(final String key, final String def) {
        return def;
    }

    /**
     * Get configured OData format.
     * If this configuration parameter doesn't exist the JSON format will be used as default.
     *
     * @return configured OData format if exists; JSON format otherwise.
     */
    public ODataPubFormat getFormat() {
        return ODataPubFormat.valueOf(getProperty("format", "JSON"));
    }
}
