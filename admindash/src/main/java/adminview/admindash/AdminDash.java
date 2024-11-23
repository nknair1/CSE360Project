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
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.util.Map;

public class AdminDash extends Application {
    private Stage primaryStage;
    private ListView<String> booksList;
    private Label booksSoldLabel;
    private Label booksListedLabel;
    private Label totalRevenueLabel;
    private Label bookNameLabel;
    private Label copiesSoldLabel;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        this.primaryStage = stage;
        SqliteImplementation.createBookTable();

        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(15);
        topBar.setStyle("-fx-background-color: #FFD700;");

        HBox adminBox = new HBox();
        adminBox.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10; -fx-background-radius: 5;");

        Label adminLabel = new Label("Mr.Admin");
        adminLabel.setFont(Font.font("System", 14));
        adminLabel.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10; -fx-background-radius: 5;");
        adminBox.getChildren().add(adminLabel);

        HBox logoBox = new HBox();
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

        Button refreshBtn = new Button("↻ Refresh");
        refreshBtn.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10; -fx-background-radius: 5;");
        refreshBtn.setOnAction(e -> refreshDashboardData());

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10; -fx-background-radius: 5;");

        topBar.getChildren().addAll(adminBox, spacer1, logoBox, dashboardTitle, spacer2, refreshBtn, logoutButton);

        HBox mainContent = new HBox(20);
        mainContent.setPadding(new Insets(20));

        VBox leftSection = new VBox(10);
        leftSection.setPrefWidth(300);

        Label currentBooksLabel = new Label("Current Listed Books");
        currentBooksLabel.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10;");
        booksList = new ListView<>();
        booksList.setPrefHeight(600);
        leftSection.getChildren().addAll(currentBooksLabel, booksList);

        VBox middleSection = new VBox(20);
        middleSection.setPrefWidth(400);

        VBox systemInfo = new VBox(10);
        systemInfo.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
        booksSoldLabel = new Label("Books Sold Today: 0");
        booksListedLabel = new Label("Books Listed Today: 0");
        totalRevenueLabel = new Label("Total Revenue for Today: $0");
        systemInfo.getChildren().addAll(booksSoldLabel, booksListedLabel, totalRevenueLabel);

        VBox bestSellingSection = new VBox(10);
        Label bestSellingLabel = new Label("Best Selling Book This Month:");
        bestSellingLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        VBox bestSellingBox = new VBox(5);
        bestSellingBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
        bookNameLabel = new Label("");
        copiesSoldLabel = new Label("Total Copies Sold: 0");
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

        Button manageTransactionsButton = new Button("Manage Transactions");
        Button viewStatsButton = new Button("View Statistics");
        Button manageAccountsButton = new Button("Manage Accounts");
        String buttonStyle = "-fx-background-color: #FFD700; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10 20;";
        manageTransactionsButton.setStyle(buttonStyle);
        viewStatsButton.setStyle(buttonStyle);
        manageAccountsButton.setStyle(buttonStyle);

        bottomButtons.getChildren().addAll(manageTransactionsButton, leftRegion, viewStatsButton, rightRegion, manageAccountsButton);
        manageAccountsButton.setOnAction(e -> openManageAccountsPage());
        viewStatsButton.setOnAction(e -> openStatisticsPage());
        manageTransactionsButton.setOnAction(e -> openManageTransactionsPage());

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(mainContent);
        root.setBottom(bottomButtons);
        root.setStyle("-fx-background-color: white;");

        // Set up auto-refresh
        setupAutoRefresh();

        // Initial data load
        refreshDashboardData();

        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("Admin Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    private void setupAutoRefresh() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(30), e -> refreshDashboardData()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void refreshDashboardData() {
        // Update books list
        booksList.getItems().clear();
        booksList.getItems().addAll(SqliteImplementation.getCurrentlyListedBooks());

        // Update system info
        Map<String, Object> stats = SqliteImplementation.getTodayStatistics();
        booksSoldLabel.setText("Books Sold Today: " + stats.getOrDefault("booksSold", 0));
        booksListedLabel.setText("Books Listed Today: " + stats.getOrDefault("booksListed", 0));
        totalRevenueLabel.setText(String.format("Total Revenue for Today: $%.2f",
                (Double) stats.getOrDefault("revenue", 0.0)));

        // Update best selling book
        Map<String, Object> bestSeller = SqliteImplementation.getBestSellingBook();
        if (bestSeller.containsKey("title")) {
            bookNameLabel.setText(bestSeller.get("title") + " by " + bestSeller.get("author"));
            copiesSoldLabel.setText("Total Copies Sold: " + bestSeller.get("copiesSold"));
        } else {
            bookNameLabel.setText("No sales this month");
            copiesSoldLabel.setText("Total Copies Sold: 0");
        }
    }
    //Exception catchers just in case. For debugging purposes. And in general. But these will make a new scene pointing to
    //...existing files.
    private void openManageAccountsPage() {
        try {
            boolean wasFullScreen = primaryStage.isFullScreen();
            ManageAccountsPage accountsPage = new ManageAccountsPage();
            accountsPage.start(primaryStage);
            primaryStage.setFullScreen(wasFullScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openStatisticsPage() {
        try {
            boolean wasFullScreen = primaryStage.isFullScreen();
            StatisticsPage statsPage = new StatisticsPage();
            statsPage.start(primaryStage);
            primaryStage.setFullScreen(wasFullScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openManageTransactionsPage() {
        try {
            boolean wasFullScreen = primaryStage.isFullScreen();
            ManageTransactionsPage transactionsPage = new ManageTransactionsPage();
            transactionsPage.start(primaryStage);
            primaryStage.setFullScreen(wasFullScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}