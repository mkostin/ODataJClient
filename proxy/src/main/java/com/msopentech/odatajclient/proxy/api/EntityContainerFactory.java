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
package com.msopentech.odatajclient.proxy.api;

import com.msopentech.odatajclient.proxy.api.annotations.EntityContainer;
import com.msopentech.odatajclient.proxy.api.annotations.Namespace;
import com.msopentech.odatajclient.proxy.api.impl.EntityContainerInvocationHandler;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;

/**
 * Entry point for ODataJClient proxy mode, gives access to entity container instances.
 */
public class EntityContainerFactory {

    private String serviceRoot;

    public void setServiceRoot(final String serviceRoot) {
        this.serviceRoot = serviceRoot;
    }

    /**
     * Return an initialized concrete implementation of the passed EntityContainer interface.
     *
     * @param <T> interface annotated as EntityContainer
     * @param reference class object of the EntityContainer annotated interface
     * @return an initialized concrete implementation of the passed reference
     * @throws IllegalStateException if <tt>serviceRoot</tt> was not set
     * @throws IllegalArgumentException if the passed reference is not an interface annotated as EntityContainer
     * @see EntityContainer
     */
    @SuppressWarnings("unchecked")
    public <T> T getEntityContainer(final Class<T> reference) throws IllegalStateException, IllegalArgumentException {
        if (this.serviceRoot == null) {
            throw new IllegalStateException("serviceRoot was not set");
        }

        if (!reference.isInterface()) {
            throw new IllegalArgumentException(reference.getName() + " is not an interface");
        }

        Annotation annotation = reference.getAnnotation(EntityContainer.class);
        if (!(annotation instanceof EntityContainer)) {
            throw new IllegalArgumentException(reference.getName()
                    + " is not annotated as @" + EntityContainer.class.getSimpleName());
        }
        final String entityContainerName = ((EntityContainer) annotation).name();

        annotation = reference.getPackage().getAnnotation(Namespace.class);
        if (!(annotation instanceof Namespace)) {
            throw new IllegalArgumentException(reference.getPackage().getName()
                    + " is not annotated as @" + Namespace.class.getSimpleName());
        }
        final String schemaName = ((Namespace) annotation).value();

        return (T) Proxy.newProxyInstance(reference.getClassLoader(), new Class<?>[] {reference},
                new EntityContainerInvocationHandler(serviceRoot, schemaName, entityContainerName));
    }
}
