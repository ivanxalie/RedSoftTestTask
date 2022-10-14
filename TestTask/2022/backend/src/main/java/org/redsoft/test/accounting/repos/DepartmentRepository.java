package org.redsoft.test.accounting.repos;

import org.redsoft.test.accounting.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
