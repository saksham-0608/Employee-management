package com.saksham.employee_management.service;

import com.saksham.employee_management.entity.Department;
import com.saksham.employee_management.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public Department createDepartment(Department department) {
       Department department1=new Department();
       department1.setName(department.getName());
        return departmentRepository.save(department1);
    }

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

}
