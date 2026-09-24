package com.ers.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

    private static final String URL =
            "jdbc:mysql://localhost:3306/expense_reimbursement_system";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "codeforinterview";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}