package game;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundSystem {

	public static Clip BG_MUSIC;
	
	public static Clip RUNNING;

	public static void Init() throws LineUnavailableException, IOException,
			UnsupportedAudioFileException {
		
		RUNNING = AudioSystem.getClip();
		AudioInputStream inputStream2 = AudioSystem
				.getAudioInputStream(new BufferedInputStream(
						new FileInputStream(Globals.SFX_RUNNING)));
		RUNNING.open(inputStream2);
		RUNNING.loop(Clip.LOOP_CONTINUOUSLY);

		// MP3s
		// File file = new File(Globals.MUS_BG);
		// AudioInputStream in= AudioSystem.getAudioInputStream(file);
		// AudioInputStream din = null;
		// AudioFormat baseFormat = in.getFormat();
		// AudioFormat decodedFormat = new
		// AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
		// baseFormat.getSampleRate(),
		// 16,
		// baseFormat.getChannels(),
		// baseFormat.getChannels() * 2,
		// baseFormat.getSampleRate(),
		// false);
		// din = AudioSystem.getAudioInputStream(decodedFormat, in);
		// BG_MUSIC = AudioSystem.getClip();
		// //AudioInputStream inputStream10 =
		// AudioSystem.getAudioInputStream(new BufferedInputStream(new
		// FileInputStream(Globals.MUS_BG)));
		// BG_MUSIC.open(din);
	}

	public static void play(final Clip c) {
		new Thread() {
			public void run() {
				c.setMicrosecondPosition(0);
				c.start();
			}
		}.start();
	}
	
	public static void startRunning(){
		if(!RUNNING.isActive()){
			RUNNING.setMicrosecondPosition(0);
			RUNNING.start();
		}
	}
	
	public static void stopRunning(){
		RUNNING.stop();
	}
	

	public static void play(String sfx) {
		try {
			Clip c = AudioSystem.getClip();
			AudioInputStream inputStream2 = AudioSystem
					.getAudioInputStream(new BufferedInputStream(
							new FileInputStream(sfx)));
			c.open(inputStream2);
			c.start();
		} catch (Exception e) {

		}
	}

	public static void playForever(Clip c) {
		c.loop(Clip.LOOP_CONTINUOUSLY);
		c.start();
	}

}
