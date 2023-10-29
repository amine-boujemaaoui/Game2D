package main;

import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	int volumeIndicator = 2;
	float volume = 0.0f;
	FloatControl fc;

	public Sound() {
		
		soundURL[0]  = getClass().getResource("/sounds/background_music.wav");
		soundURL[1]  = getClass().getResource("/sounds/ui_open.wav");
		soundURL[2]  = getClass().getResource("/sounds/ui_enter.wav");
		soundURL[3]  = getClass().getResource("/sounds/ui_back.wav");
		soundURL[4]  = getClass().getResource("/sounds/ui_select.wav");
		soundURL[5]  = getClass().getResource("/sounds/ui_unselect.wav");
		soundURL[6]  = getClass().getResource("/sounds/pickup_coin.wav");
		soundURL[7]  = getClass().getResource("/sounds/pickup_item.wav"); 
		soundURL[8]  = getClass().getResource("/sounds/pickup_mana.wav");
		soundURL[9]  = getClass().getResource("/sounds/pickup_key.wav");
		soundURL[10] = getClass().getResource("/sounds/powerup.wav");
		soundURL[11] = getClass().getResource("/sounds/footstep.wav");
		soundURL[12] = getClass().getResource("/sounds/slime1.wav");
		soundURL[13] = getClass().getResource("/sounds/swing.wav");
		soundURL[14] = getClass().getResource("/sounds/slimeHit.wav");
		soundURL[15] = getClass().getResource("/sounds/receivedamage.wav");
		soundURL[16] = getClass().getResource("/sounds/weapon_break.wav");
		soundURL[17] = getClass().getResource("/sounds/bottle.wav");
		soundURL[18] = getClass().getResource("/sounds/cloth.wav");
		soundURL[19] = getClass().getResource("/sounds/weapon.wav");


	}
	public void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
			
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
	public void checkVolume() {

		switch (volumeIndicator) {
		case 0: volume = -60.0f; break;
		case 1: volume = -53.0f; break;
		case 2: volume = -40.0f; break;
		case 3: volume = -35.0f;  break;
		case 4: volume = -20.0f;  break;
		case 5: volume = -12.0f;  break;
		case 6: volume = -5.0f;  break;
		case 7: volume = 2.0f;  break;
		case 8: volume = 6.0f;  break;
		}
		fc.setValue(volume);
	}
}