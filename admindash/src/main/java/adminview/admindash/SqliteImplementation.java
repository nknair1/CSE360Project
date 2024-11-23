package adminview.admindash;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        String sql = """
            SELECT * FROM book_transactions 
            WHERE buyer_email = ? OR seller_email = ? 
            ORDER BY transaction_date DESC""";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userEmail);
            pstmt.setString(2, userEmail);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error retrieving transactions: " + e.getMessage());
            return null;
        }
    }
}