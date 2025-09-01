 
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

 
public class Order {

    private int id;
    private int userId;
    private Date date;
    private double total;
    private String status;
    private List<OrderItem> items;

    public Order(int id, int userId, Date date, double total, String status) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.total = total;
        this.status = status;
        this.items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public void removeItem(OrderItem item) {
        this.items.remove(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        this.total = total;
        return total;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", userId=" + userId + ", date=" + date + ", total=" + total + ", status=" + status + ", items=" + items + '}';
    }

      public Date getOrderDate() {
        return this.date;
    }

    public String getTotalAmount() {
        return String.format("%.2f", this.total);
    }
}
