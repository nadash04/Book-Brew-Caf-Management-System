package model;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO للتعامل مع جدول المدفوعات (Payments)
 * عمليات CRUD أساسية: إضافة، جلب، تعديل الحالة
 */
public class PaymentDAO {

    /**
     * جلب كل المدفوعات
     */
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Payment p = new Payment(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                );
                payments.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving payments: " + e.getMessage());
        }

        return payments;
    }

    /**
     * إضافة دفع جديد
     */
    public boolean insertPayment(Payment payment) {
        String sql = "INSERT INTO payments (order_id, amount, status) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, payment.getOrderId());
            ps.setDouble(2, payment.getAmount());
            ps.setString(3, payment.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting payment: " + e.getMessage());
            return false;
        }
    }

    /**
     * تحديث حالة الدفع
     */
    public boolean updatePaymentStatus(int paymentId, String newStatus) {
        String sql = "UPDATE payments SET status = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, paymentId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating payment status: " + e.getMessage());
            return false;
        }
    }
}
