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
import java.util.Set;

/**
 * Metadata elements: <tt>&lt;ReturnType/&gt;</tt>
 */
public class FunctionReturnType implements Serializable {

    private static final long serialVersionUID = -4350642638182150565L;

    private String type;

    private Set<AnnotationAttribute> annotationAttributes;

    private CollectionType collectionType;

    private ReferenceType referenceType;

    private RowType rowType;

    private List<AnnotationElement> annotationElements;

    public String getType() {
        return type;
    }

    public Set<AnnotationAttribute> getAnnotationAttributes() {
        return annotationAttributes;
    }

    public CollectionType getCollectionType() {
        return collectionType;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public RowType getRowType() {
        return rowType;
    }

    public List<AnnotationElement> getAnnotationElements() {
        return annotationElements;
    }
}
