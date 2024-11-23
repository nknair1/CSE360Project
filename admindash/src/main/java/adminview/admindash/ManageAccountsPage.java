package adminview.admindash;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(15);
        topBar.setStyle("-fx-background-color: #FFD700;"); // Gold color

        Label adminLabel = new Label("Mr. Admin"); // Updated Label text
        adminLabel.setFont(Font.font("System", 14));
        adminLabel.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10; -fx-background-radius: 5;"); // Yellow bg

        // ASU Bookstore Image Logo
        Image image = new Image("https://github.com/nknair1/CSE360Project/blob/main/ASU.png?raw=true");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(55); // Adjusted height
        imageView.setPreserveRatio(true);

        Label titleLabel = new Label("Manage Accounts");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 40));

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10; -fx-background-radius: 5;"); // Yellow bg

        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        topBar.getChildren().addAll(adminLabel, spacer1, imageView, titleLabel, spacer2, logoutButton);

        // Table and Toggle for buyer and seller Setup
        tableView = new TableView<>();
        tableView.setStyle("-fx-background-color: #FFFFFF");

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // Use PropertyValueFactory
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<User, String> joinDateColumn = new TableColumn<>("Member Since"); // Updated column name
        joinDateColumn.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        nameColumn.setPrefWidth(200);
        emailColumn.setPrefWidth(250);
        joinDateColumn.setPrefWidth(150);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().addAll(nameColumn, emailColumn, joinDateColumn);

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

        VBox rightSection = createRightSection();

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(new ScrollPane(tableView)); // Wrap table in ScrollPane
        root.setRight(rightSection);
        root.setStyle("-fx-background-color: #FAEBD7; -fx-padding: 20px;");

        Scene scene = new Scene(root, 1024, 768);
        stage.setScene(scene);

        stage.setTitle("Manage Accounts");
        stage.show();
        SqliteImplementation.createTable();
        loadUsers("Buyer");
    }
    private VBox createRightSection() {

        VBox rightSection = new VBox(15);
        rightSection.setPrefWidth(150);
        rightSection.setStyle("-fx-background-color: #FAEBD7;");

        Label selectedLabel = new Label("× 5 Selected:");
        Button removeButton = new Button("Remove Accounts");
        Button editButton = new Button("Edit Account Info");
        Button accessButton = new Button("Access Finances");
        Button historyButton = new Button("Transaction History");

        // Add buttons and label to the right section
        rightSection.getChildren().addAll(selectedLabel, removeButton, editButton, accessButton, historyButton);
        rightSection.setAlignment(Pos.TOP_CENTER); // Align content to top-center

        return rightSection;
    }

    //Getting the users from database and loading them into table view.
    private void loadUsers(String userType) {
        data = FXCollections.observableArrayList();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = SqliteImplementation.connect();
            rs =  SqliteImplementation.getUsersByType(userType);
            while (rs != null && rs.next()) {
                String name = rs.getString("name") + " " + rs.getString("last_name");
                String email = rs.getString("email");
                String joinDate = rs.getString("join_date");
                data.add(new User(name, email, joinDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SqliteImplementation.closeResultSetAndConnection(rs, conn, pst);
        }
        tableView.setItems(data);
    }
}