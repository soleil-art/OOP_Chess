package xyz.niflheim.stockfish.repository;

import javax.swing.*;
import java.sql.*;

import static xyz.niflheim.stockfish.util.DBConnectionUtil.close;
import static xyz.niflheim.stockfish.util.DBConnectionUtil.getConnection;

public class UserRepository {

    public User findById(String userId) {
        String sql = "select * from user where Id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,userId);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                User user = new User();
                user.setId(rs.getString("Id"));
                user.setPassword(rs.getString("password"));
                return user;
            }else {
                throw new RuntimeException("user not found ");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con,pstmt,rs);
        }
    }
    public User save(User user) {
        String sql = "insert into user (Id, password) values (?,?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con =getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user.getId());
            pstmt.setString(2,user.getPassword());
            pstmt.executeUpdate();
            return user;

        } catch (SQLException e) {
            throw new RuntimeException("user not save");
        }finally {
            close(con,pstmt,null);
        }
    }
}
