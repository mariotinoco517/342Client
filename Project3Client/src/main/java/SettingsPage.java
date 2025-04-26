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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class SettingsPage {
    Scene settingsScene;
    StackPane blurredBackground, settingsFrame;
    Text title, profile, gameplay, usernameChange, setPFP, tokenColor, backgroundSettings;
    TextField nameChange;
    Rectangle settingsStructure, settingsLine;
    Button exitButton, saveButton, changePFP;

    Client clientConnection;
    String name;


    public SettingsPage(String name, Client c){
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #BFE9F5");

        makeButtons();
        makeBlurredBackground(name);
        makeSettingsFrame();

        clientConnection = c;

        root.getChildren().addAll(blurredBackground, exitButton, settingsFrame);

        settingsScene = new Scene(root, 870, 520);
    }
    private void makeBlurredBackground(String name){
        HomePage backgroundPage = new HomePage(name);
        blurredBackground = backgroundPage.getHomePane();
        ColorAdjust adj = new ColorAdjust();
        GaussianBlur blur = new GaussianBlur(55);
        adj.setInput(blur);
        adj.setBrightness(-0.5);
        blurredBackground.setEffect(adj);
    }
    private void makeSettingsFrame(){
        //sets up the frame for the settings on top of the blurred background
        settingsStructure = new Rectangle(390, 420);
        settingsStructure.setFill(Paint.valueOf("#BFE9F5"));

        //title in the rectangle
        title = new Text("Settings");
        title.setStyle("-fx-font: 24 Verdana;");
        title.setTranslateY(-150);

        settingsLine = new Rectangle();
        settingsLine.setHeight(5);
        settingsLine.setWidth(390);
        settingsLine.setTranslateY(-120);

        //makes settings profile title
        profile = new Text("Profile");
        profile.setStyle("-fx-font : 20 Verdana;");
        profile.setTranslateY(-90);

        //profile settings portion coded below
        usernameChange = new Text("Change Username:");
        usernameChange.setStyle("-fx-font : 15 Verdana;");
        usernameChange.setTranslateX(-100);
        usernameChange.setTranslateY(-40);
        nameChange = new TextField();
        nameChange.setTranslateX(70);
        nameChange.setTranslateY(-40);
        nameChange.setMaxSize(180, 10);

        nameChange.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.ENTER)){
                    clientConnection.send(new Message(nameChange.getText(), "", 0));
                    nameChange.clear();
                }
            }
        });

        setPFP = new Text("Set Profile Picture:");
        setPFP.setStyle("-fx-font : 15 Verdana;");
        setPFP.setTranslateX(-100);
        setPFP.setTranslateY(0);

        changePFP = new Button("Upload");
        changePFP.setStyle("-fx-font : 15 Verdana;");
        changePFP.setTranslateX(70);
        changePFP.setTranslateY(0);
        changePFP.setPrefSize(180, 10);


        //make gamplay settings title
        gameplay = new Text("Gameplay");
        gameplay.setStyle("-fx-font : 20 Verdana;");
        gameplay.setTranslateY(50);

        //Gameplay settings portion code below
        tokenColor = new Text("Token Color");
        tokenColor.setStyle("-fx-font : 15 Verdana;");
        tokenColor.setTranslateX(-100);
        tokenColor.setTranslateY(80);

        backgroundSettings = new Text("Background");
        backgroundSettings.setStyle("-fx-font : 15 Verdana;");
        backgroundSettings.setTranslateX(-100);
        backgroundSettings.setTranslateY(150);



        settingsFrame = new StackPane();
        settingsFrame.getChildren().addAll(settingsStructure, title, settingsLine, profile, usernameChange,
                nameChange, changePFP, setPFP, gameplay, tokenColor, backgroundSettings);
        settingsFrame.setMaxSize(390, 420);
    }
    private void makeButtons(){
        //makes the button to exit the settings page
        exitButton = new Button();
        exitButton.setBackground(Background.fill(Color.TRANSPARENT));
        exitButton.setMinSize(870, 520);


    }

    public Scene getSettingsScreen(){return settingsScene;}
    public Button getExitButton(){return exitButton;}
    public Button getSaveButton(){return saveButton;}

    public void updateName(String n){
        name = n;
    }
}
