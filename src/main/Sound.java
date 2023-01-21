package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip sound;
	URL soundURL[] = new URL[5];
	public Sound() {
		soundURL[0] = getClass().getResource("/music/GameMusic.wav");
		soundURL[1] = getClass().getResource("/music/receivedamage.wav");
		soundURL[2] = getClass().getResource("/music/titleScreenMusic.wav");

	}
	public void music(int i) {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			sound = AudioSystem.getClip();
			sound.open(ais);
			
		}catch(Exception e) {
		e.printStackTrace();
	}
}
	public void loop() {
		sound.loop(Clip.LOOP_CONTINUOUSLY);

	}
	public void play() {
		sound.start();
	
	}
	public void stop() {
		sound.stop();
		
	
	}

}
