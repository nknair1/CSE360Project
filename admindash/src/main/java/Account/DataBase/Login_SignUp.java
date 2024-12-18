package Account.DataBase;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import adminview.admindash.AdminDash;
import javafx.scene.control.PasswordField;

public class Login_SignUp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();

            HBox titleBox = new HBox();
            root.setTop(titleBox);

            titleBox.setStyle("-fx-background-color: #ffd700;");
            String imageUrl = "https://github.com/nknair1/CSE360Project/blob/main/ASU.png?raw=true";
            Image image = new Image(imageUrl);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(300);
            imageView.setFitHeight(125);
            titleBox.getChildren().add(imageView);
            titleBox.setAlignment(Pos.TOP_CENTER);
            titleBox.setMinHeight(120);

            Label email = new Label("PLEASE ENTER YOUR ASU EMAIL");
            email.setStyle("-fx-text-fill: #8F8E8E;");
            email.setFont(new Font("DM Sans", 13));
            Label password = new Label("PLEASE ENTER YOUR PASSWORD");
            password.setStyle("-fx-text-fill: #8F8E8E;");
            password.setFont(new Font("DM Sans", 13));

            TextField emailTextField = new TextField();
            emailTextField.setStyle("-fx-background-color: #D3D3D3;");
            emailTextField.setPrefWidth(300);
            emailTextField.setMaxWidth(300);
            PasswordField passwordTextField = new PasswordField();
            passwordTextField.setStyle("-fx-background-color: #D3D3D3;");
            passwordTextField.setPrefWidth(300);
            passwordTextField.setMaxWidth(300);

            PasswordField passwordTextField2 = new PasswordField();
            passwordTextField2.setStyle("-fx-background-color: #D3D3D3;");
            passwordTextField2.setPrefWidth(300);
            passwordTextField2.setMaxWidth(300);
            Hyperlink forgot = new Hyperlink("Forgot Password");
            forgot.setFont(new Font("DM Sans", 13));
            forgot.setStyle("-fx-text-fill: #8C1D40;");
            Button login = new Button("Login");
            login.setPrefSize(100, 30);
            login.setFont(new Font("DM Sans", 13));
            login.setStyle("-fx-background-color: #ffd700; -fx-text-fill: #8C1D40;");
            Hyperlink create = new Hyperlink("Create an Account");
            create.setFont(new Font("DM Sans", 13));
            create.setStyle("-fx-text-fill: #8C1D40;");
            VBox textfields = new VBox();
            textfields.getChildren().addAll(email, emailTextField, password, passwordTextField, forgot, login, create);
            textfields.setSpacing(10);
            root.setCenter(textfields);
            textfields.setAlignment(Pos.CENTER);

            forgot.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    BorderPane root1 = new BorderPane();
                    Scene intakeScene = new Scene(root1, 800, 500);
                    intakeScene.getStylesheets().add(getClass().getResource("Account/DataBase/application.css").toExternalForm());

                    VBox titleBox = new VBox();
                    root1.setTop(titleBox);
                    titleBox.setStyle("-fx-background-color: #ffd700;");
                    titleBox.setMinHeight(120);

                    HBox backBox = new HBox();
                    Button back = new Button("←");
                    back.setStyle("-fx-background-color: transparent; -fx-text-fill: #8C1D40;");
                    back.setOnAction(event -> start(primaryStage));
                    backBox.getChildren().add(back);
                    backBox.setAlignment(Pos.TOP_LEFT);
                    titleBox.setStyle("-fx-background-color: #ffd700;");
                    titleBox.setMinHeight(120);
                    titleBox.getChildren().add(backBox);

                    Label blank = new Label("");
                    Label forgot = new Label("Forgot Password");
                    forgot.setFont(new Font("DM Sans", 20));
                    forgot.setStyle("-fx-text-fill: #8C1D40;");
                    forgot.setAlignment(Pos.BOTTOM_CENTER);
                    Label newPassword = new Label("New Password");
                    newPassword.setAlignment(Pos.BOTTOM_CENTER);
                    newPassword.setFont(new Font("DM Sans", 10));
                    newPassword.setStyle("-fx-text-fill: #8C1D40;");

                    titleBox.getChildren().addAll(blank, forgot, newPassword);
                    titleBox.setSpacing(10);
                    root1.setTop(titleBox);
                    titleBox.setAlignment(Pos.TOP_CENTER);

                    Label email = new Label("PLEASE ENTER YOUR ASU EMAIL");
                    email.setStyle("-fx-text-fill: #8F8E8E;");
                    email.setFont(new Font("DM Sans", 13));

                    TextField emailTextField = new TextField();
                    emailTextField.setStyle("-fx-background-color: #D3D3D3;");
                    emailTextField.setPrefWidth(300);
                    emailTextField.setMaxWidth(300);

                    Button send = new Button("Send");
                    send.setPrefSize(100, 30);
                    send.setFont(new Font("DM Sans", 13));
                    send.setStyle("-fx-background-color: #ffd700; -fx-text-fill: #8C1D40;");

                    VBox forgotten = new VBox();
                    forgotten.getChildren().addAll(email, emailTextField, send);
                    forgotten.setSpacing(20);
                    forgotten.setAlignment(Pos.CENTER);
                    root1.setCenter(forgotten);

                    primaryStage.setScene(intakeScene);
                    primaryStage.setResizable(false);
                    primaryStage.show();
                }
            });

            create.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    BorderPane root1 = new BorderPane();
                    Scene intakeScene = new Scene(root1, 800, 500);

                    VBox titleBox = new VBox();
                    root1.setTop(titleBox);
                    titleBox.setStyle("-fx-background-color: #ffd700;");
                    titleBox.setMinHeight(90);

                    Label blank = new Label("");
                    Label createNew = new Label("Create New Account");
                    createNew.setFont(new Font("DM Sans", 20));
                    createNew.setStyle("-fx-text-fill: #8C1D40;");
                    createNew.setAlignment(Pos.TOP_CENTER);
                    Label newPassword = new Label("Already Registered? Login");
                    newPassword.setAlignment(Pos.BOTTOM_CENTER);
                    newPassword.setFont(new Font("DM Sans", 10));
                    newPassword.setStyle("-fx-text-fill: #8C1D40;");

                    VBox headers = new VBox();
                    headers.getChildren().addAll(createNew, newPassword, blank);

                    HBox backBox = new HBox();
                    Button back = new Button("←");
                    back.setStyle("-fx-background-color: transparent; -fx-text-fill: #8C1D40;");
                    back.setOnAction(event -> start(primaryStage));
                    backBox.getChildren().add(back);
                    backBox.setAlignment(Pos.TOP_LEFT);
                    titleBox.setStyle("-fx-background-color: #ffd700;");
                    titleBox.setMinHeight(90);
                    titleBox.getChildren().add(backBox);

                    titleBox.getChildren().addAll(createNew, newPassword, blank);
                    titleBox.setSpacing(10);
                    root1.setTop(titleBox);
                    titleBox.setAlignment(Pos.BASELINE_CENTER);

                    Label what = new Label("YOU ARE A:");
                    what.setStyle("-fx-text-fill: #8F8E8E;");
                    what.setFont(new Font("DM Sans", 13));
                    RadioButton rb2 = new RadioButton("Buyer");
                    RadioButton rb3 = new RadioButton("Seller");
                    rb2.setStyle("-fx-text-fill: #8F8E8E;");
                    rb2.setFont(new Font("DM Sans", 13));
                    rb3.setStyle("-fx-text-fill: #8F8E8E;");
                    rb3.setFont(new Font("DM Sans", 13));
                    ToggleGroup rbGroup = new ToggleGroup();
                    rb2.setToggleGroup(rbGroup);
                    rb3.setToggleGroup(rbGroup);

                    HBox rbuttons = new HBox();
                    rbuttons.getChildren().addAll(what, rb2, rb3);
                    rbuttons.setAlignment(Pos.BASELINE_CENTER);
                    rbuttons.setSpacing(20);

                    Label first = new Label("FIRST NAME");
                    first.setStyle("-fx-text-fill: #8F8E8E;");
                    first.setFont(new Font("DM Sans", 13));

                    Label last = new Label("LAST NAME");
                    last.setStyle("-fx-text-fill: #8F8E8E;");
                    last.setFont(new Font("DM Sans", 13));

                    TextField firstTextField = new TextField();
                    firstTextField.setStyle("-fx-background-color: #D3D3D3;");
                    firstTextField.setPrefWidth(300);
                    firstTextField.setMaxWidth(300);

                    TextField lastTextField = new TextField();
                    lastTextField.setStyle("-fx-background-color: #D3D3D3;");
                    lastTextField.setPrefWidth(300);
                    lastTextField.setMaxWidth(300);

                    HBox initial = new HBox();
                    initial.setAlignment(Pos.TOP_CENTER);
                    initial.getChildren().addAll(first, firstTextField, last, lastTextField);
                    initial.setSpacing(10);

                    TextField emailTextField2 = new TextField();
                    emailTextField2.setStyle("-fx-background-color: #D3D3D3;");
                    emailTextField2.setPrefWidth(300);
                    emailTextField2.setMaxWidth(300);

                    Label email = new Label("PLEASE ENTER YOUR ASU EMAIL");
                    email.setStyle("-fx-text-fill: #8F8E8E;");
                    email.setFont(new Font("DM Sans", 13));

                    Label emailRepeat = new Label("PLEASE RE-ENTER YOUR ASU EMAIL");
                    emailRepeat.setStyle("-fx-text-fill: #8F8E8E;");
                    emailRepeat.setFont(new Font("DM Sans", 13));

                    TextField emailTextField = new TextField();
                    emailTextField.setStyle("-fx-background-color: #D3D3D3;");
                    emailTextField.setPrefWidth(300);
                    emailTextField.setMaxWidth(300);

                    Label password = new Label("PLEASE ENTER YOUR PASSWORD");
                    password.setStyle("-fx-text-fill: #8F8E8E;");
                    password.setFont(new Font("DM Sans", 13));

                    Label passwordRepeat = new Label("PLEASE RE-ENTER YOUR PASSWORD");
                    passwordRepeat.setStyle("-fx-text-fill: #8F8E8E;");
                    passwordRepeat.setFont(new Font("DM Sans", 13));

                    PasswordField passwordTextField = new PasswordField();
                    passwordTextField.setStyle("-fx-background-color: #D3D3D3;");
                    passwordTextField.setPrefWidth(300);
                    passwordTextField.setMaxWidth(300);

                    PasswordField passwordTextField2 = new PasswordField();
                    passwordTextField2.setStyle("-fx-background-color: #D3D3D3;");
                    passwordTextField2.setPrefWidth(300);
                    passwordTextField2.setMaxWidth(300);

                    Button signUp = new Button("Sign up");
                    signUp.setPrefSize(100, 30);
                    signUp.setFont(new Font("DM Sans", 13));
                    signUp.setStyle("-fx-background-color: #ffd700; -fx-text-fill: #8C1D40;");

                    signUp.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            String firstName = firstTextField.getText();
                            String lastName = lastTextField.getText();
                            String email = emailTextField.getText();
                            String confirmEmail = emailTextField2.getText();
                            String password = passwordTextField.getText();
                            String confirmPassword = passwordTextField2.getText();
                            String userType = rb2.isSelected() ? "Buyer" : rb3.isSelected() ? "Seller" : "";

                            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || confirmEmail.isEmpty() ||
                                    password.isEmpty() || confirmPassword.isEmpty() || userType.isEmpty()) {
                                System.out.println("Fields cannot be empty");
                                showAlert1("Error", "Fields cannot be empty");
                                return;
                            }

                            email = email.trim().toLowerCase();

                            if (!email.contains("@") || !email.endsWith("@asu.edu")) {
                                System.out.println("Please enter a valid ASU email address");
                                showAlert1("Error", "Please enter a valid ASU email address");
                                return;
                            }

                            if (email.length() <= "@asu.edu".length()) {
                                System.out.println("Invalid email format: ASURITE required");
                                showAlert1("Error", "Invalid email format: ASURITE required");
                                return;
                            }

                            if (!email.equals(confirmEmail)) {
                                System.out.println("Emails don't match.");
                                showAlert1("Error", "Emails don't match.");
                                return;
                            }

                            if (!password.equals(confirmPassword)) {
                                System.out.println("Passwords don't match.");
                                showAlert1("Error", "Passwords don't match.");
                                return;
                            }

                            boolean success = SqliteImplementation.insertUser(firstName, lastName, email, confirmEmail, password, confirmPassword, userType);
                            if (success) {
                                System.out.println("User registered successfully");
                                showAlert1("Success", "User registered successfully");
                            } else {
                                System.out.println("User registration failed");
                                showAlert1("Error", "User registration failed");
                            }
                        }

                        private void showAlert1(String title, String warning) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(title);
                            alert.setHeaderText(null);
                            alert.setContentText(warning);
                            alert.showAndWait();
                        }
                    });

                    VBox forgotten = new VBox();
                    forgotten.getChildren().addAll(rbuttons, initial, email, emailTextField, emailRepeat, emailTextField2,
                            password, passwordTextField, passwordRepeat, passwordTextField2, signUp);
                    forgotten.setSpacing(10);
                    forgotten.setAlignment(Pos.CENTER);
                    root1.setCenter(forgotten);

                    primaryStage.setScene(intakeScene);
                    primaryStage.setResizable(false);
                    primaryStage.show();
                }
            });

            login.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String emailInput = emailTextField.getText();
                    String passwordInput = passwordTextField.getText();

                    // Admin Credentials
                    String adminEmail = "admin";
                    String adminPassword = "admin123";

                    if (emailInput.equals(adminEmail) && passwordInput.equals(adminPassword)) {
                        try {
                            AdminDash adminDash = new AdminDash();
                            adminDash.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        User loggedInUser = SqliteImplementation.confirmLogin(emailInput, passwordInput);

                        if (loggedInUser != null) {
                            System.out.println("Login successful for " + loggedInUser.getUserType());

                            if (loggedInUser.getUserType().equalsIgnoreCase("Buyer")) {
                                Buyerview2 buyerView = new Buyerview2(loggedInUser);
                                BorderPane buyerRoot = buyerView.getView(primaryStage);
                                Scene buyerScene = new Scene(buyerRoot, 1200, 800);
                                primaryStage.setScene(buyerScene);
                                primaryStage.setTitle("ASU Bookstore - Buyer");
                            } else if (loggedInUser.getUserType().equalsIgnoreCase("Seller")) {
                                seller sellerView = new seller();
                                try {
                                    sellerView.start(primaryStage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            System.out.println("Login failed. Please check your email/password");
                            showAlert("Login failed", "Please check your email/password");
                        }
                    }
                }

                private void showAlert(String title, String warning) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(title);
                    alert.setHeaderText(null);
                    alert.setContentText(warning);
                    alert.showAndWait();
                }
            });

            Scene scene = new Scene(root, 800, 500);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}