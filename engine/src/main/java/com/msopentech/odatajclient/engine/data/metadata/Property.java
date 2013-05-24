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
package com.msopentech.odatajclient.engine.data.metadata;

/**
 * Metadata elements: <tt>&lt;Property/&gt;</tt>
 */
public class Property extends AbstractAnnotated {

    private static final long serialVersionUID = -834599025489864843L;

    private String name;

    private String type;

    private boolean nullable;

    private String defaultValue;

    private int maxLenght;

    private boolean fixedLength;

    private int precision;

    private int scale;

    private boolean unicode;

    private String collation;

    private ConcurrencyMode concurrencyMode;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isNullable() {
        return nullable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public int getMaxLenght() {
        return maxLenght;
    }

    public boolean isFixedLength() {
        return fixedLength;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public boolean isUnicode() {
        return unicode;
    }

    public String getCollation() {
        return collation;
    }

    public ConcurrencyMode getConcurrencyMode() {
        return concurrencyMode;
    }
}
