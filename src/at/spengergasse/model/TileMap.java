package at.spengergasse.model;

import java.io.FileReader;
import java.io.IOException;

import at.spengergasse.gui.FrameFX;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileMap {
	// Game Instanz
	private FrameFX g;

	// Die Matrix der Map
	private static int[][] map;

	// The Images will be saved here
	private Image[] tiles;

	// The coordinates
	private double x;
	private double y;
	// Height and width of the Map
	private static int mapWidth;
	private static int mapHeight;
	private static int mapLength;

	// Boolean value for moving to the left or the right
	private boolean left;
	private boolean right;

	// The size of the tile
	public int tileSize;

	private int col;

	private double smooth = 0.0;
	
	private String levelName;

	public TileMap(int tileSize, String levelName, FrameFX frame) {

		// setting the coordinates for the tiles
		this.x = tileSize;
		this.y = tileSize;
		this.levelName = levelName;

		left = false;
		right = false;

		col = 0;

		// Creating a new Game
		this.g = frame;

		// Setting the tile size
		this.tileSize = tileSize;

		// Initialization of the map
		try {
			initGameMap();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadImg();
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
				}
			}
			col = 0;
			mapWidth = 21;
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if(Player.getX() == 200) {
			if (left == true && right == false) {
				left(smooth);
				smoothOutMovement(0.3);
			} else if (right == true && left == false) {
				right(smooth);
				smoothOutMovement(0.3);
			}
		}
	}
	
	public void render() {
		draw(new ImageView());
	}

	public void smoothOutMovement(double inc) {
		if (smooth < 7) {
			smooth += inc;
		}
		if (smooth > 7) {
			smooth = 7;
		}
	}

	public void resetMovement() {
		smooth = 0.0;
	}

	public double getSmooth() {
		return smooth;
	}

	/**
	 * Drawing Method This method draws the map of the game
	 * 
	 * @param im
	 *            The ImageView, which represents the map
	 */
	public void draw(ImageView im) {
		if (this.x > (col + 2) * tileSize) {
			if (mapWidth < 120) {
				col++;
				mapWidth++;
			}
		} else if (this.x < (col + 1) * tileSize) {
			if (col > 0) {
				col--;
				mapWidth--;
			}
		}
		for (int row = 0; row < mapHeight; row++) { // mapHeight
			for (int i = col; i < mapWidth; i++) { // mapWidth
				int rc = map[row][i];
				if (rc != 0) {
					im = new ImageView(tiles[rc - 1]);
					im.setFitHeight(tileSize);
					im.setFitWidth(tileSize);
					im.setLayoutX(tileSize);
					im.setLayoutY(tileSize);
					im.setTranslateX((i * tileSize) - x); // 352
					im.setTranslateY((row * tileSize) - y); // 288
					g.getRoot().getChildren().add(im);
				}
			}
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the X coordinate for the tiles
	 * 
	 * @return X coordinate
	 */
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Returns the Y coordinate for the tiles
	 * 
	 * @return Y coordinate
	 */
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

	public int getLength() {
		return mapLength;
	}

	public void right(double inc) {
		if (x <= (mapLength - 19) * 48) {
			if ((x + inc) <= (mapLength - 19) * 48) {
				x += inc;
			} else {
				x = (mapLength - 19) * 48;
			}
		}

	}

	public void left(double inc) {
		if (x > 48) {
			if ((x - inc) >= 48) {
				x -= inc;
			} else {
				x = 48;
			}
		}
	}

	public boolean isBeginning() {
		if(x <= tileSize) {
			return true;
		}
		return false;
	}

	public boolean isEnd() {
		if(x >= (mapLength-19) * 48) {
			return true;
		}
		return false;
	}
	
	public int getCol() {
		return col;
	}
	
	public static int getMapLength() {
		return mapLength;
	}

	public void loadImg() {
		tiles = new Image[11];
		Image untergrund = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/untergrund.png"));
		Image decke = new Image(getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/decke.png"));
		Image block = new Image(getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/block.png"));
		Image einzelblockU = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/einzelblock_untergrund.png"));
		Image fliegPlatEinz = new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/map/textures/fliegende_platform_einzelblock.png"));
		Image fliegPlat = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/fliegende_platform.png"));
		Image linksFliegPlat = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/links_fliegende_platform.png"));
		Image linksUnter = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/links_untergrund.png"));
		Image rechtsFliegPlat = new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/map/textures/rechts_fliegende_platform.png"));
		Image rechtsUnter = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/rechts_untergrund.png"));

		tiles[0] = untergrund;
		tiles[1] = decke;
		tiles[2] = block;
		tiles[3] = einzelblockU;
		tiles[4] = rechtsUnter;
		tiles[5] = fliegPlat;
		tiles[6] = linksFliegPlat;
		tiles[7] = linksUnter;
		tiles[8] = rechtsFliegPlat;
		tiles[9] = fliegPlatEinz;
	}

}
