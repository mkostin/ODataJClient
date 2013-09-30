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

import com.msopentech.odatajclient.engine.data.ODataDuration;
import com.msopentech.odatajclient.engine.data.ODataTimestamp;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer;
import com.msopentech.odatajclient.engine.data.metadata.edm.FunctionImport;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.UUID;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * URI utilities.
 */
public final class URIUtils {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(URIUtils.class);

    private URIUtils() {
        // Empty private constructor for static utility classes
    }

    /**
     * Build URI starting from the given base and href.
     * <p>
     * If href is absolute or base is null then base will be ignored.
     *
     * @param base URI prefix.
     * @param href URI suffix.
     * @return built URI.
     */
    public static URI getURI(final String base, final String href) {
        if (href == null) {
            throw new IllegalArgumentException("Null link provided");
        }

        URI uri = URI.create(href);

        if (!uri.isAbsolute() && base != null) {
            uri = new ODataURIBuilder(base).appendEntityTypeSegment(href).build();
        }

        return uri.normalize();
    }

    /**
     * Build URI starting from the given base and href.
     * <p>
     * If href is absolute or base is null then base will be ignored.
     *
     * @param base URI prefix.
     * @param href URI suffix.
     * @return built URI.
     */
    public static URI getURI(final URI base, final URI href) {
        if (href == null) {
            throw new IllegalArgumentException("Null link provided");
        }
        return getURI(base, href.toASCIIString());
    }

    /**
     * Build URI starting from the given base and href.
     * <p>
     * If href is absolute or base is null then base will be ignored.
     *
     * @param base URI prefix.
     * @param href URI suffix.
     * @return built URI.
     */
    public static URI getURI(final URI base, final String href) {
        if (href == null) {
            throw new IllegalArgumentException("Null link provided");
        }

        URI uri = URI.create(href);

        if (!uri.isAbsolute() && base != null) {
            uri = new ODataURIBuilder(base.toString()).appendEntityTypeSegment(href).build();
        }

        return uri.normalize();
    }

    /**
     * Gets function import URI segment.
     *
     * @param entityContainer entity container.
     * @param functionImport function import.
     * @return URI segment.
     */
    public static String rootFunctionImportURISegment(
            final EntityContainer entityContainer, final FunctionImport functionImport) {

        final StringBuilder result = new StringBuilder();
        if (!entityContainer.isDefaultEntityContainer()) {
            result.append(entityContainer.getName()).append('.');
        }
        result.append(functionImport.getName());

        return result.toString();
    }

    /**
     * Turns primitive values into their respective URI representation.
     *
     * @param obj primitive value
     * @return URI representation
     */
    public static String escape(final Object obj) {
        String value;

        try {
            value = (obj instanceof UUID)
                    ? "guid'" + obj.toString() + "'"
                    : (obj instanceof byte[])
                    ? "X'" + Hex.encodeHexString((byte[]) obj) + "'"
                    : ((obj instanceof ODataTimestamp) && ((ODataTimestamp) obj).getTimezone() == null)
                    ? "datetime'" + URLEncoder.encode(((ODataTimestamp) obj).toString(), ODataConstants.UTF8) + "'"
                    : ((obj instanceof ODataTimestamp) && ((ODataTimestamp) obj).getTimezone() != null)
                    ? "datetimeoffset'" + URLEncoder.encode(((ODataTimestamp) obj).toString(), ODataConstants.UTF8)
                    + "'"
                    : (obj instanceof ODataDuration)
                    ? "time'" + ((ODataDuration) obj).toString() + "'"
                    : (obj instanceof BigDecimal)
                    ? new DecimalFormat(EdmSimpleType.Decimal.pattern()).format((BigDecimal) obj) + "M"
                    : (obj instanceof Double)
                    ? new DecimalFormat(EdmSimpleType.Double.pattern()).format((Double) obj) + "D"
                    : (obj instanceof Float)
                    ? new DecimalFormat(EdmSimpleType.Single.pattern()).format((Float) obj) + "f"
                    : (obj instanceof Long)
                    ? ((Long) obj).toString() + "L"
                    : (obj instanceof String)
                    ? "'" + URLEncoder.encode((String) obj, ODataConstants.UTF8) + "'"
                    : obj.toString();
        } catch (Exception e) {
            LOG.warn("While generating key segment for '{}', using toString()", obj, e);
            value = obj.toString();
        }

        return value;
    }
}
