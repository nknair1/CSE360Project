package adminview.admindash;
import adminview.admindash.SqliteImplementation;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageAccountsPage extends Application {
    private TableView<User> tableView;
    private ObservableList<User> data;
    private ToggleGroup toggleGroup;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        //Creating the table view for the manage accounts page. Should have filters, and toggle for Buyer and Seller
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        TableColumn<User, String> joinDateColumn = new TableColumn<>("Join Date");
        joinDateColumn.setCellValueFactory(cellData -> cellData.getValue().joinDateProperty());
        tableView.getColumns().addAll(nameColumn, emailColumn, joinDateColumn);
        //The toggles
        toggleGroup = new ToggleGroup();
        RadioButton buyersButton = new RadioButton("Buyers");
        buyersButton.setToggleGroup(toggleGroup);
        buyersButton.setSelected(true);
        buyersButton.setOnAction(e -> loadUsers("Buyer"));
        RadioButton sellersButton = new RadioButton("Sellers");
        sellersButton.setToggleGroup(toggleGroup);
        sellersButton.setOnAction(e -> loadUsers("Seller"));
        HBox toggleBox = new HBox(10, buyersButton, sellersButton);
        toggleBox.setPadding(new Insets(10));
        root.setTop(toggleBox);
        root.setCenter(new ScrollPane(tableView));
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Manage Accounts");
        stage.show();
        loadUsers("Buyer");
    }
    //Getting the users from the database and loading them into the table view.
    private void loadUsers(String userType) {
        data = FXCollections.observableArrayList();
        try {
            ResultSet rs = SqliteImplementation.getUsersByType(userType);
            while (rs != null && rs.next()) {
                String name = rs.getString("firstName") + " " + rs.getString("lastName");
                String email = rs.getString("email");
                String joinDate = rs.getString("joinDate");
                data.add(new User(name, email, joinDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableView.setItems(data);
    }
}