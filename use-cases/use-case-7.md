# USE CASE: 7 Update Employee Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to update employee details* so that *the employee details stay current.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The employee exists in the database. The HR advisor knows the employee ID.

### Success End Condition

The employee's updated details are stored in the database.

### Failed End Condition

The employee's details are not updated.

### Primary Actor

HR Advisor.

### Trigger

Employee information needs to be changed.

## MAIN SUCCESS SCENARIO

1. HR advisor requests to update an employee's details.
2. HR advisor enters the employee ID.
3. System searches for the employee in the database.
4. System displays the employee's current details.
5. HR advisor enters the updated details.
6. System validates the updated details.
7. System saves the updated employee details to the database.
8. System confirms that the employee details have been updated successfully.

## EXTENSIONS

3. **Employee does not exist**:
    1. System informs the HR advisor that the employee cannot be found.
    2. Employee details are not updated.

6. **Updated details are invalid**:
    1. System informs the HR advisor that the details are invalid.
    2. HR advisor corrects the details.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0