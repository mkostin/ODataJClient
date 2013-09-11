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
package com.msopentech.odatajclient.proxy.api.impl;

import com.msopentech.odatajclient.engine.uri.filter.ODataFilter;
import com.msopentech.odatajclient.engine.utils.Configuration;
import com.msopentech.odatajclient.proxy.api.AbstractEntityCollection;
import com.msopentech.odatajclient.proxy.api.AsyncQuery;
import com.msopentech.odatajclient.proxy.api.NoResultException;
import com.msopentech.odatajclient.proxy.api.NonUniqueResultException;
import com.msopentech.odatajclient.proxy.api.Sort;
import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

class AsyncQueryImpl<T extends Serializable, EC extends AbstractEntityCollection<T>> extends AsyncQuery<T, EC> {

    private static final long serialVersionUID = 7296604919458680784L;

    private final QueryImpl<T, EC> syncQuery;

    AsyncQueryImpl(final QueryImpl<T, EC> syncQuery) {
        super();
        this.syncQuery = syncQuery;
    }

    @Override
    public AsyncQuery<T, EC> setFilter(final String filter) {
        syncQuery.setFilter(filter);
        return this;
    }

    @Override
    public AsyncQuery<T, EC> setFilter(final ODataFilter filter) {
        syncQuery.setFilter(filter);
        return this;
    }

    @Override
    public String getFilter() {
        return syncQuery.getFilter();
    }

    @Override
    public AsyncQuery<T, EC> setOrderBy(final Sort... sort) {
        syncQuery.setOrderBy(sort);
        return this;
    }

    @Override
    public AsyncQuery<T, EC> setOrderBy(final String orderBy) {
        syncQuery.setOrderBy(orderBy);
        return this;
    }

    @Override
    public String getOrderBy() {
        return syncQuery.getOrderBy();
    }

    @Override
    public AsyncQuery<T, EC> setMaxResults(final int maxResults) throws IllegalArgumentException {
        syncQuery.setMaxResults(maxResults);
        return this;
    }

    @Override
    public int getMaxResults() {
        return syncQuery.getMaxResults();
    }

    @Override
    public AsyncQuery<T, EC> setFirstResult(final int firstResult) throws IllegalArgumentException {
        syncQuery.setFirstResult(firstResult);
        return this;
    }

    @Override
    public int getFirstResult() {
        return syncQuery.getFirstResult();
    }

    @Override
    public Future<T> asyncGetSingleResult() throws NoResultException, NonUniqueResultException {
        return Configuration.getExecutor().submit(new Callable<T>() {

            @Override
            public T call() throws Exception {
                return syncQuery.getSingleResult();
            }
        });
    }

    @Override
    public Future<EC> asyncGetResult() {
        return Configuration.getExecutor().submit(new Callable<EC>() {

            @Override
            public EC call() throws Exception {
                return syncQuery.getResult();
            }
        });
    }
}
