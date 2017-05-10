package at.spengergasse.model;

import at.spengergasse.gui.FrameFX;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SlimeMonster extends Enemies {

	private ImageView[] frames;
	
	private double x;
	private double y;
	private double currX;
	
	private boolean left;
	private boolean right;
	
	private int TargetFrameCounter = 0;
	private int TargetFrames;
	
	public SlimeMonster(FrameFX g, double y) {
		super(g);
		this.left = true;
		this.right = false;
		this.currX = 5000;
		this.x = 400;
		this.y = y;
		loadImg();
	}
	
	public void update() {
		move();
	}

	public void draw(ImageView im, double x) {
		currX = x;
		im = frames[this.TargetFrameCounter];
		if (right)
			im.setScaleX(-1);
		else {
			im.setScaleX(1);
		}
		im.setTranslateX(x - this.x);
		im.setTranslateY(y);
		im.setFitHeight(100);
		im.setFitWidth(100);
		super.getFrame().getRoot().getChildren().add(im);
	}
	
	public void moveLeft(double x) {
		if (this.x > 0) {
			this.x -= x;
		} else {
			right = true;
			left = false;
		}

	}

	public void moveRight(double x) {
		if (this.x <= 400) {
			this.x += x;
		} else {
			right = false;
			left = true;
		}
	}

	public void move() {
		if (right) {
			moveRight(1.5);
		} else if (left) {
			moveLeft(1.5);
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
		return currX - this.x;
	}

	public double getY() {
		return y;
	}
	
	public void checkCounter(int targetCounter) {
		if (TargetFrames + 4 == targetCounter) {
			TargetFrames = targetCounter;
			TargetFrameCounter++;
			if (TargetFrameCounter == 6) {
				TargetFrameCounter = 0;
			}
		} else if (targetCounter < TargetFrames) {
			TargetFrames = targetCounter - 1;
		}
	}
	
	public void loadImg() {
		frames = new ImageView[6];

		frames[0] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/enemies/slimeMonster/slimeMonster_0.png")));
		frames[1] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/enemies/slimeMonster/slimeMonster_1.png")));
		frames[2] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/enemies/slimeMonster/slimeMonster_2.png")));
		frames[3] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/enemies/slimeMonster/slimeMonster_3.png")));
		frames[4] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/enemies/slimeMonster/slimeMonster_4.png")));
		frames[5] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/enemies/slimeMonster/slimeMonster_5.png")));
	}
	
} // Klassen Ende

