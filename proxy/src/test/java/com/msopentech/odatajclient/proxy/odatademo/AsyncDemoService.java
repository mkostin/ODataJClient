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
package com.msopentech.odatajclient.proxy.odatademo;

import com.msopentech.odatajclient.proxy.api.EntityContainer;
import com.msopentech.odatajclient.proxy.api.FunctionImport;
import com.msopentech.odatajclient.proxy.api.Parameter;
import com.msopentech.odatajclient.proxy.api.types.ParameterMode;
import java.util.Collection;
import java.util.concurrent.Future;

@EntityContainer(name = "DemoService", isDefaultEntityContainer = true)
public interface AsyncDemoService {

    AsyncProducts getProducts();

    AsyncCategories getCategories();

    AsyncSuppliers getSuppliers();

    @FunctionImport(name = "GetProductsByRating", entitySet = Products.class,
            returnType = "Collection(ODataDemo.Product)")
    Future<Collection<Product>> getProductsByRating(
            @Parameter(name = "rating", type = "Edm.Int32", mode = ParameterMode.In) Integer rating);
}
