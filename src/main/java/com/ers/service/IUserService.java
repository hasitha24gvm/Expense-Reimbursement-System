package com.ers.service;

import com.ers.model.User;

import java.util.List;

public interface IUserService {

    User addUser(User user);

    List<User> getAllUsers();

    User getUserById(int userId);

    List<User> searchUsers(String keyword);

    boolean updateUser(User user);

    boolean updateUserStatus(int userId, boolean active);
}