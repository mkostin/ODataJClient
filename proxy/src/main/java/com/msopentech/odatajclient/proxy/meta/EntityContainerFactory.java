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
package com.msopentech.odatajclient.proxy.meta;

/**
 * Entry point for ODataJClient proxy mode, give access to entity container instances.
 */
public interface EntityContainerFactory {

    /**
     * Return an initialized concrete implementation of the passed EntityContainer interface.
     *
     * @param <T> interface annotated as EntityContainer
     * @param reference class object for the EntityContainer interface
     * @return an initialized concrete implementation of the passed EntityContainer interface
     * @throws IllegalArgumentException if the passed reference is not an interface annotated as EntityContainer
     * @see EntityContainer
     */
    <T> T getEntityContainer(Class<T> reference) throws IllegalArgumentException;
}
