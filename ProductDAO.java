package model;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO للتعامل مع جدول المنتجات (Products) CRUD كامل: إضافة، تعديل، حذف، جلب
 */
public class ProductDAO {

    /**
     * إضافة منتج جديد للقاعدة
     */
    public boolean insertProduct(Product product) {
        String sql = "INSERT INTO products (products_name, products_category, products_price, quantity, products_imagepath, description, available) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getImagePath());
            ps.setString(6, product.getDescription());
            ps.setBoolean(7, product.isAvailable());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                // الحصول على الID المُنشأ تلقائياً
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error inserting product: " + e.getMessage());
            return false;
        }
    }

    /**
     * جلب جميع المنتجات حسب الفئة
     */
    public List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        // تم تعديل أسماء الأعمدة
        String sql = "SELECT * FROM products WHERE products_category = ?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("products_id"), // تم التعديل هنا
                        rs.getString("products_name"), // تم التعديل هنا
                        rs.getString("products_category"), // تم التعديل هنا
                        rs.getDouble("products_price"), // تم التعديل هنا
                        rs.getInt("quantity"),
                        rs.getString("products_imagepath"), // تم التعديل هنا
                        rs.getString("description"),
                        rs.getBoolean("available")
                );
                products.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving products by category: " + e.getMessage());
        }
        return products;
    }

    /**
     * جلب كل المنتجات
     */
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        // تم تعديل أسماء الأعمدة في الاستعلام
        String sql = "SELECT products_id, products_name, products_category, products_price, quantity, products_imagepath, description, available FROM products";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("products_id"), // تم التعديل هنا
                        rs.getString("products_name"), // تم التعديل هنا
                        rs.getString("products_category"), // تم التعديل هنا
                        rs.getDouble("products_price"), // تم التعديل هنا
                        rs.getInt("quantity"),
                        rs.getString("products_imagepath"), // تم التعديل هنا
                        rs.getString("description"),
                        rs.getBoolean("available")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all products: " + e.getMessage());
        }
        return products;
    }

    /**
     * تعديل منتج موجود
     */
    public boolean updateProduct(Product product) {
         String sql = "UPDATE products SET products_name=?, products_category=?, products_price=?, quantity=?, products_imagepath=?, description=?, available=? WHERE products_id=?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getImagePath());
            ps.setString(6, product.getDescription());
            ps.setBoolean(7, product.isAvailable());
            ps.setInt(8, product.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
            return false;
        }
    }

    /**
     * حذف منتج حسب المعرف
     */
    public boolean deleteProduct(int id) {
         String sql = "DELETE FROM products WHERE products_id=?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }
    // اختبار اتصال قاعدة البيانات
    public boolean testConnection() {
    try (Connection con = DatabaseConnection.getConnection()) {
        return con != null && !con.isClosed();
    } catch (SQLException e) {
        System.err.println("فشل الاتصال بقاعدة البيانات: " + e.getMessage());
        return false;
    }
}
}