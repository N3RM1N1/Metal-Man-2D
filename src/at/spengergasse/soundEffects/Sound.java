package at.spengergasse.soundEffects;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {

	public Sound() {
		// TODO Auto-generated constructor stub
	}
	
	public Media playJumpSound() {
		String jumpSoundFile = "Jump.wav";
		Media sound = new Media(new File(jumpSoundFile).toURI().toString());
		
		return sound;
	}

}
