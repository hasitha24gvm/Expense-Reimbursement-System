package com.ers.controller;

import com.ers.model.User;
import com.ers.service.IUserService;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class UserController {

    private static final Logger LOGGER =
            Logger.getLogger(UserController.class.getName());

    private final IUserService userService;
    private final Scanner scanner = new Scanner(System.in);

    // Constructor
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    // USER MENU
    public void showMenu() {

        while (true) {

            LOGGER.info("\n===== USER MENU =====");
            LOGGER.info("1. Add User");
            LOGGER.info("2. View All Users");
            LOGGER.info("3. View User By ID");
            LOGGER.info("4. Search Users");
            LOGGER.info("5. Update User");
            LOGGER.info("6. Update User Status");
            LOGGER.info("7. Exit");

            LOGGER.info("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addUser();
                    break;

                case 2:
                    viewAllUsers();
                    break;

                case 3:
                    viewUserById();
                    break;

                case 4:
                    searchUsers();
                    break;

                case 5:
                    updateUser();
                    break;

                case 6:
                    updateUserStatus();
                    break;

                case 7:
                    LOGGER.info("Exiting...");
                    return;

                default:
                    LOGGER.warning("Invalid choice.");
            }
        }
    }

    // CREATE USER
    private void addUser() {

        LOGGER.info("Enter username: ");
        String username = scanner.nextLine();

        LOGGER.info("Enter password: ");
        String password = scanner.nextLine();

        LOGGER.info("Enter role: ");
        String role = scanner.nextLine().toUpperCase();

        // Validation
        if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            LOGGER.warning("All fields are required.");
            return;
        }

        if (!isValidRole(role)) {
            LOGGER.warning(
                    "Invalid role. Use: ADMIN, EMPLOYEE, MANAGER, FINANCE_EXECUTIVE"
            );
            return;
        }

        User user = new User(
                username,
                password,
                role,
                true,
                null
        );

        User addedUser = userService.addUser(user);

        if (addedUser != null) {
            LOGGER.info("User added successfully.");
            LOGGER.info("Generated User ID: " + addedUser.getUserId());
        } else {
            LOGGER.warning("Failed to add user.");
        }
    }

    // VIEW ALL USERS
    private void viewAllUsers() {

        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            LOGGER.info("No users found.");
            return;
        }

        for (User user : users) {
            LOGGER.info(user.toString());
        }
    }

    // VIEW USER BY ID
    private void viewUserById() {

        LOGGER.info("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        User user = userService.getUserById(userId);

        if (user != null) {
            LOGGER.info(user.toString());
        } else {
            LOGGER.warning("User not found.");
        }
    }

    // SEARCH USERS
    private void searchUsers() {

        LOGGER.info("Enter username or role to search: ");
        String keyword = scanner.nextLine();

        if (keyword.isEmpty()) {
            LOGGER.warning("Search keyword is required.");
            return;
        }

        List<User> users = userService.searchUsers(keyword);

        if (users.isEmpty()) {
            LOGGER.info("No users found.");
            return;
        }

        for (User user : users) {
            LOGGER.info(user.toString());
        }
    }

    // UPDATE USER
    private void updateUser() {

        LOGGER.info("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        User user = userService.getUserById(userId);

        if (user == null) {
            LOGGER.warning("User not found.");
            return;
        }

        LOGGER.info("Enter new username: ");
        String username = scanner.nextLine();

        LOGGER.info("Enter new password: ");
        String password = scanner.nextLine();

        LOGGER.info("Enter new role: ");
        String role = scanner.nextLine().toUpperCase();

        LOGGER.info("Is user active? (true/false): ");
        boolean active = scanner.nextBoolean();
        scanner.nextLine();

        // Validation
        if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            LOGGER.warning("All fields are required.");
            return;
        }

        if (!isValidRole(role)) {
            LOGGER.warning(
                    "Invalid role. Use: ADMIN, EMPLOYEE, MANAGER, FINANCE_EXECUTIVE"
            );
            return;
        }

        user.setUserName(username);
        user.setPassword(password);
        user.setRole(role);
        user.setActive(active);

        boolean success = userService.updateUser(user);

        if (success) {
            LOGGER.info("User updated successfully.");
        } else {
            LOGGER.warning("Failed to update user.");
        }
    }

    // UPDATE USER STATUS
    private void updateUserStatus() {

        LOGGER.info("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        User user = userService.getUserById(userId);

        if (user == null) {
            LOGGER.warning("User not found.");
            return;
        }

        LOGGER.info("Current status: " +
                (user.isActive() ? "ACTIVE" : "INACTIVE"));

        LOGGER.info("Enter new status (true = active, false = inactive): ");
        boolean active = scanner.nextBoolean();
        scanner.nextLine();

        boolean success =
                userService.updateUserStatus(userId, active);

        if (success) {

            if (active) {
                LOGGER.info("User activated successfully.");
            } else {
                LOGGER.info("User deactivated successfully.");
            }

        } else {
            LOGGER.warning("Failed to update user status.");
        }
    }

    // ROLE VALIDATION
    private boolean isValidRole(String role) {

        return role.equals("ADMIN")
                || role.equals("EMPLOYEE")
                || role.equals("MANAGER")
                || role.equals("FINANCE_EXECUTIVE");
    }
}