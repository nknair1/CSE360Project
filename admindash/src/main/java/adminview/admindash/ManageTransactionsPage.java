package adminview.admindash;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ManageTransactionsPage extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("Manage Transactions");
        Scene scene = new Scene(new StackPane(label), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Manage Transactions");
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}