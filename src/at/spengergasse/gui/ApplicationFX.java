package at.spengergasse.gui;

import java.util.List;

import javafx.application.Application;
import javafx.scene.image.*;

import javafx.stage.Stage;

public class ApplicationFX extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception {
		final Parameters param = getParameters();
		final List<String> parameters = param.getRaw();
		for (String s : parameters) {
			System.out.println(s);
		}
		
		new FrameFX(parameters);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
