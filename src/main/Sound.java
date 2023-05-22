package main;

import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	float volume = -20.0f;
	
	public Sound() {
		
		int i =0;
		soundURL[i] = getClass().getResource("/sounds/background_music.wav"); i++;
		soundURL[i] = getClass().getResource("/sounds/ui_open.wav");          i++;
		soundURL[i] = getClass().getResource("/sounds/ui_enter.wav");         i++;
		soundURL[i] = getClass().getResource("/sounds/ui_back.wav");          i++;
		soundURL[i] = getClass().getResource("/sounds/ui_select.wav");        i++;
		soundURL[i] = getClass().getResource("/sounds/ui_unselect.wav");      i++;
		soundURL[i] = getClass().getResource("/sounds/pickup_coin.wav");      i++;
		soundURL[i] = getClass().getResource("/sounds/pickup_heart.wav");     i++; 
		soundURL[i] = getClass().getResource("/sounds/pickup_mana.wav");      i++;
		soundURL[i] = getClass().getResource("/sounds/pickup_key.wav");       i++;
		soundURL[i] = getClass().getResource("/sounds/powerup.wav");          i++;
		soundURL[i] = getClass().getResource("/sounds/footstep.wav");         i++;
	}
	public void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume);
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	public void play() {
		
		clip.start();
	}
	public void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		
		clip.stop();
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
}