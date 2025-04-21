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
import sun.font.CreatedFontTracker;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

public class LoginScene {

    Text welcome;

    Text login, username, password;
    Rectangle loginLine, loginBox;
    TextField usernameField, passwordField;
    Button loginButton;

    Text create, createName, createPassword;
    Rectangle createLine, createBox;
    TextField createUsernameField, createPasswordField;
    Button createButton;


    StackPane loginPane;
    Scene loginScene;

    public LoginScene() {
        welcome = new Text("Connect 4 Online");
        welcome.setTranslateY(-500);

        loginBox = new Rectangle();
        loginBox.setHeight(390);
        loginBox.setWidth(320);
        loginBox.setTranslateX(-175);
        loginBox.setFill(Color.web("#BDBABA"));

        login = new Text("Login");
        login.setTranslateX(-170);
        login.setTranslateY(-160);
        login.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        loginLine = new Rectangle();
        loginLine.setHeight(5);
        loginLine.setWidth(320);
        loginLine.setTranslateX(-175);
        loginLine.setTranslateY(-120);

        username = new Text("Username");
        username.setTranslateX(-245);
        username.setTranslateY(-75);

        usernameField = new TextField();
        usernameField.setMaxSize(211, 25);
        usernameField.setTranslateX(-170);
        usernameField.setTranslateY(-50);

        password = new Text("Password");
        password.setTranslateX(-245);

        passwordField = new TextField();
        passwordField.setMaxSize(211, 25);
        passwordField.setTranslateX(-170);
        passwordField.setTranslateY(25);

        loginButton = new Button("Login");
        loginButton.setTranslateX(-175);
        loginButton.setTranslateY(100);
        loginButton.setTextFill(Color.web("#FFFFFF"));
        loginButton.setStyle("-fx-background-color: #4433E2");



        createBox = new Rectangle();
        createBox.setHeight(390);
        createBox.setWidth(320);
        createBox.setTranslateX(175);
        createBox.setFill(Color.web("#BDBABA"));

        create = new Text("Create Account");
        create.setTranslateX(170);
        create.setTranslateY(-160);
        create.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        createLine = new Rectangle();
        createLine.setHeight(5);
        createLine.setWidth(320);
        createLine.setTranslateX(175);
        createLine.setTranslateY(-120);

        createName = new Text("Username");
        createName.setTranslateX(95);
        createName.setTranslateY(-75);

        createUsernameField = new TextField();
        createUsernameField.setMaxSize(211, 25);
        createUsernameField.setTranslateX(170);
        createUsernameField.setTranslateY(-50);

        createPassword = new Text("Password");
        createPassword.setTranslateX(95);

        createPasswordField = new TextField();
        createPasswordField.setMaxSize(211, 25);
        createPasswordField.setTranslateX(170);
        createPasswordField.setTranslateY(25);

        createButton = new Button("Create");
        createButton.setTranslateX(175);
        createButton.setTranslateY(100);
        createButton.setTextFill(Color.web("#FFFFFF"));
        createButton.setStyle("-fx-background-color: #4433E2");




        loginPane = new StackPane();
        loginPane.getChildren().addAll(welcome, loginBox, login, loginLine, username, usernameField, password, passwordField, loginButton,
                createBox, create, createLine, createName, createUsernameField, createPassword, createPasswordField, createButton);
        loginPane.setStyle("-fx-background-color: #BFE9F5");
        loginScene = new Scene(loginPane, 870 ,520);
    }
    public Scene getLoginScene() {
        return loginScene;
    }

    public Button getLoginButton() {
        return loginButton;
    }
    public Button getCreateButton() {
        return createButton;
    }

    public String getLoginName(){
        return usernameField.getText();
    }
    public String getLoginPassword(){
        return passwordField.getText();
    }

    public String getCreateName(){
        return createUsernameField.getText();
    }
    public String getCreatePassword(){
        return createPasswordField.getText();
    }

    public void loginNameError(){
        username.setText("Username Cannot Include Spaces");
        username.setTranslateX(-187);
    }
    public void loginPasswordError(){
        password.setText("Password Cannot Include Spaces");
        password.setTranslateX(-187);
    }
    public void loginError(){
        login.setText("\t\tLogin Error\nInvalid Username or Password");
        login.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
    }

    public void createNameError(){
        createName.setText("Name Cannot Include Spaces");
        createName.setTranslateX(198);
    }
    public void createPasswordError(){
        createPassword.setText("Password Cannot Include Spaces");
        createPassword.setTranslateX(187);
    }
    public void createError(){
        create.setText("\tCreate Error\nUsername already exists");
    }
}
