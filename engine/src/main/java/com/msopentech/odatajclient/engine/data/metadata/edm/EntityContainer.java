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

@JsonDeserialize(using = EntityContainerDeserializer.class)
public class EntityContainer extends AbstractAnnotatedEdm {

    private static final long serialVersionUID = 4121974387552855032L;

    private String name;

    private String _extends;

    private boolean lazyLoadingEnabled;

    private boolean defaultEntityContainer;

    private List<EntitySet> entitySets = new ArrayList<EntitySet>();

    private List<AssociationSet> associationSets = new ArrayList<AssociationSet>();

    private List<FunctionImport> functionImports = new ArrayList<FunctionImport>();
    
    private List<Singleton> singletons = new ArrayList<Singleton>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getExtends() {
        return _extends;
    }

    public void setExtends(final String _extends) {
        this._extends = _extends;
    }

    public boolean isLazyLoadingEnabled() {
        return lazyLoadingEnabled;
    }

    public void setLazyLoadingEnabled(final boolean lazyLoadingEnabled) {
        this.lazyLoadingEnabled = lazyLoadingEnabled;
    }

    public boolean isDefaultEntityContainer() {
        return defaultEntityContainer;
    }

    public void setDefaultEntityContainer(final boolean defaultEntityContainer) {
        this.defaultEntityContainer = defaultEntityContainer;
    }

    public List<EntitySet> getEntitySets() {
        return entitySets;
    }

    public void setEntitySets(final List<EntitySet> entitySets) {
        this.entitySets = entitySets;
    }

    public List<AssociationSet> getAssociationSets() {
        return associationSets;
    }

    public void setAssociationSets(final List<AssociationSet> associationSets) {
        this.associationSets = associationSets;
    }

    /**
     * Gets the first function import with the given name.
     *
     * @param name name.
     * @return function import.
     */
    public FunctionImport getFunctionImport(final String name) {
        final List<FunctionImport> funcImps = getFunctionImports(name);
        return funcImps.isEmpty()
                ? null
                : funcImps.get(0);
    }

    /**
     * Gets all function import with the given name.
     *
     * @param name name.
     * @return function imports.
     */
    public List<FunctionImport> getFunctionImports(final String name) {
        final List<FunctionImport> result = new ArrayList<FunctionImport>();
        for (FunctionImport functionImport : getFunctionImports()) {
            if (name.equals(functionImport.getName())) {
                result.add(functionImport);
            }
        }
        return result;
    }

    public List<FunctionImport> getFunctionImports() {
        return functionImports;
    }

    public void setFunctionImports(final List<FunctionImport> functionImports) {
        this.functionImports = functionImports;
    }

    public List<Singleton> getSingletons() {
        return singletons;
    }

    public void setSingletons(List<Singleton> singletons) {
        this.singletons = singletons;
    }
}
