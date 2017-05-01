package at.spengergasse.gui;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Background {
	
	private FrameFX g;
	
	private double x;
	private double y;
	
	private int size;

	public Background(FrameFX game) {
		this.g = game;
		Random r = new Random();
		x = r.nextInt(1600)+48;
		y = r.nextInt(624)+48;
		size = r.nextInt(2) + 3;
	}
	
	public void draw(Rectangle r) {
		
		r = new Rectangle(size,size,Color.WHITE);
		r.setTranslateX(x);
		r.setTranslateY(y);
		g.getRoot().getChildren().add(r);
	}
	
	public void render() {
		draw(new Rectangle());
	}
	
	public void left() {
		x += 1;
	}
	public void right() {
		x -= 1;
	}
}
