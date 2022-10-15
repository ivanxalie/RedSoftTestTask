package org.redsoft.test.accounting.repos;

import org.redsoft.test.accounting.entities.EmployeePosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeePositionRepository extends JpaRepository<EmployeePosition, Long> {
    List<EmployeePosition> findByNameIgnoringCase(String name);
}
