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

import com.msopentech.odatajclient.proxy.api.AbstractEntityCollection;
import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

public class EntityCollectionInvocationHandler<T extends Serializable>
        extends AbstractInvocationHandler implements AbstractEntityCollection<T> {

    private static final long serialVersionUID = 98078202642671726L;

    private final Collection<T> items;

    public EntityCollectionInvocationHandler(final Collection<T> items, final EntityContainerFactory factory) {
        super(factory);
        this.items = items;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (isSelfMethod(method, args)) {
            return invokeSelfMethod(method, args);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean contains(final Object object) {
        return items.contains(object);
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

    @Override
    public Object[] toArray() {
        return items.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] array) {
        return items.toArray(array);
    }

    @Override
    public boolean add(final T element) {
        return items.add(element);
    }

    @Override
    public boolean remove(final Object object) {
        return items.remove(object);
    }

    @Override
    public boolean containsAll(final Collection<?> collection) {
        return items.containsAll(collection);
    }

    @Override
    public boolean addAll(final Collection<? extends T> collection) {
        return items.addAll(collection);
    }

    @Override
    public boolean removeAll(final Collection<?> collection) {
        return items.removeAll(collection);
    }

    @Override
    public boolean retainAll(final Collection<?> collection) {
        return items.retainAll(collection);
    }

    @Override
    public void clear() {
        items.clear();
    }
}
