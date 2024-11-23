Certainly! Here's your original JavaFX code, formatted correctly with the spaces removed as requested:

```java
package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.FileInputStream;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import application.SqliteImplementation;
import java.io.InputStream;
import java.sql.Connection;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.InputStream;
import javafx.scene.shape.*;
import javafx.scene.image.Image;

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
            TextField passwordTextField = new TextField();
            passwordTextField.setStyle("-fx-background-color: #D3D3D3;");
            passwordTextField.setPrefWidth(300);
            passwordTextField.setMaxWidth(300);
            Hyperlink forgot = new Hyperlink("Forgot Password");
            forgot.setFont(new Font("DM Sans", 13));
            forgot.setStyle("-fx-text-fill: #8C1D40;");
            Button login = new Button("Login");
            login.setPrefSize(100,30);
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
                    intakeScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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

                    Label blank = new Label ("");
                    Label forgot = new Label("Forgot Password");
                    forgot.setFont(new Font("DM Sans",20));
                    forgot.setStyle("-fx-text-fill: #8C1D40;");
                    forgot.setAlignment(Pos.BOTTOM_CENTER);
                    Label newPassword = new Label ("New Password");
                    newPassword.setAlignment(Pos.BOTTOM_CENTER);
                    newPassword.setFont(new Font("DM Sans",10));
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
                    send.setPrefSize(100,30);
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
                    intakeScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                    VBox titleBox = new VBox();
                    root1.setTop(titleBox);
                    titleBox.setStyle("-fx-background-color: #ffd700;");
                    titleBox.setMinHeight(90);

                    Label blank = new Label ("");
                    Label createNew = new Label("Create New Account");
                    createNew.setFont(new Font("DM Sans",20));
                    createNew.setStyle("-fx-text-fill: #8C1D40;");
                    createNew.setAlignment(Pos.TOP_CENTER);
                    Label newPassword = new Label ("Already Registered? Login");
                    newPassword.setAlignment(Pos.BOTTOM_CENTER);
                    newPassword.setFont(new Font("DM Sans",10));
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

                    Button submit = new Button("Submit");
                    submit.setPrefSize(100,30);
                    submit.setFont(new Font("DM Sans", 13));
                    submit.setStyle("-fx-background-color: #ffd700; -fx-text-fill: #8C1D40;");

                    VBox createAccount = new VBox();
                    createAccount.getChildren().addAll(first, firstTextField, last, lastTextField, submit, rbuttons);
                    createAccount.setSpacing(20);
                    root1.setCenter(createAccount);

                    primaryStage.setScene(intakeScene);
                    primaryStage.setResizable(false);
                    primaryStage.show();
                }
            });

            login.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    try {
                        Connection conn = SqliteImplementation.connectToDatabase();
                        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
                        String emailValue = emailTextField.getText();
                        String passwordValue = passwordTextField.getText();
                        boolean validLogin = SqliteImplementation.verifyUser(conn, query, emailValue, passwordValue);
                        if (validLogin) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Login Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Login successful! Welcome.");
                            alert.showAndWait();
                            // Proceed to the next page
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Login Failed");
                            alert.setHeaderText(null);
                            alert.setContentText("Invalid email or password.");
                            alert.showAndWait();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            Scene scene = new Scene(root, 800, 500);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```
