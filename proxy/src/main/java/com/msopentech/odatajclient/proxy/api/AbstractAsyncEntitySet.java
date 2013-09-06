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

import com.msopentech.odatajclient.proxy.api.query.AsyncQuery;
import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * Interface for asynchronous CRUD operations on an EntitySet.
 * For synchronous version, check AbstractEntitySet.
 *
 * @see AbstractEntitySet
 */
public abstract interface AbstractAsyncEntitySet<        
        T extends Serializable, KEY extends Serializable, EC extends AbstractEntityCollection<T>>
        extends Iterable<T>, Serializable {

    /**
     * @see AbstractEntitySet#exists(java.io.Serializable)
     */
    Future<Boolean> exists(KEY key) throws IllegalArgumentException;

    /**
     * @see AbstractEntitySet#get(java.io.Serializable)
     */
    Future<T> get(KEY key) throws IllegalArgumentException;

    /**
     * @see AbstractEntitySet#get(java.io.Serializable, java.lang.Class)
     */
    <S extends T> Future<S> get(KEY key, Class<S> reference) throws IllegalArgumentException;

    /**
     * @see AbstractEntitySet#count()
     */
    Future<Long> count();

    /**
     * @see AbstractEntitySet#getAll()
     */
    Future<EC> getAll();

    /**
     * @see AbstractEntitySet#getAll(java.lang.Class, java.lang.Class)
     */
    <S extends T, SEC extends AbstractEntityCollection<S>> Future<SEC> getAll(Class<SEC> reference);

    /**
     * @see AbstractEntitySet#createQuery()
     */
    <S extends T, SEC extends AbstractEntityCollection<S>> AsyncQuery<S, SEC> createQuery();

    /**
     * @see AbstractEntitySet#createQuery(java.lang.Class) 
     */
    <S extends T, SEC extends AbstractEntityCollection<S>> AsyncQuery<S, SEC> createQuery(Class<SEC> reference);

    /**
     * @see AbstractEntitySet#delete(java.io.Serializable)
     */
    Future<Void> delete(KEY key) throws IllegalArgumentException;

    /**
     * @see AbstractEntitySet#delete(java.lang.Iterable)
     */
    <S extends T> Future<Void> delete(Iterable<S> entities);
}
