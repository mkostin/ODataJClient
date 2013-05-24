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
 * Metadata elements: <tt>&lt;EntityContainer/&gt;</tt>
 */
public class EntityContainer extends AbstractAnnotated {

    private static final long serialVersionUID = -8151048170803623082L;

    private String name;

    private String extending;

    private List<FunctionImport> functionImports;

    private List<EntitySet> entitySets;

    private List<AssociationSet> associationSets;

    public String getName() {
        return name;
    }

    public String getExtending() {
        return extending;
    }

    public List<FunctionImport> getFunctionImports() {
        return functionImports;
    }

    public List<EntitySet> getEntitySets() {
        return entitySets;
    }

    public List<AssociationSet> getAssociationSets() {
        return associationSets;
    }
}
