import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Objects;

public class RematchWait {
    Scene rematchWaitScreen;
    StackPane blurredBackground, rematchScreenPane;

    Text title, subTitle;

    public RematchWait(){
        StackPane root = new StackPane();

        makeBlurredBackground();
        makeScreen();

        root.getChildren().addAll(rematchScreenPane);
        rematchWaitScreen = new Scene(root, 875, 520);
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
        Rectangle rematchScreenBackground = new Rectangle(320, 340);
        rematchScreenBackground.setFill(Paint.valueOf("#BFE9F5"));

        title = new Text("Waiting For Rematch");
        title.setStyle("-fx-font : 20  Verdana");
        title.setTranslateY(-120);

        subTitle = new Text("Will begin game when accepted or return to main menu");
        subTitle.setStyle("-fx-font : 10  Verdana");
        subTitle.setTranslateY(-100);

        Image waitingIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("waiting.gif")));
        ImageView waitingGif = new ImageView(waitingIcon);
        waitingGif.setFitHeight(220);
        waitingGif.setFitWidth(220);
        waitingGif.setPreserveRatio(true);
        waitingGif.setSmooth(true);
        waitingGif.setCache(true);
        waitingGif.setTranslateY(40);

        rematchScreenPane = new StackPane();
        rematchScreenPane.setMaxSize(420, 220);
        rematchScreenPane.getChildren().addAll(blurredBackground, rematchScreenBackground, title, subTitle, waitingGif);

    }

    public Scene getRematchWaitScreen(){
        return rematchWaitScreen;
    }

}
