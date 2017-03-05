package at.spengergasse.player;

import at.spengergasse.game.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {

	// Walking speed, start and maximum walk speed. Player starts slower and
	// gets faster
	private final double ENDWALKSPEED = 3.0;
	private final double WALKSPEED = 0.3;

	// Boolean Values for standing and falling
	private boolean standing;
	private boolean falling;

	// Jumping speed, Falling speed
	private final double JUMPSPEED = 1.2;
	private final double JUMPSPEEDMAX = 4.0;
	private final double FALLINGSPEED = 1.2;
	private final double FALLINGSPEEDMAX = 4.0;

	// The gravity --> used for falling to increase slowly the falling speed
	private final double GRAVITY = 1.2;

	// The coordinates of the player
	private double x;
	private double y;

	private double[][] coordinates;

	// TileMap, for getting important map values and moving the map with the
	// player
	private Game g;

	private Image[] standing1;
	private Image[] running;

	private boolean left;
	private boolean right;

	public Player() {
		g = new Game();
		standing = true;
		falling = false;
		loadImg();
		right = false;
		left = false;
	}

	public void walk(boolean right) {
		if (right) {
			moveRight();
		} else {
			moveLeft();
		}
	}

	public void moveLeft() {
		if (standing == true && falling == false) {
			g.setCounter(1);
			left = true;
			right = false;
			standing = false;
		}
	}

	public void moveRight() {
		if (falling == false && standing == true) {
			g.setCounter(1);
			left = false;
			right = true;
			standing = false;
		}
	}

	public void drawPlayer(ImageView im, int counter) {
		if (standing == true) {
			if (counter == 1 || counter == 6) {
				Image image = standing1[0];
				im = new ImageView(image);
			} else if (counter == 2 || counter == 7) {
				Image image = standing1[1];
				im = new ImageView(image);
			} else if (counter == 3 || counter == 8) {
				Image image = standing1[2];
				im = new ImageView(image);
			} else if (counter == 4) {
				Image image = standing1[3];
				im = new ImageView(image);
			} else if (counter == 5) {
				Image image = standing1[4];
				im = new ImageView(image);
			}
		} else {
			running(new ImageView(), counter);
		}

		im.setTranslateX(200);  //96 x 78
		im.setTranslateY(576);
		if (left == true) {
			im.setScaleX(-1);
		}
		g.getGroup().getChildren().add(im);
	}

	public void running(ImageView im, int counter) {
		if (falling == false) {
			if (counter == 1) {
				Image image = running[0];
				im = new ImageView(image);
			} else if (counter == 2) {
				Image image = running[1];
				im = new ImageView(image);
			} else if (counter == 3) {
				Image image = running[2];
				im = new ImageView(image);
			} else if (counter == 4) {
				Image image = running[3];
				im = new ImageView(image);
			} else if (counter == 5) {
				Image image = running[4];
				im = new ImageView(image);
			} else if (counter == 6) {
				Image image = running[5];
				im = new ImageView(image);
			} else if (counter == 7) {
				Image image = running[6];
				im = new ImageView(image);
			} else if (counter == 8) {
				Image image = running[7];
				im = new ImageView(image);
			} else {
				System.out.println("Nix");
			}
		}
		im.setTranslateX(200);
		im.setTranslateY(576);
		if (left == true) {
			im.setScaleX(-1);
		}
		g.getGroup().getChildren().add(im);
	}

	public void loadImg() {
		standing1 = new Image[5];

		Image image1 = new Image(getClass().getResourceAsStream("/Res/player-frame_1.gif"));
		Image image2 = new Image(getClass().getResourceAsStream("/Res/player-frame_2.gif"));
		Image image3 = new Image(getClass().getResourceAsStream("/Res/player-frame_3.gif"));
		Image image4 = new Image(getClass().getResourceAsStream("/Res/player-frame_4.gif"));
		Image image5 = new Image(getClass().getResourceAsStream("/Res/player-frame_5.gif"));

		standing1[0] = image1;
		standing1[1] = image2;
		standing1[2] = image3;
		standing1[3] = image4;
		standing1[4] = image5;

		running = new Image[8];

		image1 = new Image(getClass().getResourceAsStream("/Res/Player_Frames_Running/frame_0_delay-0.13s.gif"));
		image2 = new Image(getClass().getResourceAsStream("/Res/Player_Frames_Running/frame_1_delay-0.13s.gif"));
		image3 = new Image(getClass().getResourceAsStream("/Res/Player_Frames_Running/frame_2_delay-0.13s.gif"));
		image4 = new Image(getClass().getResourceAsStream("/Res/Player_Frames_Running/frame_3_delay-0.13s.gif"));
		image5 = new Image(getClass().getResourceAsStream("/Res/Player_Frames_Running/frame_4_delay-0.13s.gif"));
		Image image6 = new Image(getClass().getResourceAsStream("/Res/Player_Frames_Running/frame_5_delay-0.13s.gif"));
		Image image7 = new Image(getClass().getResourceAsStream("/Res/Player_Frames_Running/frame_6_delay-0.13s.gif"));
		Image image8 = new Image(getClass().getResourceAsStream("/Res/Player_Frames_Running/frame_7_delay-0.13s.gif"));

		running[0] = image1;
		running[1] = image2;
		running[2] = image3;
		running[3] = image4;
		running[4] = image5;
		running[5] = image6;
		running[6] = image7;
		running[7] = image8;
	}

	// GET SET Methods

	public double getWalkSpeed() {
		return WALKSPEED;
	}

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public double getJumpSpeed() {
		return JUMPSPEED;
	}

	public double getGravity() {
		return GRAVITY;
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

	public void setStanding(boolean standing) {
		this.standing = standing;
	}

	public boolean getStanding() {
		return standing;
	}


}
