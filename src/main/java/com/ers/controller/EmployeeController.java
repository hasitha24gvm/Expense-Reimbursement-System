package com.ers.controller;

import com.ers.model.Employee;
import com.ers.service.IEmployeeService;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class EmployeeController {

    private static final Logger LOGGER =
            Logger.getLogger(
                    EmployeeController.class.getName()
            );

    private final IEmployeeService employeeService;

    private final Scanner scanner =
            new Scanner(System.in);


    public EmployeeController(
            IEmployeeService employeeService) {

        this.employeeService = employeeService;
    }


    // =========================
    // EMPLOYEE MENU
    // =========================

    public void showMenu() {

        while (true) {

            LOGGER.info("\n===== EMPLOYEE MENU =====");
            LOGGER.info("1. Add Employee");
            LOGGER.info("2. View All Employees");
            LOGGER.info("3. View Employee By ID");
            LOGGER.info("4. Search Employees");
            LOGGER.info("5. Update Employee");
            LOGGER.info("6. Change Password");
            LOGGER.info("7. Exit");

            LOGGER.info("Enter your choice: ");

            int choice =
                    scanner.nextInt();

            scanner.nextLine();

            switch (choice) {

                case 1:
                    addEmployee();
                    break;

                case 2:
                    viewAllEmployees();
                    break;

                case 3:
                    viewEmployeeById();
                    break;

                case 4:
                    searchEmployees();
                    break;

                case 5:
                    updateEmployee();
                    break;

                case 6:
                    changePassword();
                    break;

                case 7:
                    LOGGER.info("Exiting...");
                    return;

                default:
                    LOGGER.warning(
                            "Invalid choice."
                    );
            }
        }
    }


    // =========================
    // ADD EMPLOYEE
    // =========================

    private void addEmployee() {

        LOGGER.info("Enter User ID: ");
        int userId =
                scanner.nextInt();
        scanner.nextLine();

        LOGGER.info("Enter full name: ");
        String fullName =
                scanner.nextLine();

        LOGGER.info("Enter email: ");
        String email =
                scanner.nextLine();

        LOGGER.info("Enter department ID: ");
        int departmentId =
                scanner.nextInt();
        scanner.nextLine();


        if (userId <= 0 ||
                fullName.isEmpty() ||
                email.isEmpty() ||
                departmentId <= 0) {

            LOGGER.warning(
                    "All fields are required and IDs must be valid."
            );

            return;
        }


        Employee employee =
                new Employee(
                        userId,
                        fullName,
                        email,
                        departmentId
                );


        Employee addedEmployee =
                employeeService.addEmployee(
                        employee
                );


        if (addedEmployee != null) {

            LOGGER.info(
                    "Employee added successfully."
            );

            LOGGER.info(
                    "Generated Employee ID: "
                            + addedEmployee.getEmployeeId()
            );

        } else {

            LOGGER.warning(
                    "Failed to add employee."
            );
        }
    }


    // =========================
    // VIEW ALL EMPLOYEES
    // =========================

    private void viewAllEmployees() {

        List<Employee> employees =
                employeeService.getAllEmployees();


        if (employees.isEmpty()) {

            LOGGER.info(
                    "No employees found."
            );

            return;
        }


        for (Employee employee :
                employees) {

            LOGGER.info(
                    employee.toString()
            );
        }
    }


    // =========================
    // VIEW EMPLOYEE BY ID
    // =========================

    private void viewEmployeeById() {

        LOGGER.info(
                "Enter employee ID: "
        );

        int employeeId =
                scanner.nextInt();

        scanner.nextLine();


        Employee employee =
                employeeService.getEmployeeById(
                        employeeId
                );


        if (employee != null) {

            LOGGER.info(
                    employee.toString()
            );

        } else {

            LOGGER.warning(
                    "Employee not found."
            );
        }
    }


    // =========================
    // SEARCH EMPLOYEES
    // =========================

    private void searchEmployees() {

        LOGGER.info(
                "Enter employee name or email to search: "
        );

        String keyword =
                scanner.nextLine();


        if (keyword.isEmpty()) {

            LOGGER.warning(
                    "Search keyword is required."
            );

            return;
        }


        List<Employee> employees =
                employeeService.searchEmployees(
                        keyword
                );


        if (employees.isEmpty()) {

            LOGGER.info(
                    "No employees found."
            );

            return;
        }


        for (Employee employee :
                employees) {

            LOGGER.info(
                    employee.toString()
            );
        }
    }


    // =========================
    // UPDATE EMPLOYEE
    // =========================

    private void updateEmployee() {

        LOGGER.info(
                "Enter employee ID: "
        );

        int employeeId =
                scanner.nextInt();

        scanner.nextLine();


        Employee employee =
                employeeService.getEmployeeById(
                        employeeId
                );


        if (employee == null) {

            LOGGER.warning(
                    "Employee not found."
            );

            return;
        }


        LOGGER.info(
                "Enter new full name: "
        );

        String fullName =
                scanner.nextLine();


        LOGGER.info(
                "Enter new email: "
        );

        String email =
                scanner.nextLine();


        LOGGER.info(
                "Enter new department ID: "
        );

        int departmentId =
                scanner.nextInt();

        scanner.nextLine();


        if (fullName.isEmpty() ||
                email.isEmpty() ||
                departmentId <= 0) {

            LOGGER.warning(
                    "Invalid employee information."
            );

            return;
        }


        employee.setFullName(
                fullName
        );

        employee.setEmail(
                email
        );

        employee.setDepartmentId(
                departmentId
        );


        boolean success =
                employeeService.updateEmployee(
                        employee
                );


        if (success) {

            LOGGER.info(
                    "Employee updated successfully."
            );

        } else {

            LOGGER.warning(
                    "Failed to update employee."
            );
        }
    }


    // =========================
    // CHANGE PASSWORD
    // =========================

    private void changePassword() {

        LOGGER.info(
                "Enter User ID: "
        );

        int userId =
                scanner.nextInt();

        scanner.nextLine();


        if (userId <= 0) {

            LOGGER.warning(
                    "Invalid User ID."
            );

            return;
        }


        LOGGER.info(
                "Enter new password: "
        );

        String newPassword =
                scanner.nextLine();


        if (newPassword.isEmpty()) {

            LOGGER.warning(
                    "Password is required."
            );

            return;
        }


        boolean success =
                employeeService.changePassword(
                        userId,
                        newPassword
                );


        if (success) {

            LOGGER.info(
                    "Password changed successfully."
            );

        } else {

            LOGGER.warning(
                    "Failed to change password."
            );
        }
    }
}