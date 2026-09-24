package com.ers.service;

import com.ers.dao.IUserDao;
import com.ers.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServiceImpl implements IUserService {

    private static final Logger LOGGER =
            Logger.getLogger(UserServiceImpl.class.getName());

    private final IUserDao userDao;

    public UserServiceImpl(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User addUser(User user) {
        try {
            return userDao.addUser(user);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while adding user", e);
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userDao.getAllUsers();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while fetching users", e);
            return List.of();
        }
    }

    @Override
    public User getUserById(int userId) {
        try {
            return userDao.getUserById(userId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while fetching user", e);
            return null;
        }
    }

    @Override
    public List<User> searchUsers(String keyword) {
        try {
            return userDao.searchUsers(keyword);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while searching users", e);
            return List.of();
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            return userDao.updateUser(user);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while updating user", e);
            return false;
        }
    }

    @Override
    public boolean updateUserStatus(int userId, boolean active) {
        try {
            return userDao.updateUserStatus(userId, active);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while updating user status", e);
            return false;
        }
    }
}