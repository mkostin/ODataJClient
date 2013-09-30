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

public class AssociationEnd extends AbstractEdm {

    private static final long serialVersionUID = 3305394053564979376L;

    @JsonProperty(value = "Type", required = true)
    private String type;

    @JsonProperty(value = "Role")
    private String role;

    @JsonProperty(value = "Multiplicity")
    private String multiplicity;

    @JsonProperty(value = "Documentation")
    private Documentation documentation;

    @JsonProperty(value = "OnDelete")
    private OnDelete onDelete;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public String getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(final String multiplicity) {
        this.multiplicity = multiplicity;
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public void setDocumentation(final Documentation documentation) {
        this.documentation = documentation;
    }

    public OnDelete getOnDelete() {
        return onDelete;
    }

    public void setOnDelete(final OnDelete onDelete) {
        this.onDelete = onDelete;
    }
}
