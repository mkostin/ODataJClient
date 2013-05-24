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
import java.util.Set;

/**
 * Metadata elements: <tt>&lt;CollectionType/&gt;</tt>
 */
public class CollectionType implements Serializable {

    private static final long serialVersionUID = -7833089525323992524L;

    private String elementType;

    private String facets;

    private Set<AnnotationAttribute> annotationAttributes;

    private CollectionType collectionType;

    private ReferenceType referenceType;

    private RowType rowType;

    private TypeRef typeRef;

    public String getElementType() {
        return elementType;
    }

    public String getFacets() {
        return facets;
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

    public TypeRef getTypeRef() {
        return typeRef;
    }
}
