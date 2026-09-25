package com.ers.dao;

import com.ers.model.Employee;
import com.ers.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeDaoImpl implements IEmployeeDao {

    private static final Logger LOGGER =
            Logger.getLogger(EmployeeDaoImpl.class.getName());

    private final JDBCUtil jdbcUtil = new JDBCUtil();

    // =========================
    // SQL QUERIES
    // =========================

    private final String insertQuery =
            "INSERT INTO employees " +
                    "(user_id, full_name, email, department_id) " +
                    "VALUES (?, ?, ?, ?)";

    private final String selectAllQuery =
            "SELECT * FROM employees";

    private final String selectByIdQuery =
            "SELECT * FROM employees WHERE employee_id = ?";

    private final String searchQuery =
            "SELECT * FROM employees " +
                    "WHERE full_name LIKE ? OR email LIKE ?";

    private final String updateQuery =
            "UPDATE employees SET full_name = ?, " +
                    "email = ?, department_id = ? " +
                    "WHERE employee_id = ?";

    private final String changePasswordQuery =
            "UPDATE users SET password = ? " +
                    "WHERE user_id = ?";


    // =========================
    // CREATE EMPLOYEE
    // =========================

    @Override
    public Employee addEmployee(Employee employee)
            throws SQLException {

        Connection con = null;

        try {

            con = jdbcUtil.getConnection();

            // Start transaction
            con.setAutoCommit(false);

            try (PreparedStatement ps =
                         con.prepareStatement(
                                 insertQuery,
                                 Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, employee.getUserId());
                ps.setString(2, employee.getFullName());
                ps.setString(3, employee.getEmail());
                ps.setInt(4, employee.getDepartmentId());

                int count = ps.executeUpdate();

                if (count == 0) {

                    con.rollback();
                    return null;
                }

                try (ResultSet rs =
                             ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        employee.setEmployeeId(
                                rs.getInt(1)
                        );
                    }
                }

                // Commit transaction
                con.commit();

                LOGGER.info(
                        "Employee added successfully."
                );

                return employee;
            }

        } catch (SQLException e) {

            if (con != null) {

                try {
                    con.rollback();

                } catch (SQLException rollbackException) {

                    LOGGER.log(
                            Level.SEVERE,
                            "Rollback failed while adding employee",
                            rollbackException
                    );
                }
            }

            LOGGER.log(
                    Level.SEVERE,
                    "Error while adding employee",
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
                            "Error while closing connection",
                            e
                    );
                }
            }
        }
    }


    // =========================
    // READ ALL EMPLOYEES
    // =========================

    @Override
    public List<Employee> getAllEmployees()
            throws SQLException {

        List<Employee> employees =
                new ArrayList<>();

        try (Connection con =
                     jdbcUtil.getConnection();

             PreparedStatement ps =
                     con.prepareStatement(selectAllQuery);

             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                Employee employee =
                        new Employee(
                                rs.getInt("user_id"),
                                rs.getString("full_name"),
                                rs.getString("email"),
                                rs.getInt("department_id")
                        );

                employee.setEmployeeId(
                        rs.getInt("employee_id")
                );

                employees.add(employee);
            }
        }

        return employees;
    }


    // =========================
    // READ EMPLOYEE BY ID
    // =========================

    @Override
    public Employee getEmployeeById(int employeeId)
            throws SQLException {

        try (Connection con =
                     jdbcUtil.getConnection();

             PreparedStatement ps =
                     con.prepareStatement(
                             selectByIdQuery)) {

            ps.setInt(1, employeeId);

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {

                    Employee employee =
                            new Employee(
                                    rs.getInt("user_id"),
                                    rs.getString("full_name"),
                                    rs.getString("email"),
                                    rs.getInt("department_id")
                            );

                    employee.setEmployeeId(
                            rs.getInt("employee_id")
                    );

                    return employee;
                }
            }
        }

        return null;
    }


    // =========================
    // SEARCH EMPLOYEES
    // =========================

    @Override
    public List<Employee> searchEmployees(
            String keyword)
            throws SQLException {

        List<Employee> employees =
                new ArrayList<>();

        try (Connection con =
                     jdbcUtil.getConnection();

             PreparedStatement ps =
                     con.prepareStatement(searchQuery)) {

            String searchValue =
                    "%" + keyword + "%";

            ps.setString(1, searchValue);
            ps.setString(2, searchValue);

            try (ResultSet rs =
                         ps.executeQuery()) {

                while (rs.next()) {

                    Employee employee =
                            new Employee(
                                    rs.getInt("user_id"),
                                    rs.getString("full_name"),
                                    rs.getString("email"),
                                    rs.getInt("department_id")
                            );

                    employee.setEmployeeId(
                            rs.getInt("employee_id")
                    );

                    employees.add(employee);
                }
            }
        }

        return employees;
    }


    // =========================
    // UPDATE EMPLOYEE
    // =========================

    @Override
    public boolean updateEmployee(
            Employee employee)
            throws SQLException {

        Connection con = null;

        try {

            con = jdbcUtil.getConnection();

            // Start transaction
            con.setAutoCommit(false);

            try (PreparedStatement ps =
                         con.prepareStatement(updateQuery)) {

                ps.setString(
                        1,
                        employee.getFullName()
                );

                ps.setString(
                        2,
                        employee.getEmail()
                );

                ps.setInt(
                        3,
                        employee.getDepartmentId()
                );

                ps.setInt(
                        4,
                        employee.getEmployeeId()
                );

                int count =
                        ps.executeUpdate();

                if (count > 0) {

                    // Commit transaction
                    con.commit();

                    LOGGER.info(
                            "Employee updated successfully."
                    );

                    return true;
                }

                con.rollback();

                return false;
            }

        } catch (SQLException e) {

            if (con != null) {

                try {
                    con.rollback();

                } catch (SQLException rollbackException) {

                    LOGGER.log(
                            Level.SEVERE,
                            "Rollback failed while updating employee",
                            rollbackException
                    );
                }
            }

            LOGGER.log(
                    Level.SEVERE,
                    "Error while updating employee",
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
                            "Error while closing connection",
                            e
                    );
                }
            }
        }
    }


    // =========================
    // CHANGE PASSWORD
    // =========================

    @Override
    public boolean changePassword(
            int userId,
            String newPassword)
            throws SQLException {

        Connection con = null;

        try {

            con = jdbcUtil.getConnection();

            // Start transaction
            con.setAutoCommit(false);

            try (PreparedStatement ps =
                         con.prepareStatement(
                                 changePasswordQuery)) {

                ps.setString(1, newPassword);
                ps.setInt(2, userId);

                int count =
                        ps.executeUpdate();

                if (count > 0) {

                    // Commit transaction
                    con.commit();

                    LOGGER.info(
                            "Employee password changed successfully."
                    );

                    return true;
                }

                con.rollback();

                return false;
            }

        } catch (SQLException e) {

            if (con != null) {

                try {
                    con.rollback();

                } catch (SQLException rollbackException) {

                    LOGGER.log(
                            Level.SEVERE,
                            "Rollback failed while changing password",
                            rollbackException
                    );
                }
            }

            LOGGER.log(
                    Level.SEVERE,
                    "Error while changing password",
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
                            "Error while closing connection",
                            e
                    );
                }
            }
        }
    }
}