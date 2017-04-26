package at.spengergasse.model;

import at.spengergasse.gui.FrameFX;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Extensions {

	private int TargetSpinCounter = 0;
	private FrameFX g;

	private double x;
	private double y;


	private boolean collected;

	private ImageView[] coin;

	public Extensions(FrameFX frame) {
		collected = false;
		this.g = frame;
		loadImg();
	}

	public void initCoordinates(int row, int col) {
		y = row * 48;
		x = col * 48;
	}

//	public void update() {
//		
//	}

	public void render() {
		draw(new ImageView());
	}

	public void draw(ImageView im) {
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
		im.setTranslateX(x);
		im.setTranslateY(y);
		g.getRoot().getChildren().add(im);
	}

	public void checkCounter(int targetCounter) {
		if (TargetSpinCounter == 6) {
			TargetSpinCounter = 0;
		}

		if (targetCounter % 9 == 0) {
			TargetSpinCounter++;
		}
	}

	public double getX() {
		return x;
	}
	
	public void incX(double x) {
		this.x = x;
	}

	public void setX(double x) {
		this.x = ((int)(x-this.x)/48)*48;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void loadImg() {
		coin = new ImageView[6];

		ImageView image = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_0.gif")));
		coin[0] = image;

		image = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_1.gif")));
		coin[1] = image;

		image = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_2.gif")));
		coin[2] = image;

		image = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_3.gif")));
		coin[3] = image;

		image = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_4.gif")));
		coin[4] = image;

		image = new ImageView(new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_5.gif")));
		coin[5] = image;
	}

}
