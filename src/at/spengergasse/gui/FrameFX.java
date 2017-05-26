package at.spengergasse.gui;

import java.util.ArrayList;

import at.spengergasse.controller.KeyBoard;
import at.spengergasse.model.Clock;
import at.spengergasse.model.GameStateManager;
import at.spengergasse.model.PauseMenu;
import at.spengergasse.model.Player;
import at.spengergasse.model.TileMap;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FrameFX extends Stage {

	final private TileMap tileMap;
	final private Player player;
	final private Sound soundEffects;
	final AnimationTimer gameLoop;

	// The counter for the player animation
	private int targetFrameCounter = 0;

	final private KeyBoard input;
	private GameStateManager launcher;
	final private Clock clock;

	private Group root;

	private Label gameClock;

	private Label coinCounter;
	
	private PauseMenu pause;
	private boolean paused;
	private Rectangle pauseBack;
	private Rectangle point;

	public FrameFX(Sound sound, Color c, ArrayList<Background> stars, String levelname) {
		super();
		paused = false;
		root = new Group();

		Scene scene = null;
		if(System.getProperty("os.name").equals("Mac OS X")) {
			scene = new Scene(root, 960, 720, c); // 960 720 48
		} else if(System.getProperty("os.name").equals("Windows 7") || System.getProperty("os.name").equals("Windows 8") 
				|| System.getProperty("os.name").equals("Windows 8.1")|| System.getProperty("os.name").equals("Windows 10")) {
			scene = new Scene(root, 948, 709, c); // 960 720 48
		}

		this.soundEffects = sound;

		this.tileMap = new TileMap(48, levelname, this, soundEffects);

		this.player = new Player(this, tileMap, soundEffects);

		this.launcher = new GameStateManager();
		
		this.pause = new PauseMenu();

		this.input = new KeyBoard(tileMap, player, soundEffects, this, launcher, pause);
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, input);
		scene.addEventHandler(KeyEvent.KEY_RELEASED, input);

		Image im = new Image("/at/spengergasse/icon/Icon2.png");
		getIcons().add(im);

		this.coinCounter = new Label("0");
		this.coinCounter.setStyle("-fx-font-family: \""
				+ Font.loadFont("file:src/at/spengergasse/resources/font/8-Bit Madness.ttf", 75).getFamily()
				+ "\";-fx-font-size: 75;");
		this.coinCounter.setTranslateX(5);
		this.coinCounter.setTranslateY(48);
		this.coinCounter.setTextFill(Color.GOLDENROD);

		this.clock = new Clock();

		this.gameClock = new Label("10:00");
		this.gameClock.setStyle("-fx-font-family: \""
				+ Font.loadFont("file:src/at/spengergasse/resources/font/8-Bit Madness.ttf", 60).getFamily()
				+ "\";-fx-font-size: 60;");
		this.gameClock.setTranslateX(820);
		this.gameClock.setTranslateY(0);
		this.gameClock.setTextFill(Color.WHITE);
		
		this.point = new Rectangle(25,25,Color.BLUE);
		this.point.setStroke(Color.DEEPSKYBLUE);
		this.point.setStrokeWidth(5);

		gameLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				update(now);
				render();
				renderHUD();
			}

			private void renderHUD() {
				gameClock.setText(clock.toString());
				root.getChildren().add(coinCounter);
				root.getChildren().add(gameClock);
			}

			private void render() {
				// Clear the scene
				root.getChildren().clear();
				
				for (Background b : stars) {
					root.getChildren().add(b.render());
				}
				
				// Update the map
				tileMap.render();

				// Drawing the player
				player.render();

			}

			private void update(long now) {
				targetFrameCounter++;
				player.checkCounter(targetFrameCounter);
				player.update();
				if (player.getX() == 192) {
					tileMap.update();
				}
				if(player.isLeft() && !player.isStanding() && !player.isRight() && !tileMap.isEnd() && !tileMap.isBeginning() && tileMap.getSmooth() > 1) {
					for (Background b : stars) {
						b.left();
					}
				} else if(!player.isLeft() && !player.isStanding() && player.isRight() && !tileMap.isEnd() && !tileMap.isBeginning() && tileMap.getSmooth() > 1) {
					for (Background b : stars) {
						b.right();
					}
				}
				tileMap.extUpdate();
				clock.incrementMilliSeconds(now);
				System.out.println("Loop");
			}

		};
		gameLoop.start();

		setScene(scene);
		setTitle("Metal Man 2D");
		setResizable(false);
		setFullScreen(false);
		show();
	}
	
	// PauseMenu
	public void pauseGame() {
		gameLoop.stop();
		pauseBack = new Rectangle(960, 720, Color.BLACK);
		pauseBack.setOpacity(0.5);
		root.getChildren().add(pauseBack);
		point.setTranslateY(pause.getY());
		point.setTranslateX(pause.getX());
		VBox vbox = new VBox();
		vbox.setSpacing(30);
		vbox.setPadding(new Insets(100, 0, 0, 170));
		for (String p : pause.getItems()) {
			Text t = new Text(p);
			t.setStyle("-fx-font-family: \""
					+ Font.loadFont("file:src/at/spengergasse/resources/font/8-Bit Madness.ttf", 60).getFamily()
					+ "\";-fx-font-size: 60;");
			t.setFill(Color.WHITE);
			vbox.getChildren().add(t);
		}
		root.getChildren().add(vbox);
		root.getChildren().add(point);
		paused = true;
	}
	
	// Resume Game
	public void resumeGame() {
		root.getChildren().remove(pauseBack);
		ArrayList<Integer> removingIndex = new ArrayList<>();
		for(int i = 0; i < root.getChildren().size(); i ++) {
			if(root.getChildren().get(i) instanceof Rectangle) {
				removingIndex.add(i);
			} else if(root.getChildren().get(i) instanceof VBox) {
				removingIndex.add(i);
			}
		}
		
		// Removing the Pause Items
		for(Integer i : removingIndex) {
			root.getChildren().remove(i);
		}
		gameLoop.start();
		paused = false;
	}
	
	// Updateing the Cursor
	public void placeCursor() {
		point.setTranslateY(pause.getY());
		point.setTranslateX(pause.getX());
	}
	
	public void pick() {
		if(pause.getState() == 0) {
			resumeGame();
		} else if(pause.getState() == 1) {
			
		} else if(pause.getState() == 2) {
			soundEffects.stop();
			new GameLauncher();
			close();
		} else if(pause.getState() == 3) {
			close();
		}
	}
	
	
	// GET-/ SET - Methods

	public int getTargetFrameCounter() {
		return targetFrameCounter;
	}

	/**
	 * @return the paused
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * @param paused the paused to set
	 */
	public void setPaused(boolean paused) {
		this.paused = paused;
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
