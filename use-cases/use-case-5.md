# USE CASE: 5 Add New Employee Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to add new employee details* so that *the new employee is paid.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The HR advisor has the new employee's details. The employee does not already exist in the database.

### Success End Condition

The new employee's details are stored in the database.

### Failed End Condition

The new employee's details are not added.

### Primary Actor

HR Advisor.

### Trigger

A new employee joins the company and their details need to be added to the system.

## MAIN SUCCESS SCENARIO

1. HR advisor requests to add a new employee.
2. HR advisor enters the new employee's details.
3. System validates the employee details.
4. System adds the new employee's details to the database.
5. System confirms that the employee has been added successfully.

## EXTENSIONS

3. **Employee details are invalid**:
    1. System informs the HR advisor that the details are invalid.
    2. HR advisor corrects the details.

3. **Employee already exists**:
    1. System informs the HR advisor that the employee already exists.
    2. Employee details are not added.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0