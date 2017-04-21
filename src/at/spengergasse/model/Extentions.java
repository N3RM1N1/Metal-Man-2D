package at.spengergasse.model;

import at.spengergasse.gui.FrameFX;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Extentions extends Player {
	
	private int TargetSpinCounter = 0;
	private FrameFX g;
	private TileMap level;
	private int[][] map;
	
	private double x;
	private double y;
	
	private int xCoordinate;
	
	private boolean collected;
	
	private ImageView[] coin;

	public Extentions(FrameFX frame, TileMap level) {
		super(frame, level);
		collected = false;
		this.g = frame;
		this.level = level;
		this.map = level.getMap();
		loadImg();
		initCoordinates();
	}
	
	public void initCoordinates() {
		for (int row = 0; row < level.getRow(); row++) {
			for (int col = 0; col < level.getMapLength(); col++) {
				int rc = map[row][col];
				if(rc == 11) {
					y = row * 48;
					x = col * 48;
					xCoordinate = ((int) (x/48));
				}
			}
		}
	}
	
	public void update() {
		if(level.getCol() - (xCoordinate+1) < x/48 ) {
			if((level.isBeginning() == false && level.isEnd() == false) && level.getRight()) {
				x -= level.getSmooth();
			} else if((level.isBeginning() == false && level.isEnd() == false) && level.getLeft()) {
				x += level.getSmooth();
			}
		}
	}
	
	public void render() {
		draw(new ImageView());
	}
	
	public void draw(ImageView im) {
		if(TargetSpinCounter == 0 || TargetSpinCounter == 6) {
			im = coin[0];
		} else if(TargetSpinCounter == 1) {
			im = coin[1];
		} else if(TargetSpinCounter == 2) {
			im = coin[2];
		} else if(TargetSpinCounter == 3) {
			im = coin[3];
		} else if(TargetSpinCounter == 4) {
			im = coin[4];
		} else if(TargetSpinCounter == 5) {
			im = coin[5];
		}
		im.setTranslateX(x);
		im.setTranslateY(y);
		g.getRoot().getChildren().add(im);
	}
	
	public void checkCounter(int targetCounter) {
		if(TargetSpinCounter == 6) {
			TargetSpinCounter = 0;
		}
		
		if(targetCounter % 9 == 0) {
			TargetSpinCounter ++;
		}
	}
	
	public void loadImg() {
		coin = new ImageView[6];
		
		ImageView image = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_0.gif")));
		coin[0] = image;
		
		image = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_1.gif")));
		coin[1] = image;
		
		image = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_2.gif")));
		coin[2] = image;
		
		image = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_3.gif")));
		coin[3] = image;
		
		image = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_4.gif")));
		coin[4] = image;
		
		image = new ImageView(new Image(getClass()
				.getResourceAsStream("/at/spengergasse/resources/player/extentions/pixelFrame_5.gif")));
		coin[5] = image;
	}

}
