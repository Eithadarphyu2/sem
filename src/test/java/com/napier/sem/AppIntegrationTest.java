package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AppIntegrationTest
{
    private static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        // For local runs, the database must be running and exposed on host port 33060.
        // GitHub Actions starts the database container with the same port mapping.
        app.connect("localhost:33060", 1000);
    }

    @AfterAll
    static void cleanup()
    {
        if (app != null)
        {
            // Remove the fixed test record if the test failed before its normal cleanup.
            try
            {
                app.deleteEmployee(500000);
            }
            catch (RuntimeException ignored)
            {
                // Keep cleanup from hiding the test result if the database is unavailable.
            }
            app.disconnect();
        }
    }

    @Test
    void testGetEmployee()
    {
        Employee emp = app.getEmployee(255530);

        assertNotNull(emp);
        assertEquals(255530, emp.emp_no);
        assertEquals("Ronghao", emp.first_name);
        assertEquals("Garigliano", emp.last_name);
    }

    @Test
    void testGetEmployeeNotFound()
    {
        assertNull(app.getEmployee(9999999));
    }

    @Test
    void testGetDepartment()
    {
        Department dept = app.getDepartment("Development");

        assertNotNull(dept);
        assertEquals("Development", dept.dept_name);
        assertNotNull(dept.dept_no);
    }

    @Test
    void testGetDepartmentNotFound()
    {
        assertNull(app.getDepartment("DepartmentDoesNotExist"));
    }

    @Test
    void testGetSalariesByDepartment()
    {
        Department dept = app.getDepartment("Development");
        assertNotNull(dept);

        ArrayList<Employee> employees = app.getSalariesByDepartment(dept);

        assertNotNull(employees);
        assertFalse(employees.isEmpty());
        assertTrue(employees.stream().allMatch(e -> e != null));
    }

    @Test
    void testGetAllSalaries()
    {
        ArrayList<Employee> employees = app.getAllSalaries();

        assertNotNull(employees);
        assertFalse(employees.isEmpty());
    }

    @Test
    void testAddEmployee()
    {
        int empNo = 500000;

        // Make the test repeatable if a previous run left this row behind.
        app.deleteEmployee(empNo);

        Employee employee = new Employee();
        employee.emp_no = empNo;
        employee.first_name = "Kevin";
        employee.last_name = "Chalmers";

        try
        {
            app.addEmployee(employee);

            Employee result = app.getEmployee(empNo);
            assertNotNull(result);
            assertEquals(empNo, result.emp_no);
            assertEquals("Kevin", result.first_name);
            assertEquals("Chalmers", result.last_name);
        }
        finally
        {
            app.deleteEmployee(empNo);
        }
    }

    @Test
    void testDeleteEmployee()
    {
        int empNo = 500001;

        app.deleteEmployee(empNo);

        Employee employee = new Employee();
        employee.emp_no = empNo;
        employee.first_name = "Delete";
        employee.last_name = "Test";

        app.addEmployee(employee);
        assertNotNull(app.getEmployee(empNo));

        app.deleteEmployee(empNo);
        assertNull(app.getEmployee(empNo));
    }
}
