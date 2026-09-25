package com.ers.service;

import com.ers.dao.IEmployeeDao;
import com.ers.model.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeServiceImpl
        implements IEmployeeService {

    private static final Logger LOGGER =
            Logger.getLogger(
                    EmployeeServiceImpl.class.getName()
            );

    private final IEmployeeDao employeeDao;

    public EmployeeServiceImpl(
            IEmployeeDao employeeDao) {

        this.employeeDao = employeeDao;
    }


    @Override
    public Employee addEmployee(
            Employee employee) {

        try {

            return employeeDao.addEmployee(employee);

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while adding employee",
                    e
            );

            return null;
        }
    }


    @Override
    public List<Employee> getAllEmployees() {

        try {

            return employeeDao.getAllEmployees();

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while fetching employees",
                    e
            );

            return List.of();
        }
    }


    @Override
    public Employee getEmployeeById(
            int employeeId) {

        try {

            return employeeDao.getEmployeeById(
                    employeeId
            );

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while fetching employee",
                    e
            );

            return null;
        }
    }


    @Override
    public List<Employee> searchEmployees(
            String keyword) {

        try {

            return employeeDao.searchEmployees(
                    keyword
            );

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while searching employees",
                    e
            );

            return List.of();
        }
    }


    @Override
    public boolean updateEmployee(
            Employee employee) {

        try {

            return employeeDao.updateEmployee(
                    employee
            );

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while updating employee",
                    e
            );

            return false;
        }
    }


    @Override
    public boolean changePassword(
            int userId,
            String newPassword) {

        try {

            return employeeDao.changePassword(
                    userId,
                    newPassword
            );

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while changing password",
                    e
            );

            return false;
        }
    }
}