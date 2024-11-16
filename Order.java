package application;
import java.util.ArrayList;
class Order {
    private String orderId;
    private User user;
    private ArrayList<CartItem> items;
    private double total;

    public Order(User user, Cart cart) {
        this.orderId = generateOrderId();
        this.user = user;
        this.items = new ArrayList<>(cart.getItems());
        this.total = cart.getTotal();
    }

    private String generateOrderId() {
        return "ORD-" + System.currentTimeMillis();
    }

    public String getOrderId() { return orderId; }
}