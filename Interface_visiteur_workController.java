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
import model.OrderDAO;
import model.OrderItem;
import model.OrderItemDAO;

public class Interface_visiteur_workController implements Initializable {

    @FXML
    private Button k12;
    @FXML
    private Button k1;
    @FXML
    private Button k2;
    @FXML
    private Button k3;
    @FXML
    private Button k4;
    @FXML
    private Button h22;
    @FXML
    private Button k10;
    @FXML
    private Button k11;
    @FXML
    private ImageView k5;
    @FXML
    private ImageView k6;
    @FXML
    private ImageView k7;
    @FXML
    private ImageView k8;
    @FXML
    private ImageView k9;

    private CartManager cartManager;

    private List<OrderItem> cartItems = new ArrayList<>();
    private Order currentOrder;
    private List<Label> cartItemLabels = new ArrayList<>();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderItemDAO orderItemDAO = new OrderItemDAO();

    @FXML
    private VBox cartVBox;
    @FXML
    private Label k30;
    @FXML
    private ImageView k31;
    @FXML
    private Label cartEmptyLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cartManager = CartManager.getInstance();
        updateCartDisplay();
    }

    @FXML
    private void handleCloseVisitorPage(ActionEvent event) {
        Stage stage = (Stage) k12.getScene().getWindow();
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
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Books Menu");
            stage.show();

            ((Stage) k1.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDrinksCategory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_drinks.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Drinks Menu");
            stage.show();

            ((Stage) k2.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSnacksCategory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_snaks.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Snacks Menu");
            stage.show();

            ((Stage) k3.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleWorkspaceCategory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_work.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Snacks Menu");
            stage.show();

            ((Stage) k3.getScene().getWindow()).close();
        } catch (IOException e) {
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
        cartManager.addToCart(new OrderItem(500, "Meeting Room", 50.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg6(MouseEvent event) {
        cartManager.addToCart(new OrderItem(501, "PlayStation Room", 40.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg7(MouseEvent event) {
        cartManager.addToCart(new OrderItem(502, "Laptop Zone", 30.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg9(MouseEvent event) {
        cartManager.addToCart(new OrderItem(503, "Study Cubicle", 20.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg8(MouseEvent event) {
        cartManager.addToCart(new OrderItem(504, "Reading Room", 10.0, 1));
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
        cartVBox.getChildren().clear();

        if (cartManager.getCartItems().isEmpty()) {
            // السلة فارغة - نعرض الصورة والرسالة
            cartVBox.getChildren().addAll(k30, k31, cartEmptyLabel);

            // إضافة الأزرار في الأسفل مع تباعد مناسب
            Pane spacer = new Pane();
            spacer.setMinHeight(20);
            VBox.setVgrow(spacer, Priority.ALWAYS);
            cartVBox.getChildren().addAll(spacer, k10, k11);

            k30.setVisible(true);
            k31.setVisible(true);
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
            spacer.setMinHeight(5);
            VBox.setVgrow(spacer, Priority.ALWAYS);

            // إضافة الأزرار بعد Spacer
            cartVBox.getChildren().addAll(spacer, k10, k11);

            // إخفاء الصورة والعنوان الأساسي
            k30.setVisible(false);
            k31.setVisible(false);
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
