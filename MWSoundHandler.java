import java.io.*;
import java.applet.AudioClip;
import javax.swing.JApplet;

public class MWSoundHandler {
     
     private AudioClip expClip, introClip;
	 private AudioClip bgClip;
     
     public MWSoundHandler() {
          File file = new File("explosion.wav"); //For when we hit 
          File introFile = new File("StartSong.wav"); //The Star
		  File bgFile = new File("marioSong.wav"); //Mid Way Gameplay music
          try {
               this.expClip = JApplet.newAudioClip(file.toURI().toURL()); //We extract the music by passing in the file URL
          } catch (Exception e) {
               e.printStackTrace(); //The class requires us to use a try catch if we were to run into an exception. 
          } 
          try {
               this.introClip = JApplet.newAudioClip(introFile.toURI().toURL());
          } catch (Exception e) {
               e.printStackTrace(); //Same concept used here...
          }
		  try {
               this.bgClip = JApplet.newAudioClip(bgFile.toURI().toURL());
          } catch (Exception e) {
               e.printStackTrace(); //and here...
          }
     } 
     
     public void playExp() {
          this.expClip.stop(); //We can stop the music by using this method
          
          try {
               Thread.sleep(1);
          } catch (Exception e){
               e.printStackTrace();
          } 
          
          this.expClip.play(); //or it'll continue to play
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

