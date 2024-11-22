package adminview.admindash;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ManageAccountsPage extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("Manage Accounts");
        Scene scene = new Scene(new StackPane(label), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Manage Accounts");
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}