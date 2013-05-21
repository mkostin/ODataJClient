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
package com.msopentech.odatajclient.proxy.meta.query;

import java.io.Serializable;
import java.util.List;

/**
 * Interface used to control the execution of typed queries.
 *
 * @param <T> query result type
 */
public interface EntityQuery<T extends Serializable> extends Query {

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
     * {@inheritDoc}
     */
    @Override
    EntityQuery<T> setSelect(String select);

    /**
     * {@inheritDoc}
     */
    @Override
    EntityQuery<T> setFilter(String filter);

    /**
     * {@inheritDoc}
     */
    @Override
    EntityQuery<T> setOrderBy(Sort... sort);

    /**
     * {@inheritDoc}
     */
    @Override
    EntityQuery<T> setOrderBy(String orderBy);

    /**
     * Execute a <tt>$filter</tt> query that returns a single typed result.
     *
     * @return the result
     * @throws NoResultException if there is no result
     * @throws NonUniqueResultException if more than one result
     */
    @Override
    T getSingleResult() throws NoResultException, NonUniqueResultException;

    /**
     * Execute a <tt>$filter</tt> query and return the query results as a typed List.
     *
     * @return a list of the results
     */
    @Override
    List<T> getResultList();
}
