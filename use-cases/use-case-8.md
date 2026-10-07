# USE CASE: 8 Delete Employee Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to delete employee details* so that *the company complies with data retention legislation.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The employee exists in the database. The HR advisor knows the employee ID.

### Success End Condition

The employee's details are deleted from the database.

### Failed End Condition

The employee's details are not deleted.

### Primary Actor

HR Advisor.

### Trigger

An employee's details need to be deleted in accordance with data retention requirements.

## MAIN SUCCESS SCENARIO

1. HR advisor requests to delete an employee's details.
2. HR advisor enters the employee ID.
3. System searches for the employee in the database.
4. System displays the employee's details for confirmation.
5. HR advisor confirms that the employee's details should be deleted.
6. System deletes the employee's details from the database.
7. System confirms that the employee's details have been deleted successfully.

## EXTENSIONS

3. **Employee does not exist**:
    1. System informs the HR advisor that the employee cannot be found.
    2. Employee details are not deleted.

5. **HR advisor does not confirm deletion**:
    1. System cancels the deletion.
    2. Employee details remain in the database.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0