package application;
class CartItem {
    private Book book;
    private int quantity;

    public CartItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() { return book; }
    public int getQuantity() { return quantity; }
    public double getSubtotal() { return book.getPrice() * quantity; }

    @Override
    public String toString() {
        return String.format("%s (x%d) - $%.2f", book.getTitle(), quantity, getSubtotal());
    }
}