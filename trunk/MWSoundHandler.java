import java.io.*;//the required files to make the program run
import java.applet.AudioClip;
import javax.swing.JApplet;

public class MWSoundHandler {//plays all the sounds needed in the program
	
	private AudioClip expClip, introClip;//expClip is explosion sound, introClip is intro music before each round.
	private AudioClip bgClip;//background music, mario music.
	
	public MWSoundHandler() {
		File file = new File("explosion.wav"); //file is the explosion sound
		File introFile = new File("StartSong.wav"); //introFile is StartSong
		File bgFile = new File("marioSong.wav"); //bgFile is marioSong
		try {//when instantiating the clips, a try catch must be used.
			this.expClip = JApplet.newAudioClip(file.toURI().toURL()); //sets the expClip to the explosion sound file
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.introClip = JApplet.newAudioClip(introFile.toURI().toURL());//see above
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.bgClip = JApplet.newAudioClip(bgFile.toURI().toURL());//see above
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playExp() {
		this.expClip.stop(); //just in case the exp is still playing, stop the sound.
		
		try {
			Thread.sleep(1);//there was an error with the sound not playing if two explosions played at the same time, this causes the thread to sleep for 1 millisecond
		} catch (Exception e){
			e.printStackTrace();
		}
		
		this.expClip.play(); //after stopping and pausing, play the clip.
	}
	
	public void playIntro() {//plays intro, same concept as playExp().
		this.introClip.stop();
		
		try {
			Thread.sleep(1);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		this.introClip.play();
	}
	
	public void startBG() {//bg music only plays once, so no need to stop it in the start method, we have another method for stopping it.
	 	try {
			Thread.sleep(1);
		} catch(Exception e) {e.printStackTrace();}
		
		this.bgClip.loop();//instead of playing the music once, we made the sound clip loop.
	}
	
	public void stopBG() {//stops the bg music.
		/*
		 try {
		 Thread.sleep(1);
		 } catch(Exception e) {e.printStackTrace();}*/
		
		this.bgClip.stop();
	}
	
}

