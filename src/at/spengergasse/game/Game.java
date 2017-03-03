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
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
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
	private double smooth;

	// The counter for the player animation
	private int targetFrameCounter = 0;
	private int counter;

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Drawing the game map
		try {
			tileMap.draw(new ImageView());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// The game loop
		Timeline gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);

		KeyFrame kf = new KeyFrame(Duration.seconds(0.016), new EventHandler<ActionEvent>() { // 0.016
																								// ->
																								// 60
																								// FPS

			@Override
			public void handle(ActionEvent event) {

				group.getChildren().clear();

				sc.setOnKeyPressed(new EventHandler<KeyEvent>() {

					@Override
					public void handle(KeyEvent event) {
						switch (event.getCode()) {
						case E:
							tileMap.setRight(true);
							player.setStanding(true);
							System.out.println("1");
							break;
						case W:
							tileMap.setLeft(true);
							player.setStanding(true);
							System.out.println("2");
							break;
						}
					}
				});
				sc.setOnKeyReleased(new EventHandler<KeyEvent>() { // Releasing
																	// key

					@Override
					public void handle(KeyEvent event) {
						switch (event.getCode()) {
						case E:
							setSmooth(0.0);
							player.setRight(false);
							player.setLeft(false);
							player.setStanding(true);
							tileMap.setRight(false); // Don't move anymore
							break;
						case W:
							setSmooth(0.0);
							player.setRight(false);
							player.setStanding(true);
							tileMap.setLeft(false); // Don't move anymore
							break;
						}
					}
				});

				// Moving to the left or the right
				if (tileMap.getRight() == true && tileMap.getLeft() == false) {
					smoothOutMovement(0.3);
					player.moveRight();
					tileMap.right(smooth); // Moving 3.0 Pixels to the right
				}
				if (tileMap.getLeft() == true && tileMap.getRight() == false) {
					player.moveLeft();
					smoothOutMovement(0.3);
					tileMap.left(smooth);
				}

				// Clear the scene
				group.getChildren().clear();
				group.getChildren().removeAll();

				// Update the map
				tileMap.update();

				// Drawing the player the last
				player.drawPlayer(new ImageView(), counter);

				if (targetFrameCounter % 7 == 0) {
					counter++;
				}

				if (counter == 9) {
					counter = 1;
				}

				targetFrameCounter++;
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

		// Generating a new window
		sc = new Scene(group, 960, 720, Color.BLACK); // 960 720 48

		// Creating a new TileMap Object for reading and drawing the game map
		try {
			tileMap = new TileMap(48);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		smooth = 0.0;

		counter = 1;
		player = new Player();
	}

	public void smoothOutMovement(double inc) {
		if (smooth < 2.9) {
			smooth += inc;
		}
		if (smooth > 2.9) {
			smooth = 3;
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

	public TileMap getTileMap() {
		return tileMap;
	}

	public void setTileMap(TileMap tileMap) {
		this.tileMap = tileMap;
	}

	public static void setGroup(Group group) {
		Game.group = group;
	}

	public Scene getScene() {
		return sc;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}
