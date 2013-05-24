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
import java.net.URI;
import java.util.List;
import java.util.Set;

/**
 * Metadata elements: <tt>&lt;Schema/&gt;</tt>
 */
public class Schema implements Serializable {

    private static final long serialVersionUID = -7786468421571261463L;

    private String namespace;

    private String alias;

    private URI namespaceURI;

    private Set<AnnotationAttribute> annotationAttributes;

    private List<Using> usings;

    private List<Association> associations;

    private List<ComplexType> complexTypes;

    private List<EntityType> entityTypes;

    private List<EntityContainer> entityContainers;

    private List<ValueTerm> valueTerms;

    private Annotations annotations;

    private List<AnnotationElement> annotationElements;

    public String getNamespace() {
        return namespace;
    }

    public String getAlias() {
        return alias;
    }

    public URI getNamespaceURI() {
        return namespaceURI;
    }

    public Set<AnnotationAttribute> getAnnotationAttributes() {
        return annotationAttributes;
    }

    public List<Using> getUsings() {
        return usings;
    }

    public List<Association> getAssociations() {
        return associations;
    }

    public List<ComplexType> getComplexTypes() {
        return complexTypes;
    }

    public List<EntityType> getEntityTypes() {
        return entityTypes;
    }

    public List<EntityContainer> getEntityContainers() {
        return entityContainers;
    }

    public List<ValueTerm> getValueTerms() {
        return valueTerms;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public List<AnnotationElement> getAnnotationElements() {
        return annotationElements;
    }
}
