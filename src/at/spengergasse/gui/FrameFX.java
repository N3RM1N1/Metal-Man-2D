package at.spengergasse.gui;

import java.util.List;

import at.spengergasse.controller.KeyBoard;
import at.spengergasse.model.Clock;
import at.spengergasse.model.GameLauncher;
import at.spengergasse.model.Player;
import at.spengergasse.model.TileMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FrameFX extends Stage {

	final private TileMap tileMap;
	final private Player player;
	final private Sound soundEffects;
	final AnimationTimer gameLoop;

	// The counter for the player animation
	private int targetFrameCounter = 0;
	public boolean launcherOpen;

	final private List<String> args;
	final private KeyBoard input;
	private GameLauncher launcher;
	private Clock clock;

	private Group root;
	
	private Text gameClock;
	
	private Text coinCounter;

	public FrameFX(List<String> args) {
		super();
		this.args = args;

		root = new Group();

		Color back = new Color(0.07, 0.231, 0.29, 1);

		Scene scene = new Scene(root, 948, 709, back); // 960 720 48

		this.soundEffects = new Sound();

		this.tileMap = new TileMap(48, "Level1.txt", this, soundEffects);

		this.player = new Player(this, tileMap, soundEffects);

		this.launcher = new GameLauncher(this, soundEffects);
		
		this.input = new KeyBoard(tileMap, player, soundEffects, this, launcher);

		this.launcherOpen = true;

		scene.addEventHandler(KeyEvent.KEY_PRESSED, input);
		scene.addEventHandler(KeyEvent.KEY_RELEASED, input);

		Image im = new Image("/at/spengergasse/icon/Icon2.png");
		getIcons().add(im);
		
		this.coinCounter = new Text("0");
		this.coinCounter.setStyle("-fx-font-family: \""
				+ Font.loadFont("file:src/at/spengergasse/resources/font/8-Bit Madness.ttf", 75).getFamily()
				+ "\";-fx-font-size: 75;");
		this.coinCounter.setTranslateX(5);
		this.coinCounter.setTranslateY(80);
		this.coinCounter.setFill(Color.GOLDENROD);
		
		this.clock = new Clock();

		this.gameClock = new Text("10:00");
		this.gameClock.setStyle("-fx-font-family: \""
				+ Font.loadFont("file:src/at/spengergasse/resources/font/8-Bit Madness.ttf", 60).getFamily()
				+ "\";-fx-font-size: 60;");
		this.gameClock.setTranslateX(820);
		this.gameClock.setTranslateY(35);
		this.gameClock.setFill(Color.WHITE);
		gameLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				update();
				render();
				renderHUD();
				
			}
			
			private void renderHUD() {
				if(launcherOpen == false) {
					gameClock.setText(clock.toString());
					root.getChildren().add(coinCounter);
					root.getChildren().add(gameClock);
				}
			}

			private void render() {
				// Clear the scene
				root.getChildren().clear();
				if (launcherOpen == true) {
					launcher.draw();
				} else {
					// Update the map
					tileMap.render();

					// Drawing the player
					player.render();
				}

			}

			private void update() {
				if (launcherOpen == true) {
					launcher.update();
				} else {
					targetFrameCounter++;
					player.checkCounter(targetFrameCounter);
					player.update();
					if (player.getX() == 192) {
						tileMap.update();
					}
					tileMap.extUpdate();
					clock.incrementMilliSeconds();
				}
			}
		};
		gameLoop.start();

		setScene(scene);
		setTitle("Metal Man 2D");
		setResizable(false);
		setFullScreen(false);
		show();
	}
	
	public void closeLauncher() {
		launcherOpen = false;
		this.launcher = null;
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
