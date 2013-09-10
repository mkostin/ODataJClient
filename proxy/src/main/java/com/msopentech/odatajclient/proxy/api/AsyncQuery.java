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

import com.msopentech.odatajclient.engine.uri.filter.ODataFilter;
import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * Abstract class to control asynchronous query execution.
 */
public abstract class AsyncQuery<T extends Serializable, EC extends AbstractEntityCollection<T>>
        implements Query<T, EC> {

    private static long serialVersionUID = -1384992773012007320L;

    @Override
    public abstract AsyncQuery<T, EC> setFilter(String filter);

    @Override
    public abstract AsyncQuery<T, EC> setFilter(ODataFilter filter);

    @Override
    public abstract AsyncQuery<T, EC> setOrderBy(Sort... sort);

    @Override
    public abstract AsyncQuery<T, EC> setOrderBy(String orderBy);

    @Override
    public abstract AsyncQuery<T, EC> setMaxResults(int maxResults) throws IllegalArgumentException;

    @Override
    public abstract AsyncQuery<T, EC> setFirstResult(int firstResult) throws IllegalArgumentException;

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException at any invocation, use <tt>asyncGetSingleResult()</tt> instead.
     */
    @Override
    public T getSingleResult() throws NoResultException, NonUniqueResultException {
        throw new UnsupportedOperationException("Synchronous operations not supported");
    }

    /**
     * Asynchronous variant of <tt>getSingleResult()</tt>.
     *
     * @see Query#getSingleResult()
     */
    public abstract Future<T> asyncGetSingleResult() throws NoResultException, NonUniqueResultException;

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException at any invocation, use <tt>asyncGetResultList()</tt> instead.
     */
    @Override
    public EC getResult() {
        throw new UnsupportedOperationException("Synchronous operations not supported");
    }

    /**
     * Asynchronous variant of <tt>getResultList()</tt>.
     *
     * @see Query#getResultList()
     */
    public abstract Future<EC> asyncGetResult();
}
