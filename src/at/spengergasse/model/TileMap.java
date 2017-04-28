package at.spengergasse.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import at.spengergasse.gui.FrameFX;

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
	private int mapWidth;
	private int mapHeight;
	private int mapLength;

	// Boolean value for moving to the left or the right
	private boolean left;
	private boolean right;

	// The size of the tile
	public int tileSize;

	private int col;

	private double smooth = 0.0;
	
	private ArrayList<Extensions> extensions;
	
	private String levelName;

	public TileMap(int tileSize, String levelName, FrameFX frame) {

		// setting the coordinates for the tiles
		this.x = tileSize;
		this.y = tileSize;
		this.levelName = levelName;
		this.extensions = new ArrayList<Extensions>();

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
					if(map[row][col] == 11) {
						Extensions ext = new Extensions(g);
						extensions.add(ext);
					}
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
		if(Player.getX() == 192) {
			if (left == true && right == false) {
				left(smooth);
				smoothOutMovement(0.3);
			} else if (right == true && left == false) {
				right(smooth);
				smoothOutMovement(0.3);
			}
		}
		if(!extensions.isEmpty()) {
			for(Extensions e : extensions) {
				e.checkCounter(g.getTargetFrameCounter());
			}
		}
	}
	
	public void render() {
		draw(new ImageView());
		
	}
	
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
//					e.draw(new ImageView(), x, y);
//					System.out.println(e);
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
		smooth = 0.0;

	}

	public double getSmooth() {
		return smooth;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public double getX() {
		return x;
	}

	public void setX(double x) {
		if(x >= 48) {
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
	
	public int getMapLength() {
		return this.mapLength;
	}
	
	public int[][] getMap() {
		return map;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public int getRow() {
		return mapHeight;
	}

	public void loadImg() {
		tiles = new Image[11];
		Image untergrund = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/untergrund.gif"));
		Image decke = new Image(getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/decke.gif"));
		Image block = new Image(getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/block.gif"));
		Image einzelblockU = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/einzelblock_untergrund.gif"));
		Image fliegPlatEinz = new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/map/textures/fliegende_platform_einzelblock.gif"));
		Image fliegPlat = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/fliegende_platform.gif"));
		Image linksFliegPlat = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/links_fliegende_platform.gif"));
		Image linksUnter = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/links_untergrund.gif"));
		Image rechtsFliegPlat = new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/map/textures/rechts_fliegende_platform.gif"));
		Image rechtsUnter = new Image(
				getClass().getResourceAsStream("/at/spengergasse/resources/map/textures/rechts_untergrund.gif"));

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
