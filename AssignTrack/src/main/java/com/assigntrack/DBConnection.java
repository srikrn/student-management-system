package com.assigntrack;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        Connection con = null; 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/assigntrack",
                "root",
                "root"
            );

            //System.out.println("DB Connected");

        } catch (Exception e) {
            //System.out.println("❌ DB CONNECTION FAILED");
            e.printStackTrace();
        }
        return con; 
    }
}