package at.spengergasse.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import at.spengergasse.gui.Background;
import at.spengergasse.gui.FrameFX;
import at.spengergasse.gui.Sound;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileMap {
	// Game Instanz
	private FrameFX g;

	// Die Matrix der Map
	private int[][] map;

	// The Images will be saved here
	private Image[] tiles;

	// The coordinates
	private double x;
	private double y;
	// Height and width of the Map
	private int bufferedLength;
	private int mapHeight;
	private int mapLength;

	// Boolean value for moving to the left or the right
	private boolean left;
	private boolean right;

	// The size of the tile
	public int tileSize;

	private int col;

	private double smooth = 0.0;

	private ArrayList<Enemies> enemies;
	private ArrayList<Extensions> extensions;
	private ArrayList<Background> stars;
	
	private Sound soundEffects;

	private String levelName;

	public TileMap(int tileSize, String levelName, FrameFX frame, Sound effects) {

		// setting the coordinates for the tiles
		this.x = tileSize;
		this.y = tileSize;
		this.levelName = levelName;
		this.extensions = new ArrayList<>();
		this.stars = new ArrayList<>();
		this.enemies = new ArrayList<>();
		
		this.soundEffects = effects;

		left = false;
		right = false;

		col = 0;

		// Creating a new Game
		this.g = frame;

		// Setting the tile size
		this.tileSize = tileSize;

		loadImg();

		// Initialization of the map
		try {
			initGameMap();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 30; i++) {
			stars.add(new Background(g));
		}
	}

	/**
	 * Reading Method This method is using a BufferedReaeder to read the matrix
	 * of the game map Only reading the coordinates of the tiles No drawing
	 * 
	 * @throws IOException
	 * @throws NumberFormatException
	 * 
	 */
	public void initGameMap() throws NumberFormatException, IOException {
		try {
			FileReader fr = new FileReader("src/at/spengergasse/mapLevels/" + levelName);
			BufferedReader br = new BufferedReader(fr);

			mapLength = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			map = new int[mapHeight][mapLength];

			for (int row = 0; row < mapHeight; row++) {
				String line = br.readLine();
				String[] zeichen = line.split(" ");
				for (int col = 0; col < mapLength; col++) {
					map[row][col] = Integer.parseInt(zeichen[col]);
					if (map[row][col] == 11) {
						Extensions ext = new Extensions(g, col * tileSize, row * tileSize, col, soundEffects);
						extensions.add(ext);
					} else if(map[row][col] == 12) {
						Enemies en = new Enemies(this.g, row*tileSize-43);
						enemies.add(en);
					}
				}
			}
			col = 0;
			bufferedLength = 25;
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if (left == true && right == false) {
			left(smooth);
			for (Extensions e : extensions) {
				e.left(smooth);
			}
			smoothOutMovement(0.3);
		} else if (right == true && left == false) {
			right(smooth);
			for (Extensions e : extensions) {
				e.right(smooth);
			}
			smoothOutMovement(0.3);
		}
		for(int i = 0; i < extensions.size(); i ++) {
			if(extensions.get(i).getOpacity() <= 0) {
				extensions.remove(i);
			}
		}
		

	}

	public void extUpdate() {
		for (Extensions e : extensions) {
			e.checkCounter(g.getTargetFrameCounter());
		}
		
		for (Enemies en : enemies) {
			en.checkCounter(g.getTargetFrameCounter());
			en.move();
		}
	}

	public void render() {
		draw(new ImageView());

	}

	public void draw(ImageView im) {
		if (this.x > (col + 2) * tileSize) {
			if (bufferedLength < 120) {
				col++;
				bufferedLength++;
			}
		} else if (this.x < (col + 1) * tileSize) {
			if (col > 0) {
				col--;
				bufferedLength--;
			}
		}

		// stars for the background
		for (Background b : stars) {
			b.render();
		}

		for (int row = 0; row < mapHeight; row++) { // mapHeight
			for (int i = col; i < bufferedLength; i++) { // buffered map length
				int rc = map[row][i];
				if (rc != 0 && rc < 11) {
					im = new ImageView(tiles[rc - 1]);
					im.setFitHeight(tileSize);
					im.setFitWidth(tileSize);
					im.setLayoutX(tileSize);
					im.setLayoutY(tileSize);
					im.setTranslateX((i * tileSize) - x); // 352
					im.setTranslateY((row * tileSize) - y); // 288
					g.getRoot().getChildren().add(im);
				} else if(rc == 11) {
					if(!extensions.isEmpty()) {
						for (Extensions e : extensions) {
							if(i == e.getCol())
								e.draw(new ImageView(), (i*48)-(x-48));
						}
					}
				} else if(rc == 12) {
					for (Enemies en : enemies) {
						if(en.isRight()) {
							en.draw(new ImageView(), (i*48)-(x-48));
						} else if(en.isLeft()) {
							en.draw(new ImageView(), (i*48)-(x-48));
						}	
					}
				}
			}
		}

	}

	public void smoothOutMovement(double inc) {
		if (smooth < 7) {
			smooth += inc;
		} else if (smooth > 7) {
			smooth = 7;
		}
	}

	public void resetMovement() {
		smooth = 1.0;

	}

	public double getSmooth() {
		return smooth;
	}
	
	public void collect(int col) {
		if(!extensions.isEmpty()) {
			for(Extensions e : extensions) {
				if(e.getCol() == col || e.getCol()-1 == col) {
					e.setCollected(true);
				}
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getTileSize() {
		return this.tileSize;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		if (x >= tileSize) {
			left = false;
			right = false;
			resetMovement();
			this.x = x;
		}
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean getLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean getRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void right(double inc) {
		if (x <= (mapLength - 19) * tileSize) {
			if ((x + inc) <= (mapLength - 19) * tileSize) {
				x += inc;
			} else {
				x = (mapLength - 19) * tileSize;
			}
		}
		if (inc > 1.0) {
			for (Background b : stars) {
				b.right();
			}
		}

	}

	public void left(double inc) {
		if (x > tileSize) {
			if ((x - inc) >= tileSize) {
				x -= inc;
			} else {
				x = tileSize;
			}
		}
		if (inc > 1.0) {
			for (Background b : stars) {
				b.left();
			}
		}

	}

	public boolean isBeginning() {
		if (x <= tileSize) {
			return true;
		}
		return false;
	}

	public boolean isEnd() {
		if (x >= (mapLength - 19) * tileSize) {
			return true;
		}
		return false;
	}

	public int getMapLength() {
		return this.mapLength;
	}

	public int[][] getMap() {
		int[][] mapInPx = new int[mapHeight*tileSize][mapLength*tileSize];
		int collision = 0;
		
		for(int row = 0; row < mapHeight; row ++) {
			for(int col = 0; col < mapLength; col ++) {
				if(this.map[row][col] != 0) {
					collision = 1;
				}
				for(int i = row*tileSize; i < row*tileSize+tileSize; i ++) {
					for(int j = col*tileSize; j < col*tileSize+tileSize; j ++) {
						mapInPx[i][j] = collision;
					}
				}
				collision = 0;
			}
		}
		
		return mapInPx;
	}

	public int getCol() {
		return this.col;
	}

	public int getRow() {
		return mapHeight;
	}

	public void loadImg() {
		tiles = new Image[11];

		tiles[0] = new Image(getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/untergrund.gif"));
		tiles[1] = new Image(getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/decke.gif"));
		tiles[2] = new Image(getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/block.gif"));
		tiles[3] = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/einzelblock_untergrund.gif"));
		tiles[4] = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/rechts_untergrund.gif"));
		tiles[5] = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/fliegende_platform.gif"));
		tiles[6] = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/links_fliegende_platform.gif"));
		tiles[7] = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/links_untergrund.gif"));
		tiles[8] = new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/map/textures/rechts_fliegende_platform.gif"));
		tiles[9] = new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/map/textures/fliegende_platform_einzelblock.gif"));
	}

}