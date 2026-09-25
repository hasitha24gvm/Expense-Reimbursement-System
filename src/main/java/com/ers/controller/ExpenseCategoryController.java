package com.ers.controller;

import com.ers.model.ExpenseCategory;
import com.ers.service.IExpenseCategoryService;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class ExpenseCategoryController {

    private static final Logger LOGGER =
            Logger.getLogger(
                    ExpenseCategoryController.class.getName()
            );

    private final IExpenseCategoryService categoryService;
    private final Scanner scanner;

    public ExpenseCategoryController(
            IExpenseCategoryService categoryService) {

        this.categoryService = categoryService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {

        while (true) {

            LOGGER.info("\n===== EXPENSE CATEGORY MENU =====");
            LOGGER.info("1. Add Category");
            LOGGER.info("2. View All Categories");
            LOGGER.info("3. View Category By ID");
            LOGGER.info("4. Search Categories");
            LOGGER.info("5. Update Category");
            LOGGER.info("6. Update Category Status");
            LOGGER.info("7. Exit");
            LOGGER.info("Enter your choice:");

            int choice;

            try {
                choice =
                        Integer.parseInt(
                                scanner.nextLine()
                        );

            } catch (NumberFormatException e) {

                LOGGER.warning(
                        "Please enter a valid number."
                );

                continue;
            }

            switch (choice) {

                case 1:
                    addCategory();
                    break;

                case 2:
                    viewAllCategories();
                    break;

                case 3:
                    viewCategoryById();
                    break;

                case 4:
                    searchCategories();
                    break;

                case 5:
                    updateCategory();
                    break;

                case 6:
                    updateCategoryStatus();
                    break;

                case 7:
                    LOGGER.info(
                            "Exiting Expense Category Module..."
                    );
                    return;

                default:
                    LOGGER.warning(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }


    private void addCategory() {

        LOGGER.info("Enter Category Name:");

        String categoryName =
                scanner.nextLine().trim();

        if (categoryName.isEmpty()) {

            LOGGER.warning(
                    "Category name cannot be empty."
            );

            return;
        }

        LOGGER.info("Enter Description:");

        String description =
                scanner.nextLine().trim();

        ExpenseCategory category =
                new ExpenseCategory(
                        categoryName,
                        description,
                        true
                );

        ExpenseCategory addedCategory =
                categoryService.addCategory(category);

        if (addedCategory != null) {

            LOGGER.info(
                    "Category added successfully. Category ID: "
                            + addedCategory.getCategoryId()
            );

        } else {

            LOGGER.warning(
                    "Failed to add category."
            );
        }
    }


    private void viewAllCategories() {

        List<ExpenseCategory> categories =
                categoryService.getAllCategories();

        if (categories.isEmpty()) {

            LOGGER.info(
                    "No expense categories found."
            );

            return;
        }

        LOGGER.info(
                "===== ALL EXPENSE CATEGORIES ====="
        );

        for (ExpenseCategory category : categories) {

            LOGGER.info(
                    category.toString()
            );
        }
    }


    private void viewCategoryById() {

        LOGGER.info("Enter Category ID:");

        int categoryId;

        try {

            categoryId =
                    Integer.parseInt(
                            scanner.nextLine()
                    );

        } catch (NumberFormatException e) {

            LOGGER.warning(
                    "Please enter a valid Category ID."
            );

            return;
        }

        ExpenseCategory category =
                categoryService.getCategoryById(
                        categoryId
                );

        if (category != null) {

            LOGGER.info(
                    category.toString()
            );

        } else {

            LOGGER.warning(
                    "Category not found with ID: "
                            + categoryId
            );
        }
    }


    private void searchCategories() {

        LOGGER.info(
                "Enter category name or description to search:"
        );

        String keyword =
                scanner.nextLine().trim();

        if (keyword.isEmpty()) {

            LOGGER.warning(
                    "Search keyword cannot be empty."
            );

            return;
        }

        List<ExpenseCategory> categories =
                categoryService.searchCategories(
                        keyword
                );

        if (categories.isEmpty()) {

            LOGGER.info(
                    "No categories found."
            );

            return;
        }

        LOGGER.info(
                "===== SEARCH RESULTS ====="
        );

        for (ExpenseCategory category : categories) {

            LOGGER.info(
                    category.toString()
            );
        }
    }


    private void updateCategory() {

        LOGGER.info("Enter Category ID:");

        int categoryId;

        try {

            categoryId =
                    Integer.parseInt(
                            scanner.nextLine()
                    );

        } catch (NumberFormatException e) {

            LOGGER.warning(
                    "Please enter a valid Category ID."
            );

            return;
        }

        LOGGER.info("Enter New Category Name:");

        String categoryName =
                scanner.nextLine().trim();

        if (categoryName.isEmpty()) {

            LOGGER.warning(
                    "Category name cannot be empty."
            );

            return;
        }

        LOGGER.info("Enter New Description:");

        String description =
                scanner.nextLine().trim();

        ExpenseCategory category =
                new ExpenseCategory(
                        categoryName,
                        description,
                        true
                );

        category.setCategoryId(categoryId);

        boolean updated =
                categoryService.updateCategory(
                        category
                );

        if (updated) {

            LOGGER.info(
                    "Category updated successfully."
            );

        } else {

            LOGGER.warning(
                    "Category not found or update failed."
            );
        }
    }


    private void updateCategoryStatus() {

        LOGGER.info("Enter Category ID:");

        int categoryId;

        try {

            categoryId =
                    Integer.parseInt(
                            scanner.nextLine()
                    );

        } catch (NumberFormatException e) {

            LOGGER.warning(
                    "Please enter a valid Category ID."
            );

            return;
        }

        LOGGER.info(
                "Enter status (1 = Active, 0 = Inactive):"
        );

        int status;

        try {

            status =
                    Integer.parseInt(
                            scanner.nextLine()
                    );

        } catch (NumberFormatException e) {

            LOGGER.warning(
                    "Please enter 1 or 0."
            );

            return;
        }

        if (status != 0 && status != 1) {

            LOGGER.warning(
                    "Status must be 1 or 0."
            );

            return;
        }

        boolean active = status == 1;

        boolean updated =
                categoryService.updateCategoryStatus(
                        categoryId,
                        active
                );

        if (updated) {

            LOGGER.info(
                    "Category status updated successfully."
            );

        } else {

            LOGGER.warning(
                    "Category not found or status update failed."
            );
        }
    }
}