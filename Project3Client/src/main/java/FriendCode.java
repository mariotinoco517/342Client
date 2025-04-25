import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class FriendCode {
    Scene friendCodeScreen;
    StackPane blurredBackground, friendCodePane;
    Button exitButton;
    Text title;
    TextField userInput;


    public FriendCode(){
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #BFE9F5");

        makeBlurredBackground();
        makeButtons();
        makeFriendCodeScreen();


        root.getChildren().addAll(blurredBackground, exitButton, friendCodePane);

        friendCodeScreen = new Scene(root, 870, 520);
    }
    private void makeFriendCodeScreen(){
        Rectangle friendCodeStructure = new Rectangle(420, 220);
        friendCodeStructure.setFill(Paint.valueOf("#BFE9F5"));
        title = new Text("Enter Code:");
        title.setStyle("-fx-font : 15 Verdana");
        title.setTranslateY(-50);

        userInput = new TextField();
        userInput.setMaxSize(140, 10);

        friendCodePane = new StackPane();
        friendCodePane.setMaxSize(420,220);
        friendCodePane.getChildren().addAll(friendCodeStructure, title, userInput);
    }
    private void makeBlurredBackground(){
        HomePage backgroundPage = new HomePage("NULL");
        blurredBackground = backgroundPage.getHomePane();
        ColorAdjust adj = new ColorAdjust();
        GaussianBlur blur = new GaussianBlur(55);
        adj.setInput(blur);
        adj.setBrightness(-0.5);
        blurredBackground.setEffect(adj);
    }
    private void makeButtons(){
        //makes the button to exit the settings page
        exitButton = new Button();
        exitButton.setBackground(Background.fill(Color.TRANSPARENT));
        exitButton.setMinSize(870, 520);
    }

    public Scene getFriendCodeScreen(){
        return friendCodeScreen;
    }
    public Button getExitButton(){
        return exitButton;
    }
}
