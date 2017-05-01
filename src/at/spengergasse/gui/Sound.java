package at.spengergasse.gui;

import java.io.File;

import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {
	
	private MediaPlayer gameTrack;
	private MediaPlayer effects;

	public Sound() {
		String gameTrackFile = "src/at/spengergasse/resources/game/soundEffects/Game_Sound_Track.wav";
		Media sound1 = new Media(new File(gameTrackFile).toURI().toString());
		gameTrack = new MediaPlayer(sound1);
		gameTrack.setCycleCount(Timeline.INDEFINITE);
		gameTrack.play();
		
	}
	
	public void playJumpSound() {
		String jumpSoundFile = "src/at/spengergasse/resources/game/soundEffects/Jump.wav";
		Media sound = new Media(new File(jumpSoundFile).toURI().toString());
		effects = new MediaPlayer(sound);
		effects.setVolume(0.3);
		effects.play();
	}
	
	public void playCoinSound() {
		String coinCollectSoundFile = "src/at/spengergasse/resources/game/soundEffects/Pickup_Coin.wav";
		Media sound = new Media(new File(coinCollectSoundFile).toURI().toString());
		effects = new MediaPlayer(sound);
		effects.setVolume(0.4);
		effects.play();
	}

}
