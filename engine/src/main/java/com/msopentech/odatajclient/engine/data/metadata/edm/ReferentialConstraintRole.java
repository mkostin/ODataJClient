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

public class ReferentialConstraintRole extends AbstractEdm {

    private static final long serialVersionUID = -3712887115248634164L;

    @JsonProperty(value = "Role", required = true)
    private String role;

    @JsonProperty(value = "PropertyRef", required = true)
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PropertyRef> propertyRef = new ArrayList<PropertyRef>();

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public List<PropertyRef> getPropertyRef() {
        return propertyRef;
    }

    public void setPropertyRef(final List<PropertyRef> propertyRef) {
        this.propertyRef = propertyRef;
    }
}
