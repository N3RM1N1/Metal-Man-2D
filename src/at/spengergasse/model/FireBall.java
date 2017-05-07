package at.spengergasse.model;

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

	public FireBall(FrameFX g, int targetFrames, double x, double y, boolean left) {
		this.g = g;
		this.TargetFrames = targetFrames;
		this.x = x;
		this.startingCoord = 0;
		this.y = y;
		end = false;
		this.left = left;
		loadImg();
	}
	
	public void loadImg() {
		fireball = new ImageView[5];
		
		fireball[0] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/fight/fireball_0.gif")));
		fireball[1] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/fight/fireball_1.gif")));
		fireball[2] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/fight/fireball_2.gif")));
		fireball[3] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/fight/fireball_3.gif")));
		fireball[4] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/fight/fireball_4.gif")));
	}
	
	public void update() {
		fire(5.0);
		System.out.println(left);
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
		if(startingCoord < 700 && !left) {
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
