/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Admin
 */
public class KetNoi {

    public static Connection layKetNoi() {
        Connection conn = null;
        String uRL = "jdbc:sqlserver://;databaseName=QLGIAYDEP";
        String userName = "sa";
        String password = "123";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(uRL, userName, password);
            if (conn != null) {
                System.out.println("Da ket noi");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
