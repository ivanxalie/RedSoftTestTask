package org.redsoft.test.accounting.repos;

import org.redsoft.test.accounting.entities.Department;
import org.redsoft.test.accounting.entities.Employee;
import org.redsoft.test.accounting.entities.EmployeePosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment(Department department);

    List<Employee> findByPosition(EmployeePosition position);
}
