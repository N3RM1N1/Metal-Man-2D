package at.spengergasse.gui;

import javafx.application.Application;

import javafx.stage.Stage;

public class ApplicationFX extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception {
		
		new GameLauncher();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
