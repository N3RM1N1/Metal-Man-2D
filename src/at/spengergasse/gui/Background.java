package at.spengergasse.gui;

import at.spengergasse.model.Player;
import at.spengergasse.model.TileMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Background {
	
	private FrameFX g;
	
	private TileMap map;
	
	private Player player;
	
	private ImageView background;
	private ImageView background2;
	
	private double x;

	public Background(FrameFX game, TileMap map, Player p) {
		
		this.g = game;
		
		this.map = map;
		
		this.player = p;
		
		this.x = 0;
		
		background = new ImageView(new Image(("/at/spengergasse/resources/map/textures/space_background.gif")));
		background2 = new ImageView(new Image(("/at/spengergasse/resources/map/textures/space_background.gif")));
		background.setTranslateX(x);
		background2.setTranslateX(background.getFitWidth());
	}
	
	public void draw() {
		background.setTranslateX(x);
		background.setTranslateY(0);
		
		g.getRoot().getChildren().add(background);
		g.getRoot().getChildren().add(background2);
	}
	
	public void render() {
		draw();
	}
	
	public void update() {
		if(map.getLeft() && !map.isBeginning() && !map.isEnd() && !player.getStanding()) {
			moveLeft();
		} else if(map.getRight() && !map.isBeginning() && !map.isEnd() && !player.getStanding()) {
			moveRight();
		}
		background2.setTranslateX(x+960);
	}
	
	public void moveLeft() {
		x += 1.0;
	}
	
	public void moveRight() {
		x -= 1.0;
	}

}
