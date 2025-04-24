
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

	MainGameScreen gameScreen;
	HomePage homeScreen;
	SettingsPage settingsScreen;
	LoginScene login;

	TextField c1;
	Button b1;
	HashMap<String, Scene> sceneMap;
	HashMap<String, Integer> loggedInUsers;
	VBox clientBox;
	String name;
	Client clientConnection;
	String opp;

	HBox fields;

	ComboBox<String> listUsers;
	ListView<String> listItems;

	
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
							System.out.println("GOT A TEXT " + data.message);
							gameScreen.addChat(data.recipient + ": " + data.message);
//							listItems.getItems().add(data.recipient+": "+data.message);
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
								name = data.message.substring(0, data.message.indexOf(" "));
								homeScreen.updateText("Welcome to Connect Four, " + name + "!");
								primaryStage.setScene(sceneMap.get("Home"));
							}
							break;
						case LOGGEDIN:
							listUsers.getItems().add(data.message);
						case SERVERMESSAGE:
							if(data.message.equals("SERVER LOOKING")){
								homeScreen.updateText("SERVER IS LOOKING FOR A GAME");
							}else if(data.message.equals("GAME FOUND")){
								homeScreen.updateText("GAME FOUND");
								primaryStage.setScene(sceneMap.get("Game"));
								opp = data.recipient;
								gameScreen.setText(name + " Vs. " + opp);
							}

					}
			});
		});
//		MainGameScreen gameScreen = new MainGameScreen();
		homeScreen = new HomePage("NULL");
		settingsScreen = new SettingsPage("NULL");
		gameScreen = new MainGameScreen();
		login = new LoginScene();
		sceneMap = new HashMap<>();
		sceneMap.put("Login", login.getLoginScene());
		sceneMap.put("Home", homeScreen.getHomeScreen());
		sceneMap.put("Game", gameScreen.getGameScreen());
		sceneMap.put("Settings", settingsScreen.getSettingsScreen());


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





		//login screen buttons
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
//				primaryStage.setScene(homeScreen.getHomeScreen());
			}

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
//				primaryStage.setScene(homeScreen.getHomeScreen());
			}

		});

		//home screen buttons
		homeScreen.getLogout().setOnAction(e->{
			primaryStage.setScene(login.getLoginScene());
		});
		homeScreen.getNewGame().setOnAction(e->{
			//sends to client that this user is looking for a game
			clientConnection.send(new Message(name, 1));
//			primaryStage.setScene(gameScreen.getGameScreen());
		});
		homeScreen.getQuitGame().setOnAction(e->{
			javafx.application.Platform.exit();
		});
		homeScreen.getSettings().setOnAction(e->{
			primaryStage.setScene(settingsScreen.getSettingsScreen());
		});

		//settings screen buttons
		settingsScreen.getExitButton().setOnAction(e->{
			primaryStage.setScene(homeScreen.getHomeScreen());
		});

		//game screen buttons
		gameScreen.getExitGame().setOnAction( e->{
			primaryStage.setScene(homeScreen.getHomeScreen());
		});

		gameScreen.getSendButton().setOnAction(e->{
			String mess = gameScreen.getMessage();
			gameScreen.addChat(name + ": " + mess + "When clicked");
			clientConnection.send(new Message(opp, mess));
			System.err.println("OPP IS:  " + opp);
		});
		
	}

	


}
