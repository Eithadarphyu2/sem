# USE CASE: 3 Produce a Report on the Salary of Employees in My Department

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *department manager* I want *to produce a report on the salary of employees in my department* so that *I can support departmental financial reporting.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The department manager belongs to a department. Database contains current employee salary data.

### Success End Condition

A report is available for the department manager.

### Failed End Condition

No report is produced.

### Primary Actor

Department Manager.

### Trigger

A request for departmental financial information is made.

## MAIN SUCCESS SCENARIO

1. Department manager requests salary information for their department.
2. System identifies the department of the department manager.
3. System extracts current salary information of employees in that department.
4. System provides the report to the department manager.

## EXTENSIONS

3. **No employees are found in the department**:
    1. System informs the department manager that no employee salary information is available.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0