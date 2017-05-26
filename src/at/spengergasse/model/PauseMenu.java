package at.spengergasse.model;

import java.util.ArrayList;

public class PauseMenu {

	private ArrayList<String> items;

	private double x;
	private double y;
	
	private int state;

	public PauseMenu() {
		items = new ArrayList<>();
		items.add("Resume");
		items.add("Options");
		items.add("Back to Menu");
		items.add("Exit");
		x = 100;
		y = 108;
		this.state = 0;
	}

	public ArrayList<String> getItems() {
		return this.items;
	}

	// Moving the cursor in pause
	public void moveCursorDown() {
		if (y == 108 || y == 180 || y == 252) {
			y += 72;
		} else {
			y = 108;
		}
		checkState();
	}

	public void moveCursorUp() {
		if(y == 324 || y == 252 || y == 180) {
			y -= 72;
		} else {
			y = 324;
		}
		checkState();
	}
	
	public void checkState() {
		if(y == 108) {
			state = 0;
		} else if(y == 180) {
			state = 1;
		} else if(y == 252) {
			state = 2;
		} else if(y == 324) {
			state = 3;
		}
		System.out.println(state);
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

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
	

}
