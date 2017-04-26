package at.spengergasse.model;

import at.spengergasse.gui.FrameFX;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemies {
	
	private FrameFX g;
	private TileMap map;
	
	private boolean defeated;
	
	private boolean left;
	private boolean right;
	
	private double x;
	private double y;
	
	private int TargetFrameCounter = 0;
	private int TargetFrames;
	
	private ImageView[] frames;

	public Enemies(FrameFX g, TileMap map) {
		this.g = g;
		this.map = map;
		this.defeated = false;
		this.left = false;
		this.right = true;
		this.x = 400;
		this.y = 515;
		loadImg();
	}
	
	public void update() {
		move();
	}
	
	public void render() {
		draw(new ImageView());
	}
	
	public void draw(ImageView im) {
		im = frames[TargetFrameCounter];
		if(left)
			im.setScaleX(-1);
		else {
			im.setScaleX(1);
		}
		im.setTranslateX(x);
		im.setTranslateY(y);
		im.setFitHeight(70);
		im.setFitWidth(70);
		g.getRoot().getChildren().add(im);
	}
	
	public void moveLeft(double x) {
		if(this.x > 400) {
			this.x -= x;
		} else {
			right = true;
			left = false;
		}
		
	}
	
	public void moveRight(double x) {
		if(this.x <= 500) {
			this.x +=x;
		} else {
			right = false;
			left = true;
		}
		
	}
	
	public void move() {
		if(right) {
			moveRight(2.0);
		} else if(left) {
			moveLeft(2.0);
		}
	}
	
	public void checkCoutner(int targetCounter) {
		if(TargetFrames+5 == targetCounter) {
			TargetFrames = targetCounter;
			TargetFrameCounter ++;
			if(TargetFrameCounter == 6) {
				TargetFrameCounter = 0;
			}
		}
		
	}
	
	public void loadImg() {
		frames = new ImageView[6];
		
		frames[0] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/enemies/skeletonWarrior/frame_0_delay-0.1s.gif")));
		frames[1] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/enemies/skeletonWarrior/frame_1_delay-0.1s.gif")));
		frames[2] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/enemies/skeletonWarrior/frame_2_delay-0.1s.gif")));
		frames[3] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/enemies/skeletonWarrior/frame_3_delay-0.1s.gif")));
		frames[4] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/enemies/skeletonWarrior/frame_4_delay-0.1s.gif")));
		frames[5] = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/enemies/skeletonWarrior/frame_5_delay-0.1s.gif")));
	}

}
