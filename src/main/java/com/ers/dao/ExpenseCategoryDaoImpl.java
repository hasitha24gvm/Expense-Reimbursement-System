package com.ers.dao;

import com.ers.model.ExpenseCategory;
import com.ers.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpenseCategoryDaoImpl
        implements IExpenseCategoryDao {

    private static final Logger LOGGER =
            Logger.getLogger(
                    ExpenseCategoryDaoImpl.class.getName()
            );

    private final JDBCUtil jdbcUtil = new JDBCUtil();

    private final String insertQuery =
            "INSERT INTO expense_categories " +
                    "(category_name, description, is_active) " +
                    "VALUES (?, ?, ?)";

    private final String selectAllQuery =
            "SELECT * FROM expense_categories";

    private final String selectByIdQuery =
            "SELECT * FROM expense_categories " +
                    "WHERE category_id = ?";

    private final String searchQuery =
            "SELECT * FROM expense_categories " +
                    "WHERE category_name LIKE ? " +
                    "OR description LIKE ?";

    private final String updateQuery =
            "UPDATE expense_categories " +
                    "SET category_name = ?, description = ? " +
                    "WHERE category_id = ?";

    private final String updateStatusQuery =
            "UPDATE expense_categories " +
                    "SET is_active = ? " +
                    "WHERE category_id = ?";


    @Override
    public ExpenseCategory addCategory(
            ExpenseCategory category) throws SQLException {

        try (Connection con = jdbcUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(
                             insertQuery,
                             Statement.RETURN_GENERATED_KEYS)) {

            con.setAutoCommit(false);

            try {

                ps.setString(
                        1,
                        category.getCategoryName()
                );

                ps.setString(
                        2,
                        category.getDescription()
                );

                ps.setBoolean(
                        3,
                        category.isActive()
                );

                ps.executeUpdate();

                try (ResultSet rs =
                             ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        category.setCategoryId(
                                rs.getInt(1)
                        );
                    }
                }

                con.commit();

                LOGGER.info(
                        "Expense category added successfully: "
                                + category.getCategoryName()
                );

                return category;

            } catch (SQLException e) {

                con.rollback();

                LOGGER.log(
                        Level.SEVERE,
                        "Error while adding expense category",
                        e
                );

                throw e;
            }
        }
    }


    @Override
    public List<ExpenseCategory> getAllCategories()
            throws SQLException {

        List<ExpenseCategory> categories =
                new ArrayList<>();

        try (Connection con =
                     jdbcUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(selectAllQuery);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ExpenseCategory category =
                        new ExpenseCategory();

                category.setCategoryId(
                        rs.getInt("category_id")
                );

                category.setCategoryName(
                        rs.getString("category_name")
                );

                category.setDescription(
                        rs.getString("description")
                );

                category.setActive(
                        rs.getBoolean("is_active")
                );

                categories.add(category);
            }

            LOGGER.info(
                    "Fetched all expense categories successfully"
            );

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while fetching expense categories",
                    e
            );

            throw e;
        }

        return categories;
    }


    @Override
    public ExpenseCategory getCategoryById(
            int categoryId) throws SQLException {

        try (Connection con =
                     jdbcUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(selectByIdQuery)) {

            ps.setInt(1, categoryId);

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {

                    ExpenseCategory category =
                            new ExpenseCategory();

                    category.setCategoryId(
                            rs.getInt("category_id")
                    );

                    category.setCategoryName(
                            rs.getString("category_name")
                    );

                    category.setDescription(
                            rs.getString("description")
                    );

                    category.setActive(
                            rs.getBoolean("is_active")
                    );

                    LOGGER.info(
                            "Expense category found with ID: "
                                    + categoryId
                    );

                    return category;
                }
            }

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while fetching expense category",
                    e
            );

            throw e;
        }

        return null;
    }


    @Override
    public List<ExpenseCategory> searchCategories(
            String keyword) throws SQLException {

        List<ExpenseCategory> categories =
                new ArrayList<>();

        try (Connection con =
                     jdbcUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(searchQuery)) {

            String searchKeyword =
                    "%" + keyword + "%";

            ps.setString(1, searchKeyword);
            ps.setString(2, searchKeyword);

            try (ResultSet rs =
                         ps.executeQuery()) {

                while (rs.next()) {

                    ExpenseCategory category =
                            new ExpenseCategory();

                    category.setCategoryId(
                            rs.getInt("category_id")
                    );

                    category.setCategoryName(
                            rs.getString("category_name")
                    );

                    category.setDescription(
                            rs.getString("description")
                    );

                    category.setActive(
                            rs.getBoolean("is_active")
                    );

                    categories.add(category);
                }
            }

            LOGGER.info(
                    "Expense category search completed"
            );

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error while searching expense categories",
                    e
            );

            throw e;
        }

        return categories;
    }


    @Override
    public boolean updateCategory(
            ExpenseCategory category)
            throws SQLException {

        try (Connection con =
                     jdbcUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(updateQuery)) {

            con.setAutoCommit(false);

            try {

                ps.setString(
                        1,
                        category.getCategoryName()
                );

                ps.setString(
                        2,
                        category.getDescription()
                );

                ps.setInt(
                        3,
                        category.getCategoryId()
                );

                int rowsAffected =
                        ps.executeUpdate();

                con.commit();

                if (rowsAffected > 0) {

                    LOGGER.info(
                            "Expense category updated successfully: "
                                    + category.getCategoryId()
                    );

                    return true;
                }

                return false;

            } catch (SQLException e) {

                con.rollback();

                LOGGER.log(
                        Level.SEVERE,
                        "Error while updating expense category",
                        e
                );

                throw e;
            }
        }
    }


    @Override
    public boolean updateCategoryStatus(
            int categoryId,
            boolean active) throws SQLException {

        try (Connection con =
                     jdbcUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(updateStatusQuery)) {

            con.setAutoCommit(false);

            try {

                ps.setBoolean(1, active);
                ps.setInt(2, categoryId);

                int rowsAffected =
                        ps.executeUpdate();

                con.commit();

                if (rowsAffected > 0) {

                    LOGGER.info(
                            "Expense category status updated: "
                                    + categoryId
                    );

                    return true;
                }

                return false;

            } catch (SQLException e) {

                con.rollback();

                LOGGER.log(
                        Level.SEVERE,
                        "Error while updating category status",
                        e
                );

                throw e;
            }
        }
    }
}