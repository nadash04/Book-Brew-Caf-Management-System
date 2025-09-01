package books.coffee;

 import books.coffee.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.CartManager;
import model.Order;
import model.OrderDAO;
import model.OrderItem;
import model.User;

public class Interface_cashareController implements Initializable {

    @FXML
    private Button x1;
    @FXML
    private Button x2;
    @FXML
    private Button x3;
    @FXML
    private Button x4;
    @FXML
    private Pane x6;
    @FXML
    private Button x5;
    @FXML
    private Pane x7;
    @FXML
    private Pane x8;
    @FXML
    private Pane x9;
    @FXML
    private TextField searchField;

    @FXML
    private Pane x10;
    private List<Order> orders = new ArrayList<>();
    private List<OrderItem> cartItems = new ArrayList<>();
    private Order currentOrder;
    @FXML
    private Label x20;

    private OrderDAO orderDAO = new OrderDAO(); 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadOrdersFromDatabase(); //يحمل الطلبات من قاعدة البيانات. إذا لم توجد طلبات، ينشئ عينات للاختبار.
        displayCartItems();  //تعرض محتويات عربة التسوق في منطقة التفاصيل (x10) عند فتح الواجهة لأول مرة.
    }

    @FXML
    private void handleBackAction(ActionEvent event) {
        Stage stage = (Stage) x1.getScene().getWindow();
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
    private void handleOrdersTabAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Interface_cashare.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePaymentsTabAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Interface_cashare_Payments.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleStatisticsTabAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Interface_cashare_Statistics.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleOrderCardClick(MouseEvent event) {
        Pane clickedPane = (Pane) event.getSource();
        String paneId = clickedPane.getId();

        // تحديد الطلب بناءً على البانل الذي تم النقر عليه
        Order selectedOrder = null;

        if (paneId.equals("x6")) {
            selectedOrder = orders.get(0); // الطلب الأول
        } else if (paneId.equals("x7")) {
            selectedOrder = orders.get(1); // الطلب الثاني
        } else if (paneId.equals("x8")) {
            selectedOrder = orders.get(2); // الطلب الثالث
        } else if (paneId.equals("x9")) {
            selectedOrder = orders.get(3); // الطلب الرابع
        }

        if (selectedOrder != null) {
            displayOrderDetails(selectedOrder);
        }
    }

    @FXML
    private void searchRequest(ActionEvent event) {
        String searchText = searchField.getText().trim();
        System.out.println("Searching for: " + searchText);

        if (searchText.isEmpty()) {
            return;
        }

        // البحث في الطلبات
        for (Order order : orders) {
            // البحث برقم الطلب
            if (String.valueOf(order.getId()).contains(searchText)) {
                displayOrderDetails(order);
                return;
            }

            // البحث في عناصر الطلب
            for (OrderItem item : order.getItems()) {
                if (item.getProductName().contains(searchText)) {
                    displayOrderDetails(order);
                    return;
                }
            }
        }

        // إذا لم يتم العثور على أي طلب
        x10.getChildren().clear();
        Label notFoundLabel = new Label("لا توجد نتائج لـ: " + searchText);
        notFoundLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #7f5539;");
        x10.getChildren().add(notFoundLabel);

    }

    @FXML
    private void handlerequest(MouseEvent event) {
        x10.getChildren().clear();
        x10.getChildren().add(x20);
    }

    private void createSampleOrders() {

        Order order1 = new Order(1, 101, new Date(), 15.0, "Ready");
        order1.addItem(new OrderItem(1, "Cappuccino", 3.0, 2));
        order1.addItem(new OrderItem(2, "Zikola Land 2 Book", 3.0, 1));
        order1.addItem(new OrderItem(3, "Cookies",  6.0, 1));

        Order order2 = new Order(2, 102, new Date(), 10.0, "Ready");
        order2.addItem(new OrderItem(4, "Espresso", 2.0, 1));
        order2.addItem(new OrderItem(5, "Rabri Cookies Book", 2.0, 1));
        order2.addItem(new OrderItem(6, "Cheese Sandwich", 6.0, 1));

        Order order3 = new Order(3, 103, new Date(), 12.0, "In preparation");
        order3.addItem(new OrderItem(7, "Tea", 5.0, 1));
        order3.addItem(new OrderItem(8, "Productive Thinking Book",  5.0,1));
        order3.addItem(new OrderItem(9, "Cookies", 2.0, 1));

        Order order4 = new Order(4, 104, new Date(), 8.0, "In preparation");
        order4.addItem(new OrderItem(10, "Strawberry Juice", 2.0, 1));
        order4.addItem(new OrderItem(11, "Children's Diary Book", 2.0, 1));
        order4.addItem(new OrderItem(12, "Browniez", 2.0, 2));

        orders.addAll(Arrays.asList(order1, order2, order3, order4));
    }

    private void displayOrderDetails(Order order) {

        x10.getChildren().clear();

        VBox orderDetails = new VBox(10);
        orderDetails.setStyle("-fx-padding: 15; -fx-background-color: transparent;");
        orderDetails.setMaxWidth(x10.getPrefWidth() - 30); // تقليل العرض لترك مساحة للهامش

        Label orderTitle = new Label("Order Details #" + order.getId());
        orderTitle.setStyle("-fx-font-size: 18; -fx-text-fill: #7f5539; -fx-font-weight: bold;");
        orderTitle.setMaxWidth(Double.MAX_VALUE);
        orderTitle.setAlignment(Pos.CENTER);

        Separator separator1 = new Separator();
        separator1.setStyle("-fx-background-color: #E3AE66;");

        // Customer Info
        HBox customerBox = new HBox(10);
        Label customerTitle = new Label("Customer:");
        customerTitle.setStyle("-fx-font-size: 14; -fx-text-fill: #7f5539; -fx-font-weight: bold;");
        Label customerLabel = new Label("Aluaman Osama");
        customerLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #7f5539;");
        customerBox.getChildren().addAll(customerTitle, customerLabel);

        // Status Info
        HBox statusBox = new HBox(10);
        Label statusTitle = new Label("Status:");
        statusTitle.setStyle("-fx-font-size: 14; -fx-text-fill: #7f5539; -fx-font-weight: bold;");
        Label statusLabel = new Label(order.getStatus());
        statusLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #7f5539;");
        statusBox.getChildren().addAll(statusTitle, statusLabel);

        // Date Info
        HBox dateBox = new HBox(10);
        Label dateTitle = new Label("Date:");
        dateTitle.setStyle("-fx-font-size: 14; -fx-text-fill: #7f5539; -fx-font-weight: bold;");
        Label dateLabel = new Label(order.getDate().toString());
        dateLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #7f5539;");
        dateBox.getChildren().addAll(dateTitle, dateLabel);

        Separator separator2 = new Separator();
        separator2.setStyle("-fx-background-color: #E3AE66;");

        // Items Title
        Label itemsTitle = new Label("Items:");
        itemsTitle.setStyle("-fx-font-size: 16; -fx-text-fill: #7f5539; -fx-font-weight: bold;");
        itemsTitle.setMaxWidth(Double.MAX_VALUE);
        itemsTitle.setAlignment(Pos.CENTER_LEFT);

        // Items List
        VBox itemsBox = new VBox(5);
        itemsBox.setStyle("-fx-padding: 0 0 0 15;");

        for (OrderItem item : order.getItems()) {
            HBox itemBox = new HBox(10);
            itemBox.setAlignment(Pos.CENTER_LEFT);

            Label nameLabel = new Label(item.getProductName());
            nameLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #7f5539; -fx-font-weight: bold;");

            Label quantityLabel = new Label(String.format("x%d", item.getQuantity()));
            quantityLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #7f5539;");

            Label priceLabel = new Label(String.format("$%.2f", item.getSubtotal()));
            priceLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #7f5539;");

            itemBox.getChildren().addAll(nameLabel, quantityLabel, priceLabel);
            itemsBox.getChildren().add(itemBox);
        }

        Separator separator3 = new Separator();
        separator3.setStyle("-fx-background-color: #E3AE66;");

        // Total
        HBox totalBox = new HBox(10);
        Label totalTitle = new Label("Total:");
        totalTitle.setStyle("-fx-font-size: 16; -fx-text-fill: #7f5539; -fx-font-weight: bold;");
        Label totalLabel = new Label("$" + order.getTotal());
        totalLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #7f5539; -fx-font-weight: bold;");
        totalBox.getChildren().addAll(totalTitle, totalLabel);

        orderDetails.getChildren().addAll(
                orderTitle,
                separator1,
                customerBox,
                statusBox,
                dateBox,
                separator2,
                itemsTitle,
                itemsBox,
                separator3,
                totalBox
        );

        ScrollPane scrollPane = new ScrollPane(orderDetails);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        scrollPane.setPrefViewportWidth(x10.getPrefWidth() - 20);
        scrollPane.setPrefViewportHeight(x10.getPrefHeight() - 20);

        // ضبط حدود الـ Pane x10
        x10.setStyle("-fx-background-color: #FFF5E1; -fx-border-color: #E3AE66; -fx-background-radius: 10; -fx-border-radius: 10;");
        x10.getChildren().add(scrollPane);

    }

    private void displayCartItems() {
        x10.getChildren().clear();

        VBox cartDetails = new VBox(10);
        cartDetails.setStyle("-fx-padding: 15; -fx-background-color: transparent;");
        cartDetails.setMaxWidth(x10.getPrefWidth() - 30);

        Label cartTitle = new Label("Shopping Cart");
        cartTitle.setStyle("-fx-font-size: 18; -fx-text-fill: #7f5539; -fx-font-weight: bold;");
        cartTitle.setMaxWidth(Double.MAX_VALUE);
        cartTitle.setAlignment(Pos.CENTER);

        List<OrderItem> cartItems = CartManager.getInstance().getCartItems();
        if (cartItems.isEmpty()) {
            Label emptyLabel = new Label("Your cart is empty");
            emptyLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #7f5539;");
            emptyLabel.setAlignment(Pos.CENTER);
            cartDetails.getChildren().addAll(cartTitle, emptyLabel);
        } else {
            cartDetails.getChildren().add(cartTitle);

            Separator separator = new Separator();
            separator.setStyle("-fx-background-color: #E3AE66;");
            cartDetails.getChildren().add(separator);

            for (OrderItem item : cartItems) {
                HBox itemBox = new HBox(10);
                itemBox.setAlignment(Pos.CENTER_LEFT);

                Label nameLabel = new Label(item.getProductName());
                nameLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #7f5539; -fx-font-weight: bold;");

                Label detailsLabel = new Label(String.format("%d x $%.2f", item.getQuantity(), item.getUnitPrice()));
                detailsLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #7f5539;");

                Label subtotalLabel = new Label(String.format("$%.2f", item.getSubtotal()));
                subtotalLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #7f5539; -fx-font-weight: bold;");

                itemBox.getChildren().addAll(nameLabel, detailsLabel, subtotalLabel);
                cartDetails.getChildren().add(itemBox);
            }

            Separator totalSeparator = new Separator();
            totalSeparator.setStyle("-fx-background-color: #E3AE66;");
            cartDetails.getChildren().add(totalSeparator);

            double total = CartManager.getInstance().calculateTotal();
            HBox totalBox = new HBox(10);
            totalBox.setAlignment(Pos.CENTER_LEFT);

            Label totalTitle = new Label("Total:");
            totalTitle.setStyle("-fx-font-size: 16; -fx-text-fill: #7f5539; -fx-font-weight: bold;");

            Label totalLabel = new Label(String.format("$%.2f", total));
            totalLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #7f5539; -fx-font-weight: bold;");

            totalBox.getChildren().addAll(totalTitle, totalLabel);
            cartDetails.getChildren().add(totalBox);
        }

        ScrollPane scrollPane = new ScrollPane(cartDetails);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        scrollPane.setPrefViewportWidth(x10.getPrefWidth() - 20);
        scrollPane.setPrefViewportHeight(x10.getPrefHeight() - 20);

        x10.setStyle("-fx-background-color: #FFF5E1; -fx-border-color: #E3AE66; -fx-background-radius: 10; -fx-border-radius: 10;");
        x10.getChildren().add(scrollPane);
    }

    private void loadOrdersFromDatabase() {
         orders = orderDAO.getAllOrders(); // جلب جميع الطلبات من قاعدة البيانات
        if (orders.isEmpty()) {
            createSampleOrders(); // إذا لم توجد طلبات، إنشاء عينات (للاختبار فقط)
        }
    }

}
