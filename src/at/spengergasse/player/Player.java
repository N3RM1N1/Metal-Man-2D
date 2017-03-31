package at.spengergasse.player;

import at.spengergasse.game.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {

	// Walking speed, start and maximum walk speed. Player starts slower and
	// gets faster
	private final double ENDWALKSPEED = 5.0;
	private final double WALKSPEED = 0.3;

	// Boolean Values for standing and falling
	private boolean isStanding;
	private boolean isFalling;
	private boolean goIn = true;

	// Jumping speed, Falling speed
	private final double JUMPSPEED = 4;
	private double VELOCITY = 0;
	private final double JUMPSPEEDMAX = 140;
	private final double FALLINGSPEEDMAX = 60;

	// The gravity --> used for falling to increase slowly the falling speed
	private final double GRAVITY = 5;

	// The coordinates of the player
	private double x = 200;
	private double y = 5550;

	// The counter for the Frames
	private static int TargetStandingCounter = 1;
	private static int TargetRunningCounter = 1;
	private static int TargetJumpCounter = 1;

	// TileMap, for getting important map values and moving the map with the
	// player
	private Game g;

	private ImageView[] standing;
	private ImageView[] running;
	private ImageView[] jump;

	private boolean left;
	private boolean right;
	private boolean jumping;

	public Player() {
		g = new Game();
		isStanding = true;
		isFalling = false;
		loadImg();
		right = false;
		left = false;
		jumping = false;
	}

	public void walk(boolean right) {
		if (right) {
			moveRight();
		} else {
			moveLeft();
		}
	}

	public void moveLeft() {
		if (isStanding == true) {
			g.setCounter(1);
			left = true;
			right = false;
			isStanding = false;
		}
	}

	public void moveRight() {
		if (isStanding == true) {
			g.setCounter(1);
			left = false;
			right = true;
			isStanding = false;
		}
	}

	public void draw(ImageView im) {
		if (isStanding == true && jumping == false) {
			drawStanding(im);
		} else if (jumping == true) {
			drawJump(im);
		} else {
			drawRunning(im);
		}

	}

	public void drawStanding(ImageView im) {
		if (TargetStandingCounter == 1 || TargetStandingCounter == 6) {
//			Image image = standing[0];
			im = standing[0];
		} else if (TargetStandingCounter == 2 || TargetStandingCounter == 7) {
//			Image image = standing[1];
			im = standing[1];
		} else if (TargetStandingCounter == 3 || TargetStandingCounter == 8) {
//			Image image = standing[2];
			im = standing[2];
		} else if (TargetStandingCounter == 4) {
//			Image image = standing[3];
			im = standing[3];
		} else if (TargetStandingCounter == 5) {
//			Image image = standing[4];
			im = standing[4];
		}

		im.setTranslateX(200); // 96 x 78
		im.setTranslateY(576);
//		im.setFitHeight(88);
//		im.setFitWidth(82.5);
		if (left == true) {
			im.setScaleX(-1); // Spiegelverkehrt
		} else if(right == true) {
			im.setScaleX(1);
		}
		g.getGroup().getChildren().add(im);
	}

	public void drawRunning(ImageView im) {
		if (isFalling == false && jumping == false) {
			if (TargetRunningCounter == 1 || TargetRunningCounter == 9) {
//				Image image = running[0];
				im = running[0];
			} else if (TargetRunningCounter == 2) {
//				Image image = running[1];
				im = running[1];
			} else if (TargetRunningCounter == 3) {
//				Image image = running[2];
				im = running[2];
			} else if (TargetRunningCounter == 4) {
//				Image image = running[3];
				im = running[3];
			} else if (TargetRunningCounter == 5) {
//				Image image = running[4];
				im = running[4];
			} else if (TargetRunningCounter == 6) {
//				Image image = running[5];
				im = running[5];
			} else if (TargetRunningCounter == 7) {
//				Image image = running[6];
				im = running[6];
			} else if (TargetRunningCounter == 8) {
//				Image image = running[7];
				im = running[7];
			}
		}
		im.setTranslateX(200);
		im.setTranslateY(576);
		if (left == true) {
			im.setScaleX(-1);
		} else if(right == true){
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
			isFalling = true;
		} else {
			im = drawFalling(im);
		}
		if(TargetJumpCounter != 1) {
			calculateJump();
		}
		
		im.setTranslateX(203);
		im.setTranslateY(y/10);
		if (left == true) {
			im.setScaleX(-1);
			im.setTranslateX(191);
		}
		System.out.println(VELOCITY);
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
		} else if(TargetJumpCounter == 13) {
			im = jump[13];
		}
		return im;
	}
	
	public void calculateJump() {
		x++;
		if(VELOCITY < JUMPSPEEDMAX && goIn == true) {
			VELOCITY = JUMPSPEEDMAX;
		} else if(VELOCITY <= 0) {
			calculateFalling();
		} else if(isFalling == false){
			goIn = false;
			VELOCITY -= JUMPSPEED;
			y -= VELOCITY;
		} 
	}

	private void calculateFalling() {
		if(VELOCITY <= 0 && VELOCITY >= -FALLINGSPEEDMAX) {
			VELOCITY -= GRAVITY;
		}
		if(y-VELOCITY <= 5550) {
			y -= VELOCITY;
		} else {
			y = 5550;
			VELOCITY = 0;
		}
	}

	/**
	 * Loading the images for the player Standing, Running, Jumping, Fighting
	 * frames
	 * 
	 */
	public void loadImg() {
		standing = new ImageView[5];

		ImageView image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_1.gif")));
		standing[0] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_2.gif")));
		standing[1] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_3.gif")));
		standing[2] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_4.gif")));
		standing[3] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_5.gif")));
		standing[4] = image1;

		running = new ImageView[8];
		
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_0_delay-0.13s.gif")));
		running[0] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_1_delay-0.13s.gif")));
		running[1] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_2_delay-0.13s.gif")));
		running[2] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_3_delay-0.13s.gif")));
		running[3] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_4_delay-0.13s.gif")));
		running[4] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_5_delay-0.13s.gif")));
		running[5] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_6_delay-0.13s.gif")));
		running[6] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_7_delay-0.13s.gif")));
		running[7] = image1;
		

		jump = new ImageView[15];

		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_0_delay-0.13s.gif")));
		jump[0] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_1_delay-0.13s.gif")));
		jump[1] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_2_delay-0.06s.gif")));
		jump[2] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_3_delay-0.06s.gif")));
		jump[3] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_4_delay-0.06s.gif")));
		jump[4] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_5_delay-0.06s.gif")));
		jump[5] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_6_delay-0.06s.gif")));
		jump[6] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_7_delay-0.06s.gif")));
		jump[7] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_8_delay-0.06s.gif")));
		jump[8] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_9_delay-0.03s.gif")));
		jump[9] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_10_delay-0.03s.gif")));
		jump[10] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_11_delay-0.13s.gif")));
		jump[11] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_12_delay-0.13s.gif")));
		jump[12] = image1;
		image1 = new ImageView(new Image(getClass().getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_13_delay-0.06s.gif"))); 
		jump[13] = image1;

	}

	// GET SET Methods

	public double getWalkSpeed() {
		return WALKSPEED;
	}

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
			this.jumping = jumping;
	}

	public void setStanding(boolean standing) {
		this.isStanding = standing;
	}

	public boolean getStanding() {
		return isStanding;
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
			if (TargetJumpCounter == 14) {
				TargetJumpCounter = 0;
				jumping = false;
				isFalling = false;
				resetCounter();
			}
		} else if(right == true || left == true){
			if (TargetRunningCounter == 9) {
				TargetRunningCounter = 1;
			}
			if (targetCounter % 5 == 0) {
				TargetRunningCounter++;
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
