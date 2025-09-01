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
import model.AdminDAO;
import model.User;
import model.UserDAO;

public class Creat_account_adminController implements Initializable {

    @FXML
    private TextField e2;
    @FXML
    private TextField e3;
    @FXML
    private TextField e4;
    @FXML
    private TextField e5;
    @FXML
    private TextField e8;
    @FXML
    private TextField e9;
    @FXML
    private Button e10;
    @FXML
    private Hyperlink e11;
    @FXML
    private Button e1;
    @FXML
    private PasswordField e6;
    @FXML
    private PasswordField e7;

    private User currentUser;
    private boolean isEditMode = false;

    private UserDAO userDAO = new UserDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleAdminSignUp(ActionEvent event) {
        if (validateFields() && validatePasswords()) {
            try {
                boolean success;

                if (isEditMode) {
                    // وضع التعديل
                    currentUser.setName(e2.getText() + " " + e3.getText());
                    currentUser.setEmail(e4.getText());
                    success = userDAO.updateUser(currentUser);
                } else {
                    // وضع الإنشاء
                    success = userDAO.insertUser(
                            e5.getText(), // username
                            e6.getText(), // password
                            e2.getText(), // first name
                            e3.getText(), // last name
                            e4.getText(), // email
                            "admin" // role
                    );
                }

                if (success) {
                    String message = isEditMode
                            ? "Admin account updated successfully!"
                            : "Admin account created successfully!";
                    showAlert("Success", message);
                    goToLoginPage();
                } else {
                    String message = isEditMode
                            ? "Failed to update admin account!"
                            : "Failed to create admin account!";
                    showAlert("Error", message);

                    Stage currentStage = (Stage) e10.getScene().getWindow();
                    currentStage.close();
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

            ((Stage) e11.getScene().getWindow()).close();
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

            ((Stage) e11.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main_interface.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Book & Brew - Main");
            stage.show();

            ((Stage) e1.getScene().getWindow()).close();
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
        if (e2.getText().isEmpty() || e3.getText().isEmpty() || e4.getText().isEmpty()
                || e5.getText().isEmpty() || e6.getText().isEmpty() || e7.getText().isEmpty()
                || e8.getText().isEmpty() || e9.getText().isEmpty()) {
            showAlert("Error", "All fields are required!");
            return false;
        }
        return true;
    }

    private boolean validatePasswords() {
        if (!e6.getText().equals(e7.getText())) {
            showAlert("Error", "Passwords do not match!");
            return false;
        }
        return true;
    }

    void loadUserData(User user) {
        this.currentUser = user;
        this.isEditMode = true;

        // تقسيم الاسم الكامل إلى first و last name
        String[] names = user.getName().split(" ", 2);
        e2.setText(names[0]);
        e3.setText(names.length > 1 ? names[1] : "");
        e4.setText(user.getEmail());
        e5.setText(user.getName().replace(" ", "").toLowerCase());

        // تغيير نص الزر
        e10.setText("Update Account");
    }

    User getUpdatedUser() {
        if (currentUser == null) {
            return null;
        }
        User updatedUser = new User(
                currentUser.getId(), // احتفظ بالـ ID الأصلي
                e2.getText() + " " + e3.getText(), // الاسم الكامل
                e4.getText(), // البريد الإلكتروني
                "admin", // العضوية
                currentUser.getAmountSpent() // احتفظ بنفس المبلغ المنفق
        );

        return updatedUser;
    }
}
