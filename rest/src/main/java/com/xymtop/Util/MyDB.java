package com.xymtop.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MyDB {



    private static final String password = "";
    private static final String usename = "";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    static final String drive = "com.mysql.cj.jdbc.Driver";
    static final String url = "jdbc:mysql://url:3306/javaweb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    Connection conn = null;

    public Connection Conn() {
        try {
            Class.forName(drive);
            conn = DriverManager.getConnection(url, usename, password);
            System.out.println("success");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void close(Connection conn, PreparedStatement psm) {
        if (conn != null) {
            try {
                conn.close();
                psm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
       new MyDB().Conn();
    }

}