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

import com.ers.dao.IExpenseCategoryDao;
import com.ers.dao.ExpenseCategoryDaoImpl;

import com.ers.service.IExpenseCategoryService;
import com.ers.service.ExpenseCategoryServiceImpl;

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


        // EXPENSE CATEGORY MODULE


        IExpenseCategoryDao categoryDao =
                new ExpenseCategoryDaoImpl();

        IExpenseCategoryService categoryService =
                new ExpenseCategoryServiceImpl(categoryDao);

        ExpenseCategoryController categoryController =
                new ExpenseCategoryController(categoryService);


// TEST EXPENSE CATEGORY MODULE
        categoryController.showMenu();
    }
}