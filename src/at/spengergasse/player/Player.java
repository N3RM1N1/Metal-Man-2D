package at.spengergasse.player;

import at.spengergasse.game.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {

	// Walking speed, start and maximum walk speed. Player starts slower and
	// gets faster
	private final double ENDWALKSPEED = 7.0;
	private final double WALKSPEED = 1.0;

	// Boolean Values for standing and falling
	private boolean isStanding;
	private boolean isFalling;
	private boolean fighting;

	// Jumping speed, Falling speed
	private final double JUMPSPEED = 140;
	private double VELOCITY = 0;
	private final double JUMPSPEEDMAX = 140;
	private final double FALLINGSPEEDMAX = 60;

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
	private static int TargetStandingCounter = 1;
	private static int TargetRunningCounter = 1;
	private static int TargetJumpCounter = 1;
	private static int TargetFightCounter = 1;

	// TileMap, for getting important map values and moving the map with the
	// player
	private Game g;

	private ImageView[] standing;
	private ImageView[] running;
	private ImageView[] jump;
	private ImageView[] fight;
	
	private boolean left;
	private boolean right;
	private boolean standingLeft;
	private boolean jumping;

	public Player() {
		g = new Game();
		isStanding = true;
		isFalling = false;
		loadImg();
		right = false;
		left = false;
		jumping = false;
		x = 200;
	}

	public void moveLeft() {
		if (isStanding == true) {
			left = true;
			right = false;
			isStanding = false;
			standingLeft = true;
		}
	}

	public void moveRight() {
		if (isStanding == true) {
			left = false;
			right = true;
			isStanding = false;
			standingLeft = false;
		}
	}

	public void walkLeft(double speed) {
		if(TileMap.isEnd()) {
			if (x - speed >= 200) {
				x -= speed;
			} else if (x - speed < 200) {
				x = 200;
			}
		}
		if(TileMap.isBeginning()) {
			if (x - speed >= 48 && x <= 200) {
				x -= speed;
			} else {
				x = 48;
			}
		}
		TileMap.smoothOutMovement(WALKSPEED);
	}

	public void walkRight(double speed) {
		if(TileMap.isEnd()) {
			if (x + speed <= 835) {
				x += speed;
			} else if (x + speed > 835) {
				x = 835;
			}
			
		}
		
		if(TileMap.isBeginning()) {
			if (x + speed <= 200) {
				x += speed;
			} else if (x + speed > 200) {
				x = 200;
			}
		}
		TileMap.smoothOutMovement(WALKSPEED);
	}

	public void update() {
		if (left == true && right == false) {
			walkLeft(TileMap.getSmooth());
			moveLeft();
		} else if (right == true && left == false) {
			walkRight(TileMap.getSmooth());
			moveRight();
		} else if (right == true && left == true) {
			setStanding(true);
		}
		if(x == TileMap.tileSize || x == 835) {
			setStanding(true);
		}
	}

	public void draw(ImageView im) {
		if (isStanding == true && jumping == false && fighting == false) {
			drawStanding(im);
		} else if (jumping == true) {
			drawJump(im);
		} else if(fighting == true) {
			drawFight(im);
			System.out.println("true");
		} else {
			drawRunning(im);
		}
		System.out.println(y);

	}

	public void drawStanding(ImageView im) {
		if (TargetStandingCounter == 1 || TargetStandingCounter == 6) {
			im = standing[0];
		} else if (TargetStandingCounter == 2 || TargetStandingCounter == 7) {
			im = standing[1];
		} else if (TargetStandingCounter == 3 || TargetStandingCounter == 8) {
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
		g.getGroup().getChildren().add(im);
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
		im.setTranslateX(x + 3);
		im.setTranslateY(576);
		if (left == true) {
			im.setScaleX(-1);
		} else if (left == false) {
			im.setScaleX(1);
		}
		g.getGroup().getChildren().add(im);
	}

	public void drawJump(ImageView im) {
		if (TargetJumpCounter == 1) {
			im = jump[1];
		} else if (TargetJumpCounter == 2) {
			im = jump[2];
		} else if (TargetJumpCounter == 3) {
			im = jump[3];
		} else if (TargetJumpCounter == 4) {
			im = jump[4];
		} else if (TargetJumpCounter == 5) {
			im = jump[5];
		} else if (TargetJumpCounter == 6) {
			im = jump[6];
		} else if (TargetJumpCounter == 7) {
			im = jump[7];
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
		g.getGroup().getChildren().add(im);
	}

	public ImageView drawFalling(ImageView im) {
		if (TargetJumpCounter == 8) {
			im = jump[8];
		} else if (TargetJumpCounter == 9) {
			im = jump[9];
		} else if (TargetJumpCounter == 10) {
			im = jump[10];
		} else if (TargetJumpCounter == 11) {
			im = jump[11];
		} else if (TargetJumpCounter == 12) {
			im = jump[12];
		} else if (TargetJumpCounter >= 13) {
			im = jump[13];
		}
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
		if (TargetFightCounter == 1) {
			im = fight[0];
		} else if (TargetFightCounter == 2) {
			im = fight[1];
		} else if (TargetFightCounter == 3) {
			im = fight[2];
		} else if (TargetFightCounter == 4) {
			im = fight[3];
		} else if (TargetFightCounter == 5) {
			im = fight[4];
		} else if (TargetFightCounter == 6) {
			im = fight[5];
		} else if(TargetFightCounter == 7) {
			im = fight[6];
			fighting = false;
		}
		
		im.setTranslateX(x);
		im.setTranslateY(y / 10 - 3);
		if (standingLeft == true) {
			im.setScaleX(-1);
			im.setTranslateX(x);
		} else if (standingLeft == false) {
			im.setScaleX(1);
		}
		g.getGroup().getChildren().add(im);
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

	public boolean isStandingLeft() {
		return standingLeft;
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
		if(!this.jumping && jumping) {
			VELOCITY = JUMPSPEED;
			TargetJumpCounter = 1;
		}
		this.jumping = jumping;
		
	}

	public void setStanding(boolean standing) {
		this.isStanding = standing;
	}

	public boolean getStanding() {
		return isStanding;
	}
	
	public boolean isFighting() {
		return fighting;
	}

	public void setFighting(boolean fighting) {
		if(this.fighting != fighting) {
			this.fighting = fighting;
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
			if (targetCounter % 8 == 0 && targetCounter > 0) {
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
		if(fighting == true) {
			if(targetCounter % 5 == 0 && targetCounter > 0) {
				TargetFightCounter ++;
			}
		}
	}

	public void resetCounter() {
		TargetRunningCounter = 1;
		TargetStandingCounter = 1;
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------
	// Target Running or standing counter for the frames

	public int getTargetStandingCounter() {
		return TargetStandingCounter;
	}

	public void setTargetStandingCounter(int targetStandingCounter) {
		TargetStandingCounter = targetStandingCounter;
	}

	public int getTargetRunningCounter() {
		return TargetRunningCounter;
	}

	public void setTargetRunningCounter(int targetRunningCounter) {
		TargetRunningCounter = targetRunningCounter;
	}

	public static int getTargetJumpCounter() {
		return TargetJumpCounter;
	}

	public static void setTargetJumpCounter(int targetJumpCounter) {
		TargetJumpCounter = targetJumpCounter;
	}

}
