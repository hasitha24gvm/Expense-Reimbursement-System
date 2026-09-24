package com.ers.dao;

import com.ers.model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDao {

    // CREATE
    User addUser(User user) throws SQLException;

    // READ
    List<User> getAllUsers() throws SQLException;

    User getUserById(int userId) throws SQLException;

    // SEARCH
    List<User> searchUsers(String keyword) throws SQLException;

    // UPDATE
    boolean updateUser(User user) throws SQLException;

    // DEACTIVATE
    boolean updateUserStatus(int userId, boolean active) throws SQLException;
}