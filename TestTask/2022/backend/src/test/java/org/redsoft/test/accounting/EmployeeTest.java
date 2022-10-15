package org.redsoft.test.accounting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redsoft.test.accounting.entities.*;
import org.redsoft.test.accounting.handler.EmployeeCRUDEventHandler;
import org.redsoft.test.accounting.repos.DepartmentRepository;
import org.redsoft.test.accounting.repos.EmployeePositionRepository;
import org.redsoft.test.accounting.repos.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ValidationException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeTest {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeePositionRepository employeePositionRepository;
    @Autowired
    EmployeeCRUDEventHandler employeeCRUDEventHandler;

    Department department;
    EmployeePosition testPosition;

    @BeforeEach
    void beforeEach() {
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();
        employeePositionRepository.deleteAll();
        department = Department.builder()
                .name("Test department")
                .build();
        testPosition = EmployeePosition.builder().name("Test").salary(BigDecimal.ZERO).build();
        departmentRepository.save(department);
        employeePositionRepository.save(testPosition);
    }

    @Test
    void testNotNull() {
        assertNotNull(employeeRepository);
        assertNotNull(employeePositionRepository);
        assertNotNull(departmentRepository);
    }

    @Test
    void departmentIsMandatory() {
        assertThrows(ValidationException.class, () ->
                save("Test", "Test", true, testPosition, null));
    }

    @Test
    void positionIsMandatory() {
        assertThrows(ValidationException.class, () ->
                save("Test", "Test", true, null, department));
    }

    @Test
    void onlyOnePositionPerDepartment() {
        save("Test", "Test", true, testPosition, department);
        assertThrows(PositionAssignedToEmployeeException.class, () ->
                save("Test", "Test", false, testPosition, department));
    }

    @Test
    void testOnlyOneBossForDepartmentAtTheSameTime() {
        save("Alex", "Dense", true);
        EmployeePosition otherTest = EmployeePosition.builder().name("OtherTest").salary(BigDecimal.ZERO).build();
        employeePositionRepository.save(otherTest);
        Employee notBoss = save("Henry", "Riverdale", false,
                otherTest, department);
        assertThrows(BossAlreadyAssignedException.class, () ->
                save("Andrew", "Filler", true));
        assertThrows(BossAlreadyAssignedException.class, () -> {
            try {
                notBoss.setBoss(true);
                employeeCRUDEventHandler.onUpdate(notBoss);
                employeeRepository.save(notBoss);
            } catch (BossAlreadyAssignedException e) {
                notBoss.setBoss(false);
                throw e;
            }
        });
        assertFalse(notBoss.isBoss());
    }

    private Employee save(String firstName, String lastName, boolean isBoss) {
        return save(firstName, lastName, isBoss, testPosition, department);
    }

    private Employee save(String firstName, String lastName, boolean isBoss,
                          EmployeePosition position, Department department) {
        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .boss(isBoss)
                .position(position)
                .department(department)
                .build();
        employeeCRUDEventHandler.onCreate(employee);
        employeeRepository.save(employee);
        return employee;
    }
}
