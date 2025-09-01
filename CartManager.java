package model;

import java.util.ArrayList;
import java.util.List;

/**
 * CartManager: كلاس لإدارة سلة المشتريات في التطبيق
 * يستخدم نمط Singleton لضمان وجود نسخة واحدة فقط في التطبيق.
 */
public class CartManager {

    // النسخة الوحيدة (Singleton)
    private static CartManager instance;

    // قائمة العناصر المضافة إلى السلة
    private List<OrderItem> cartItems;

    // الطلب الحالي المرتبط بالسلة
    private Order currentOrder;

    // المُنشئ الخاص: يمنع إنشاء نسخ جديدة من خارج الكلاس
    private CartManager() {
        cartItems = new ArrayList<>();
        currentOrder = new Order(0, 0, new java.util.Date(), 0.0, "pending");
    }

    /**
     * الحصول على النسخة الوحيدة (Singleton)
     * @return instance - النسخة الوحيدة من CartManager
     */
    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    /**
     * استرجاع قائمة العناصر في السلة
     * @return قائمة OrderItem في السلة
     */
    public List<OrderItem> getCartItems() { 
        return cartItems; 
    }

    /**
     * استرجاع الطلب الحالي المرتبط بالسلة
     * @return الطلب الحالي
     */
    public Order getCurrentOrder() { 
        return currentOrder; 
    }

    /**
     * إضافة عنصر إلى السلة
     * إذا كان العنصر موجودًا بالفعل، يتم زيادة الكمية بدلًا من الإضافة المكررة
     * @param item العنصر المراد إضافته
     */
    public void addToCart(OrderItem item) {
        boolean itemExists = false;
        for (OrderItem cartItem : cartItems) {
            if (cartItem.getProductId() == item.getProductId()) {
                // زيادة كمية العنصر إذا كان موجودًا بالفعل
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                itemExists = true;
                break;
            }
        }
        // إذا لم يكن موجودًا مسبقًا، أضف العنصر إلى القائمة
        if (!itemExists) {
            cartItems.add(item);
        }

        // تحديث الطلب الحالي بناءً على محتويات السلة
        currentOrder.setItems(new ArrayList<>(cartItems));
        currentOrder.calculateTotal();
    }

    /**
     * مسح جميع العناصر من السلة
     * ويعاد ضبط الطلب الحالي
     */
    public void clearCart() {
        cartItems.clear();
        currentOrder.getItems().clear();
        currentOrder.setTotal(0);
    }

    /**
     * حساب المجموع الكلي لكل العناصر في السلة
     * @return المجموع الكلي
     */
    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : cartItems) {
            total += item.getSubtotal(); // استخدام getSubtotal من OrderItem
        }
        currentOrder.setTotal(total);
        return total;
    }
}
