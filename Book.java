package application;

import javafx.scene.image.Image;
import java.util.ArrayList;

public class Book {
    private int id;
    private String author;
    private String title;
    private double price;
    private String photo; 
    private Image coverImage; 
    //private Image regularImage;

    public Book(int id, String author, String title, double price, String photo) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.price = price;
        this.photo = photo;
    }

    public Book(String author, String title, double price, String photo) {
        this(-1, author, title, price, photo);
    }

  
    public int getId() { return id; }
    public String getAuthor() { return author; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public String getPhoto() { return photo; }

    public Image getCoverImage() {
        if (coverImage == null) {
            if (photo != null && !photo.isEmpty()) {
                try {
                    coverImage = new Image(photo, true);
                } catch (Exception e) {
                    
                    coverImage = new Image("https://via.placeholder.com/150");
                }
            } else {
                coverImage = new Image("https://via.placeholder.com/150");
            }
        }
        return coverImage;
    }
   
    public static class BookCatalog {
        private ArrayList<Book> books;

        public BookCatalog() {
            loadBooksFromDatabase();
        }

        private void loadBooksFromDatabase() {
            books = SqliteImplementation.getAllBooks();
        }

        public ArrayList<Book> getBooks() { return books; }
    }
}
