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

import com.msopentech.odatajclient.proxy.api.context.Context;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataMetadataRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.proxy.api.impl.EntityContainerInvocationHandler;
import java.lang.reflect.Proxy;
import org.apache.commons.lang3.StringUtils;

/**
 * Entry point for ODataJClient proxy mode, gives access to entity container instances.
 */
public class EntityContainerFactory {

    private static final Object MONITOR = new Object();

    private String serviceRoot;

    private EdmMetadata metadata;

    private static Context context = null;

    public static Context getContext() {
        synchronized (MONITOR) {
            if (context == null) {
                context = new Context();
            }
        }

        return context;
    }

    public static EntityContainerFactory newInstance(final String serviceRoot) {
        return new EntityContainerFactory(serviceRoot);
    }

    private EntityContainerFactory(final String serviceRoot) {
        this.serviceRoot = serviceRoot;
        final ODataMetadataRequest req = ODataRetrieveRequestFactory.getMetadataRequest(serviceRoot);

        final ODataRetrieveResponse<EdmMetadata> res = req.execute();
        metadata = res.getBody();

        if (metadata == null) {
            throw new IllegalStateException("No metadata found at URI '" + serviceRoot + "'");
        }
    }

    public String getServiceRoot() {
        return serviceRoot;
    }

    public EdmMetadata getMetadata() {
        return metadata;
    }

    /**
     * Return an initialized concrete implementation of the passed EntityContainer interface.
     *
     * @param <T> interface annotated as EntityContainer
     * @param reference class object of the EntityContainer annotated interface
     * @return an initialized concrete implementation of the passed reference
     * @throws IllegalStateException if <tt>serviceRoot</tt> was not set
     * @throws IllegalArgumentException if the passed reference is not an interface annotated as EntityContainer
     * @see com.msopentech.odatajclient.proxy.api.annotations.EntityContainer
     */
    @SuppressWarnings("unchecked")
    public <T> T getEntityContainer(final Class<T> reference) throws IllegalStateException, IllegalArgumentException {
        if (StringUtils.isBlank(serviceRoot)) {
            throw new IllegalStateException("serviceRoot was not set");
        }

        return (T) Proxy.newProxyInstance(
                reference.getClassLoader(),
                new Class<?>[] {reference},
                new EntityContainerInvocationHandler(reference, this));
    }
}
