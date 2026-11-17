package com.saksham.employee_management.service;
import java.util.List;
import java.util.Optional;

import com.saksham.employee_management.dto.EmployeeResponseDTO;
import com.saksham.employee_management.dto.EmployeeRequestDTO;
import com.saksham.employee_management.entity.Employee;
import com.saksham.employee_management.entity.Department;
import com.saksham.employee_management.exception.ResourceNotFoundException;
import com.saksham.employee_management.dto.EmployeeRequestDTO;
import com.saksham.employee_management.entity.Employee;
import com.saksham.employee_management.entity.Department;
import com.saksham.employee_management.exception.ResourceNotFoundException;
import com.saksham.employee_management.repository.DepartmentRepository;
import com.saksham.employee_management.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/*
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testCreateEmployee() {

        // Arrange: Prepare test data
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setName("Rahul");
        dto.setEmail("rahul@gmail.com");
        dto.setSalary(50000.0);
        dto.setDepartmentId(1L);

        Department department = new Department();
        department.setId(1L);
        department.setName("IT");

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        when(employeeRepository.save(any(Employee.class)))
                .thenAnswer(invocation -> {
                    Employee employee = invocation.getArgument(0);
                    employee.setId(1L);
                    return employee;
                });

        // Act: Execute actual service method
        Employee result = employeeService.createEmployee(dto);

        // Assert: Verify results
        assertEquals(1L, result.getId());
        assertEquals("Rahul", result.getName());
        assertEquals("rahul@gmail.com", result.getEmail());
        assertEquals(50000.0, result.getSalary());

        verify(departmentRepository).findById(1L);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void testCreateEmployeeDepartmentNotFound() {

        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setName("Rahul");
        dto.setEmail("rahul@gmail.com");
        dto.setSalary(50000.0);
        dto.setDepartmentId(99L);

        when(departmentRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                employeeService.createEmployee(dto)
        );

        verify(departmentRepository).findById(99L);

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    // 3. Get employee by ID - Success
    @Test
    void testGetEmployeeById() {
        Department department = new Department();
        department.setId(1L);
        department.setName("IT");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Rahul");
        employee.setEmail("rahul@gmail.com");
        employee.setSalary(50000.0);
        employee.setDepartment(department);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        Optional<EmployeeResponseDTO> result =
                employeeService.getEmployeeById(1L);

        assertTrue(result.isPresent());
        assertEquals("Rahul", result.get().getName());
        assertEquals("rahul@gmail.com", result.get().getEmail());
        assertEquals("IT", result.get().getDepartmentName());

        verify(employeeRepository).findById(1L);
    }


    // 4. Get employee by ID - Not Found
    @Test
    void testGetEmployeeByIdNotFound() {
        when(employeeRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<EmployeeResponseDTO> result =
                employeeService.getEmployeeById(99L);

        assertTrue(result.isEmpty());

        verify(employeeRepository).findById(99L);
    }


    // 5. Get all employees - Success
    @Test
    void testGetAllEmployees() {
        Department department = new Department();
        department.setId(1L);
        department.setName("IT");

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("Rahul");
        employee1.setEmail("rahul@gmail.com");
        employee1.setSalary(50000.0);
        employee1.setDepartment(department);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Amit");
        employee2.setEmail("amit@gmail.com");
        employee2.setSalary(60000.0);
        employee2.setDepartment(department);

        when(employeeRepository.findAll())
                .thenReturn(List.of(employee1, employee2));

        List<EmployeeResponseDTO> result =
                employeeService.getAllEmployees();

        assertEquals(2, result.size());
        assertEquals("Rahul", result.get(0).getName());
        assertEquals("Amit", result.get(1).getName());

        verify(employeeRepository).findAll();
    }


    // 6. Get all employees - Empty List
    @Test
    void testGetAllEmployeesEmpty() {
        when(employeeRepository.findAll())
                .thenReturn(List.of());

        List<EmployeeResponseDTO> result =
                employeeService.getAllEmployees();

        assertTrue(result.isEmpty());

        verify(employeeRepository).findAll();
    }


    // 7. Update employee - Success
    @Test
    void testUpdateEmployee() {
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setName("Rahul Updated");
        dto.setEmail("rahulupdated@gmail.com");
        dto.setSalary(70000.0);
        dto.setDepartmentId(2L);

        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        existingEmployee.setName("Rahul");
        existingEmployee.setEmail("rahul@gmail.com");
        existingEmployee.setSalary(50000.0);

        Department department = new Department();
        department.setId(2L);
        department.setName("HR");

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(existingEmployee));

        when(departmentRepository.findById(2L))
                .thenReturn(Optional.of(department));

        when(employeeRepository.save(any(Employee.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Employee result = employeeService.updateEmployee(1L, dto);

        assertEquals("Rahul Updated", result.getName());
        assertEquals("rahulupdated@gmail.com", result.getEmail());
        assertEquals(70000.0, result.getSalary());
        assertEquals("HR", result.getDepartment().getName());

        verify(employeeRepository).save(existingEmployee);
    }


    // 8. Update employee - Employee Not Found
    @Test
    void testUpdateEmployeeNotFound() {
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setDepartmentId(2L);

        when(employeeRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.updateEmployee(99L, dto));

        verify(departmentRepository, never()).findById(anyLong());
        verify(employeeRepository, never()).save(any(Employee.class));
    }


    // 9. Update employee - Department Not Found
    @Test
    void testUpdateEmployeeDepartmentNotFound() {
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setName("Rahul Updated");
        dto.setEmail("rahulupdated@gmail.com");
        dto.setSalary(70000.0);
        dto.setDepartmentId(99L);

        Employee employee = new Employee();
        employee.setId(1L);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(departmentRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.updateEmployee(1L, dto));

        verify(employeeRepository, never()).save(any(Employee.class));
    }


    // 10. Delete employee - Success
    @Test
    void testDeleteEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Rahul");

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).findById(1L);
        verify(employeeRepository).delete(employee);
    }


    // 11. Delete employee - Not Found
    @Test
    void testDeleteEmployeeNotFound() {
        when(employeeRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.deleteEmployee(99L));

        verify(employeeRepository, never()).delete(any(Employee.class));
    }


    // 12. Search employees by department - Success
    @Test
    void testGetEmployeeByDepartment() {
        Department department = new Department();
        department.setId(1L);
        department.setName("IT");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Rahul");
        employee.setEmail("rahul@gmail.com");
        employee.setSalary(50000.0);
        employee.setDepartment(department);

        when(employeeRepository.findByDepartment_NameIgnoreCase("IT"))
                .thenReturn(List.of(employee));

        List<EmployeeResponseDTO> result =
                employeeService.getEmployeeByDepartmentIgnoreCase("IT");

        assertEquals(1, result.size());
        assertEquals("Rahul", result.get(0).getName());
        assertEquals("IT", result.get(0).getDepartmentName());

        verify(employeeRepository).findByDepartment_NameIgnoreCase("IT");
    }


    // 13. Search employees by department - Empty Result
    @Test
    void testGetEmployeeByDepartmentEmpty() {
        when(employeeRepository.findByDepartment_NameIgnoreCase("Finance"))
                .thenReturn(List.of());

        List<EmployeeResponseDTO> result =
                employeeService.getEmployeeByDepartmentIgnoreCase("Finance");

        assertTrue(result.isEmpty());

        verify(employeeRepository)
                .findByDepartment_NameIgnoreCase("Finance");
    }


    // 14. Search employees by salary range - Success
    @Test
    void testGetEmployeeBySalaryBetween() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Rahul");
        employee.setEmail("rahul@gmail.com");
        employee.setSalary(50000.0);

        when(employeeRepository.findBySalaryBetween(30000.0, 80000.0))
                .thenReturn(List.of(employee));

        List<EmployeeResponseDTO> result =
                employeeService.getEmployeeBySalaryBetween(30000.0, 80000.0);

        assertEquals(1, result.size());
        assertEquals("Rahul", result.get(0).getName());
        assertEquals(50000.0, result.get(0).getSalary());

        verify(employeeRepository)
                .findBySalaryBetween(30000.0, 80000.0);
    }


    // 15. Search employees by salary range - Empty Result
    @Test
    void testGetEmployeeBySalaryBetweenEmpty() {
        when(employeeRepository.findBySalaryBetween(90000.0, 100000.0))
                .thenReturn(List.of());

        List<EmployeeResponseDTO> result =
                employeeService.getEmployeeBySalaryBetween(90000.0, 100000.0);

        assertTrue(result.isEmpty());

        verify(employeeRepository)
                .findBySalaryBetween(90000.0, 100000.0);
    }

}
*/