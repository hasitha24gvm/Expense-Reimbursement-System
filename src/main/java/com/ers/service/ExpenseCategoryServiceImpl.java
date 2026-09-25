package com.ers.service;

import com.ers.dao.IExpenseCategoryDao;
import com.ers.model.ExpenseCategory;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpenseCategoryServiceImpl
        implements IExpenseCategoryService {

    private static final Logger LOGGER =
            Logger.getLogger(
                    ExpenseCategoryServiceImpl.class.getName()
            );

    private final IExpenseCategoryDao categoryDao;

    public ExpenseCategoryServiceImpl(
            IExpenseCategoryDao categoryDao) {

        this.categoryDao = categoryDao;
    }

    @Override
    public ExpenseCategory addCategory(
            ExpenseCategory category) {

        try {
            return categoryDao.addCategory(category);

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while adding expense category",
                    e
            );

            return null;
        }
    }

    @Override
    public List<ExpenseCategory> getAllCategories() {

        try {
            return categoryDao.getAllCategories();

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while fetching expense categories",
                    e
            );

            return List.of();
        }
    }

    @Override
    public ExpenseCategory getCategoryById(
            int categoryId) {

        try {
            return categoryDao.getCategoryById(
                    categoryId
            );

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while fetching expense category",
                    e
            );

            return null;
        }
    }

    @Override
    public List<ExpenseCategory> searchCategories(
            String keyword) {

        try {
            return categoryDao.searchCategories(
                    keyword
            );

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while searching expense categories",
                    e
            );

            return List.of();
        }
    }

    @Override
    public boolean updateCategory(
            ExpenseCategory category) {

        try {
            return categoryDao.updateCategory(
                    category
            );

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while updating expense category",
                    e
            );

            return false;
        }
    }

    @Override
    public boolean updateCategoryStatus(
            int categoryId,
            boolean active) {

        try {
            return categoryDao.updateCategoryStatus(
                    categoryId,
                    active
            );

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while updating category status",
                    e
            );

            return false;
        }
    }
}