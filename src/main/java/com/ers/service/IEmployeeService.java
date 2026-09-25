package com.ers.service;

import com.ers.model.Employee;

import java.util.List;

public interface IEmployeeService {

    Employee addEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(int employeeId);

    List<Employee> searchEmployees(String keyword);

    boolean updateEmployee(Employee employee);

    boolean changePassword(
            int userId,
            String newPassword
    );
}