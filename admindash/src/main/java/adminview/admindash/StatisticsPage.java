package adminview.admindash;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StatisticsPage extends Application {
    private VBox chartsContainer;

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #FAEBD7;");

        root.getChildren().add(createTopBar(stage));

        chartsContainer = new VBox(20);
        chartsContainer.setPadding(new Insets(20));
        ScrollPane scrollPane = new ScrollPane(chartsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #FAEBD7; -fx-background-color: #FAEBD7;");

        addStatisticsCards();

        addRevenueChart();
        addUserDistributionChart();
        addTransactionHistoryChart();
        addPopularBooksChart();

        root.getChildren().add(scrollPane);

        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("Statistics");
        stage.setScene(scene);
        stage.show();
    }

    private HBox createTopBar(Stage stage) {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(15);
        topBar.setStyle("-fx-background-color: #FFD700;"); // Gold color

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

        Image image = new Image("https://github.com/nknair1/CSE360Project/blob/main/ASU.png?raw=true");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(55);
        imageView.setPreserveRatio(true);

        Label titleLabel = new Label("Statistics Dashboard");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 40));

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10; -fx-background-radius: 5;");

        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        topBar.getChildren().addAll(adminLabel, spacer1, imageView, titleLabel, spacer2, logoutButton);
        return topBar;
    }

    private void addStatisticsCards() {
        HBox statsCards = new HBox(20);
        statsCards.setAlignment(Pos.CENTER);
        statsCards.setPadding(new Insets(0, 0, 20, 0));

        statsCards.getChildren().addAll(
                createStatCard("Total Revenue", "$" + calculateTotalRevenue(), "-fx-background-color: #4CAF50;"),
                createStatCard("Total Users", String.valueOf(getTotalUsers()), "-fx-background-color: #2196F3;"),
                createStatCard("Total Transactions", String.valueOf(getTotalTransactions()), "-fx-background-color: #FFC107;"),
                createStatCard("Average Transaction", "$" + String.format("%.2f", calculateAverageTransaction()), "-fx-background-color: #9C27B0;")
        );

        chartsContainer.getChildren().add(statsCards);
    }

    private VBox createStatCard(String title, String value, String style) {
        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15));
        card.setMinWidth(200);
        card.setMinHeight(100);
        card.setStyle(style + "; -fx-background-radius: 10;");

        Label titleLabel = new Label(title);
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        Label valueLabel = new Label(value);
        valueLabel.setTextFill(Color.WHITE);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    private void addRevenueChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Last 7 Days");
        yAxis.setLabel("Revenue ($)");

        LineChart<Number, Number> revenueChart = new LineChart<>(xAxis, yAxis);
        revenueChart.setTitle("Revenue Trend");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Daily Revenue");

        Map<Integer, Double> revenueData = getRevenueData();
        revenueData.forEach((day, revenue) -> {
            series.getData().add(new XYChart.Data<>(day, revenue));
        });

        revenueChart.getData().add(series);
        revenueChart.setMaxHeight(400);
        chartsContainer.getChildren().add(revenueChart);
    }

    private void addUserDistributionChart() {
        PieChart userDistChart = new PieChart();
        userDistChart.setTitle("User Distribution");

        Map<String, Integer> userDist = getUserDistribution();
        userDist.forEach((type, count) -> {
            userDistChart.getData().add(new PieChart.Data(type, count));
        });

        userDistChart.setMaxHeight(400);
        chartsContainer.getChildren().add(userDistChart);
    }

    private void addTransactionHistoryChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Number of Transactions");

        BarChart<String, Number> transactionChart = new BarChart<>(xAxis, yAxis);
        transactionChart.setTitle("Transaction History");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Transactions");

        Map<String, Integer> transactionData = getTransactionHistory();
        transactionData.forEach((time, count) -> {
            series.getData().add(new XYChart.Data<>(time, count));
        });

        transactionChart.getData().add(series);
        transactionChart.setMaxHeight(400);
        chartsContainer.getChildren().add(transactionChart);
    }

    private void addPopularBooksChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Book Title");
        yAxis.setLabel("Number of Sales");

        BarChart<String, Number> bookChart = new BarChart<>(xAxis, yAxis);
        bookChart.setTitle("Most Popular Books");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales");

        Map<String, Integer> booksData = getPopularBooks();
        booksData.forEach((book, sales) -> {
            series.getData().add(new XYChart.Data<>(book, sales));
        });

        bookChart.getData().add(series);
        bookChart.setMaxHeight(400);
        chartsContainer.getChildren().add(bookChart);
    }

    private double calculateTotalRevenue() {
        double total = 0;
        try (Connection conn = SqliteImplementation.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM book_transactions")) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    private int getTotalUsers() {
        int total = 0;
        try (Connection conn = SqliteImplementation.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users")) {
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    private int getTotalTransactions() {
        int total = 0;
        try (Connection conn = SqliteImplementation.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM book_transactions")) {
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    private double calculateAverageTransaction() {
        double avg = 0;
        try (Connection conn = SqliteImplementation.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT AVG(price) FROM book_transactions")) {
            if (rs.next()) {
                avg = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avg;
    }

    private Map<Integer, Double> getRevenueData() {
        Map<Integer, Double> revenue = new HashMap<>();
        try (Connection conn = SqliteImplementation.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT strftime('%d', transaction_date) as day, SUM(price) as daily_revenue " +
                             "FROM book_transactions " +
                             "GROUP BY day ORDER BY day DESC LIMIT 7")) {
            while (rs.next()) {
                revenue.put(rs.getInt("day"), rs.getDouble("daily_revenue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenue;
    }

    private Map<String, Integer> getUserDistribution() {
        Map<String, Integer> distribution = new HashMap<>();
        try (Connection conn = SqliteImplementation.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT user_type, COUNT(*) as count FROM users GROUP BY user_type")) {
            while (rs.next()) {
                distribution.put(rs.getString("user_type"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distribution;
    }

    private Map<String, Integer> getTransactionHistory() {
        Map<String, Integer> history = new TreeMap<>();
        try (Connection conn = SqliteImplementation.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT strftime('%H:00', transaction_date) as hour, COUNT(*) as count " +
                             "FROM book_transactions " +
                             "GROUP BY hour ORDER BY hour DESC LIMIT 24")) {
            while (rs.next()) {
                history.put(rs.getString("hour"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }

    private Map<String, Integer> getPopularBooks() {
        Map<String, Integer> books = new HashMap<>();
        try (Connection conn = SqliteImplementation.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT book_title, COUNT(*) as sales_count " +
                             "FROM book_transactions " +
                             "GROUP BY book_title " +
                             "ORDER BY sales_count DESC " +
                             "LIMIT 5")) {
            while (rs.next()) {
                books.put(rs.getString("book_title"), rs.getInt("sales_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static void main(String[] args) {
        launch(args);
    }
}