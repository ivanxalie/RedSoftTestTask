package org.redsoft.test.accounting.config;

import lombok.AllArgsConstructor;
import org.redsoft.test.accounting.entities.Department;
import org.redsoft.test.accounting.entities.Employee;
import org.redsoft.test.accounting.entities.EmployeePosition;
import org.redsoft.test.accounting.repos.DepartmentRepository;
import org.redsoft.test.accounting.repos.EmployeePositionRepository;
import org.redsoft.test.accounting.repos.EmployeeRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class DbInitialize implements InitializingBean {
    private final EmployeePositionRepository employeePositionRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final Map<String, EmployeePosition> positions = Map.of("CEO", EmployeePosition.builder().name("CEO").salary(BigDecimal.valueOf(12_500)).build(), "CPO", EmployeePosition.builder().name("CPO").salary(BigDecimal.valueOf(10_000)).build(), "Director", EmployeePosition.builder().name("Director").salary(BigDecimal.valueOf(7_500)).build(), "Analyst", EmployeePosition.builder().name("Analyst").salary(BigDecimal.valueOf(6_300)).build(), "Engineer", EmployeePosition.builder().name("Engineer").salary(BigDecimal.valueOf(5_500)).build(), "Tester", EmployeePosition.builder().name("Tester").salary(BigDecimal.valueOf(4_500)).build());

    @Override
    public void afterPropertiesSet() {
        initEmployeesPositions();
        initEmployees();
    }

    private void initEmployeesPositions() {
        employeePositionRepository.saveAll(positions.values());
    }

    private void initEmployees() {
        Department itDepartment = departmentRepository.save(Department.builder().name("The Automator's Number One, Inc").phone("8 800 123 456 78").email("it@example.net").build());
        departmentRepository.save(Department.builder().name("Example Incorporation").phone("8 123 456 7899").email("it@example.net").build());
        employeeRepository.saveAll(List.of(Employee.builder().department(itDepartment).firstName("Alex").lastName("James").middleName("Greenfield").position(positions.get("CEO")).boss(true).build(), Employee.builder().department(itDepartment).firstName("James").lastName("Peter").middleName("Spring").position(positions.get("CPO")).build(), Employee.builder().department(itDepartment).firstName("Katy").lastName("Tamara").middleName("Winter").position(positions.get("Director")).build(), Employee.builder().department(itDepartment).firstName("Jean").lastName("Jay").middleName("Freedom").position(positions.get("Engineer")).build(), Employee.builder().department(itDepartment).firstName("Walter").lastName("William").middleName("Waltz").position(positions.get("Tester")).build()));
    }
}
