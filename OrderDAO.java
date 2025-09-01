package model;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DAO لإدارة الطلبات (Order) في قاعدة البيانات.
 * يتعامل مع جدول orders ويستخدم OrderItemDAO لإدارة العناصر المرتبطة بكل طلب.
 */
public class OrderDAO {

    private OrderItemDAO orderItemDAO = new OrderItemDAO(); // للتعامل مع جدول order_items

    /**
     * إدراج طلب جديد مع عناصره
     * @param order كائن Order يحتوي على بيانات الطلب وقائمة العناصر
     * @return معرف الطلب الجديد إذا نجح الإدراج، -1 إذا فشل
     */
    public int insertOrder(Order order) {
        String sqlOrder = "INSERT INTO orders (userId, orderDate, totalAmount, status) VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement psOrder = con.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {

            // تعيين بيانات الطلب
            psOrder.setInt(1, order.getUserId());
            psOrder.setTimestamp(2, new Timestamp(order.getDate().getTime()));
            psOrder.setDouble(3, order.getTotal());
            psOrder.setString(4, order.getStatus());

            if (psOrder.executeUpdate() == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            // الحصول على معرف الطلب المولد تلقائياً
            try (ResultSet generatedKeys = psOrder.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);

                    // إدراج كل عناصر الطلب
                    if (order.getItems() != null) {
                        for (OrderItem item : order.getItems()) {
                            int insertedRows = orderItemDAO.insertOrderItem(orderId, item);
                            if (insertedRows <= 0) {
                                throw new SQLException("Failed to insert order item: " + item.getProductName());
                            }
                        }
                    }
                    return orderId;

                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error inserting order: " + e.getMessage());
            return -1;
        }
    }

    /**
     * جلب كل الطلبات مع عناصرها
     * @return قائمة بالطلبات Order
     */
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int orderId = rs.getInt("id");

                // إنشاء كائن Order
                Order order = new Order(
                        orderId,
                        rs.getInt("userId"),
                        new Date(rs.getTimestamp("orderDate").getTime()),
                        rs.getDouble("totalAmount"),
                        rs.getString("status")
                );

                // جلب العناصر المرتبطة بالطلب
                List<OrderItem> items = orderItemDAO.getOrderItemsByOrderId(orderId);
                order.setItems(items);

                orders.add(order);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving orders: " + e.getMessage());
        }

        return orders;
    }

    /**
     * تحديث حالة الطلب
     * @param orderId معرف الطلب
     * @param status الحالة الجديدة
     * @return true إذا تم التحديث بنجاح
     */
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating order status: " + e.getMessage());
            return false;
        }
    }

    /**
     * حذف الطلب مع كل العناصر المرتبطة به
     * @param orderId معرف الطلب
     * @return true إذا تم الحذف بنجاح
     */
    public boolean deleteOrder(int orderId) {
        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false); // بدء معاملة

            // حذف العناصر أولاً
            boolean itemsDeleted = orderItemDAO.deleteOrderItemsByOrderId(orderId);
            if (!itemsDeleted) {
                con.rollback(); // التراجع في حالة فشل الحذف
                return false;
            }

            // حذف الطلب
            String sqlOrder = "DELETE FROM orders WHERE id = ?";
            try (PreparedStatement psOrder = con.prepareStatement(sqlOrder)) {
                psOrder.setInt(1, orderId);
                if (psOrder.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }
            }

            con.commit(); // تأكيد المعاملة
            return true;

        } catch (SQLException e) {
            System.err.println("Error deleting order: " + e.getMessage());
            return false;
        }
    }
}
