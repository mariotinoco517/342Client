import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameSettings {
    Scene gameSettingsScene;
    StackPane blurredBackground, gameSettingsPane;

    Text title, profanity, friends, settings;
    TextField code;
    Button exitButton;
    CheckBox friendsOnly, profanityFilter;

    public GameSettings(){
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #BFE9F5");

        makeButtons();
        makeBackground();

        root.getChildren().addAll(gameSettingsPane, friendsOnly, profanityFilter);
        gameSettingsScene = new Scene(root, 870, 520);
    }
    public void makeBackground(){
        //makes the blurred background
        HomePage backgroundPage = new HomePage("NULL");
        blurredBackground = backgroundPage.getHomePane();
        ColorAdjust adj = new ColorAdjust();
        GaussianBlur blur = new GaussianBlur(55);
        adj.setInput(blur);
        adj.setBrightness(-0.5);
        blurredBackground.setEffect(adj);

        //makes the background for the code input
        Rectangle gameSettingsBackground = new Rectangle(420, 220);
        gameSettingsBackground.setFill(Paint.valueOf("#BFE9F5"));

        title = new Text("Code:");
        title.setStyle("-fx-font : 20 Verdana");
        title.setTranslateY(40);

        settings = new Text("Settings");
        settings.setStyle("-fx-font : 20 Verdana");
        settings.setTranslateY(-80);

        profanity = new Text("Profanity Filter:");
        profanity.setStyle("-fx-font : 15 Verdana");
        profanity.setTranslateY(-40);
        profanity.setTranslateX(-40);


        friends = new Text("Friends Only:");
        friends.setStyle("-fx-font : 15 Verdana");
        friends.setTranslateY(-20);
        friends.setTranslateX(-40);

        code = new TextField();
        code.setMaxSize(140, 10);
        code.setTranslateY(70);

        gameSettingsPane = new StackPane();
        gameSettingsPane.setMaxSize(420, 220);
        gameSettingsPane.getChildren().addAll(blurredBackground, exitButton, gameSettingsBackground, title, settings, profanity, friends, code);
    }
    public void makeButtons(){
        exitButton = new Button();
        exitButton.setBackground(Background.fill(Color.TRANSPARENT));
        exitButton.setMinSize(870, 520);

        //makes shape for toggle buttons
        Rectangle theShape = new Rectangle(80, 8);
        theShape.setArcWidth(20);
        theShape.setArcHeight(20);

        friendsOnly = new CheckBox();
        friendsOnly.setMaxSize(10, 10);
        friendsOnly.setTranslateY(-20);
        friendsOnly.setTranslateX(60);

        profanityFilter = new CheckBox();
        profanityFilter.setMaxSize(10, 10);
        profanityFilter.setTranslateY(-40);
        profanityFilter.setTranslateX(60);
    }

    public Scene getGameSettingsScreen(){return gameSettingsScene;}
    public Button getExitButton(){return exitButton;}
    public TextField getEnterCode(){return code;}

}
