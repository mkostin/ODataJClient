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
import java.io.Serializable;
import java.util.Date;

@EntityType("Product")
public class Product implements Serializable {

    private static final long serialVersionUID = -7176997693842768563L;

    @Key
    @Property(name = "ID", type = "Edm.Int32", nullable = false)
    private Integer id;

    @Property(name = "Name", type = "Edm.String", nullable = true,
            fcTargetPath = "SyndicationTitle", fcContentKind = EdmContentKind.text, fcKeepInContent = false)
    private String name;

    @Property(name = "Description", type = "Edm.String", nullable = true,
            fcTargetPath = "SyndicationSummary", fcContentKind = EdmContentKind.text, fcKeepInContent = false)
    private String description;

    @Property(name = "ReleaseDate", type = "Edm.DateTime", nullable = false)
    private Date releaseDate;

    @Property(name = "DiscontinuedDate", type = "Edm.DateTime", nullable = true)
    private Date discontinuedDate;

    @Property(name = "Rating", type = "Edm.Int32", nullable = false)
    private Integer rating;

    @Property(name = "Price", type = "Edm.Decimal", nullable = false)
    private Float price;

    @NavigationProperty(name = "Category", relationship = "ODataDemo.Product_Category_Category_Products",
            fromRole = "Product_Category", toRole = "Category_Products")
    private Category category;

    @NavigationProperty(name = "Supplier", relationship = "ODataDemo.Product_Supplier_Supplier_Products",
            fromRole = "Product_Supplier", toRole = "Supplier_Products")
    private Supplier supplier;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getDiscontinuedDate() {
        return discontinuedDate;
    }

    public void setDiscontinuedDate(Date discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
