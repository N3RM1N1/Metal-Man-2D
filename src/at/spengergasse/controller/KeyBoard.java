/**
 * 
 */
package at.spengergasse.controller;

import at.spengergasse.gui.FrameFX;
import at.spengergasse.gui.Sound;
import at.spengergasse.model.GameLauncher;
import at.spengergasse.model.Player;
import at.spengergasse.model.TileMap;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Daniel Danilovic
 *
 */
public class KeyBoard implements EventHandler<KeyEvent>{

	private TileMap tileMap;
	private Player player;
	private Sound soundEffects;
	private FrameFX g;
	private GameLauncher launcher;
	
	/**
	 * 
	 */
	public KeyBoard(TileMap tileMap, Player player, Sound soundEffects, FrameFX g, GameLauncher launcher) {
		this.tileMap = tileMap;
		this.player = player;
		this.soundEffects = soundEffects;
		this.g = g;
		this.launcher = launcher;
	}

	@Override
	public void handle(KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getEventType() == KeyEvent.KEY_PRESSED) {
			if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
				if(player.isRight() == false && !g.launcherOpen) {
					g.setTargetFrameCounter(0);
					tileMap.resetMovement();
					player.setStanding(false);
					player.setRight(true);
					
				}
			} else if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
				if(player.isLeft() == false && !g.launcherOpen) {
					g.setTargetFrameCounter(0);
					tileMap.resetMovement();
					player.setStanding(false);
					player.setLeft(true);
				}
			} else if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
				if(event.getCode() == KeyCode.UP) {
					launcher.moveCusorUp();
				}
				
				if(player.isJumping() == false && player.isFighting() == false && !g.launcherOpen) {
					soundEffects.playJumpSound();
					g.setTargetFrameCounter(0);
					player.setJumping(true);
					player.setStanding(false);
				}
			} else if(event.getCode() == KeyCode.E) {
				if(!g.launcherOpen) {
					player.setFighting(true);
				}
				
			}
			else if(event.getCode() == KeyCode.DOWN){
				if(g.launcherOpen) {
					launcher.moveCursorDown();
				}
			}
			if(g.launcherOpen) {
				if(event.getCode() == KeyCode.ENTER) {
					launcher.pick();
				}
			}
		} else if(event.getEventType() == KeyEvent.KEY_RELEASED) {
			if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
				player.setRight(false);
				tileMap.resetMovement();
			}
			if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
				player.setLeft(false);
				tileMap.resetMovement();
			}
		}
	}

}
