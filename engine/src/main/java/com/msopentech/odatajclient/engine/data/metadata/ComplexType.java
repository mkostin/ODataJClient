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

import java.util.List;

/**
 * Metadata elements: <tt>&lt;ComplexType/&gt;</tt>
 */
public class ComplexType extends AbstractAnnotated {

    private static final long serialVersionUID = 6941112242359766336L;

    protected String name;

    protected String baseType;

    protected boolean abstractEntity;

    protected List<Property> properties;

    public String getName() {
        return name;
    }

    public String getBaseType() {
        return baseType;
    }

    public boolean isAbstractEntity() {
        return abstractEntity;
    }

    public List<Property> getProperties() {
        return properties;
    }
}
