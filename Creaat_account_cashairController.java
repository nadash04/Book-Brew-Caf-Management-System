package books.coffee;

import books.coffee.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.UserDAO;

public class Creaat_account_cashairController implements Initializable {

    @FXML
    private TextField f1;
    @FXML
    private TextField f2;
    @FXML
    private TextField f3;
    @FXML
    private TextField f4;
    @FXML
    private Button f7;
    @FXML
    private Hyperlink f8;
    @FXML
    private Button f9;
    @FXML
    private PasswordField f5;
    @FXML
    private PasswordField f6;

    private User currentUser;  //ضروريي في loadUserData
    private boolean isEditMode = false;

    private UserDAO userDAO = new UserDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    //هذه الطريقة مسؤولة عن إنشاء أو تعديل حساب أمين الصندوق، مع التحقق من البيانات ومعالجة الأخطاء.
    @FXML
    private void createCashierAccount(ActionEvent event) {

        if (validateFields() && validatePasswords()) {//validateFields(): يتأكد أن جميع الحقول مليئة validatePasswords(): يتأكد من تطابق كلمتي المرور
            try {
                boolean success;

                if (isEditMode) {
                    // وضع التعديل - تحديث بيانات موجودة  
                    currentUser.setName(f1.getText());  // تحديث الاسم
                    currentUser.setEmail(f3.getText());   // تحديث البريد
                    success = userDAO.updateUser(currentUser);  // حفظ في قاعدة البيانات

                } else {
                    // وضع الإنشاء - إدخال بيانات جديدة
                    success = userDAO.insertUser(
                            f2.getText(), // username
                            f5.getText(), // password
                            f1.getText(), // first name
                            "", // last name
                            f3.getText(), // email
                            "cashier" // role
                    );
                }

                /*   إذا نجحت العملية (success = true):
عرض رسالة نجاح مناسبة للعملية                     
إغلاق النافذة الحالية                         
الانتقال إلى صفحة تسجيل الدخول                   
إذا فشلت العملية                    (success = false):            
                    عرض رسالة خطأ مناسبة للعملية
                 */
                if (success) { 
                    String message = isEditMode
                            ? "Cashier account updated successfully!"
                            : "Cashier account created successfully!";
                    showAlert("Success", message);
                    Stage currentStage = (Stage) f7.getScene().getWindow();
                    currentStage.close();

                    goToLoginPage();
                } else {
                    String message = isEditMode
                            ? "Failed to update cashier account!"
                            : "Failed to create cashier account!";
                    showAlert("Error", message);
                }
            } catch (Exception e) {
                showAlert("Error", "Operation failed: " + e.getMessage());
            }
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("sign.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

            // إغلاق النافذة الحالية
            ((Stage) f8.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToLoginPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("sign.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

            // إغلاق النافذة الحالية
            ((Stage) f8.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBackToRoleSelection(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main_interface.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Book & Brew - Main");
            stage.show();

            ((Stage) f9.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateFields() {
        if (f1.getText().isEmpty() || f2.getText().isEmpty() || f3.getText().isEmpty()
                || f4.getText().isEmpty() || f5.getText().isEmpty() || f6.getText().isEmpty()) {
            showAlert("Error", "All fields are required!");
            return false;
        }
        return true;
    }

    private boolean validatePasswords() {
        if (!f5.getText().equals(f6.getText())) {
            showAlert("Error", "Passwords don't match!");
            return false;
        }
        return true;
    }

    void loadUserData(User user) {
        this.currentUser = user;           //  حفظ المستخدم الحالي في متغير للوصول إليه لاحقاً
        this.isEditMode = true;           //  تفعيل وضع التعديل (بدلاً من وضع الإنشاء)
        f1.setText(user.getName());      // تعبئة حقل الاسم ببيانات المستخدم
        f2.setText(user.getName().replace(" ", "").toLowerCase()); //  إنشاء اسم مستخدم تلقائي من الاسم بإزالة المسافات وتحويله لحروف صغيرة  
        f3.setText(user.getEmail()); 

        // تغيير نص الزر
        f7.setText("Update Account"); //تغيير نص الزر من "Create" إلى "Update"
    }

    User getUpdatedUser() {    //الوظيفة: إنشاء كائن مستخدم محدث بالبيانات الجديدة من الحقول.
        if (currentUser == null) {  // التحقق من وجود مستخدم حالي
            return null;
        }
// إنشاء كائن User مع البيانات المحدثة من الحقول
        User updatedUser = new User(    // إنشاء كائن جديد ببيانات محدثة مع الحفاظ على:
                currentUser.getId(), // احتفظ بالـ ID الأصلي
                f1.getText(),   //الاسم الجديد من الحقل
                f3.getText(),
                "cashier", // العضوية
                currentUser.getAmountSpent() // احتفظ بنفس المبلغ المنفق
        );

        return updatedUser;
    }

}
