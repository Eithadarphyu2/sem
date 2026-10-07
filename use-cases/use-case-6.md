# USE CASE: 6 View Employee Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to view employee details* so that *I can support a promotion request.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The employee exists in the database. The HR advisor knows the employee ID.

### Success End Condition

The employee's details are displayed to the HR advisor.

### Failed End Condition

The employee's details cannot be displayed.

### Primary Actor

HR Advisor.

### Trigger

A promotion request requires the employee's details.

## MAIN SUCCESS SCENARIO

1. HR advisor requests the details of an employee.
2. HR advisor enters the employee ID.
3. System searches for the employee in the database.
4. System retrieves the employee's details.
5. System displays the employee's details to the HR advisor.

## EXTENSIONS

3. **Employee does not exist**:
    1. System informs the HR advisor that the employee cannot be found.
    2. No employee details are displayed.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0