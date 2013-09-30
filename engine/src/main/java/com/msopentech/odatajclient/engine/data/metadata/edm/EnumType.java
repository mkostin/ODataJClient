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

public class EnumType extends AbstractAnnotatedEdm {

    private static final long serialVersionUID = 2688487586103418210L;

    @JsonProperty(value = "Name", required = true)
    private String name;

    @JsonProperty("UnderlyingType")
    private String underlyingType;

    @JsonProperty("IsFlags")
    private boolean flags;

    @JsonProperty("Member")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Member> members = new ArrayList<Member>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUnderlyingType() {
        return underlyingType;
    }

    public void setUnderlyingType(final String underlyingType) {
        this.underlyingType = underlyingType;
    }

    public boolean isFlags() {
        return flags;
    }

    public void setFlags(final boolean flags) {
        this.flags = flags;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(final List<Member> members) {
        this.members = members;
    }
}
