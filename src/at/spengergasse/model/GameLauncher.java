/**
 * 
 */
package at.spengergasse.model;

import java.util.ArrayList;

import at.spengergasse.gui.Background;
import at.spengergasse.gui.FrameFX;
import at.spengergasse.gui.Sound;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * 
 *
 */
public class GameLauncher {

	private FrameFX g;
	private Circle point;
	private double x;
	private double y;
	private Text start;
	private Text options;
	private Text exit;
	private ImageView im;

	private ArrayList<Background> background;

	private Sound s;

	/**
	 * Konstruktor
	 */
	public GameLauncher(FrameFX g, Sound s) {
		this.g = g;
		this.s = s;

		background = new ArrayList<>();

		for (int i = 0; i < 30; i++) {
			background.add(new Background(this.g));
		}
		im = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/game/launcher/launcherStart.png")));
		im.setTranslateX(100);
		im.setTranslateY(100);

		this.point = new Circle(10, Color.DEEPSKYBLUE);
		this.x = 300;
		this.y = 388;
		point.setTranslateX(x);
		point.setTranslateY(y);

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
	} // Konstruktor Ende

	public void draw() {
		for (Background b : background) {
			b.render();
		}
		g.getRoot().getChildren().add(im);
		g.getRoot().getChildren().add(point);
		g.getRoot().getChildren().add(start);
		g.getRoot().getChildren().add(options);
		g.getRoot().getChildren().add(exit);
	}

	public void update() {
		point.setTranslateY(y);
	}

	public void moveCursorDown() {
		if (y == 388 || y == 468) {
			y += 80;
		} else {
			y = 388;
		}
	}

	public void moveCusorUp() {
		if (y == 548 || y == 468) {
			y -= 80;
		} else {
			y = 548;
		}
	}

	public void pick() {
		if (y == 388) {
			g.closeLauncher();
			g.closeLauncher();
			s.playGameSound();
		} else if (y == 468) {
			// noch nichts
		} else if (y == 548) {
			g.close();
		}
	}

	public void close() {
		background.clear();
		background = null;
		this.point = null;
		this.start = null;
		this.options = null;
		this.exit = null;
		this.s = null;
	}

}// Klassen Ende
