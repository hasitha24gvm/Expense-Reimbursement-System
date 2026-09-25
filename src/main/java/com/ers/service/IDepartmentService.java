package com.ers.service;

import com.ers.model.Department;

import java.util.List;

public interface IDepartmentService {

    Department addDepartment(Department department);

    List<Department> getAllDepartments();

    Department getDepartmentById(int departmentId);

    List<Department> searchDepartments(String keyword);

    boolean updateDepartment(Department department);
}