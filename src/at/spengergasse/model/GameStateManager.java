/**
 * 
 */
package at.spengergasse.model;

import java.util.ArrayList;

public class GameStateManager {

	private double x;
	private double y;
	
	private int state; // 0 start game, 1 options, 2 Exit, 3 Level1, 4 Level2, ...

	/**
	 * Konstruktor
	 */
	public GameStateManager() {
		this.x = 280;
		this.y = 375;
		this.state = 0;
	} // Konstruktor Ende

	public void moveCursorDown() {
		if (y == 375 || y == 455) {
			y += 80;
			
		} else {
			y = 375;
			state = 0;
		}
		checkState();
	}

	public void moveCusorUp() {
		if (y == 535 || y == 455) {
			y -= 80;
		} else {
			y = 535;
			state = 2;
		}
		checkState();
	}
	
	public void moveCursorLeft() {
		if(state <= 17 && state > 3) {
			state --;
		} else {
			state = 17;
		}
	}
	
	public void moveCursorRight() {
		if(state >= 3 && state <= 16) {
			state ++;
		} else {
			state = 3;
		}
	}
	
	public void checkState() {
		if(y == 455)
			state = 1;
		else if(y == 535)
			state = 2;
		else
			state = 0;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	public int getState() {
		return this.state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	// Level Manager
	public ArrayList<Integer> levelManager() {
		ArrayList<Integer> levels = new ArrayList<>();
		
		for(int i = 1; i < 16; i ++) {
			levels.add(i);  // Levels
			levels.add(70); // height, width
		}
		
		this.x = 210; // The cursor
		this.y = 500;
		
		return levels;
	}
	

}// Klassen Ende
