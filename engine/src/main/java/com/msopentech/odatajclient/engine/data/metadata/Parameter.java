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
 * Metadata elements: <tt>&lt;Parameter/&gt;</tt>
 */
public class Parameter extends AbstractAnnotated {

    private static final long serialVersionUID = -7960545701744362922L;

    private String name;

    private String type;

    private ParameterMode mode;

    private int maxLenght;

    private int precision;

    private int scale;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public ParameterMode getMode() {
        return mode;
    }

    public int getMaxLenght() {
        return maxLenght;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }
}
