package xyz.niflheim.stockfish.util;

import java.sql.*;


import static xyz.niflheim.stockfish.util.ConnectionConst.*;

public class DBConnectionUtil {

    public static Connection getConnection() {
        try{
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void close(Connection con, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("close error");
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println("close error");
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("close error");
            }
        }
    }
}
