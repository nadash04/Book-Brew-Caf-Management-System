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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main_interfaceController implements Initializable {

    @FXML
    private Button a5;
    @FXML
    private Button a4;
    @FXML
    private VBox a3;
    @FXML
    VBox a1;
    @FXML
    private VBox a2;
    @FXML
    private Button a6;
    private String currentUsername;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) a5.getScene().getWindow();
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
    private void handleAboutClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("about1.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("About Book & Brew");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVisitorClick(MouseEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sign.fxml"));
            Parent root = loader.load();

            SignController controller = loader.getController();
            controller.setAccountType("visitor");

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("visitor Login");
            stage.show();

            ((Stage) a1.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCashierClick(MouseEvent event) {
   try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sign.fxml"));
            Parent root = loader.load();

            SignController controller = loader.getController();
            controller.setAccountType("cashier");

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("cashier Login");
            stage.show();

            ((Stage) a1.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdminClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sign.fxml"));
            Parent root = loader.load();

            SignController controller = loader.getController();
            controller.setAccountType("admin");

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Login");
            stage.show();

            ((Stage) a2.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handlechangepassword(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main_interface_change password.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up");
            stage.show();

            ((Stage) a6.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

}
