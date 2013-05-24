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
 * Metadata elements: <tt>&lt;Function/&gt;</tt>
 */
public class Function extends AbstractAnnotated {

    private static final long serialVersionUID = -6193391588457248829L;

    private String name;

    private FunctionReturnType returnType;

    private boolean abstractFunction;

    private List<FunctionParameter> parameters;

    private String definitionExpression;

    public String getName() {
        return name;
    }

    public FunctionReturnType getReturnType() {
        return returnType;
    }

    public boolean isAbstractFunction() {
        return abstractFunction;
    }

    public List<FunctionParameter> getParameters() {
        return parameters;
    }

    public String getDefinitionExpression() {
        return definitionExpression;
    }
}
