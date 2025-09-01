package books.coffee;

import books.coffee.*;
import java.sql.SQLException;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Product;
import model.ProductDAO;

public class New_productController implements Initializable {

    // -------------------- التعامل مع قاعدة البيانات -----------------------
    private String selectedImagePath = null;      // لتخزين مسار الصورة المختارة
    private ProductDAO productDAO = new ProductDAO(); // DAO للتعامل مع جدول المنتجات
    private Product currentProduct; // لتخزين المنتج الحالي (للتعديل)

// ----------------------------------------------------------------------
    @FXML
    private TextField n1;
    @FXML
    private ComboBox<String> n2;
    @FXML
    private TextArea n3;
    @FXML
    private TextField n4;
    @FXML
    private TextField n5;
    @FXML
    private TextField n6;
    @FXML
    private Button n8;
    @FXML
    private Button n9;
    @FXML
    private Button n10;
    @FXML
    private Button n7;

    private Interface_admin_productController parentController;
    @FXML
    private ImageView imagePreview;
    @FXML
    private Label uploadLabel;
    @FXML
    private ImageView uploadIcon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // إضافة التصنيفات المتاحة لقائمة ComboBox عند فتح النافذة
        n2.getItems().addAll("Books", "Drinks", "Snacks", "Workspace"); //Workspace???
    }

    // -------------------- أحداث واجهة المستخدم ---------------------------
    // إغلاق نافذة إضافة المنتج
    @FXML
    private void handleCloseWindow(ActionEvent event) {
        Stage stage = (Stage) n10.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCategorySelected(ActionEvent event) {
    }

    @FXML
    private void handleUploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Product Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedImagePath = selectedFile.getAbsolutePath();

            // عرض الصورة في ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imagePreview.setImage(image);

            // تحديث واجهة المستخدم
            updateImageUI();
        }
    }

    // حفظ المنتج في قاعدة البيانات
    @FXML
    private void handleSaveProduct(ActionEvent event) {
        System.out.println("=== Attempting to save product ==="); // For debugging

        // Validate all required fields are filled
        if (n1.getText().isEmpty() || n2.getValue() == null
                || n5.getText().isEmpty() || n6.getText().isEmpty()
                || n4.getText().isEmpty()) {
            showAlert("Error", "Please fill in all required fields");
            System.out.println("Error: Required fields are empty");
            return;
        }

        try {
            // Read and validate data from fields
            String name = n1.getText().trim();
            String cat = n2.getValue();
            String desc = n3.getText().trim();

            // Validate numeric values
            double cost;
            double pr;
            int qty;

            try {
                cost = Double.parseDouble(n4.getText().trim());
                pr = Double.parseDouble(n5.getText().trim());
                qty = Integer.parseInt(n6.getText().trim());

                // Validate positive values
                if (cost <= 0 || pr <= 0 || qty < 0) {
                    showAlert("Error", "Price and cost must be greater than zero, quantity must be zero or more");
                    return;
                }

            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter valid numbers for price, cost and quantity");
                System.out.println("Number conversion error: " + e.getMessage());
                return;
            }

            String img = selectedImagePath; // Selected image path

            System.out.println("Product data entered:");
            System.out.println("Name: " + name);
            System.out.println("Category: " + cat);
            System.out.println("Price: " + pr);
            System.out.println("Cost: " + cost);
            System.out.println("Quantity: " + qty);
            System.out.println("Image: " + img);
            System.out.println("Description: " + desc);

            boolean success;
            Product productToSave;

            if (currentProduct == null) {
                // Add new product
                System.out.println("Attempting to add new product");
                productToSave = new Product(
                        0, // id
                        name, // name
                        cat, // category
                        pr, // price
                        qty, // quantity
                        img, // imagePath
                        desc, // description
                        true // available
                );
                success = productDAO.insertProduct(productToSave);
                System.out.println("Insert result: " + (success ? "Success" : "Failed"));
            } else {
                // Update existing product
                System.out.println("Attempting to update existing product - ID: " + currentProduct.getId());
                currentProduct.setName(name);
                currentProduct.setCategory(cat);
                currentProduct.setPrice(pr);
                currentProduct.setQuantity(qty);
                currentProduct.setImagePath(img);
                currentProduct.setDescription(desc);

                success = productDAO.updateProduct(currentProduct);
                System.out.println("Update result: " + (success ? "Success" : "Failed"));
                productToSave = currentProduct;
            }

            if (success) {
                System.out.println("Product saved successfully - ID: "
                        + (productToSave.getId() > 0 ? productToSave.getId() : "Unknown"));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText(currentProduct == null
                        ? "Product saved successfully!" : "Product updated successfully!");
                alert.showAndWait();

                // Close window after successful save
                Stage currentStage = (Stage) n8.getScene().getWindow();
                currentStage.close();

                // Refresh table in main interface if parentController exists
                if (parentController != null) {
                    System.out.println("Refreshing table in main interface");
                    parentController.refreshTable();
                } else {
                    System.out.println("Warning: parentController is null");
                }
            } else {
                System.out.println("Failed to save product to database");
                showAlert("Error", "Failed to save product. Please check database connection");
            }

        } catch (NumberFormatException e) {
            System.out.println("Number format error: " + e.getMessage());
            showAlert("Error", "Price, cost and quantity must be valid numbers");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", "Unexpected error while saving product: " + e.getMessage());
        } finally {
            System.out.println("=== Save attempt completed ===\n");
        }
    }

    // إلغاء العملية وإغلاق النافذة
    @FXML
    private void handleCancel(ActionEvent event) {
        ((Stage) n9.getScene().getWindow()).close();
    }

    // دالة عامة لعرض رسالة تنبيه
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // نوع التنبيه: خطأ
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    void setParentController(Interface_admin_productController aThis) {
        this.parentController = aThis;
    }

    Product getCurrentProduct() {
        return currentProduct;
    }

    void setProduct(Product product) {
        this.currentProduct = product;

        if (product != null) {
            // وضع بيانات المنتج في الحقول للتعديل
            n1.setText(product.getName());
            n2.setValue(product.getCategory());
            n3.setText(product.getDescription());
            n4.setText(String.valueOf(product.getPrice()));
            n5.setText(String.valueOf(product.getPrice()));
            n6.setText(String.valueOf(product.getQuantity()));
            selectedImagePath = product.getImagePath();

            // عرض الصورة إذا كان هناك مسار صورة
            if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
                File imageFile = new File(product.getImagePath());
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    imagePreview.setImage(image);
                }
            }

            // تحديث واجهة المستخدم
            updateImageUI();

            // تغيير نص زر الحفظ ليعكس عملية التعديل
            n8.setText("Update Product");
        } else {
            // مسح الحقول لإضافة منتج جديد
            n1.clear();
            n2.getSelectionModel().clearSelection();
            n3.clear();
            n4.clear();
            n5.clear();
            n6.clear();
            selectedImagePath = null;
            imagePreview.setImage(null); // مسح الصورة المعروضة

            // تحديث واجهة المستخدم
            updateImageUI();

            // إعادة نص زر الحفظ إلى الوضع الافتراضي
            n8.setText("Save Product");
        }
    }

    private void updateImageUI() {
        boolean hasImage = imagePreview.getImage() != null;

        // إخفاء/إظهار العناصر بناءً على وجود الصورة
        n7.setVisible(!hasImage);
        uploadIcon.setVisible(!hasImage);
        uploadLabel.setVisible(!hasImage);
        // يمكنك إضافة عناصر أخرى هنا لإخفائها عند وجود صورة
    }
}
