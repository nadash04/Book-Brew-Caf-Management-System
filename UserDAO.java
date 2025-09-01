package model;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO للتعامل مع جدول المستخدمين (Users)
 */
public class UserDAO {

    private Connection con = DatabaseConnection.getConnection();
    private Object password;

    
   // إضافة مستخدم جديد
     public boolean insertUser(String username, String password, String firstName, String lastName, String email, String role) {
        String sql = "INSERT INTO users (user_name, user_password, first_name, last_name, email, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.setString(5, email);
            ps.setString(6, role);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
            return false;
        }
    }

    /**
     * جلب كل المستخدمين
     */
    public List<User> getAllUsers() {
      List<User> users = new ArrayList<>();
        // تم التصحيح: استخدام amount_spent بدلاً من amountSpent
        String sql = "SELECT user_id, first_name, last_name, email, role, amount_spent FROM users";

        try (PreparedStatement ps = con.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("first_name") + " " + rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getDouble("amount_spent") // تم التصحيح هنا
                );
                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }

        return users;
    }

    /**
     * التحقق من بيانات تسجيل الدخول
     */
    public String validateUser(String username, String password) {
        String sql = "SELECT role FROM users WHERE user_name = ? AND user_password = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace(); // إضافة طباعة StackTrace للتفاصيل
        }
        return null;
    }

    /**
     * التحقق من كلمة المرور المشفرة
     */
    public boolean checkPassword(String username, String encryptedPassword) throws SQLException {
        // تم التصحيح: استخدام user_name و user_password
        String sql = "SELECT user_password FROM users WHERE user_name = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("user_password").equals(encryptedPassword);
                }
            }
        }
        return false;
    }

    /**
     * تحديث كلمة المرور
     */
    public boolean updatePassword(String username, String encryptedNewPassword) throws SQLException {
          String sql = "UPDATE users SET user_password = ? WHERE user_name = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, encryptedNewPassword);
        ps.setString(2, username);
        return ps.executeUpdate() > 0;
    }

    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET user_name=?, first_name=?, last_name=?, email=?, role=?, amount_spent=? WHERE user_id=?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            
            // تقسيم الاسم الكامل إلى first_name و last_name
            String[] names = user.getName().split(" ", 2);
            String firstName = names[0];
            String lastName = names.length > 1 ? names[1] : "";

            ps.setString(1, user.getName().replace(" ", "").toLowerCase());
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getMembership());
            ps.setDouble(6, user.getAmountSpent());
            ps.setInt(7, user.getId());

            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id  = ?";

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0; // true إذا تم الحذف بنجاح

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }

    }

    public static void initializeTestUsers() {
        String checkSql = "SELECT COUNT(*) AS count FROM users";
        String insertSql = "INSERT INTO users (user_name, user_password, first_name, last_name, email, role) VALUES (?, ?, ?, ?, ?, ?)";

        String[][] testUsers = {
            {"admin", "admin123", "Admin", "User", "admin@example.com", "admin"},
            {"cashier", "cashier123", "Cashier", "User", "cashier@example.com", "cashier"},
            {"visitor", "visitor123", "Visitor", "User", "visitor@example.com", "visitor"}
        };

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement checkPs = con.prepareStatement(checkSql); ResultSet rs = checkPs.executeQuery()) {

            // التحقق إذا كان الجدول فارغاً
            if (rs.next() && rs.getInt("count") == 0) {
                try (PreparedStatement insertPs = con.prepareStatement(insertSql)) {
                    for (String[] user : testUsers) {
                        insertPs.setString(1, user[0]);
                        insertPs.setString(2, user[1]);
                        insertPs.setString(3, user[2]);
                        insertPs.setString(4, user[3]);
                        insertPs.setString(5, user[4]);
                        insertPs.setString(6, user[5]);
                        insertPs.addBatch();
                    }

                    insertPs.executeBatch();
                    System.out.println("Test users added successfully!");
                }
            } else {
                System.out.println("Users table is not empty. Skipping test data initialization.");
            }

        } catch (SQLException e) {
            System.err.println("Error initializing test users: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
