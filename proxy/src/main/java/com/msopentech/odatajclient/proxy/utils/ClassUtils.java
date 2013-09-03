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
package com.msopentech.odatajclient.proxy.utils;

import com.msopentech.odatajclient.proxy.api.annotations.Property;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

public final class ClassUtils {

    private ClassUtils() {
        // Empty private constructor for static utility classes
    }

    public static Method findGetterByAnnotatedName(final Class<?> clazz, final String name) {
        final Method[] methods = clazz.getMethods();

        Method result = null;
        for (int i = 0; i < methods.length && result == null; i++) {
            final Annotation annotation = methods[i].getAnnotation(Property.class);
            if ((annotation instanceof Property)
                    && methods[i].getName().startsWith("get") // Assumption: getter is always prefixed by 'get' word 
                    && name.equals(((Property) annotation).name())) {
                result = methods[i];
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <ANN extends Annotation> ANN getAnnotation(final Class<ANN> reference, final AccessibleObject obj) {
        final Annotation ann = obj.getAnnotation(reference);
        return ann == null ? null : (ANN) ann;
    }
}
