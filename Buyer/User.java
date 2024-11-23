package application;
class User {
    private String name;
    private String username;
    private String registrationDate;

    public User(String name, String username) {
        this.name = name;
        this.username = username;
        this.registrationDate = "2024-01-01"; // Simulated date
    }

    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getRegistrationDate() { return registrationDate; }
}
