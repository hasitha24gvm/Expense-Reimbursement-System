package com.ers.controller;

import com.ers.dao.IUserDao;
import com.ers.dao.UserDaoImpl;
import com.ers.service.IUserService;
import com.ers.service.UserServiceImpl;

public class AppController {

    public static void main(String[] args) {

        // DAO
        IUserDao userDao = new UserDaoImpl();

        // Service
        IUserService userService = new UserServiceImpl(userDao);

        // Controller
        UserController userController =
                new UserController(userService);

        // Start application
        userController.showMenu();
    }
}