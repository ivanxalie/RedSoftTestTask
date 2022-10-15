package org.redsoft.test.accounting.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redsoft.test.accounting.entities.*;
import org.redsoft.test.accounting.repos.EmployeePositionRepository;
import org.redsoft.test.accounting.repos.EmployeeRepository;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Validated
@Component
@RepositoryEventHandler
@AllArgsConstructor
@Slf4j
public class PositionCRUDEventHandler {
    private final EmployeeRepository employeeRepository;

    private final EmployeePositionRepository employeePositionRepository;

    @HandleBeforeCreate
    public void onCreate(@Valid EmployeePosition position) {
        employeePositionRepository.findByNameIgnoringCase(position.getName())
                .stream().filter(Predicate.not(isTheSamePosition(position)))
                .findFirst()
                .ifPresent(alreadyCreatedPosition -> {
                    throw new PositionAlreadyCreatedException(alreadyCreatedPosition.getName());
                });
    }

    @HandleBeforeSave
    public void onUpdate(@Valid EmployeePosition position) {
        onCreate(position);
    }

    private Predicate<EmployeePosition> isTheSamePosition(EmployeePosition position) {
        return candidate -> candidate.getId().equals(position.getId());
    }

    @HandleBeforeDelete
    public void onBeforeDelete(@Valid EmployeePosition position) {
        Map<Department, List<Employee>> employeesWithAssignedPosition = employeeRepository.findByPosition(position)
                .stream().collect(Collectors.groupingBy(Employee::getDepartment));
        if (!employeesWithAssignedPosition.isEmpty())
            throw new PositionAssignedToEmployeeException(position, employeesWithAssignedPosition);
    }
}
