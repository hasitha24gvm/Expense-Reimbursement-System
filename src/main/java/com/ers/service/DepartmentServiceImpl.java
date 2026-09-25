package com.ers.service;

import com.ers.dao.IDepartmentDao;
import com.ers.model.Department;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentServiceImpl implements IDepartmentService {

    private static final Logger LOGGER =
            Logger.getLogger(DepartmentServiceImpl.class.getName());

    private final IDepartmentDao departmentDao;

    public DepartmentServiceImpl(IDepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public Department addDepartment(Department department) {
        try {
            return departmentDao.addDepartment(department);
        } catch (SQLException e) {
            LOGGER.log(
                    Level.SEVERE,
                    "Error while adding department",
                    e
            );
            return null;
        }
    }

    @Override
    public List<Department> getAllDepartments() {
        try {
            return departmentDao.getAllDepartments();
        } catch (SQLException e) {
            LOGGER.log(
                    Level.SEVERE,
                    "Error while fetching departments",
                    e
            );
            return List.of();
        }
    }

    @Override
    public Department getDepartmentById(int departmentId) {
        try {
            return departmentDao.getDepartmentById(departmentId);
        } catch (SQLException e) {
            LOGGER.log(
                    Level.SEVERE,
                    "Error while fetching department",
                    e
            );
            return null;
        }
    }

    @Override
    public List<Department> searchDepartments(String keyword) {
        try {
            return departmentDao.searchDepartments(keyword);
        } catch (SQLException e) {
            LOGGER.log(
                    Level.SEVERE,
                    "Error while searching departments",
                    e
            );
            return List.of();
        }
    }

    @Override
    public boolean updateDepartment(Department department) {
        try {
            return departmentDao.updateDepartment(department);
        } catch (SQLException e) {
            LOGGER.log(
                    Level.SEVERE,
                    "Error while updating department",
                    e
            );
            return false;
        }
    }
}