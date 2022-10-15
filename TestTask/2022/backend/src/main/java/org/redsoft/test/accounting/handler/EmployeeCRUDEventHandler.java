package org.redsoft.test.accounting.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redsoft.test.accounting.entities.BossAlreadyAssignedException;
import org.redsoft.test.accounting.entities.Employee;
import org.redsoft.test.accounting.entities.PositionAssignedToEmployeeException;
import org.redsoft.test.accounting.repos.EmployeeRepository;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

@Validated
@Component
@RepositoryEventHandler
@AllArgsConstructor
@Slf4j
public class EmployeeCRUDEventHandler {
    private final EmployeeRepository employeeRepository;

    @HandleBeforeCreate
    public void onCreate(@Valid Employee employee) {
        if (employee.isBoss())
            checkIfBossAlreadyAssign(employee);
        checkIfPositionAlreadyAssign(employee);
    }

    private void checkIfPositionAlreadyAssign(Employee employee) {
        employeeRepository
                .findByDepartmentAndPosition(employee.getDepartment(), employee.getPosition())
                .stream().filter(not(isTheSameEmployee(employee)))
                .findFirst()
                .ifPresent(employeeWithAssignedPosition -> {
                    throw new PositionAssignedToEmployeeException(employee.getDepartment().getName());
                });
    }

    @HandleBeforeSave
    public void onUpdate(@Valid Employee employee) {
        onCreate(employee);
    }

    @HandleBeforeDelete
    public void onDelete(@Valid Employee employee) {
        employee.removeFromDepartment();
    }

    private void checkIfBossAlreadyAssign(@Valid Employee employee) {
        employeeRepository
                .findByDepartment(employee.getDepartment())
                .stream()
                .filter(Employee::isBoss)
                .filter(not(isTheSameEmployee(employee)))
                .findFirst()
                .ifPresent(boss -> {
                    throw new BossAlreadyAssignedException(boss.getDepartment().getName());
                });
    }

    private Predicate<Employee> isTheSameEmployee(Employee employee) {
        return candidate -> candidate.getId().equals(employee.getId());
    }
}
