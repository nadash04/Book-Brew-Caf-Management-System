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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Product;
import model.ProductDAO;

public class Interface_admin_productController implements Initializable {

    @FXML
    private Button j19;
    @FXML
    private Button l1;
    @FXML
    private Button l2;
    @FXML
    private Button l3;
    @FXML
    private Button l4;
    @FXML
    private Button l5;
    @FXML
    private Button j20;
    @FXML
    private ImageView j1;
    @FXML
    private ImageView j2;
    @FXML
    private ImageView j3;
    @FXML
    private ImageView j16;
    @FXML
    private ImageView j17;
    @FXML
    private ImageView j18;
    @FXML
    private ImageView j13;
    @FXML
    private ImageView j14;
    @FXML
    private ImageView j15;
    @FXML
    private ImageView j4;
    @FXML
    private ImageView j5;
    @FXML
    private ImageView j6;
    @FXML
    private ImageView j7;
    @FXML
    private ImageView j8;
    @FXML
    private ImageView j9;
    @FXML
    private ImageView j10;
    @FXML
    private ImageView j11;
    @FXML
    private ImageView j12;
    @FXML
    private HBox HBox1;
    @FXML
    private HBox HBox6;
    @FXML
    private HBox HBox5;
    @FXML
    private HBox HBox2;
    @FXML
    private HBox HBox3;
    @FXML
    private HBox HBox4;

    private ProductDAO productDAO;

    private List<Product> products = new ArrayList<>();  //قائمة لتخزين جميع المنتجات في النظام
    private Product selectedProduct;  // المنتج المحدد حاليًا للعمليات (عرض، تعديل، حذف)

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productDAO = new ProductDAO();
        initializeSampleProducts();
        displayProducts();
    }

    @FXML
    private void handleCloseDashboard(ActionEvent event) {
        Stage stage = (Stage) j19.getScene().getWindow();
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
//----------------------------------------------------------------

    @FXML
    private void handleControlPanelAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_admin.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
            stage.show();

            ((Stage) l1.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProductsAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_admin_product.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Product Management");
            stage.show();

            ((Stage) l2.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUsersAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_admin_users.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) l3.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAnalyticsAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_admin_analtics.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) l4.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddProductAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("new_product.fxml"));
            Parent root = loader.load();

            New_productController controller = loader.getController();  //getController(): يعيد المرجع إلى وحدة التحكم المرتبطة بالواجهة
            controller.setParentController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            stage.setOnHidden(e -> {
                Product newProduct = controller.getCurrentProduct();
                if (newProduct != null) {
                    products.add(newProduct);
                    displayProducts();
                }
            });

        } catch (Exception e) {    //معالجة الأخطاء
            e.printStackTrace();
        }

    }

    @FXML
    private void Veiw1(MouseEvent event) {
        veiwProduct(0);

    }

    @FXML
    private void Edit1(MouseEvent event) {
        editProduct(0);
    }

    @FXML
    private void Delete1(MouseEvent event) {
        deleteProduct(0);
    }

    @FXML
    private void Veiw6(MouseEvent event) {
        veiwProduct(5);

    }

    @FXML
    private void Edit6(MouseEvent event) {
        editProduct(5);
    }

    @FXML
    private void Delete6(MouseEvent event) {
        deleteProduct(5);
    }

    @FXML
    private void Veiw5(MouseEvent event) {
        veiwProduct(4);
    }

    @FXML
    private void Edit5(MouseEvent event) {
        editProduct(4);
    }

    @FXML
    private void Delete5(MouseEvent event) {
        deleteProduct(4);
    }

    @FXML
    private void Veiw2(MouseEvent event) {
        veiwProduct(1);
    }

    @FXML
    private void Edit2(MouseEvent event) {
        editProduct(1);
    }

    @FXML
    private void Delete2(MouseEvent event) {
        deleteProduct(1);
    }

    @FXML
    private void Veiw3(MouseEvent event) {
        veiwProduct(2);
    }

    @FXML
    private void Edit3(MouseEvent event) {
        editProduct(2);
    }

    @FXML
    private void Delete3(MouseEvent event) {
        deleteProduct(2);
    }

    @FXML
    private void Veiw4(MouseEvent event) {
        veiwProduct(3);
    }

    @FXML
    private void Edit4(MouseEvent event) {
        editProduct(3);
    }

    @FXML
    private void Delete4(MouseEvent event) {
        deleteProduct(3);
    }

    private void initializeSampleProducts() {
        products.add(new Product(1, "Cappuccino", "Drinks", 4.0, 150, "images/cappuccino.jpg", "Hot cappuccino", true));
        products.add(new Product(2, "Latte", "Drinks", 3.0, 56, "images/latte.jpg", "Hot latte", true));
        products.add(new Product(3, "Croissant", "Snacks", 8.0, 58, "images/croissant.jpg", "Fresh croissant", false));
        products.add(new Product(4, "Meeting Room", "Workspace", 50.0, 123, "images/meeting.jpg", "Private meeting room", true));
        products.add(new Product(5, "Cheese Sandwich", "Snacks", 7.0, 153, "images/sandwich.jpg", "Cheese sandwich", false));
        products.add(new Product(6, "Donuts", "Snacks", 4.0, 168, "images/donuts.jpg", "Fresh donuts", true));

           // حفظ المنتجات في قاعدة البيانات
        for (Product product : products) {
            productDAO.insertProduct(product);
        }
    }

    private void displayProducts() {
        System.out.println("Displaying products...");
    }

    private void editProduct(int productIndex) {
        if (productIndex >= 0 && productIndex < products.size()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("new_product.fxml"));
                Parent root = loader.load();

                New_productController controller = loader.getController();
                controller.setProduct(products.get(productIndex));
                controller.setParentController(this);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                stage.setOnHidden(e -> {      //setOnHidden: حدث يتم تنفيذه عند إغلاق النافذة
                    Product updatedProduct = controller.getCurrentProduct(); //getCurrentProduct(): يحصل على المنتج المعدل من الواجهة
                    if (updatedProduct != null) {
                        products.set(productIndex, updatedProduct);  //products.set(): يستبدل المنتج القديم بالمعدل في القائمة
                        displayProducts();
                    }
                });

            } catch (IOException e) {
                showAlert("Error", "Failed to open edit window", AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Invalid product selection", AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void deleteProduct(int productIndex) {
        if (productIndex >= 0 && productIndex < products.size()) {   // 1. التحقق من صحة الفهرس
            Product productToDelete = products.get(productIndex);   // 2. الحصول على المنتج المراد حذفه

            Alert alert = new Alert(AlertType.CONFIRMATION);      // 3. إنشاء نافذة تأكيد للحذف
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Delete Product");
            alert.setContentText("Are you sure you want to delete " + productToDelete.getName() + "?");

            alert.showAndWait().ifPresent(response -> {          // 4. عرض النافذة وانتظار رد المستخدم

                if (response == javafx.scene.control.ButtonType.OK) {   // 5. إذا ضغط المستخدم على OK
                    // 6. حذف المنتج من القائمة (بيانات)
                    products.remove(productIndex);

                    // 7. حذف المنتج من الواجهة (عرض)
                    removeProductFromUI(productIndex);

                    showAlert("Success", "Product deleted successfully", AlertType.INFORMATION);
                }
            });
        } else {
            showAlert("Error", "Invalid product selection", AlertType.ERROR);
        }
    }

    private void veiwProduct(int productIndex) {
        if (productIndex >= 0 && productIndex < products.size()) {
            Product product = products.get(productIndex);     //الحصول على المنتج المطلوب

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Product Details");
            alert.setHeaderText(product.getName());   // اسم المنتج كعنوان
            alert.setContentText(      // 4. بناء محتوى النافذة بمعلومات المنتج
                    "Category: " + product.getCategory() + "\n"
                    + "Price: $" + product.getPrice() + "\n"
                    + "Quantity: " + product.getQuantity() + "\n"
                    + "Description: " + product.getDescription()
            );

            alert.showAndWait();
        } else {
            showAlert("Error", "Invalid product selection", AlertType.ERROR);
        }
    }

    private void removeProductFromUI(int productIndex) {
        switch (productIndex) {
            case 0:      // إزالة HBox1 من parent الخاص به
                ((Pane) HBox1.getParent()).getChildren().remove(HBox1);
                break;
            case 1:
                ((Pane) HBox2.getParent()).getChildren().remove(HBox2);
                break;
            case 2:
                ((Pane) HBox3.getParent()).getChildren().remove(HBox3);
                break;
            case 3:
                ((Pane) HBox4.getParent()).getChildren().remove(HBox4);
                break;
            case 4:
                ((Pane) HBox5.getParent()).getChildren().remove(HBox5);
                break;
            case 5:
                ((Pane) HBox6.getParent()).getChildren().remove(HBox6);
                break;
            default:
                break;   // لا تفعل شيئاً إذا كان الفهرس غير معروف
        }
    }

    void refreshTable() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
