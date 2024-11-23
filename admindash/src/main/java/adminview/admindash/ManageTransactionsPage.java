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
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageTransactionsPage extends Application {
    private TableView<Transaction> transactionTable;
    private Label selectedCountLabel;
    private HBox selectionToolbar;
    private ObservableList<Transaction> transactions;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SqliteImplementation.createBookTransactionTable();
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        HBox header = createHeader();
        selectionToolbar = createSelectionToolbar();
        selectionToolbar.setVisible(false);
        transactionTable = createTransactionTable();
        loadTransactions();
        root.getChildren().addAll(header, selectionToolbar, transactionTable);
        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setTitle("Manage Transactions");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #FFD700;"); // Gold background

        Label adminLabel = new Label("Mr. Admin");
        adminLabel.setStyle("-fx-font-weight: bold; -fx-padding: 5 10 5 10;");
        adminLabel.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10; -fx-background-radius: 5;");
        ImageView logo = new ImageView(new Image("https://github.com/nknair1/CSE360Project/blob/main/ASU.png?raw=true"));
        logo.setFitHeight(40);
        logo.setFitWidth(40);
        logo.setPreserveRatio(true);

        Label title = new Label("Manage Transactions");
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: #FFFF00; -fx-padding: 5 10; -fx-background-radius: 5;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(adminLabel, logo, title, spacer, logoutBtn);
        return header;
    }

    private HBox createSelectionToolbar() {
        HBox toolbar = new HBox(15);
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setPadding(new Insets(10));
        toolbar.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5;");

        selectedCountLabel = new Label("0 Selected:");
        selectedCountLabel.setStyle("-fx-font-weight: bold;");

        Button clearSelectionBtn = new Button("×");
        clearSelectionBtn.setOnAction(e -> clearSelection());
        clearSelectionBtn.setStyle("-fx-text-fill: red;");
        Button refundBtn = new Button("Refund Transactions");
        refundBtn.setStyle("-fx-text-fill: red;");

        Button emailBuyerBtn = new Button("Email Buyer");
        Button emailSellerBtn = new Button("Email Seller");

        toolbar.getChildren().addAll(
                clearSelectionBtn,
                selectedCountLabel,
                new Separator(javafx.geometry.Orientation.VERTICAL),
                refundBtn,
                emailBuyerBtn,
                emailSellerBtn
        );

        return toolbar;
    }

    private TableView<Transaction> createTransactionTable() {
        TableView<Transaction> table = new TableView<>();
        table.setStyle("-fx-background-color: #FFFFFF;");
        TableColumn<Transaction, Boolean> selectCol = new TableColumn<>("");
        selectCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        selectCol.setCellFactory(tc -> new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();
            {
                checkBox.setOnAction(e -> {
                    Transaction transaction = getTableView().getItems().get(getIndex());
                    if (transaction != null) {
                        transaction.setSelected(checkBox.isSelected());
                        updateSelectionToolbar();
                    }
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(checkBox);
                    checkBox.setSelected(item);
                }
            }
        });

        TableColumn<Transaction, String> bookCol = new TableColumn<>("Book Purchased");
        bookCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));

        TableColumn<Transaction, String> buyerCol = new TableColumn<>("Buyer");
        buyerCol.setCellValueFactory(new PropertyValueFactory<>("buyerEmail"));

        TableColumn<Transaction, String> sellerCol = new TableColumn<>("Seller");
        sellerCol.setCellValueFactory(new PropertyValueFactory<>("sellerEmail"));

        TableColumn<Transaction, Double> totalCol = new TableColumn<>("Transaction Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalCol.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Transaction Time");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        dateCol.setStyle("-fx-alignment: CENTER-RIGHT;");

        table.getColumns().addAll(selectCol, bookCol, buyerCol, sellerCol, totalCol, dateCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

    private void loadTransactions() {
        transactions = FXCollections.observableArrayList();
        try (ResultSet rs = SqliteImplementation.getUserTransactions(null)) {
            if (rs != null) {
                while (rs.next()) {
                    String bookTitle = rs.getString("book_title");
                    String buyerEmail = rs.getString("buyer_email");
                    String sellerEmail = rs.getString("seller_email");
                    Double price = rs.getDouble("price");
                    String transactionDate = rs.getString("transaction_date");

                    if (bookTitle != null && buyerEmail != null && sellerEmail != null &&
                            price != null && transactionDate != null) {
                        transactions.add(new Transaction(bookTitle, buyerEmail, sellerEmail, price, transactionDate));
                    } else {
                        System.err.println("Skipping transaction due to null values in database.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load transactions: " + e.getMessage());
        }
        transactionTable.setItems(transactions);
    }

    private void updateSelectionToolbar() {
        if (transactions != null) {
            long selectedCount = transactions.stream().filter(Transaction::isSelected).count();
            selectionToolbar.setVisible(selectedCount > 0);
            selectedCountLabel.setText(selectedCount + " Selected:");
        }
    }

    private void clearSelection() {
        if (transactions != null) {
            transactions.forEach(t -> t.setSelected(false));
            transactionTable.refresh();
        }
        updateSelectionToolbar();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static class Transaction {
        private final String bookTitle;
        private final String buyerEmail;
        private final String sellerEmail;
        private final double price;
        private final String transactionDate;
        private boolean selected;

        public Transaction(String bookTitle, String buyerEmail, String sellerEmail,
                           double price, String transactionDate) {
            this.bookTitle = bookTitle;
            this.buyerEmail = buyerEmail;
            this.sellerEmail = sellerEmail;
            this.price = price;
            this.transactionDate = transactionDate;
            this.selected = false;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public String getBuyerEmail() {
            return buyerEmail;
        }

        public String getSellerEmail() {
            return sellerEmail;
        }

        public double getPrice() {
            return price;
        }

        public String getTransactionDate() {
            return transactionDate;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}