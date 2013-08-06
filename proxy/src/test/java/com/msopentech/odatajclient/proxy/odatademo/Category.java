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

import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.annotations.Key;
import com.msopentech.odatajclient.proxy.api.annotations.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import com.msopentech.odatajclient.engine.data.metadata.EdmContentKind;
import com.msopentech.odatajclient.proxy.api.impl.AbstractType;
import java.util.List;

@EntityType("Category")
public class Category extends AbstractType {

    private static final long serialVersionUID = 7872557425262013627L;

    @Key
    @Property(name = "ID", type = "Edm.Int32", nullable = false)
    private Integer id;

    @Property(name = "Name", type = "Edm.String", nullable = true,
            fcTargetPath = "SyndicationTitle", fcContentKind = EdmContentKind.text, fcKeepInContent = true)
    private String name;

    @NavigationProperty(name = "Products", relationship = "ODataDemo.Product_Category_Category_Products",
            fromRole = "Category_Products", toRole = "Product_Category")
    private List<Product> products;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(
            List<Product> products) {
        this.products = products;
    }
}
