package org.redsoft.test.accounting.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean boss;
    @NotBlank(message = "First Name is compulsory!")
    private String firstName;
    @NotBlank(message = "Last Name is compulsory!")
    private String lastName;
    private String middleName;
    @OneToOne
    @NotNull(message = "Every employee must have position!")
    private EmployeePosition position;
    @ManyToOne
    @NotNull(message = "Every employee must belongs to department!")
    @ToString.Exclude
    private Department department;

    @Column(updatable = false, insertable = false, name = "position_name")
    private String positionName;

    @Column(updatable = false, insertable = false, name = "salary")
    private BigDecimal salary;

    public String getPositionName() {
        return position.getName();
    }

    public BigDecimal getSalary() {
        return position.getSalary();
    }

    public void removeFromDepartment() {
        getDepartment().remove(this);
    }
}
