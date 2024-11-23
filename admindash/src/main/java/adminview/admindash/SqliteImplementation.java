package adminview.admindash;

import Account.DataBase.Book;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SqliteImplementation {

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Database36.sqlite");
            System.out.println("Connection to SQLite database has been established.");
        } catch (SQLException e) {
            System.out.println("Connection to SQLite failed. " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found. " + e.getMessage());
        }
        return conn;
    }
    public static boolean insertUser(String name, String lastName, String email, String confirmEmail, String password, String confirmPassword, String userType) {
        if (name == null || name.isEmpty() || lastName == null || lastName.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty() || userType == null || userType.isEmpty()) {
            System.out.println("All fields are required.");
            return false;
        }
        if (!email.matches("[^@]+@[^@]+\\.[^@]+")) {
            System.out.println("Invalid email format.");
            return false;
        }
        if (!email.equals(confirmEmail))
        {
            System.out.println("Email and Confirm Email do not match");
            return false;
        }
        if (!password.equals(confirmPassword))
        {
            System.out.println("Password and Confirm Password do not match");
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String joinDate = now.format(formatter);

        String sql = "INSERT INTO users (name, last_name, email, confirm_email, password, confirm_password, user_type, join_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, confirmEmail);
            pstmt.setString(5, password);
            pstmt.setString(6, confirmPassword);
            pstmt.setString(7, userType);
            pstmt.setString(8, joinDate);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Insert user failed. " + e.getMessage());
            return false;
        } finally {
            closeResultSetAndConnection(null, conn, pstmt);
        }
    }
    public static void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                  name TEXT,
                  last_name TEXT,
                  email TEXT,
                  confirm_email TEXT,
                  password TEXT,
                  confirm_password TEXT,
                  user_type TEXT,
                  join_date TEXT
                );""";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Table creation failed. " + e.getMessage());
        }
    }

    public static String confirmLogin(String email, String password) {
        String sql = "SELECT user_type FROM users WHERE email = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try(ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    return rs.getString("user_type");
                }
            }

        } catch (SQLException e) {
            System.out.println("Login validation failed. " + e.getMessage());
        }
        return null;
    }

    public static ResultSet getUsersByType(String userType) {
        String sql = "SELECT name, last_name, email, join_date FROM users WHERE user_type = ?";
        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userType);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
            return null;
        }
    }
    public static void closeResultSetAndConnection(ResultSet rs, Connection conn, PreparedStatement pstmt) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
    public static void createBookTransactionTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS book_transactions (
                transaction_id INTEGER PRIMARY KEY AUTOINCREMENT,
                buyer_email TEXT NOT NULL,
                seller_email TEXT NOT NULL,
                book_title TEXT NOT NULL,
                price DECIMAL(10,2) NOT NULL,
                transaction_date TEXT,
                FOREIGN KEY (buyer_email) REFERENCES users(email),
                FOREIGN KEY (seller_email) REFERENCES users(email)
            );""";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Book transactions table created successfully.");
        } catch (SQLException e) {
            System.out.println("Book transactions table creation failed. " + e.getMessage());
        }
    }

    public static boolean insertBookTransaction(String buyerEmail, String sellerEmail, String bookTitle, double price) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String transactionDate = now.format(formatter);

        String sql = "INSERT INTO book_transactions (buyer_email, seller_email, book_title, price, transaction_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, buyerEmail);
            pstmt.setString(2, sellerEmail);
            pstmt.setString(3, bookTitle);
            pstmt.setDouble(4, price);
            pstmt.setString(5, transactionDate);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Insert book transaction failed. " + e.getMessage());
            return false;
        }
    }

    public static ResultSet getUserTransactions(String userEmail) {
        String sql;
        Connection conn = connect();
        PreparedStatement pstmt;

        try {
            if (userEmail == null || userEmail.isEmpty()) {
                sql = "SELECT * FROM book_transactions ORDER BY transaction_date DESC";
                pstmt = conn.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM book_transactions WHERE buyer_email = ? OR seller_email = ? ORDER BY transaction_date DESC";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, userEmail);
                pstmt.setString(2, userEmail);
            }

            ResultSet rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println("Error retrieving transactions: " + e.getMessage());
            return null;
        }
    }
    public static void printAllTransactions() {
        String sql = "SELECT * FROM book_transactions";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- All Transactions in Database ---");
            while (rs.next()) {
                System.out.println(String.format("ID: %d, Book: %s, Buyer: %s, Seller: %s, Price: %.2f, Date: %s",
                        rs.getInt("transaction_id"),
                        rs.getString("book_title"),
                        rs.getString("buyer_email"),
                        rs.getString("seller_email"),
                        rs.getDouble("price"),
                        rs.getString("transaction_date")));
            }
            System.out.println("--- End of Transactions ---\n");
        } catch (SQLException e) {
            System.out.println("Error printing transactions: " + e.getMessage());
        }
    }
    public static void deleteUser(String email) throws SQLException {
        String sql = "DELETE FROM users WHERE email = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
        }
    }

    public static void updateUser(String originalEmail, String newName, String newEmail) throws SQLException {
        String[] nameParts = newName.trim().split("\\s+", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        String sql = "UPDATE users SET name = ?, last_name = ?, email = ? WHERE email = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, newEmail);
            pstmt.setString(4, originalEmail);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
        }
    }
    public static void createBookTable() {
        String sql = "CREATE TABLE IF NOT EXISTS books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "author TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "photo TEXT," +
                "listing_date DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to get all currently listed books
    public static List<String> getCurrentlyListedBooks() {
        List<String> books = new ArrayList<>();
        String sql = "SELECT title, author, price FROM books ORDER BY listing_date DESC";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String bookInfo = String.format("%s by %s - $%.2f",
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"));
                books.add(bookInfo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return books;
    }

    // Method to get today's statistics
    public static Map<String, Object> getTodayStatistics() {
        Map<String, Object> stats = new HashMap<>();

        String sqlBooksSold = "SELECT COUNT(*) as count, SUM(price) as revenue " +
                "FROM book_transactions " +
                "WHERE date(transaction_date) = date('now')";

        String sqlBooksListed = "SELECT COUNT(*) as count " +
                "FROM books " +
                "WHERE date(listing_date) = date('now')";

        String sqlUserCount = "SELECT COUNT(DISTINCT user_id) as count " +
                "FROM user_sessions " +
                "WHERE last_activity > datetime('now', '-15 minutes')";

        try (Connection conn = connect()) {
            // Get books sold and revenue
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlBooksSold)) {
                if (rs.next()) {
                    stats.put("booksSold", rs.getInt("count"));
                    stats.put("revenue", rs.getDouble("revenue"));
                }
            }

            // Get books listed today
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlBooksListed)) {
                if (rs.next()) {
                    stats.put("booksListed", rs.getInt("count"));
                }
            }

            // Get active users
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlUserCount)) {
                if (rs.next()) {
                    stats.put("activeUsers", rs.getInt("count"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stats;
    }

    // Method to get best selling book of the month
    public static Map<String, Object> getBestSellingBook() {
        Map<String, Object> bestSeller = new HashMap<>();

        String sql = "SELECT bt.book_title, b.author, b.photo_url, " +
                "COUNT(*) as copies_sold " +
                "FROM book_transactions bt " +
                "JOIN books b ON bt.book_title = b.title " +
                "WHERE strftime('%Y-%m', transaction_date) = strftime('%Y-%m', 'now') " +
                "GROUP BY bt.book_title " +
                "ORDER BY copies_sold DESC " +
                "LIMIT 1";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                bestSeller.put("title", rs.getString("book_title"));
                bestSeller.put("author", rs.getString("author"));
                bestSeller.put("photoUrl", rs.getString("photo_url"));
                bestSeller.put("copiesSold", rs.getInt("copies_sold"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bestSeller;
    }
    public static List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT title, author, price, photo FROM books";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                String photoUrl = rs.getString("photo");

                Book book = new Book(title, author, price, photoUrl);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving books: " + e.getMessage());
        }
        return books;
    }
}