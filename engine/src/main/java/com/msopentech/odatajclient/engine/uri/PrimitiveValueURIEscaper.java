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
package com.msopentech.odatajclient.engine.uri;

import com.msopentech.odatajclient.engine.data.ODataDuration;
import com.msopentech.odatajclient.engine.data.ODataTimestamp;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.UUID;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for turning primitive values into their respective URI representation.
 */
public final class PrimitiveValueURIEscaper {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(PrimitiveValueURIEscaper.class);

    private PrimitiveValueURIEscaper() {
        // Empty private constructor for static utility classes
    }

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
                    ? new DecimalFormat(EdmSimpleType.DECIMAL.pattern()).format((BigDecimal) obj) + "M"
                    : (obj instanceof Double)
                    ? new DecimalFormat(EdmSimpleType.DOUBLE.pattern()).format((Double) obj) + "D"
                    : (obj instanceof Float)
                    ? new DecimalFormat(EdmSimpleType.SINGLE.pattern()).format((Float) obj) + "f"
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
