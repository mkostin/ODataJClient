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

import com.msopentech.odatajclient.engine.client.http.DefaultHttpClientFactory;
import com.msopentech.odatajclient.engine.client.http.DefaultHttpUriRequestFactory;
import com.msopentech.odatajclient.engine.client.http.HttpClientFactory;
import com.msopentech.odatajclient.engine.client.http.HttpUriRequestFactory;
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

    public static final String HTTP_CLIENT_FACTORY = "httpClientFactory";

    public static final String HTTP_URI_REQUEST_FACTORY = "httpUriRequestFactory";

    public static final String USE_XHTTP_METHOD = "useHTTPMethod";

    public static final String KEY_AS_SEGMENT = "keyAsSegment";

    private static final Map<String, Object> CONF = new HashMap<String, Object>();

    private static ExecutorService EXECUTOR = Executors.newFixedThreadPool(10);

    private Configuration() {
        // Empty private constructor for static utility classes
    }

    /**
     * Gets given configuration property.
     *
     * @param key key value of the property to be retrieved.
     * @param defaultValue default value to be used in case of the given key doesn't exist.
     * @return property value if exists; default value if does not exist.
     */
    public static Object getProperty(final String key, final Object defaultValue) {
        return CONF.containsKey(key) ? CONF.get(key) : defaultValue;
    }

    /**
     * Sets new configuration property.
     *
     * @param key configuration property key.
     * @param value configuration property value.
     * @return given value.
     */
    public static Object setProperty(final String key, final Object value) {
        return CONF.put(key, value);
    }

    /**
     * Gets configured OData format.
     * If this configuration parameter doesn't exist the JSON_FULL_METADATA format will be used as default.
     *
     * @return configured OData format if exists; JSON_FULL_METADATA format otherwise.
     * @see ODataPubFormat#JSON_FULL_METADATA
     */
    public static ODataPubFormat getDefaultPubFormat() {
        return ODataPubFormat.valueOf(getProperty(DEFAULT_FORMAT, "JSON_FULL_METADATA").toString());
    }

    /**
     * Sets default format.
     *
     * @param format default format.
     */
    public static void setDefaultPubFormat(final ODataPubFormat format) {
        setProperty(DEFAULT_FORMAT, format.name());
    }

    /**
     * Gets the HttpClient factory to be used for executing requests.
     *
     * @return provided implementation (if configured via <tt>setHttpClientFactory</tt> or default.
     * @see DefaultHttpClientFactory
     */
    public static HttpClientFactory getHttpClientFactory() {
        return (HttpClientFactory) getProperty(HTTP_CLIENT_FACTORY, new DefaultHttpClientFactory());
    }

    /**
     * Sets the HttpClient factory to be used for executing requests.
     *
     * @param factory implementation of <tt>HttpClientFactory</tt>.
     * @see HttpClientFactory
     */
    public static void setHttpClientFactory(final HttpClientFactory factory) {
        setProperty(HTTP_CLIENT_FACTORY, factory);
    }

    /**
     * Gets the HttpUriRequest factory for generating requests to be executed.
     *
     * @return provided implementation (if configured via <tt>setHttpUriRequestFactory</tt> or default.
     * @see DefaultHttpUriRequestFactory
     */
    public static HttpUriRequestFactory getHttpUriRequestFactory() {
        return (HttpUriRequestFactory) getProperty(HTTP_URI_REQUEST_FACTORY, new DefaultHttpUriRequestFactory());
    }

    /**
     * Sets the HttpUriRequest factory generating requests to be executed.
     *
     * @param factory implementation of <tt>HttpUriRequestFactory</tt>.
     * @see HttpUriRequestFactory
     */
    public static void setHttpUriRequestFactory(final HttpUriRequestFactory factory) {
        setProperty(HTTP_URI_REQUEST_FACTORY, factory);
    }

    /**
     * Gets whether <tt>PUT</tt>, <tt>MERGE</tt>, <tt>PATCH</tt>, <tt>DELETE</tt> HTTP methods need to be translated to
     * <tt>POST</tt> with additional <tt>X-HTTTP-Method</tt> header.
     *
     * @return whether <tt>X-HTTTP-Method</tt> header is to be used
     */
    public static boolean isUseXHTTPMethod() {
        return (Boolean) getProperty(USE_XHTTP_METHOD, false);
    }

    /**
     * Sets whether <tt>PUT</tt>, <tt>MERGE</tt>, <tt>PATCH</tt>, <tt>DELETE</tt> HTTP methods need to be translated to
     * <tt>POST</tt> with additional <tt>X-HTTTP-Method</tt> header.
     *
     * @param value 'TRUE' to use tunneling.
     */
    public static void setUseXHTTPMethod(final boolean value) {
        setProperty(USE_XHTTP_METHOD, value);
    }

    public static boolean isKeyAsSegment() {
        return (Boolean) getProperty(KEY_AS_SEGMENT, false);
    }

    public static void setKeyAsSegment(final boolean value) {
        setProperty(KEY_AS_SEGMENT, value);
    }

    /**
     * Retrieves request executor service.
     *
     * @return request executor service.
     */
    public static ExecutorService getExecutor() {
        return EXECUTOR;
    }

    /**
     * Sets request executor service.
     *
     * @param executorService new executor services.
     */
    public static void setExecutor(final ExecutorService executorService) {
        EXECUTOR = executorService;
    }
}
