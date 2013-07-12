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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Configuration wrapper.
 */
public final class Configuration {

    public static final String DEFAULT_FORMAT = "format";

    public static final String USE_XHTTP_METHOD = "USE_XHTTP_METHOD";

    private static final Map<String, String> conf = new HashMap<String, String>();

    private static ExecutorService EXECUTOR = Executors.newFixedThreadPool(10);

    private Configuration() {
        // Empty private constructor for static utility classes
    }

    /**
     * Get given configuration property.
     *
     * @param key key value of the property to be retrieved.
     * @param defaultValue default value to be used in case of the given key doesn't exist.
     * @return property value if exists; default value if does not exist.
     */
    public static String getProperty(final String key, final String defaultValue) {
        return conf.containsKey(key) ? conf.get(key) : defaultValue;
    }

    public static String setProperty(final String key, final String value) {
        return conf.put(key, value);
    }

    /**
     * Get configured OData format.
     * If this configuration parameter doesn't exist the JSON_FULL_METADATA format will be used as default.
     *
     * @return configured OData format if exists; JSON_FULL_METADATA format otherwise.
     * @see ODataPubFormat#JSON_FULL_METADATA
     */
    public static ODataPubFormat getDefaultFormat() {
        return ODataPubFormat.valueOf(getProperty(DEFAULT_FORMAT, "JSON_FULL_METADATA"));
    }

    public static void setDefaultFormat(final ODataPubFormat format) {
        setProperty(DEFAULT_FORMAT, format.name());
    }

    /**
     * Gets whether <tt>PUT</tt>, <tt>MERGE</tt>, <tt>PATCH</tt>, <tt>DELETE</tt> HTTP methods need to be translated to
     * <tt>POST</tt> with additional <tt>X-HTTTP-Method</tt> header.
     *
     * @return whether <tt>X-HTTTP-Method</tt> header is to be used
     */
    public static boolean isUseXHTTPMethod() {
        return Boolean.valueOf(getProperty(USE_XHTTP_METHOD, "false"));
    }

    public static void setUseXHTTPMethod(final boolean value) {
        setProperty(USE_XHTTP_METHOD, Boolean.toString(value));
    }

    public static ExecutorService getExecutor() {
        return EXECUTOR;
    }

    public static void setExecutor(final ExecutorService executorService) {
        EXECUTOR = executorService;
    }
}
