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
package com.msopentech.odatajclient.proxy.northwind.model;

import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.annotations.Key;
import com.msopentech.odatajclient.proxy.api.annotations.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EntityType("Employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 4486947286520681789L;

    @Key
    @Property(name = "EmployeeID", type = "Edm.Int32", nullable = false)
    private Integer EmployeeID;

    @Property(name = "LastName", type = "Edm.String", nullable = false)
    private String LastName;

    @Property(name = "FirstName", type = "Edm.String", nullable = false)
    private String FirstName;

    @Property(name = "Title", type = "Edm.String", nullable = true)
    private String Title;

    @Property(name = "TitleOfCourtesy", type = "Edm.String", nullable = true)
    private String TitleOfCourtesy;

    @Property(name = "BirthDate", type = "Edm.DateTime", nullable = true)
    private Date BirthDate;

    @Property(name = "HireDate", type = "Edm.DateTime", nullable = true)
    private Date HireDate;

    @Property(name = "Address", type = "Edm.String", nullable = true)
    private String Address;

    @Property(name = "City", type = "Edm.String", nullable = true)
    private String City;

    @Property(name = "Region", type = "Edm.String", nullable = true)
    private String Region;

    @Property(name = "PostalCode", type = "Edm.String", nullable = true)
    private String PostalCode;

    @Property(name = "Country", type = "Edm.String", nullable = true)
    private String Country;

    @Property(name = "HomePhone", type = "Edm.String", nullable = true)
    private String HomePhone;

    @Property(name = "Extension", type = "Edm.String", nullable = true)
    private String Extension;

    @Property(name = "Photo", type = "Edm.Binary", nullable = true)
    private Byte[] Photo;

    @Property(name = "Notes", type = "Edm.String", nullable = true)
    private String Notes;

    @Property(name = "ReportsTo", type = "Edm.Int32", nullable = true)
    private Integer ReportsTo;

    @Property(name = "PhotoPath", type = "Edm.String", nullable = true)
    private String PhotoPath;

    @NavigationProperty(name = "Employees1", relationship = "NorthwindModel.FK_Employees_Employees",
            fromRole = "Employees", toRole = "Employees1")
    private List<Employee> employees1;

    @NavigationProperty(name = "Employee1", relationship = "NorthwindModel.FK_Employees_Employees",
            fromRole = "Employees1", toRole = "Employees")
    private Employee employee1;

    @NavigationProperty(name = "Orders", relationship = "NorthwindModel.FK_Orders_Employees",
            fromRole = "Employees", toRole = "Orders")
    private List<Order> orders;

    @NavigationProperty(name = "Territories", relationship = "NorthwindModel.EmployeeTerritories",
            fromRole = "Employees", toRole = "Territories")
    private List<Territory> territories;

    public Integer getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(Integer EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getTitleOfCourtesy() {
        return TitleOfCourtesy;
    }

    public void setTitleOfCourtesy(String TitleOfCourtesy) {
        this.TitleOfCourtesy = TitleOfCourtesy;
    }

    public Date getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(Date BirthDate) {
        this.BirthDate = BirthDate;
    }

    public Date getHireDate() {
        return HireDate;
    }

    public void setHireDate(Date HireDate) {
        this.HireDate = HireDate;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String Region) {
        this.Region = Region;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String PostalCode) {
        this.PostalCode = PostalCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getHomePhone() {
        return HomePhone;
    }

    public void setHomePhone(String HomePhone) {
        this.HomePhone = HomePhone;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String Extension) {
        this.Extension = Extension;
    }

    public Byte[] getPhoto() {
        return Photo;
    }

    public void setPhoto(Byte[] Photo) {
        this.Photo = Photo;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String Notes) {
        this.Notes = Notes;
    }

    public Integer getReportsTo() {
        return ReportsTo;
    }

    public void setReportsTo(Integer ReportsTo) {
        this.ReportsTo = ReportsTo;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String PhotoPath) {
        this.PhotoPath = PhotoPath;
    }

    public List<Employee> getEmployees1() {
        return employees1;
    }

    public void setEmployees1(List<Employee> employees1) {
        this.employees1 = employees1;
    }

    public Employee getEmployee1() {
        return employee1;
    }

    public void setEmployee1(Employee employee1) {
        this.employee1 = employee1;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(List<Territory> territories) {
        this.territories = territories;
    }
}
