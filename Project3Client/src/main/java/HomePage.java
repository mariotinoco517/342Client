import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;

public class HomePage {
    Scene homeScreen;
    StackPane root;
    Button newGame, addFriend, playFriend, playRandom, logout, quitGame, settings;
    Text title;

    public HomePage(String name){
        root = new StackPane();
        root.setStyle("-fx-background-color: #BFE9F5");

        //sets up the title for the home page
        title = new Text("Welcome to Connect Four, " + name + "!");
        title.setStyle("-fx-font: 24 Verdana;");
        title.setTranslateY(-200);

        //sets up the buttons on the home page
        setButtons();

        Image connect4Icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("C4Main.gif")));
        ImageView connect4Gif = new ImageView(connect4Icon);
        connect4Gif.setFitHeight(750);
        connect4Gif.setFitWidth(550);
        connect4Gif.setPreserveRatio(true);
        connect4Gif.setSmooth(true);
        connect4Gif.setCache(true);

        root.getChildren().addAll(newGame, addFriend, playFriend, playRandom, logout, quitGame, settings, title, connect4Gif);

        homeScreen = new Scene(root, 870, 520);
    }

    private void setButtons(){
        //new game button adjustments
        newGame = new Button("New Game");
        newGame.setMaxSize(100, 20);
        newGame.setFont(Font.font("Verdana"));
        newGame.setTranslateX(-350);
        newGame.setTranslateY(-150);

        //add friend button adjustments
        addFriend = new Button("Add Friend");
        addFriend.setMaxSize(100, 20);
        addFriend.setFont(Font.font("Verdana"));
        addFriend.setTranslateX(370);
        addFriend.setTranslateY(-150);

        //play friend button adjustments
        playFriend = new Button("Play a Friend");
        playFriend.setMaxSize(100, 20);
        playFriend.setFont(Font.font("Verdana"));
        playFriend.setTranslateX(-350);
        playFriend.setTranslateY(-100);

        //play random button adjustments
        playRandom = new Button("Play Random");
        playRandom.setMaxSize(100, 20);
        playRandom.setFont(Font.font("Verdana"));
        playRandom.setTranslateX(-350);
        playRandom.setTranslateY(-50);

        //logout button adjustments
        logout = new Button("Logout");
        logout.setMaxSize(100, 20);
        logout.setFont(Font.font("Verdana"));
        logout.setTranslateX(-350);
        logout.setTranslateY(180);

        //quit game button adjustments
        quitGame = new Button("Quit Game");
        quitGame.setMaxSize(100, 20);
        quitGame.setFont(Font.font("Verdana"));
        quitGame.setTextFill(Paint.valueOf("#F70000"));
        quitGame.setTranslateX(-350);
        quitGame.setTranslateY(220);

        //settings button adjustments
        settings = new Button("Settings");
        settings.setMaxSize(100, 20);
        settings.setFont(Font.font("Verdana"));
        settings.setTranslateX(370);
        settings.setTranslateY(220);
    }

    public StackPane getHomePane(){return root;}
    public Scene getHomeScreen(){return homeScreen;}
    public Button getNewGame(){return newGame;}
    public Button getPlayRandom(){return playRandom;}
    public Button getPlayFriend(){return playFriend;}
    public Button getLogout(){return logout;}
    public Button getQuitGame(){return quitGame;}
    public Button getAddFriend(){return addFriend;}
    public Button getSettings(){return settings;}

    public void updateText(String t){
        title.setText(t);
    }
}
