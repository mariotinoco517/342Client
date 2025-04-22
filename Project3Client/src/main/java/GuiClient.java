
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
import jdk.tools.jmod.Main;

public class GuiClient extends Application{

//	MainGameScreen n = new MainGameScreen();
	
	TextField c1;
	Button b1;
	HashMap<String, Scene> sceneMap;
	HashMap<String, Integer> loggedInUsers;
	VBox clientBox;
	Client clientConnection;

	HBox fields;

	ComboBox<String> listUsers;
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
//							listUsers.getItems().add(data.code + "");
							listItems.getItems().add("Client number" + data.code + " has joined but isn't logged in!");
							break;
						case DISCONNECT:
//							listUsers.getItems().remove(data.code);
							listItems.getItems().add(data.code + " has disconnected!");
							break;
						case TEXT:
							System.out.println("GOT A TEXT");
							listItems.getItems().add(data.recipient+": "+data.message);
							break;
						case USERS:
							System.out.println("Trying to update ComboBox");
							//gets a hashmap of logged in clients and adds usernames to listUsers
							loggedInUsers = data.loggedInClient;
							for(String u: loggedInUsers.keySet()){
								if(!(listUsers.getItems().contains(u))){
									System.out.println(u);
									listUsers.getItems().add(u);
								}

							}
							break;
						case VALIDNAME:
							System.out.println("Getting validation");
							if(data.code == 404){
								System.err.println("USER NOT FOUND");
								login.loginError();
							}else if(data.code == 414){
								System.err.println("USER ALREADY EXISTS");
								login.createError();
							}else if(data.code == 1){
								System.out.println("VALID COMBO");
								primaryStage.setScene(sceneMap.get("Box"));
							}
							break;
						case LOGGEDIN:
							listUsers.getItems().add(data.message);
					}
			});
		});

		login = new LoginScene();
		sceneMap = new HashMap<>();
		sceneMap.put("Login", login.getLoginScene());


		//Start clients connection with the server
		clientConnection.start();


		listUsers = new ComboBox<String>();
		listUsers.getItems().add("Send to All");
		//sets dropbox default to -1
		listUsers.setValue("Send to All");
		listItems = new ListView<String>();

		
		c1 = new TextField();
		b1 = new Button("Send");
		fields = new HBox(listUsers,b1);
		b1.setOnAction(e->{clientConnection.send(new Message(c1.getText(), 1)); c1.clear();});

		clientBox = new VBox(10, c1,fields,listItems);
		clientBox.setStyle("-fx-background-color: blue;"+"-fx-font-family: 'serif';");



		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });


		sceneMap.put("Box", new Scene(clientBox, 400, 300));

		primaryStage.setScene(sceneMap.get("Login"));
		primaryStage.setTitle("Client");
		primaryStage.show();




		login.getLoginButton().setOnAction(e->{
			String username = login.getLoginName();
			String password = login.getLoginPassword();
			boolean valid = true;
			if(username.contains(" ") || password.isEmpty()){
				login.loginNameError();
				valid = false;
			}
			if(password.contains(" ") || password.isEmpty()){
				login.loginPasswordError();
				valid = false;
			}
			if(valid){
				clientConnection.send(new Message(username, password, 1));
			}
			//primaryStage.setScene(n.getGameScreen());
			//if you wanna see the game screen uncomment line above and comment the if statement above it
		});
		login.getCreateButton().setOnAction(e->{
			String username = login.getCreateName();
			String password = login.getCreatePassword();
			boolean valid = true;
			if(username.contains(" ") || password.isEmpty()){
				login.createNameError();
				valid = false;
			}
			if(password.contains(" ") || password.isEmpty()){
				login.createPasswordError();
				valid = false;
			}
			if(valid){
				clientConnection.send(new Message(username, password, 0));
			}
		});
//		n.getExitGame().setOnAction( e->{
//				//adding to go back to main menu
//		});
		
	}

	


}
