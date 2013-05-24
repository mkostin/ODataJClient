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

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Metadata elements: <tt>&lt;Documentation/&gt;</tt>
 */
public class Documentation implements Serializable {

    private static final long serialVersionUID = 3239186184769450382L;

    private Set<AnnotationAttribute> annotationAttributes;

    private String summary;

    private String longDescription;

    private List<AnnotationElement> annotationElements;

    public Set<AnnotationAttribute> getAnnotationAttributes() {
        return annotationAttributes;
    }

    public String getSummary() {
        return summary;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public List<AnnotationElement> getAnnotationElements() {
        return annotationElements;
    }
}
