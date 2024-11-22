package adminview.admindash;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AdminDash extends Application {

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(15);
        topBar.setStyle("-fx-background-color: #FFD700;");
        HBox adminBox = new HBox();
        adminBox.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10; -fx-background-radius: 5;");
        //This is the placeholder for the account profile icon for now. Waiting for the database implementation
        Label adminLabel = new Label("Placeholder");
        adminLabel.setFont(Font.font("System", 14));
        adminBox.getChildren().add(adminLabel);
        HBox logoBox = new HBox();
        //Added the image for the logo through github so it can be a permanent link that anyone can access.
        Image image = new Image("https://github.com/nknair1/CSE360Project/blob/main/ASU.png?raw=true");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        logoBox.getChildren().add(imageView);
        logoBox.setAlignment(Pos.CENTER);
        Label dashboardTitle = new Label("Admin Dashboard");
        dashboardTitle.setFont(Font.font("System", FontWeight.BOLD, 40));
        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10; -fx-background-radius: 5;");
        //Essentially the main top bar. Will be pretty much the same except for title in other sections
        topBar.getChildren().addAll(adminBox, spacer1, logoBox, dashboardTitle, spacer2, logoutButton);
        HBox mainContent = new HBox(20);
        mainContent.setPadding(new Insets(20));
        VBox leftSection = new VBox(10);
        leftSection.setPrefWidth(300);
        //Currently all of the below boxes are empty cause no database implementation yet.
        //Revenues, Book Sold Also Zero. And no Picture place for the best sold book cause no database
        Label currentBooksLabel = new Label("Current Listed Books");
        currentBooksLabel.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10;");
        ListView<String> booksList = new ListView<>();
        booksList.setPrefHeight(600);
        leftSection.getChildren().addAll(currentBooksLabel, booksList);
        VBox middleSection = new VBox(20);
        middleSection.setPrefWidth(400);
        VBox systemInfo = new VBox(10);
        systemInfo.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
        Label booksSoldLabel = new Label("Books Sold Today: 0");
        Label booksListedLabel = new Label("Books Listed Today: 0");
        Label usersBrowsingLabel = new Label("Users Currently Browsing: 0");
        Label totalRevenueLabel = new Label("Total Revenue for Today: $0");
        systemInfo.getChildren().addAll(booksSoldLabel, booksListedLabel, usersBrowsingLabel, totalRevenueLabel);
        VBox bestSellingSection = new VBox(10);
        Label bestSellingLabel = new Label("Best Selling Book This Month:");
        bestSellingLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        VBox bestSellingBox = new VBox(5);
        bestSellingBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
        Label bookNameLabel = new Label("");
        Label copiesSoldLabel = new Label("Total Copies Sold: 0");
        bestSellingBox.getChildren().addAll(bookNameLabel, copiesSoldLabel);
        bestSellingSection.getChildren().addAll(bestSellingLabel, bestSellingBox);
        middleSection.getChildren().addAll(systemInfo, bestSellingSection);
        VBox rightSection = new VBox(20);
        rightSection.setPrefWidth(400);
        VBox requestedListings = new VBox(10);
        Label requestedListingsLabel = new Label("Requested Listings");
        requestedListingsLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        VBox listingBox = new VBox(5);
        listingBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
        //Just setting this up for future, after Seller view is connected. This is for the Admin to Reject/Accept Book Listings
        /*HBox actionButtons = new HBox(10);
        Button acceptButton = new Button("Accept");
        Button rejectButton = new Button("Reject");
        acceptButton.setStyle("-fx-background-color: Green;");
        rejectButton.setStyle("-fx-background-color: Red;");
        actionButtons.getChildren().addAll(acceptButton, rejectButton); */
        requestedListings.getChildren().addAll(requestedListingsLabel, listingBox);
        VBox alertsSection = new VBox(10);
        Label alertsLabel = new Label("Todays Alerts");
        alertsLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        VBox alertsBox = new VBox(5);
        alertsBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
        alertsSection.getChildren().addAll(alertsLabel, alertsBox);
        rightSection.getChildren().addAll(requestedListings, alertsSection);
        mainContent.getChildren().addAll(leftSection, middleSection, rightSection);
        HBox bottomButtons = new HBox();
        bottomButtons.setPadding(new Insets(20));
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setStyle("-fx-background-color: white;");
        Region leftRegion = new Region();
        Region centerRegion = new Region();
        Region rightRegion = new Region();
        HBox.setHgrow(leftRegion, Priority.ALWAYS);
        HBox.setHgrow(centerRegion, Priority.ALWAYS);
        HBox.setHgrow(rightRegion, Priority.ALWAYS);
        //Making buttons for the 3 scenes that will be attached.
        Button manageTransactionsButton = new Button("Manage Transactions");
        Button viewStatsButton = new Button("View Statistics");
        Button manageAccountsButton = new Button("Manage Accounts");
        String buttonStyle = "-fx-background-color: #FFD700; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10 20;";
        manageTransactionsButton.setStyle(buttonStyle);
        viewStatsButton.setStyle(buttonStyle);
        manageAccountsButton.setStyle(buttonStyle);
        bottomButtons.getChildren().addAll(manageTransactionsButton, leftRegion, viewStatsButton, rightRegion, manageAccountsButton);
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(mainContent);
        root.setBottom(bottomButtons);
        root.setStyle("-fx-background-color: white;");
        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("Admin Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}