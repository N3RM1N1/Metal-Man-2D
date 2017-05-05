/**
 * 
 */
package at.spengergasse.model;

import at.spengergasse.gui.FrameFX;
import at.spengergasse.gui.Sound;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
	private ImageView imageLauncher;
	private Circle point;
	private double x;
	private double y;
	private Text start;
	private Text options;
	private Text exit;
	
	private Sound s;

	/**
	 * Konstruktor
	 */
	public GameLauncher(FrameFX g, Sound s) {
		this.g = g;
		this.s = s;
		
		this.imageLauncher = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/game/launcher/Launcher 2.0.png")));
		imageLauncher.setFitHeight(720);
		imageLauncher.setFitWidth(960);
		
		this.point = new Circle(10, Color.DEEPSKYBLUE);
		this.x = 300;
		this.y = 388;
		point.setTranslateX(x);
		point.setTranslateY(y);
		
		this.start = new Text("Start Game");
		this.start.setStyle("-fx-font-family: \""+ Font.loadFont("file:src/at/spengergasse/resources/font/8-Bit Madness.ttf", 60).getFamily() + "\";-fx-font-size: 60;");
		start.setTranslateX(350);
		start.setTranslateY(400);
		this.start.setFill(Color.WHITE);
		
		this.options = new Text("Options");
		this.options.setStyle("-fx-font-family: \""+ Font.loadFont("file:src/at/spengergasse/resources/font/8-Bit Madness.ttf", 60).getFamily() + "\";-fx-font-size: 60;");
		options.setTranslateX(350);
		options.setTranslateY(480);
		this.options.setFill(Color.WHITE);
		
		this.exit = new Text("Exit");
		this.exit.setStyle("-fx-font-family: \""+ Font.loadFont("file:src/at/spengergasse/resources/font/8-Bit Madness.ttf", 60).getFamily() + "\";-fx-font-size: 60;");
		exit.setTranslateX(350);
		exit.setTranslateY(560);
		this.exit.setFill(Color.WHITE);
	} // Konstruktor Ende

	public void draw() {
		g.getRoot().getChildren().add(imageLauncher);
		g.getRoot().getChildren().add(point);
		g.getRoot().getChildren().add(start);
		g.getRoot().getChildren().add(options);
		g.getRoot().getChildren().add(exit);
	}

	public void update() {
		point.setTranslateY(y);
		if(true) {
			
		}
	}

	public void moveCursorDown() {
		if (y == 388 || y == 468) {
			y += 80;
		} else {
			y = 388;
		}
	}

	public void moveCusorUp() {
		if(y == 548 || y == 468){
			y-= 80;
		}else{
			y= 548;
		}
	}
	
	public void pick() {
		if(y == 388) {
			g.launcherOpen = false;
			s.playGameSound();
		} else if(y == 468) {
			// noch nichts
		} else if(y == 548) {
			g.close();
		}
	}

}// Klassen Ende
