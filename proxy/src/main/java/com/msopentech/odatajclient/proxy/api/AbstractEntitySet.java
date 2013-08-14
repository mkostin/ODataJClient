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

import com.msopentech.odatajclient.proxy.api.query.EntityQuery;
import com.msopentech.odatajclient.proxy.api.query.Query;
import java.io.Serializable;

/**
 * Interface for synchronous CRUD operations on an EntitySet.
 */
public abstract interface AbstractEntitySet<T, KEY extends Serializable> extends Serializable {

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param key must not be null
     * @return true if an entity with the given id exists, false otherwise
     * @throws IllegalArgumentException in case the given key is null
     */
    Boolean exists(KEY key) throws IllegalArgumentException;

    /**
     * Retrieves an entity by its key.
     *
     * @param key must not be null
     * @return the entity with the given id or null if none found
     * @throws IllegalArgumentException in case the given key is null
     */
    T get(KEY key) throws IllegalArgumentException;

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    Long count();

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Iterable<T> getAll();

    /**
     * Create an instance of <tt>Query</tt>.
     *
     * @return the new query instance
     */
    Query createQuery();

    /**
     * Create an instance of <tt>TypedQuery</tt>.
     *
     * @param entityClass the type of the query result
     * @param <E> the type of the query result
     * @return the new query instance
     */
    <E extends Serializable> EntityQuery<E> createQuery(Class<E> entityClass);

    /**
     * Deletes the entity with the given key.
     *
     * @param key must not be null
     * @throws IllegalArgumentException in case the given key is null
     */
    void delete(KEY key) throws IllegalArgumentException;

    /**
     * Deletes the given entities in a batch.
     *
     * @param entities to be deleted
     */
    void delete(Iterable<T> entities);

    /**
     * Flushes all pending changes to the OData service.
     */
    void flush();

    /**
     * Create a new entity instance.
     *
     * @return new entity instance.
     */
    T newEntity();
}
