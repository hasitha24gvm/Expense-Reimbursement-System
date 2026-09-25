package com.ers.dao;

import com.ers.model.Employee;

import java.sql.SQLException;
import java.util.List;

public interface IEmployeeDao {

    // CREATE
    Employee addEmployee(Employee employee) throws SQLException;

    // READ
    List<Employee> getAllEmployees() throws SQLException;

    Employee getEmployeeById(int employeeId) throws SQLException;

    // SEARCH
    List<Employee> searchEmployees(String keyword)
            throws SQLException;

    // UPDATE
    boolean updateEmployee(Employee employee)
            throws SQLException;

    // CHANGE PASSWORD
    boolean changePassword(int userId, String newPassword)
            throws SQLException;
}