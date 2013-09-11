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

import com.msopentech.odatajclient.engine.utils.Configuration;
import com.msopentech.odatajclient.proxy.api.AbstractEntityCollection;
import java.io.Serializable;
import java.net.URI;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class AsyncEntitySetIterator<T extends Serializable, KEY extends Serializable, EC extends AbstractEntityCollection<T>>
        implements Future<Iterator<T>> {

    private final Future<EntitySetIterator<T, KEY, EC>> entitySetItor;

    AsyncEntitySetIterator(final URI uri, final EntitySetInvocationHandler<T, KEY, EC> esi) {
        this.entitySetItor = Configuration.getExecutor().submit(new Callable<EntitySetIterator<T, KEY, EC>>() {

            @Override
            public EntitySetIterator<T, KEY, EC> call() throws Exception {
                return new EntitySetIterator<T, KEY, EC>(uri, esi);
            }
        });
    }

    @Override
    public boolean cancel(final boolean mayInterruptIfRunning) {
        return entitySetItor.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return entitySetItor.isCancelled();
    }

    @Override
    public boolean isDone() {
        return entitySetItor.isDone();
    }

    @Override
    public Iterator<T> get() throws InterruptedException, ExecutionException {
        return entitySetItor.get();
    }

    @Override
    public Iterator<T> get(final long timeout, final TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {

        return entitySetItor.get(timeout, unit);
    }
}
