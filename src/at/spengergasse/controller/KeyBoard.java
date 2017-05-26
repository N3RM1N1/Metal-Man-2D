/**
 * 
 */
package at.spengergasse.controller;

import at.spengergasse.gui.FrameFX;
import at.spengergasse.gui.GameLauncher;
import at.spengergasse.gui.Sound;
import at.spengergasse.model.GameStateManager;
import at.spengergasse.model.PauseMenu;
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
	private GameLauncher gL;
	private GameStateManager launcher;
	private PauseMenu pauseMenu;
	
	/**
	 * 
	 */
	public KeyBoard(TileMap tileMap, Player player, Sound soundEffects, FrameFX g, GameStateManager launcher, PauseMenu pause) {
		this.tileMap = tileMap;
		this.player = player;
		this.soundEffects = soundEffects;
		this.g = g;
		this.launcher = launcher;
		this.pauseMenu = pause;
	}
	
	public KeyBoard(GameLauncher g, GameStateManager launcher) {
		this.gL = g;
		this.launcher = launcher;
	}

	@Override
	public void handle(KeyEvent event) {
		if(gL != null) {
			
			if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
				if(event.getCode() == KeyCode.UP) {
					if(gL != null && launcher.getState() <= 2)
						launcher.moveCusorUp();
				}
			}
			if(event.getCode() == KeyCode.DOWN){
				if(gL != null && launcher.getState() <= 2) {
					launcher.moveCursorDown();
				}
			}
			
			if(event.getCode() == KeyCode.LEFT){
				if(gL != null) {
					launcher.moveCursorLeft();
				}
			}
			
			if(event.getCode() == KeyCode.RIGHT){
				if(gL != null) {
					launcher.moveCursorRight();
				}
			}
			
			if(gL != null) {
				if(event.getCode() == KeyCode.ENTER) {
					gL.pick();
					if(launcher.getY() == 375) {
						this.gL = null;
					}
				}
			}
		} else {
			if(event.getEventType() == KeyEvent.KEY_PRESSED) {
				
				if(event.getCode() == KeyCode.ESCAPE) {
					if(g.isPaused()) {
						g.resumeGame();
					} else {
						g.pauseGame();
					}
					
				}
				
				if(event.getCode() == KeyCode.ENTER) {
					if(g.isPaused()) {
						g.pick();
					}
				}
				
				if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
					if(player.isRight() == false) {
						g.setTargetFrameCounter(0);
						tileMap.resetMovement();
						player.setStanding(false);
						player.setRight(true);
						
					}
				} 
				if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
					if(player.isLeft() == false) {
						g.setTargetFrameCounter(0);
						tileMap.resetMovement();
						player.setStanding(false);
						player.setLeft(true);
					}
				} 
				if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
					
					if(player.isJumping() == false && player.isFighting() == false && g.isPaused() == false) {
						soundEffects.playJumpSound();
						g.setTargetFrameCounter(0);
						player.setJumping(true);
						player.setStanding(false);
					} else {
						pauseMenu.moveCursorUp();
						g.placeCursor();
					}
				}
				
				if(event.getCode() == KeyCode.DOWN) {
					if(g.isPaused()) {
						pauseMenu.moveCursorDown();
						g.placeCursor();
					}
						
				}
				
				if(event.getCode() == KeyCode.E) {
						player.setFighting(true);
				}
			} else if(event.getEventType() == KeyEvent.KEY_RELEASED) {
				if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
					tileMap.setRight(false);
					player.setRight(false);
					tileMap.resetMovement();
				}
				if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
					tileMap.setLeft(false);
					player.setLeft(false);
					tileMap.resetMovement();
				}
			}
		}
		
	}

}
