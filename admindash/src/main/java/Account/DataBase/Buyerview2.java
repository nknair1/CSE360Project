package Account.DataBase;

import Account.DataBase.Book;
import Account.DataBase.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import adminview.admindash.SqliteImplementation;
import Account.DataBase.Login_SignUp;

import java.util.ArrayList;
import java.util.List;

public class Buyerview2 {
    // Fields
    private final User currentUser;
    private final Cart userCart;
    private final Book.BookCatalog bookCatalog;

    // Constructor
    public Buyerview2(User user) {
        this.currentUser = user;
        this.userCart = new Cart(currentUser);
        this.bookCatalog = new Book.BookCatalog();
    }

    // Method to get the buyer view
    public BorderPane getView(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Top navigation bar
        HBox topNav = createTopNav(primaryStage);
        root.setTop(topNav);

        // Center content
        TabPane contentTabs = createContentTabs();
        root.setCenter(contentTabs);

        return root;
    }

    private HBox createTopNav(Stage primaryStage) {
        HBox topNav = new HBox(10);
        topNav.setPadding(new Insets(10));
        topNav.setStyle("-fx-background-color: #FFD700;"); // Color Gold
        topNav.setAlignment(Pos.CENTER);

        // The logo
        ImageView logoView = new ImageView(new Image("https://github.com/nknair1/CSE360Project/blob/main/ASU.png?raw=true"));
        logoView.setFitHeight(40);  // Adjust size as needed
        logoView.setFitWidth(40);   // Adjust size as needed
        logoView.setPreserveRatio(true);

        HBox logoContainer = new HBox(logoView);
        HBox.setHgrow(logoContainer, Priority.ALWAYS);
        logoContainer.setAlignment(Pos.CENTER_LEFT);

        // Profile section
        Button profileBtn = new Button(currentUser.getName());
        profileBtn.setOnAction(e -> showProfile());

        // Navigation buttons
        HBox centerButtons = new HBox(20); // Increased spacing between buttons
        centerButtons.setAlignment(Pos.CENTER);

        Button browseBtn = new Button("Browse Books");
        Button cartBtn = new Button("View Cart");
        Label cartCount = new Label("(" + userCart.getItemCount() + ")");
        cartCount.setStyle("-fx-text-fill: black;"); // Made text black

        styleButton(browseBtn);
        styleButton(cartBtn);

        centerButtons.getChildren().addAll(browseBtn, cartBtn, cartCount);
        HBox.setHgrow(centerButtons, Priority.ALWAYS);

        // Logout button
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> handleLogout(primaryStage));

        String buttonStyle = "-fx-text-fill: white; -fx-background-color: transparent;";
        profileBtn.setStyle(buttonStyle);
        browseBtn.setStyle(buttonStyle);
        cartBtn.setStyle(buttonStyle);
        logoutBtn.setStyle(buttonStyle);

        // Add spacing
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topNav.getChildren().addAll(profileBtn, browseBtn, cartBtn, cartCount, spacer, logoutBtn);
        return topNav;
    }

    private void styleButton(Button button) {
        button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: black;" +            // Black text
                        "-fx-font-size: 14px;" +             // Larger font
                        "-fx-font-weight: bold;" +           // Bold text
                        "-fx-cursor: hand;"                  // Hand cursor on hover
        );

        // Add hover effect
        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: #E8B423;" + // Slightly darker gold on hover
                                "-fx-text-fill: black;" +
                                "-fx-font-size: 14px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: transparent;" +
                                "-fx-text-fill: black;" +
                                "-fx-font-size: 14px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-cursor: hand;"
                )
        );
    }

    private TabPane createContentTabs() {
        TabPane tabPane = new TabPane();

        // Browse Books Tab
        Tab browseTab = new Tab("Browse Books");
        browseTab.setContent(createBrowseContent());
        browseTab.setClosable(false);

        // Cart Tab
        Tab cartTab = new Tab("Cart");
        cartTab.setContent(createCartContent());
        cartTab.setClosable(false);

        tabPane.getTabs().addAll(browseTab, cartTab);
        return tabPane;
    }

    private VBox createBrowseContent() {
        VBox browseContent = new VBox(10);
        browseContent.setPadding(new Insets(10));

        // Filter section
        HBox filterBox = new HBox(10);
        ComboBox<String> categoryFilter = new ComboBox<>();
        categoryFilter.getItems().addAll("Natural Science", "Computer Science", "Mathematics", "Engineering");
        categoryFilter.setPromptText("Select Category");

        ComboBox<String> conditionFilter = new ComboBox<>();
        conditionFilter.getItems().addAll("New", "Used - Like New", "Used - Good", "Used - Acceptable");
        conditionFilter.setPromptText("Select Condition");

        filterBox.getChildren().addAll(new Label("Category:"), categoryFilter,
                new Label("Condition:"), conditionFilter);

        // Search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search books...");
        searchField.setPrefWidth(300);

        // Books grid
        GridPane booksGrid = createBooksGrid();

        // Pagination
        Pagination pagination = new Pagination();
        pagination.setPageCount(5);
        pagination.setMaxPageIndicatorCount(5);

        browseContent.getChildren().addAll(filterBox, searchField, booksGrid, pagination);
        return browseContent;
    }

    private GridPane createBooksGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Get books from the catalog
        List<Book> books = SqliteImplementation.getBooks();
        int col = 0;
        int row = 0;

        for (Book book : books) {
            VBox bookBox = createBookCard(book);
            grid.add(bookBox, col, row);

            col++;
            if (col > 2) {
                col = 0;
                row++;
            }
        }

        return grid;
    }

    private VBox createBookCard(Book book) {
        VBox bookBox = new VBox(5);
        bookBox.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-padding: 10;");

        ImageView coverImageView = new ImageView(book.getCoverImage());
        coverImageView.setFitWidth(150);
        coverImageView.setFitHeight(200);

        Label titleLabel = new Label(book.getTitle());
        titleLabel.setWrapText(true);
        titleLabel.setStyle("-fx-font-weight: bold;");

        Label authorLabel = new Label("By " + book.getAuthor());

        Label priceLabel = new Label("$" + String.format("%.2f", book.getPrice()));

        Button addToCartBtn = new Button("+");
        addToCartBtn.setOnAction(e -> addToCart(book));

        bookBox.getChildren().addAll(coverImageView, titleLabel, authorLabel, priceLabel, addToCartBtn);
        return bookBox;
    }

    private VBox createCartContent() {
        VBox cartContent = new VBox(10);
        cartContent.setPadding(new Insets(10));

        // Cart items list
        ListView<CartItem> cartItems = new ListView<>();
        cartItems.setItems(userCart.getItems());

        // Total section
        GridPane totalsGrid = new GridPane();
        totalsGrid.setHgap(10);
        totalsGrid.setVgap(5);

        totalsGrid.add(new Label("Subtotal:"), 0, 0);
        totalsGrid.add(new Label("$" + String.format("%.2f", userCart.getSubtotal())), 1, 0);
        totalsGrid.add(new Label("Tax (7.4%):"), 0, 1);
        totalsGrid.add(new Label("$" + String.format("%.2f", userCart.getTax())), 1, 1);
        totalsGrid.add(new Label("Total:"), 0, 2);
        totalsGrid.add(new Label("$" + String.format("%.2f", userCart.getTotal())), 1, 2);

        Button checkoutBtn = new Button("Check Out");
        checkoutBtn.setStyle("-fx-background-color: #FFC627;"); // ASU Gold
        checkoutBtn.setOnAction(e -> handleCheckout());

        cartContent.getChildren().addAll(cartItems, totalsGrid, checkoutBtn);
        return cartContent;
    }

    private void showProfile() {
        Stage profileStage = new Stage();
        VBox profileContent = new VBox(10);
        profileContent.setPadding(new Insets(10));

        Label nameLabel = new Label("Name: " + currentUser.getName());
        Label regDateLabel = new Label("Registration Date: " + currentUser.getRegistrationDate());

        Button changeNameBtn = new Button("Change Name");
        Button changePasswordBtn = new Button("Change Password");

        profileContent.getChildren().addAll(nameLabel, regDateLabel, changeNameBtn, changePasswordBtn);

        Scene profileScene = new Scene(profileContent, 300, 200);
        profileStage.setTitle("User Profile");
        profileStage.setScene(profileScene);
        profileStage.show();
    }

    private void handleCheckout() {
        if (userCart.getItemCount() == 0) {
            showAlert("Cart is empty", "Please add items to your cart before checking out.");
            return;
        }

        Stage checkoutStage = new Stage();
        VBox checkoutContent = new VBox(10);
        checkoutContent.setPadding(new Insets(10));

        // Display cart items
        ListView<CartItem> items = new ListView<>(userCart.getItems());

        // Total
        Label totalLabel = new Label("Total: $" + String.format("%.2f", userCart.getTotal()));

        // Buttons
        HBox buttons = new HBox(10);
        Button placeOrderBtn = new Button("Place Order");
        Button cancelBtn = new Button("Cancel Order");

        placeOrderBtn.setOnAction(e -> handlePlaceOrder(checkoutStage));
        cancelBtn.setOnAction(e -> checkoutStage.close());

        buttons.getChildren().addAll(placeOrderBtn, cancelBtn);

        checkoutContent.getChildren().addAll(items, totalLabel, buttons);

        Scene checkoutScene = new Scene(checkoutContent, 400, 500);
        checkoutStage.setTitle("Checkout");
        checkoutStage.setScene(checkoutScene);
        checkoutStage.show();
    }

    private void handlePlaceOrder(Stage checkoutStage) {
        Order order = new Order(currentUser, userCart);
        showAlert("Order Placed", "Your order has been successfully placed!\nOrder ID: " + order.getOrderId());
        userCart.clear();
        checkoutStage.close();
    }

    private void handleLogout(Stage primaryStage) {
        if (showConfirmation("Logout", "Are you sure you want to logout?")) {
            // Return to login screen
            Login_SignUp loginSignup = new Login_SignUp();
            loginSignup.start(primaryStage);
        }
    }

    private void addToCart(Book book) {
        userCart.addItem(book, 1);
        showAlert("Success", "Book added to cart!");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert.showAndWait().get() == ButtonType.OK;
    }

    // Inner Classes for CartItem, Cart, and Order...

    public class CartItem {
        private final Book book;
        private final int quantity;

        public CartItem(Book book, int quantity) {
            this.book = book;
            this.quantity = quantity;
        }

        public Book getBook() {
            return book;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getSubtotal() {
            return book.getPrice() * quantity;
        }

        @Override
        public String toString() {
            return String.format("%s (x%d) - $%.2f", book.getTitle(), quantity, getSubtotal());
        }
    }

    public class Cart {
        private final User user;
        private final ObservableList<CartItem> items;
        private final double tax = 0.074; // 7.4%

        public Cart(User user) {
            this.user = user;
            this.items = FXCollections.observableArrayList();
        }

        public void addItem(Book book, int quantity) {
            items.add(new CartItem(book, quantity));
        }

        public ObservableList<CartItem> getItems() {
            return items;
        }

        public int getItemCount() {
            return items.size();
        }

        public double getSubtotal() {
            return items.stream().mapToDouble(CartItem::getSubtotal).sum();
        }

        public double getTax() {
            return getSubtotal() * tax;
        }

        public double getTotal() {
            return getSubtotal() + getTax();
        }

        public void clear() {
            items.clear();
        }
    }

    public class Order {
        private final String orderId;
        private final User user;
        private final ArrayList<CartItem> items;
        private final double total;

        public Order(User user, Cart cart) {
            this.orderId = generateOrderId();
            this.user = user;
            this.items = new ArrayList<>(cart.getItems());
            this.total = cart.getTotal();
        }

        private String generateOrderId() {
            return "ORD-" + System.currentTimeMillis();
        }

        public String getOrderId() {
            return orderId;
        }
    }
}
