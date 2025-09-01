package database;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // بيانات الاتصال بقاعدة البيانات
    private static final String URL = "jdbc:mysql://localhost:3306/finalprojectdb?useSSL=false&serverTimezone=UTC&autoReconnect=true";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // متغير الاتصال الوحيد
    private static Connection connection;

    // مُنشئ خاص لمنع إنشاء نسخ من خارج الكلاس
    private DatabaseConnection() {
    }

    /**
     * الحصول على الاتصال الحالي أو إنشاء واحد جديد إذا لم يكن موجودًا
     *
     * @return الاتصال بقاعدة البيانات
     */
    public static Connection getConnection() {
        try {
            // تحميل Driver الخاص بMySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // إنشاء اتصال جديد في كل مرة
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
            return connection;

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * غلق الاتصال بالقاعدة
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Failed to close the database connection.");
                e.printStackTrace();
            }
        }
    }

    public static void closeStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                System.err.println("Failed to close statement.");
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Failed to close result set.");
                e.printStackTrace();
            }
        }
    }
}
