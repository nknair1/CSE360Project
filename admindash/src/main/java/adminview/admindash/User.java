package adminview.admindash;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty joinDate;

    public User(String name, String email, String joinDate) {
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.joinDate = new SimpleStringProperty(joinDate);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getJoinDate() {
        return joinDate.get();
    }

    public void setJoinDate(String joinDate) {
        this.joinDate.set(joinDate);
    }

    public StringProperty joinDateProperty() {
        return joinDate;
    }
}