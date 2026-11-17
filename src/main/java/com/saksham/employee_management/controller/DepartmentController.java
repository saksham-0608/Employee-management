package com.saksham.employee_management.controller;

import com.saksham.employee_management.entity.Department;
import com.saksham.employee_management.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping()
    public ResponseEntity<Department> createDepartment(@RequestBody  Department department) {
        Department saveDepartment=departmentService.createDepartment(department);
        return new ResponseEntity<>(saveDepartment, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments=departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }
}
