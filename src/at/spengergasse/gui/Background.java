package at.spengergasse.gui;

import at.spengergasse.model.BackgroundStars;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Background {

	private BackgroundStars back;

	public Background() {
		this.back = new BackgroundStars();
	}
	
	public Rectangle draw(Rectangle r) {
		
		r = new Rectangle(back.getSize(),back.getSize(),Color.WHITE);
		r.setTranslateX(back.getX());
		r.setTranslateY(back.getY());
		return r;
	}
	
	public Rectangle render() {
		return draw(new Rectangle());
	}
	
	public void left() {
		back.setX(back.getX() + 1);
	}
	public void right() {
		back.setX(back.getX() - 1);
	}
}
