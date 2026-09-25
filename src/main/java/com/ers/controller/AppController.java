package com.ers.controller;

import com.ers.dao.IUserDao;
import com.ers.dao.UserDaoImpl;

import com.ers.dao.IEmployeeDao;
import com.ers.dao.EmployeeDaoImpl;

import com.ers.dao.IDepartmentDao;
import com.ers.dao.DepartmentDaoImpl;

import com.ers.service.IUserService;
import com.ers.service.UserServiceImpl;

import com.ers.service.IEmployeeService;
import com.ers.service.EmployeeServiceImpl;

import com.ers.service.IDepartmentService;
import com.ers.service.DepartmentServiceImpl;

public class AppController {

    public static void main(String[] args) {

        // USER MODULE
        IUserDao userDao = new UserDaoImpl();

        IUserService userService =
                new UserServiceImpl(userDao);

        UserController userController =
                new UserController(userService);


        // EMPLOYEE MODULE
        IEmployeeDao employeeDao =
                new EmployeeDaoImpl();

        IEmployeeService employeeService =
                new EmployeeServiceImpl(employeeDao);

        EmployeeController employeeController =
                new EmployeeController(employeeService);


        // DEPARTMENT MODULE
        IDepartmentDao departmentDao =
                new DepartmentDaoImpl();

        IDepartmentService departmentService =
                new DepartmentServiceImpl(departmentDao);

        DepartmentController departmentController =
                new DepartmentController(departmentService);


        // TEST DEPARTMENT MODULE
        departmentController.showMenu();
    }
}