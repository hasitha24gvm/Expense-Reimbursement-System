package com.ers.controller;

import com.ers.model.Department;
import com.ers.service.IDepartmentService;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class DepartmentController {

    private static final Logger LOGGER =
            Logger.getLogger(DepartmentController.class.getName());

    private final IDepartmentService departmentService;
    private final Scanner scanner;

    public DepartmentController(IDepartmentService departmentService) {
        this.departmentService = departmentService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {

        while (true) {

            LOGGER.info("\n===== DEPARTMENT MENU =====");
            LOGGER.info("1. Add Department");
            LOGGER.info("2. View All Departments");
            LOGGER.info("3. View Department By ID");
            LOGGER.info("4. Search Departments");
            LOGGER.info("5. Update Department");
            LOGGER.info("6. Exit");
            LOGGER.info("Enter your choice:");

            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {
                LOGGER.warning("Please enter a valid number.");
                continue;
            }

            switch (choice) {

                case 1:
                    addDepartment();
                    break;

                case 2:
                    viewAllDepartments();
                    break;

                case 3:
                    viewDepartmentById();
                    break;

                case 4:
                    searchDepartments();
                    break;

                case 5:
                    updateDepartment();
                    break;

                case 6:
                    LOGGER.info("Exiting Department Module...");
                    return;

                default:
                    LOGGER.warning("Invalid choice. Please try again.");
            }
        }
    }

    private void addDepartment() {

        LOGGER.info("Enter Department Name:");
        String departmentName = scanner.nextLine().trim();

        if (departmentName.isEmpty()) {
            LOGGER.warning("Department name cannot be empty.");
            return;
        }

        LOGGER.info("Enter Manager ID (0 if no manager):");

        int managerId;

        try {
            managerId = Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {
            LOGGER.warning("Please enter a valid Manager ID.");
            return;
        }

        Department department =
                new Department(departmentName, managerId);

        Department addedDepartment =
                departmentService.addDepartment(department);

        if (addedDepartment != null) {

            LOGGER.info(
                    "Department added successfully. Department ID: "
                            + addedDepartment.getDepartmentId()
            );

        } else {
            LOGGER.warning("Failed to add department.");
        }
    }

    private void viewAllDepartments() {

        List<Department> departments =
                departmentService.getAllDepartments();

        if (departments.isEmpty()) {
            LOGGER.info("No departments found.");
            return;
        }

        LOGGER.info("===== ALL DEPARTMENTS =====");

        for (Department department : departments) {
            LOGGER.info(department.toString());
        }
    }

    private void viewDepartmentById() {

        LOGGER.info("Enter Department ID:");

        int departmentId;

        try {
            departmentId = Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {
            LOGGER.warning("Please enter a valid Department ID.");
            return;
        }

        Department department =
                departmentService.getDepartmentById(departmentId);

        if (department != null) {
            LOGGER.info(department.toString());
        } else {
            LOGGER.warning(
                    "Department not found with ID: " + departmentId
            );
        }
    }

    private void searchDepartments() {

        LOGGER.info("Enter department name to search:");

        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            LOGGER.warning("Search keyword cannot be empty.");
            return;
        }

        List<Department> departments =
                departmentService.searchDepartments(keyword);

        if (departments.isEmpty()) {
            LOGGER.info("No departments found.");
            return;
        }

        LOGGER.info("===== SEARCH RESULTS =====");

        for (Department department : departments) {
            LOGGER.info(department.toString());
        }
    }

    private void updateDepartment() {

        LOGGER.info("Enter Department ID:");

        int departmentId;

        try {
            departmentId = Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {
            LOGGER.warning("Please enter a valid Department ID.");
            return;
        }

        LOGGER.info("Enter New Department Name:");

        String departmentName = scanner.nextLine().trim();

        if (departmentName.isEmpty()) {
            LOGGER.warning("Department name cannot be empty.");
            return;
        }

        LOGGER.info("Enter Manager ID (0 if no manager):");

        int managerId;

        try {
            managerId = Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {
            LOGGER.warning("Please enter a valid Manager ID.");
            return;
        }

        Department department =
                new Department(departmentName, managerId);

        department.setDepartmentId(departmentId);

        boolean updated =
                departmentService.updateDepartment(department);

        if (updated) {
            LOGGER.info("Department updated successfully.");
        } else {
            LOGGER.warning(
                    "Department not found or update failed."
            );
        }
    }
}