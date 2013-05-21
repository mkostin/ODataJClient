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

import com.msopentech.odatajclient.proxy.meta.query.EntityQuery;
import com.msopentech.odatajclient.proxy.meta.query.Query;
import java.io.Serializable;

/**
 * Interface for CRUD operations on an EntitySet.
 */
public abstract interface EntitySet<T extends Serializable, KEY extends Serializable> extends Serializable {

    /**
     * Returns the entity at the specified position in this entity set.
     *
     * @param index index of the entity to return
     * @return the entity at the specified position in this entity set
     * @throws IndexOutOfBoundsException if the index is out of range
     * (<tt>index &lt; 0 || index &gt;= count()</tt>)
     */
    T get(int index) throws IndexOutOfBoundsException;

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param key must not be null
     * @return true if an entity with the given id exists, false otherwise
     * @throws IllegalArgumentException in case the given key is null
     */
    boolean exists(KEY key) throws IllegalArgumentException;

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
    long count();

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
     * @return the new query instance
     */
    EntityQuery<T> createQuery(Class<T> entityClass);

    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation might have changed the entity instance
     * completely.
     *
     * @param entity to be saved
     * @return saved entity
     */
    T save(T entity);

    /**
     * Saves an entity and flushes changes instantly.
     *
     * @param entity to be saved
     * @return saved entity
     */
    T saveAndFlush(T entity);

    /**
     * Saves all given entities.
     *
     * @param entities to be saved
     * @return the saved entities
     */
    Iterable<T> save(Iterable<T> entities);

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
}
