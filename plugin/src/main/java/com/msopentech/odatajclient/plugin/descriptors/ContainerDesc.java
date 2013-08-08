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
package com.msopentech.odatajclient.plugin.descriptors;

import java.util.List;

public class ContainerDesc {

    private boolean defaultContainer;

    private String name;

    private List<EntitySetDesc> entitySets;

    private List<OperationDesc> operations;

    public boolean isDefaultContainer() {
        return defaultContainer;
    }

    public void setDefaultContainer(boolean defaultContainer) {
        this.defaultContainer = defaultContainer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EntitySetDesc> getEntitySets() {
        return entitySets;
    }

    public void setEntitySets(
            List<EntitySetDesc> entitySets) {
        this.entitySets = entitySets;
    }

    public List<OperationDesc> getOperations() {
        return operations;
    }

    public void setOperations(
            List<OperationDesc> operations) {
        this.operations = operations;
    }
}
