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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Interface_admin_analticsController implements Initializable {

    @FXML
    private Button m6;
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
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
 
    @FXML
    private void handleCloseDashboard(ActionEvent event) {
        Stage stage = (Stage) m6.getScene().getWindow();
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
            Parent root = FXMLLoader.load(getClass().getResource("new_product.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    @FXML
    private void OnMouseExited(MouseEvent event) {
        pane1.setScaleX(1.0);
        pane1.setScaleY(1.0);
    }

    @FXML
    private void OnMouseEntered(MouseEvent event) {
        pane1.setScaleX(1.05);
        pane1.setScaleY(1.05);
    }

    @FXML
    private void OnMouseExited2(MouseEvent event) {
        pane2.setScaleX(1.0);
        pane2.setScaleY(1.0);

    }

    @FXML
    private void OnMouseEntered2(MouseEvent event) {
        pane2.setScaleX(1.05);
        pane2.setScaleY(1.05);
    }

    @FXML
    private void OnMouseExited3(MouseEvent event) {
        pane3.setScaleX(1.0);
        pane3.setScaleY(1.0);

    }

    @FXML
    private void OnMouseEntered3(MouseEvent event) {
        pane3.setScaleX(1.05);
        pane3.setScaleY(1.05);
    }

}
