package at.spengergasse.game;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import javafx.util.*;

public class TileMap {
	// Game Instanz
	private Game g;

	// Die Matrix der Map
	private static int[][] map;

	// The Images will be saved here
	private Image[] images;

	// The coordinates
	private double x;
	private double y;

	// Height and width of the Map
	private int mapWidth;
	private int mapHeight;

	// Boolean value for moving to the left or the right
	private boolean left;
	private boolean right;

	// The size of the tile
	private int tileSize;
	
	private int row;
	private int col;

	public TileMap(int tileSize) {

		// setting the coordinates for the tiles
		this.x = tileSize;
		this.y = tileSize;
		
		left = false;
		right = false;
		
		row = 0;
		col = 0;

		// Creating a new Game
		g = new Game();

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
			FileReader fr = new FileReader("map");
			BufferedReader br = new BufferedReader(fr);

			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			map = new int[mapHeight][mapWidth];

			for (int row = 0; row < mapHeight; row++) {
				String line = br.readLine();
				String[] zeichen = line.split(" ");
				for (int col = 0; col < mapWidth; col++) {
					map[row][col] = Integer.parseInt(zeichen[col]);
				}
			}
			mapWidth = 32;
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		draw(new ImageView());
	}

	/**
	 * Drawing Method This method draws the map of the game
	 * 
	 * @param im
	 *            The ImageView, which represents the map
	 */
	public void draw(ImageView im) {
		if(x < 620) {
			col = 0;
			mapWidth = 32;
		}
//		if (x >= 620 && x < 1570) {
//			// Tile 31 asdf
//			col = 11;
//			mapWidth = 52;
//		} 
		if(x >= 1570 && x < 2540) {
			col = 31;
			mapWidth = 72;
		}
//		if(x >= 2540) {
//			col = 52;
//			mapWidth = 96;
//		}
		
		for (; row < mapHeight; row++) { // mapHeight
			for (; col < mapWidth; col++) { // mapWidth
				int rc = map[row][col];
				im = new ImageView(images[rc]);
				im.setFitHeight(tileSize);
				im.setFitWidth(tileSize);
				im.setLayoutX(tileSize);
				im.setLayoutY(tileSize);
				im.setTranslateX((col * tileSize) - x); // 352
				im.setTranslateY((row * tileSize) - y); // 288
				g.getGroup().getChildren().add(im);
			}
			col = 0;
		}
		row = 0;
		col = 0;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the X coordinate for the tiles
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

	public void right(double inc) {
		if (x <= 3696) {
			if ((x + inc) <= 3696) {
				x += inc;
			} else {
				x = 3696;
			}
		} else
			System.out.println("Ende");

	}

	public void left(double inc) {
		if (x > 48) {
			if ((x - inc) >= 48) {
				x -= inc;
			} else {
				x = 48;
			}

		} else {
			System.out.println("Ende");
		}
	}

	public void loadImg() {
		images = new Image[11];

		Image background = new Image(getClass().getResourceAsStream("/Res/Map_Textures_Dungeon/background.png"));
		Image untergrund = new Image(getClass().getResourceAsStream("/Res/Map_Textures_Dungeon/untergrund.png"));
		Image decke = new Image(getClass().getResourceAsStream("/Res/Map_Textures_Dungeon/decke.png"));
		Image block = new Image(getClass().getResourceAsStream("/Res/Map_Textures_Dungeon/block.png"));
		Image einzelblockU = new Image(getClass().getResourceAsStream("/Res/Map_Textures_Dungeon/einzelblock_untergrund.png"));
		Image fliegPlatEinz = new Image(getClass().getResourceAsStream("/Res/Map_Textures_Dungeon/fliegende_platform_einzelblock.png"));
		Image fliegPlat = new Image(getClass().getResourceAsStream("/Res/Map_Textures_Dungeon/fliegende_platform.png"));
		Image linksFliegPlat = new Image(getClass().getResourceAsStream("/Res/Map_Textures_Dungeon/links_fliegende_platform.png"));
		Image linksUnter = new Image(getClass().getResourceAsStream("/Res/Map_Textures_Dungeon/links_untergrund.png"));
		Image rechtsFliegPlat = new Image(getClass().getResourceAsStream("/Res/Map_Textures_Dungeon/rechts_fliegende_platform.png"));
		Image rechtsUnter = new Image(getClass().getResourceAsStream("/Res/Map_Textures_Dungeon/rechts_untergrund.png"));
		
		
		images[0] = background;
		images[1] = untergrund;
		images[2] = decke;
		images[3] = block;
		images[4] = einzelblockU;
		images[5] = rechtsUnter;
		images[6] = fliegPlat;
		images[7] = linksFliegPlat;
		images[8] = linksUnter;
		images[9] = rechtsFliegPlat;
		images[10] = fliegPlatEinz;
	}

}
