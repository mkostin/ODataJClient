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

import com.fasterxml.jackson.core.JsonParser;
import java.io.IOException;
import org.apache.commons.lang3.ArrayUtils;

public final class AbstractAnnotatedEdmUtils {

    private static final String[] PROPERTIES = {"Documentation", "TypeAnnotation", "ValueAnnotation"};

    public static boolean isAbstractAnnotatedProperty(final String name) {
        return ArrayUtils.contains(PROPERTIES, name);
    }

    public static void parseAnnotatedEdm(final AbstractAnnotatedEdm item, final JsonParser jp) throws IOException {
        if ("Documentation".equals(jp.getCurrentName())) {
            jp.nextToken();
            item.setDocumentation(jp.getCodec().readValue(jp, Documentation.class));
        } else if ("TypeAnnotation".equals(jp.getCurrentName())) {
            jp.nextToken();
            item.getTypeAnnotations().add(jp.getCodec().readValue(jp, TypeAnnotation.class));
        } else if ("ValueAnnotation".equals(jp.getCurrentName())) {
            jp.nextToken();
            item.getValueAnnotations().add(jp.getCodec().readValue(jp, ValueAnnotation.class));
        }
    }

    private AbstractAnnotatedEdmUtils() {
        // empty constructor for static utility classes
    }
}
