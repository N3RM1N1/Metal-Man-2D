package at.spengergasse.model;

import at.spengergasse.gui.FrameFX;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemies {
	
	private FrameFX g;
	
	private boolean defeated;
	
	private boolean left;
	private boolean right;
	
	private double x;
	private double y;
	
	private int TargetFrameCounter = 0;
	private int TargetFrames;
	
	private ImageView[] frames;

	public Enemies(FrameFX g, double y) {
		this.g = g;
		this.left = true;
		this.right = false;
		this.defeated = false;
		this.x = 400;
		this.y = y;
		loadImg();
	}
	
	public void update() {
		move();
	}
	
	public void render() {
	}
	
	public void draw(ImageView im, double x) {
		im = frames[TargetFrameCounter];
		if(right)
			im.setScaleX(-1);
		else {
			im.setScaleX(1);
		}
		im.setTranslateX(x-this.x);
		im.setTranslateY(y);
		im.setFitHeight(90);
		im.setFitWidth(80);
		g.getRoot().getChildren().add(im);
		System.out.println(left);
	}
	
	public void moveLeft(double x) {
		if(this.x > 0) {
			this.x -= x;
		} else {
			right = true;
			left = false;
		}
		
	}
	
	public void moveRight(double x) {
		if(this.x <= 400) {
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
	
	public void checkCounter(int targetCounter) {
		if(TargetFrames+5 == targetCounter) {
			TargetFrames = targetCounter;
			TargetFrameCounter ++;
			if(TargetFrameCounter == 6) {
				TargetFrameCounter = 0;
			}
		} else if(targetCounter < TargetFrames) {
			TargetFrames = targetCounter-1;
		}
		
	}
	
	
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
	
	public double getX() {
		return x;
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
