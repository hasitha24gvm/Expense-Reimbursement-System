package com.ers.dao;

import com.ers.model.Department;

import java.sql.SQLException;
import java.util.List;

public interface IDepartmentDao {

    Department addDepartment(Department department) throws SQLException;

    List<Department> getAllDepartments() throws SQLException;

    Department getDepartmentById(int departmentId) throws SQLException;

    List<Department> searchDepartments(String keyword) throws SQLException;

    boolean updateDepartment(Department department) throws SQLException;
}