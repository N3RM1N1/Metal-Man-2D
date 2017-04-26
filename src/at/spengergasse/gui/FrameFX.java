package at.spengergasse.gui;

import java.util.List;

import at.spengergasse.controller.KeyBoard;
import at.spengergasse.model.Enemies;
import at.spengergasse.model.Extensions;
import at.spengergasse.model.Player;
import at.spengergasse.model.TileMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class FrameFX extends Stage {

	final private TileMap tileMap;
	final private Player player;
	final private Sound soundEffects;
	final private Background background;
	final private Enemies enemies;
	private Timeline gameLoop;

	// The counter for the player animation
	private int targetFrameCounter = 0;
	
	final private List<String> args;
	final private KeyBoard input;
	
	private Group root;
	
	
	public FrameFX(List<String> args) {
		super();
		this.args = args;
		
		root = new Group();

//		Color background = new Color(0.165, 0.165, 0.165, 1);
		

		Scene scene = new Scene(root, 960, 720); // 960 720 48
		

		this.tileMap = new TileMap(48, "Level1.txt", this);

		this.player = new Player(this, tileMap);

		this.soundEffects = new Sound();
		
		this.background = new Background(this, tileMap, player);
		
		this.enemies = new Enemies(this, tileMap);

		this.input = new KeyBoard(tileMap, player, soundEffects, this);
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, input);
		scene.addEventHandler(KeyEvent.KEY_RELEASED, input);
		
		gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		
		

		
		KeyFrame kf = new KeyFrame(Duration.seconds(1.0 / 60.0), new EventHandler<ActionEvent>() {
			

			@Override
			public void handle(ActionEvent event) {

				update();

				render();
				
			}

			private void render() {
				// Clear the scene
				root.getChildren().clear();

				// Render Background
				background.render();

				// Update the map
				tileMap.render();
				
				// Rendering enemies
				enemies.render();

				// Drawing the player
				player.render();
			}

			private void update() {
				targetFrameCounter++;
				background.update();
				player.checkCounter(targetFrameCounter);
				enemies.checkCoutner(targetFrameCounter);
				tileMap.update();
				enemies.update();
				player.update();
			}
			
		});

		gameLoop.getKeyFrames().add(kf);
		gameLoop.play();
		
		setScene(scene);
		setTitle("Metal Man 2D");
		setResizable(true);
		show();
	}

	
	// GET-/ SET - Methods

	public int getTargetFrameCounter() {
		return targetFrameCounter;
	}

	public void setTargetFrameCounter(int targetFrameCounter1) {
		this.targetFrameCounter = targetFrameCounter1;
	}

	public TileMap getTileMap() {
		return tileMap;
	}

	public Player getPlayer() {
		return player;
	}

	public Sound getSoundEffects() {
		return soundEffects;
	}

	public List<String> getArgs() {
		return args;
	}

	public KeyBoard getInput() {
		return input;
	}

	public Group getRoot() {
		return root;
	}


	public Timeline getGameLoop() {
		return gameLoop;
	}
}
