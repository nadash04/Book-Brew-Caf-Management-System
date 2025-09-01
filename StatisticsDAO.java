package model;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO للإحصاءات: عدد الطلبات ومجموع المبيعات
 */
public class StatisticsDAO {

    /**
     * جلب العدد الكلي للطلبات
     */
    public int getTotalOrders() {
        String sql = "SELECT COUNT(*) AS total FROM orders";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total orders: " + e.getMessage());
        }
        return 0;
    }

    /**
     * جلب مجموع المبيعات
     */
    public double getTotalSales() {
        String sql = "SELECT SUM(total_amount) AS totalSales FROM orders";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("totalSales");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total sales: " + e.getMessage());
        }
        return 0.0;
    }
}
