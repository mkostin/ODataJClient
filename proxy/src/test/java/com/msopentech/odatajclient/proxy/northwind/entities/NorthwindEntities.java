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
package com.msopentech.odatajclient.proxy.northwind.entities;

import com.msopentech.odatajclient.proxy.api.annotations.EntityContainer;

@EntityContainer(name = "NorthwindEntities", isDefaultEntityContainer = true)
public interface NorthwindEntities {

    Categories getCategories();

    CustomerDemographics getCustomerDemographics();

    Customers getCustomers();

    Employees getEmployees();

    OrderDetails getOrderDetails();

    Orders getOrders();

    Products getProducts();

    Regions getRegions();

    Shippers getShippers();

    Suppliers getSuppliers();

    Territories getTerritories();

    AlphabeticalListOfProducts getAlphabeticalListOfProducts();

    CategorySalesFor1997 getCategorySalesFor1997();

    CurrentProductLists getCurrentProductLists();

    CustomerAndSuppliersByCities getCustomerAndSuppliersByCities();

    Invoices getInvoices();

    OrderDetailsExtendeds getOrderDetailsExtendeds();

    OrderSubtotals getOrderSubtotals();

    OrdersQries getOrdersQries();

    ProductSalesFor1997 getProductSalesFor1997();

    ProductsAboveAveragePrices getProductsAboveAveragePrices();

    ProductsByCategories getProductsByCategories();

    SalesByCategories getSalesByCategories();

    SalesTotalsByAmounts getSalesTotalsByAmounts();

    SummaryOfSalesByQuarters getSummaryOfSalesByQuarters();

    SummaryOfSalesByYears getSummaryOfSalesByYears();
}
