package at.spengergasse.model;

import at.spengergasse.gui.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {

	// Walking speed, start and maximum walk speed. Player starts slower and
	// gets faster
	private final double WALKSPEED = 0.3;

	// Boolean Values for standing and falling
	private boolean isStanding;
	private boolean isFalling;
	private boolean isFighting;

	// Jumping speed, Falling speed
	private final double JUMPSPEED = 140;
	private double VELOCITY = 0;
	private final double FALLINGSPEEDMAX = 140;

	// The gravity --> used for falling to increase slowly the falling speed
	private final double GRAVITY = 4;

	// The coordinates of the player
	private static double x = 200;

	public static double getX() {
		return x;
	}

	public static void setX(double x) {
		Player.x = x;
	}

	private double y = 5550;

	// The counter for the Frames
	private static int TargetStandingCounter;
	private static int TargetRunningCounter;
	private static int TargetJumpCounter;
	private static int TargetFightCounter;

	// TileMap, for getting important map values and moving the map with the
	// player
	private FrameFX g;

	private ImageView[] standing;
	private ImageView[] running;
	private ImageView[] jump;
	private ImageView[] fight;

	private boolean left;
	private boolean right;
	private boolean standingLeft;
	private boolean jumping;

	private TileMap level;

	public Player(FrameFX frame, TileMap level) {
		this.g = frame;
		isStanding = true;
		isFalling = false;
		right = false;
		left = false;
		jumping = false;
		x = 200;
		this.level = level;
		loadImg();
	}

	public void moveLeft() {
		left = true;
		right = false;
		isStanding = false;
		standingLeft = true;
	}

	public void moveRight() {
		left = false;
		right = true;
		isStanding = false;
		standingLeft = false;
	}

	public void walkLeft(double speed) {
		if (level.isEnd()) {
			if (x - speed >= 200) {
				x -= speed;
			} else if (x - speed < 200) {
				x = 200;
			}
		}
		if (level.isBeginning()) {
			if (x - speed >= 48 && x <= 200) {
				x -= speed;
			} else {
				x = 48;
			}
		}
		level.smoothOutMovement(WALKSPEED);
	}

	public void walkRight(double speed) {
		if (level.isEnd()) {
			if (x + speed <= 835) {
				x += speed;
			} else if (x + speed > 835) {
				x = 835;
			}

		}

		if (level.isBeginning()) {
			if (x + speed <= 200) {
				x += speed;
			} else if (x + speed > 200) {
				x = 200;
			}
		}
		level.smoothOutMovement(WALKSPEED);
	}

	public void update() {
		if (left == true && right == false) {
			walkLeft(level.getSmooth());
			moveLeft();
		} else if (right == true && left == false) {
			walkRight(level.getSmooth());
			moveRight();
		} else if (right == true && left == true) {
			setStanding(true);
		}
		if (x == level.tileSize || x == 835) {
			setStanding(true);
		}
	}
	
	public void render() {
		draw(new ImageView());
	}

	public void draw(ImageView im) {
		if (isStanding == true && jumping == false && isFighting == false) {
			drawStanding(im);
		} else if (jumping == true) {
			drawJump(im);
		} else if (isFighting == true) {
			drawFight(im);

		} else {
			drawRunning(im);
		}

	}

	public void drawStanding(ImageView im) {
		if (TargetStandingCounter == 1 || TargetStandingCounter == 6) {
			im = standing[0];
		} else if (TargetStandingCounter == 2) {
			im = standing[1];
		} else if (TargetStandingCounter == 3) {
			im = standing[2];
		} else if (TargetStandingCounter == 4) {
			im = standing[3];
		} else if (TargetStandingCounter == 5) {
			im = standing[4];
		}

		im.setTranslateX(x); // 96 x 78
		im.setTranslateY(576);
		if (standingLeft == true) {
			im.setScaleX(-1); // Spiegelverkehrt
		} else if (standingLeft == false) {
			im.setScaleX(1);
		}
		g.getRoot().getChildren().add(im);
	}

	public void drawRunning(ImageView im) {
		if (jumping == false) {
			if (TargetRunningCounter == 1 || TargetRunningCounter == 9) {
				im = running[0];
			} else if (TargetRunningCounter == 2) {
				im = running[1];
			} else if (TargetRunningCounter == 3) {
				im = running[2];
			} else if (TargetRunningCounter == 4) {
				im = running[3];
			} else if (TargetRunningCounter == 5) {
				im = running[4];
			} else if (TargetRunningCounter == 6) {
				im = running[5];
			} else if (TargetRunningCounter == 7) {
				im = running[6];
			} else if (TargetRunningCounter == 8) {
				im = running[7];
			}
		}
		System.out.println(TargetRunningCounter);
		im.setTranslateX(x + 3);
		im.setTranslateY(576);
		if (left == true) {
			im.setScaleX(-1);
		} else if (left == false) {
			im.setScaleX(1);
		}
		g.getRoot().getChildren().add(im);
	}

	public void drawJump(ImageView im) {
		if (TargetJumpCounter <= 7) {
			im = jump[TargetJumpCounter];
		} else {
			im = drawFalling(im);
		}
		if (TargetJumpCounter != 1) {
			calculateJump();
		}

		im.setTranslateX(x);
		im.setTranslateY(y / 10);
		if (standingLeft == true) {
			im.setScaleX(-1);
			im.setTranslateX(x);
		} else if (standingLeft == false) {
			im.setScaleX(1);
		}
		g.getRoot().getChildren().add(im);
		System.out.println(TargetJumpCounter);
	}

	public ImageView drawFalling(ImageView im) {
		im = jump[TargetJumpCounter];
		return im;
	}

	public void calculateJump() {
		if (jumping) {
			calculateFalling();
		}
	}

	private void calculateFalling() {
		if (VELOCITY >= -FALLINGSPEEDMAX) {
			VELOCITY -= GRAVITY;
		}
		if (y - VELOCITY <= 5550) {
			y -= VELOCITY;
		} else {
			y = 5550;
			VELOCITY = 0;
			jumping = false;
		}
	}

	public void drawFight(ImageView im) {
		if (TargetFightCounter <= 7) {
			im = fight[TargetFightCounter - 1];
		} else {
			isFighting = false;
		}

		im.setTranslateX(x);
		im.setTranslateY(y / 10 - 3);
		if (standingLeft == true) {
			im.setScaleX(-1);
			im.setTranslateX(x - 30);
		} else if (standingLeft == false) {
			im.setScaleX(1);
		}
		g.getRoot().getChildren().add(im);
	}

	/**
	 * Loading the images for the player Standing, Running, Jumping, Fighting
	 * frames
	 * 
	 */
	public void loadImg() {
		standing = new ImageView[5];

		ImageView image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_1.gif")));
		standing[0] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_2.gif")));
		standing[1] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_3.gif")));
		standing[2] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_4.gif")));
		standing[3] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_5.gif")));
		standing[4] = image1;

		running = new ImageView[8];

		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_0_delay-0.13s.gif")));
		running[0] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_1_delay-0.13s.gif")));
		running[1] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_2_delay-0.13s.gif")));
		running[2] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_3_delay-0.13s.gif")));
		running[3] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_4_delay-0.13s.gif")));
		running[4] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_5_delay-0.13s.gif")));
		running[5] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_6_delay-0.13s.gif")));
		running[6] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_7_delay-0.13s.gif")));
		running[7] = image1;

		jump = new ImageView[15];

		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_0_delay-0.13s.gif")));
		jump[0] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_1_delay-0.13s.gif")));
		jump[1] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_2_delay-0.06s.gif")));
		jump[2] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_3_delay-0.06s.gif")));
		jump[3] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_4_delay-0.06s.gif")));
		jump[4] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_5_delay-0.06s.gif")));
		jump[5] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_6_delay-0.06s.gif")));
		jump[6] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_7_delay-0.06s.gif")));
		jump[7] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_8_delay-0.06s.gif")));
		jump[8] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_9_delay-0.03s.gif")));
		jump[9] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_10_delay-0.03s.gif")));
		jump[10] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_11_delay-0.13s.gif")));
		jump[11] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_12_delay-0.13s.gif")));
		jump[12] = image1;
		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_13_delay-0.06s.gif")));
		jump[13] = image1;

		fight = new ImageView[7];

		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/fight/frame_0_delay-0.33s.gif")));
		fight[0] = image1;

		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/fight/frame_1_delay-0.1s.gif")));
		fight[1] = image1;

		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/fight/frame_2_delay-0.1s.gif")));
		fight[2] = image1;

		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/fight/frame_3_delay-0.1s.gif")));
		fight[3] = image1;

		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/fight/frame_4_delay-0.1s.gif")));
		fight[4] = image1;

		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/fight/frame_5_delay-0.06s.gif")));
		fight[5] = image1;

		image1 = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/fight/frame_6_delay-0.1s.gif")));
		fight[6] = image1;

	}

	// GET SET Methods

	public boolean isFalling() {
		return isFalling;
	}

	public void setFalling(boolean falling) {
		this.isFalling = falling;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		if (this.left != left) {
			this.left = left;
			TargetRunningCounter = 1;
		}
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		if (this.right != right) {
			this.right = right;
			TargetRunningCounter = 1;
		}

	}

	/**
	 * @return the jumping
	 */
	public boolean isJumping() {
		return jumping;
	}

	/**
	 * @param jumping
	 *            the jumping to set
	 */
	public void setJumping(boolean jumping) {
		if (!this.jumping && jumping) {
			VELOCITY = JUMPSPEED;
			TargetJumpCounter = 1;
		}
		if (!isFighting)
			this.jumping = jumping;
	}

	public void setStanding(boolean standing) {
		this.isStanding = standing;
	}

	public boolean getStanding() {
		return isStanding;
	}

	public boolean isFighting() {
		return isFighting;
	}

	public void setFighting(boolean fighting) {
		if (this.isFighting != fighting && isJumping() != true) {
			this.isFighting = fighting;
			isStanding = true;
			left = false;
			right = false;
			level.setRight(false);
			level.setLeft(false);
			level.resetMovement();
			TargetFightCounter = 1;
		}
	}

	public void checkCounter(int targetCounter) {
		if (isStanding == true && jumping == false) {
			if (TargetStandingCounter == 6) {
				TargetStandingCounter = 1;
			}
			if (targetCounter % 10 == 0) {
				TargetStandingCounter++;
			}

		}
		if (jumping == true) {
			if (targetCounter % 8 == 0) {
				TargetJumpCounter++;
			}
		} else if (right == true || left == true) {
			if (TargetRunningCounter == 9) {
				TargetRunningCounter = 1;
			}

			if (targetCounter % 5 == 0) {
				TargetRunningCounter++;
			}
		}
		if (isFighting == true) {
			if (targetCounter % 5 == 0) {
				TargetFightCounter++;
			}
		}
	}
}
