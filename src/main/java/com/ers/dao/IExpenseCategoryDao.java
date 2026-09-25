package com.ers.dao;

import com.ers.model.ExpenseCategory;

import java.sql.SQLException;
import java.util.List;

public interface IExpenseCategoryDao {

    ExpenseCategory addCategory(ExpenseCategory category)
            throws SQLException;

    List<ExpenseCategory> getAllCategories()
            throws SQLException;

    ExpenseCategory getCategoryById(int categoryId)
            throws SQLException;

    List<ExpenseCategory> searchCategories(String keyword)
            throws SQLException;

    boolean updateCategory(ExpenseCategory category)
            throws SQLException;

    boolean updateCategoryStatus(int categoryId, boolean active)
            throws SQLException;
}