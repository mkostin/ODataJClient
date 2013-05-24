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

public abstract class AbstractTypeValueAnnotations implements Serializable {

    private static final long serialVersionUID = -1292588853124168762L;

    protected List<TypeAnnotation> typeAnnotations;

    protected List<ValueAnnotation> valueAnnotations;

    public List<TypeAnnotation> getTypeAnnotations() {
        return typeAnnotations;
    }

    public List<ValueAnnotation> getValueAnnotations() {
        return valueAnnotations;
    }
}
