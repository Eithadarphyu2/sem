package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

/**
 * Main application class.
 */
public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/employees?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " +                                  Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Gets an employee using their employee number.
     *
     * @param ID Employee number.
     * @return Employee object, or null if not found.
     */
    public Employee getEmployee(int ID)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT emp_no, first_name, last_name "
                            + "FROM employees "
                            + "WHERE emp_no = " + ID;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Return new employee if valid
            if (rset.next())
            {
                Employee emp = new Employee();

                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");

                return emp;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    /**
     * Gets all current employees and their salaries.
     *
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<Employee> getAllSalaries()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries "
                            + "WHERE employees.emp_no = salaries.emp_no "
                            + "AND salaries.to_date = '9999-01-01' "
                            + "ORDER BY employees.emp_no ASC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create list to store employees
            ArrayList<Employee> employees = new ArrayList<Employee>();

            // Extract employee information
            while (rset.next())
            {
                Employee emp = new Employee();

                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");

                employees.add(emp);
            }

            return employees;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    /**
     * Prints a list of employees and their salaries.
     *
     * @param employees The list of employees to print.
     */
    public void printSalaries(ArrayList<Employee> employees)
    {
        // Check employees is not null
        if (employees == null)
        {
            System.out.println("No employees");
            return;
        }
        // Print header
        System.out.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
        // Loop over all employees in the list
        for (Employee emp : employees)
        {
            if (emp == null)
                continue;
            String emp_string =
                    String.format("%-10s %-15s %-20s %-8s",
                            emp.emp_no, emp.first_name, emp.last_name, emp.salary);
            System.out.println(emp_string);
        }
    }

    /**
     * Gets a department using its name.
     *
     * @param dept_name Department name.
     * @return Department object, or null if not found.
     */
    public Department getDepartment(String dept_name)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Get department details
            String strSelect =
                    "SELECT dept_no, dept_name "
                            + "FROM departments "
                            + "WHERE dept_name = '" + dept_name + "'";

            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next())
            {
                Department dept = new Department();

                dept.dept_no = rset.getString("dept_no");
                dept.dept_name = rset.getString("dept_name");

                // Get department manager
                String strManager =
                        "SELECT employees.emp_no, employees.first_name, employees.last_name "
                                + "FROM employees, dept_manager "
                                + "WHERE employees.emp_no = dept_manager.emp_no "
                                + "AND dept_manager.dept_no = '" + dept.dept_no + "' "
                                + "AND dept_manager.to_date = '9999-01-01'";

                ResultSet managerResult = stmt.executeQuery(strManager);

                if (managerResult.next())
                {
                    Employee manager = new Employee();

                    manager.emp_no = managerResult.getInt("employees.emp_no");
                    manager.first_name =
                            managerResult.getString("employees.first_name");
                    manager.last_name =
                            managerResult.getString("employees.last_name");

                    dept.manager = manager;
                }

                return dept;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get department details");
            return null;
        }
    }

    /**
     * Gets current salaries for all employees in a department.
     *
     * @param dept Department to search.
     * @return List of employees and their salaries.
     */
    public ArrayList<Employee> getSalariesByDepartment(Department dept)
    {
        ArrayList<Employee> employees = new ArrayList<>();

        try
        {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, " +
                            "employees.last_name, salaries.salary " +
                            "FROM employees, salaries, dept_emp, departments " +
                            "WHERE employees.emp_no = salaries.emp_no " +
                            "AND employees.emp_no = dept_emp.emp_no " +
                            "AND dept_emp.dept_no = departments.dept_no " +
                            "AND salaries.to_date = '9999-01-01' " +
                            "AND departments.dept_no = '" + dept.dept_no + "' " +
                            "ORDER BY employees.emp_no ASC";

            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next())
            {
                Employee employee = new Employee();

                employee.emp_no = rset.getInt("emp_no");
                employee.first_name = rset.getString("first_name");
                employee.last_name = rset.getString("last_name");
                employee.salary = rset.getInt("salary");

                employees.add(employee);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error retrieving salaries by department");
            e.printStackTrace();
        }

        return employees;
    }

    public void deleteEmployee(int empNo) {
        String sql = "DELETE FROM employees WHERE emp_no = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, empNo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete employee", e);
        }
    }

    /**
     * Displays one employee's information.
     *
     * @param emp Employee to display.
     */
    public void displayEmployee(Employee emp)
    {
        if (emp != null)
        {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary:" + emp.salary + "\n"
                            + emp.dept + "\n"
                            + "Manager: " + emp.manager + "\n");
        }
    }


    public void addEmployee(Employee emp)
    {
        String sql =
                "INSERT INTO employees " +
                        "(emp_no, first_name, last_name, birth_date, gender, hire_date) " +
                        "VALUES (?, ?, ?, '9999-01-01', 'M', '9999-01-01')";

        try (PreparedStatement stmt = con.prepareStatement(sql))
        {
            stmt.setInt(1, emp.emp_no);
            stmt.setString(2, emp.first_name);
            stmt.setString(3, emp.last_name);

            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Failed to add employee", e);
        }
    }


    /**
     * Main application method.
     */
    public static void main(String[] args) {
        // Create new Application and connect to database
        App a = new App();

        if(args.length < 1){
            a.connect("localhost:33060", 30000);
        }else{
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        Department dept = a.getDepartment("Development");
        ArrayList<Employee> employees = a.getSalariesByDepartment(dept);


        // Print salary report
        a.printSalaries(employees);

        // Disconnect from database
        a.disconnect();
    }
}