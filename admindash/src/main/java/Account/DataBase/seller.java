package Account.DataBase;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class seller extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            HBox titleBox = new HBox();
            root.setTop(titleBox);
            titleBox.setStyle("-fx-background-color: #ffd700;");
            Button home = new Button("🏠");
            home.setStyle("-fx-font-size: 24px; -fx-background-color: transparent;");
            Button search = new Button("🔎");
            search.setStyle("-fx-font-size: 24px; -fx-background-color: transparent;");
            Button notifications = new Button("🔔");
            notifications.setStyle("-fx-font-size: 24px; -fx-background-color: transparent;");
            Button menu = new Button("☰");
            menu.setStyle("-fx-font-size: 24px; -fx-background-color: transparent;");
            Button logOut = new Button("Log Out");
            logOut.setStyle("-fx-font-size: 13px; -fx-background-color: transparent;");
            Image image = new Image("https://github.com/nknair1/CSE360Project/blob/main/ASU.png?raw=true");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setFitHeight(50);
            titleBox.getChildren().addAll(home, search, notifications, menu, logOut, imageView);
            titleBox.setAlignment(Pos.CENTER);
            titleBox.setSpacing(20);

            logOut.setOnAction(event -> handleLogout(primaryStage));

            VBox imageDescription = new VBox();
            Label bookImage = new Label("Book Image");
            bookImage.setFont(new Font("DM Sans", 13));
            Button add = new Button("➕");
            add.setPrefWidth(100);
            add.setPrefHeight(100);
            add.setStyle("-fx-background-color: #D3D3D3; -fx-text-fill: #000000;");

            Label bookDescription = new Label("Book Description");
            bookDescription.setFont(new Font("DM Sans", 13));
            TextField bookDescriptionTextField = new TextField();
            bookDescriptionTextField.setPrefWidth(100);
            bookDescriptionTextField.setPrefHeight(200);
            imageDescription.setSpacing(20);
            bookDescriptionTextField.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 2px;");
            imageDescription.getChildren().addAll(bookImage, add, bookDescription, bookDescriptionTextField);
            root.setLeft(imageDescription);

            VBox listNew = new VBox();
            Label newBook = new Label("List New Book");
            newBook.setStyle("-fx-background-color: #ffd700; -fx-text-fill: #8C1D40;");
            Label bookTitle = new Label("Book Title");
            TextField bookTitleTextField = new TextField();
            bookTitleTextField.setStyle("-fx-background-color: #D3D3D3;");
            bookTitleTextField.setPrefWidth(300);
            bookTitleTextField.setMaxWidth(300);
            Label bookCondition = new Label("Book Condition");
            TextField conditionTextField = new TextField();
            conditionTextField.setStyle("-fx-background-color: #D3D3D3;");
            conditionTextField.setPrefWidth(300);
            conditionTextField.setMaxWidth(300);
            Label ISBN = new Label("ISBN");
            TextField ISBNTextField = new TextField();
            ISBNTextField.setStyle("-fx-background-color: #D3D3D3;");
            ISBNTextField.setPrefWidth(300);
            ISBNTextField.setMaxWidth(300);
            Label publishingYear = new Label("Publishing Year");
            TextField yearTextField = new TextField();
            yearTextField.setStyle("-fx-background-color: #D3D3D3;");
            yearTextField.setPrefWidth(300);
            yearTextField.setMaxWidth(300);
            listNew.getChildren().addAll(newBook, bookTitle, bookTitleTextField, bookCondition, conditionTextField, ISBN, ISBNTextField, publishingYear, yearTextField);
            listNew.setSpacing(20);
            listNew.setAlignment(Pos.CENTER);
            root.setCenter(listNew);

            VBox viewList = new VBox();
            Label viewListLabel = new Label("View Listed Books");
            viewListLabel.setStyle("-fx-background-color: #ffd700; -fx-text-fill: #8C1D40;");
            Label bookType = new Label("Book Type");
            TextField bookTypeTextField = new TextField();
            bookTypeTextField.setStyle("-fx-background-color: #D3D3D3;");
            bookTypeTextField.setPrefWidth(300);
            bookTypeTextField.setMaxWidth(300);
            Label genre = new Label("Genre");
            TextField genreTextField = new TextField();
            genreTextField.setStyle("-fx-background-color: #D3D3D3;");
            genreTextField.setPrefWidth(300);
            genreTextField.setMaxWidth(300);
            Label price = new Label("Price");
            TextField priceTextField = new TextField();
            priceTextField.setStyle("-fx-background-color: #D3D3D3;");
            priceTextField.setPrefWidth(300);
            priceTextField.setMaxWidth(300);
            Label author = new Label("Author");
            TextField authorTextField = new TextField();
            authorTextField.setStyle("-fx-background-color: #D3D3D3;");
            authorTextField.setPrefWidth(300);
            authorTextField.setMaxWidth(300);
            viewList.getChildren().addAll(viewListLabel, bookType, bookTypeTextField, genre, genreTextField, price, priceTextField, author, authorTextField);
            viewList.setSpacing(20);
            viewList.setAlignment(Pos.CENTER);
            root.setRight(viewList);

            HBox last = new HBox();
            Button listBookButton = new Button("List Book");
            listBookButton.setStyle("-fx-background-color: #ffd700; -fx-text-fill: #8C1D40;");
            last.getChildren().add(listBookButton);
            root.setBottom(last);
            last.setAlignment(Pos.TOP_RIGHT);

            Scene scene = new Scene(root, 800, 500);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleLogout(Stage primaryStage) {
        if (showConfirmation("Logout", "Are you sure you want to logout?")) {
            // Return to login screen
            Login_SignUp loginSignup = new Login_SignUp();
            loginSignup.start(primaryStage);
        }
    }

    private boolean showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert.showAndWait().get() == ButtonType.OK;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
