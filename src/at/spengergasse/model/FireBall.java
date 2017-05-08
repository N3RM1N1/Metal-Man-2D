package at.spengergasse.model;

import java.util.ArrayList;

import at.spengergasse.gui.FrameFX;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FireBall {
	
	private int TargetCounter = 0;
	private int TargetFrames;
	private FrameFX g;
	
	private ImageView[] fireball;
	
	private double x;
	private double y;
	private double startingCoord;
	
	private boolean end;
	private boolean left;
	
	private int col;

	public FireBall(FrameFX g, int targetFrames, double x, double y, boolean left, int col) {
		this.g = g;
		this.TargetFrames = targetFrames;
		this.x = x;
		this.startingCoord = 0;
		this.y = y;
		end = false;
		this.left = left;
		this.col = col;
		loadImg();
	}
	
	public void loadImg() {
		fireball = new ImageView[5];
		
		fireball[0] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/fight/fireball_0.png")));
		fireball[1] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/fight/fireball_1.png")));
		fireball[2] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/fight/fireball_2.png")));
		fireball[3] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/fight/fireball_3.png")));
		fireball[4] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/fight/fireball_4.png")));
	}
	
	public void checkCollision(ArrayList<Enemies> enemies, boolean left) {
		for(Enemies e : enemies) {
			for(double j = 0; j < 70; j ++) {
				if(y + j >= e.getY()) {
					if(x >= e.getX() && left == false && this.col + 7 <= e.getCol()) {
						e.setDefeated(true);
						break;
					} else if(x <= e.getX() && left == true && this.col - 7 <= e.getCol()) {
						e.setDefeated(true);
						break;
					}
				}
			}
		}
	}
	
	public void update() {
		fire(6.0);
	}
	
	public void draw(ImageView im) {
		im = fireball[TargetCounter];
		if(left)
			im.setScaleX(-1);
		im.setFitHeight(70);
		im.setFitWidth(70);
		im.setTranslateX(this.x);
		im.setTranslateY(y);
		g.getRoot().getChildren().add(im);
	}
	
	public void fire(double x) {
		if(startingCoord < 400 && !left) {
			this.x +=x;
			this.startingCoord += x;
		} else if(startingCoord > -700 && left) {
			this.x -=x;
			this.startingCoord -= x;
		} else {
			end = true;
		}
	}
	
	public void walk(boolean right, double speed) {
		if(right == false) {
			x += speed;
		} else if(right == true) {
			x -= speed;
		}
	}
	
	public double getX() {
		return x;
	}
	
	public boolean getEnd() {
		return this.end;
	}
	
	public void checkCounter(int targetCounter) {
		if(TargetFrames+5 == targetCounter) {
			TargetFrames = targetCounter;
			TargetCounter ++;
			if(TargetCounter == 5) {
				TargetCounter = 0;
			}
		} else if(targetCounter < TargetFrames) {
			TargetFrames = targetCounter-1;
		}
	}

}
