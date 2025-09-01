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
import model.PaymentDAO;

public class Interface_cashare_PaymentsController implements Initializable {

    @FXML
    private Button x1;
    @FXML
    private Button x2;
    @FXML
    private Button x3;
    @FXML
    private Button x4;
    @FXML
    private Pane x9;
    @FXML
    private Pane pane4;
    @FXML
    private Pane pane5;
    @FXML
    private Pane pane6;
    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;

    private PaymentDAO paymentDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paymentDAO = new PaymentDAO();
          
    }

    @FXML
    private void handleBackAction(ActionEvent event) {
        ((Stage) x1.getScene().getWindow()).close();
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
    }

    @FXML
    private void onMouseEnteredCard(MouseEvent event) {
        pane1.setScaleX(1.05);
        pane1.setScaleY(1.05);
    }

    @FXML
    private void onMouseExitedCard(MouseEvent event) {
        pane1.setScaleX(1.0);
        pane1.setScaleY(1.0);
    }

    @FXML
    private void onMouseExitedCard4(MouseEvent event) {
        pane4.setScaleX(1.0);
        pane4.setScaleY(1.0);
    }

    @FXML
    private void onMouseEnteredCard4(MouseEvent event) {
        pane4.setScaleX(1.05);
        pane4.setScaleY(1.05);
    }

    @FXML
    private void onMouseExitedCard5(MouseEvent event) {
        pane5.setScaleX(1.0);
        pane5.setScaleY(1.0);
    }

    @FXML
    private void onMouseEnteredCard5(MouseEvent event) {
        pane5.setScaleX(1.05);
        pane5.setScaleY(1.05);
    }

    @FXML
    private void onMouseExitedCard6(MouseEvent event) {
        pane6.setScaleX(1.0);
        pane6.setScaleY(1.0);
    }

    @FXML
    private void onMouseEnteredCard6(MouseEvent event) {
        pane6.setScaleX(1.05);
        pane6.setScaleY(1.05);
    }

    @FXML
    private void onMouseExitedCard2(MouseEvent event) {
        pane2.setScaleX(1.0);
        pane2.setScaleY(1.0);
    }

    @FXML
    private void onMouseEnteredCard2(MouseEvent event) {
        pane2.setScaleX(1.05);
        pane2.setScaleY(1.05);
    }

    @FXML
    private void onMouseExitedCard3(MouseEvent event) {
        pane3.setScaleX(1.0);
        pane3.setScaleY(1.0);
    }

    @FXML
    private void onMouseEnteredCard3(MouseEvent event) {
        pane3.setScaleX(1.05);
        pane3.setScaleY(1.05);
    }

}
