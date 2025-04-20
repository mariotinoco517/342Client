import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

public class LoginScene {

    Text welcome;

    HBox usernameInput;
    VBox loginBox;
    Text login;
    Rectangle loginLine;
    Text username;
    TextField usernameField;
    Text password;
    TextField passwordField;
    Button loginButton;

    VBox createBox;

    StackPane loginPane;
    Scene loginScene;

    public LoginScene() {
        welcome = new Text("Connect 4 Online");
        login = new Text("Login");
        login.setTranslateX(-166);
        login.setTranslateY(-102);
        login.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        loginLine = new Rectangle();
        loginLine.setHeight(5);
        loginLine.setWidth(320);
        loginLine.setTranslateX(-60);
        loginLine.setTranslateY(-157);
        username = new Text("Username");
        usernameField = new TextField();
        password = new Text("Password");
        passwordField = new TextField();
        loginButton = new Button("Login");
        loginPane = new StackPane();
        loginPane.getChildren().addAll(login, loginLine, username, usernameField, password, passwordField, loginButton);
        loginScene = new Scene(loginPane, 870 ,520);
    }
    public Scene getLoginScene() {
        return loginScene;
    }
}
