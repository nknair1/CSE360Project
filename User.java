package application;

public class User {
    private String name;
    private String email;
    private String userType;
    private String registrationDate;

    public User(String name, String email, String userType) {
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.registrationDate = "2024-01-01";
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getUserType() { return userType; }
    public String getRegistrationDate() { return registrationDate; }
}
