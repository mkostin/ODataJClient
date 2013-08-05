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

import com.msopentech.odatajclient.proxy.api.query.AsyncEntityQuery;
import com.msopentech.odatajclient.proxy.api.query.AsyncQuery;
import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * Interface for asynchronous CRUD operations on an EntitySet.
 * For synchronous version, check EntitySet.
 *
 * @see EntitySet
 */
public abstract interface AbstractAsyncEntitySet<T extends Serializable, KEY extends Serializable> extends Serializable {

    /**
     * @see EntitySet#exists(java.io.Serializable)
     */
    Future<Boolean> exists(KEY key) throws IllegalArgumentException;

    /**
     * @see EntitySet#get(java.io.Serializable)
     */
    Future<T> get(KEY key) throws IllegalArgumentException;

    /**
     * @see EntitySet#count()
     */
    Future<Long> count();

    /**
     * @see EntitySet#getAll()
     */
    Future<Iterable<T>> getAll();

    /**
     * @see EntitySet#createQuery()
     */
    AsyncQuery createQuery();

    /**
     * @see EntitySet#createQuery(java.lang.Class)
     */
    <E extends Serializable> AsyncEntityQuery<E> createQuery(Class<E> entityClass);

    /**
     * @see EntitySet#save(java.io.Serializable)
     */
    Future<T> save(T entity);

    /**
     * @see EntitySet#saveAndFlush(java.io.Serializable)
     */
    Future<T> saveAndFlush(T entity);

    /**
     * @see EntitySet#save(java.lang.Iterable)
     */
    Future<Iterable<T>> save(Iterable<T> entities);

    /**
     * @see EntitySet#delete(java.io.Serializable)
     */
    Future<Void> delete(KEY key) throws IllegalArgumentException;

    /**
     * @see EntitySet#delete(java.lang.Iterable)
     */
    Future<Void> delete(Iterable<T> entities);

    /**
     * @see EntitySet#flush()
     */
    Future<Void> flush();
}
