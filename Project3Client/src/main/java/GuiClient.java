
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
	FriendCode friendCode = new FriendCode();
	GameSettings gameSettings = new GameSettings();
	LoginScene login;
	YouRematch rematch = new YouRematch();
	RematchWait rematchWait = new RematchWait();

	TextField c1;
	Button b1;
	HashMap<String, Scene> sceneMap;
	HashMap<String, Integer> loggedInUsers;
	VBox clientBox;
	String name;
	Client clientConnection;
	String opp;
	int wins;
	int loses;
	boolean waiting;
	boolean oppWaiting;

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
							primaryStage.setScene(homeScreen.getHomeScreen());
							name = data.message.substring(0, data.message.indexOf(" "));
							settingsScreen.updateName(name);
							wins = data.wl[1];
							loses = data.wl[0];
							homeScreen.updateText("Welcome to Connect Four, " + name + "!\n" + wins + " wins and " + loses + " loses");
//							primaryStage.setScene(homeScreen.getHomeScreen());
						}
						break;
					case LOGGEDIN:
						listUsers.getItems().add(data.message);
						break;
					case SERVERMESSAGE:
						if(data.message.equals("SERVER LOOKING")){
							homeScreen.updateText("SERVER IS LOOKING FOR A GAME");
						}else if(data.message.equals("GAME FOUND")){
							homeScreen.updateText("GAME FOUND");
							primaryStage.setScene(sceneMap.get("Game"));
							opp = data.recipient;
							gameScreen.setText(name + " Vs. " + opp);
						}else if(data.message.equals("WINNER")){
							wins++;
							gameScreen.clearGrid();
							gameScreen.clearChat();
							homeScreen.updateText(wins + " W:" + loses + "L");
							primaryStage.setScene(sceneMap.get("Rematch"));
							rematch.updateText(1);
						}else if(data.message.equals("LOSER")){
							loses++;
							gameScreen.clearGrid();
							gameScreen.clearChat();
							homeScreen.updateText(wins + " W:" + loses + "L");
							primaryStage.setScene(sceneMap.get("Rematch"));
							rematch.updateText(-1);
						}else if(data.message.equals("REMATCH")){
							//opponent didn't rematch
							if(data.code == 0){
								System.out.println("OPP didnt rematch");
								primaryStage.setScene(sceneMap.get("Home"));
								homeScreen.updateText("Opponent didn't rematch");
								waiting = false;
								oppWaiting = false;
							}else{
								oppWaiting = true;
								if(waiting){
									primaryStage.setScene(sceneMap.get("Game"));
									waiting = false;
									oppWaiting = false;
								}
							}
						}
						break;
					case PLAYERMOVE:
						if(data.code == 0 && data.message.equals(name)){
							System.err.println("Invalid Move at Column: " + data.move);
						}else if(data.code == 1){
							if(data.message.equals(name)){
								System.out.println("Valid Move at Column: " + data.move);
								gameScreen.placeToken(data.move, 0);
							}else{
								gameScreen.placeToken(data.move, 1);
							}

						}else if(data.code == 2){
							if(data.message.equals(name)){
								System.out.println("WINNER!!!");
								gameScreen.clearGrid();
								primaryStage.setScene(sceneMap.get("Home"));
							}
						}
						break;
					case CHANGENAME:
						name = data.message;
						settingsScreen.updateName(name);
						homeScreen.updateText("Welcome to Connect Four, " + name + "!\n" + wins + " wins and " + loses + " loses");
						break;
//					case REMATCH:
//						primaryStage.setScene(sceneMap.get("Game"));
//						opp = data.recipient;
//						gameScreen.setText(name + " Vs. " + opp);
				}
			});
		});
//		MainGameScreen gameScreen = new MainGameScreen();
		homeScreen = new HomePage("NULL");
		settingsScreen = new SettingsPage("NULL", clientConnection);
		gameScreen = new MainGameScreen(clientConnection, name);
		login = new LoginScene();
		sceneMap = new HashMap<>();
		sceneMap.put("Login", login.getLoginScene());
		sceneMap.put("Home", homeScreen.getHomeScreen());
		sceneMap.put("Game", gameScreen.getGameScreen());
		sceneMap.put("Settings", settingsScreen.getSettingsScreen());
		sceneMap.put("Rematch", rematch.getRematchScreen());


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


//		sceneMap.put("Box", new Scene(clientBox, 400, 300));

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
//			clientConnection.send(new Message(name, -1));
			primaryStage.setScene(gameSettings.gameSettingsScene);
		});
		homeScreen.getPlayRandom().setOnAction(e->{
			clientConnection.send(new Message(name, -1));
		});
		homeScreen.getQuitGame().setOnAction(e->{
			javafx.application.Platform.exit();
		});
		homeScreen.getSettings().setOnAction(e->{
			primaryStage.setScene(settingsScreen.getSettingsScreen());
		});
		homeScreen.getPlayFriend().setOnAction(e->{
			primaryStage.setScene(friendCode.getFriendCodeScreen());
		});

		//settings screen buttons
		settingsScreen.getExitButton().setOnAction(e->{
			primaryStage.setScene(homeScreen.getHomeScreen());
		});

		//game screen buttons
		gameScreen.getExitGame().setOnAction( e->{
			clientConnection.send(new Message(opp, "EXIT GAME"));
//			primaryStage.setScene(homeScreen.getHomeScreen());
		});
		gameScreen.getSendButton().setOnAction(e->{
			String mess = gameScreen.getMessage();
			gameScreen.addChat(name + ": " + mess);
			clientConnection.send(new Message(opp, mess));
			System.err.println("OPP IS:  " + opp);
		});

		//friend code screen buttons
		friendCode.getExitButton().setOnAction(e->{
			primaryStage.setScene(homeScreen.getHomeScreen());
		});

		//game settings screen buttons
		gameSettings.getExitButton().setOnAction(e->{
			primaryStage.setScene(homeScreen.getHomeScreen());
		});
		gameSettings.getEnterCode().setOnAction(e->{
			primaryStage.setScene(gameScreen.getGameScreen());
		});

		rematch.getRematchButton().setOnAction(e->{
			if(oppWaiting){
				primaryStage.setScene(gameScreen.getGameScreen());
				clientConnection.send(new Message(1));
				oppWaiting = false;
			}else{
				primaryStage.setScene(rematchWait.getRematchWaitScreen());
				clientConnection.send(new Message(1));
				waiting = true;
			}

		});
		rematch.getReturnMenu().setOnAction(e->{
			primaryStage.setScene(homeScreen.getHomeScreen());
			clientConnection.send(new Message(0));
			waiting = false;
		});

	}




}
