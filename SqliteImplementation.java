package application;

import java.sql.*;
import java.util.ArrayList;

public class SqliteImplementation {

    public static Connection connect() {
        Connection conn = null; 
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Database36.sqlite");
            System.out.println("Connection to SQLite database has been established.");
        } catch (SQLException e) {
            System.out.println("Connection to SQLite failed.");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found.");
        }
        return conn; 
    }

    public static boolean insertUser(String firstName, String lastName, String email, String confirmEmail, String password, String confirmPassword, String userType) {
        String sql = "INSERT INTO users (name, last_name, email, confirm_email, password, confirm_password, user_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, confirmEmail);
            pstmt.setString(5, password);
            pstmt.setString(6, confirmPassword);
            pstmt.setString(7, userType);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Insert user failed.");
            return false;
        }
    }

    public static User confirmLogin(String email, String password) {
        String sql = "SELECT name, last_name, user_type FROM users WHERE email = ? AND password = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String firstName = rs.getString("name");
                String lastName = rs.getString("last_name");
                String userType = rs.getString("user_type");
                String fullName = firstName + " " + lastName;
                return new User(fullName, email, userType);
            }
        } catch (SQLException e) {
            System.out.println("Login validation failed.");
        }
        return null;
    }
    
    public static boolean insertBook(Book book) {
        String sql = "INSERT INTO books (author, title, price, photo) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getAuthor());
            pstmt.setString(2, book.getTitle());
            pstmt.setDouble(3, book.getPrice());
            pstmt.setString(4, book.getPhoto());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Insert book failed. " + e.getMessage());
            return false;
        }
    }
    
    public static ArrayList<Book> getAllBooks() {
        String sql = "SELECT * FROM books";
        ArrayList<Book> books = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String author = rs.getString("author");
                String title = rs.getString("title");
                double price = rs.getDouble("price");
                String photo = rs.getString("photo");

                Book book = new Book(id, author, title, price, photo);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Retrieve books failed. " + e.getMessage());
        }
        return books;
    }

    
    
    
}
