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

public class TypeAnnotation extends AbstractEdm {

    private static final long serialVersionUID = -7585489230017331877L;

    @JsonProperty(value = "Term", required = true)
    private String term;

    @JsonProperty("Qualifier")
    private String qualifier;

    @JsonProperty("Documentation")
    private Documentation documentation;

    @JsonProperty("PropertyValue")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();

    public String getTerm() {
        return term;
    }

    public void setTerm(final String term) {
        this.term = term;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(final String qualifier) {
        this.qualifier = qualifier;
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public void setDocumentation(final Documentation documentation) {
        this.documentation = documentation;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(final List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
    }
}
