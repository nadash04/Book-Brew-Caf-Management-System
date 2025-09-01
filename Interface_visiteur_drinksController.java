package books.coffee;

import books.coffee.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import model.OrderItem;

public class Interface_visiteur_drinksController implements Initializable {

    @FXML
    private Button h13;
    @FXML
    private Button h1;
    @FXML
    private Button h2;
    @FXML
    private Button h3;
    @FXML
    private Button h4;
    @FXML
    private Button h22;
    @FXML
    private Button h14;
    @FXML
    private Button h21;
    @FXML
    private ImageView h5;
    @FXML
    private ImageView h6;
    @FXML
    private ImageView h7;
    @FXML
    private ImageView h8;
    @FXML
    private ImageView h9;
    @FXML
    private ImageView h10;
    @FXML
    private ImageView h11;
    @FXML
    private ImageView h12;

    private CartManager cartManager;

    private List<OrderItem> cartItems = new ArrayList<>();
    private Order currentOrder;
    private List<Label> cartItemLabels = new ArrayList<>();
    @FXML
    private VBox cartVBox;
    @FXML
    private Label h31;
    @FXML
    private ImageView h30;
    @FXML
    private Label cartEmptyLabel;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cartManager = CartManager.getInstance();     // الحصول على نسخة وحيدة من مدير السلة
        updateCartDisplay();

    }

    @FXML
    private void handleCloseVisitorPage(ActionEvent event) {
        Stage stage = (Stage) h13.getScene().getWindow();
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
    private void handleBooksCategory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_books.fxml"));
            Stage stage = (Stage) h1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDrinksCategory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_drinks.fxml"));
            Stage stage = (Stage) h1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSnacksCategory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_snaks.fxml"));
            Stage stage = (Stage) h1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleWorkspaceCategory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_work.fxml"));
            Stage stage = (Stage) h1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void confirmOrder(ActionEvent event) {
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
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg5(MouseEvent event) {
        cartManager.addToCart(new OrderItem(300, "Cappuccino", 3.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg6(MouseEvent event) {
        cartManager.addToCart(new OrderItem(301, "Espresso", 2.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg7(MouseEvent event) {
        cartManager.addToCart(new OrderItem(302, "Macchiato", 4.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg8(MouseEvent event) {
        cartManager.addToCart(new OrderItem(303, "Coffee", 3.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg9(MouseEvent event) {
        cartManager.addToCart(new OrderItem(304, "Tea", 5.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg10(MouseEvent event) {
        cartManager.addToCart(new OrderItem(305, "Flat White", 3.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg11(MouseEvent event) {
        cartManager.addToCart(new OrderItem(306, "Strawberry Juice", 2.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCart12(MouseEvent event) {
        cartManager.addToCart(new OrderItem(307, "Mango Juice", 4.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleNewCategory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_newproducts.fxml"));
            Stage stage = (Stage) h22.getScene().getWindow();
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
            cartVBox.getChildren().addAll(h31, h30, cartEmptyLabel);

            // إضافة الأزرار في الأسفل مع تباعد مناسب
            Pane spacer = new Pane();
            spacer.setMinHeight(20);
            VBox.setVgrow(spacer, Priority.ALWAYS);
            cartVBox.getChildren().addAll(spacer, h14, h21);

            h31.setVisible(true);
            h30.setVisible(true);
            cartEmptyLabel.setVisible(true);
        } else {
            // هناك طلبات - نعرضها أولاً
            Label cartTitle = new Label("Your Orders:");
            cartTitle.setStyle("-fx-text-fill: #7f5539; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 0 0 10 0;");
            cartVBox.getChildren().add(cartTitle);

            for (OrderItem item : cartManager.getCartItems()) {
                Label itemLabel = new Label(String.format("%s - %d x $%.2f = $%.2f",
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
            spacer.setMinHeight(20);
            VBox.setVgrow(spacer, Priority.ALWAYS);

            // إضافة الأزرار بعد Spacer
            cartVBox.getChildren().addAll(spacer, h14, h21);

            // إخفاء الصورة والعنوان الأساسي
            h31.setVisible(false);
            h30.setVisible(false);
            cartEmptyLabel.setVisible(false);
        }
    }

    private void addToCart(int productId, String productName, double price) {
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
