package com.internet.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find DB driver");
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.put("user", "test");
        properties.put("password", "testPwd");
        String url = "jdbc:mysql://localhost:3306/internet_shop?serverTimezone=UTC";
        try {
            return DriverManager.getConnection(url,properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to DB", e);
        }
    }
}
