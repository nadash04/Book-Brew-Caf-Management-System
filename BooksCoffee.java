package books.coffee;

import books.coffee.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BooksCoffee extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main_interface.fxml"));
        stage.setTitle("Book & Brew Café Management System");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
