package com.ers.dao;

import com.ers.model.User;
import com.ers.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoImpl implements IUserDao {

    private static final Logger LOGGER =
            Logger.getLogger(UserDaoImpl.class.getName());

    private final JDBCUtil jdbcUtil = new JDBCUtil();

    // =========================
    // SQL QUERIES
    // =========================

    private final String insertQuery =
            "INSERT INTO users (username, password, role, is_active) " +
                    "VALUES (?, ?, ?, ?)";

    private final String selectAllQuery =
            "SELECT * FROM users";

    private final String selectByIdQuery =
            "SELECT * FROM users WHERE user_id = ?";

    private final String searchQuery =
            "SELECT * FROM users " +
                    "WHERE username LIKE ? OR role LIKE ?";

    private final String updateQuery =
            "UPDATE users SET username = ?, password = ?, " +
                    "role = ?, is_active = ? WHERE user_id = ?";

    private final String updateStatusQuery =
            "UPDATE users SET is_active = ? WHERE user_id = ?";


    // =========================
    // CREATE USER
    // =========================

    @Override
    public User addUser(User user) throws SQLException {

        Connection con = null;

        try {

            con = jdbcUtil.getConnection();

            // Start transaction
            con.setAutoCommit(false);

            try (PreparedStatement ps =
                         con.prepareStatement(
                                 insertQuery,
                                 Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, user.getUserName());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getRole());
                ps.setBoolean(4, user.isActive());

                int count = ps.executeUpdate();

                if (count == 0) {
                    con.rollback();
                    return null;
                }

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }

                // Commit transaction
                con.commit();

                LOGGER.info("User added successfully.");

                return user;
            }

        } catch (SQLException e) {

            // Rollback if something fails
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackException) {
                    LOGGER.log(
                            Level.SEVERE,
                            "Rollback failed while adding user",
                            rollbackException
                    );
                }
            }

            LOGGER.log(Level.SEVERE, "Error while adding user", e);

            throw e;

        } finally {

            if (con != null) {

                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    LOGGER.log(
                            Level.SEVERE,
                            "Error while closing database connection",
                            e
                    );
                }
            }
        }
    }


    // =========================
    // READ ALL USERS
    // =========================

    @Override
    public List<User> getAllUsers() throws SQLException {

        List<User> users = new ArrayList<>();

        try (Connection con = jdbcUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(selectAllQuery);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                User user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("created_at")
                                .toLocalDateTime()
                );

                user.setUserId(rs.getInt("user_id"));

                users.add(user);
            }
        }

        return users;
    }


    // =========================
    // READ USER BY ID
    // =========================

    @Override
    public User getUserById(int userId) throws SQLException {

        try (Connection con = jdbcUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(selectByIdQuery)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    User user = new User(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getBoolean("is_active"),
                            rs.getTimestamp("created_at")
                                    .toLocalDateTime()
                    );

                    user.setUserId(rs.getInt("user_id"));

                    return user;
                }
            }
        }

        return null;
    }


    // =========================
    // SEARCH USERS
    // =========================

    @Override
    public List<User> searchUsers(String keyword)
            throws SQLException {

        List<User> users = new ArrayList<>();

        try (Connection con = jdbcUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(searchQuery)) {

            String searchValue = "%" + keyword + "%";

            ps.setString(1, searchValue);
            ps.setString(2, searchValue);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    User user = new User(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getBoolean("is_active"),
                            rs.getTimestamp("created_at")
                                    .toLocalDateTime()
                    );

                    user.setUserId(rs.getInt("user_id"));

                    users.add(user);
                }
            }
        }

        return users;
    }


    // =========================
    // UPDATE USER
    // =========================

    @Override
    public boolean updateUser(User user)
            throws SQLException {

        Connection con = null;

        try {

            con = jdbcUtil.getConnection();

            // Start transaction
            con.setAutoCommit(false);

            try (PreparedStatement ps =
                         con.prepareStatement(updateQuery)) {

                ps.setString(1, user.getUserName());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getRole());
                ps.setBoolean(4, user.isActive());
                ps.setInt(5, user.getUserId());

                int count = ps.executeUpdate();

                if (count > 0) {

                    // Commit transaction
                    con.commit();

                    LOGGER.info("User updated successfully.");

                    return true;
                }

                // No row updated
                con.rollback();

                return false;
            }

        } catch (SQLException e) {

            // Rollback if something fails
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackException) {
                    LOGGER.log(
                            Level.SEVERE,
                            "Rollback failed while updating user",
                            rollbackException
                    );
                }
            }

            LOGGER.log(Level.SEVERE, "Error while updating user", e);

            throw e;

        } finally {

            if (con != null) {

                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    LOGGER.log(
                            Level.SEVERE,
                            "Error while closing database connection",
                            e
                    );
                }
            }
        }
    }


    // =========================
    // ACTIVATE / DEACTIVATE USER
    // =========================

    @Override
    public boolean updateUserStatus(
            int userId,
            boolean active) throws SQLException {

        Connection con = null;

        try {

            con = jdbcUtil.getConnection();

            // Start transaction
            con.setAutoCommit(false);

            try (PreparedStatement ps =
                         con.prepareStatement(updateStatusQuery)) {

                ps.setBoolean(1, active);
                ps.setInt(2, userId);

                int count = ps.executeUpdate();

                if (count > 0) {

                    // Commit transaction
                    con.commit();

                    LOGGER.info("User status updated successfully.");

                    return true;
                }

                // No row updated
                con.rollback();

                return false;
            }

        } catch (SQLException e) {

            // Rollback if something fails
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackException) {
                    LOGGER.log(
                            Level.SEVERE,
                            "Rollback failed while updating user status",
                            rollbackException
                    );
                }
            }

            LOGGER.log(
                    Level.SEVERE,
                    "Error while updating user status",
                    e
            );

            throw e;

        } finally {

            if (con != null) {

                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    LOGGER.log(
                            Level.SEVERE,
                            "Error while closing database connection",
                            e
                    );
                }
            }
        }
    }
}