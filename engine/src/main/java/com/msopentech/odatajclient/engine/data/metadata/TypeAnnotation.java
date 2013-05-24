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
import java.util.List;

/**
 * Metadata elements: <tt>&lt;TypeAnnotation/&gt;</tt>
 */
public class TypeAnnotation implements Serializable {

    private static final long serialVersionUID = -2893050064921923944L;

    private String term;

    private String qualifier;

    private List<PropertyValue> propertyValues;

    public String getTerm() {
        return term;
    }

    public String getQualifier() {
        return qualifier;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }
}
