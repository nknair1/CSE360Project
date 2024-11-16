package application;
import javafx.scene.image.Image;

class Book {
    private String title;
    private double price;
    private Image coverImage;

    public Book(String title, double price) {
        this.title = title;
        this.price = price;
        // load actual book cover later
        this.coverImage = null;
    }

    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public Image getCoverImage() { return coverImage; }
}
