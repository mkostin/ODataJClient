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
package com.msopentech.odatajclient.proxy.api.impl;

import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractInvocationHandler implements InvocationHandler {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractInvocationHandler.class);

    private static final long serialVersionUID = 358520026931462958L;

    protected final EntityContainerFactory factory;

    protected AbstractInvocationHandler(final EntityContainerFactory factory) {
        this.factory = factory;
    }

    protected boolean isSelfMethod(final Method method, final Object[] args) {
        final Method[] selfMethods = getClass().getMethods();

        boolean result = false;

        for (int i = 0; i < selfMethods.length && !result; i++) {
            result = method.getName().equals(selfMethods[i].getName())
                    && Arrays.equals(method.getParameterTypes(), selfMethods[i].getParameterTypes());
        }

        System.out.println("KKKKKKKKKKKK " + method.getName() + " " + result);
        
        return result;
    }

    protected Object invokeSelfMethod(final Method method, final Object[] args)
            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        return getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(this, args);
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
