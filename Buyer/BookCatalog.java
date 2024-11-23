package application;
import java.util.ArrayList;
class BookCatalog {
    private ArrayList<Book> books;

    public BookCatalog() {
        books = new ArrayList<>();
        initializeSampleBooks();
    }

    private void initializeSampleBooks() {
        books.add(new Book("The Code of Tomorrow: Introduction to Programming", 42.69));
        books.add(new Book("Deep Waters: The Mysteries of the Ocean Depths", 35.99));
        books.add(new Book("The Calculus of Life: From Curves to Rates of Change", 55.50));
        books.add(new Book("The Art of Words: Mastering English Grammar", 29.99));
    }

    public ArrayList<Book> getBooks() { return books; }
}
