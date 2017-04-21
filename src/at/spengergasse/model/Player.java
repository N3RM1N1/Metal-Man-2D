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
	private final double JUMPSPEED = 160;
	private double VELOCITY = 0;
	private final double FALLINGSPEEDMAX = 240;

	// The gravity --> used for falling to increase slowly the falling speed
	private final double GRAVITY = 5;

	// The coordinates of the player
	private static double x = 192;

	

	public static double getX() {
		return x;
	}

	public static void setX(double x) {
		Player.x = x;
	}

	private double y = 5750;
	private double calculateHit = y;

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

	private int[][] map;

	public Player(FrameFX frame, TileMap level) {
		this.g = frame;
		isStanding = true;
		isFalling = false;
		right = false;
		left = false;
		jumping = false;
		this.level = level;
		map = level.getMap();
		loadImg();
	}

	public void moveLeft() {
		left = true;
		right = false;
		standingLeft = true;
		isStanding = false;
	}

	public void moveRight() {
		left = false;
		right = true;
		standingLeft = false;
		isStanding = false;
	}

	public void walkLeft(double speed) {
		if (level.isEnd()) {
			if (x - speed >= 192) {
				x -= speed;
			} else if (x - speed < 192) {
				x = 192;
			}
		}
		if (level.isBeginning()) {
			x -= speed;
		}
		level.smoothOutMovement(WALKSPEED);
	}

	public void walkRight(double speed) {
		if (level.isEnd()) {
			x += speed;
		}

		if (level.isBeginning()) {
			if (x + speed <= 192) {
				x += speed;
			} else if (x + speed > 192) {
				x = 192;
			}
		}
		level.smoothOutMovement(WALKSPEED);
	}

	public void update() {
//		if (jumping) {
//
//		} else if (isFalling) {
//
//		}
//
//		if (left && !right) {
//			
//			if(map[getYTiles()][getXTiles()/48-1] != 3) {
//				
//				walkLeft(level.getSmooth());
//				moveLeft();
//				calculateHit = ((((int)getXTiles()/48)-4)*48);
//				
//			} else if(level.getX() - level.getSmooth() > calculateHit) {
//				
//				walkLeft(WALKSPEED);
//				moveLeft();
//				
//			} else {
//				level.setX(calculateHit);
//				setStanding(true);
//				level.setLeft(left);
//			}
//			
//		} else if (right && !left) {
//			if (map[getYTiles()][(int) (getXTiles() / 48)-1] != 3) { 
//				System.out.println("ja");
//				walkRight(level.getSmooth());
//				moveRight();
//				calculateHit = ((((int)getXTiles()/48)-5)*48)+21;
//
//			} else if (level.getX() + level.getSmooth() < calculateHit) {
//				walkRight(level.getSmooth());
//				moveRight();
//
//			} else {
//				
//				level.setX(calculateHit);
//				setStanding(true);
//				level.setRight(right);
//			}
//		} else if (left && right) {
//			setStanding(true);
//		} else {
//			setStanding(true);
//		}
		
		if(jumping && !isFalling) {
			if(!standingLeft) {
	 			if(map[getYTiles()-2][(getXTiles()/48)-2] == 2 
	 					|| map[getYTiles()-2][(getXTiles()/48)-2] == 3) {
					if(VELOCITY > 0) {
						VELOCITY = 0;
					}
				}
			} else if(standingLeft) {
				if(map[getYTiles()-2][(getXTiles()/48)] == 2 
						|| map[getYTiles()-2][(getXTiles()/48)] == 3) {
					if(VELOCITY > 0) {
						VELOCITY = 0;
					}
				}
			}
			if (TargetJumpCounter != -1) {
				calculateJump();
			}

		} else if(isFalling) {
			if(standingLeft) {
				if(map[getYTiles()+1][((getXTiles()+20)/48)-1] == 1 || map[getYTiles()+1][((getXTiles()+20)/48)] == 1
						|| map[getYTiles()+1][((getXTiles()+20)/48)-1] == 5 || map[getYTiles()+1][((getXTiles()+20)/48)] == 5
						|| map[getYTiles()+1][((getXTiles()+20)/48)-1] == 10 || map[getYTiles()+1][((getXTiles()+20)/48)] == 10
						|| map[getYTiles()+1][((getXTiles()+20)/48)-1] == 3 || map[getYTiles()+1][((getXTiles()+20)/48)] == 3) {
					calculateHit = ((getYTiles())*480)-490;
					System.out.println(calculateHit);
				} else  {
					calculateHit += 48;
				}
			} else if(!standingLeft) {
				if(map[getYTiles()+1][((getXTiles()+30)/48)-2] == 1 || map[getYTiles()+1][((getXTiles()+30)/48)-3] == 1
						|| map[getYTiles()+1][((getXTiles()+30)/48)-2] == 5 || map[getYTiles()+1][((getXTiles()+30)/48)-3] == 5
						|| map[getYTiles()+1][((getXTiles()+30)/48)-2] == 10 || map[getYTiles()+1][((getXTiles()+30)/48)-3] == 10
						|| map[getYTiles()+1][((getXTiles()+30)/48)-2] == 3 || map[getYTiles()+1][((getXTiles()+30)/48)-3] == 3) {
					calculateHit = ((getYTiles())*480)-490;
					System.out.println(calculateHit);
				} else {
					calculateHit += 48;
				}
			}
			if (TargetJumpCounter != -1) {
				calculateJump();
			}
		}
		
		if(left && !right) {
			
			if(map[getYTiles()][(getXTiles()/48)-1] != 3 && map[getYTiles()-1][(getXTiles()/48)-1] != 3 
					&& map[getYTiles()][getXTiles()/48-1] != 1 && map[getYTiles()-1][(getXTiles()/48)-1] != 1 
					&& map[getYTiles()][getXTiles()/48-1] != 2 && map[getYTiles()-1][(getXTiles()/48)-1] != 2) {
				walkLeft(level.getSmooth());
				moveLeft();
				
			} else {
				if(x == 192) {
					level.setX((int) (((getXTiles()/48)-3)*48)-1);
				} else {
					setX((((int)(x/48))*48)+47);
				}
				
				setStanding(true);
				level.setLeft(left);
			} 
			
		} else if(right && !left) {
			
			if(map[getYTiles()][(getXTiles()/48)-1] != 3 && map[getYTiles()-1][(getXTiles()/48)-1] != 3 
					&& map[getYTiles()][getXTiles()/48-1] != 1 && map[getYTiles()-1][(getXTiles()/48)-1] != 1 
					&& map[getYTiles()][getXTiles()/48-1] != 2 && map[getYTiles()-1][(getXTiles()/48)-1] != 2) {
				walkRight(level.getSmooth());
				moveRight();
				
			} else {
				if(x == 192) {
					level.setX((int)(((getXTiles()/48)-5)*48)-28);
				} else {
					setX((((int)(x/48))*48)+20);
				}
				setStanding(true);
				level.setRight(right);
			}
			if(map[getYTiles()+1][((getXTiles()+30)/48)-2] == 1 || map[getYTiles()+1][((getXTiles()+30)/48)-3] == 1) {
				
			}
			 
		} else if(left && right) {
			setStanding(true);
		} else {
			setStanding(true);
		}

		if (x == level.tileSize || x == 835) {
			setStanding(true);
		}
		
		if(!standingLeft) {
			if(map[getYTiles()+1][((getXTiles()+30)/48)-2] == 0 && map[getYTiles()+1][((getXTiles()-15)/48)-2] == 0) {
				isFalling = true;
			}
		} else if(standingLeft) {
			if(map[getYTiles()+1][((getXTiles())/48)] == 1 || map[getYTiles()+1][((getXTiles()+15)/48)] == 1) {
				System.out.println("asfijfnkkfnsnalkfkklk");
			}
		}
		
//		System.out.println("X Tiles: " + getXTiles()/48);
//		System.out.println("Level x: " + level.getX());
//		System.out.println("X: " + x);
//		System.out.println("Left: " + left);
//		System.out.println("Right: " + right + "\n");
//		System.out.println(y);
//		System.out.println("Falling: " + isFalling);
//		System.out.println();
//		System.out.println(calculateHit);
		
	}

	public void render() {
		draw(new ImageView());
	}

	public void draw(ImageView im) {
		if (isStanding == true && jumping == false && isFighting == false) {
			drawStanding(im);
		} else if (jumping == true || isFalling == true) {
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
		im.setTranslateY(y / 10);
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
		im.setTranslateX(x + 3);
		im.setTranslateY(y / 10);
		if (left == true) {
			im.setScaleX(-1);
		} else if (left == false) {
			im.setScaleX(1);
		}
		g.getRoot().getChildren().add(im);
	}

	public void drawJump(ImageView im) {
		if (TargetJumpCounter <= 7 && !isFalling) {
			im = jump[TargetJumpCounter+1];
		} else {
			im = drawFalling(im);
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
	}

	public ImageView drawFalling(ImageView im) {
		im = jump[TargetJumpCounter];
		return im;
	}

	public void calculateJump() {
		calculateFalling();
	}

	private void calculateFalling() {
		if (VELOCITY >= -FALLINGSPEEDMAX) {
			VELOCITY -= GRAVITY;
			if(VELOCITY == 0) {
				isFalling = true;
			}
		}
		if (y - VELOCITY <= calculateHit) {
			y -= VELOCITY;
		} else {
			y = calculateHit;
			VELOCITY = 0;
			jumping = false;
			isFalling = false;
		}
	}

	public void drawFight(ImageView im) {
		if (TargetFightCounter <= 7) {
			im = fight[TargetFightCounter - 1];
		} else {
			isFighting = false;
		}

		im.setTranslateX(x);
		im.setTranslateY((y / 10) - 23);
		if (standingLeft == true) {
			im.setScaleX(-1);
			im.setTranslateX(x - 30);
		} else if (standingLeft == false) {
			im.setScaleX(1);
		}
		g.getRoot().getChildren().add(im);
	}

	public int getXTiles() {
		int position = 1;
		if (standingLeft) {
			position = (int) (x + level.getX());
		} else {
			position = (int) (x + level.getX()) + 76;
		}
		return position;
	}

	public int getYTiles() {
		int position = (int) this.y / 480;
		position += 2;
		return position;
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
		this.left = left;
		
		if(left != right) {
			standingLeft=true;
		}
		
		TargetRunningCounter = 1;	
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
		standingLeft=false;
		
		TargetRunningCounter = 1;
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
