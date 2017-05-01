package at.spengergasse.model;

import at.spengergasse.gui.FrameFX;
import at.spengergasse.gui.Sound;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Extensions {

	private int TargetSpinCounter = 0;
	private FrameFX g;

	private boolean collected;

	private double x;
	private double y;

	private int col;

	private double opacity;
	
	private Sound soundEffects;

	private ImageView[] coin;

	public Extensions(FrameFX frame, double x, double y, int col, Sound sound) {
		collected = false;
		this.g = frame;
		this.x = x;
		this.y = y;
		this.col = col;
		this.opacity = 10;
		this.soundEffects = sound;
		loadImg();
	}

	public void draw(ImageView im, double x) {
		if (TargetSpinCounter == 0 || TargetSpinCounter == 6) {
			im = coin[0];
		} else if (TargetSpinCounter == 1) {
			im = coin[1];
		} else if (TargetSpinCounter == 2) {
			im = coin[2];
		} else if (TargetSpinCounter == 3) {
			im = coin[3];
		} else if (TargetSpinCounter == 4) {
			im = coin[4];
		} else if (TargetSpinCounter == 5) {
			im = coin[5];
		}
		if(collected) {
			im.setOpacity(opacity/10);
			opacity --;
			this.y -= 6.0;
		}
		im.setTranslateX(x);
		im.setTranslateY(this.y);
		g.getRoot().getChildren().add(im);
		System.out.println(x);
	}

	public void checkCounter(int targetCounter) {
		if (TargetSpinCounter == 6) {
			TargetSpinCounter = 0;
		}

		if (targetCounter % 9 == 0) {
			TargetSpinCounter++;
		}
	}


	public void left(double x) {
		this.x += x;
	}

	public void right(double x) {
		this.x -= x;
	}

	public double getX() {
		return this.x;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public void setCollected(boolean collected) {
		if(this.collected != collected) {
			this.collected = collected;
			soundEffects.playCoinSound();
		}
		
	}

	public double getOpacity() {
		return this.opacity;
	}

	public void loadImg() {
		coin = new ImageView[6];

		coin[0] = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_0.gif")));
		;

		coin[1] = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_1.gif")));
		;

		coin[2] = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_2.gif")));
		;

		coin[3] = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_3.gif")));
		;

		coin[4] = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_4.gif")));
		;

		coin[5] = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_5.gif")));
		;
	}

}
