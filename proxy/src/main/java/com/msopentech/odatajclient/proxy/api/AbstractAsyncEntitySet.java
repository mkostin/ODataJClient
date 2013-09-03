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
 * For synchronous version, check AbstractEntitySet.
 *
 * @see AbstractEntitySet
 */
public abstract interface AbstractAsyncEntitySet<T extends Serializable, KEY extends Serializable> extends Serializable {

    /**
     * @see AbstractEntitySet#exists(java.io.Serializable)
     */
    Future<Boolean> exists(KEY key) throws IllegalArgumentException;

    /**
     * @see AbstractEntitySet#get(java.io.Serializable)
     */
    Future<T> get(KEY key) throws IllegalArgumentException;

    /**
     * @see AbstractEntitySet#count()
     */
    Future<Long> count();

    /**
     * @see AbstractEntitySet#getAll()
     */
    <EC extends AbstractEntityCollection<T>> Future<EC> getAll();

    /**
     * @see AbstractEntitySet#createQuery()
     */
    AsyncQuery createQuery();

    /**
     * @see AbstractEntitySet#createQuery(java.lang.Class)
     */
    <E extends Serializable> AsyncEntityQuery<E> createQuery(Class<E> entityClass);

    /**
     * @see AbstractEntitySet#delete(java.io.Serializable)
     */
    Future<Void> delete(KEY key) throws IllegalArgumentException;

    /**
     * @see AbstractEntitySet#delete(java.lang.Iterable)
     */
    Future<Void> delete(Iterable<T> entities);

    /**
     * @see AbstractEntitySet#flush()
     */
    Future<Void> flush();
}
