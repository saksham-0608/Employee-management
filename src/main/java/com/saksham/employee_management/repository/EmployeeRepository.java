package com.saksham.employee_management.repository;

import com.saksham.employee_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

public List<Employee> findByDepartment(String department);

    List<Employee> findByDepartment_NameIgnoreCase(String department);
public List<Employee> findBySalaryBetween(Double salaryStart, Double salaryEnd);
}
