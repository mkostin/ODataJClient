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
 * Metadata elements: <tt>&lt;EntityType/&gt;</tt>
 */
public class EntityType extends ComplexType {

    private static final long serialVersionUID = 1603846108931407190L;

    private boolean openType;

    private Key key;

    private List<NavigationProperty> navigationProperties;

    public boolean isOpenType() {
        return openType;
    }

    public Key getKey() {
        return key;
    }

    public List<NavigationProperty> getNavigationProperties() {
        return navigationProperties;
    }
}
