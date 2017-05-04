/**
 * 
 */
package at.spengergasse.model;

import at.spengergasse.gui.FrameFX;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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

	/**
	 * Konstruktor
	 */
	public GameLauncher(FrameFX g) {
		this.g = g;
		this.imageLauncher = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/game/launcher/Launcher_Game.png")));
		imageLauncher.setFitHeight(720);
		imageLauncher.setFitWidth(960);
		this.point = new Circle(10, Color.RED);
		this.x = 365;
		this.y = 427;
		point.setTranslateX(x);
		point.setTranslateY(y);
	} // Konstruktor Ende

	public void draw() {
		g.getRoot().getChildren().add(imageLauncher);
		g.getRoot().getChildren().add(point);
	}

	public void update() {
		point.setTranslateY(y);
	}

	public void moveCursorDown() {
		if (y == 427 || y == 472) {
			y += 45;
		} else {
			y = 427;
		}
	}

	public void moveCusorUp() {
		if(y == 517 || y == 472){
			y-= 45;
		}else{
			y= 517;
		}
	}

}// Klassen Ende
