/**
 * 
 */
package at.spengergasse.controller;

import at.spengergasse.gui.FrameFX;
import at.spengergasse.gui.Sound;
import at.spengergasse.model.Player;
import at.spengergasse.model.TileMap;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Danilovic Daniel
 *
 */
public class KeyBoard implements EventHandler<KeyEvent>{

	private TileMap tileMap;
	private Player player;
	private Sound soundEffects;
	private FrameFX g;
	/**
	 * 
	 */
	public KeyBoard(TileMap tileMap, Player player, Sound soundEffects, FrameFX g) {
		this.tileMap = tileMap;
		this.player = player;
		this.soundEffects = soundEffects;
		this.g = g;
	}

	@Override
	public void handle(KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getEventType() == KeyEvent.KEY_PRESSED) {
			if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
				if(player.isRight() == false) {
					g.setTargetFrameCounter(0);
					player.setRight(true);
					tileMap.setRight(true);
				}
			}
			if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
				if(player.isLeft() == false) {
					g.setTargetFrameCounter(0);
					tileMap.setLeft(true);
					player.setLeft(true);
				}
			}
			if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
				if(player.isJumping() == false && player.isFighting() == false) {
					soundEffects.playJumpSound();
					g.setTargetFrameCounter(0);
					player.setJumping(true);
				}
			}
			if(event.getCode() == KeyCode.E) {
				player.setFighting(true);
			}
		} else if(event.getEventType() == KeyEvent.KEY_RELEASED) {
			if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
				player.setRight(false);
				player.setStanding(true);
				tileMap.setRight(false); // Don't move anymore
				tileMap.resetMovement();
			}
			if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
				player.setLeft(false);
				player.setStanding(true);
				tileMap.setLeft(false); // Don't move anymore
				tileMap.resetMovement();
			}
		}
	}

}
