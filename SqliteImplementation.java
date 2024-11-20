package application;

import java.sql.*;

public class SqliteImplementation {
  
    public static Connection connect() {
        Connection conn = null; // declare connection for it to be returned after try catch
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Database36.sqlite");
            System.out.println("Connection to SQLite database has been established.");
        } catch (SQLException e) {
            System.out.println("Connection to SQLite failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found: " + e.getMessage());
        }
        return conn; // return the connection object
    }



}