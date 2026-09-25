package com.ers.service;

import com.ers.model.ExpenseCategory;

import java.util.List;

public interface IExpenseCategoryService {

    ExpenseCategory addCategory(
            ExpenseCategory category);

    List<ExpenseCategory> getAllCategories();

    ExpenseCategory getCategoryById(int categoryId);

    List<ExpenseCategory> searchCategories(
            String keyword);

    boolean updateCategory(
            ExpenseCategory category);

    boolean updateCategoryStatus(
            int categoryId,
            boolean active);
}