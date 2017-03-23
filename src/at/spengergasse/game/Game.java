package at.spengergasse.game;

import at.spengergasse.player.Player;
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

public class Game extends Application {

	// The Map
	private TileMap tileMap;

	// The Player
	private Player player;

	// The Windows
	private static Group group;

	// The Scenes
	private Scene sc;

	// Needed for smooth movement
	private double smooth = 0.0;

	// The counter for the player animation
	private double targetFrameCounter = 0;

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Drawing the game map
		tileMap.draw(new ImageView());
		// The game loop
		Timeline gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		
		sc.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT){
					tileMap.setRight(true);
				}
				if(event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT){
					tileMap.setLeft(true);
				}
				if(event.getCode() == KeyCode.SPACE){
					player.setJumping(true);
				}
			}
		});
		sc.setOnKeyReleased(new EventHandler<KeyEvent>() { // Releasing
															// key

			@Override
			public void handle(KeyEvent event) {	
				if(event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT){
					player.setStanding(true);
					tileMap.setRight(false); // Don't move anymore
					player.resetCounter();
				}
				if(event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT){
					player.setStanding(true);
					tileMap.setLeft(false); // Don't move anymore
					player.resetCounter();
				}
				
			}
		});

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
				tileMap.update();

				// Drawing the player the last
				player.drawPlayer(new ImageView(), (int) targetFrameCounter);
			}

			private void update() {
				// Moving to the left or the right
				if (tileMap.getRight() == true && tileMap.getLeft() == false && tileMap.getX() < ((tileMap.getLength()-19)*48)) {
					player.moveRight();
					smoothOutMovement(1.0);
					tileMap.right(smooth); // Moving 5.0 Pixels to the right
				} else
				// Nothing

				if (tileMap.getLeft() == true && tileMap.getRight() == false && tileMap.getX() > 48) {
					player.moveLeft();
					smoothOutMovement(1.0);
					tileMap.left(smooth);
				} else
					player.setStanding(true);
				
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
	}

	/**
	 * Smooth Movement
	 * 
	 */
	public void smoothOutMovement(double inc) {
		if (smooth < 4.9) {
			smooth += inc;
		}
		if (smooth > 4.9) {
			smooth = 5;
		}
	}

	public void setSmooth(double value) {
		this.smooth = value;
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

	public void setCounter(double counter) {
		this.targetFrameCounter = counter;
	}
}
