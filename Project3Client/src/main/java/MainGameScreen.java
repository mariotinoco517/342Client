import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//if you want to see this game screen go to where the get login button is and read the comment there
public class MainGameScreen{
    private static final int COL = 7; //num of columns of the board
    private static final int ROW = 6; //num of rows of the board
    private static final int tileSize = 60; //size of the tiles which ultimately changes the size of the board
    GridPane gridPane = new GridPane();


    List<Rectangle> highlight;
    Scene gameScreen;
    TextField cBox;
    Button sendButton, exitGame;
    Text vsText;
    ListView<String> chatBox;
    int playerNum = 0;
    int colChosen = -1;
    String name;

    Client clientConnection;


    public MainGameScreen(Client connection, String user){
        clientConnection = connection;
        name = user;

        StackPane root = new StackPane();
        StackPane chat;
        Shape gridShape = makeGrid();
        chat = makeChatBox();
        exitGame = leaveGame();
        highlight = hoverEffect();
        vsText = new Text("");
        vsText.setStyle("-fx-font: 24 Verdana;");
        vsText.setTranslateY(-250);


        root.getChildren().addAll(vsText, gridShape, gridPane, chat, exitGame);
        root.getChildren().addAll(highlight);


        root.setStyle("-fx-background-color: #BFE9F5");
        gameScreen = new Scene(root, 870, 520);
    }
    private StackPane makeChatBox(){
        StackPane chatArea = new StackPane();

        //creates the initial area of the chat
//        Rectangle chatBox = new Rectangle(150, 400);
        chatBox = new ListView<String>();
        chatBox.setTranslateX(350);
        chatBox.setTranslateY(-20);
//        chatBox.setFill(Paint.valueOf("CDCDCD"));
        chatBox.setMaxSize(150, 350);
//        chatBox.setArcWidth(20);
//        chatBox.setArcHeight(20);

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
        Image sendIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("sendBetter.png")));
        ImageView sendImage = new ImageView(sendIcon);
        sendImage.setFitHeight(16);
        sendImage.setFitWidth(16);
        sendImage.setPreserveRatio(true);

        //for hover effect purposes
        Rectangle tmp = new Rectangle(10, 10);
        tmp.setArcWidth(5);
        tmp.setArcHeight(5);

        //creates a new button which holds the send image in
        sendButton = new Button();
        sendButton.setShape(tmp);
        sendButton.setMaxSize(10, 10);
        sendButton.setTranslateY(180);
        sendButton.setTranslateX(400);
        sendButton.setGraphic(sendImage);
        sendButton.setBackground(Background.fill(Color.rgb(162, 162, 162)));

        //hover effects for the button
        sendButton.setOnMouseEntered(e->{
            sendButton.setBackground(Background.fill(Color.rgb(216, 216, 216, 0.5)));
        });
        sendButton.setOnMouseExited(e->{
            sendButton.setBackground(Background.fill(Color.rgb(162, 162, 162)));
        });

        chatArea.getChildren().addAll(chatBox, cBox, sendButton);
        return chatArea;
    }
    private Shape makeGrid(){
        Shape shape = new Rectangle((COL+1) * tileSize, (ROW+1) * tileSize);
        for(int y = 0; y < ROW; ++y) {
            for (int x = 0; x < COL; ++x) {
                Circle circle = new Circle(tileSize / 2); //defines the size of the circles
                circle.setFill(Paint.valueOf("#BFE9F5"));

                gridPane.setConstraints(circle, x, y);
                gridPane.setHgap(5);
                gridPane.setVgap(5);
                gridPane.setTranslateX(208);
                gridPane.setTranslateY(68);
                gridPane.getChildren().add(circle);
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
        for(int y = 0; y < COL; ++y){
            final int colNum = y;
            Rectangle addToCol = new Rectangle(tileSize, (ROW+1) * tileSize);
            addToCol.setTranslateX((colNum * (tileSize+5)+ tileSize/4) - 210);
            addToCol.setFill(Color.TRANSPARENT);
            addToCol.setOnMouseEntered(e-> addToCol.setFill(Color.rgb(253, 251, 124, 0.5)));
            addToCol.setOnMouseExited(e-> addToCol.setFill(Color.TRANSPARENT));
            addToCol.setOnMouseClicked(e-> {
                clientConnection.send(new Message(name, colNum));
            });

            theList.add(addToCol);
        }
        return theList;
    }

    public void placeToken(int colNum, int p){
        //check with server
        //recieve x value on where to be placed by server
        int x = 5;
        Circle circle = (Circle) gridPane.getChildren().get((x*7)+colNum);
        while(!(circle.getFill().equals((Paint.valueOf("#BFE9F5")))) && x > 0){
            x -= 1;
            circle = (Circle) gridPane.getChildren().get((x*7)+colNum);
        }
        if(p == 0){
            circle.setFill(Color.RED);
        }
        else{
            circle.setFill(Color.YELLOW);
        }
    }

    public Scene getGameScreen(){return gameScreen;}

    public String getMessage(){String mess = cBox.getText(); cBox.clear(); return mess;}

    public Button getExitGame(){return exitGame;}

    public Button getSendButton(){return sendButton;}

    public TextField getEnterMessage(){return cBox;}

    public void setText(String t){
        vsText.setText(t);
    }

    public void addChat(String chat){
        chatBox.getItems().add(chat);
    }

    public void clearChat(){
        chatBox.getItems().clear();
    }

    public void clearGrid(){
        for(int y = 0; y < COL; ++y){
            int x = 5;
            Circle circle = (Circle) gridPane.getChildren().get((x*7)+y);
            while(!(circle.getFill().equals((Paint.valueOf("#BFE9F5")))) && x >= 0){
                x--;
                circle.setFill(Paint.valueOf("#BFE9F5"));
                circle = (Circle) gridPane.getChildren().get((x*7)+y);
            }
        }

    }


}
