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

import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Geospatial;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Point;
import java.net.URI;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * OData primitive property value.
 */
public class ODataPrimitiveValue extends ODataValue {

    private static final long serialVersionUID = 2841837627899878223L;

    public static class Builder {

        private final ODataPrimitiveValue opv;

        public Builder() {
            this.opv = new ODataPrimitiveValue();
        }

        public Builder setText(final String text) {
            this.opv.text = text;
            return this;
        }

        public Builder setValue(final Object value) {
            this.opv.value = value;
            return this;
        }

        public Builder setType(final EdmSimpleType type) {
            if (type == EdmSimpleType.STREAM) {
                throw new IllegalArgumentException("Cannot build a primitive value for "
                        + EdmSimpleType.STREAM.toString());
            }
            this.opv.type = type;
            return this;
        }

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
    private Object value;

    /**
     * Value type.
     */
    private EdmSimpleType type;

    /**
     * Private constructor, need to use the builder to instantiate this class.
     *
     * @see Builder
     */
    private ODataPrimitiveValue() {
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
            case TIME:
                String dateText = this.toString();
                String[] dateParts = dateText.split("\\.");
                SimpleDateFormat sdf = new SimpleDateFormat(this.type.pattern());
                try {
                    this.value = new Timestamp(sdf.parse(dateParts[0]).getTime());
                    if (dateParts.length > 1) {
                        this.<Timestamp>toCastValue().setNanos(Integer.parseInt(dateParts[1]));
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException(
                            "Cannot parse " + this.toString() + " as " + this.type.toString(), e);
                }
                break;

            case SINGLE:
            case DECIMAL:
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

            case GEOGRAPHY_POINT:
            case GEOMETRY_POINT:
                String[] points = this.toString().split("\\|");
                if (points == null || points.length == 0) {
                    throw new IllegalArgumentException("No points found in " + this.toString());
                }
                String[] point = points[0].split(" ");
                if (point == null || point.length != 2) {
                    throw new IllegalArgumentException("No X Y coordinates found in " + point);
                }
                this.value = new Point(this.type == EdmSimpleType.GEOGRAPHY_POINT
                        ? Geospatial.Dimension.GEOGRAPHY
                        : Geospatial.Dimension.GEOMETRY);
                this.<Point>toCastValue().setX(Double.parseDouble(point[0]));
                this.<Point>toCastValue().setY(Double.parseDouble(point[1]));
                break;

            case GEOGRAPHY:
            case GEOGRAPHY_LINE_STRING:
            case GEOGRAPHY_POLYGON:
            case GEOGRAPHY_MULTI_POINT:
            case GEOGRAPHY_MULTI_LINE_STRING:
            case GEOGRAPHY_MULTI_POLYGON:
            case GEOGRAPHY_COLLECTION:
            case GEOMETRY:
            case GEOMETRY_LINE_STRING:
            case GEOMETRY_POLYGON:
            case GEOMETRY_MULTI_POINT:
            case GEOMETRY_MULTI_LINE_STRING:
            case GEOMETRY_MULTI_POLYGON:
            case GEOMETRY_COLLECTION:
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
            case TIME:
                Timestamp timestamp = this.<Timestamp>toCastValue();
                SimpleDateFormat sdf = new SimpleDateFormat(this.type.pattern());
                StringBuffer formatted = new StringBuffer().append(sdf.format(timestamp));
                if (timestamp.getNanos() > 0) {
                    formatted.append('.').append(String.valueOf(timestamp.getNanos()));
                }
                this.text = formatted.toString();
                break;

            case SINGLE:
            case DECIMAL:
                this.text = this.<Float>toCastValue().toString();
                break;

            case DOUBLE:
                this.text = this.<Double>toCastValue().toString();
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

            case GEOGRAPHY_POINT:
            case GEOMETRY_POINT:
                this.text = Double.valueOf(this.<Point>toCastValue().getX()).toString()
                        + " " + Double.valueOf(this.<Point>toCastValue().getY()).toString();
                break;

            case GEOGRAPHY:
            case GEOGRAPHY_LINE_STRING:
            case GEOGRAPHY_POLYGON:
            case GEOGRAPHY_MULTI_POINT:
            case GEOGRAPHY_MULTI_LINE_STRING:
            case GEOGRAPHY_MULTI_POLYGON:
            case GEOGRAPHY_COLLECTION:
            case GEOMETRY:
            case GEOMETRY_LINE_STRING:
            case GEOMETRY_POLYGON:
            case GEOMETRY_MULTI_POINT:
            case GEOMETRY_MULTI_LINE_STRING:
            case GEOMETRY_MULTI_POLYGON:
            case GEOMETRY_COLLECTION:
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

    @Override
    public String toString() {
        return this.text;
    }

    public Object toValue() {
        return this.value;
    }

    @SuppressWarnings("unchecked")
    public <T> T toCastValue() {
        return (T) type.javaType().cast(toValue());
    }
}
