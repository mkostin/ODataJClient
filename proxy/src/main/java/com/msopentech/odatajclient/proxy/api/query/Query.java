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
package com.msopentech.odatajclient.proxy.api.query;

import com.msopentech.odatajclient.proxy.api.AbstractEntityCollection;
import java.io.Serializable;

/**
 * Interface used to control query execution.
 */
public interface Query extends Serializable {

    /**
     * Set the <tt>$select</tt> expression for this query.
     *
     * @param select the <tt>$select</tt> expression for this query
     * @return the same query instance
     */
    Query setSelect(String select);

    /**
     * The <tt>$select</tt> expression for this query.
     *
     * @return the <tt>$select</tt> expression for this query
     */
    String getSelect();

    /**
     * Set the <tt>$filter</tt> expression for this query.
     * Any of available operators and functions can be embodied here.
     *
     * @param filter the <tt>$filter</tt> expression for this query
     * @return the same query instance
     */
    Query setFilter(String filter);

    /**
     * The <tt>$filter</tt> expression for this query.
     *
     * @return the <tt>$filter</tt> expression for this query
     */
    String getFiter();

    /**
     * Set the <tt>$orderBy</tt> expression for this query via sort options.
     *
     * @param sort sort options
     * @return the same query instance
     */
    Query setOrderBy(Sort... sort);

    /**
     * Set the <tt>$orderBy</tt> expression for this query.
     *
     * @param select the <tt>$orderBy</tt> expression for this query
     * @return the same query instance
     */
    Query setOrderBy(String orderBy);

    /**
     * The <tt>$orderBy</tt> expression for this query.
     *
     * @return the <tt>$orderBy</tt> expression for this query
     */
    String getOrderBy();

    /**
     * Set the maximum number of results to retrieve (<tt>$top</tt>).
     *
     * @param maxResult maximum number of results to retrieve
     * @return the same query instance
     * @throws IllegalArgumentException if the argument is negative
     */
    Query setMaxResults(int maxResult) throws IllegalArgumentException;

    /**
     * The maximum number of results the query object was set to retrieve (<tt>$top</tt>).
     * Returns <tt>Integer.MAX_VALUE</tt> if setMaxResults was not applied to the query object.
     *
     * @return maximum number of results
     */
    int getMaxResults();

    /**
     * Set the position of the first result to retrieve (<tt>$skip</tt>).
     *
     * @param startPosition position of the first result, numbered from 0
     * @return the same query instance
     * @throws IllegalArgumentException if the argument is negative
     */
    Query setFirstResult(int startPosition) throws IllegalArgumentException;

    /**
     * The position of the first result the query object was set to retrieve (<tt>$skip</tt>).
     *
     * Returns 0 if <tt>setFirstResult</tt> was not applied to the query object.
     *
     * @return position of the first result
     */
    int getFirstResult();

    /**
     * Execute a <tt>$filter</tt> query that returns a single untyped result.
     *
     * @return the result
     * @throws NoResultException if there is no result
     * @throws NonUniqueResultException if more than one result
     */
    Serializable getSingleResult() throws NoResultException, NonUniqueResultException;

    /**
     * Execute a <tt>$filter</tt> query and return the query results as an untyped iterable.
     *
     * @return an iterable view of the results
     */
    <T extends Serializable, EC extends AbstractEntityCollection<T>> EC getResultList();
}
