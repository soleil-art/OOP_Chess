package xyz.niflheim.stockfish;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConectionTest
{

    @Test
    void test(){
        Connection connection=null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chess", "root", "@Jmy8gO77Zek0vcIeD");
            Class<? extends Connection> aClass = connection.getClass();
            System.out.println("connection = " + connection);
            System.out.println("aClass = " + aClass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
