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

import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.utils.Configuration;
import com.msopentech.odatajclient.proxy.api.AbstractAsyncEntitySet;
import com.msopentech.odatajclient.proxy.api.AbstractEntityCollection;
import com.msopentech.odatajclient.proxy.api.AsyncQuery;
import com.msopentech.odatajclient.proxy.utils.ClassUtils;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

class AsyncEntitySetInvocationHandler<
        T extends Serializable, KEY extends Serializable, EC extends AbstractEntityCollection<T>>
        extends AbstractInvocationHandler
        implements AbstractAsyncEntitySet<T, KEY, EC> {

    private static final long serialVersionUID = -7792721105644066069L;

    static <T extends Serializable, KEY extends Serializable, EC extends AbstractEntityCollection<T>> AsyncEntitySetInvocationHandler<T, KEY, EC> getInstance(
            final EntitySetInvocationHandler<T, KEY, EC> syncHandler) {

        return new AsyncEntitySetInvocationHandler<T, KEY, EC>(syncHandler);
    }

    private final EntitySetInvocationHandler<T, KEY, EC> syncHandler;

    @SuppressWarnings("unchecked")
    private AsyncEntitySetInvocationHandler(final EntitySetInvocationHandler<T, KEY, EC> syncHandler) {
        super(syncHandler.containerHandler);
        this.syncHandler = syncHandler;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (isSelfMethod(method, args)) {
            return invokeSelfMethod(method, args);
        } else {
            throw new UnsupportedOperationException("Method not found: " + method);
        }
    }

    @Override
    public Future<Boolean> exists(final KEY key) throws IllegalArgumentException {
        return Configuration.getExecutor().submit(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                return syncHandler.exists(key);
            }
        });
    }

    @Override
    public Future<T> get(final KEY key) throws IllegalArgumentException {
        return Configuration.getExecutor().submit(new Callable<T>() {

            @Override
            public T call() throws Exception {
                return syncHandler.get(key);
            }
        });
    }

    @Override
    public <S extends T> Future<S> get(final KEY key, final Class<S> reference) throws IllegalArgumentException {
        return Configuration.getExecutor().submit(new Callable<S>() {

            @Override
            public S call() throws Exception {
                return syncHandler.get(key, reference);
            }
        });
    }

    @Override
    public Future<Long> count() {
        return Configuration.getExecutor().submit(new Callable<Long>() {

            @Override
            public Long call() throws Exception {
                return syncHandler.count();
            }
        });
    }

    @Override
    public Future<EC> getAll() {
        return Configuration.getExecutor().submit(new Callable<EC>() {

            @Override
            public EC call() throws Exception {
                return syncHandler.getAll();
            }
        });
    }

    @Override
    public <S extends T, SEC extends AbstractEntityCollection<S>> Future<SEC> getAll(final Class<SEC> reference) {
        return Configuration.getExecutor().submit(new Callable<SEC>() {

            @Override
            public SEC call() throws Exception {
                return syncHandler.getAll(reference);
            }
        });
    }

    @Override
    public Future<Iterator<T>> iterator() {
        return new AsyncEntitySetIterator<T, KEY, EC>(
                new ODataURIBuilder(syncHandler.getUri().toASCIIString()).appendStructuralSegment(
                ClassUtils.getNamespace(syncHandler.getTypeRef()) + "."
                + ClassUtils.getEntityTypeName(syncHandler.getTypeRef())).build(),
                syncHandler);
    }

    @Override
    public Future<Void> delete(final KEY key) throws IllegalArgumentException {
        return Configuration.getExecutor().submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                syncHandler.delete(key);
                return ClassUtils.returnVoid();
            }
        });
    }

    @Override
    public <S extends T> Future<Void> delete(final Iterable<S> entities) {
        return Configuration.getExecutor().submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                syncHandler.delete(entities);
                return ClassUtils.returnVoid();
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends T, SEC extends AbstractEntityCollection<S>> AsyncQuery<S, SEC> createQuery() {
        return new AsyncQueryImpl<S, SEC>((QueryImpl<S, SEC>) syncHandler.createQuery());
    }

    @Override
    public <S extends T, SEC extends AbstractEntityCollection<S>> AsyncQuery<S, SEC> createQuery(
            final Class<SEC> reference) {

        return new AsyncQueryImpl<S, SEC>((QueryImpl<S, SEC>) syncHandler.createQuery(reference));
    }
}
