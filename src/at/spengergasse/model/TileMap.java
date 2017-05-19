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
						Extensions ext = new Extensions(g, col * tileSize, row * tileSize, col, row, soundEffects);
						extensions.add(ext);
					} else if (map[row][col] == 14) {
						Enemies en = new SlimeMonster(this.g, row * tileSize - 52,  col);
						enemies.add(en);
					}else if (map[row][col] == 12){
						Enemies en = new SkeletonWarrior(this.g, row * tileSize - 40, col);
						enemies.add(en);
					}else if (map[row][col] == 13){
						Enemies en = new WildBoar(this.g, row * tileSize - 33, col);
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
				if (e.getOpacity() != 0.0)
					e.left(smooth);
			}
			smoothOutMovement(0.5);
		} else if (right == true && left == false) {
			right(smooth);
			for (Extensions e : extensions) {
				if (e.getOpacity() != 0.0)
					e.right(smooth);
			}
			smoothOutMovement(0.5);
		}
	}

	public void extUpdate() {
		for (Extensions e : extensions) {
			if (e.getOpacity() != 0.0)
				e.checkCounter(g.getTargetFrameCounter());
		}

		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).isDefeated()) {
				enemies.remove(i);
			}
		}

		if (!enemies.isEmpty()) {
			for (Enemies en : enemies) {
				if (!en.isDefeated()) {
					if (en instanceof SlimeMonster) {
						((SlimeMonster) en).checkCounter(g.getTargetFrameCounter());
						((SlimeMonster) en).update();
					} else if(en instanceof SkeletonWarrior) {
						((SkeletonWarrior) en).checkCounter(g.getTargetFrameCounter());
						((SkeletonWarrior) en).update();
					} else if(en instanceof WildBoar) {
						((WildBoar) en).checkCounter(g.getTargetFrameCounter());
						((WildBoar) en).update();
					}
				}
			}
		}
	}

	public void render() {
		draw(new ImageView());
	}

	public void draw(ImageView im) {
		if (this.x > (col + 2) * tileSize) {
			if (bufferedLength < mapLength) {
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
				} else if (rc == 11) {
					for (Extensions e : extensions) {
						if (i == e.getCol() && row == e.getRow() && e.getOpacity() != 0.0)
							e.draw(new ImageView(), (i * 48) - (x - 48));
					}
				} else if (rc == 12 || rc == 13 || rc == 14) {
					if (!enemies.isEmpty()) {
						for (Enemies en : enemies) {
							if (!en.isDefeated()) {
								System.out.println("col: " + col);
								if (en instanceof SlimeMonster && col <= ((SlimeMonster) en).getCol()+2 && rc == 14) {  	// auf col überprüfen
									((SlimeMonster) en).draw(new ImageView(), (i * 48) - (x - 48));
									System.out.println(((SlimeMonster) en).getCol());
								} else if (en instanceof SkeletonWarrior && ((SkeletonWarrior) en).getCol()+2 >= col && rc == 12) {
									((SkeletonWarrior) en).draw(new ImageView(), (i * 48) - (x - 48));
								} else if (en instanceof WildBoar && ((WildBoar) en).getCol()+2 >= col && rc == 13) {
									((WildBoar) en).draw(new ImageView(), (i * 48) - (x - 48));
									System.out.println("WildBoar: " + ((WildBoar) en).getCol());
								}
							}
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
		smooth = 0.0;

	}

	public double getSmooth() {
		return smooth;
	}

	public void collect(int col, int row) {
		if (!extensions.isEmpty()) {
			for (Extensions e : extensions) {
				if ((e.getCol() == col || e.getCol() - 1 == col) && e.getRow() == row) {
					e.setCollected(true);
				}
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ArrayList<Enemies> getEnemies() {
		return enemies;
	}

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
			System.out.println("Gesetzt: " + x);
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
		int[][] mapInPx = new int[mapHeight * tileSize][mapLength * tileSize];
		int collision = 0;

		for (int row = 0; row < mapHeight; row++) {
			for (int col = 0; col < mapLength; col++) {
				if (this.map[row][col] == 1 || this.map[row][col] == 2 || this.map[row][col] == 3
						|| this.map[row][col] == 5 || this.map[row][col] == 8 || this.map[row][col] == 4) {
					collision = 1;
				} else if (this.map[row][col] == 7 || this.map[row][col] == 9 || this.map[row][col] == 6
						|| this.map[row][col] == 10) {
					collision = 2;
				} else if (this.map[row][col] == 11) {
					collision = 3;
				}
				for (int i = row * tileSize; i < row * tileSize + tileSize; i++) {
					for (int j = col * tileSize; j < col * tileSize + tileSize; j++) {
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
