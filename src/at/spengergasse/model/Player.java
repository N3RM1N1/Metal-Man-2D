package at.spengergasse.model;

import at.spengergasse.gui.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {

	// Walking speed, start and maximum walk speed. Player starts slower and
	// gets faster
	private final double WALKSPEED = 1.0;

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
	private double x = 192;

	private double y = 3750;
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
	final int tileSize;
	private int[][] map;

	public Player(FrameFX frame, TileMap level) {
		this.g = frame;
		isStanding = true;
		isFalling = false;
		right = false;
		left = false;
		jumping = false;
		this.level = level;
		this.tileSize = level.getTileSize();
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
		System.out.println(level.getSmooth());
		if(jumping && !isFalling) {
			if(!standingLeft) {
				if(right) {
					if(map[getYTiles()-2][(getXTiles()/tileSize)-1] == 2
							|| map[getYTiles()-2][(getXTiles()/tileSize)-1] == 3) {
						if(VELOCITY > 0) {
							VELOCITY = 0;
						}
						isFalling = true;
					}
				}
	 			if(map[getYTiles()-2][(getXTiles()/tileSize)-2] == 2 
	 					|| map[getYTiles()-2][(getXTiles()/tileSize)-2] == 3) {
					if(VELOCITY > 0) {
						VELOCITY = 0;
					}
					isFalling = true;
				}
			} else if(standingLeft) {
				if(map[getYTiles()-2][(getXTiles()/tileSize)] == 2 
						|| map[getYTiles()-2][(getXTiles()/tileSize)] == 3) {
					if(VELOCITY > 0) {
						VELOCITY = 0;
					}
					isFalling = true;
				}
			}
			if (TargetJumpCounter >= 1) {
				calculateJump();
			}

		} else if(isFalling) {
			if(standingLeft) {
				if(map[getYTiles()+1][((getXTiles()+20)/tileSize)-1] == 1 || map[getYTiles()+1][((getXTiles()+20)/tileSize)] == 1
						|| map[getYTiles()+1][((getXTiles()+20)/tileSize)-1] == 5 || map[getYTiles()+1][((getXTiles()+20)/tileSize)] == 5
						|| map[getYTiles()+1][((getXTiles()+20)/tileSize)-1] == 10 || map[getYTiles()+1][((getXTiles()+20)/tileSize)] == 10
						|| map[getYTiles()+1][((getXTiles()+20)/tileSize)-1] == 3 || map[getYTiles()+1][((getXTiles()+20)/tileSize)] == 3
						|| map[getYTiles()+1][((getXTiles()+20)/tileSize)-1] == 4 || map[getYTiles()+1][((getXTiles()+20)/tileSize)] == 4
						|| map[getYTiles()+1][((getXTiles()+20)/tileSize)-1] == 6 || map[getYTiles()+1][((getXTiles()+20)/tileSize)] == 6
						|| map[getYTiles()+1][((getXTiles()+20)/tileSize)-1] == 7 || map[getYTiles()+1][((getXTiles()+20)/tileSize)] == 7
						|| map[getYTiles()+1][((getXTiles()+20)/tileSize)-1] == 8 || map[getYTiles()+1][((getXTiles()+20)/tileSize)] == 8
						|| map[getYTiles()+1][((getXTiles()+20)/tileSize)-1] == 9 || map[getYTiles()+1][((getXTiles()+20)/tileSize)] == 9) {
					calculateHit = ((getYTiles())*(tileSize*10))-((tileSize*10)+10);
				} else  {
					calculateHit += FALLINGSPEEDMAX;
				}
			} else if(!standingLeft) {
				if(map[getYTiles()+1][((getXTiles()+30)/tileSize)-2] == 1 || map[getYTiles()+1][((getXTiles()+30)/tileSize)-3] == 1
						|| map[getYTiles()+1][((getXTiles()+30)/tileSize)-2] == 5 || map[getYTiles()+1][((getXTiles()+30)/tileSize)-3] == 5
						|| map[getYTiles()+1][((getXTiles()+30)/tileSize)-2] == 10 || map[getYTiles()+1][((getXTiles()+30)/tileSize)-3] == 10
						|| map[getYTiles()+1][((getXTiles()+30)/tileSize)-2] == 3 || map[getYTiles()+1][((getXTiles()+30)/tileSize)-3] == 3
						|| map[getYTiles()+1][((getXTiles()+30)/tileSize)-2] == 4 || map[getYTiles()+1][((getXTiles()+30)/tileSize)-3] == 4
						|| map[getYTiles()+1][((getXTiles()+30)/tileSize)-2] == 6 || map[getYTiles()+1][((getXTiles()+30)/tileSize)-3] == 6
						|| map[getYTiles()+1][((getXTiles()+30)/tileSize)-2] == 7 || map[getYTiles()+1][((getXTiles()+30)/tileSize)-3] == 7
						|| map[getYTiles()+1][((getXTiles()+30)/tileSize)-2] == 8 || map[getYTiles()+1][((getXTiles()+30)/tileSize)-3] == 8
						|| map[getYTiles()+1][((getXTiles()+30)/tileSize)-2] == 9 || map[getYTiles()+1][((getXTiles()+30)/tileSize)-3] == 9) {
					calculateHit = ((getYTiles())*(tileSize*10))-((tileSize*10)+10);
				} else {
					calculateHit += FALLINGSPEEDMAX;
				}
			}
			calculateJump();
		}
		
		if(left && !right) {
			
			if(map[getYTiles()][(getXTiles()/tileSize)-1] != 3 && map[getYTiles()-1][(getXTiles()/tileSize)-1] != 3 
					&& map[getYTiles()][getXTiles()/tileSize-1] != 1 && map[getYTiles()-1][(getXTiles()/tileSize)-1] != 1 
					&& map[getYTiles()][getXTiles()/tileSize-1] != 2 && map[getYTiles()-1][(getXTiles()/tileSize)-1] != 2) {
				walkLeft(level.getSmooth());
				moveLeft();
				
			} else {
				if(x == 192) {
					level.setX((int) (((getXTiles()/tileSize)-3)*tileSize)-1);
				} else if(level.getSmooth() > 1.0) {
					setX((((int)(x/tileSize))*tileSize)+(tileSize-1));
				} else {
					walkLeft(level.getSmooth());
					moveLeft();
				}
				
				setStanding(true);
				level.setLeft(left);
			} 
			System.out.println(true);
			
		} else if(right && !left) {
			
			if(map[getYTiles()][(getXTiles()/tileSize)-1] != 3 && map[getYTiles()-1][(getXTiles()/tileSize)-1] != 3 
					&& map[getYTiles()][getXTiles()/tileSize-1] != 1 && map[getYTiles()-1][(getXTiles()/tileSize)-1] != 1 
					&& map[getYTiles()][getXTiles()/tileSize-1] != 2 && map[getYTiles()-1][(getXTiles()/tileSize)-1] != 2) {
				walkRight(level.getSmooth());
				moveRight();
				
			} else {
				if(x == 192) {
					level.setX((int)(((getXTiles()/tileSize)-5)*tileSize)-28);
				} else if(level.getSmooth() > 1.0) {
					setX((((int)(x/tileSize))*tileSize)+20);
				} else {
					walkRight(level.getSmooth());
					moveRight();
				}
				setStanding(true);
				level.setRight(true);
			}
			 
		} else if(left && right) {
			setStanding(true);
		} else {
			left = false;
			right = false;
			setStanding(true);
			
		}
		
		// falling
		if(!standingLeft && !jumping) {
			if(map[getYTiles()+1][((getXTiles()+30)/tileSize)-2] == 0 && map[getYTiles()+1][((getXTiles()-15)/tileSize)-2] == 0) {
				isFalling = true;
			}
		} else if(standingLeft && !jumping) {
			if(map[getYTiles()+1][((getXTiles()-25)/tileSize)] == 0 && map[getYTiles()+1][((getXTiles()+16)/tileSize)] == 0) {
				isFalling = true;
			}
		}
		
		if(y/10 > g.getScene().getHeight()) {
			System.out.println(true);
			g.getGameLoop().stop();
			System.exit(0);
		}
		
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
//		im.setFitHeight(139);
//		im.setFitWidth(110);
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
//		im.setFitHeight(139);
//		im.setFitWidth(100);
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
		if (TargetJumpCounter <= 7) {
			im = jump[TargetJumpCounter+1];
		} else {
			im = drawFalling(im);
		}
		
//		im.setFitHeight(139);
//		im.setFitWidth(110);
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
		if(TargetJumpCounter > 7 && TargetJumpCounter < 14) {
			im = jump[TargetJumpCounter];
		}
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
//		im.setFitHeight(139);
//		im.setFitWidth(110);
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
		int position = ((int) this.y / (tileSize*10));
		position += 2;
		if(position < 14) {
			return position;
		}
		return 1;
		
	}

	/**
	 * Loading the images for the player Standing, Running, Jumping, Fighting
	 * frames
	 * 
	 */
	public void loadImg() {
		standing = new ImageView[5];

		standing[0] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_1.gif")));
		standing[1] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_2.gif")));
		standing[2] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_3.gif")));
		standing[3] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_4.gif")));
		standing[4] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/standing/player-frame_5.gif")));

		running = new ImageView[8];

		running[0] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_0_delay-0.13s.gif")));
		running[1] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_1_delay-0.13s.gif")));
		running[2] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_2_delay-0.13s.gif")));
		running[3] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_3_delay-0.13s.gif")));
		running[4] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_4_delay-0.13s.gif")));
		running[5] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_5_delay-0.13s.gif")));
		running[6] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_6_delay-0.13s.gif")));
		running[7] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/running/frame_7_delay-0.13s.gif")));

		jump = new ImageView[15];

		jump[0] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_0_delay-0.13s.gif")));

		jump[1] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_1_delay-0.13s.gif")));

		jump[2] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_2_delay-0.06s.gif")));

		jump[3] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_3_delay-0.06s.gif")));

		jump[4] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_4_delay-0.06s.gif")));

		jump[5] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_5_delay-0.06s.gif")));

		jump[6] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_6_delay-0.06s.gif")));

		jump[7] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_7_delay-0.06s.gif")));
		
		jump[8] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_8_delay-0.06s.gif")));

		jump[9] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_9_delay-0.03s.gif")));

		jump[10] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_10_delay-0.03s.gif")));

		jump[11] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_11_delay-0.13s.gif")));

		jump[12] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_12_delay-0.13s.gif")));

		jump[13] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/jump/frame_13_delay-0.06s.gif")));

		fight = new ImageView[7];

		fight[0] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/fight/frame_0_delay-0.33s.gif")));

		fight[1] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/fight/frame_1_delay-0.1s.gif")));

		fight[2] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/fight/frame_2_delay-0.1s.gif")));

		fight[3] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/fight/frame_3_delay-0.1s.gif")));

		fight[4] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/fight/frame_4_delay-0.1s.gif")));

		fight[5] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/fight/frame_5_delay-0.06s.gif")));

		fight[6] = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/frames/fight/frame_6_delay-0.1s.gif")));

	}

	// GET SET Methods

	public boolean isFalling() {
		return isFalling;
	}

	public void setFalling(boolean falling) {
		this.isFalling = falling;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
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
		if(!isFalling)
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
