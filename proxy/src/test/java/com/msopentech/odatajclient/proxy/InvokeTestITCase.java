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
package com.msopentech.odatajclient.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.ContactDetails;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Customer;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.CustomerCollection;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Employee;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.EmployeeCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class InvokeTestITCase extends AbstractTest {

    @Test
    public void getWithNoParams() {
        // 1. primitive result
        final String string = container.getPrimitiveString();
        assertEquals("Foo", string);

        // 2. complex collection result
        final Collection<ContactDetails> details = container.entityProjectionReturnsCollectionOfComplexTypes();
        assertFalse(details.isEmpty());
        for (ContactDetails detail : details) {
            assertNotNull(detail);
        }
        assertNotNull(details.iterator().next());
    }

    @Test
    public void getWithParam() {
        // 1. primitive result
        assertEquals(155, container.getArgumentPlusOne(154).intValue());

        // 2. entity collection result
        final CustomerCollection customers = container.getSpecificCustomer(StringUtils.EMPTY);
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertEquals(1, customers.size());
        for (Customer customer : customers) {
            assertNotNull(customer);
        }
        final Customer customer = customers.iterator().next();
        assertEquals(-8, customer.getCustomerId().intValue());
    }

    @Test
    public void entityBoundPost() {
        // 0. create an employee
        final Integer id = 101;

        Employee employee = container.getPerson().newEmployee();
        employee.setPersonId(id);
        employee.setName("sample employee from proxy");
        employee.setManagersPersonId(-9918);
        employee.setSalary(2147483647);
        employee.setTitle("CEO");

        container.flush();

        employee = container.getPerson().get(id, Employee.class);
        assertNotNull(employee);
        assertEquals(id, employee.getPersonId());

        try {
            // 1. invoke action bound with the employee just created
            employee.sack();

            // 2. check that invoked action has effectively run
            employee = container.getPerson().get(id, Employee.class);
            assertEquals(0, employee.getSalary().intValue());
            assertTrue(employee.getTitle().endsWith("[Sacked]"));
        } finally {
            // 3. remove the test employee
            container.getPerson().delete(Collections.<Employee>singleton(employee));
            container.flush();
        }
    }

    @Test
    public void entityCollectionBoundPostWithParam() {
        EmployeeCollection employees = container.getPerson().getAllEmployee();
        assertFalse(employees.isEmpty());
        final Map<Integer, Integer> preSalaries = new HashMap<Integer, Integer>(employees.size());
        for (Employee employee : employees) {
            preSalaries.put(employee.getPersonId(), employee.getSalary());
        }
        assertFalse(preSalaries.isEmpty());

        employees.increaseSalaries(1);
        container.flush();

        employees = container.getPerson().getAllEmployee();
        assertFalse(employees.isEmpty());
        for (Employee employee : employees) {
            assertEquals(preSalaries.get(employee.getPersonId()) + 1, employee.getSalary().intValue());
        }
    }
}
