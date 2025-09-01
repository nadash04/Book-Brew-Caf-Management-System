package model;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DAO للتعامل مع جدول المسؤولين (Admins)
 * فقط عملية الإدخال (Create)
 */
public class AdminDAO {

    /**
     * إضافة مسؤول جديد
     * @param firstName الاسم الأول
     * @param lastName الاسم الأخير
     * @param email البريد الإلكتروني
     * @param pass كلمة المرور
     * @param username اسم المستخدم
     * @return true إذا تم الإدخال بنجاح
     */
    public boolean addAdmin(String firstName, String lastName, String email, String pass, String username) {
        String sql = "INSERT INTO admins (first_name, last_name, email, password, username) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, pass);  // لاحقًا يمكن تشفيرها قبل الإدخال
            ps.setString(5, username);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding admin: " + e.getMessage());
            return false;
        }
    }
}
