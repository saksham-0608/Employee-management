package com.saksham.employee_management.service;
import com.saksham.employee_management.entity.Department;
import com.saksham.employee_management.repository.DepartmentRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.saksham.employee_management.dto.EmployeeRequestDTO;
import com.saksham.employee_management.dto.EmployeeResponseDTO;
import com.saksham.employee_management.exception.ResourceNotFoundException;
import com.saksham.employee_management.repository.EmployeeRepository;
import com.saksham.employee_management.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

private final EmployeeRepository employeeRepository;

private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    //DTO Object
    public EmployeeResponseDTO convertToDTO(Employee employee) {

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        if (employee.getDepartment() != null) {
            dto.setDepartmentId(employee.getDepartment().getId());
            dto.setDepartmentName(employee.getDepartment().getName());
        }
        dto.setSalary(employee.getSalary());

        return dto;
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponseDTO> getAllEmployees(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size,sort);

        return employeeRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public Employee createEmployee(EmployeeRequestDTO dto) {

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + dto.getDepartmentId()));

        Employee employee = new Employee();

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    @Transactional(readOnly=true)
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    @Transactional(readOnly=true)
    public Optional<EmployeeResponseDTO> getEmployeeById(Long id){
        return employeeRepository.findById(id).stream().map(this::convertToDTO).findFirst();
    }

    @Transactional(readOnly=true)
    public List<EmployeeResponseDTO> getEmployeeByDepartment(String department) {
        return employeeRepository.findByDepartment(department).stream().map(this::convertToDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> getEmployeeByDepartmentIgnoreCase(String department) {

        return employeeRepository.findByDepartment_NameIgnoreCase(department)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> getEmployeeBySalaryBetween(Double salaryStart, Double salaryEnd) {
        return employeeRepository.findBySalaryBetween(salaryStart, salaryEnd).stream().map(this::convertToDTO).toList();
    }


    @Transactional
    public Employee updateEmployee(Long id, EmployeeRequestDTO dto) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + id));

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + dto.getDepartmentId()));

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }


    public void deleteEmployee(Long id)  {
     Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found with id: "+id));
     employeeRepository.delete(employee);
    }
}

