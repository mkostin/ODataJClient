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

public class ReferentialConstraint extends AbstractEdm {

    private static final long serialVersionUID = 9067893732765127269L;

    @JsonProperty(value = "Principal", required = true)
    private ReferentialConstraintRole principal;

    @JsonProperty(value = "Dependent", required = true)
    private ReferentialConstraintRole dependent;

    @JsonProperty("Documentation")
    private Documentation documentation;

    public ReferentialConstraintRole getPrincipal() {
        return principal;
    }

    public void setPrincipal(final ReferentialConstraintRole principal) {
        this.principal = principal;
    }

    public ReferentialConstraintRole getDependent() {
        return dependent;
    }

    public void setDependent(final ReferentialConstraintRole dependent) {
        this.dependent = dependent;
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public void setDocumentation(final Documentation documentation) {
        this.documentation = documentation;
    }
}
