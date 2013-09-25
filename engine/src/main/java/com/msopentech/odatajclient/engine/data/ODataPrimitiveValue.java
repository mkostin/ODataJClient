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
package com.msopentech.odatajclient.engine.data;

import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;
import javax.xml.datatype.Duration;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * OData primitive property value.
 */
public class ODataPrimitiveValue extends ODataValue {

    private static final long serialVersionUID = 2841837627899878223L;

    /**
     * Primitive value builder.
     */
    public static class Builder {

        private final ODataPrimitiveValue opv;

        /**
         * Constructor.
         */
        public Builder() {
            this.opv = new ODataPrimitiveValue();
        }

        /**
         * Sets the given value provided as a text.
         *
         * @param text value.
         * @return the current builder.
         */
        public Builder setText(final String text) {
            this.opv.text = text;
            return this;
        }

        /**
         * Sets the actual object value.
         *
         * @param value value.
         * @return the current builder.
         */
        public Builder setValue(final Object value) {
            this.opv.value = value;
            return this;
        }

        /**
         * Sets actual value type.
         *
         * @param type type.
         * @return the current builder.
         */
        public Builder setType(final EdmSimpleType type) {
            if (type == EdmSimpleType.STREAM) {
                throw new IllegalArgumentException("Cannot build a primitive value for "
                        + EdmSimpleType.STREAM.toString());
            }
            this.opv.type = type;
            return this;
        }

        /**
         * Builds the primitive value.
         *
         * @return <code>ODataPrimitiveValue</code> object.
         */
        public ODataPrimitiveValue build() {
            if (this.opv.text == null && this.opv.value == null) {
                throw new IllegalArgumentException("Must provide either text or value");
            }
            if (this.opv.text != null && this.opv.value != null) {
                throw new IllegalArgumentException("Cannot provide both text and value");
            }

            if (this.opv.type == null) {
                this.opv.type = EdmSimpleType.STRING;
            }

            if (this.opv.type.isGeospatial()) {
                throw new IllegalArgumentException(
                        "Use " + ODataGeospatialValue.class.getSimpleName() + " for geospatial types");
            }

            if (this.opv.value instanceof Timestamp) {
                this.opv.value = ODataTimestamp.getInstance(this.opv.type, (Timestamp) this.opv.value);
            } else if (this.opv.value instanceof Date) {
                this.opv.value = ODataTimestamp.getInstance(this.opv.type,
                        new Timestamp(((Date) this.opv.value).getTime()));
            }
            if (this.opv.value instanceof Duration) {
                this.opv.value = new ODataDuration((Duration) this.opv.value);
            }

            if (this.opv.value != null && !this.opv.type.javaType().isAssignableFrom(this.opv.value.getClass())) {
                throw new IllegalArgumentException("Provided value is not compatible with " + this.opv.type.toString());
            }

            if (this.opv.text != null) {
                this.opv.parseText();
            }
            if (this.opv.value != null) {
                this.opv.formatValue();
            }

            return this.opv;
        }
    }

    /**
     * Text value.
     */
    private String text;

    /**
     * Actual value.
     */
    protected Object value;

    /**
     * Value type.
     */
    protected EdmSimpleType type;

    /**
     * Protected constructor, need to use the builder to instantiate this class.
     *
     * @see Builder
     */
    protected ODataPrimitiveValue() {
        super();
    }

    /**
     * Parses given text as object value.
     */
    private void parseText() {
        switch (this.type) {
            case NULL:
                this.value = null;
                break;

            case BINARY:
                this.value = Base64.decodeBase64(this.toString());
                break;

            case S_BYTE:
                this.value = Byte.parseByte(this.toString());
                break;

            case BOOLEAN:
                this.value = Boolean.parseBoolean(this.toString());
                break;

            case DATE_TIME:
            case DATE_TIME_OFFSET:
                this.value = ODataTimestamp.parse(this.type, this.toString());
                break;

            case TIME:
                this.value = new ODataDuration(this.toString());
                break;

            case DECIMAL:
                this.value = new BigDecimal(this.toString());
                break;

            case SINGLE:
                this.value = Float.parseFloat(this.toString());
                break;

            case DOUBLE:
                this.value = Double.parseDouble(this.toString());
                break;

            case GUID:
                this.value = UUID.fromString(this.toString());
                break;

            case INT_16:
                this.value = Short.parseShort(this.toString());
                break;

            case BYTE:
            case INT_32:
                this.value = Integer.parseInt(this.toString());
                break;

            case INT_64:
                this.value = Long.parseLong(this.toString());
                break;

            case STREAM:
                this.value = URI.create(this.toString());
                break;

            case STRING:
                this.value = this.toString();
                break;

            default:
        }
    }

    /**
     * Format given value as text.
     */
    private void formatValue() {
        switch (this.type) {
            case NULL:
                this.text = StringUtils.EMPTY;
                break;

            case BINARY:
                this.text = Base64.encodeBase64String(this.<byte[]>toCastValue());
                break;

            case S_BYTE:
                this.text = this.<Byte>toCastValue().toString();
                break;

            case BOOLEAN:
                this.text = this.<Boolean>toCastValue().toString();
                break;

            case DATE_TIME:
            case DATE_TIME_OFFSET:
                this.text = this.<ODataTimestamp>toCastValue().toString();
                break;

            case TIME:
                this.text = this.<ODataDuration>toCastValue().toString();
                break;

            case DECIMAL:
                this.text = new DecimalFormat(this.type.pattern()).format(this.<BigDecimal>toCastValue());
                break;

            case SINGLE:
                this.text = new DecimalFormat(this.type.pattern()).format(this.<Float>toCastValue());
                break;

            case DOUBLE:
                this.text = new DecimalFormat(this.type.pattern()).format(this.<Double>toCastValue());
                break;

            case GUID:
                this.text = this.<UUID>toCastValue().toString();
                break;

            case INT_16:
                this.text = this.<Short>toCastValue().toString();
                break;

            case BYTE:
            case INT_32:
                this.text = this.<Integer>toCastValue().toString();
                break;

            case INT_64:
                this.text = this.<Long>toCastValue().toString();
                break;

            case STREAM:
                this.text = this.<URI>toCastValue().toASCIIString();
                break;

            case STRING:
                this.text = this.<String>toCastValue();
                break;

            default:
        }
    }

    /**
     * Gets type name.
     *
     * @return type name.
     */
    public String getTypeName() {
        return type.toString();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String toString() {
        return this.text;
    }

    /**
     * Gets actual primitive value.
     *
     * @return
     */
    public Object toValue() {
        return this.value;
    }

    /**
     * Casts primitive value.
     *
     * @param <T> cast.
     * @return casted value.
     */
    @SuppressWarnings("unchecked")
    public <T> T toCastValue() {
        return (T) type.javaType().cast(toValue());
    }
}
