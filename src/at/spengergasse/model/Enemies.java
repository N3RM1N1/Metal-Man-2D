package at.spengergasse.model;

import at.spengergasse.gui.FrameFX;

public abstract class Enemies {

	private FrameFX g;

	private boolean defeated;

	public Enemies(FrameFX g) {
		this.g = g;
		this.defeated = false;
	}
	
	
	public FrameFX getFrame() {
		return this.g;
	}

	public void setDefeated(boolean defeated) {
		this.defeated = defeated;
	}

	public boolean isDefeated() {
		return this.defeated;
	}
}
