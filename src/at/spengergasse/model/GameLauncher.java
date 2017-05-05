/**
 * 
 */
package at.spengergasse.model;

import at.spengergasse.gui.FrameFX;
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

	/**
	 * Konstruktor
	 */
	public GameLauncher(FrameFX g) {
		this.g = g;
		this.imageLauncher = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/game/launcher/Launcher 2.0.png")));
		imageLauncher.setFitHeight(720);
		imageLauncher.setFitWidth(960);
		this.point = new Circle(10, Color.RED);
		this.x = 365;
		this.y = 427;
		point.setTranslateX(x);
		point.setTranslateY(y);
		this.start = new Text("Start Game");
		start.setFont(new Font(35));
		start.setTranslateX(390);
		start.setTranslateY(438);
		this.start.setFill(Color.WHITE);
		this.options = new Text("Options");
		this.exit = new Text("Exit");
	} // Konstruktor Ende

	public void draw() {
		g.getRoot().getChildren().add(imageLauncher);
		g.getRoot().getChildren().add(point);
		g.getRoot().getChildren().add(start);
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
