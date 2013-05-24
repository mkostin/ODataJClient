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
 * Metadata elements: <tt>&lt;FunctionImport/&gt;</tt>
 */
public class FunctionImport extends AbstractAnnotated {

    private static final long serialVersionUID = 3280376235200636794L;

    private String name;

    private ReturnType returnType;

    private EntitySet entitySet;

    private String entitySetPath;

    private boolean isSideEffecting;

    private boolean isBindable;

    private boolean isComposable;

    private List<Parameter> parameters;

    public String getName() {
        return name;
    }

    public ReturnType getReturnType() {
        return returnType;
    }

    public EntitySet getEntitySet() {
        return entitySet;
    }

    public String getEntitySetPath() {
        return entitySetPath;
    }

    public boolean isIsSideEffecting() {
        return isSideEffecting;
    }

    public boolean isIsBindable() {
        return isBindable;
    }

    public boolean isIsComposable() {
        return isComposable;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }
}
