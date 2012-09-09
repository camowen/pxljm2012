package game;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundSystem {
	
	public static Clip SFX_EXPLOSION;
	public static Clip SFX_RELOAD;
	public static Clip SFX_RELOAD2;
	public static Clip SFX_ROCKET;
	public static Clip SFX_SHOT;
	public static Clip SFX_SHOT2;
	public static Clip SFX_RUNNING;
	public static Clip SFX_SPLAT; 
	public static Clip SFX_SPLATTER;
	
	public static Clip BG_MUSIC;
	
	public static void Init() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		SFX_EXPLOSION = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(Globals.SFX_EXPLOSION)));
        SFX_EXPLOSION.open(inputStream);
        
        SFX_RELOAD = AudioSystem.getClip();
        AudioInputStream inputStream2 = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(Globals.SFX_RELOAD)));
        SFX_RELOAD.open(inputStream2);
        
        SFX_RELOAD2 = AudioSystem.getClip();
        AudioInputStream inputStream3 = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(Globals.SFX_RELOAD2)));
        SFX_RELOAD2.open(inputStream3);
        
        SFX_ROCKET = AudioSystem.getClip();
        AudioInputStream inputStream4 = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(Globals.SFX_ROCKET)));
        SFX_ROCKET.open(inputStream4);
        
        SFX_SHOT = AudioSystem.getClip();
        AudioInputStream inputStream5 = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(Globals.SFX_SHOT)));
        SFX_SHOT.open(inputStream5);
        
        SFX_SHOT2 = AudioSystem.getClip();
        AudioInputStream inputStream6 = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(Globals.SFX_SHOT2)));
        SFX_SHOT2.open(inputStream6);
        
        SFX_RUNNING = AudioSystem.getClip();
        AudioInputStream inputStream7 = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(Globals.SFX_RUNNING)));
        SFX_RUNNING.open(inputStream7);
        
        SFX_SPLAT = AudioSystem.getClip();
        AudioInputStream inputStream8 = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(Globals.SFX_SPLAT)));
        SFX_SPLAT.open(inputStream8);
        
        SFX_SPLATTER = AudioSystem.getClip();
        AudioInputStream inputStream9 = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(Globals.SFX_SPLATTER)));
        SFX_SPLATTER.open(inputStream9);
        
        BG_MUSIC = AudioSystem.getClip();
        AudioInputStream inputStream10 = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(Globals.MUS_BG)));
        BG_MUSIC.open(inputStream10);
	}
	
	public static void play(Clip c){
		if(c == null) return;
		
		c.start();
	}
	
	public static void playForever(Clip c){
		if(c == null) return;
		
		c.loop(Clip.LOOP_CONTINUOUSLY);
		c.start();
	}

}
