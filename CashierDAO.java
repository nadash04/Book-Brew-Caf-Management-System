package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DAO لإدارة بيانات الكاشير في قاعدة البيانات.
 * يحتوي على ميثود لإضافة كاشير جديد.
 */
public class CashierDAO {

    // إعدادات الاتصال بقاعدة البيانات
    private final String url = "jdbc:mysql://localhost:3306/finalprojectdb";  
    private final String user = "root";  
    private final String password = "";  

    /**
     * إضافة كاشير جديد إلى جدول cashiers
     *
     * @param firstName الاسم الأول
     * @param lastName الاسم الأخير
     * @param email البريد الإلكتروني
     * @param username اسم المستخدم
     * @param pass كلمة المرور
     * @return true إذا تم الإدراج بنجاح، false إذا فشل
     */
    public boolean addCashier(String firstName, String lastName, String email, String username, String pass) {
        String sql = "INSERT INTO cashiers (first_name, last_name, email, username, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            // تعيين المعاملات في الاستعلام
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, username);
            ps.setString(5, pass);

            // تنفيذ الاستعلام وإرجاع نتيجة النجاح
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            // طباعة الخطأ في حال حدوث مشكلة
            e.printStackTrace();
            return false;
        }
    }
}
