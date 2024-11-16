package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();

            HBox topBar = new HBox();
            topBar.setBackground(new Background(new BackgroundFill(Color.web("#FFD700"), CornerRadii.EMPTY, Insets.EMPTY)));
            topBar.setPadding(new Insets(20));
            topBar.setSpacing(20);

            ImageView profileImage = new ImageView();
            profileImage.setFitWidth(120);
            profileImage.setFitHeight(120);
            profileImage.setImage(new Image("file:/C:/Users/lalo/Desktop/ASU-BOOKSTORE.png", true));

            Label profileIcon = new Label("Generic Seller\nAccount");
            profileIcon.setTextFill(Color.BLACK);
            profileIcon.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

            HBox profileBox = new HBox(10, profileImage, profileIcon);
            profileBox.setPadding(new Insets(0, 0, 0, 10));
            profileBox.setAlignment(Pos.CENTER_LEFT);

            Button homeButton = new Button("Home");
            TextField searchBar = new TextField();
            searchBar.setPromptText("Search...");
            searchBar.setPrefWidth(300);
            Button searchButton = new Button("Search");
            Button notificationsButton = new Button("🔔");
            notificationsButton.setStyle("-fx-font-size: 24px; -fx-background-color: transparent;");

            ImageView logoImage = new ImageView();
            logoImage.setFitWidth(160);
            logoImage.setFitHeight(80);
            logoImage.setImage(new Image("file:/C:/Users/lalo/Desktop/ASU-BOOKSTORE.png", true));

            topBar.getChildren().addAll(profileBox, homeButton, searchBar, searchButton, notificationsButton, logoImage);
            topBar.setAlignment(Pos.CENTER);

            root.setTop(topBar);

            GridPane centerLayout = new GridPane();
            centerLayout.setPadding(new Insets(20));
            centerLayout.setHgap(20);
            centerLayout.setVgap(20);
            centerLayout.setAlignment(Pos.TOP_CENTER);

            Label bookImageLabel = new Label("Book Image");
            TextField bookImageField = new TextField();
            bookImageField.setPromptText("Drag and drop an image");
            bookImageField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10; -fx-border-radius: 10; -fx-padding: 5;");

            Label bookTitleLabel = new Label("Book Title");
            TextField bookTitleField = new TextField();
            bookTitleField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10; -fx-border-radius: 10; -fx-padding: 5;");

            Label bookConditionLabel = new Label("Book Condition");
            TextField bookConditionField = new TextField();
            bookConditionField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10; -fx-border-radius: 10; -fx-padding: 5;");

            Label bookTypeLabel = new Label("Book Type");
            TextField bookTypeField = new TextField();
            bookTypeField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10; -fx-border-radius: 10; -fx-padding: 5;");

            Label genreLabel = new Label("Genre");
            TextField genreField = new TextField();
            genreField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10; -fx-border-radius: 10; -fx-padding: 5;");

            Label priceLabel = new Label("Price");
            TextField priceField = new TextField();
            priceField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10; -fx-border-radius: 10; -fx-padding: 5;");

            Label isbnLabel = new Label("ISBN");
            TextField isbnField = new TextField();
            isbnField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10; -fx-border-radius: 10; -fx-padding: 5;");

            Label yearLabel = new Label("Publishing Year");
            TextField yearField = new TextField();
            yearField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10; -fx-border-radius: 10; -fx-padding: 5;");

            Label authorLabel = new Label("Author");
            TextField authorField = new TextField();
            authorField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10; -fx-border-radius: 10; -fx-padding: 5;");

            // Add nodes to grid pane
            centerLayout.add(bookImageLabel, 0, 0);
            centerLayout.add(bookImageField, 1, 0);

            centerLayout.add(bookTitleLabel, 2, 0);
            centerLayout.add(bookTitleField, 3, 0);

            centerLayout.add(bookConditionLabel, 0, 1);
            centerLayout.add(bookConditionField, 1, 1);

            centerLayout.add(bookTypeLabel, 2, 1);
            centerLayout.add(bookTypeField, 3, 1);

            centerLayout.add(genreLabel, 0, 2);
            centerLayout.add(genreField, 1, 2);

            centerLayout.add(priceLabel, 2, 2);
            centerLayout.add(priceField, 3, 2);

            centerLayout.add(isbnLabel, 0, 3);
            centerLayout.add(isbnField, 1, 3);

            centerLayout.add(yearLabel, 2, 3);
            centerLayout.add(yearField, 3, 3);

            centerLayout.add(authorLabel, 0, 4);
            centerLayout.add(authorField, 1, 4);

            root.setCenter(centerLayout);

            Button listBookButton = new Button("List Book");
            listBookButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10;");
            HBox buttonBox = new HBox(listBookButton);
            buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
            buttonBox.setPadding(new Insets(20));

            root.setBottom(buttonBox);

            Scene scene = new Scene(root, 1920, 1080);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Book Listing Application");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
