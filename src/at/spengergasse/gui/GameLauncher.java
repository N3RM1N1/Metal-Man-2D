package at.spengergasse.gui;

import java.util.ArrayList;

import at.spengergasse.controller.KeyBoard;
import at.spengergasse.model.GameStateManager;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameLauncher extends Stage {

	final private Sound soundEffects;
	final private GameStateManager launcher;
	final private KeyBoard input;
	
	private Rectangle point;
	private Text start;
	private Text options;
	private Text exit;
	private ImageView im;
	
	private Scene scene;
	
	private AnimationTimer loop;
	private Group root;
	private Color back;
	
	private ArrayList<Background> background;
	private FlowPane levels;
	private ArrayList<Text> levelTexts;
	
	public GameLauncher() {
		root = new Group();
		back = new Color(0.07, 0.231, 0.29, 1);
		
		this.scene = new Scene(root, 948, 709, back);
		
		this.soundEffects = new Sound();
		
		this.launcher = new GameStateManager();
		
		this.input = new KeyBoard(this, launcher);
		
		levelTexts = new ArrayList<>();
		levels = new FlowPane();
		levels.setMinWidth(920);
		levels.setHgap(70.0);
		levels.setVgap(40);
		levels.setPadding(new Insets(300, 80, 0, 120));
		
		im = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/game/launcher/launcherStart.png")));
		im.setTranslateX(100);
		im.setTranslateY(100);
		
		this.point = new Rectangle(25,25, Color.BLUE);
		this.point.setStroke(Color.DEEPSKYBLUE);
		this.point.setStrokeWidth(5);
		point.setTranslateX(launcher.getX());
		point.setTranslateY(launcher.getY());

		this.start = new Text("Start Game");
		this.start.setStyle("-fx-font-family: \""
				+ Font.loadFont("file:src/at/spengergasse/resources/font/8-Bit Madness.ttf", 60).getFamily()
				+ "\";-fx-font-size: 60;");
		start.setTranslateX(350);
		start.setTranslateY(400);
		this.start.setFill(Color.WHITE);

		this.options = new Text("Options");
		this.options.setStyle("-fx-font-family: \""
				+ Font.loadFont("file:src/at/spengergasse/resources/font/8-Bit Madness.ttf", 60).getFamily()
				+ "\";-fx-font-size: 60;");
		options.setTranslateX(350);
		options.setTranslateY(480);
		this.options.setFill(Color.WHITE);

		this.exit = new Text("Exit");
		this.exit.setStyle("-fx-font-family: \""
				+ Font.loadFont("file:src/at/spengergasse/resources/font/8-Bit Madness.ttf", 60).getFamily()
				+ "\";-fx-font-size: 60;");
		exit.setTranslateX(350);
		exit.setTranslateY(560);
		this.exit.setFill(Color.WHITE);
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, input);
		
		background = new ArrayList<>();
		
		for (int i = 0; i < 60; i++) {
			background.add(new Background());
		}
		
		loop = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				update();
				render();
			}

			private void render() {
				root.getChildren().clear();
				for (Background b : background) {
					root.getChildren().add(b.render());
				}
				root.getChildren().add(im);
				if(point != null)
					root.getChildren().add(point);
				if(start != null) {
					root.getChildren().add(start);
					root.getChildren().add(options);
					root.getChildren().add(exit);
				}
				
				if(!levels.getChildren().isEmpty())
					root.getChildren().add(levels);
				
				if(!levelTexts.isEmpty()) {
					root.getChildren().addAll(levelTexts);
				}
			}

			private void update() {
				if(point != null) {
					point.setTranslateY(launcher.getY());
					point.setTranslateX(launcher.getX());
				}
				for (Text text : levelTexts) {
					text.setTranslateX(200);
					text.setTranslateX(600);
				}
				
				for(int i = 0; i < levels.getChildren().size(); i ++) {
					for(int j = 0; j < levels.getChildren().size(); j ++) {
						((Shape) levels.getChildren().get(j)).setStroke(Color.DEEPSKYBLUE);
					}
					((Shape) levels.getChildren().get(launcher.getState()-3)).setStroke(Color.BLUE);
					levelTexts.get(i).setTranslateX(levels.getChildren().get(i).getLayoutX() + 10);
					levelTexts.get(i).setTranslateY(levels.getChildren().get(i).getLayoutY() + 30);
					if(i > 0)
						levelTexts.get(i).setOpacity(0.5);
				}
			}
			
		};
		
		loop.start();
		
		setScene(scene);
		setTitle("Metal Man 2D");
		setResizable(false);
		setFullScreen(false);
		show();
	}

	public void pick() {
		if(launcher.getState() == 0) {
			changeState(launcher.levelManager());
			launcher.setState(3);
		} else if(launcher.getState() == 1) {
			System.out.println("Options");
		} else if(launcher.getState() == 2) {
			close();
		} else if(launcher.getState() > 2) {
			String level = "Level" + (launcher.getState()-2) + ".txt";
			root.getChildren().clear();
			loop.stop();
			new FrameFX(soundEffects, back, background, level);
			soundEffects.playGameSound();
			close();
		}
	}
	
	public void changeState(ArrayList<Integer> levels) {
		start = null;
		options = null;
		exit = null;
		point = null;
		
		for (Integer objects : levels) {
			if(objects == 70) {
				Rectangle r1 = new Rectangle(objects, objects, Color.DEEPSKYBLUE);
				r1.setStroke(Color.DEEPSKYBLUE);
				r1.setStrokeWidth(10);
				this.levels.getChildren().add(r1);
			} else {
				Text t = new Text("" + objects);
				t.setStyle("-fx-font-family: \""
						+ Font.loadFont("file:src/at/spengergasse/resources/font/8-Bit Madness.ttf", 45).getFamily()
						+ "\";-fx-font-size: 45;");
				t.setFill(Color.WHITE);
				this.levelTexts.add(t);
			}
		}
	}
}
