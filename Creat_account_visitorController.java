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

public class Creat_account_visitorController implements Initializable {

    @FXML
    private TextField d1;
    @FXML
    private TextField d2;
    @FXML
    private TextField d3;
    @FXML
    private Button d6;
    @FXML
    private Hyperlink d7;
    @FXML
    private Button c7;
    @FXML
    private PasswordField d4;
    @FXML
    private PasswordField d5;

    private UserDAO userDAO = new UserDAO();

    private User currentUser; // متغير لحفظ المستخدم الحالي أثناء التعديل
    private boolean isEditMode = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleSignUpAction(ActionEvent event) {
        if (validateFields() && validatePasswords()) {
            try {
                boolean success;

                if (isEditMode) {
                    // وضع التعديل
                    currentUser.setName(d1.getText());
                    currentUser.setEmail(d3.getText());
                    currentUser.setMembership("visitor"); // تأكيد العضوية
                    success = userDAO.updateUser(currentUser);
                } else {
                    // وضع الإنشاء
                    success = userDAO.insertUser(
                            d2.getText(), // username
                            d4.getText(), // password
                            d1.getText(), // first name
                            "", // last name
                            d3.getText(), // email
                            "visitor" // role
                    );
                }

                if (success) {
                    String message = isEditMode
                            ? "Visitor account updated successfully!"
                            : "Visitor account created successfully!";
                    showAlert("Success", message);

                    Stage currentStage = (Stage) d6.getScene().getWindow();
                    currentStage.close();

                    handleGoToLogin();
                } else {
                    String message = isEditMode
                            ? "Failed to update visitor account!"
                            : "Failed to create visitor account!";
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

            ((Stage) d7.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("sign.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

            ((Stage) d7.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main_interface.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Book & Brew - Main");
            stage.show();

            ((Stage) c7.getScene().getWindow()).close();
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
        if (d1.getText().isEmpty() || d2.getText().isEmpty()
                || d3.getText().isEmpty() || d4.getText().isEmpty()
                || d5.getText().isEmpty()) {
            showAlert("Error", "All fields are required!");
            return false;
        }
        return true;

    }

    private boolean validatePasswords() {
        if (!d4.getText().equals(d5.getText())) {
            showAlert("Error", "Passwords do not match!");
            return false;
        }
        return true;
    }

    void loadUserData(User user) {
        this.currentUser = user;
        this.isEditMode = true;

        d1.setText(user.getName());
        d2.setText(user.getName().replace(" ", "").toLowerCase());
        d3.setText(user.getEmail());

        // تغيير نص الزر
        d6.setText("Update Account");
    }

    private void refreshUserTable() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    User getUpdatedUser() {
        if (currentUser == null) {
            return null;
        }

        // إنشاء كائن User مع البيانات المحدثة من الحقول
        User updatedUser = new User(
                currentUser.getId(), // احتفظ بالـ ID الأصلي
                d1.getText(), // الاسم
                d3.getText(), // البريد الإلكتروني
                "visitor", // العضوية
                currentUser.getAmountSpent() // احتفظ بنفس المبلغ المنفق
        );

        return updatedUser;
    }

}
