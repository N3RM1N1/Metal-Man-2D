package at.spengergasse.game;

import java.io.File;

import at.spengergasse.player.Player;
import at.spengergasse.soundEffects.Sound;
import javafx.animation.KeyFrame; // F�r den Gameloop
import javafx.animation.Timeline; // F�r den Gameloop (konstante 60 FPS, ...)
import javafx.application.Application; // Das ganze Programm
import javafx.scene.Group; // Das eigentliche Geschehen
import javafx.scene.Scene; // Das Fenster
import javafx.stage.Stage;
import javafx.util.Duration; // Zeitangabe
import javafx.scene.paint.Color; // Farben
import javafx.scene.image.ImageView; // Das Bild, das gezeigt wird
import javafx.event.ActionEvent; // Es passiert etwas (rendern, update, ...)
import javafx.event.EventHandler; // F�r Key - Abfragen ben�tigt (Tastatur)
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent; // Pr�ft welche Taste gedr�ckt wurde
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Game extends Application {

	// The Map
	private TileMap tileMap;

	// The Player
	private Player player;
	private MediaPlayer mediaPlayer;
	private Sound soundEffects;

	// The Windows
	private static Group group;

	// The Scenes
	private static Scene sc;

	// The counter for the player animation
	private int targetFrameCounter = 0;

	@Override
	public void start(Stage primaryStage) throws Exception {

		// The game loop
		Timeline gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);

		sc.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
					tileMap.setRight(true);
					player.setRight(true);
				}
				if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
					tileMap.setLeft(true);
					player.setLeft(true);
				}
				if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
					if(player.isJumping() == false && player.isFighting() == false) {
						mediaPlayer = new MediaPlayer(soundEffects.playJumpSound());
						mediaPlayer.setVolume(0.3);
						mediaPlayer.play();
					}
					player.setJumping(true);
					
				}
				if(event.getCode() == KeyCode.E) {
					player.setFighting(true);
				}
			}
		});
		sc.setOnKeyReleased(new EventHandler<KeyEvent>() { // Releasing
															// key

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
					player.setRight(false);
					player.setStanding(true);
					tileMap.setRight(false); // Don't move anymore
					tileMap.resetMovement();
					player.resetCounter();
				}
				if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
					player.setLeft(false);
					player.setStanding(true);
					tileMap.setLeft(false); // Don't move anymore
					tileMap.resetMovement();
					player.resetCounter();
				}
			}
		});
		MediaPlayer gameTrack = new MediaPlayer(new Media(new File("src/at/spengergasse/resources/game/soundEffects/Game_Sound_Track.wav").toURI().toString()));
		

		KeyFrame kf = new KeyFrame(Duration.seconds(1.0 / 60.0), new EventHandler<ActionEvent>() {
			

			@Override
			public void handle(ActionEvent event) {

				update();

				render();
				
			}

			private void render() {
				// Clear the scene
				group.getChildren().clear();

				// Update the map
				tileMap.draw(new ImageView());

				// Drawing the player the last
				player.draw(new ImageView());
			}

			private void update() {
				player.update();
				tileMap.update();
				
				gameTrack.setCycleCount(Timeline.INDEFINITE);
				gameTrack.play();
				
				targetFrameCounter++;

				player.checkCounter(targetFrameCounter);
			}
			
		});

		gameLoop.getKeyFrames().add(kf);
		gameLoop.play();

		// The generated scene is represented here
		primaryStage.setScene(sc);
		primaryStage.setTitle("Metal Man 2D");
		primaryStage.setResizable(true);
		primaryStage.setFullScreen(false);
		primaryStage.show();
	}

	/**
	 * 
	 * Initialization
	 * 
	 */
	public void init() {

		// Creating the group for the tiles
		group = new Group();

		// The background color, kind of a dark green
		Color background = new Color(0.165, 0.165, 0.165, 1);

		// Generating a new window
		sc = new Scene(group, 960, 720, background); // 960 720 48

		// Creating a new TileMap Object for reading and drawing the game map
		tileMap = new TileMap(48);

		// Creating the player
		player = new Player();

		soundEffects = new Sound();

		mediaPlayer = new MediaPlayer(soundEffects.playJumpSound());

	}

	
	// ------------------------------------------------------------------------------------------------------------------------------------------
	// Main

	/**
	 * Launching the Application
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		launch(args);
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------
	// GET / SET - Methods

	public Group getGroup() {
		return group;
	}

	public double getCounter() {
		return targetFrameCounter;
	}
	
	public static Scene getSc() {
		return sc;
	}
}
