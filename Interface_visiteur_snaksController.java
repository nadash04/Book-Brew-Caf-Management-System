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

public class Interface_visiteur_snaksController implements Initializable {

    @FXML
    private Button i13;
    @FXML
    private Button i1;
    @FXML
    private Button i2;
    @FXML
    private Button i3;
    @FXML
    private Button i4;
    @FXML
    private Button i22;
    @FXML
    private Button i14;
    @FXML
    private Button i21;
    @FXML
    private ImageView i5;
    @FXML
    private ImageView i6;
    @FXML
    private ImageView i7;
    @FXML
    private ImageView i8;
    @FXML
    private ImageView i9;
    @FXML
    private ImageView i10;
    @FXML
    private ImageView i11;
    @FXML
    private ImageView i12;

    private CartManager cartManager;

    private List<OrderItem> cartItems = new ArrayList<>();
    private Order currentOrder;
    private List<Label> cartItemLabels = new ArrayList<>();
     private OrderDAO orderDAO = new OrderDAO();
    @FXML
    private VBox cartVBox;
    @FXML
    private Label i30;
    @FXML
    private ImageView i31;
    @FXML
    private Label cartEmptyLabel;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cartManager = CartManager.getInstance();
        updateCartDisplay();

    }

    @FXML
    private void handleCloseVisitorPage(ActionEvent event) {
        Stage stage = (Stage) i13.getScene().getWindow();
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
            Stage stage = (Stage) i1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDrinksCategory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_drinks.fxml"));
            Stage stage = (Stage) i2.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSnacksCategory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_snaks.fxml"));
            Stage stage = (Stage) i2.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleWorkspaceCategory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_visiteur_work.fxml"));
            Stage stage = (Stage) i2.getScene().getWindow();
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
        cartManager.addToCart(new OrderItem(200, "Cheese Sandwich", 3.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg6(MouseEvent event) {
        cartManager.addToCart(new OrderItem(202, "Avocado Sandwich", 2.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg7(MouseEvent event) {
        cartManager.addToCart(new OrderItem(203,  "Turkey Sandwich",  4.0,1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg8(MouseEvent event) {
        cartManager.addToCart(new OrderItem(204, "Tuna Sandwich", 3.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg9(MouseEvent event) {
        cartManager.addToCart(new OrderItem(205, "Browniez", 5.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg10(MouseEvent event) {
        cartManager.addToCart(new OrderItem(206,  "Donut", 3.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCartg11(MouseEvent event) {
        cartManager.addToCart(new OrderItem(207, "Cookies",  2.0, 1));
        updateCartDisplay();
    }

    @FXML
    private void handleAddToCart12(MouseEvent event) {
        cartManager.addToCart(new OrderItem(208,"Croissant", 4.0, 1));
        updateCartDisplay();
    }

    private void updateCartDisplay() {
        // مسح جميع العناصر الموجودة في cartVBox
        cartVBox.getChildren().clear();

        if (cartManager.getCartItems().isEmpty()) {
            // السلة فارغة - نعرض الصورة والرسالة

            cartVBox.getChildren().addAll(i30, i31, cartEmptyLabel);

            // إضافة الأزرار في الأسفل مع تباعد مناسب
            Pane spacer = new Pane();
            spacer.setMinHeight(20);
            VBox.setVgrow(spacer, Priority.ALWAYS);
            cartVBox.getChildren().addAll(spacer, i14, i21);

            i30.setVisible(true);
            i31.setVisible(true);
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
            spacer.setMinHeight(10);
            VBox.setVgrow(spacer, Priority.ALWAYS);

            // إضافة الأزرار بعد Spacer
            cartVBox.getChildren().addAll(spacer, i14, i21);

            // إخفاء الصورة والعنوان الأساسي
            i31.setVisible(false);
            i30.setVisible(false);
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
