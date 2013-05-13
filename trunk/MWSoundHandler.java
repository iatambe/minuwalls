import java.io.*;
import java.applet.AudioClip;
import javax.swing.JApplet;

public class MWSoundHandler {
     
     private AudioClip expClip, introClip;
	 private AudioClip bgClip;
     
     public MWSoundHandler() {
          File file = new File("explosion.wav");
          File introFile = new File("StartSong.wav");
		  File bgFile = new File("marioSong.wav");
          try {
               this.expClip = JApplet.newAudioClip(file.toURI().toURL());
          } catch (Exception e) {
               e.printStackTrace();
          } 
          try {
               this.introClip = JApplet.newAudioClip(introFile.toURI().toURL());
          } catch (Exception e) {
               e.printStackTrace();
          }
		  try {
               this.bgClip = JApplet.newAudioClip(bgFile.toURI().toURL());
          } catch (Exception e) {
               e.printStackTrace();
          }
     } 
     
     public void playExp() {
          this.expClip.stop();
          
          try {
               Thread.sleep(1);
          } catch (Exception e){
               e.printStackTrace();
          } 
          
          this.expClip.play();
     } 
     
     public void playIntro() {
          this.introClip.stop();
          
          try {
               Thread.sleep(1);
          } catch (Exception e){
               e.printStackTrace();
          } 
          
          this.introClip.play();
     }
	 
	 public void startBG() {
	 	try {
			Thread.sleep(1);
		} catch(Exception e) {e.printStackTrace();}
		
		this.bgClip.loop();
	}
	
	public void stopBG() {
		/*
		try {
			Thread.sleep(1);
		} catch(Exception e) {e.printStackTrace();}*/
		
		this.bgClip.stop();
	}
     
} 

