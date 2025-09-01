package model;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO لإدارة عناصر الطلب (OrderItem) في قاعدة البيانات.
 * يحتوي على ميثودات لإدراج العناصر، جلبها حسب الطلب، وحذفها.
 */
public class OrderItemDAO {

    /**
     * إدراج عنصر جديد في جدول order_items مرتبط بطلب محدد.
     *
     * @param orderId معرف الطلب الذي ينتمي إليه العنصر
     * @param item كائن OrderItem يحتوي على تفاصيل المنتج والكمية والسعر
     * @return عدد الصفوف التي تم إدراجها (عادة 1 إذا نجح)
     */
    public int insertOrderItem(int orderId, OrderItem item) {
        String sql = "INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);                 // ربط العنصر بالطلب
            ps.setInt(2, item.getProductId());     // معرف المنتج
            ps.setString(3, item.getProductName());// اسم المنتج
            ps.setInt(4, item.getQuantity());      // كمية المنتج
            ps.setDouble(5, item.getUnitPrice());  // سعر الوحدة

            // تنفيذ الإدراج وإرجاع عدد الصفوف المضافة
            return ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting order item: " + e.getMessage());
            return 0; // إرجاع 0 في حالة حدوث خطأ
        }
    }

    /**
     * جلب كل عناصر الطلب المرتبطة بمعرف طلب محدد.
     *
     * @param orderId معرف الطلب
     * @return قائمة OrderItem المرتبطة بالطلب
     */
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId); // تمرير معرف الطلب
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

//   int quantity)

                    OrderItem item = new OrderItem(
                            rs.getInt("productId"),           // معرف العنصر
                            rs.getString("productName"),     // معرف الطلب
                            rs.getDouble("unit_price"),
                            rs.getInt("product_id")  // معرف المنتج
                    );
                    items.add(item); // إضافة العنصر إلى القائمة
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching order items: " + e.getMessage());
        }

        return items; // إرجاع قائمة العناصر
    }

    /**
     * حذف كل العناصر المرتبطة بطلب محدد.
     *
     * @param orderId معرف الطلب
     * @return true إذا تم حذف صف واحد أو أكثر، false إذا لم يتم حذف أي صف
     */
    public boolean deleteOrderItemsByOrderId(int orderId) {
        String sql = "DELETE FROM order_items WHERE order_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId); // تمرير معرف الطلب
            return ps.executeUpdate() > 0; // تحقق من نجاح الحذف

        } catch (SQLException e) {
            System.err.println("Error deleting order items: " + e.getMessage());
            return false;
        }
    }
}
