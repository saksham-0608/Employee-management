package com.saksham.employee_management.controller;

import com.saksham.employee_management.dto.EmployeeRequestDTO;
import com.saksham.employee_management.dto.EmployeeResponseDTO;
import com.saksham.employee_management.entity.Employee;
import com.saksham.employee_management.service.EmployeeService;
import com.saksham.employee_management.config.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
/*
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;


    // Helper method to create EmployeeResponseDTO
    private EmployeeResponseDTO createResponseDTO() {

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setId(1L);
        dto.setName("Rahul");
        dto.setEmail("rahul@gmail.com");
        dto.setSalary(50000.0);
        dto.setDepartmentId(1L);
        dto.setDepartmentName("IT");

        return dto;
    }


    // Helper method to create Employee entity
    private Employee createEmployee() {

        Employee employee = new Employee();

        employee.setId(1L);
        employee.setName("Rahul");
        employee.setEmail("rahul@gmail.com");
        employee.setSalary(50000.0);

        return employee;
    }


    // 1. Test Create Employee - Success
    @Test
    void testCreateEmployee() throws Exception {

        Employee employee = createEmployee();
        EmployeeResponseDTO responseDTO = createResponseDTO();

        when(employeeService.createEmployee(any(EmployeeRequestDTO.class)))
                .thenReturn(employee);

        when(employeeService.convertToDTO(any(Employee.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Rahul",
                                    "email": "rahul@gmail.com",
                                    "salary": 50000,
                                    "departmentId": 1
                                }
                                """))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rahul"))
                .andExpect(jsonPath("$.email").value("rahul@gmail.com"))
                .andExpect(jsonPath("$.salary").value(50000.0));

        verify(employeeService).createEmployee(any(EmployeeRequestDTO.class));
    }


    // 2. Test Get All Employees
    @Test
    void testGetAllEmployees() throws Exception {

        EmployeeResponseDTO dto = createResponseDTO();

        when(employeeService.getAllEmployees())
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/employees"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Rahul"))
                .andExpect(jsonPath("$[0].email").value("rahul@gmail.com"));
    }


    // 3. Test Get All Employees - Empty List
    @Test
    void testGetAllEmployeesEmpty() throws Exception {

        when(employeeService.getAllEmployees())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/employees"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }


    // 4. Test Get Employee By ID - Success
    @Test
    void testGetEmployeeById() throws Exception {

        EmployeeResponseDTO dto = createResponseDTO();

        when(employeeService.getEmployeeById(1L))
                .thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/employees/1"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rahul"));
    }


    // 5. Test Get Employee By ID - Not Found
    @Test
    void testGetEmployeeByIdNotFound() throws Exception {

        when(employeeService.getEmployeeById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/employees/99"))

                .andExpect(status().isNotFound());
    }


    // 6. Test Pagination and Sorting
    @Test
    void testGetEmployeesWithPagination() throws Exception {

        EmployeeResponseDTO dto = createResponseDTO();

        Page<EmployeeResponseDTO> page =
                new PageImpl<>(List.of(dto));

        when(employeeService.getAllEmployees(0, 10, "salary", "desc"))
                .thenReturn(page);

        mockMvc.perform(get("/api/employees/page")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "salary")
                        .param("direction", "desc"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Rahul"));
    }


    // 7. Test Search Employee By Department
    @Test
    void testGetEmployeeByDepartment() throws Exception {

        EmployeeResponseDTO dto = createResponseDTO();

        when(employeeService.getEmployeeByDepartment("IT"))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/employees/search")
                        .param("department", "IT"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Rahul"));
    }


    // 8. Test Search Employee By Department - Ignore Case
    @Test
    void testGetEmployeeByDepartmentIgnoreCase() throws Exception {

        EmployeeResponseDTO dto = createResponseDTO();

        when(employeeService.getEmployeeByDepartmentIgnoreCase("IT"))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/employees/search/IT"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Rahul"));
    }


    // 9. Test Search Employees By Salary Range
    @Test
    void testGetEmployeeBySalaryBetween() throws Exception {

        EmployeeResponseDTO dto = createResponseDTO();

        when(employeeService.getEmployeeBySalaryBetween(30000.0, 80000.0))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/employees/search/salary")
                        .param("min", "30000")
                        .param("max", "80000"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Rahul"));
    }


    // 10. Test Update Employee - Success
    @Test
    void testUpdateEmployee() throws Exception {

        Employee employee = createEmployee();
        EmployeeResponseDTO responseDTO = createResponseDTO();

        when(employeeService.updateEmployee(
                eq(1L), any(EmployeeRequestDTO.class)))
                .thenReturn(employee);

        when(employeeService.convertToDTO(any(Employee.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Rahul",
                                    "email": "rahul@gmail.com",
                                    "salary": 50000,
                                    "departmentId": 1
                                }
                                """))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rahul"));
    }


    // 11. Test Delete Employee
    @Test
    void testDeleteEmployee() throws Exception {

        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))

                .andExpect(status().isNoContent());

        verify(employeeService).deleteEmployee(1L);
    }


    // 12. Test Create Employee - Validation Failure
    @Test
    void testCreateEmployeeValidationFailure() throws Exception {

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "",
                                    "email": "invalid-email",
                                    "salary": -500,
                                    "departmentId": null
                                }
                                """))

                .andExpect(status().isBadRequest());

        verify(employeeService, never())
                .createEmployee(any(EmployeeRequestDTO.class));
    }*/
}