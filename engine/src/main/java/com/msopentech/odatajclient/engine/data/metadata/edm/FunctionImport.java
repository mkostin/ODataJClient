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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

public class FunctionImport extends AbstractAnnotatedEdm {

    private static final long serialVersionUID = 4154308065211315663L;

    @JsonProperty(value = "Name", required = true)
    private String name;

    @JsonProperty("ReturnType")
    private String returnType;

    @JsonProperty("EntitySet")
    private String entitySet;

    @JsonProperty("EntitySetPath")
    private String entitySetPath;

    @JsonProperty("IsComposable")
    private boolean composable;

    @JsonProperty("IsSideEffecting")
    private boolean sideEffecting = true;

    @JsonProperty("IsBindable")
    private boolean bindable;

    @JsonProperty("IsAlwaysBindable")
    private boolean alwaysBindable;

    @JsonProperty("HttpMethod")
    private String httpMethod;

    @JsonProperty("Parameter")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Parameter> parameters = new ArrayList<Parameter>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(final String returnType) {
        this.returnType = returnType;
    }

    public String getEntitySet() {
        return entitySet;
    }

    public void setEntitySet(final String entitySet) {
        this.entitySet = entitySet;
    }

    public String getEntitySetPath() {
        return entitySetPath;
    }

    public void setEntitySetPath(final String entitySetPath) {
        this.entitySetPath = entitySetPath;
    }

    public boolean isComposable() {
        return composable;
    }

    public void setComposable(final boolean composable) {
        this.composable = composable;
    }

    public boolean isSideEffecting() {
        return sideEffecting;
    }

    public void setSideEffecting(final boolean sideEffecting) {
        this.sideEffecting = sideEffecting;
    }

    public boolean isBindable() {
        return bindable;
    }

    public void setBindable(final boolean bindable) {
        this.bindable = bindable;
    }

    public boolean isAlwaysBindable() {
        return alwaysBindable;
    }

    public void setAlwaysBindable(final boolean alwaysBindable) {
        this.alwaysBindable = alwaysBindable;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(final String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(final List<Parameter> parameters) {
        this.parameters = parameters;
    }
}
