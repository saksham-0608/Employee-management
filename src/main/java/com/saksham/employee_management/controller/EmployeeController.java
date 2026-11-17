package com.saksham.employee_management.controller;

import com.saksham.employee_management.dto.EmployeeRequestDTO;
import com.saksham.employee_management.dto.EmployeeResponseDTO;
import com.saksham.employee_management.entity.Employee;
import com.saksham.employee_management.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

private final EmployeeService employeeService;

 @Autowired
 public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @PostMapping()
  public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO employee) {
      Employee savedEmployee= employeeService.createEmployee(employee);
      EmployeeResponseDTO dtoEmployee = employeeService.convertToDTO(savedEmployee);

     return new ResponseEntity<>(dtoEmployee , HttpStatus.CREATED);
  }

  @GetMapping()
  public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
    List<EmployeeResponseDTO> employees= employeeService.getAllEmployees();
    return new ResponseEntity<>(employees, HttpStatus.OK);
  }

    @GetMapping("/page")
    public ResponseEntity<Page<EmployeeResponseDTO>> getEmployeesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "salary") String sortBy,
            @RequestParam(defaultValue = "desc") String direction ) {

        Page<EmployeeResponseDTO> employees =
                employeeService.getAllEmployees(page, size,sortBy,direction);

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(
            @PathVariable("id") Long id) {

        Optional<EmployeeResponseDTO> employee =
                employeeService.getEmployeeById(id);

        return employee
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeeByDepartment(@RequestParam String department) {
     List<EmployeeResponseDTO> employees= employeeService.getEmployeeByDepartment(department);
     return new ResponseEntity<>(employees,HttpStatus.OK);
    }

    @GetMapping("/search/{department}")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeeByDepartmentIgnoreCase(
            @PathVariable String department) {

        List<EmployeeResponseDTO> employees =
                employeeService.getEmployeeByDepartmentIgnoreCase(department);

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("search/salary")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeeBySalaryBetween(@RequestParam("min") Double salaryStart,@RequestParam("max") Double salaryEnd) {
     List<EmployeeResponseDTO> employees= employeeService.getEmployeeBySalaryBetween(salaryStart, salaryEnd);
     return new ResponseEntity<>(employees,HttpStatus.OK);
    }

  @PutMapping("/{id}")
  public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id,@Valid @RequestBody EmployeeRequestDTO employee)  {
   Employee employee1=employeeService.updateEmployee(id,employee);
   EmployeeResponseDTO employee2=employeeService.convertToDTO(employee1);
   return new ResponseEntity<>(employee2,HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
   employeeService.deleteEmployee(id);
      return ResponseEntity.noContent().build();
  }
}
