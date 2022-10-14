package org.redsoft.test.accounting.repos;

import org.redsoft.test.accounting.entities.EmployeePosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeePositionRepository extends JpaRepository<EmployeePosition, Long> {
}
