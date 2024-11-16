package application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
class Cart {
    private User user;
    private ObservableList<CartItem> items;
    private double tax = 0.074; // 7.4%

    public Cart(User user) {
        this.user = user;
        this.items = FXCollections.observableArrayList();
    }

    public void addItem(Book book, int quantity) {
        items.add(new CartItem(book, quantity));
    }

    public ObservableList<CartItem> getItems() { return items; }
    public int getItemCount() { return items.size(); }
    
    public double getSubtotal() {
        return items.stream().mapToDouble(CartItem::getSubtotal).sum();
    }
    
    public double getTax() { return getSubtotal() * tax; }
    public double getTotal() { return getSubtotal() + getTax(); }
    
    public void clear() { items.clear(); }
}
