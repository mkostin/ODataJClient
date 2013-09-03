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
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.lang3.ArrayUtils;

public class EntityCollectionInvocationHandler<T extends Serializable>
        extends AbstractInvocationHandler implements AbstractEntityCollection<T> {

    private static final long serialVersionUID = 98078202642671726L;

    private static final Method[] COLLECTION_METHODS = Collection.class.getMethods();

    private final Collection<T> items;

    public EntityCollectionInvocationHandler(final Collection<T> items, final EntityContainerFactory factory) {
        super(factory);
        this.items = items;
    }

    private boolean isCollectionMethod(final Method method) {
        boolean result = false;

        for (int i = 0; i < COLLECTION_METHODS.length && !result; i++) {
            result = method.getName().equals(COLLECTION_METHODS[i].getName())
                    && Arrays.equals(method.getParameterTypes(), COLLECTION_METHODS[i].getParameterTypes());
        }

        return result;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if ("equals".equals(method.getName()) && !(ArrayUtils.isEmpty(args)) && args.length == 1) {
            return this.equals(args[0]);
        } else if ("hashCode".equals(method.getName()) && (ArrayUtils.isEmpty(args))) {
            return this.hashCode();
        } else if ("toString".equals(method.getName()) && (ArrayUtils.isEmpty(args))) {
            return this.toString();
        } else if (isCollectionMethod(method)) {
            return this.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(this, args);
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
