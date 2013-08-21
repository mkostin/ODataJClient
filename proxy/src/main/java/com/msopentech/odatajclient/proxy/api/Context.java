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

import java.util.HashMap;
import java.util.Map;

public class Context {

    private final Map<Class<?>, Class<?>> entitySetMapping = new HashMap<Class<?>, Class<?>>();

    private final EntityContext entities;

    private final LinkContext links;

    public Context() {
        this.entities = new EntityContext();
        this.links = new LinkContext();
        
    }

    public EntityContext getEntities() {
        return entities;
    }

    public LinkContext getLinks() {
        return links;
    }
}
