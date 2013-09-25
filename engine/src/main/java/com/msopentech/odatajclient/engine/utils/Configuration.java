/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.engine.utils;

import com.msopentech.odatajclient.engine.client.http.DefaultHttpClientFactory;
import com.msopentech.odatajclient.engine.client.http.DefaultHttpUriRequestFactory;
import com.msopentech.odatajclient.engine.client.http.HttpClientFactory;
import com.msopentech.odatajclient.engine.client.http.HttpUriRequestFactory;
import com.msopentech.odatajclient.engine.format.ODataFormat;
import com.msopentech.odatajclient.engine.format.ODataMediaFormat;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.format.ODataValueFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Configuration wrapper.
 */
public final class Configuration {

    private static final String DEFAULT_PUB_FORMAT = "pubFormat";

    private static final String DEFAULT_VALUE_FORMAT = "valueFormat";

    private static final String DEFAULT_MEDIA_FORMAT = "valueFormat";

    private static final String HTTP_CLIENT_FACTORY = "httpClientFactory";

    private static final String HTTP_URI_REQUEST_FACTORY = "httpUriRequestFactory";

    private static final String USE_XHTTP_METHOD = "useHTTPMethod";

    private static final String KEY_AS_SEGMENT = "keyAsSegment";

    private static final String GZIP_COMPRESSION = "gzipCompression";

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
    private static Object getProperty(final String key, final Object defaultValue) {
        return CONF.containsKey(key) ? CONF.get(key) : defaultValue;
    }

    /**
     * Sets new configuration property.
     *
     * @param key configuration property key.
     * @param value configuration property value.
     * @return given value.
     */
    private static Object setProperty(final String key, final Object value) {
        return CONF.put(key, value);
    }

    /**
     * Gets the configured OData format for AtomPub exchanges.
     * If this configuration parameter doesn't exist the JSON_FULL_METADATA format will be used as default.
     *
     * @return configured OData format for AtomPub if specified; JSON_FULL_METADATA format otherwise.
     * @see ODataPubFormat#JSON_FULL_METADATA
     */
    public static ODataPubFormat getDefaultPubFormat() {
        return ODataPubFormat.valueOf(
                getProperty(DEFAULT_PUB_FORMAT, ODataPubFormat.JSON_FULL_METADATA.name()).toString());
    }

    /**
     * Sets the default OData format for AtomPub exchanges.
     *
     * @param format default format.
     */
    public static void setDefaultPubFormat(final ODataPubFormat format) {
        setProperty(DEFAULT_PUB_FORMAT, format.name());
    }

    /**
     * Gets the configured OData format.
     * This value depends on what is returned from <tt>getDefaultPubFormat()</tt>.
     *
     * @return configured OData format
     * @see #getDefaultPubFormat()
     */
    public static ODataFormat getDefaultFormat() {
        ODataFormat format;

        switch (getDefaultPubFormat()) {
            case ATOM:
                format = ODataFormat.XML;
                break;

            case JSON_FULL_METADATA:
                format = ODataFormat.JSON_FULL_METADATA;
                break;

            case JSON_NO_METADATA:
                format = ODataFormat.JSON_NO_METADATA;
                break;

            case JSON:
            default:
                format = ODataFormat.JSON;
        }

        return format;
    }

    /**
     * Gets the configured OData value format.
     * If this configuration parameter doesn't exist the TEXT format will be used as default.
     *
     * @return configured OData value format if specified; TEXT format otherwise.
     * @see ODataValueFormat#TEXT
     */
    public static ODataValueFormat getDefaultValueFormat() {
        return ODataValueFormat.valueOf(
                getProperty(DEFAULT_VALUE_FORMAT, ODataValueFormat.TEXT.name()).toString());
    }

    /**
     * Sets the default OData value format.
     *
     * @param format default format.
     */
    public static void setDefaultValueFormat(final ODataValueFormat format) {
        setProperty(DEFAULT_VALUE_FORMAT, format.name());
    }

    /**
     * Gets the configured OData media format.
     * If this configuration parameter doesn't exist the APPLICATION_OCTET_STREAM format will be used as default.
     *
     * @return configured OData media format if specified; APPLICATION_OCTET_STREAM format otherwise.
     * @see ODataMediaFormat#WILDCARD
     */
    public static ODataMediaFormat getDefaultMediaFormat() {
        return ODataMediaFormat.valueOf(
                getProperty(DEFAULT_VALUE_FORMAT, ODataMediaFormat.APPLICATION_OCTET_STREAM.name()).toString());
    }

    /**
     * Sets the default OData media format.
     *
     * @param format default format.
     */
    public static void setDefaultMediaFormat(final ODataMediaFormat format) {
        setProperty(DEFAULT_MEDIA_FORMAT, format.name());
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
     * Checks whether Gzip compression (e.g. support for <tt>Accept-Encoding: gzip</tt> and
     * <tt>Content-Encoding: gzip</tt> HTTP headers) is enabled.
     *
     * @return whether HTTP Gzip compression is enabled
     */
    public static boolean isGzipCompression() {
        return (Boolean) getProperty(GZIP_COMPRESSION, false);
    }

    /**
     * Sets Gzip compression (e.g. support for <tt>Accept-Encoding: gzip</tt> and
     * <tt>Content-Encoding: gzip</tt> HTTP headers) enabled or disabled.
     */
    public static void setGzipCompression(final boolean value) {
        setProperty(GZIP_COMPRESSION, value);
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
