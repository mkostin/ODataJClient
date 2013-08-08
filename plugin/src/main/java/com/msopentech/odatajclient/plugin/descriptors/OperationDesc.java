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

public class OperationDesc {

    private String name;

    private String entitySet;

    private String oReturnType;

    private String returnType;

    private List<OperationParameters> parameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntitySet() {
        return entitySet;
    }

    public void setEntitySet(String entitySet) {
        this.entitySet = entitySet;
    }

    public String getoReturnType() {
        return oReturnType;
    }

    public void setoReturnType(String oReturnType) {
        this.oReturnType = oReturnType;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public List<OperationParameters> getParameters() {
        return parameters;
    }

    public void setParameters(
            List<OperationParameters> parameters) {
        this.parameters = parameters;
    }
}
