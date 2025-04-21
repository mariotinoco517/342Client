import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//if you want to see this game screen go to where the get login button is and read the comment there
public class MainGameScreen{
    private static final int COL = 7; //num of columns of the board
    private static final int ROW = 6; //num of rows of the board
    private static final int tileSize = 60; //size of the tiles which ultimately changes the size of the board

    List<Rectangle> highlight;
    Scene gameScreen;
    TextField cBox;
    Button sendButton, exitGame;


    public MainGameScreen(){
        StackPane root = new StackPane();
        StackPane chat = new StackPane();
        Shape gridShape = makeGrid();
        chat = makeChatBox();
        exitGame = leaveGame();
        highlight = hoverEffect();


        root.getChildren().addAll(gridShape, chat, exitGame);
        root.getChildren().addAll(highlight);


        root.setStyle("-fx-background-color: #BFE9F5");
        gameScreen = new Scene(root, 870, 520);
    }
    private StackPane makeChatBox(){
        StackPane chatArea = new StackPane();

        //creates the initial area of the chat
        Rectangle chatBox = new Rectangle(150, 400);
        chatBox.setTranslateX(350);
        chatBox.setFill(Paint.valueOf("CDCDCD"));
        chatBox.setArcWidth(20);
        chatBox.setArcHeight(20);

        //creates the text field to send the message in
        cBox = new TextField();
        cBox.setTranslateX(350);
        cBox.setTranslateY(180);
        Rectangle textFieldShape = new Rectangle(130, 20);
        textFieldShape.setArcHeight(20);
        textFieldShape.setArcWidth(20);
        cBox.setShape(textFieldShape);
        cBox.setMaxSize(140, 20);
        cBox.setBackground(Background.fill(Paint.valueOf("#A2A2A2")));

        //sets up send button in case user can't hit send
        Image sendIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("sendButton.png")));
        ImageView sendImage = new ImageView(sendIcon);
        sendImage.setFitHeight(50);
        sendImage.setFitWidth(50);
        sendImage.setPreserveRatio(true);
        sendImage.setSmooth(true);
        sendImage.setCache(true);
        sendImage.setTranslateY(180);
        sendImage.setTranslateX(406);

        sendButton = new Button();
        sendButton.setGraphic(sendImage);


        chatArea.getChildren().addAll(chatBox, cBox, sendImage);
        return chatArea;
    }
    private Shape makeGrid(){
        Shape shape = new Rectangle((COL+1) * tileSize, (ROW+1) * tileSize);
        for(int y = 0; y < ROW; ++y) {
            for (int x = 0; x < COL; ++x) {
                Circle circle = new Circle(tileSize / 2); //defines the size of the circles
                circle.setCenterX(tileSize / 2); //centers every circle for an easier translation
                circle.setCenterY(tileSize / 2);
                circle.setTranslateX(x * (tileSize + 5) + tileSize/4); //divide by 4 in order to match up to the board
                circle.setTranslateY(y * (tileSize + 5) + tileSize/4); //according to the tile size

                shape = shape.subtract(shape, circle);
            }
        }
        shape.setFill(Color.BLUE);
        return shape;
    }
    private Button leaveGame(){
        Button tmp = new Button("Leave Game");
        tmp.setTextFill(Paint.valueOf("#F70000"));
        tmp.setFont(Font.font("Verdana"));
        tmp.setTranslateX(350);
        tmp.setTranslateY(230);
        return tmp;
    }
    private List<Rectangle> hoverEffect(){
        List<Rectangle> theList = new ArrayList<>();
        for(int x = 0; x < COL; ++x){
            int colNum = x;
            Rectangle addToCol = new Rectangle(tileSize, (ROW+1) * tileSize);
            addToCol.setTranslateX((x * (tileSize+5)+ tileSize/4) - 210);
            addToCol.setFill(Color.TRANSPARENT);

            addToCol.setOnMouseEntered(e-> addToCol.setFill(Color.rgb(253, 251, 124, 0.5)));
            addToCol.setOnMouseExited(e-> addToCol.setFill(Color.TRANSPARENT));
            //addToCol.setOnMouseClicked(e-> {});
            theList.add(addToCol);
        }
        return theList;
    }

    public Scene getGameScreen(){return gameScreen;}

    public String getMessage(){return cBox.getText();}

    public Button getExitGame(){return exitGame;}


}
