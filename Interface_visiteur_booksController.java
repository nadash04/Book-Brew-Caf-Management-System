package books.coffee;

import books.coffee.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CartManager;

import model.Order;
import model.Order;
import model.OrderDAO;
import model.OrderItem;

public class Interface_visiteur_booksController implements Initializable {

    @FXML
    private Button g13;
    @FXML
    private Button g1;
    @FXML
    private Button g2;
    @FXML
    private Button g3;
    @FXML
    private Button g4;
    @FXML
    private Button g22;
    @FXML
    private Button g14;
    @FXML
    private Button g21;
    @FXML
    private ImageView g5;
    @FXML
    private ImageView g6;
    @FXML
    private ImageView g7;
    @FXML
    private ImageView g8;
    @FXML
    private ImageView g9;
    @FXML
    private ImageView g10;
    @FXML
    private ImageView g11;
    @FXML
    private ImageView g12;

    private CartManager cartManager;

    private List<OrderItem> cartItems = new ArrayList<>();
    private Order currentOrder;
    private List<Label> cartItemLabels = new ArrayList<>();
    private OrderDAO orderDAO = new OrderDAO();

    @FXML
    private VBox cartVBox;
    @FXML
    private Label cartStatusLabel;
    @FXML
    private Label cartEmptyLabel;
    @FXML
    private Label g31;
    @FXML
    private ImageView g30;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cartManager = CartManager.getInstance();

        updateCartDisplay();
    }

    @FXML
    private void handleCloseVisitorPage() {
        Stage stage = (Stage) g13.getScene().getWindow();
        stage.close();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main_interface.fxml"));
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.setTitle("Login");
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBooksCategory() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_books.fxml"));
            Stage stage = (Stage) g2.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDrinksCategory() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_drinks.fxml"));
            Stage stage = (Stage) g2.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSnacksCategory() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_snaks.fxml"));
            Stage stage = (Stage) g2.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleWorkspaceCategory() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_work.fxml"));
            Stage stage = (Stage) g2.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void confirmOrder() {
        if (cartManager.getCartItems().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Cart");
            alert.setHeaderText(null);
            alert.setContentText("Your cart is empty! Please add items before confirming.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Confirmed");
        alert.setHeaderText(null);
        alert.setContentText("Your order has been confirmed successfully!\n\n"
                + "Total: $" + String.format("%.2f", cartManager.calculateTotal()));

        alert.showAndWait();

        updateCartDisplay();
    }

    @FXML
    private void clearCart() {
        cartManager.clearCart();

        g31.setVisible(true);
        g30.setVisible(true);
        g14.setVisible(true);
        cartEmptyLabel.setVisible(true);
        g21.setVisible(true);
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg5(MouseEvent event) {
        cartManager.addToCart(new OrderItem(1, "Al-Hulooli Book", 3.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg6(MouseEvent event) {
        cartManager.addToCart(new OrderItem(2, "The Subtle Art of Not Giving a F*ck", 2.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg7(MouseEvent event) {
        cartManager.addToCart(new OrderItem(3, "Time Management Book", 4.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg8(MouseEvent event) {
        cartManager.addToCart(new OrderItem(4, "Children's Diary Book", 3.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg9(MouseEvent event) {
        cartManager.addToCart(new OrderItem(5, "Girls' Rules Book", 5.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg10(MouseEvent event) {
        cartManager.addToCart(new OrderItem(6, "Zikola Land 2 Book", 3.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg11(MouseEvent event) {
        cartManager.addToCart(new OrderItem(7, "Productive Thinking Book", 2.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCart12(MouseEvent event) {
        cartManager.addToCart(new OrderItem(8, "Rabri Cookies Book", 4.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleNewCategory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_newproducts.fxml"));
            Stage stage = (Stage) g22.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCartDisplay() {
        // مسح جميع العناصر الموجودة في cartVBox
        cartVBox.getChildren().clear();

        if (cartManager.getCartItems().isEmpty()) {
            // السلة فارغة - نعرض الصورة والرسالة

            cartVBox.getChildren().addAll(g31, g30, cartEmptyLabel);

            // إضافة الأزرار في الأسفل مع تباعد مناسب
            Pane spacer = new Pane();
            spacer.setMinHeight(20);
            VBox.setVgrow(spacer, Priority.ALWAYS);
            cartVBox.getChildren().addAll(spacer, g14, g21);

            g31.setVisible(true);
            g30.setVisible(true);
            cartEmptyLabel.setVisible(true);
        } else {
            // هناك طلبات - نعرضها أولاً
            Label cartTitle = new Label("Your Orders:");
            cartTitle.setStyle("-fx-text-fill: #7f5539; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 0 0 10 0;");
            cartVBox.getChildren().add(cartTitle);

            for (OrderItem item : cartManager.getCartItems()) {
                Label itemLabel = new Label(String.format("%s - %d x $%.2f = $%.2f", // تنسيق النص
                        item.getProductName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()));
                itemLabel.setStyle("-fx-text-fill: #7f5539; -fx-font-size: 14px; -fx-padding: 0 0 5 0;");
                cartVBox.getChildren().add(itemLabel);
            }

            // إضافة المجموع
            double total = cartManager.calculateTotal();
            Label totalLabel = new Label(String.format("Total: $%.2f", total));
            totalLabel.setStyle("-fx-text-fill: #7f5539; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 10 0 10 0;");
            cartVBox.getChildren().add(totalLabel);

            // إضافة Spacer لدفع الأزرار للأسفل
            Pane spacer = new Pane();
            spacer.setMinHeight(10);
            VBox.setVgrow(spacer, Priority.ALWAYS);

            // إضافة الأزرار بعد Spacer
            cartVBox.getChildren().addAll(spacer, g14, g21);

            // إخفاء الصورة والعنوان الأساسي
            g31.setVisible(false);
            g30.setVisible(false);
            cartEmptyLabel.setVisible(false);
        }
    }

    private void addToCart(int productId, String productName, double price) {
        // Check if item already exists in cart
        boolean itemExists = false;
        for (OrderItem item : cartItems) {
            if (item.getProductId() == productId) {
                item.setQuantity(item.getQuantity() + 1);
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            OrderItem newItem = new OrderItem(productId, productName, price, 1);
            cartItems.add(newItem);
            currentOrder.addItem(newItem);
        }

        updateCartDisplay();

    }

}
