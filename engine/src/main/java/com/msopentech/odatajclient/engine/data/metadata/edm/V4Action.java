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

import java.util.ArrayList;
import java.util.List;

public class V4Action extends AbstractAnnotatedEdm {

    private static final long serialVersionUID = 6570183520995323695L;

    private String name;
    
    private String isBound;
    
    private String entitySetPath;
    
    private String returnType;
    
    private List<Parameter> parameters = new ArrayList<Parameter>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsBound() {
        return isBound;
    }

    public void setIsBound(String isBound) {
        this.isBound = isBound;
    }

    public String getEntitySetPath() {
        return entitySetPath;
    }

    public void setEntitySetPath(String entitySetPath) {
        this.entitySetPath = entitySetPath;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}