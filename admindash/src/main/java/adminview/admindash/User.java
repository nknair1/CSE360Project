package adminview.admindash;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//Need to properly implement this user class to do the sqllite implementation.
public class User {
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty joinDate;
    public User(String name, String email, String joinDate) {
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.joinDate = new SimpleStringProperty(joinDate);
    }
    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty emailProperty() {
        return email;
    }
    public StringProperty joinDateProperty() {
        return joinDate;
    }
}