package at.spengergasse.model;

import java.util.Random;

public class BackgroundStars {

	private double x;
	private double y;
	
	private int size;
	
	public BackgroundStars() {
		Random r = new Random();
		x = r.nextInt(1600)+48;
		y = r.nextInt(624)+48;
		size = r.nextInt(2) + 3;
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
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	

}
