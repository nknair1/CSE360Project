package adminview.admindash;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StatisticsPage extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("Statistics Page");
        Scene scene = new Scene(new StackPane(label), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Statistics");
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}