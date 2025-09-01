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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserDAO;

public class SignController implements Initializable {

    @FXML
    private TextField tfUsername;      // حقل اسم المستخدم
    @FXML
    private TextField tfPassword;      // حقل كلمة المرور
    @FXML
    private CheckBox cbRemember;
    @FXML
    private Label lblMessage;          // لعرض رسائل أو تحذيرات
    @FXML
    private Button btnLogin;           // زر تسجيل الدخول
    @FXML
    private Hyperlink hlSignUp;        // رابط الانتقال للتسجيل
    @FXML
    private Button btnBack;            // زر العودة للواجهة الرئيسية

    private UserDAO userDAO = new UserDAO(); // DAO للتعامل مع بيانات المستخدمين

    private String rememberedUsername = "";
    private String rememberedPassword = "";
    private Object accountType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserDAO.initializeTestUsers();
        // التحقق من أن جميع الحقول تم تهيئتها بشكل صحيح
        if (!checkFXMLFieldsInitialized()) {
            showAlert("Configuration Error", "Failed to load UI components. Please restart the application.", Alert.AlertType.ERROR);
            return;
        }

        // تهيئة الحقول إذا كانت هناك قيم محفوظة
        if (rememberedUsername != null && !rememberedUsername.isEmpty()) {
            tfUsername.setText(rememberedUsername);
            tfPassword.setText(rememberedPassword);
            cbRemember.setSelected(true);
        }
    }

    /**
     * التحقق من أن جميع حقول FXML تم تهيئتها بشكل صحيح
     */
    private boolean checkFXMLFieldsInitialized() { // تساعد في تحديد أي عنصر لم يتم ربطه بشكل صحيح، مما يسهل عملية تصحيح الأخطاء
        boolean allInitialized = true;

        if (tfUsername == null) {
            System.err.println("Error: tfUsername not initialized from FXML");
            allInitialized = false;
        }
        if (tfPassword == null) {
            System.err.println("Error: tfPassword not initialized from FXML");
            allInitialized = false;
        }
        if (cbRemember == null) {
            System.err.println("Error: cbRemember not initialized from FXML");
            allInitialized = false;
        }
        if (lblMessage == null) {
            System.err.println("Error: lblMessage not initialized from FXML");
            allInitialized = false;
        }
        if (btnLogin == null) {
            System.err.println("Error: btnLogin not initialized from FXML");
            allInitialized = false;
        }
        if (hlSignUp == null) {
            System.err.println("Error: hlSignUp not initialized from FXML");
            allInitialized = false;
        }
        if (btnBack == null) {
            System.err.println("Error: btnBack not initialized from FXML");
            allInitialized = false;    //نعرف بالضبط أي العناصر فشلت في التهيئة وليس فقط أن هناك فشل
        }

        return allInitialized;
    }

    @FXML
    private void rememberMeClicked(ActionEvent event) {
        if (cbRemember == null) {
            return;    //الخروج المبكر: إذا كان العنصر غير موجود، يخرج من الدالة فوراً لمنع الأخطاء
        }

        boolean remember = cbRemember.isSelected();
        if (remember) {
            // حفظ البيانات مؤقتًا في متغيرات
            rememberedUsername = tfUsername != null ? tfUsername.getText() : "";  //يحفظ اسم المستخدم وكلمة المرور في متغيرات
            rememberedPassword = tfPassword != null ? tfPassword.getText() : "";
        } else {
            // مسح البيانات
            rememberedUsername = "";
            rememberedPassword = "";
        }
    }

    // -------------------- حدث تسجيل الدخول --------------------
    @FXML
    private void loginAction(ActionEvent event) {
        // التحقق من أن الحقول ليست null
        if (tfUsername == null || tfPassword == null) {
            showAlert("Error", "UI components not loaded properly. Please restart the application.", Alert.AlertType.ERROR);
            return;
        }

        String username = tfUsername.getText();  //يحصل على النص الذي أدخله المستخدم في الحقول
        String password = tfPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password", Alert.AlertType.ERROR);
            return;
        }
        if (username.equals("admin") && password.equals("admin123")) {
            openAdminDashboard();
            return;  //بعد فتح الواجهة المناسبة، يخرج من الدالة فوراً
        }
        if (username.equals("visitor") && password.equals("visitor123")) {
            openVisitorDashboard();
            return;
        }
        if (username.equals("cashier") && password.equals("cashier123")) {
            openCashierDashboard();
            return;
        }

        // التحقق من أن الحقول غير فارغة
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password", Alert.AlertType.ERROR);
            return;
        }

        // التحقق من صحة المستخدم وكلمة المرور باستخدام DAO
        String role = userDAO.validateUser(username, password);
        if (role == null) {
            showAlert("Error", "Invalid username or password!", Alert.AlertType.ERROR);
            return;  //إذا فشل التحقق: يعرض رسالة خطأ ويخرج
        }

        // فتح الواجهة المناسبة حسب الدور
        switch (role.toLowerCase()) {
            case "admin":
                openAdminDashboard();
                break;
            case "cashier":
                openCashierDashboard();
                break;
            case "visitor":
                openVisitorDashboard();
                break;
            default:
                showAlert("Error", "Unknown user role: " + role, Alert.AlertType.ERROR);
        }
    }

    // -------------------- الانتقال لواجهة إنشاء حساب جديد --------------------
    @FXML
    private void goToSignUp(ActionEvent event) {
        try {
            FXMLLoader loader;
            Stage stage = new Stage();
            String fxmlFile = "";

            if ("admin".equals(accountType)) {
                fxmlFile = "Creat_account_admin.fxml";
            } else if ("cashier".equals(accountType)) {
                fxmlFile = "creaat_account_cashair.fxml";
            } else {
                fxmlFile = "creat _account _visitor.fxml";
            }

            // تحقق من وجود الملف أولاً
            URL url = getClass().getResource(fxmlFile);
            if (url == null) {
                throw new RuntimeException("Cannot find FXML file: " + fxmlFile);
            }

            loader = new FXMLLoader(url);
            Parent root = loader.load();

            if ("admin".equals(accountType)) {
                stage.setTitle("Admin Sign Up");
            } else if ("cashier".equals(accountType)) {
                stage.setTitle("Cashier Sign Up");
            } else {
                stage.setTitle("Visitor Sign Up");
            }

            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) hlSignUp.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the sign up page: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (RuntimeException e) {
            e.printStackTrace();
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    // -------------------- العودة للواجهة الرئيسية --------------------
    @FXML
    private void backToHome(ActionEvent event) {
        openInterface("Main_interface.fxml", "Home");
    }

    // -------------------- عرض رسالة تنبيه --------------------
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // -------------------- فتح واجهة المسؤول --------------------
    private void openAdminDashboard() {
        openInterface("Interface_admin.fxml", "Admin Dashboard");
    }

    // -------------------- فتح واجهة الصراف --------------------
    private void openCashierDashboard() {
        openInterface("Interface_cashare.fxml", "Cashier Dashboard");
    }

    // -------------------- فتح واجهة الزائر --------------------
    private void openVisitorDashboard() {
        openInterface("Interface_visiteur_books.fxml", "Visitor Dashboard");
    }

    // -------------------- دالة عامة لإعادة استخدام الكود عند فتح أي واجهة جديدة --------------------
    private void openInterface(String fxmlFile, String title) {
        try {
            // التحقق من أن الملف موجود
            URL url = getClass().getResource(fxmlFile);
            if (url == null) {
                showAlert("Error", "File not found: " + fxmlFile, Alert.AlertType.ERROR);
                return;
            }

            Parent root = FXMLLoader.load(url);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

            // إغلاق نافذة تسجيل الدخول الحالية
            if (btnLogin != null && btnLogin.getScene() != null) {
                Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                currentStage.close();
            }
        } catch (IOException e) {  // محدد - يصطاد فقط أخطاء IO  يعرف بالضبط نوع المشكلة
            e.printStackTrace();
            showAlert("Error", "Failed to load page: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {  // عام جداً - قد يخفي أخطاء أخرى
            e.printStackTrace();
            showAlert("Error", "An unexpected error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
