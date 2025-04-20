
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiClient extends Application{

	
	TextField c1;
	Button b1;
	HashMap<String, Scene> sceneMap;
	VBox clientBox;
	Client clientConnection;

	HBox fields;

	ComboBox<Integer> listUsers;
	ListView<String> listItems;

	LoginScene login;
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		clientConnection = new Client(data->{
				Platform.runLater(()->{
					switch (data.type){
						case NEWUSER:
							listUsers.getItems().add(data.recipient);
							listItems.getItems().add(data.recipient + " has joined!");
							break;
						case DISCONNECT:
							listUsers.getItems().remove(data.recipient);
							listItems.getItems().add(data.recipient + " has disconnected!");
							break;
						case TEXT:
							listItems.getItems().add(data.recipient+": "+data.message);
							break;
						case USERS:
							System.out.println("Trying to update ComboBox");
							ArrayList<Integer> temp = data.users;
							ComboBox<Integer> tempArr = new ComboBox<>();
//							listUsers = new ComboBox<>();
							for(Integer u: temp){
//								System.out.println(u);
//								tempArr.getItems().add(u);
								listUsers.getItems().add(u);
							}
//							listUsers = tempArr;
							break;


					}
			});
		});

		login = new LoginScene();
		sceneMap = new HashMap<>();
		sceneMap.put("Login", login.getLoginScene());


		//Start clients connection with the server
		clientConnection.start();


		listUsers = new ComboBox<Integer>();
		listUsers.getItems().add(-1);
		//sets dropbox default to -1
		listUsers.setValue(-1);
		listItems = new ListView<String>();

		
		c1 = new TextField();
		b1 = new Button("Send");
		fields = new HBox(listUsers,b1);
		b1.setOnAction(e->{clientConnection.send(new Message(listUsers.getValue(), c1.getText())); c1.clear();});

		clientBox = new VBox(10, c1,fields,listItems);
		clientBox.setStyle("-fx-background-color: blue;"+"-fx-font-family: 'serif';");



		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });


		primaryStage.setScene(new Scene(clientBox, 400, 300));
		primaryStage.setTitle("Client");
		primaryStage.show();

		primaryStage.setScene(sceneMap.get("Login"));
		
	}
	


}
