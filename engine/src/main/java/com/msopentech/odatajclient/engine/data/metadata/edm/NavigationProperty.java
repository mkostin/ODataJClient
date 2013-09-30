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

public class NavigationProperty extends AbstractAnnotatedEdm {

    private static final long serialVersionUID = 3112463683071069594L;

    @JsonProperty(value = "Name", required = true)
    private String name;

    @JsonProperty(value = "Relationship", required = true)
    private String relationship;

    @JsonProperty(value = "ToRole", required = true)
    private String toRole;

    @JsonProperty(value = "FromRole", required = true)
    private String fromRole;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(final String relationship) {
        this.relationship = relationship;
    }

    public String getToRole() {
        return toRole;
    }

    public void setToRole(final String toRole) {
        this.toRole = toRole;
    }

    public String getFromRole() {
        return fromRole;
    }

    public void setFromRole(final String fromRole) {
        this.fromRole = fromRole;
    }
}
