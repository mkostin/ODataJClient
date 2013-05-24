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

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractValue implements Serializable {

    private static final long serialVersionUID = 1157468860101394777L;

    protected String expression;

    protected String pathValue;

    protected String stringValue;

    protected int intValue;

    protected float floatValue;

    protected float decimalValue;

    protected boolean booleanValue;

    protected Date datetimeValue;

    public String getExpression() {
        return expression;
    }

    public String getPathValue() {
        return pathValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public float getDecimalValue() {
        return decimalValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public Date getDatetimeValue() {
        return datetimeValue;
    }
}
