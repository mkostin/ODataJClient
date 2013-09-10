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
import com.msopentech.odatajclient.proxy.api.AbstractAsyncContainer;
import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import com.msopentech.odatajclient.proxy.utils.ClassUtils;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

class AsyncContainer implements AbstractAsyncContainer {

    private static final long serialVersionUID = 7979064943616142288L;

    private final Container syncContainer;

    AsyncContainer(final EntityContainerFactory factory) {
        this.syncContainer = new Container(factory);
    }

    @Override
    public Future<Void> flush() {
        return Configuration.getExecutor().submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                syncContainer.flush();
                return ClassUtils.returnVoid();
            }
        });
    }
}
