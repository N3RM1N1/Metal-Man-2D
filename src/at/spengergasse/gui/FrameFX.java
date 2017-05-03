package at.spengergasse.gui;

import java.util.List;

import at.spengergasse.controller.KeyBoard;
import at.spengergasse.model.Player;
import at.spengergasse.model.TileMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class FrameFX extends Stage {

	final private TileMap tileMap;
	final private Player player;
	final private Sound soundEffects;
	final AnimationTimer gameLoop;

	// The counter for the player animation
	private int targetFrameCounter = 0;
	
	final private List<String> args;
	final private KeyBoard input;
	
	private Group root;
	
	
	public FrameFX(List<String> args) {
		super();
		this.args = args;
		
		root = new Group();

		Color back = new Color(0.07,0.231,0.29, 1);
		

		Scene scene = new Scene(root, 948, 709, back); // 960 720 48
		
		this.soundEffects = new Sound();

		this.tileMap = new TileMap(48, "Level1.txt", this, soundEffects);

		this.player = new Player(this, tileMap, soundEffects); 

		this.input = new KeyBoard(tileMap, player, soundEffects, this);
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, input);
		scene.addEventHandler(KeyEvent.KEY_RELEASED, input);
		
		Image im = new Image("/at/spengergasse/icon/Icon2.png");
		getIcons().add(im);
		
		gameLoop = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				update();
				render();
			}
			
			private void render() {
				// Clear the scene
				root.getChildren().clear();

				// Update the map
				tileMap.render();

				// Drawing the player
				player.render();
			}

			private void update() {
				targetFrameCounter++;
				player.checkCounter(targetFrameCounter);
				if(player.getX() == 192) {
					tileMap.update();
				}
				tileMap.extUpdate();
				player.update();
			}
		};
		gameLoop.start();
		
		setScene(scene);
		setTitle("Metal Man 2D");
		setResizable(false);
		setFullScreen(false);
		show();
	}

	
	// GET-/ SET - Methods

	public int getTargetFrameCounter() {
		return targetFrameCounter;
	}

	public void setTargetFrameCounter(int targetFrameCounter) {
		this.targetFrameCounter = targetFrameCounter;
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
	
	public AnimationTimer getGameLoop() {
		return gameLoop;
	}
}
