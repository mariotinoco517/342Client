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

public class YouRematch {
    Scene rematchScreen;
    StackPane blurredBackground, rematchScreenPane;
    Button rematch, returnMenu, exitButton;

    Text title;

    public YouRematch(){
        StackPane root = new StackPane();

        makeBlurredBackground();
        makeScreen();
        makeButtons();

        root.getChildren().addAll(rematchScreenPane, rematch, returnMenu);
        rematchScreen = new Scene(root, 875, 520);
    }

    public void makeBlurredBackground(){
        //makes the blurred background
        HomePage backgroundPage = new HomePage("NULL");
        blurredBackground = backgroundPage.getHomePane();
        ColorAdjust adj = new ColorAdjust();
        GaussianBlur blur = new GaussianBlur(55);
        adj.setInput(blur);
        adj.setBrightness(-0.5);
        blurredBackground.setEffect(adj);
    }

    public void makeScreen(){
        //makes the background for the code input
        Rectangle rematchScreenBackground = new Rectangle(420, 220);
        rematchScreenBackground.setFill(Paint.valueOf("#BFE9F5"));

        title = new Text("You[Insert if won]");
        title.setStyle("-fx-font : 20  Verdana");
        title.setTranslateY(-30);

        rematchScreenPane = new StackPane();
        rematchScreenPane.setMaxSize(420, 220);
        rematchScreenPane.getChildren().addAll(blurredBackground, rematchScreenBackground, title);

    }

    public void makeButtons(){
        rematch = new Button("Rematch");
        rematch.setStyle("-fx-font : 10  Verdana");
        rematch.setMinSize(90, 20);
        rematch.setTranslateY(10);

        returnMenu = new Button("Return to Lobby");
        returnMenu.setStyle("-fx-font : 10  Verdana");
        returnMenu.setMinSize(90, 20);
        returnMenu.setTranslateY(40);
    }

    public Scene getRematchScreen(){
        return rematchScreen;
    }

    public Button getReturnMenu(){
        return returnMenu;
    }

    public Button getRematchButton(){
        return rematch;
    }

    public void updateText(int result){
        if(result == -1){
            title.setText("You Lost :(");
        }else if(result == 0){
            title.setText("You Drew :|");
        }else if(result == 1){
            title.setText("You Won :D");
        }
    }
}
