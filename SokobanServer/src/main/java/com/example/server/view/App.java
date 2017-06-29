package com.example.server.view;

import com.example.SokobanServer.SokobanClientHandler;
import com.example.SokobanServer.Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *  The main class - running the server and load gui 
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
        Server server = new Server(5555, new SokobanClientHandler());
        try {
        	new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						server.runServer();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
        	}).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
        
        Scene scene = new Scene(root, 400,400);
    
        stage.setTitle("Admin Dashboard");
        stage.setScene(scene);
        stage.show();
	}
}
