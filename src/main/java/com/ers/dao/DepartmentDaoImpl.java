package com.ers.dao;

import com.ers.model.Department;
import com.ers.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentDaoImpl implements IDepartmentDao {

    // ================= LOGGER =================
    private static final Logger LOGGER =
            Logger.getLogger(DepartmentDaoImpl.class.getName());

    private final JDBCUtil jdbcUtil = new JDBCUtil();

    // ================= SQL QUERIES =================

    private final String insertQuery =
            "INSERT INTO departments (department_name, manager_id) " +
                    "VALUES (?, ?)";

    private final String selectAllQuery =
            "SELECT * FROM departments";

    private final String selectByIdQuery =
            "SELECT * FROM departments WHERE department_id = ?";

    private final String searchQuery =
            "SELECT * FROM departments " +
                    "WHERE department_name LIKE ?";

    private final String updateQuery =
            "UPDATE departments SET department_name = ?, " +
                    "manager_id = ? WHERE department_id = ?";


    // ================= ADD DEPARTMENT =================

    @Override
    public Department addDepartment(Department department)
            throws SQLException {

        // TRANSACTION START
        try (Connection con = jdbcUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(
                             insertQuery,
                             java.sql.Statement.RETURN_GENERATED_KEYS)) {

            con.setAutoCommit(false);

            try {

                ps.setString(1, department.getDepartmentName());
                ps.setInt(2, department.getManagerId());

                ps.executeUpdate();

                // Get generated department ID
                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        department.setDepartmentId(
                                rs.getInt(1)
                        );
                    }
                }

                // COMMIT
                con.commit();

                LOGGER.info(
                        "Department added successfully: "
                                + department.getDepartmentName()
                );

                return department;

            } catch (SQLException e) {

                //  ROLLBACK
                con.rollback();

                //  LOGGER
                LOGGER.log(
                        Level.SEVERE,
                        "Error while adding department",
                        e
                );

                throw e;
            }

        }
    }


    // ================= VIEW ALL DEPARTMENTS =================

    @Override
    public List<Department> getAllDepartments()
            throws SQLException {

        List<Department> departments = new ArrayList<>();

        try (Connection con = jdbcUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(selectAllQuery);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Department department =
                        new Department();

                department.setDepartmentId(
                        rs.getInt("department_id")
                );

                department.setDepartmentName(
                        rs.getString("department_name")
                );

                department.setManagerId(
                        rs.getInt("manager_id")
                );

                departments.add(department);
            }

            LOGGER.info(
                    "Fetched all departments successfully"
            );

        } catch (SQLException e) {

            //  LOGGER
            LOGGER.log(
                    Level.SEVERE,
                    "Error while fetching departments",
                    e
            );

            throw e;
        }

        return departments;
    }


    // ================= VIEW DEPARTMENT BY ID =================

    @Override
    public Department getDepartmentById(int departmentId)
            throws SQLException {

        try (Connection con = jdbcUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(selectByIdQuery)) {

            ps.setInt(1, departmentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Department department =
                            new Department();

                    department.setDepartmentId(
                            rs.getInt("department_id")
                    );

                    department.setDepartmentName(
                            rs.getString("department_name")
                    );

                    department.setManagerId(
                            rs.getInt("manager_id")
                    );

                    LOGGER.info(
                            "Department found with ID: "
                                    + departmentId
                    );

                    return department;
                }
            }

        } catch (SQLException e) {

            //  LOGGER
            LOGGER.log(
                    Level.SEVERE,
                    "Error while fetching department by ID",
                    e
            );

            throw e;
        }

        return null;
    }


    // ================= SEARCH DEPARTMENTS =================

    @Override
    public List<Department> searchDepartments(String keyword)
            throws SQLException {

        List<Department> departments = new ArrayList<>();

        try (Connection con = jdbcUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(searchQuery)) {

            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Department department =
                            new Department();

                    department.setDepartmentId(
                            rs.getInt("department_id")
                    );

                    department.setDepartmentName(
                            rs.getString("department_name")
                    );

                    department.setManagerId(
                            rs.getInt("manager_id")
                    );

                    departments.add(department);
                }
            }

            LOGGER.info(
                    "Department search completed"
            );

        } catch (SQLException e) {

            //  LOGGER
            LOGGER.log(
                    Level.SEVERE,
                    "Error while searching departments",
                    e
            );

            throw e;
        }

        return departments;
    }


    // ================= UPDATE DEPARTMENT =================

    @Override
    public boolean updateDepartment(Department department)
            throws SQLException {

        // TRANSACTION START
        try (Connection con = jdbcUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(updateQuery)) {

            con.setAutoCommit(false);

            try {

                ps.setString(
                        1,
                        department.getDepartmentName()
                );

                ps.setInt(
                        2,
                        department.getManagerId()
                );

                ps.setInt(
                        3,
                        department.getDepartmentId()
                );

                int rowsAffected = ps.executeUpdate();

                // COMMIT
                con.commit();

                if (rowsAffected > 0) {

                    LOGGER.info(
                            "Department updated successfully: "
                                    + department.getDepartmentId()
                    );

                    return true;
                }

                return false;

            } catch (SQLException e) {

                //  ROLLBACK
                con.rollback();

                //  LOGGER
                LOGGER.log(
                        Level.SEVERE,
                        "Error while updating department",
                        e
                );

                throw e;
            }

        }
    }
}