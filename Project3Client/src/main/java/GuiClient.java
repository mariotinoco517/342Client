
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
							for(Integer u: temp){
								listUsers.getItems().add(u);
							}
							break;
						case VALIDNAME:
							System.out.println("Getting validation");
							if(data.recipient == 404){
								System.err.println("USER NOT FOUND");
								login.loginError();
							}else if(data.recipient == 414){
								System.err.println("USER ALREADY EXISTS");
								login.createError();
							}else if(data.recipient == 1){
								System.out.println("VALID COMBO");
								primaryStage.setScene(sceneMap.get("Box"));
							}
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
		
	}

	


}
