package books.coffee;

import books.coffee.*;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserDAO;

public class Main_interface_changePasswordController implements Initializable {

    @FXML
    private TextField p1;
    @FXML
    private TextField p2;
    @FXML
    private TextField p3;
    @FXML
    private Button p4;
    @FXML
    private Button p5;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void changepass(ActionEvent event) throws SQLException {
         String currentPassword = p1.getText();
        String newPassword = p2.getText();
        String confirmPassword = p3.getText();

        // التحقق من صحة المدخلات
        if (!validateInputs(currentPassword, newPassword, confirmPassword)) {
            return;
        }

// تشفير كلمة المرور الجديدة
        String hashedPassword = hashPassword(newPassword);

        //  هنا يتم حفظ كلمة المرور الجديدة في قاعدة البيانات
         // updatePasswordInDatabase(hashedPassword);
        showAlert("Success", "Password changed successfully!", Alert.AlertType.INFORMATION);
        returnToLogin();

    }

    @FXML
    private void backToHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main_interface.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Visitor Dashboard");
            stage.show();

            ((Stage) p5.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /*
 التحقق من صحة المدخلات
     */
    private boolean validateInputs(String currentPassword, String newPassword, String confirmPassword) {
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Please fill all fields", Alert.AlertType.ERROR);
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Error", "New passwords do not match", Alert.AlertType.ERROR);
            return false;
        }

        if (currentPassword.equals(newPassword)) {
            showAlert("Error", "New password must be different from current password", Alert.AlertType.ERROR);
            return false;
        }

        if (!isPasswordValid(newPassword)) {
            showAlert("Error", "Password must be at least 8 characters long and contain letters and numbers",
                    Alert.AlertType.ERROR);
            return false;
        }

        if (isDefaultPassword(newPassword)) {
            showAlert("Error", "This password is too common. Please choose a more secure one.",
                    Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private String hashPassword(String password) {   // تشفير كلمة المرور
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            showAlert("Error", "Password encryption failed", Alert.AlertType.ERROR);
            throw new RuntimeException("Failed to hash password", e);
        }

    }

    private void returnToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main_interface.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

            ((Stage) p5.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open login screen: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean isPasswordValid(String password) {  //التحقق من قوة كلمة المرور 
        return password.length() >= 8 && password.matches(".*[a-zA-Z]+.*") && password.matches(".*[0-9]+.*");
    }

    private boolean isDefaultPassword(String password) {
        String[] defaultPasswords = {"admin123", "cashier123", "visitor123",};
        for (String defaultPass : defaultPasswords) {
            if (password.equals(defaultPass)) {
                return true;
            }
        }
        return false;
    }

    
     
 
}
