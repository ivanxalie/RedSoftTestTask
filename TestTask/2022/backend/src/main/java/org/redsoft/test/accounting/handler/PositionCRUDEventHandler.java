package org.redsoft.test.accounting.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redsoft.test.accounting.entities.Department;
import org.redsoft.test.accounting.entities.Employee;
import org.redsoft.test.accounting.entities.EmployeePosition;
import org.redsoft.test.accounting.entities.PositionAssignedToEmployeeException;
import org.redsoft.test.accounting.repos.EmployeeRepository;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Validated
@Component
@RepositoryEventHandler
@AllArgsConstructor
@Slf4j
public class PositionCRUDEventHandler {
    private final EmployeeRepository employeeRepository;

    @HandleBeforeDelete
    public void onBeforeDelete(@Valid EmployeePosition position) {
        Map<Department, List<Employee>> employeesWithAssignedPosition = employeeRepository.findByPosition(position)
                .stream().collect(Collectors.groupingBy(Employee::getDepartment));
        if (!employeesWithAssignedPosition.isEmpty())
            throw new PositionAssignedToEmployeeException(position, employeesWithAssignedPosition);
    }
}
