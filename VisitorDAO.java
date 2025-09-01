package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * DAO للتعامل مع الزوار (Visitors)
 * مع تشفير كلمة المرور قبل الإدخال
 */
public class VisitorDAO {

    private final String url = "jdbc:mysql://localhost:3306/bookbrew_db";
    private final String user = "root";
    private final String password = "";

    // تشفير كلمة المرور باستخدام SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * إضافة زائر جديد مع تشفير كلمة المرور
     */
    public boolean addVisitor(String firstName, String lastName, String email, String pass, String phone) {
        String sql = "INSERT INTO visitors (first_name, last_name, email, password, phone) VALUES (?, ?, ?, ?, ?)";
        String hashedPass = hashPassword(pass);

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, hashedPass);
            ps.setString(5, phone);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding visitor: " + e.getMessage());
            return false;
        }
    }
}
