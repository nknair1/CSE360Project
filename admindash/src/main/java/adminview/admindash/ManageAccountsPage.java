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
import javafx.util.Pair;
import javafx.scene.Node;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageAccountsPage extends Application {
    private TableView<User> tableView;
    private ObservableList<User> data;
    private ToggleGroup toggleGroup;
    private Label selectedLabel;
    private RadioButton currentSelectedButton;


    @Override
    public void start(Stage stage) {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(15);
        topBar.setStyle("-fx-background-color: #FFD700;");

        Label adminLabel = new Label("Mr. Admin");
        adminLabel.setFont(Font.font("System", 14));
        adminLabel.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10; -fx-background-radius: 5;");
        adminLabel.setOnMouseClicked(e -> {
            try {
                boolean wasFullScreen = stage.isFullScreen();
                new AdminDash().start(stage);
                stage.setFullScreen(wasFullScreen);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // ASU Bookstore Image Logo
        Image image = new Image("https://github.com/nknair1/CSE360Project/blob/main/ASU.png?raw=true");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(55);
        imageView.setPreserveRatio(true);

        Label titleLabel = new Label("Manage Accounts");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 40));

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10; -fx-background-radius: 5;");

        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        topBar.getChildren().addAll(adminLabel, spacer1, imageView, titleLabel, spacer2, logoutButton);

        // Table and Toggle for buyer and seller Setup
        tableView = new TableView<>();
        tableView.setStyle("-fx-background-color: #FFFFFF");
        TableColumn<User, CheckBox> selectColumn = new TableColumn<>("");
        selectColumn.setCellValueFactory(new PropertyValueFactory<>("select"));
        selectColumn.setPrefWidth(30);

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
        tableView.getColumns().addAll(selectColumn, nameColumn, emailColumn, joinDateColumn);

        toggleGroup = new ToggleGroup();
        RadioButton buyersButton = new RadioButton("Buyers");
        buyersButton.setToggleGroup(toggleGroup);
        buyersButton.setSelected(true);
        buyersButton.setOnAction(e -> loadUsers("Buyer"));

        RadioButton sellersButton = new RadioButton("Sellers");
        sellersButton.setToggleGroup(toggleGroup);
        sellersButton.setOnAction(e -> loadUsers("Seller"));

        Button refreshButton = new Button("↻ Refresh");
        refreshButton.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10; -fx-background-radius: 5;");
        refreshButton.setOnAction(e -> refreshUsers());

        HBox toggleBox = new HBox(10, buyersButton, sellersButton, refreshButton);
        toggleBox.setPadding(new Insets(10));

        VBox rightSection = createRightSection();

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setRight(rightSection);
        root.setCenter(new VBox(10, toggleBox, new ScrollPane(tableView)));
        root.setStyle("-fx-background-color: #FAEBD7; -fx-padding: 20px;");

        Scene scene = new Scene(root, 1200, 800);
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

        selectedLabel = new Label("× 0 Selected:");
        selectedLabel.setStyle("-fx-font-weight: bold;");

        Button removeButton = new Button("Remove Accounts");
        removeButton.setOnAction(e -> removeSelectedAccounts());

        Button editButton = new Button("Edit Account Info");
        editButton.setOnAction(e -> editSelectedAccount());

        rightSection.getChildren().addAll(selectedLabel, removeButton, editButton);
        rightSection.setAlignment(Pos.TOP_CENTER);

        return rightSection;
    }

    //Getting the users from database and loading them into table view.
    private void loadUsers(String userType) {
        data = FXCollections.observableArrayList();
        try (Connection conn = SqliteImplementation.connect();
             ResultSet rs = SqliteImplementation.getUsersByType(userType)) {

            while (rs != null && rs.next()) {
                String name = rs.getString("name") + " " + rs.getString("last_name");
                String email = rs.getString("email");
                String joinDate = rs.getString("join_date");
                User user = new User(name, email, joinDate);
                user.getSelect().setOnAction(e -> updateSelectedCount());
                data.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load users: " + e.getMessage());
        }
        tableView.setItems(data);
        updateSelectedCount();
    }
    private void updateSelectedCount() {
        long count = data.stream().filter(user -> user.getSelect().isSelected()).count();
        selectedLabel.setText("× " + count + " Selected:");
    }

    private void refreshUsers() {
        String userType = ((RadioButton)toggleGroup.getSelectedToggle()).getText();
        loadUsers(userType.endsWith("s") ? userType.substring(0, userType.length()-1) : userType);
        showNotification("Refresh complete", "Account list has been updated");
    }

    private void removeSelectedAccounts() {
        List<User> selectedUsers = data.stream()
                .filter(user -> user.getSelect().isSelected())
                .collect(Collectors.toList());

        if (selectedUsers.isEmpty()) {
            showAlert("Warning", "Please select accounts to remove");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to remove " + selectedUsers.size() + " selected accounts?",
                ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                for (User user : selectedUsers) {
                    try {
                        SqliteImplementation.deleteUser(user.getEmail());
                    } catch (SQLException e) {
                        showAlert("Error", "Failed to remove user: " + user.getEmail());
                        e.printStackTrace();
                    }
                }
                refreshUsers();
            }
        });
    }

    private void editSelectedAccount() {
        List<User> selectedUsers = data.stream()
                .filter(user -> user.getSelect().isSelected())
                .collect(Collectors.toList());

        if (selectedUsers.size() != 1) {
            showAlert("Warning", "Please select exactly one account to edit");
            return;
        }

        User selectedUser = selectedUsers.get(0);
        showEditDialog(selectedUser);
    }

    private void showEditDialog(User user) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Edit Account");
        dialog.setHeaderText("Edit account information for: " + user.getEmail());

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField(user.getName());
        TextField emailField = new TextField(user.getEmail());

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Pair<>(nameField.getText(), emailField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(nameEmail -> {
            try {
                SqliteImplementation.updateUser(user.getEmail(), nameEmail.getKey(), nameEmail.getValue());
                refreshUsers();
                showAlert("Success", "User information updated successfully!");
            } catch (SQLException e) {
                showAlert("Error", "Failed to update user: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showNotification(String title, String message) {
        Label notification = new Label(message);
        notification.setStyle("-fx-background-color: #90EE90; -fx-padding: 10; -fx-background-radius: 5;");

        Popup popup = new Popup();
        popup.getContent().add(notification);

        Stage stage = (Stage) tableView.getScene().getWindow();
        popup.show(stage);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> popup.hide()
        ));
        timeline.play();
    }
}