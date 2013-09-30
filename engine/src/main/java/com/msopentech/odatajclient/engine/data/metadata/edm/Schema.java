/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.engine.data.metadata.edm;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = SchemaDeserializer.class)
public class Schema extends AbstractEdm {

    private static final long serialVersionUID = -1356392748971378455L;

    private String namespace;

    private String alias;

    private List<Using> usings = new ArrayList<Using>();

    private List<Association> associations = new ArrayList<Association>();

    private List<ComplexType> complexTypes = new ArrayList<ComplexType>();

    private List<EntityType> entityTypes = new ArrayList<EntityType>();

    private List<EnumType> enumTypes = new ArrayList<EnumType>();

    private List<ValueTerm> valueTerms = new ArrayList<ValueTerm>();

    private List<Annotations> annotations = new ArrayList<Annotations>();

    private List<EntityContainer> entityContainers = new ArrayList<EntityContainer>();

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(final String namespace) {
        this.namespace = namespace;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(final String alias) {
        this.alias = alias;
    }

    public List<EntityType> getEntityTypes() {
        return entityTypes;
    }

    public void setEntityTypes(final List<EntityType> entityTypes) {
        this.entityTypes = entityTypes;
    }

    public List<EnumType> getEnumTypes() {
        return enumTypes;
    }

    public void setEnumTypes(final List<EnumType> enumTypes) {
        this.enumTypes = enumTypes;
    }

    public List<Using> getUsings() {
        return usings;
    }

    public void setUsings(final List<Using> usings) {
        this.usings = usings;
    }

    public List<ValueTerm> getValueTerms() {
        return valueTerms;
    }

    public void setValueTerms(final List<ValueTerm> valueTerms) {
        this.valueTerms = valueTerms;
    }

    public Association getAssociation(final String name) {
        Association result = null;
        for (Association association : getAssociations()) {
            if (name.equals(association.getName())) {
                result = association;
            }
        }
        return result;
    }

    public List<Association> getAssociations() {
        return associations;
    }

    public void setAssociations(final List<Association> associations) {
        this.associations = associations;
    }

    public List<EntityContainer> getEntityContainers() {
        return entityContainers;
    }

    public void setEntityContainers(final List<EntityContainer> entityContainers) {
        this.entityContainers = entityContainers;
    }

    public List<Annotations> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(final List<Annotations> annotations) {
        this.annotations = annotations;
    }

    public List<ComplexType> getComplexTypes() {
        return complexTypes;
    }

    public void setComplexTypes(final List<ComplexType> complexTypes) {
        this.complexTypes = complexTypes;
    }

    /**
     * Gets default entity container.
     *
     * @return default entity container.
     */
    public EntityContainer getDefaultEntityContainer() {
        EntityContainer result = null;
        for (EntityContainer container : getEntityContainers()) {
            if (container.isDefaultEntityContainer()) {
                result = container;
            }
        }
        return result;
    }

    /**
     * Gets entity container with the given name.
     *
     * @param name name.
     * @return entity container.
     */
    public EntityContainer getEntityContainer(final String name) {
        EntityContainer result = null;
        for (EntityContainer container : getEntityContainers()) {
            if (name.equals(container.getName())) {
                result = container;
            }
        }
        return result;
    }

    /**
     * Gets entity type with the given name.
     *
     * @param name name.
     * @return entity type.
     */
    public EntityType getEntityType(final String name) {
        EntityType result = null;
        for (EntityType type : getEntityTypes()) {
            if (name.equals(type.getName())) {
                result = type;
            }
        }
        return result;
    }

    /**
     * Gets complex type with the given name.
     *
     * @param name name.
     * @return complex type.
     */
    public ComplexType getComplexType(final String name) {
        ComplexType result = null;
        for (ComplexType type : getComplexTypes()) {
            if (name.equals(type.getName())) {
                result = type;
            }
        }
        return result;
    }
}
