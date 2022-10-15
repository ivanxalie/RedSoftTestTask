package org.redsoft.test.accounting.entities;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PositionAssignedToEmployeeException extends EntityCustomException {
    public PositionAssignedToEmployeeException(EmployeePosition position, Map<Department,
            List<Employee>> employeesWithAssignedPosition) {
        super(formatMessage(position, employeesWithAssignedPosition));
    }

    public PositionAssignedToEmployeeException(String name) {
        super(String.format("The position has already assigned for other employee in department \"%s\"!", name));
    }

    private static String formatMessage(EmployeePosition position, Map<Department,
            List<Employee>> employeesWithAssignedPosition) {
        StringBuilder message = new StringBuilder();
        message
                .append("Cannot delete position \"")
                .append(position.getName())
                .append("\"")
                .append(" because it contains in next employees:\n");
        employeesWithAssignedPosition.forEach((department, employees) -> {
            message.append(department.getName()).append(":").append("\n");
            message.append(employees.stream()
                    .map(employee ->
                            employee.getLastName() + " " + employee.getFirstName())
                    .collect(Collectors.joining(",\n\t-", "\t-", "\n")));
        });
        message.append("Either delete this employees or change their position!");
        return message.toString();
    }
}
